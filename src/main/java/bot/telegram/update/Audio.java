package bot.telegram.update;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public record Audio(
        @JsonProperty("file_id")
        String fileId,
        @JsonProperty("duration")
        Integer duration,
        @JsonProperty("file_name")
        String fileName,
        @JsonProperty("mime_type")
        String mimeType,
        @JsonProperty("file_size")
        Long fileSize
) {}
