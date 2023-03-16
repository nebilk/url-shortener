package tr.com.nebildev.urlshortener.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tr.com.nebildev.urlshortener.dto.ShortUrlRequest;
import tr.com.nebildev.urlshortener.service.ShortUrlService;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/shorty")
@Slf4j
public class ShortUrlController {

    private final ShortUrlService shortUrlService;

    public ShortUrlController(ShortUrlService shortUrlService) {
        this.shortUrlService = shortUrlService;
    }

    @PostMapping
    public ResponseEntity<String> shortenUrl(@Validated @RequestBody ShortUrlRequest shortUrlRequest){
        final URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
        return ResponseEntity.created(uri).body(shortUrlService.createShortUrl(shortUrlRequest.getLongUrl()));
    }

    @GetMapping("/{code}")
    public ResponseEntity<String> resolveUrl(@PathVariable String code){
        final String longUrl = shortUrlService.getLongUrl(code);
        log.info(longUrl);
        return ResponseEntity
                .status(HttpStatus.MOVED_PERMANENTLY)
                .location(URI.create(longUrl))
                .header(HttpHeaders.CONNECTION, "close")
                .build();
    }
}
