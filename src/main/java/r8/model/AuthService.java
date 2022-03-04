package r8.model;

import org.mindrot.jbcrypt.BCrypt;
import r8.model.dao.AccountDAO;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

public class AuthService {

    public static String hashPassword(String originalPw) {
        String hashedPw = BCrypt.hashpw(originalPw, BCrypt.gensalt(12));
        return hashedPw;
    }

    public static boolean authenticatePassword(String login, String userInput, String hashedpw) {
        //Käytä loginia db queryyn
        AccountDAO accountDAO = new AccountDAO();
        accountDAO.getLoginInfo(login);
        boolean pwCheck = BCrypt.checkpw(userInput, hashedpw);
        return pwCheck;
    }
}
