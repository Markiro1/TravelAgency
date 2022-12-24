package org.project.travelagency.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Pattern(regexp = "[A-Z][a-z]+", message = "Must start with a capital letter")
    @Column(name = "firstname", nullable = false)
    private String firstName;

    @Pattern(regexp = "[A-Z][a-z]+", message = "Must start with a capital letter")
    @Column(name = "lastname", nullable = false)
    private String lastName;

    @Email(message = "Invalid email")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    //@Column(name = "account_non_expired")
    @Transient
    private boolean accountNonExpired;

    //@Column(name = "account_non_locked")
    @Transient
    private boolean accountNonLocked;

    //@Column(name = "credentials_non_expired")
    @Transient
    private boolean credentialsNonExpired;

    //@Column(name = "enabled")
    @Transient
    private boolean enabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(role);
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}