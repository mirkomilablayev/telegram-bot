package bot.telegram.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record SendPhoto(
        @JsonProperty("chat_id")
        long chatId,
        @JsonProperty("photo")
        String photo,
        @JsonProperty("caption")
        String caption,
        @JsonProperty("parse_mode")
        String parseMode,
        @JsonProperty("reply_markup")
        Object replyMarkup
) {}
