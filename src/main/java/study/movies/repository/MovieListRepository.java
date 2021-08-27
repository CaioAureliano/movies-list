package study.movies.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import study.movies.domain.MovieList;

import java.util.List;

@Repository
public interface MovieListRepository extends JpaRepository<MovieList, Long> {

    @Query("SELECT CASE WHEN(COUNT(ml) > 0) THEN true ELSE false END FROM MovieList ml WHERE ml.imdbMovieId = :movie_id")
    Boolean existsMovieById(@Param("movie_id") Integer id);

    @Query("SELECT ml.imdbMovieId FROM MovieList ml WHERE ml.id = :list_id")
    List<Integer> findImdbMovieIdByUserListId(@Param("list_id") Long listId);

}
