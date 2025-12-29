package bot.telegram.model;

import bot.telegram.constants.Language;

public interface BotUser<S extends Enum<S>> {
    String getFirstname();

    String getLastname();

    Long getId();

    Long getChatId();

    Language getLanguage();

    String getPhoneNumber();

    S getState();
}
