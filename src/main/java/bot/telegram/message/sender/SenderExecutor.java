package bot.telegram.message.sender;


import bot.telegram.message.DeleteMessage;
import bot.telegram.message.SendMessage;
import bot.telegram.message.SendPhoto;
import bot.telegram.message.SendVideo;

public interface SenderExecutor {

    void sendMessage(SendMessage message);

    void sendPhoto(SendPhoto photo);

    void sendVideo(SendVideo video);

    void removeKeyboard(long chatId, String text);

    void deleteMessage(long chatId, int messageId);
}
