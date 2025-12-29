package bot.telegram.message.buttons;

import com.fasterxml.jackson.annotation.JsonProperty;

public record WebAppInfo(
        @JsonProperty("url")
        String url
) {}