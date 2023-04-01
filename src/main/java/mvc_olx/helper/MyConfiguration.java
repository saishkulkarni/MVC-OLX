package mvc_olx.helper;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("mvc_olx")
public class MyConfiguration {

	@Bean
	public EntityManager getEM() {
		return Persistence.createEntityManagerFactory("dev").createEntityManager();
	}
}
