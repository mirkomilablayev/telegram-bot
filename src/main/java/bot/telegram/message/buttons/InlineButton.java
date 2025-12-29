package bot.telegram.message.buttons;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record InlineButton(
        @JsonProperty("text")
        String text,
        @JsonProperty("callback_data")
        String callbackData,
        @JsonProperty("url")
        String url
) {}
