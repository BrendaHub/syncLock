package zqit.syncLock.pulgins.redis.test.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import zqit.syncLock.pulgins.redis.test.RedisService;

@Controller
@RequestMapping("/redisBase")
public class RedisBaseTestController {

	@Autowired
	RedisService redisService;
	
	/**
	 * redis redisTemplate 测试
	 * @return
	 */
	@ResponseBody
	@GetMapping("/redisTest")
	public String redisTest() {
		//set
		System.out.println(redisService.setRedisCache());
		//get
		System.out.println(redisService.getRedisCache());
		//del
		System.out.println(redisService.delRedisCache());
		//get
		System.out.println(redisService.getRedisCache());
		
		return "redisTest";
	}

}
