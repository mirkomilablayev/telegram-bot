package bot.telegram;


import bot.telegram.update.Update;
import bot.telegram.update.context.UpdateContext;

public interface UpdatePipeline {
    void handle(Update update);
}
