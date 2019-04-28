package zqit.syncLock.pulgins.redis.cache;

import java.util.Arrays;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * redis缓存配置类
 */
@Order(2)
@Configuration
@EnableCaching
public class RedisCacheConfig extends CachingConfigurerSupport {

	final String redisName = "ZQIT_Redis";

	/**
	 * 定义redis缓存
	 * @param redisTemplate
	 * @return
	 */
	@Bean
	public CacheManager cacheManager(RedisTemplate<?, ?> redisTemplate) {
		RedisCacheManager rcm = new RedisCacheManager(redisTemplate);
		// 多个缓存的名称,目前只定义了一个
		rcm.setCacheNames(Arrays.asList(redisName));
		// 设置缓存过期时间(秒)
		rcm.setDefaultExpiration(600);
		return rcm;
	}

}