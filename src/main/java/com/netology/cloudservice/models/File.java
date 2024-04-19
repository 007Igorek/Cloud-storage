package com.netology.cloudservice.models;

import com.netology.cloudservice.models.security.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "files", schema = "public")
@Data
@NoArgsConstructor
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "original_filename")
    private String originalName;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "size")
    private Long size;

    @Column(name = "human_size")
    private String humanSize;

    @Column(name = "location")
    private String location;

    @CreatedDate
    @Column(name = "created")
    private Date created;

    @LastModifiedDate
    @Column(name = "updated")
    private Date updated;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @JsonIgnore
    @OneToOne
    private User user;

    public File(String originalName, String name, Long size, String humanSize, String location, Date created, Date updated, Status status, User user) {
        this.originalName = originalName;
        this.name = name;
        this.size = size;
        this.humanSize = humanSize;
        this.location = location;
        this.created = created;
        this.updated = updated;
        this.status = status;
        this.user = user;
    }
}
