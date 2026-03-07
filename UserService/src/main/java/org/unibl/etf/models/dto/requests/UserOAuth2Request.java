package org.unibl.etf.models.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.unibl.etf.models.enums.UserRole;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOAuth2Request {
    private String firstname;
    private String lastname;
    private String email;
    private String googleId;
}
