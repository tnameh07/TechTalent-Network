package project.techTalent.Network.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto 
{
    private int id;
    
    @Size(min=4, message = "Username must be min of 4 characters")
    private String name;
    
    @Email(message = "Email address is not valid!")
    private String email;
    
    @Size(min=3, max=10, message = "Password must be min of 3 chars and max of 10 chars")
    private String password;
    
    private String about;
}
