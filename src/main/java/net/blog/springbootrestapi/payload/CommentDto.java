package net.blog.springbootrestapi.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        description = "CommentDto Model Information"
)
public class CommentDto {
    private long id;
    @Schema(
            description = "Blog Comment Name"
    )
    @NotEmpty(message = "Name should not be empty")
    private String name;
    @Schema(
            description = "Blog Comment Email"
    )
    @NotEmpty(message = "email should not be empty")
    @Email(message = "email should contain '@'and '.'")
    private String email;
    @Schema(
            description = "Blog Comment Body"
    )
    @NotEmpty(message = "body should not be empty")
    @Size(min = 10,message = "body should contain atleast 10 character")
    private String body;

}
