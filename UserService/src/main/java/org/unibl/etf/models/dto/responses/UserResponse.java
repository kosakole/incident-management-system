package org.unibl.etf.models.dto.responses;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.unibl.etf.models.enums.UserRole;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private long id;
    private String firstname;
    private String lastname;
    private String email;
    private UserRole role;
}
