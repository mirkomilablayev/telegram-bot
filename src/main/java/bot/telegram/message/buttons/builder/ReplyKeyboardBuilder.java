package bot.telegram.message.buttons.builder;


import bot.telegram.message.buttons.ReplyButton;
import bot.telegram.message.buttons.keyboard.ReplyKeyboardMarkup;

import java.util.ArrayList;
import java.util.List;

public class ReplyKeyboardBuilder {

    private final List<List<ReplyButton>> rows = new ArrayList<>();

    public ReplyKeyboardBuilder row(ReplyButton... buttons) {
        rows.add(List.of(buttons));
        return this;
    }

    public ReplyKeyboardMarkup build() {
        return new ReplyKeyboardMarkup(rows, true, false);
    }
}
