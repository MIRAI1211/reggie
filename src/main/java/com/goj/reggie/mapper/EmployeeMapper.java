package com.goj.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.goj.reggie.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}