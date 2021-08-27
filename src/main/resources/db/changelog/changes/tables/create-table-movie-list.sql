CREATE TABLE movie_list(
    id BIGINT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    imdb_movie_id BIGINT(11) NOT NULL,
    user_list_id BIGINT(11) NOT NULL,
    FOREIGN KEY (user_list_id) REFERENCES user_list(id)
)