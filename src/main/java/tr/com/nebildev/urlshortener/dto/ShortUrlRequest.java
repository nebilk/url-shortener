package tr.com.nebildev.urlshortener.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Getter
@Setter
public class ShortUrlRequest {
    @NotNull
    @NotEmpty
    private String longUrl;
}
