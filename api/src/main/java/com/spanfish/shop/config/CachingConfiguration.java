package com.spanfish.shop.config;

import static com.github.benmanes.caffeine.cache.Caffeine.newBuilder;
import static com.github.benmanes.caffeine.cache.Ticker.systemTicker;
import static java.util.Arrays.asList;
import static java.util.concurrent.Executors.newCachedThreadPool;
import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.MINUTES;

import com.github.benmanes.caffeine.cache.Ticker;
import java.util.concurrent.TimeUnit;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;

public class CachingConfiguration {

  @Bean
  public CacheManager cacheManager(final Ticker ticker) {
    final CaffeineCache productCache = buildCache("product", ticker, 5, MINUTES);
    final CaffeineCache categoryCache = buildCache("category", ticker, 1, HOURS);
    final CaffeineCache subCategoryCache = buildCache("subCategory", ticker, 1, HOURS);
    final CaffeineCache manufacturerCache = buildCache("manufacturer", ticker, 1, HOURS);

    final SimpleCacheManager manager = new SimpleCacheManager();

    manager.setCaches(asList(productCache, categoryCache, subCategoryCache, manufacturerCache));

    return manager;
  }

  private CaffeineCache buildCache(
      final String name, final Ticker ticker, final long duration, final TimeUnit unit) {
    return new CaffeineCache(
        name,
        newBuilder()
            .expireAfterWrite(duration, unit)
            .maximumSize(500_000)
            .executor(newCachedThreadPool())
            .ticker(ticker)
            .build());
  }

  @Bean
  public Ticker ticker() {
    return systemTicker();
  }

  @Bean("customKeyGenerator")
  public KeyGenerator keyGenerator() {
    return (target, method, params) ->
        target.getClass().getSimpleName()
            + "_"
            + method.getName()
            + "_"
            + StringUtils.arrayToDelimitedString(params, "_");
  }
}
