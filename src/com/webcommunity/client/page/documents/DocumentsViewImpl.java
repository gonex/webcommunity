package com.webcommunity.client.page.documents;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class DocumentsViewImpl extends Composite implements DocumentsView {

	interface DocumentsViewImplUiBinder extends	UiBinder<Widget, DocumentsViewImpl> {
	}

	private static DocumentsViewImplUiBinder uiBinder = GWT.create(DocumentsViewImplUiBinder.class);

	
	public DocumentsViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}
}
