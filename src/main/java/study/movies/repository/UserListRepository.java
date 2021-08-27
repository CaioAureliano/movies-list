package study.movies.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.movies.domain.User;
import study.movies.domain.UserList;

import java.util.stream.DoubleStream;

@Repository
public interface UserListRepository extends JpaRepository<UserList, Long> {
    Page<UserList> findAllByUser(User user, Pageable pageable);

    boolean existsByNameAndUser(String listName, User user);
}
