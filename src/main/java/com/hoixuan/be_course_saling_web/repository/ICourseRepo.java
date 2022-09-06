package com.hoixuan.be_course_saling_web.repository;

import com.hoixuan.be_course_saling_web.model.Course;
import com.hoixuan.be_course_saling_web.model.MyCourse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ICourseRepo extends PagingAndSortingRepository<Course,Long> {
    Course findByIdCourse(long id);
}
