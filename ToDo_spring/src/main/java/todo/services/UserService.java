package todo.services;

import org.springframework.stereotype.Service;
import todo.data.UserRepository;
import todo.entity.User;

@Service
public class UserService {

    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public User getByUsername(String username){
        return userRepo.findByUsername(username);
    }

}
