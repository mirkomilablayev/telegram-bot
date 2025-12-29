package bot.telegram.message.buttons.keyboard;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import bot.telegram.message.buttons.InlineButton;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record InlineKeyboardMarkup(
        @JsonProperty("inline_keyboard")
        List<List<InlineButton>> inlineKeyboard
) {}
