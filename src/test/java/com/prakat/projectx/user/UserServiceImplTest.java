package com.prakat.projectx.user;

import com.prakat.projectx.entity.User;
import com.prakat.projectx.repo.UserRepository;
import com.prakat.projectx.serviceImpl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceImplTest {

    private UserRepository userRepository;
    private UserServiceImpl userServiceImpl;

    @BeforeEach
    public void setUp() {
        //mocking the Repository
        userRepository = mock(UserRepository.class);
        userServiceImpl = new UserServiceImpl(userRepository);
    }

    @Test
    public void testGetUserById_UserNotExists() {
        // Arrange
        Integer userId = 2;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        User actualUser = userServiceImpl.getUserById(userId);

        assertNull(actualUser);
    }

}
