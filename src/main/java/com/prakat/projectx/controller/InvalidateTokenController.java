package com.prakat.projectx.controller;

import com.prakat.projectx.dto.InvalidateTokenDto;
import com.prakat.projectx.serviceImpl.InvalidateTokenServiceImpl;
import com.prakat.projectx.serviceImpl.UserServiceImpl;
import com.prakat.projectx.utils.Util;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class to handle token invalidation requests.
 * This controller exposes an endpoint to invalidate the current user's token
 * by updating their secret code.
 */
@RestController
@RequestMapping("/api/")
public class InvalidateTokenController {

    /**
     * The service responsible for handling token invalidation logic.
     */
    private final InvalidateTokenServiceImpl invalidateTokenServiceImpl;

    /**
     * Constructs a new InvalidateTokenController with the specified InvalidateTokenServiceImpl.
     *
     * @param invalidateTokenServiceImpl The service responsible for handling token invalidation.
     */
    public InvalidateTokenController(InvalidateTokenServiceImpl invalidateTokenServiceImpl) {
        this.invalidateTokenServiceImpl = invalidateTokenServiceImpl;
    }

    /**
     * Endpoint to invalidate the current user's token by updating their secret code.
     *
     * <p>
     * The user's email is retrieved from the Util class, representing the logged-in user.
     * A new secret code is generated using the UserServiceImpl.generateSecretCode() method.
     * The secret code is then updated for the user using the InvalidateTokenServiceImpl.
     * </p>
     *
     * @return ResponseEntity with a success message if the token is invalidated successfully.
     */
    @GetMapping("invalidateToken")
    public ResponseEntity<String> invalidateToken() {
        // Retrieve the logged-in user's email
        String loggedInUserEmail = Util.getLoggedInUserEmail();

        // Generate a new secret code for the user
        String newSecretCode = UserServiceImpl.generateSecretCode();

        // Create an InvalidateTokenDto with the user's email and update the secret code
        InvalidateTokenDto invalidateTokenDto = new InvalidateTokenDto();
        invalidateTokenDto.setEmail(loggedInUserEmail);
        invalidateTokenServiceImpl.updateSecretCode(invalidateTokenDto, newSecretCode);

        // Return a ResponseEntity with a success message
        return ResponseEntity.ok("Token invalidated successfully");
    }
}

