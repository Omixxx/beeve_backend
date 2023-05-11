package it.unimol.vino.controller;


import it.unimol.vino.controllers.UserController;


import it.unimol.vino.services.UserService;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserPermissionController {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

}

