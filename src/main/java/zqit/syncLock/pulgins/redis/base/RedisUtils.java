package zqit.syncLock.pulgins.redis.base;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * redis操作模版工具类
 * @author mac
 *
 */
@Component
public class RedisUtils {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    /**
     * 根据指定o获取Object
     * @param o
     * @return
     */
    public Object getObj(String o){
    	return redisTemplate.opsForValue().get(o);
    }

    /**
     * 设置obj缓存
     * @param o1
     * @param o2
     */
    public void setObj(String o1, Object o2){
    	redisTemplate.opsForValue().set(o1, o2);
    }

    /**
     * 删除Obj缓存
     * @param o
     */
    public void delObj(String o){
    	redisTemplate.delete(o);
    }

    /**
     * 设置Obj的有效时间(s)
     * @param o
     * @param timeout
     */
    public void expireObje(String o, Long timeout){
    	redisTemplate.expire(o, timeout, TimeUnit.SECONDS);
    }
}