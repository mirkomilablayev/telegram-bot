package bot.telegram.configuration;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class BotClientConfiguration {
    @Bean
    public WebClient telegramWebClient(
            @Value("${telegram.bot.token}") String token
    ) {
        return WebClient.builder()
                .baseUrl("https://api.telegram.org/bot" + token)
                .build();
    }
}
