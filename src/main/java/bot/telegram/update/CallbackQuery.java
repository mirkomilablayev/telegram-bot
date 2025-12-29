package bot.telegram.update;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CallbackQuery(
        @JsonProperty("id")
        String id,
        @JsonProperty("from")
        From from,
        @JsonProperty("message")
        Message message,
        @JsonProperty("data")
        String data
) {
    public boolean hasFrom() {
        return this.from != null;
    }

    public boolean hasMessage() {
        return this.message != null;
    }

    public boolean hasData() {
        return this.data != null;
    }
}
