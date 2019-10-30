package ostap.storoshchuk.nerdytask.dto.response;

import lombok.Getter;
import lombok.Setter;
import ostap.storoshchuk.nerdytask.entity.User;

@Setter
@Getter
public class UserResponse {

    private Integer id;

    private String email;

    private String username;

  //  private String password;

    public UserResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
    //    this.password = user.getPassword();
        this.username = user.getUsername();
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
          //      ", password='" + password + '\'' +
                '}';
    }
}
