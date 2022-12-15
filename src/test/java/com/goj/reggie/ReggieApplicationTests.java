package com.goj.reggie;

import com.goj.reggie.entity.Employee;
import com.goj.reggie.mapper.EmployeeMapper;
import com.goj.reggie.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ReggieApplicationTests {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    EmployeeMapper em;



    @Test
    void test1(){
        List<Employee> employees = em.selectList(null);
        System.out.println(employees);
    }
}
