package helpers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * User: Jasper
 * Class: makes use of the SHA1 encryption through this class (for the passwords of the useraccounts)
 */
public class Utils {

    /**
     * constructor
     */
    public Utils()
    {

    }

    /**
     *
     * SHA1 encryption method
     *
     * @param input
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String sha1(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }
}
