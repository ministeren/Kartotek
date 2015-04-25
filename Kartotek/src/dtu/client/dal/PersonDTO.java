package dtu.client.dal;

public class PersonDTO {
	private String navn;
	private int alder;
	
	public PersonDTO(String navn, int alder) {
		super();
		this.navn = navn;
		this.alder = alder;
	}
	public String getNavn() {
		return navn;
	}
	public void setNavn(String navn) {
		this.navn = navn;
	}
	public int getAlder() {
		return alder;
	}
	public void setAlder(int alder) {
		this.alder = alder;
	}
}
