package org.unibl.etf.security.handlers;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.unibl.etf.models.dto.requests.UserOAuth2Request;
import org.unibl.etf.models.dto.responses.UserOAuth2Response;
import org.unibl.etf.services.UserServiceClient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

@Component
@Service
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${app.url.frontend}")
    private String FRONTEND_URL;
    @Value("${app.cookie.expiration}")
    private int COOKIE_EXPIRATION;
    @Value("${app.path.private-key-JWT}")
    private String PRIVATE_KEY_PATH;

    @Autowired
    private final UserServiceClient userServiceClient;

    public OAuth2LoginSuccessHandler(UserServiceClient userServiceClient) {
        this.userServiceClient = userServiceClient;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        OAuth2User googleUser = (OAuth2User) authentication.getPrincipal();
        String email = googleUser.getAttribute("email");
        String googleId = googleUser.getAttribute("sub");
        String firstname = googleUser.getAttribute("given_name");
        String lastname = googleUser.getAttribute("family_name");

        PrivateKey privateKey = null;
        try {
            privateKey = getPrivateKey();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        UserOAuth2Request requestUser = UserOAuth2Request.builder().firstname(firstname).lastname(lastname).email(email).googleId(googleId).build();
        UserOAuth2Response localUser = userServiceClient.getUser(requestUser);

        String token = Jwts.builder()
                .setSubject(email)
                .claim("userId", localUser.getId())
                .claim("roles", localUser.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + COOKIE_EXPIRATION))
                .signWith(privateKey)
                .compact();
        Cookie cookie = new Cookie("IS_MS", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        response.sendRedirect(FRONTEND_URL);
    }

    private PrivateKey getPrivateKey() throws Exception {
        byte[] keyBytes = Files.readAllBytes(Paths.get(getClass().getClassLoader().getResource(PRIVATE_KEY_PATH).toURI()));
        String temp = new String(keyBytes);
        String privateKeyPEM = temp
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");

        byte[] decoded = Base64.getDecoder().decode(privateKeyPEM);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }
}