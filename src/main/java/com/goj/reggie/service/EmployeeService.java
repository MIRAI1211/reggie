package com.goj.reggie.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.goj.reggie.entity.Employee;

import java.util.List;

public interface EmployeeService extends IService<Employee> {
    boolean save(Employee employee);

    Employee login(Employee employee);

    Page selectPage(Page page, LambdaQueryWrapper lqw);

    boolean offAndOn(Employee employee);
}

