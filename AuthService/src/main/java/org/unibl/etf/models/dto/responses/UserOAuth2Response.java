package org.unibl.etf.models.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.unibl.etf.models.enums.UserRole;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOAuth2Response {
    private long id;
    private String googleId;
    private UserRole role;
}
