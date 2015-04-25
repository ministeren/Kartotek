package dtu.client.ui;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import dtu.client.dal.IPersonDAO;
import dtu.client.dal.PersonDTO;
import dtu.shared.FieldVerifier;


public class EditView extends Composite {
	VerticalPanel editPanel;
	FlexTable t;
	

	// editing text boxes
	TextBox nameTxt;
	TextBox ageTxt;

	// valid fields - initially a field is valid
	boolean nameValid = true;
	boolean ageValid = true;

	int eventRowIndex;

	// reference to data layer
	IPersonDAO iPersonDAO;
	
	// person list
	List<PersonDTO> personer;

	// previous cancel anchor
	Anchor previousCancel = null;

	public EditView(IPersonDAO iPersonDAO) {
		this.iPersonDAO = iPersonDAO;

		editPanel = new VerticalPanel();
		initWidget(this.editPanel);

		t = new FlexTable();

		// adjust column widths
		t.getFlexCellFormatter().setWidth(0, 0, "200px");
		t.getFlexCellFormatter().setWidth(0, 1, "50px");

		// style table
		t.addStyleName("FlexTable");
		t.getRowFormatter().addStyleName(0,"FlexTable-Header");

		// set headers in flextable
		t.setText(0, 0, "Navn");
		t.setText(0, 1, "alder");

		// fetch persons from data layer
		personer = iPersonDAO.getPersons();

		// populate table and add edit anchor to each row
		for (int rowIndex=0; rowIndex < personer.size(); rowIndex++) {
			t.setText(rowIndex+1, 0, personer.get(rowIndex).getNavn());
			t.setText(rowIndex+1, 1, "" + personer.get(rowIndex).getAlder());
			Anchor edit = new Anchor("edit");
			t.setWidget(rowIndex+1, 2, edit);

			edit.addClickHandler(new EditHandler());
		}

		editPanel.add(t);
		
		// text boxes
		nameTxt = new TextBox();
		ageTxt = new TextBox();
		ageTxt.setWidth("20px");
	}

	private class EditHandler implements ClickHandler {
		public void onClick(ClickEvent event) {

			// if previous edit open - force cancel operation¨
			if (previousCancel != null)
				previousCancel.fireEvent(new ClickEvent(){});

			// get rowindex where event happened
			eventRowIndex = t.getCellForEvent(event).getRowIndex();

			// populate textboxes
			nameTxt.setText(t.getText(eventRowIndex, 0));
			ageTxt.setText(t.getText(eventRowIndex, 1));

			// show text boxes for editing
			t.setWidget(eventRowIndex, 0, nameTxt);
			t.setWidget(eventRowIndex, 1, ageTxt);

			// start editing here
			nameTxt.setFocus(true);

			// get edit anchor ref for cancel operation
			final Anchor edit =  (Anchor) event.getSource();

			// get textbox contents for cancel operation
			final String name = nameTxt.getText();
			final String age = ageTxt.getText();


			final Anchor ok = new Anchor("ok");
			ok.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {

					// remove inputboxes
					t.setText(eventRowIndex, 0, nameTxt.getText());
					t.setText(eventRowIndex, 1, ageTxt.getText());


					// here you will normally fetch the primary key of the row 
					// and use it for location the object to be edited

					// fill DTO
					PersonDTO personDTO = new PersonDTO(nameTxt.getText(), Integer.parseInt(ageTxt.getText()));

					// save in backend
					iPersonDAO.updatePerson(personDTO, eventRowIndex-1);		

					// restore edit link
					t.setWidget(eventRowIndex, 2, edit);
					t.clearCell(eventRowIndex, 3);
					
					previousCancel = null;

				}

			});

			Anchor cancel = new Anchor("cancel");
			previousCancel = cancel;
			cancel.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					
					// restore original content of textboxes and rerun input validation
					nameTxt.setText(name);
					nameTxt.fireEvent(new KeyUpEvent() {}); // validation
					
					ageTxt.setText(age);
					ageTxt.fireEvent(new KeyUpEvent() {});  // validation
					
					
					t.setText(eventRowIndex, 0, name);
					t.setText(eventRowIndex, 1, age);
					
					// restore edit link
					t.setWidget(eventRowIndex, 2, edit);
					t.clearCell(eventRowIndex, 3);
					
					previousCancel = null;
				}

			});


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

					// enable/disable ok depending on form status 
					if (nameValid&&ageValid)
						t.setWidget(eventRowIndex, 2, ok);
					else
						t.setText(eventRowIndex, 2, "ok");				
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

					// enable/disable ok depending on form status 
					if (nameValid&&ageValid)
						t.setWidget(eventRowIndex, 2, ok);
					else
						t.setText(eventRowIndex, 2, "ok");
				}

			});

			// showing ok and cancel widgets
			t.setWidget(eventRowIndex, 2 , ok);
			t.setWidget(eventRowIndex, 3 , cancel);		
		}
	}
}
