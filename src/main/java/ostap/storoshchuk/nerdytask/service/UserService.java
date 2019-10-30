package ostap.storoshchuk.nerdytask.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ostap.storoshchuk.nerdytask.dto.request.UserRequest;
import ostap.storoshchuk.nerdytask.dto.response.UserResponse;
import ostap.storoshchuk.nerdytask.entity.User;
import ostap.storoshchuk.nerdytask.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void create(UserRequest userRequest) {
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setEmail(userRequest.getEmail());
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public User findByEmail(String email) {
        return userRepository.findByUsername(email);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
