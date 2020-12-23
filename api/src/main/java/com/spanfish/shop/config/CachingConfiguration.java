package com.spanfish.shop.config;

import com.github.benmanes.caffeine.cache.Ticker;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;

import static com.github.benmanes.caffeine.cache.Caffeine.newBuilder;
import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.MINUTES;

public class CachingConfiguration {

  @Bean
  public CacheManager cacheManager(Ticker ticker) {
    CaffeineCache productCache = buildCache("product", ticker, 10, MINUTES);
    CaffeineCache categoryCache = buildCache("category", ticker, 1, HOURS);
    CaffeineCache subCategoryCache = buildCache("subCategory", ticker, 1, HOURS);
    CaffeineCache manufacturerCache = buildCache("manufacturer", ticker, 1, HOURS);

    SimpleCacheManager manager = new SimpleCacheManager();

    manager.setCaches(
        Arrays.asList(productCache, categoryCache, subCategoryCache, manufacturerCache));

    return manager;
  }

  private CaffeineCache buildCache(String name, Ticker ticker, long duration, TimeUnit unit) {
    return new CaffeineCache(
        name,
        newBuilder()
            .expireAfterWrite(duration, unit)
            .maximumSize(500_000)
            .executor(Executors.newCachedThreadPool())
            .ticker(ticker)
            .build());
  }

  @Bean
  public Ticker ticker() {
    return Ticker.systemTicker();
  }
}
