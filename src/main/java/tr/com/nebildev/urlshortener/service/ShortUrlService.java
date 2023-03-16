package tr.com.nebildev.urlshortener.service;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import org.springframework.stereotype.Service;
import tr.com.nebildev.urlshortener.entity.ShortUrl;
import tr.com.nebildev.urlshortener.exception.UrlNotFoundException;
import tr.com.nebildev.urlshortener.repository.ShortUrlRepository;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ShortUrlService {
    private final ShortUrlRepository shortUrlRepository;

    private final ShortUrlCacheService shortUrlCacheService;

    public ShortUrlService(ShortUrlRepository shortUrlRepository,
                           ShortUrlCacheService shortUrlCacheService) {
        this.shortUrlRepository = shortUrlRepository;
        this.shortUrlCacheService = shortUrlCacheService;
    }

    private HashCode shortenUrl(String url){
        return Hashing.murmur3_32_fixed(5).hashString(url, Charset.defaultCharset());
    }

    public String createShortUrl(String longUrl){
        final HashCode hashCode = this.shortenUrl(longUrl);
        String shortUrlString = hashCode.toString();
        final Optional<String> urlFromCache = shortUrlCacheService.getUrlFromCache(shortUrlString);
        if(!urlFromCache.isPresent()){
            final Optional<ShortUrl> shortUrlByShortCode = shortUrlRepository.findShortUrlByShortCode(shortUrlString);
            if(!shortUrlByShortCode.isPresent()){
                ShortUrl shortUrl = ShortUrl.builder()
                        .longUrl(longUrl)
                        .shortCode(shortUrlString)
                        .createdAt(LocalDateTime.now())
                        .build();
                shortUrlRepository.save(shortUrl);
            }
            shortUrlCacheService.putUrlToCache(shortUrlString, longUrl);
        }
        return shortUrlString;
    }

    public String getLongUrl(String shortCode){
        final Optional<String> urlFromCache = shortUrlCacheService.getUrlFromCache(shortCode);
        String url = null;
        if(!urlFromCache.isPresent()){
            final Optional<ShortUrl> shortUrlByShortCode = shortUrlRepository.findShortUrlByShortCode(shortCode);
            final ShortUrl shortUrl = shortUrlByShortCode.orElseThrow(() -> new UrlNotFoundException("Url Not found"));
            url = shortUrl.getLongUrl();
        } else {
            url = urlFromCache.get();
        }
        return url;
    }
}
