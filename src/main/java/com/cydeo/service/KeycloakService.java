package com.cydeo.service;

import com.cydeo.dto.UserDTO;

public interface KeycloakService {

    void createUser(UserDTO userDTO);
    void delete(String username);


}
