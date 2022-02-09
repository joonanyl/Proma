package r8.model;

/**
 * 
 * @author sanku
 *
 */


/* direct association w/ */
/* EVENT, TASK */

/* association w/ PROJECT */

public class Account {
	
	/**
	 * Käyttäjätilin haltijan etunimi
	 */
	private String firstName;
	
	/**
	 * Käyttäjätilin haltijan sukunimi
	 */
	private String lastName;
	
	/**
	 * Käyttäjätilin haltijan puhelinnumero merkkijonona
	 */
	private String phoneNumber;
	
	/**
	 * Käyttäjätilin haltijan sähköpostiosoite merkkijonona
	 */
	private String email;
	
	/**
	 * Käyttäjätilin haltijan tilin id
	 */
	private int accountID;	// pitäskö olla static

	
	/**
	 * Constructor
	 * @param fN etunimi
	 * @param lN sukunimi
	 * @param pN puhelinnumero
	 * @param eM sähköposti
	 */
	public Account(String fN, String lN, String pN, String eM) {
		this.firstName = fN;
		this.lastName = lN;
		this.phoneNumber = pN;
		this.email = eM;
	}
	
	
	
	
	
	
	
}
