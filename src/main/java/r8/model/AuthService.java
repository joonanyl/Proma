package r8.model;

import org.mindrot.jbcrypt.BCrypt;
import r8.model.dao.AccountDAO;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

public class AuthService {

    public static String hashPassword(String originalPw) {
        return BCrypt.hashpw(originalPw, BCrypt.gensalt(12));
    }

    public static boolean authenticatePassword(String email, String userInput) {
        AccountDAO accountDAO = new AccountDAO();
        String hashedpw = accountDAO.getHashedPw(email);
        return BCrypt.checkpw(userInput, hashedpw);
    }
}
