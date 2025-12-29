package bot.telegram.update;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Update(
        @JsonProperty("update_id")
        long updateId,
        @JsonProperty("message")
        Message message,
        @JsonProperty("callback_query")
        CallbackQuery callbackQuery
) {
    public boolean hasMessage(){
        return message != null;
    }

    public boolean hasCallBackQuery(){
        return callbackQuery != null;
    }
}
