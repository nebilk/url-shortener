package tr.com.nebildev.urlshortener.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.com.nebildev.urlshortener.entity.ShortUrl;

import java.util.Optional;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {

    Optional<ShortUrl> findShortUrlByShortCode(String shortCode);
}
