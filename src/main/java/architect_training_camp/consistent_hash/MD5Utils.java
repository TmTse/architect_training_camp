package architect_training_camp.consistent_hash;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 生成MD5
 */
class MD5Utils {
    private static final String[] hexDigIts = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};


    public static String encode(String origin) {
        return encode(origin, "UTF-8");

    }

    public static String encode(String origin, String charset) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (null == charset || "".equals(charset)) {
                return byteArrayToHexString(md.digest(origin.getBytes()));
            } else {
                return byteArrayToHexString(md.digest(origin.getBytes(charset)));
            }
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return origin;
    }


    private static String byteArrayToHexString(byte[] b) {
        StringBuilder resultSb = new StringBuilder();
        for (byte tmp : b) {
            resultSb.append(byteToHexString(tmp));
        }
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigIts[d1] + hexDigIts[d2];
    }

}
