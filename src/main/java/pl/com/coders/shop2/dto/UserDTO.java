package pl.com.coders.shop2.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class UserDTO {
    @Email(message = "Invalid email.")
    private String email;

    @NotBlank(message = "Cannot be blank.")
    private String firstName;

    @NotBlank(message = "Cannot be blank.")
    private String lastName;

    @NotBlank(message = "Cannot be blank.")
    private String password;
}
