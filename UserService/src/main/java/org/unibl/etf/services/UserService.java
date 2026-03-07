package org.unibl.etf.services;

import org.unibl.etf.models.dto.requests.UserOAuth2Request;
import org.unibl.etf.models.dto.responses.UserOAuth2Response;
import org.unibl.etf.models.dto.responses.UserResponse;
import org.unibl.etf.models.enums.UserRole;

import java.util.List;

public interface UserService {
    UserOAuth2Response getUserOAuth2(UserOAuth2Request request);
    UserOAuth2Response getUserOAuth2ByGoogleId(String id);
    List<UserResponse> getAll();
    UserResponse changeRole(Long id, UserRole role);
    UserResponse changeRole(Long id);
}
