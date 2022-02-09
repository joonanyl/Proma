package r8.model;

/**
 * Singleton, joka selvittää sovelluksen tilan ja alustaa käyttäjäkohtaisen näkymän
 * 
 * @author sanku
 *
 */


/* direct association w/ */
/* ACCOUNT */

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
