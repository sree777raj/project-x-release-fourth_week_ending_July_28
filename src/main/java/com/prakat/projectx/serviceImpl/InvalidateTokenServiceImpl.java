package com.prakat.projectx.serviceImpl;

import com.prakat.projectx.dto.InvalidateTokenDto;
import com.prakat.projectx.dto.UserDto;

import com.prakat.projectx.exception.CustomException;
import com.prakat.projectx.service.InvalidateTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvalidateTokenServiceImpl implements InvalidateTokenService {
    @Autowired
    private UserServiceImpl userServiceImpl;
@Override
    public UserDto updateSecretCode(InvalidateTokenDto invalidateTokenDto, String newSecretCode) {
        // Fetch the user details by email from the provided InvalidateTokenDto
        UserDto userDto = userServiceImpl.getByEmail(invalidateTokenDto.getEmail());

        // Check if the user exists
        if (userDto == null) {
            throw new CustomException("User not found", 404);
        }

        // Set the new secret code
        userDto.setSecretCode(newSecretCode);

        // Save changes through the user service
        return userServiceImpl.updateUser(userDto);
    }
}