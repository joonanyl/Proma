package r8.model;

/**
 * Singleton, joka selvittää sovelluksen tilan ja alustaa käyttäjäkohtaisen näkymän
 * 
 * @author sanku
 *
 */


/* direct association w/ */
/* ACCOUNT */

	/*
	sannan kommentti idea idestä
	 */

/*
 * saisko tää tyylii accountin vastaan? en jtnki hahmota just nyt
 */

public class AppState {
	
/**
 * Singletonin instanssi
 */
	private static AppState INSTANCE = null;
	
/**
 * Private constructor
*/
	private AppState() {
		
	}
	
/**
 * Singletonin geInstance()-metodi
 * @return Singletonin ainoan instanssin
 */	
	public static AppState getInstance() {
		if(INSTANCE == null)
			INSTANCE = new AppState();
		
		return INSTANCE;
	}
}
