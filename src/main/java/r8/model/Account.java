package r8.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author sanku
 *
 */


/* direct association w/ */
/* EVENT, TASK */

/* association w/ PROJECT */
@Entity
@Table(name = "account")
public class Account {

	/**
	 * Käyttäjätilin haltijan tilin id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int account_id;

	// private static int id = 1; // väliaikainen ratkaisu

	/**
	 * Käyttäjätilin haltijan etunimi
	 */
	@Column(name = "first_name")
	private String firstName;
	
	/**
	 * Käyttäjätilin haltijan sukunimi
	 */
	@Column(name = "last_name")
	private String lastName;
	
	/**
	 * Käyttäjätilin haltijan puhelinnumero merkkijonona
	 */
	@Column(name = "phone_number")
	private String phoneNumber;
	
	/**
	 * Käyttäjätilin haltijan sähköpostiosoite merkkijonona
	 */
	@Column(name = "email")
	private String email;

	@ManyToMany(mappedBy = "accounts")
	private Set<Project> projects = new HashSet<Project>();
	
	/**
	 * Constructor
	 * @param fName etunimi
	 * @param lName sukunimi
	 * @param pNumber puhelinnumero
	 * @param email sähköposti
	 */
	public Account(String fName, String lName, String pNumber, String email) {
		this.firstName = fName;
		this.lastName = lName;
		this.phoneNumber = pNumber;
		this.email = email;
	}

	public Account() {}
	
	public int getAccountID() {
		return this.account_id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String toString() {
		return account_id + " " + firstName + " " + lastName;
	}
	
}
