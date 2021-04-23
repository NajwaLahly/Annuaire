package fr.ai109.projet.annuaire;

public class Trainee {
	
	private String lastName;
	private String firstName;
	private String postCode;
	private String promo;
	private int year;

	
	public Trainee() {
		
	}


	public Trainee(String lastName, String firstName, String postCode, String promo, int year) {
		super();
		this.lastName = lastName;
		this.firstName = firstName;
		this.postCode = postCode;
		this.promo = promo;
		this.year = year;
	}

	@Override
	public String toString() {
		return lastName + "\t" + firstName + "\t" + postCode + "\t"
				+ promo + "\t" + year;
	}

	public Trainee stringToObject(String traineeString) {
		
		String[] tab = traineeString.split("\t");
		Trainee trainee = new Trainee(tab[0], tab[1], tab[2], tab[3], Integer.parseInt(tab[4]));
		return trainee;
		
	}

	

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getPromo() {
		return promo;
	}

	public void setPromo(String promo) {
		this.promo = promo;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}


}
