package com.hoixuan.be_course_saling_web.controller;

import com.hoixuan.be_course_saling_web.model.*;
import com.hoixuan.be_course_saling_web.model.dto.LessonLearned;
import com.hoixuan.be_course_saling_web.service.AppUserService;
import com.hoixuan.be_course_saling_web.service.CourseService;
import com.hoixuan.be_course_saling_web.service.MyCourseService;
import com.hoixuan.be_course_saling_web.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin("*")
@RequestMapping("course")
public class MyCourseAPI {
    @Autowired
    AppUserService appUserService;

    @Autowired
    MyCourseService myCourseService;

    @Autowired
    CourseService courseService;
    @Autowired
    WalletService walletService;


    @GetMapping("/myCourse")
    public ResponseEntity<List<MyCourse>> getAllMyCourseByUser() {
        List<MyCourse> myCourses = myCourseService.findAllMyCourseByIdUser(myCourseService.findIdUser());
        return new ResponseEntity<>(myCourses, HttpStatus.ACCEPTED);
    }

    @GetMapping("/myCourseLearn/{idCourse}")
    public ResponseEntity<MyCourse> getMyCourseLearn(@PathVariable long idCourse){
        return new ResponseEntity<>(myCourseService.findMyCourseLearn(idCourse),HttpStatus.OK);
    }

    @PostMapping("/learned")
    public ResponseEntity<MyCourse> lessonLearned (@RequestBody LessonLearned lessonLearned){
        myCourseService.learned(lessonLearned.getIdMyCourse(),lessonLearned.getIdLesson());
        MyCourse myCourse = myCourseService.findMyCourseLearn(lessonLearned.getIdMyCourse());
        return new ResponseEntity<>(myCourse,HttpStatus.OK);
    }

    @GetMapping("/buyCourse/{idCourse}")
    public ResponseEntity<MyCourse> buyCourse(@PathVariable long idCourse){
        //        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Wallet wallet = walletService.findByIdUser(appUserService.findByUserName("conganh").getIdUser());
        Course course = courseService.findById(idCourse);
        if(wallet.getMoney() >= course.getPriceCourse()){
            wallet.setMoney(wallet.getMoney()-course.getPriceCourse());
            walletService.save(wallet);
            MyCourse myCourse = new MyCourse();
            AppUser appUser = appUserService.findByUserName("conganh");
            myCourse.setCourse(course);
            myCourse.setAppUser(appUser);
            myCourse.setStatusMyCourse(true);
            Set<Lesson> lessonlist = new HashSet<>();
            myCourse.setLessonList(lessonlist);
            myCourse.setCompletionProgress(0);
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH,course.getTimeCourse());
            Date date=new Date(cal.getTimeInMillis());
            myCourse.setExpire(date);
            return new ResponseEntity<>(myCourseService.save(myCourse),HttpStatus.OK);
        } else return new ResponseEntity(new MyCourse(), HttpStatus.BAD_REQUEST);
    }
}