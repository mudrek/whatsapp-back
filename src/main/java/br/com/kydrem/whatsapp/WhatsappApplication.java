package br.com.kydrem.whatsapp;

import br.com.kydrem.whatsapp.core.authentication.config.RsaKeyConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(RsaKeyConfigProperties.class)
@SpringBootApplication
public class WhatsappApplication {

	public static void main(String[] args) {
		SpringApplication.run(WhatsappApplication.class, args);
	}

}
