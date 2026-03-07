package org.unibl.etf.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.unibl.etf.models.enums.UserRole;

import java.util.Set;


@Data
@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String email;
    private String firstname;
    private String lastname;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    private String googleId;
}
