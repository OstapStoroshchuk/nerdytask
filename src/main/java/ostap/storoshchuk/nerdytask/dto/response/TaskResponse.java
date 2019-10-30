package ostap.storoshchuk.nerdytask.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.mapping.Collection;
import ostap.storoshchuk.nerdytask.entity.Task;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class TaskResponse {

    private Integer id;

    private String title;

    private String description;

    private Set<UserResponse> users;

    public TaskResponse(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        users = new HashSet<>(task.getUsers().stream()
                .map(UserResponse::new)
                .collect(Collectors.toSet()));
    }

    @Override
    public String toString() {
        return "TaskResponse{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", users=" + users +
                '}';
    }
}
