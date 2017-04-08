package hello;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class GreetingController {
	@Autowired
	private MobileSession mobileSession;
	
	@MessageMapping("/hello")
	@SendTo("/topic/greetings")
	public Greeting greeting(GyroscopeData message) throws Exception {
		
		String context = "Alpha " + message.getAlpha() + " Beta " + message.getBeta() + " Gamma "+ message.getGamma();
		System.out.println(context);
	    return new Greeting(context);
	    
	}
	
	@MessageMapping("/code")
	@SendTo("/topic/greetings")
	public void introduceCode(String code) throws Exception {
		System.out.println(code);
		System.out.println(mobileSession.getUid().toString());
	}
	
	
	
	@PostMapping("/code")
	void uid() {
		
		
	}
	
	
	
	@Autowired
	private RedisTemplate<String,String> template;
    // inject the template as ListOperations
    // can also inject as Value, Set, ZSet, and HashOperations
    @Resource(name="redisTemplate")
    private ListOperations<String, String> listOps;
    public void saveCode(String userId, String url) {
    	listOps.leftPush(userId, url);
        String salio = listOps.leftPop(userId);
    }
}
