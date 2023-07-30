package net.blog.springbootrestapi.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        description = "SignUpDto Model Information"
)
public class SignUpDto {
    @Schema(
            description = "New User Name"
    )
    private String name;
    @Schema(
            description = "New User Username"
    )
    private String username;
    @Schema(
            description = "New User Email"
    )
    private String email;
    @Schema(
            description = "New User Password"
    )
    private String password;
}
