package bot.telegram;

import bot.telegram.update.context.UpdateContext;

public interface CallbackHandler {
    boolean supports(String callbackData);

    void handle(UpdateContext ctx);
}
