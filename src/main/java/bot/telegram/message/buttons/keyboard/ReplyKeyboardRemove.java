package bot.telegram.message.buttons.keyboard;


import com.fasterxml.jackson.annotation.JsonProperty;

public record ReplyKeyboardRemove(
        @JsonProperty("remove_keyboard")
        boolean removeKeyboard
) {}
