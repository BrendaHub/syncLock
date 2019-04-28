package zqit.syncLock.pulgins.redis.syncLock;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * redis同步锁工厂
 * @author mac
 *
 */
@Component
public class SyncLockFactory {

	@Autowired
    RedisTemplate<String, Object> redisTemplate;
	
	private Map<String, SyncLock> syncLockMap = new HashMap<String, SyncLock>();
	
	/**
     * 创建SyncLock
     *
     * @param key Redis key
     * @param expire Redis TTL/秒，默认10秒
     * @param safetyTime 安全时间/秒，为了防止程序异常导致死锁，在此时间后强制拿锁，默认 expire * 5 秒
     */
	public SyncLock build(String key, Long expire, Long safetyTime) {
        if (!syncLockMap.containsKey(key)) {
        	if(expire == null){
        		expire = 10L;
        	}
        	syncLockMap.put(key, new SyncLock(key, redisTemplate, expire, safetyTime==null?5*expire:safetyTime));
        }
        return syncLockMap.get(key);
    }
	
}
