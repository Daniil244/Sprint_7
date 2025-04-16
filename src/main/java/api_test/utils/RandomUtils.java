package api_test.utils;

import java.util.concurrent.ThreadLocalRandom;

public class RandomUtils {
    public static String getRandomString(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = ThreadLocalRandom.current().nextInt(chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }

    public static String getRandomLogin() {
        return "courier_" + getRandomString(8);
    }

    public static String getRandomPassword() {
        return getRandomString(10);
    }

    public static String getRandomFirstName() {
        return "FirstName_" + getRandomString(5);
    }
}
