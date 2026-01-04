package bot.telegram.update.context;

import bot.telegram.model.BotUser;
import bot.telegram.update.CallbackQuery;
import bot.telegram.update.Message;
import bot.telegram.update.Update;

public class UpdateContext<U extends BotUser<?>> {
    private final Long userId;
    private final Message message;
    private final CallbackQuery callback;
    private final InputType inputType;
    private U user;

    private UpdateContext(Long userId,
                          Message message,
                          CallbackQuery callback,
                          InputType inputType) {
        this.userId = userId;
        this.message = message;
        this.callback = callback;
        this.inputType = inputType;
    }

    public static <U extends BotUser<?>> UpdateContext<U> fromUpdate(Update update) {
        if (update.hasMessage() && update.message().hasFrom()) {
            Message m = update.message();
            InputType type = m.hasVoice() ? InputType.VOICE : m.hasText() && m.text().startsWith("/") ? InputType.COMMAND : InputType.TEXT;
            return new UpdateContext<>(m.from().id(), m, null, type);
        }

        if (update.hasCallBackQuery() && update.callbackQuery().hasFrom()) {
            return new UpdateContext<>(
                    update.callbackQuery().from().id(),
                    null,
                    update.callbackQuery(),
                    InputType.CALLBACK
            );
        }

        return new UpdateContext<>(null, null, null, InputType.UNKNOWN);
    }

    public Long getUserId() {
        return this.userId;
    }

    public Message getMessage() {
        return this.message;
    }

    public CallbackQuery getCallback() {
        return this.callback;
    }

    public InputType getInputType() {
        return this.inputType;
    }

    public U getUser() {return this.user;}

    public void setUser(U user){
        this.user = user;
    }
}
