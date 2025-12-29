package bot.telegram.update;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PhotoSize(
        @JsonProperty("file_id")
        String fileId,
        @JsonProperty("width")
        Integer width,
        @JsonProperty("height")
        Integer height,
        @JsonProperty("file_size")
        Long fileSize
) {
}
