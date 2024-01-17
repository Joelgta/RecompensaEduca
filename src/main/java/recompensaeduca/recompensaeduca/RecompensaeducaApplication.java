package recompensaeduca.recompensaeduca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RecompensaeducaApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecompensaeducaApplication.class, args);
	}

}
