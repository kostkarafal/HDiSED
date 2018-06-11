package pl.kostkamatloch.nfcreader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;



@SpringBootApplication
@EnableJpaAuditing
public class NfcReaderApplication {

	public static void main(String[] args) {
		SpringApplication.run(NfcReaderApplication.class, args);
	}
}
