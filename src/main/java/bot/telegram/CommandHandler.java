package bot.telegram;

import bot.telegram.update.context.UpdateContext;

public interface CommandHandler {
    String command();

    void handle(UpdateContext updateContext);
}
