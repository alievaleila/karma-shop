package az.edu.itbrains.karma_shop.service;

import az.edu.itbrains.karma_shop.dto.RegisterDto;

public interface UserService {

    boolean registerUser(RegisterDto registerDto);
}
