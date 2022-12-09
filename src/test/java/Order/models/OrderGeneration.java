package Order.models;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class OrderGeneration {

    private String firstName = getRandomString(10);
    private String lastName =getRandomString(10);
    private String address =getRandomString(10);
    private int metroStation =getRandomInt(1,9);
    private String phone = getRandomPhone();
    private int rentTime = getRandomInt(1,9);
    private String deliveryDate="2020-06-06";
    private String comment=getRandomString(10);


    public CreateOrder newOrder(String[] color) {
        return new CreateOrder(firstName, lastName, address,metroStation,phone,rentTime,deliveryDate,comment, color);
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

    public static int getRandomInt(int min, int max) {
        Random r = new Random();

        return r.nextInt((max - min) + 1) + min;
    }

    public static Long getRandomLong(Long min, Long max) {
        return ThreadLocalRandom.current().nextLong(min, max);
    }

    public static String getRandomPhone() {
        return getRandomLong(11111111111111111L, 99999999999999999L) + "";
    }

}
