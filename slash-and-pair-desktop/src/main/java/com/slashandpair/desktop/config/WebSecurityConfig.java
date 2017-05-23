package com.slashandpair.desktop.config;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by guillermoblascojimenez on 08/04/17.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//	  @Bean
//	  public EmbeddedServletContainerFactory servletContainer() {
//	    TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory() {
//	        @Override
//	        protected void postProcessContext(Context context) {
//	          SecurityConstraint securityConstraint = new SecurityConstraint();
//	          securityConstraint.setUserConstraint("CONFIDENTIAL");
//	          SecurityCollection collection = new SecurityCollection();
//	          collection.addPattern("/*");
//	          securityConstraint.addCollection(collection);
//	          context.addConstraint(securityConstraint);
//	        }
//	      };
//	    
//	    tomcat.addAdditionalTomcatConnectors(initiateHttpConnector());
//	    return tomcat;
//	  }
//	  
//	  private Connector initiateHttpConnector() {
//	    Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
//	    connector.setScheme("http");
//	    connector.setPort(8080);
//	    connector.setSecure(false);
//	    connector.setRedirectPort(8443);
//	    
//	    return connector;
//	  }
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http	.csrf().disable()
        		.authorizeRequests()
                .antMatchers("/**")
                .permitAll();

    }

}


