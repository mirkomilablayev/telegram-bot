package bot.telegram.message.buttons.keyboard;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import bot.telegram.message.buttons.ReplyButton;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ReplyKeyboardMarkup(
        @JsonProperty
        List<List<ReplyButton>> keyboard,
        @JsonProperty
        Boolean resize_keyboard,
        @JsonProperty("")
        Boolean one_time_keyboard
) {}
