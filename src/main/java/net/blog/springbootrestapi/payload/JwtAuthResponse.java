package net.blog.springbootrestapi.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        description = "JwtAuthResponse Model Information"
)
public class JwtAuthResponse {
    @Schema(
            description = "Contains Access Token"
    )
    private String accessToken;
    @Schema(
            description = "Information about token type"
    )
    private String tokenType = "Bearer";

}
