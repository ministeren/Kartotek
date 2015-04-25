package dtu.client.ui;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

import dtu.client.dal.IPersonDAO;


public class ContentView extends Composite {
	// reference to data layer
	IPersonDAO iPersonDAO;

	VerticalPanel contentPanel;

	public ContentView() {
	}

	public ContentView(IPersonDAO iPersonDAO) {
		this.iPersonDAO = iPersonDAO;
		contentPanel = new VerticalPanel();
		initWidget(this.contentPanel);
	}	



	// Sub views
	public void openWelcomeView() {
		contentPanel.clear();
		WelcomeView welcomeView = new WelcomeView(iPersonDAO);
		contentPanel.add(welcomeView);
	}

	public void openAddView() {
		contentPanel.clear();
		AddView addView = new AddView(iPersonDAO);
		contentPanel.add(addView);

	}

	public void openBrowseView() {
		contentPanel.clear();
		BrowseView browseView = new BrowseView(iPersonDAO);
		contentPanel.add(browseView);
	}

	public void openDeleteView() {
		contentPanel.clear();
		DeleteView deleteView = new DeleteView(iPersonDAO);
		contentPanel.add(deleteView);
	}

	public void openEditView() {
		contentPanel.clear();
		EditView editView = new EditView(iPersonDAO);
		contentPanel.add(editView);
	}


}
