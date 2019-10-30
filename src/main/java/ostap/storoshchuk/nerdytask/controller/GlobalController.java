package ostap.storoshchuk.nerdytask.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ostap.storoshchuk.nerdytask.entity.User;
import ostap.storoshchuk.nerdytask.service.UserService;


@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class GlobalController {

    @Autowired
    private UserService userService;
    private User loginUser;

    public User getLoginUser() {
        if (loginUser == null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            loginUser = userService.findByUsername(auth.getName());
        //    System.out.print( auth.getName());
        }
     //   System.out.println(loginUser);

        return loginUser;
    }
}
