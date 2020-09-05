/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package clash.back;

import clash.back.controller.GameController;
import clash.back.controller.InstituteController;
import clash.back.controller.NotificationController;
import clash.back.controller.PlayerController;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = {JacksonAutoConfiguration.class})
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    CommandLineRunner initGameController(GameController gameController) {
        return (args -> gameController.init());
    }

    @Bean
    CommandLineRunner initPlayerController(PlayerController playerController) {
        return (args -> playerController.init());
    }

    @Bean
    CommandLineRunner initNotificationController(NotificationController notificationController) {
        return (args -> notificationController.init());
    }

    @Bean
    CommandLineRunner initInstituteController(InstituteController instituteController) {
        return args -> instituteController.init();
    }
}

