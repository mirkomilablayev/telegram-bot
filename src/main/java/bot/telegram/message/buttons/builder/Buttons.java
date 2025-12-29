package bot.telegram.message.buttons.builder;


import bot.telegram.message.buttons.InlineButton;
import bot.telegram.message.buttons.ReplyButton;
import bot.telegram.message.buttons.WebAppInfo;

public final class Buttons {

    private Buttons() {
    }

    public static ReplyButton text(String text) {
        return new ReplyButton(text, null, null, null);
    }

    public static ReplyButton webApp(String text, String url) {
        return new ReplyButton(text, null, null, new WebAppInfo(url));
    }

    public static ReplyButton contact(String text) {
        return new ReplyButton(text, null, true, null);
    }

    public static InlineButton callback(String text, String data) {
        return new InlineButton(text, data, null);
    }

    public static InlineButton url(String text, String url) {
        return new InlineButton(text, null, url);
    }
}
