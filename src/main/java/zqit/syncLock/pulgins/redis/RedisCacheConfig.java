package zqit.syncLock.pulgins.redis;

import java.util.Arrays;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * redis配置类
 * @author hulonghai 
 */
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

	/**
	 * 初始化 redis模版
	 * @param redisConnectionFactory
	 * @return
	 */
	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory)
	{
	        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
	        ObjectMapper om = new ObjectMapper();
	        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
	        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
	        jackson2JsonRedisSerializer.setObjectMapper(om);
	        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
	        template.setConnectionFactory(redisConnectionFactory);
	        template.setKeySerializer(jackson2JsonRedisSerializer);
	        template.setValueSerializer(jackson2JsonRedisSerializer);
	        template.setHashKeySerializer(jackson2JsonRedisSerializer);
	        template.setHashValueSerializer(jackson2JsonRedisSerializer);
	        template.afterPropertiesSet();
	        return template;
	}

}