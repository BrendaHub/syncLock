package zqit.syncLock.pulgins.redis.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import zqit.syncLock.pulgins.redis.RedisUtils;

@Service
public class RedisService {
	
	@Autowired
	RedisUtils redisUtil;
	//set
	public String setRedisCache() {
		try {
			redisUtil.setObj("testString", "testA");
			return "setRedisCache success";
		}catch (Exception e) {
			e.printStackTrace();
			return "setRedisCache fail";
		}
	}
	//get
	public String getRedisCache() {
		try {
			Object obj = redisUtil.getObj("testString");
			if(obj != null) {
				System.out.println("testString : " + obj.toString());
			}else {
				System.out.println("testString : null");
			}
			
			return "getRedisCache success";
		}catch (Exception e) {
			e.printStackTrace();
			return "getRedisCache fail";
		}
	}
	//del
	public String delRedisCache() {
		try {
			redisUtil.delObj("testString");
			return "delRedisCache success";
		}catch (Exception e) {
			e.printStackTrace();
			return "delRedisCache fail";
		}
	}
	
	//@CacheEvict 清除缓存
	@CacheEvict(value="ZQIT_Redis", key="'users_'+#id")
    public void redisCacheTestB(Integer id) {
        // 删除user
        System.out.println("user删除");
    }
	
	//@Cacheable 设置缓存
	@Cacheable(value="ZQIT_Redis", key="'users_'+#id")
	public String redisCacheTestA(Integer id) {
		//缓存存在，则不会执行方法内容 - 故不会打印
		System.out.println("I'm in");
		return "redisTestSelect";
	}
	
	
}
