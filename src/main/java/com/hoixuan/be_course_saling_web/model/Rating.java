package com.hoixuan.be_course_saling_web.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idRating;
    private String contentRating;
    private int numStar;
    private boolean statusRating;
    @ManyToOne
    private AppUser appUser;
    @ManyToOne
    private Course course;
}