package javaja;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.enums.BaseTypeId;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.apache.commons.lang3.StringUtils;

public class CMISTest {

	private static final String ALFRSCO_ATOMPUB_URL = "http://localhost:8080/alfresco/service/cmis";
	private static final String REPOSITORY_ID = "47f103d7-bc34-462c-b8b7-8df9d3f39462"; // <cmis:repositoryId>
																						// value

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CMISTest javaja = new CMISTest();
		Folder root = javaja.connect();
		System.out.println(root.getName());
		javaja.showFolderContents(0, root);
		Folder newFolder = javaja.createFolder(root,
				String.valueOf(System.currentTimeMillis()));
		System.out.println("created folder: " + newFolder.getName());
	}

	private Folder createFolder(Folder folder, String name) {
		Map<String, String> props = new HashMap<String, String>();
		props.put(PropertyIds.OBJECT_TYPE_ID, "cmis:folder");
		props.put(PropertyIds.NAME, name);
		Folder newFolder = folder.createFolder(props);
		return newFolder;
	}

	private void showFolderContents(int depth, Folder folder) {
		String indent = StringUtils.repeat("\t", depth);
		for (Iterator<CmisObject> it = folder.getChildren().iterator(); it
				.hasNext();) {
			CmisObject o = it.next();
			if (BaseTypeId.CMIS_DOCUMENT.equals(o.getBaseTypeId())) {
				System.out.println(indent.toString() + "[Docment] "
						+ o.getName());
			} else if (BaseTypeId.CMIS_FOLDER.equals(o.getBaseTypeId())) {
				System.out.println(indent.toString() + "[Folder] "
						+ o.getName());
				showFolderContents(++depth, (Folder) o);
			}
		}
	}

	private Folder connect() {
		SessionFactory sessionFactory = SessionFactoryImpl.newInstance();
		Map<String, String> parameter = new HashMap<String, String>();
		parameter.put(SessionParameter.USER, "admin");
		parameter.put(SessionParameter.PASSWORD, "admin");
		parameter.put(SessionParameter.ATOMPUB_URL, ALFRSCO_ATOMPUB_URL);
		parameter.put(SessionParameter.BINDING_TYPE,
				BindingType.ATOMPUB.value());
		parameter.put(SessionParameter.REPOSITORY_ID, REPOSITORY_ID);
		Session session = sessionFactory.createSession(parameter);
		return session.getRootFolder();
	}

}
