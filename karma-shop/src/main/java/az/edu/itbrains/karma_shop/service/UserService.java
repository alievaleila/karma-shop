package az.edu.itbrains.karma_shop.service;

import az.edu.itbrains.karma_shop.dto.RegisterDto;
import az.edu.itbrains.karma_shop.model.User;

public interface UserService {

    boolean registerUser(RegisterDto registerDto);

    User findByEmail(String email);
}
