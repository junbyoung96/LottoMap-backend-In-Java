package devcourse.assignment.lottomap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LottomapApplication {

	public static void main(String[] args) {
		SpringApplication.run(LottomapApplication.class, args);
	}

}
