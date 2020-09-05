package clash.back.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageConfig {
    @Value("${storage.upload.folder}")
    String path;


    public String getAnswersPath() {
        return path + "/submits";
    }
}
