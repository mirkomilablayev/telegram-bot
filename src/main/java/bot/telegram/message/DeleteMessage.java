package bot.telegram.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record DeleteMessage(
        @JsonProperty("chat_id")
        long chatId,

        @JsonProperty("message_id")
        int messageId
) {
}
