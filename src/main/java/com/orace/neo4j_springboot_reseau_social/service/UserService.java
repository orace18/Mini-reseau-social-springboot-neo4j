package com.orace.neo4j_springboot_reseau_social.service;


import com.orace.neo4j_springboot_reseau_social.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.orace.neo4j_springboot_reseau_social.domain.User;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Enregistrement
    public User registerUser(String username, String email, String password, String name) {
        User user = new User(username,name ,email, password, LocalDate.now().toString());
        return userRepository.save(user);
    }

    // Follow
    @Transactional
    public User follow(Long userId, Long targetId) {
        User user = userRepository.findById(userId).orElseThrow();
        User target = userRepository.findById(targetId).orElseThrow();

        user.getFollowing().add(target);
        return userRepository.save(user);
    }

    // Amitié (bidirectionnelle)
    @Transactional
    public void befriend(Long userId, Long targetId) {
        User user = userRepository.findById(userId).orElseThrow();
        User target = userRepository.findById(targetId).orElseThrow();

        user.getFriends().add(target);
        target.getFriends().add(user);

        userRepository.save(user);
        userRepository.save(target);
    }

    // Suggestions
    public List<User> suggestFriends(Long userId) {
        return userRepository.suggestFriends(userId);
    }
}
