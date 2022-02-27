package uz.pdp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.model.Role;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto {
    private UUID id ;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;
    private String bio;
    private List<CourseDto> courses;
    private UUID[] role;
    private List<Role> roles;
    private String image;
}
