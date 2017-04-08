package hello;

import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MobileSession {
	
	private UUID uid;
	
	@Bean
	public UUID uidMobileSession(){
		return uid = UUID.randomUUID();
	}
	
	
	public UUID getUid(){
		System.out.println(uid);
		return uid;
	}
}
