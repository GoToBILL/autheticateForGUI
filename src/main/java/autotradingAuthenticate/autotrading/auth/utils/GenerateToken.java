package autotradingAuthenticate.autotrading.utils;

import java.util.UUID;

public class GenerateToken {
    public static void main(String[] args) {
        UUID token = UUID.randomUUID();
        System.out.println("Generated Token: " + token.toString());
    }
}