package dtu.client.dal;

import java.util.List;

public interface IPersonDAO {
	public void savePerson(PersonDTO p);
	public void updatePerson(PersonDTO p, int index);
	public List<PersonDTO> getPersons();
	public void deletePerson(int index);
	public int getSize();
}
