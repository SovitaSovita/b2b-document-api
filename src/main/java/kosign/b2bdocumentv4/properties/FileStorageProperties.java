package kosign.b2bdocumentv4.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "files")
@Data
public class FileStorageProperties {
    private String uploadDir;
    private String serverPath;
}
