package com.prakat.projectx.service;

import com.prakat.projectx.dto.InvalidateTokenDto;
import com.prakat.projectx.dto.UserDto;

public interface InvalidateTokenService {
    UserDto updateSecretCode(InvalidateTokenDto invalidateTokenDto, String newSecretCode);
}
