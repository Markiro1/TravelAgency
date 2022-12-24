package org.project.travelagency.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.project.travelagency.model.Order;
import org.project.travelagency.model.Role;

import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDto {

    private  Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Role role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCreateDto userDto = (UserCreateDto) o;
        return email.equals(userDto.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
