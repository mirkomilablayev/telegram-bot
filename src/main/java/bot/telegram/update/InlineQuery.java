package bot.telegram.update;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record InlineQuery(
        @JsonProperty("id")
        String id,
        @JsonProperty("from")
        From from,
        @JsonProperty("query")
        String query,
        @JsonProperty("offset")
        String offset
) {}
