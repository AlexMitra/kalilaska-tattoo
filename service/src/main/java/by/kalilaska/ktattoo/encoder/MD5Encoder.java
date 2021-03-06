package by.kalilaska.ktattoo.encoder;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by lovcov on 13.07.2017.
 */
public class MD5Encoder {
	private final static String MD5_ENCODING_TYPE = "MD5";
	
    public static String encode(String source){
        MessageDigest messageDigest = null;
        byte[] digest = new byte[0];

        try {
            messageDigest = MessageDigest.getInstance(MD5_ENCODING_TYPE);
            messageDigest.reset();
            messageDigest.update(source.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        BigInteger bigInt = new BigInteger(1, digest);
        String md5Hex = bigInt.toString(16);

        while( md5Hex.length() < 32 ){
            md5Hex = String.valueOf(0) + md5Hex;
        }

        return md5Hex;
    }
}
