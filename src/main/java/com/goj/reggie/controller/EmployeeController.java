package com.goj.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.goj.reggie.common.R;
import com.goj.reggie.entity.Employee;
import com.goj.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    @PostMapping("/login")
    private R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());//md5加密
        Employee result = employeeService.login(employee);
        if (result==null){
            return R.error("登录失败");
        }
        if(!result.getPassword().equals(password)){
            return R.error("登录失败");
        }
        if (result.getStatus()==0){
            return R.error("员工以禁用");
        }
        HttpSession session = request.getSession();
        session.setAttribute("employee",result.getId());
        return R.success(result);
    }

    @PostMapping("/logout")
    private R<String> logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.removeAttribute("employee");
        return R.success("退出成功");
    }

    @PostMapping()
    public R addEmployee(HttpServletRequest request,@RequestBody Employee employee){
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        String empId = (String) request.getSession().getAttribute("employee");
        employee.setCreateUser(empId);
        employee.setUpdateUser(empId);
        employeeService.save(employee);
        return R.success("新增员工成功");
    }

    @GetMapping("/page")
    private R<Page> page(int page, int pageSize, String name){
        Page pageObject=new Page(page,pageSize);
        LambdaQueryWrapper<Employee> lqw=new LambdaQueryWrapper<>();
        lqw.like(name!=null,Employee::getName,name);
        Page page1 = employeeService.selectPage(pageObject, lqw);
        return R.success(page1);
    }
    
    @PutMapping()
    private R<String> offAndOn(HttpServletRequest request,@RequestBody Employee employee){
        String emId =(String) request.getSession().getAttribute("employee");
        employee.setId(emId);
        employee.setUpdateTime(LocalDateTime.now());
        employeeService.offAndOn(employee);
        return R.success("员工信息修改成功");
    }
}