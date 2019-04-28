package zqit.syncLock.pulgins.redis.syncLock.anno;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import zqit.syncLock.pulgins.redis.syncLock.SyncLock;

@Order(3)
@Aspect
@Component
public class SyncLockAspect {

	@Autowired
	RedisTemplate<String, Object> redisTemplate;

	//会启动报错 - 不能在使用@pointCut 内@annotation
//	@Pointcut("@annotation(lock)")
//	public void pointCut(JoinPoint point, SyncLockAble lock) {
//	}

	@Before("@annotation(lock)")
	public void before(JoinPoint point, SyncLockAble lock) {
	}

	@After("@annotation(lock)")
	public void after(JoinPoint point, SyncLockAble lock) {

	}

	@Around("@annotation(lock)")
	public Object around(ProceedingJoinPoint pjp, SyncLockAble lock) throws Throwable {

		String key = lock.key();
		long expire = lock.expire();
		long tryTimeout = lock.tryTimeout();

		SyncLock syncLock = new SyncLock(key, redisTemplate, expire);

		//获取锁
		Boolean tryLock = syncLock.tryLock(tryTimeout);
		
		Object proceed = null;
		if(tryLock == true){
			System.out.println("aspect - 获得同步锁 - key("+key+") - method("+Thread.currentThread().getName()+")");
			// 继续执行
			Object[] args = pjp.getArgs();
			proceed = pjp.proceed(args);
			
			//释放锁
			syncLock.unLock();
		}else{
			System.out.println("aspect - 无法获得同步锁 - key("+key+") - method("+Thread.currentThread().getName()+")");
		}
		
		return proceed;

	}

}
