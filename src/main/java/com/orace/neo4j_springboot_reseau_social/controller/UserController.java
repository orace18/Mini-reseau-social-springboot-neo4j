package com.orace.neo4j_springboot_reseau_social.controller;

import com.orace.neo4j_springboot_reseau_social.domain.User;
import com.orace.neo4j_springboot_reseau_social.exeception.ApiResponse;
import com.orace.neo4j_springboot_reseau_social.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Créer un utilisateur
    @PostMapping
    public ResponseEntity<ApiResponse<User>> createUser(@RequestBody User user) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Utilisateur créé", userRepository.save(user)));
    }

    // Follow
    @PostMapping("/{userId}/follow/{targetId}")
    public ResponseEntity<ApiResponse<User>> follow(@PathVariable Long userId, @PathVariable Long targetId) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<User> targetOpt = userRepository.findById(targetId);

        if (userOpt.isEmpty() || targetOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Utilisateur introuvable", null));
        }

        User user = userOpt.get();
        User target = targetOpt.get();
        user.getFollowing().add(target);
        userRepository.save(user);

        return ResponseEntity.ok(new ApiResponse<>(true, "Follow effectué", user));
    }

    // Friendship
    @PostMapping("/{userId}/befriend/{targetId}")
    public ResponseEntity<ApiResponse<User>> befriend(@PathVariable Long userId, @PathVariable Long targetId) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<User> targetOpt = userRepository.findById(targetId);

        if (userOpt.isEmpty() || targetOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Utilisateur introuvable", null));
        }

        User user = userOpt.get();
        User target = targetOpt.get();

        user.getFriends().add(target);
        target.getFriends().add(user);

        userRepository.save(user);
        userRepository.save(target);

        return ResponseEntity.ok(new ApiResponse<>(true, "Amitié créée", user));
    }


    @GetMapping("/{userId}/suggestions")
    public ResponseEntity<ApiResponse<Set<User>>> suggestFriends(@PathVariable Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);

        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Utilisateur introuvable", null));
        }

        User user = userOpt.get();
        Set<User> suggestions = new HashSet<>();
        for (User friend : user.getFriends()) {
            for (User fof : friend.getFriends()) {
                if (!fof.equals(user) && !user.getFriends().contains(fof)) {
                    suggestions.add(fof);
                }
            }
        }

        return ResponseEntity.ok(new ApiResponse<>(true, "Suggestions récupérées", suggestions));
    }
}
