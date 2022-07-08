package com.example.taskuniversity.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class
Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false)
    private String name;

    @ManyToOne(optional = false)
    private Group group;

    @OneToMany(mappedBy = "student")
    private List<Mark> markList;
}


