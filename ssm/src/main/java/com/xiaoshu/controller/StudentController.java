package com.xiaoshu.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.config.util.ConfigUtil;
import com.xiaoshu.entity.Course;
import com.xiaoshu.entity.Operation;
import com.xiaoshu.entity.Role;
import com.xiaoshu.entity.Student;
import com.xiaoshu.service.CourseService;
import com.xiaoshu.service.OperationService;
import com.xiaoshu.service.RoleService;
import com.xiaoshu.service.StudentService;
import com.xiaoshu.util.StringUtil;
import com.xiaoshu.util.WriterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @author shkstart
 * @create 2020-06-23 9:49
 */
@Controller
@RequestMapping("student")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private CourseService courseService;

    @Autowired
    private RoleService roleService ;

    @Autowired
    private OperationService operationService;


    @RequestMapping("studentIndex")
    public String index(HttpServletRequest request,Integer menuid) throws Exception{
        List<Role> roleList = roleService.findRole(new Role());
        List<Operation> operationList = operationService.findOperationIdsByMenuid(menuid);
        request.setAttribute("operationList", operationList);
        request.setAttribute("roleList", roleList);
        List<Course> courses=courseService.getAll();
        request.setAttribute("courses",courses);
        return "student";
    }


    @RequestMapping(value="studentList",method= RequestMethod.POST)
    public void userList(HttpServletRequest request,HttpServletResponse response,String offset,String limit) throws Exception{
        try {
            Student student = new Student();
            String name = request.getParameter("name");
            String grade = request.getParameter("grade");
            String start = request.getParameter("start");
            String end = request.getParameter("end");
            String order = request.getParameter("order");
            String ordername = request.getParameter("ordername");
            if (StringUtil.isNotEmpty(name)) {
                student.setName(name);
            }
            if (StringUtil.isNotEmpty(grade)) {
                student.setGrade(grade);
            }

            Integer pageSize = StringUtil.isEmpty(limit)? ConfigUtil.getPageSize():Integer.parseInt(limit);
            Integer pageNum =  (Integer.parseInt(offset)/pageSize)+1;
            PageInfo<Student> userList= studentService.findUserPage(student,start,end,pageNum,pageSize,ordername,order);

            JSONObject jsonObj = new JSONObject();
            jsonObj.put("total",userList.getTotal() );
            jsonObj.put("rows", userList.getList());
            WriterUtil.write(response,jsonObj.toString());
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    // 新增或修改
    @RequestMapping("reserveStudent")
    public void reserveUser(HttpServletRequest request,Student student,HttpServletResponse response){
        Integer userId = student.getId();
        JSONObject result=new JSONObject();
        student.setCraetetime(new Date());
        try {
            if (userId != null) {   // userId不为空 说明是修改
                studentService.updateUser(student);
                result.put("success", true);

            }else {   // 添加
                if(studentService.existUserWithUserName(student.getName())==null){  // 没有重复可以添加
                    studentService.addUser(student);
                    result.put("success", true);
                } else {
                    result.put("success", true);
                    result.put("errorMsg", "该用户名被使用");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", true);
            result.put("errorMsg", "对不起，操作失败");
        }
        WriterUtil.write(response, result.toString());
    }


    @RequestMapping("deleteStudent")
    public void delUser(HttpServletRequest request, HttpServletResponse response){
        JSONObject result=new JSONObject();
        try {
            String[] ids=request.getParameter("ids").split(",");
            for (String id : ids) {
                studentService.deleteUser(Integer.parseInt(id));
            }
            result.put("success", true);
            result.put("delNums", ids.length);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("errorMsg", "对不起，删除失败");
        }
        WriterUtil.write(response, result.toString());
    }
}
