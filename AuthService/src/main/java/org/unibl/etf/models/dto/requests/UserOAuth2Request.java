package org.unibl.etf.models.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserOAuth2Request {
    private String googleId;
    private String firstname;
    private String lastname;
    private String email;
}
