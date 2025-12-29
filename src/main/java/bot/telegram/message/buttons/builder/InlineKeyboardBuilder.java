package bot.telegram.message.buttons.builder;


import bot.telegram.message.buttons.InlineButton;
import bot.telegram.message.buttons.keyboard.InlineKeyboardMarkup;

import java.util.ArrayList;
import java.util.List;

public class InlineKeyboardBuilder {

    private final List<List<InlineButton>> rows = new ArrayList<>();

    public InlineKeyboardBuilder row(InlineButton... buttons) {
        rows.add(List.of(buttons));
        return this;
    }

    public InlineKeyboardMarkup build() {
        return new InlineKeyboardMarkup(rows);
    }
}
