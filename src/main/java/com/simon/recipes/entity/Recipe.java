package com.simon.recipes.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "recipes")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "recipes")
    Set<Category> categories;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Recipe() {
    }

    public Recipe(String name, String description, User user) {
        this.name = name;
        this.description = description;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", user='" + (user != null ? user.getUsername() : "null") +
                "'" + '}';
    }
}
