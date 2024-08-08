package com.activity.Activity.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "Activities")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "activity_id")
    private Long activityId;

    @Column(name = "name")
    private String name;

    @Column(name="description")
    private String description;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "activity_students",
            joinColumns = @JoinColumn(name = "activity_id",referencedColumnName = "activity_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id",referencedColumnName = "user_id"))
    @JsonIgnore
    private List<User> assignedStudents;

}
