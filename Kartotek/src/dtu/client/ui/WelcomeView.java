package dtu.client.ui;


import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import dtu.client.dal.IPersonDAO;

public class WelcomeView extends Composite {

	public WelcomeView(IPersonDAO iPersonDAO) {
		
	VerticalPanel w = new VerticalPanel();
	initWidget(w);
		w.add(new Label("Velkommen til personkartotek"));
		w.add(new Label("Antal personer i kartotek = " + iPersonDAO.getSize()));
	}
	
	public void show() {
		
		
	}
}
