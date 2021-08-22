package com.example.demo.module;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "Note")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name ="NAME")
    private String title;

    @Column(name = "DESCRIPTION", columnDefinition="text")
    private String description;

    @ManyToOne
    @JoinColumn(name="USERID", nullable=false, referencedColumnName="ID")
    @JsonIgnoreProperties({"note"})
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Note() {
    }

    public Note(Long id, String title, String description, User user) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", name='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
