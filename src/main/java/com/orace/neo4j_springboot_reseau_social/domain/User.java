package com.orace.neo4j_springboot_reseau_social.domain;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

@Node("User")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String username;
    private String name;
    private String email;
    private String password;
    private String createdAt;

    // ---- RELATIONS ----

    /**
     * Utilisateur suit d'autres utilisateurs
     */
    @Relationship(type = "FOLLOWS", direction = Relationship.Direction.OUTGOING)
    private Set<User> following = new HashSet<>();

    /**
     * Utilisateur a des amis (relation bidirectionnelle)
     */

    @Relationship(type = "FRIEND_WITH", direction = Relationship.Direction.OUTGOING)
    private Set<User> friends = new HashSet<>();

    @Relationship(type = "CREATED_BY", direction = Relationship.Direction.INCOMING)
    private Set<Post> posts = new HashSet<>();


    // ---- CONSTRUCTEURS / GETTERS / SETTERS ----

    public User() {}

    public User(String username, String name, String email, String password, String createdAt) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Set<User> getFollowing() {
        return following;
    }

    public void setFollowing(Set<User> following) {
        this.following = following;
    }

    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }
}
