package az.edu.itbrains.karma_shop.service;

public interface PasswordResetTokenService {

    String createTokenForUser(String username);

    boolean isTokenValid(String username);

    boolean resetPassword(String token, String newPassword);


}
