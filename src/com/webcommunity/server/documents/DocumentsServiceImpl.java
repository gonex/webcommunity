package com.webcommunity.server.documents;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.webcommunity.client.page.documents.service.DocumentsService;
import com.webcommunity.server.UserManager;
import com.webcommunity.shared.UserRole;
import com.webcommunity.shared.users.UserEntry;

public class DocumentsServiceImpl extends RemoteServiceServlet implements DocumentsService {

	private static final long serialVersionUID = 7793243291354685886L;

	
	@Override
	public String[] getDocumentLinks(String sessionId) {
		return hasPermission(sessionId) ? new String[] {"https://drive.google.com/folderview?id=0B6krNfjcP-veREtLTXBvb1hRcU0&usp=sharing"} : new String[0];
	}
	
	private boolean hasPermission(String sessionId) {
		UserEntry user = UserManager.getUserBySessionId(sessionId);
		if (user != null) {
			return user.getUserRole() > UserRole.NONE;
		}
		
		return false;
	}
}
