package org.unibl.etf.services.implementation;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.unibl.etf.models.dto.requests.UserOAuth2Request;
import org.unibl.etf.models.dto.responses.UserOAuth2Response;
import org.unibl.etf.services.UserServiceClient;

@Service
public class UserServiceClientImpl implements UserServiceClient {


    private final WebClient webClient;

    public UserServiceClientImpl(WebClient.Builder webClient, @Value("${app.url.users-oauth2}") String url) {
        this.webClient = webClient.baseUrl(url).build();
    }

    public UserOAuth2Response getUser(UserOAuth2Request request) {
        return webClient.post()
                .bodyValue(request)
                .retrieve()
                .bodyToMono(UserOAuth2Response.class)
                .block();
    }
}
