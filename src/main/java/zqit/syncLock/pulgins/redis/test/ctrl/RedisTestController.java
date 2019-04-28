package zqit.syncLock.pulgins.redis.test.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import zqit.syncLock.pulgins.redis.test.RedisService;

@Controller
@RequestMapping("/redisTest")
public class RedisTestController {

	@Autowired
	RedisService redisService;
	
	// redis redisTemplate 测试
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

	// 缓存测试 - 如果缓存存在，则不执行方法内容
	@ResponseBody
	@GetMapping("/redisTestSelect")
	public String RedisCacheTest(Integer id) {
		redisService.redisCacheTestA(id);
		return "redisTestSelect";
	}

	// 缓存测试 - 方法执行完后，清除缓存
	@GetMapping("/redisTestDEL")
	@ResponseBody
	public void delUser(Integer id) {
		// 删除user
		redisService.redisCacheTestB(id);
		System.out.println("user删除");
	}
}
