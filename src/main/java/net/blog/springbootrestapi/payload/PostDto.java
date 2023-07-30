package net.blog.springbootrestapi.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;


import java.util.Set;

@Data
@Schema(
        description = "PostDto Model Information"
)
public class PostDto {
    private Long id;
    //title should not empty and at least two charachter
    @Schema(
            description = "Blog Post Title"
    )
    @NotEmpty
    @Size(min = 2,message = "Post title should have atleast 2 character")
    private String title;
    @Schema(
            description = "Blog Post Description"
    )
    @NotEmpty
    @Size(min = 10,message = "Post description should have atleast 10 character")
    private String description;
    @Schema(
            description = "Blog Post Content"
    )
    @NotEmpty
    private String content;
    @Schema(
            description = "Blog Post Comments"
    )
    private Set<CommentDto> comments;
    @Schema(
            description = "Blog Post Category"
    )
    private Long categoryId;
}
