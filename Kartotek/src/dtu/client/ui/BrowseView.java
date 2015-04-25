package dtu.client.ui;

import java.util.List;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.VerticalPanel;

import dtu.client.dal.IPersonDAO;
import dtu.client.dal.PersonDTO;

public class BrowseView extends Composite {
	VerticalPanel browsePanel;
	
	// reference to data layer
	IPersonDAO iPersonDAO;

	public BrowseView(IPersonDAO iPersonDAO) {
		this.iPersonDAO = iPersonDAO;

		browsePanel = new VerticalPanel();
		initWidget(this.browsePanel);

		FlexTable t = new FlexTable();
		t.getFlexCellFormatter().setWidth(0, 0, "200px");
		t.getFlexCellFormatter().setWidth(0, 1, "50px");
		
		t.addStyleName("FlexTable");
		t.getRowFormatter().addStyleName(0,"FlexTable-Header");

		t.setText(0, 0, "Navn");
		t.setText(0, 1, "alder");

		List<PersonDTO> personer = iPersonDAO.getPersons();

		for (int i=0; i < personer.size(); i++) {
			t.setText(i+1, 0, personer.get(i).getNavn());
			t.setText(i+1, 1, "" + personer.get(i).getAlder());
		}

		browsePanel.add(t);
	}
}
