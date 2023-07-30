package net.blog.springbootrestapi.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        description = "PostResponse Model Information"
)
public class PostResponse {
    @Schema(
            description = "Blog Post Content"
    )
    private List<PostDto> content;
    @Schema(
            description = "Page number information"
    )
    private int pageNo;
    @Schema(
            description = "Page size information"
    )
    private int pageSize;
    @Schema(
            description = "Total Elements information"
    )
    private long totalElements;
    @Schema(
            description = "Total page information"
    )
    private int totalPages;
    @Schema(
            description = "last page information"
    )
    private boolean last;

}
