package hello;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
public class User {
	private MobileSession session;
	
	public MobileSession getSession(){
		return session;
	}
	
	public void createSession(){
		session = new MobileSession();
	}
	
	
}
