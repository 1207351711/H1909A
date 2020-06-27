package com.xiaoshu.entity;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;

/**
 * @author shkstart
 * @create 2020-06-23 9:47
 */
@Repository
public class Student {
    @Id
    private Integer id;
    private Integer courseid;
    private String name;
    private String age;
    private String code;
    private String grade;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date entrytime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date craetetime;
    @Transient
    private Course course;

    public Student(Integer id, Integer courseid, String name, String age, String code, String grade, Date entrytime, Date craetetime) {
        this.id = id;
        this.courseid = courseid;
        this.name = name;
        this.age = age;
        this.code = code;
        this.grade = grade;
        this.entrytime = entrytime;
        this.craetetime = craetetime;
    }

    public Student() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCourseid() {
        return courseid;
    }

    public void setCourseid(Integer courseid) {
        this.courseid = courseid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Date getEntrytime() {
        return entrytime;
    }

    public void setEntrytime(Date entrytime) {
        this.entrytime = entrytime;
    }

    public Date getCraetetime() {
        return craetetime;
    }

    public void setCraetetime(Date craetetime) {
        this.craetetime = craetetime;
    }


    @Override
    public String toString() {
        return "Jiyun_Student{" +
                "id=" + id +
                ", courseid=" + courseid +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", code='" + code + '\'' +
                ", grade='" + grade + '\'' +
                ", entrytime=" + entrytime +
                ", craetetime=" + craetetime +
                ", course=" + course +
                '}';
    }
}
