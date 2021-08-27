package study.movies.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class ImdbMovieResponseDeserialize extends JsonDeserializer<ImdbMovieResponseDTO> {
    @Override
    public ImdbMovieResponseDTO deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws JsonProcessingException, IOException {
        JsonNode node = jsonParser.readValueAsTree();

        return ImdbMovieResponseDTO.builder()
                .id(node.get("id").asInt())
                .title(node.get("original_title").asText())
                .overview(node.get("overview").asText())
                .backdropPath(node.get("backdrop_path").asText())
                .build();
    }
}
