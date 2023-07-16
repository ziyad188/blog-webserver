package net.blog.springbootrestapi.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto {
    private long id;
    @NotEmpty(message = "Name should not be empty")
    private String name;
    @NotEmpty(message = "email should not be empty")
    private String email;
    @NotEmpty(message = "body should not be empty")
    @Size(min = 10,message = "body should contain atleast 10 character")
    private String body;

}
