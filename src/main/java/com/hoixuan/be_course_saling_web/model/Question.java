package com.hoixuan.be_course_saling_web.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Data
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idQuestion;
    @NotBlank
    private String contentQuestion;
    @NotBlank
    private String A;
    @NotBlank
    private String B;
    @NotBlank
    private String C;
    @NotBlank
    private String D;
    @NotBlank
    private String answer;
}
