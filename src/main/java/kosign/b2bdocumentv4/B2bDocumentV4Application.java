package kosign.b2bdocumentv4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;

import java.util.TimeZone;

@SpringBootApplication(exclude = {JacksonAutoConfiguration.class})
public class B2bDocumentV4Application {
    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Phnom_Penh"));
        SpringApplication.run(B2bDocumentV4Application.class, args);
    }
}
