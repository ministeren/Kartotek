package dtu.client.ui;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.VerticalPanel;

import dtu.client.dal.IPersonDAO;
import dtu.client.dal.PersonDTO;

public class DeleteView extends Composite {
	VerticalPanel deletePanel;
	FlexTable t;

	// reference to data layer
	IPersonDAO iPersonDAO;

	// previous cancel anchor
	Anchor previousCancel = null;

	public DeleteView(IPersonDAO iPersonDAO) {
		this.iPersonDAO = iPersonDAO;

		deletePanel = new VerticalPanel();
		initWidget(this.deletePanel);

		t = new FlexTable();
		t.getFlexCellFormatter().setWidth(0, 0, "200px");
		t.getFlexCellFormatter().setWidth(0, 1, "50px");

		t.addStyleName("FlexTable");
		t.getRowFormatter().addStyleName(0,"FlexTable-Header");

		// set headers in flextable
		t.setText(0, 0, "Navn");
		t.setText(0, 1, "alder");

		// fetch persons from data layer
		List<PersonDTO> personer = iPersonDAO.getPersons();

		// populate table and add delete anchor to each row
		for (int rowIndex=0; rowIndex < personer.size(); rowIndex++) {
			t.setText(rowIndex+1, 0, personer.get(rowIndex).getNavn());
			t.setText(rowIndex+1, 1, "" + personer.get(rowIndex).getAlder());
			Anchor delete = new Anchor("delete");
			t.setWidget(rowIndex+1, 2, delete);

			delete.addClickHandler(new DeleteHandler());
		}

		deletePanel.add(t);
	}

	private class DeleteHandler implements ClickHandler {
		public void onClick(ClickEvent event) {
			
			// if previous cancel open - force cancel operation¨
			if (previousCancel != null)
				previousCancel.fireEvent(new ClickEvent(){});


			// get rowindex where event happened
			final int eventRowIndex = t.getCellForEvent(event).getRowIndex();

			// get delete anchor ref for cancel operation
			final Anchor delete =  (Anchor) event.getSource();

			Anchor ok = new Anchor("ok");
			ok.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {

					// remove row in flextable
					t.removeRow(eventRowIndex);

					// here you will normally fetch the primary key of the row 
					// and use it for location the object to be deleted
					iPersonDAO.deletePerson(eventRowIndex-1);
				}

			});

			Anchor cancel = new Anchor("cancel");
			previousCancel = cancel;
			cancel.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					t.setWidget(eventRowIndex, 2, delete);
					t.clearCell(eventRowIndex, 3);
				}

			});

			// showing ok and cancel widgets
			t.setWidget(eventRowIndex, 2 , ok);
			t.setWidget(eventRowIndex, 3 , cancel);
		}
	}
}


