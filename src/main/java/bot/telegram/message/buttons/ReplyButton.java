package bot.telegram.message.buttons;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonInclude(JsonInclude.Include.NON_NULL)
public record ReplyButton(
        @JsonProperty("text")
        String text,
        @JsonProperty("request_contact")
        Boolean requestContact,
        @JsonProperty("request_location")
        Boolean requestLocation,
        @JsonProperty("web_app")
        WebAppInfo webApp
) {
}

