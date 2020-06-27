package com.xiaoshu.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.dao.CourseMapper;
import com.xiaoshu.dao.StudentMapper;
import com.xiaoshu.entity.Student;
import com.xiaoshu.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author shkstart
 * @create 2020-06-23 9:50
 */
@Service
public class StudentService {
    @Autowired(required = false)
    private StudentMapper studentMapper;
    @Autowired(required = false)
    private CourseMapper courseMapper;


    // 查询所有
    public List<Student> getAll() throws Exception {
        return studentMapper.selectAll();
    };



    // 新增
    public void addUser(Student t) throws Exception {
        studentMapper.insert(t);
    };

    // 修改
    public void updateUser(Student t) throws Exception {
        studentMapper.updateByPrimaryKeySelective(t);
    };

    // 删除
    public void deleteUser(Integer id) throws Exception {
        studentMapper.deleteByPrimaryKey(id);
    };


    // 通过用户名判断是否存在，（新增时不能重名）
    public Student existUserWithUserName(String name) throws Exception {
        Example example = new Example(Student.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("name",name);
        List<Student> userList = studentMapper.selectByExample(example);
        return userList.isEmpty()?null:userList.get(0);
    };


    public PageInfo<Student> findUserPage(Student student, String start, String end, int pageNum, int pageSize, String ordername, String order) {

        PageHelper.startPage(pageNum, pageSize);
        ordername = StringUtil.isNotEmpty(ordername)?ordername:"id";
        order = StringUtil.isNotEmpty(order)?order:"desc";
        Example example = new Example(Student.class);
        example.setOrderByClause(ordername+" "+order);
        Example.Criteria criteria = example.createCriteria();

        /*criteria.andLike("name","%"+student.getName()+"%");
        criteria.andEqualTo("grade","%"+student.getGrade()+"%");*/

        if (StringUtil.isNotEmpty(start) && StringUtil.isNotEmpty(end)){
            criteria.andBetween("entrytime", start, end);
        }
        if (StringUtil.isNotEmpty(start) && StringUtil.isEmpty(end)){
            criteria.andGreaterThanOrEqualTo("entrytime",start);
        }
        if (StringUtil.isEmpty(start) && StringUtil.isNotEmpty(end)){
            criteria.andLessThanOrEqualTo("entrytime",end);
        }

        List<Student> userList = studentMapper.selectByExample(example);
        PageInfo<Student> pageInfo = new PageInfo<Student>(userList);
		/*for (Student jiyunStudent : userList) {
			Course course=courseMapper.selectByPrimaryKey(jiyunStudent.getCourseid());

		}*/
        return pageInfo;
    }
}
