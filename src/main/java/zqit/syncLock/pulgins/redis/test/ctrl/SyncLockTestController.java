package zqit.syncLock.pulgins.redis.test.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import zqit.syncLock.pulgins.redis.syncLock.SyncLock;
import zqit.syncLock.pulgins.redis.syncLock.SyncLockFactory;
import zqit.syncLock.pulgins.redis.test.RedisService;

@Controller
@RequestMapping("/syncLock")
public class SyncLockTestController {
	
	
	@Autowired
	SyncLockFactory syncLockFactory;
	@Autowired
	RedisService redisService;
	
	/**
	 * 同步锁 - java对象
	 * @return
	 * @throws InterruptedException
	 */
	@GetMapping("setALock")
	@ResponseBody
	public String setALock() throws InterruptedException{
		
		SyncLock syncLock = syncLockFactory.build("service-A", 30L, null);
		
		//获得锁
		if(syncLock.tryLock(30L)){
			System.out.println("I get the lock");
		}else{
			System.out.println("I can't get the lock");
		}
		
		//程序运行...
		Thread.sleep(50000);
		
		//释放锁
		syncLock.unLock();
		
		return "A";
	}
	
	/**
	 * 同步锁 - 注解
	 * @return
	 * @throws InterruptedException
	 */
	@GetMapping("setBLock")
	@ResponseBody
	public String setBLock() throws InterruptedException{
		
		redisService.setBLock();
		
		return "B";
	}
}
