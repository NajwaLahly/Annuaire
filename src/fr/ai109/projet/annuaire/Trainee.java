package fr.ai109.projet.annuaire;

public class Trainee {
	
	private String lastName;
	private String firstName;
	private String postCode;
	private String promo;
	private int promoNumber;
	private int year;
	private long positionInFileLeft;
	private long positionInFileright;

	public Trainee() {
		
	}



	public Trainee(String lastName, String firstName, String postCode, String promo, int promoNumber, int year) {
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
		return lastName + " " + firstName + " " + postCode + " "
				+ promo + " " + promoNumber + " " + year;
	}

	public Trainee stringToObject(String traineeString) {
		
		String[] tab = traineeString.split(" ");
		Trainee trainee = new Trainee(tab[0], tab[1], tab[2], tab[3], Integer.parseInt(tab[4]), Integer.parseInt(tab[5]));
		return trainee;
		
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
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



	public long getPositionInFileLeft() {
		return positionInFileLeft;
	}



	public void setPositionInFileLeft(long positionInFileLeft) {
		this.positionInFileLeft = positionInFileLeft;
	}



	public long getPositionInFileright() {
		return positionInFileright;
	}



	public void setPositionInFileright(long positionInFileright) {
		this.positionInFileright = positionInFileright;
	}
	

}
