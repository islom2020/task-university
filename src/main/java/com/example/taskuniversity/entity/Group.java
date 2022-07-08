package com.example.taskuniversity.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToOne(optional = false)
    private Faculty faculty;

    @OneToOne(mappedBy = "group",fetch = FetchType.LAZY)
    private Journal journal;

    @OneToMany(mappedBy = "group")
    private List<Student> students;

}
