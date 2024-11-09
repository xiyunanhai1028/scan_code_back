package org.cowain.service;


import org.cowain.dto.EmployeeDTO;
import org.cowain.dto.EmployeeLoginDTO;
import org.cowain.dto.EmployeePageQueryDTO;
import org.cowain.entity.Employee;
import org.cowain.result.PageResult;

public interface EmployeeService {

    //员工登录
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    //新增员工
    void save(EmployeeDTO employeeDTO);

    //请求员工列表数据
    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    //启用/禁用账号
    void startOrStop(Integer status, Long id);

    //根据ID查询员工信息
    Employee getById(Long id);

    //编辑员工信息
    void update(EmployeeDTO employeeDTO);

    //删除员工
    void deleteById(Long id);
}
