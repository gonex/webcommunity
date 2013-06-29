package com.webcommunity.client.page.documents;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;

public class DocumentsViewImpl extends Composite implements DocumentsView {

	interface DocumentsViewImplUiBinder extends	UiBinder<Widget, DocumentsViewImpl> {
	}

	private static DocumentsViewImplUiBinder uiBinder = GWT.create(DocumentsViewImplUiBinder.class);

	private Presenter presenter;

	@UiField FlexTable dokumentLinksTable;

	
	public DocumentsViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
		this.presenter.getDocumentLinks();
	}

	@Override
	public void updateDocumentLinks(String[] result) {
		dokumentLinksTable.removeAllRows();
		
		dokumentLinksTable.addStyleName("documentsLinkList");

		if (result.length > 0) {
			for (String link : result) {
				int row = dokumentLinksTable.getRowCount() ;
				dokumentLinksTable.setHTML(row, 0, constructDocumentLink(link));
			}
		} else {
			dokumentLinksTable.setText(0, 0, "Login påkrævet");
		}
	}
	
	private String constructDocumentLink(String link) {
		return link != null ? "<a href=\"" + link + "\" target=\"_blank\">" + link + "</a>" : "";
	}
}
