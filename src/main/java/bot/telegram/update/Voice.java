package bot.telegram.update;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Voice(
        @JsonProperty("file_id")
        String fileId,
        @JsonProperty("duration")
        Long duration,
        @JsonProperty("mime_type")
        String mimeType,
        @JsonProperty("file_size")
        Long fileSize
) {
}
