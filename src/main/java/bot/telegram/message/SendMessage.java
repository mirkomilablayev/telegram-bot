package bot.telegram.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record SendMessage(
        @JsonProperty("chat_id")
        long chatId,
        @JsonProperty("text")
        String text,
        @JsonProperty("parse_mode")
        String parseMode,
        @JsonProperty("reply_markup")
        Object replyMarkup
) {}
