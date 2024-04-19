package com.netology.cloudservice.models.security;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles", schema = "public")
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Transient
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<User> users;
}
