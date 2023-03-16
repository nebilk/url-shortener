package tr.com.nebildev.urlshortener.service;

import org.springframework.stereotype.Service;
import tr.com.nebildev.urlshortener.cache.RedisCache;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class ShortUrlCacheService {
    private final RedisCache redisCache;

    public ShortUrlCacheService(RedisCache redisCache) {
        this.redisCache = redisCache;
    }

    public void putUrlToCache(String code, String longUrl){
        redisCache.putCache(code, longUrl, 24, TimeUnit.HOURS);
    }

    public Optional<String> getUrlFromCache(String code){
        return redisCache.getFromCache(code);
    }
}
