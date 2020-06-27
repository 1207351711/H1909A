package com.xiaoshu.service;

import com.xiaoshu.dao.CourseMapper;
import com.xiaoshu.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shkstart
 * @create 2020-06-23 9:50
 */
@Service
public class CourseService {
    @Autowired(required = false)
    private CourseMapper courseMapper;

    public List<Course> getAll() {
        return courseMapper.selectAll();
    }
}
