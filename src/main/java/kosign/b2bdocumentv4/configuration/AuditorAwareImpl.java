package kosign.b2bdocumentv4.configuration;

import kosign.b2bdocumentv4.utils.AuthHelper;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String>  {
    @Override
    public Optional<String> getCurrentAuditor() {
        if (AuthHelper.getUser()==null) {
            return Optional.of("system");
        }
        return Optional.of(String.valueOf(AuthHelper.getUserId()));
    }
}
