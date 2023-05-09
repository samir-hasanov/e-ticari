package electronic.commerce.dto.request;

import lombok.Data;

@Data
public class AddRoleToUser {

    private String email;
    private String role;
}
