package bot.telegram.message.sender;

import bot.telegram.message.DeleteMessage;
import bot.telegram.message.SendMessage;
import bot.telegram.message.SendPhoto;
import bot.telegram.message.SendVideo;
import bot.telegram.message.buttons.keyboard.ReplyKeyboardRemove;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.reactive.function.client.WebClient;

public class SenderExecutorImpl implements SenderExecutor {

    private final WebClient client;

    public SenderExecutorImpl(WebClient telegramWebClient) {
        this.client = telegramWebClient;
    }

    @Override
    @Async("senderExecutor")
    public void sendMessage(SendMessage message) {
        post("/sendMessage", message);
    }

    @Override
    @Async("senderExecutor")
    public void sendPhoto(SendPhoto photo) {
        post("/sendPhoto", photo);
    }

    @Override
    @Async("senderExecutor")
    public void sendVideo(SendVideo video) {
        post("/sendVideo", video);
    }

    @Override
    @Async("senderExecutor")
    public void removeKeyboard(long chatId, String text) {
        SendMessage msg = new SendMessage(
                chatId,
                text,
                null,
                new ReplyKeyboardRemove(true)
        );
        post("/sendMessage", msg);
    }

    @Override
    @Async("senderExecutor")
    public void deleteMessage(long chatId, int messageId) {
        DeleteMessage msg = new DeleteMessage(chatId, messageId);
        post("/deleteMessage", msg);
    }

    private void post(String path, Object payload) {
        client.post()
                .uri(path)
                .bodyValue(payload)
                .retrieve()
                .onStatus(
                        status -> status.value() == 429,
                        response -> response.bodyToMono(String.class)
                                .map(body -> new IllegalStateException("Telegram rate limit: " + body))
                )
                .bodyToMono(Void.class)
                .doOnError(this::logError)
                .subscribe();
    }

    private void logError(Throwable e) {
        // log only, never throw
        System.err.println("Telegram send error: " + e.getMessage());
    }
}
