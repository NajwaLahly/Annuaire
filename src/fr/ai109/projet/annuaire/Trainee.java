package fr.ai109.projet.annuaire;

public class Trainee {
	
	private String lastName;
	private String firstName;
	private int postCode;
	private String promo;
	private int promoNumber;
	private int year;
	
	public Trainee() {
		
	}

	public Trainee(String lastName, String firstName, int postCode, String promo, int promoNumber, int year) {
		super();
		this.lastName = lastName;
		this.firstName = firstName;
		this.postCode = postCode;
		this.promo = promo;
		this.promoNumber = promoNumber;
		this.year = year;
	}

	@Override
	public String toString() {
		return "Trainee [lastName=" + lastName + ", firstName=" + firstName + ", postCode=" + postCode + ", promo="
				+ promo + ", promoNumber=" + promoNumber + ", year=" + year + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + postCode;
		result = prime * result + ((promo == null) ? 0 : promo.hashCode());
		result = prime * result + promoNumber;
		result = prime * result + year;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Trainee other = (Trainee) obj;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (postCode != other.postCode)
			return false;
		if (promo == null) {
			if (other.promo != null)
				return false;
		} else if (!promo.equals(other.promo))
			return false;
		if (promoNumber != other.promoNumber)
			return false;
		if (year != other.year)
			return false;
		return true;
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

	public int getPostCode() {
		return postCode;
	}

	public void setPostCode(int postCode) {
		this.postCode = postCode;
	}

	public String getPromo() {
		return promo;
	}

	public void setPromo(String promo) {
		this.promo = promo;
	}

	public int getPromoNumber() {
		return promoNumber;
	}

	public void setPromoNumber(int promoNumber) {
		this.promoNumber = promoNumber;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
}
