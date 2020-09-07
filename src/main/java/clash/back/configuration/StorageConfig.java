package clash.back.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageConfig {
    @Value("${storage.upload.folder}")
    String answersPath;

    @Value("${storage.challenges.folder}")
    String challengesPath;


    public String getAnswersPath() {
        return answersPath + "/submits";
    }

    public String getChallengesPath() {
        return challengesPath;
    }
}
