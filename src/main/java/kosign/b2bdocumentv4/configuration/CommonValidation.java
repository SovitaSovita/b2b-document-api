package kosign.b2bdocumentv4.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.regex.Pattern;

@Configuration
public class CommonValidation {
    public boolean isNumeric(String str) {
        return Pattern.compile("\\d+").matcher(str).matches();
    }
}
