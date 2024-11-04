package com.i2i.smrb.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.i2i.smrb.enums.UserRole;

/**
 * <p>
 * Entity class representing a user.
 * This class maps to the users table in the database and contains information about user
 * such as its ID, email, hashedPassword, projectName and associated role.
 * </p>
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "email", nullable = false, unique = true, length = 40)
    private String email;

    @Column(name = "hashed_password", nullable = false, length = 100)
    private String hashedPassword;

    @Column(name = "role", nullable = false)
    private UserRole role;

    @Column(name = "project_name", nullable = false, length = 60)
    private String projectName;
}
