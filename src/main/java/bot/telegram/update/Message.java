package bot.telegram.update;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Message(
        @JsonProperty("message_id")
        long messageId,
        @JsonProperty("from")
        From from,
        @JsonProperty("chat")
        Chat chat,
        @JsonProperty("voice")
        Voice voice,
        @JsonProperty("audio")
        Audio audio,
        @JsonProperty("document")
        Document document,
        @JsonProperty("photo")
        List<PhotoSize> photo,
        @JsonProperty("contact")
        Contact contact,
        @JsonProperty("text")
        String text
) {
    public boolean hasText() {
        return text != null;
    }

    public boolean hasContact() {
        return contact != null;
    }

    public boolean hasPhoto() {
        return photo != null;
    }

    public boolean hasDocument() {
        return document != null;
    }

    public boolean hasAudio() {
        return audio != null;
    }

    public boolean hasVoice() {
        return voice != null;
    }

    public boolean hasChat() {
        return chat != null;
    }

    public boolean hasFrom() {
        return from != null;
    }

}
