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
package com.tachibanakikaku.mryoshio;

import java.util.List;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.action.executer.ActionExecuterAbstractBase;
import org.alfresco.service.cmr.action.Action;
import org.alfresco.service.cmr.action.ParameterDefinition;
import org.alfresco.service.cmr.model.FileFolderService;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author mryoshio
 */
public class MyActionExecuter extends ActionExecuterAbstractBase {

	private static Log logger = LogFactory.getLog(MyActionExecuter.class);
	public static final String NAME = "my-action";
	private static final String clazz = MyActionExecuter.class.getSimpleName();

	private FileFolderService fileFolderService;
	private NodeService nodeService;

	/**
	 * 
	 * @param fileFolderService
	 */
	public void setFileFolderService(FileFolderService fileFolderService) {
		this.fileFolderService = fileFolderService;
	}

	/**
	 * 
	 * @param nodeService
	 */
	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}

	/**
	 * 
	 */
	@Override
	protected void executeImpl(Action action, NodeRef actionedUponNodeRef) {
		logger.debug(clazz + "#executeImpl");
		ChildAssociationRef ref = nodeService
				.getPrimaryParent(actionedUponNodeRef);
		try {
			fileFolderService.copy(
					actionedUponNodeRef,
					ref.getParentRef(),
					nodeService.getProperty(actionedUponNodeRef,
							ContentModel.PROP_NAME) + ".bak");
			logger.debug("Created backup of uploaede file");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	@Override
	protected void addParameterDefinitions(List<ParameterDefinition> paramList) {
		logger.debug(clazz + "#executeImpl");
	}

}
