package hello;

import java.util.UUID;

import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloRestController {

	private static final Logger logger =
			LoggerFactory.getLogger(HelloRestController.class);
	
	@GetMapping("/")
	Greeting uid(HttpSession session) {
		UUID uid = (UUID) session.getAttribute("uid");
		if (uid == null) {
			uid = UUID.randomUUID();
		}
		session.setAttribute("uid", uid);
		logger.debug("--------------------" + ((UUID) session.getAttribute("uid")).toString());
		return new Greeting("holii");
	}

}
