package zqit.syncLock.pulgins.redis.syncLock.anno;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 同步锁 
 * @author mac
 * @see
 * {@value key - redis KEY}
 * {@value expire - redis KEY 过期时间 /s}
 * {@value tryTimeout - 获取锁最长等待时间 /s}
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SyncLockAble {
	/**
	 * redis KEY
	 * @return
	 */
	public String key();
	/**
	 * redis KEY 过期时间 /s
	 * @return
	 */
	public long expire() default 10;
	/**
	 * 获取锁最长等待时间 /s
	 * @return
	 */
	public long tryTimeout() default 10;
	
	
}
