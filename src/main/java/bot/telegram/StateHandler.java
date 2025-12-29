package bot.telegram;

import bot.telegram.update.context.UpdateContext;

public interface StateHandler<S extends Enum<S>> {

    S state();

    boolean supports(UpdateContext ctx);

    void handle(UpdateContext ctx);
}
