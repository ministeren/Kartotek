package dtu.client.dal;

import java.util.ArrayList;
import java.util.List;

public class PersonDAO implements IPersonDAO {

	private List<PersonDTO> pList;

	public PersonDAO() {
		pList = new ArrayList<PersonDTO>();
		
		// Indset start data
		pList.add(new PersonDTO("Hans Jensen",23));
		pList.add(new PersonDTO("Ulla Jacobsen",25));
		pList.add(new PersonDTO("Peter Hansen",25));
	}

	@Override
	public void savePerson(PersonDTO p) {
		pList.add(p);
	}

	@Override
	public void updatePerson(PersonDTO p, int index) {
		pList.set(index, p);
	}
	
	
	@Override
	public List<PersonDTO> getPersons() {

		return pList;
	}

	@Override
	public int getSize() {
		return pList.size();
	}

	@Override
	public void deletePerson(int index) {
		pList.remove(index);
	}

	

}
