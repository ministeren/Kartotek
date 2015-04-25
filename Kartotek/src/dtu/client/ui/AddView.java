package dtu.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import dtu.client.dal.IPersonDAO;
import dtu.client.dal.PersonDTO;
import dtu.shared.FieldVerifier;

public class AddView extends Composite {
	VerticalPanel addPanel;

	// reference to data layer
	IPersonDAO iPersonDAO;

	// controls
	Label nameLbl;
	Label ageLbl;
	TextBox nameTxt;
	TextBox ageTxt;
	Button save = new Button("Tilf\u00F8j");

	// valid fields
	boolean ageValid = false;
	boolean nameValid = false;

	public AddView(final IPersonDAO iPersonDAO) {
		this.iPersonDAO = iPersonDAO;
		addPanel = new VerticalPanel();

		// total height of widget. Components are distributed evenly
		addPanel.setHeight("120px");	
		initWidget(this.addPanel);


		HorizontalPanel namePanel = new HorizontalPanel();
		HorizontalPanel agePanel = new HorizontalPanel();

		nameLbl = new Label("Navn:");
		nameLbl.setWidth("60px");
		nameTxt = new TextBox();
		nameTxt.setHeight("1em");
		namePanel.add(nameLbl);
		namePanel.add(nameTxt);


		Label alderLbl = new Label("Alder:");
		alderLbl.setWidth("60px");
		ageTxt = new TextBox();
		ageTxt.setWidth("5em");
		ageTxt.setHeight("1em");
		agePanel.add(alderLbl);
		agePanel.add(ageTxt);

		// use unicode escape sequence \u00F8 for '�'
		save = new Button("Tilf\u00F8j");
		save.setEnabled(false);

		save.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				iPersonDAO.savePerson(new PersonDTO(nameTxt.getText(), Integer.parseInt(ageTxt.getText())));
				Window.alert("Person gemt i kartotek");
			}
		});


		// register event handlers
		nameTxt.addKeyUpHandler(new KeyUpHandler(){

			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (!FieldVerifier.isValidName(nameTxt.getText())) {
					nameTxt.setStyleName("gwt-TextBox-invalidEntry");
					nameValid = false;
				}
				else {
					nameTxt.removeStyleName("gwt-TextBox-invalidEntry");
					nameValid = true;
				}

				checkFormValid();
			}

		});

		ageTxt.addKeyUpHandler(new KeyUpHandler(){

			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (!FieldVerifier.isValidAge(ageTxt.getText())) {
					ageTxt.setStyleName("gwt-TextBox-invalidEntry");
					ageValid = false;
				}
				else {
					ageTxt.removeStyleName("gwt-TextBox-invalidEntry");
					ageValid = true;
				}
				checkFormValid();
			}

		});

		addPanel.add(namePanel);
		addPanel.add(agePanel);
		addPanel.add(save);


	}

	private void checkFormValid() {
		if (ageValid && nameValid)
			save.setEnabled(true);
		else
			save.setEnabled(false);

	}

}
