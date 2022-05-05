package r8.model;

import org.mindrot.jbcrypt.BCrypt;
import r8.model.dao.AccountDAO;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

public class AuthService {

    /**
     *
     * @param originalPw
     * @return returns a Hashed String
     */
    public static String hashPassword(String originalPw) {
        String hashedPw = BCrypt.hashpw(originalPw, BCrypt.gensalt(12));
        return hashedPw;
    }

    /**
     *
     * @param email
     * @param userInput Password the user is trying to authenticate
     * @return Returns false if a user with the given Email is not found or the given password doesn't match with the password assigned to the email.
     */
    public static boolean authenticatePassword(String email, String userInput) {
        //Käytä loginia db queryyn
        AccountDAO accountDAO = new AccountDAO();
        String hashedpw = accountDAO.getHashedPw(email);
        if(hashedpw == null) return false;
        return BCrypt.checkpw(userInput, hashedpw);
    }
}
