package zqit.syncLock.pulgins.redis.syncLock;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;

/**
 * 同步锁
 *
 * @property key Redis key
 * @property stringRedisTemplate RedisTemplate
 * @property expire Redis TTL/秒
 * @property safetyTime 安全时间/秒
 * @property waitMillisPer 获取锁失败时，下次获取的等待阻塞时间 /ms
 */
public class SyncLock {

	private String key;
	private RedisTemplate<String, Object> redisTemplate;
	private Long expire;
	private Long safetyTime;
	private Long waitMillisPer;

	public SyncLock(String key, RedisTemplate<String, Object> redisTemplate, Long expire, Long safetyTime) {
		this.key = key;
		this.redisTemplate = redisTemplate;
		this.expire = expire;
		this.safetyTime = safetyTime;
		this.waitMillisPer = 200L;
	}


	/**
	 * 尝试获取锁，并至多等待timeout时长
	 *
	 * @param timeout 超时时长(s)
	 *
	 * @return 是否获取成功
	 * @throws InterruptedException 
	 */
	public Boolean tryLock(Long timeout) throws InterruptedException {
	    Long waitMax = TimeUnit.SECONDS.toMillis(timeout);
	    Long waitAlready = 0L;

	    //当前线程name
	    String value = Thread.currentThread().getName();
	    
	    //setIfAbsent函数(对应SETNX命令)
	    while (redisTemplate.opsForValue().setIfAbsent(key, value) != true && waitAlready < waitMax) {
	        Thread.sleep(waitMillisPer);
	        waitAlready += waitMillisPer;
	        System.out.println("锁被占有，等待阻塞中  - 当前线程: " + value);
	    }

	    //设置有效时间
	    if (waitAlready < waitMax) {
	    	redisTemplate.expire(key, expire, TimeUnit.SECONDS);
	        return true;
	    }
	    return false;
	}
	
	/**
	 * 获取锁 - 超过保护时间，则强制获取
	 *
	 * @param unit 时间单位
	 *
	 * @return 是否获取成功
	 * @throws InterruptedException 
	 */
	public Boolean lock() throws InterruptedException {
	    Long waitMax = TimeUnit.SECONDS.toMillis(safetyTime);
	    Long waitAlready = 0L;
	    
	    //当前线程name
	    String value = Thread.currentThread().getName();

	    //setIfAbsent函数(对应SETNX命令)
	    while (redisTemplate.opsForValue().setIfAbsent(key, value) != true && waitAlready < waitMax) {
	        Thread.sleep(waitMillisPer);
	        waitAlready += waitMillisPer;
	        System.out.println("锁被占有，等待阻塞中  - 当前线程: " + value);
	    }

	    //设置有效时间
	    redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        return true;
	}
	
	/**
	 * 释放锁
	 */
	public void unLock(){
		Object object = redisTemplate.opsForValue().get(key);
		
		//当前线程name
	    String value = Thread.currentThread().getName();
	    
		if(object != null){
			//只能释放 当前线程所持有的锁
			if(value.equals(object.toString())){
				redisTemplate.delete(key);
			}
	    }
	}
	
}
