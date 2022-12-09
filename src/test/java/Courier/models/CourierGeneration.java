package Courier.models;

import java.util.Random;

public class CourierGeneration {

    private String login = getRandomString(10);
    private String password = getRandomString(10);
    private String firstName = getRandomString(10);

    public CreateCourier newCourier() {
        return new CreateCourier(login, password, firstName);
    }

    public static String getRandomString(int length) {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder result = new StringBuilder();
        Random rnd = new Random();
        while (result.length() < length) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            result.append(SALTCHARS.charAt(index));
        }
        return result.toString();
    }
}
