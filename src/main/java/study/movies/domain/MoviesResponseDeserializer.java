package study.movies.domain;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import study.movies.dto.MoviesResponseDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MoviesResponseDeserializer extends JsonDeserializer<MoviesResponseDTO> {

    @Override
    public MoviesResponseDTO deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String imagePath = "https://image.tmdb.org/t/p/original";

        JsonNode node = jsonParser.readValueAsTree();

        Integer page = node.get("page").asInt();
        Integer totalPages = node.get("total_pages").asInt();
        Integer totalResults = node.get("total_results").asInt();

        JsonNode results = node.get("results");
        List<Movie> movies = new ArrayList<>();
        for (int counterResult = 0; counterResult < results.size(); counterResult++) {
            Movie movie = new Movie();
            movie.setId(results.get(counterResult).get("id").asLong());
            movie.setTitle(results.get(counterResult).get("original_title").asText());
            movie.setOverview(results.get(counterResult).get("overview").asText());
            movie.setBackdropPath(imagePath + results.get(counterResult).get("backdrop_path").asText());

            movies.add(movie);
        }

        return MoviesResponseDTO.builder()
                .page(page)
                .totalPages(totalPages)
                .totalResults(totalResults)
                .movies(movies)
                .build();
    }
}
