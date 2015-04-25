package dtu.client.controller;

import com.google.gwt.user.client.ui.RootPanel;

import dtu.client.dal.IPersonDAO;
import dtu.client.dal.PersonDAO;
import dtu.client.ui.ContentView;
import dtu.client.ui.MenuView;


public class MainView  {
	
	// reference to ContentView
	private ContentView contentView;
	
	// reference to data layer
	private IPersonDAO iPersonDAO;
	
	public MainView() {
		
		// add implementation of data layer
		iPersonDAO = new PersonDAO();
		
		// wrap menuView
		MenuView m = new MenuView(this);
		RootPanel.get("nav").add(m);
		
		// wrap contentView
		contentView = new ContentView(iPersonDAO);
		RootPanel.get("section").add(contentView);
	
	
	}
	
	public void run() {
		// show welcome panel
		contentView.openWelcomeView();
		
	}
	
	
	// Call back handlers
	public void addPerson() {
		contentView.openAddView();
	}
	
	public void showPersons() {
		contentView.openBrowseView();
	}
	
	public void editPersons() {
		contentView.openEditView();
	}
	
	public void deletePersons() {
		contentView.openDeleteView();
	}
	
}
