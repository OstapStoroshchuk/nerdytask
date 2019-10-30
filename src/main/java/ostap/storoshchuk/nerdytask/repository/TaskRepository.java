package ostap.storoshchuk.nerdytask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ostap.storoshchuk.nerdytask.entity.Task;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    @Query("select t from Task t join t.users u where u.username =:username")
    List<Task> findAllByUsersUsername(@Param("username") String username);

    Task findOneById(Integer id);

    void deleteById(Integer id);
}
