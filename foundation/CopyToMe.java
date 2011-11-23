/**
 * Copyright (C) 2011 tachibanakikaku.com Limited.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.tachibanakikaku;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.model.FileFolderService;
import org.alfresco.service.cmr.model.FileInfo;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.cmr.security.AuthenticationService;
import org.alfresco.service.cmr.security.PersonService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.DeclarativeWebScript;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptRequest;

public class CopyToMe extends DeclarativeWebScript {

	private AuthenticationService authenticationService;
	private FileFolderService fileFolderService;
	private NodeService nodeService;
	private PersonService personService;
	private String BACKUP_FOLDER = "bak";
	private static final Log log = LogFactory.getLog(CopyToMe.class);

	public void setFileFolderService(FileFolderService fileFolderService) {
		this.fileFolderService = fileFolderService;
	}

	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}

	public void setAuthenticationService(
			AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req,
			Status status, Cache cache) {

		Map<String, Object> model = new HashMap<String, Object>();

		// storeRef for "workspace://SpacesStore"
		StoreRef storeRef = new StoreRef(StoreRef.PROTOCOL_WORKSPACE,
				"SpacesStore");

		/*
		 * get contents under the specified space
		 */
		String uuid = req.getParameter("uuid");
		log.debug("uuid: " + uuid);
		NodeRef nodeRef = new NodeRef(storeRef, uuid);
		log.debug("copy src space: " + nodeRef);
		List<FileInfo> contents = fileFolderService.list(nodeRef);

		/*
		 * get "<userHome>/bak" space
		 */
		NodeRef personRef = personService.getPerson(authenticationService
				.getCurrentUserName());
		String homeFolder = String.valueOf(nodeService.getProperty(personRef,
				ContentModel.PROP_HOMEFOLDER));
		NodeRef homeFolderRef = new NodeRef(homeFolder);
		NodeRef destFolder = fileFolderService.searchSimple(homeFolderRef,
				BACKUP_FOLDER);
		if (destFolder == null) {
			destFolder = fileFolderService.create(homeFolderRef, BACKUP_FOLDER,
					ContentModel.TYPE_FOLDER).getNodeRef();
		}

		log.debug("copy target space: " + destFolder);

		// copy files to "/<userHome>/bak/" space
		for (Iterator<FileInfo> it = contents.iterator(); it.hasNext();) {
			FileInfo f = it.next();
			try {
				FileInfo copied = fileFolderService.copy(f.getNodeRef(),
						destFolder, null);
				log.debug("copied file: " + copied.getName());
			} catch (Exception e) {
				log.error("Error occurred while copy " + f.getName());
				log.error(e);
			}
		}

		/*
		 * prepare model to return
		 */
		JSONObject json = new JSONObject();
		try {
			json.put("count", contents.size());
			json.put("time", new Date());
		} catch (JSONException e) {
			log.error(e);
		}
		model.put("result", json.toString());
		return model;
	}
}
