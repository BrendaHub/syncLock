package zqit.syncLock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import zqit.syncLock.pulgins.redis.syncLock.SyncLock;
import zqit.syncLock.pulgins.redis.syncLock.SyncLockFactory;

@Controller
@RequestMapping("/syncLock")
public class SyncLockController {
	
	@Autowired
	SyncLockFactory syncLockFactory;
	
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
		Thread.sleep(10000);
		syncLock.unLock();
		
		return "A";
	}
}
