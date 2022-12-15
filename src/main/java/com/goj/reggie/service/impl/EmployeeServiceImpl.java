package com.goj.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.goj.reggie.entity.Employee;
import com.goj.reggie.mapper.EmployeeMapper;
import com.goj.reggie.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService{

    @Autowired
    private EmployeeMapper eMapper;

    @Override
    public boolean save(Employee employee) {
        int count = eMapper.insert(employee);
        return count>0;
    }

    @Override
    public Employee login(Employee employee) {
        LambdaQueryWrapper<Employee> lqw=new LambdaQueryWrapper<>();
        lqw.eq(Employee::getUsername,employee.getUsername());
        return eMapper.selectOne(lqw);
    }

    public Page selectPage(Page page,LambdaQueryWrapper lqw){
        return eMapper.selectPage(page, lqw);
    }

}