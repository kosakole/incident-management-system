package org.unibl.etf.services;

import org.unibl.etf.models.dto.requests.UserOAuth2Request;
import org.unibl.etf.models.dto.responses.UserOAuth2Response;
import reactor.core.publisher.Mono;

public interface UserServiceClient {

    UserOAuth2Response getUser(UserOAuth2Request request);
}
