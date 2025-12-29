package bot.telegram.update;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record From(
        @JsonProperty("id")
        Long id,
        @JsonProperty("is_bot")
        Boolean isBot,
        @JsonProperty("first_name")
        String firstName,
        @JsonProperty("last_name")
        String lastName,
        @JsonProperty("username")
        String username,
        @JsonProperty("language_code")
        String languageCode,
        @JsonProperty("is_premium")
        Boolean isPremium
) {
}
