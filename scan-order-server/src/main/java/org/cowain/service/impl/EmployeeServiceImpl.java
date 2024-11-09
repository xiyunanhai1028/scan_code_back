package org.cowain.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.cowain.constant.MessageConstant;
import org.cowain.constant.PasswordConstant;
import org.cowain.constant.StatusConstant;
import org.cowain.dto.EmployeeDTO;
import org.cowain.dto.EmployeeLoginDTO;
import org.cowain.dto.EmployeePageQueryDTO;
import org.cowain.entity.Employee;
import org.cowain.exception.AccountLockedException;
import org.cowain.exception.AccountNotFundException;
import org.cowain.exception.PasswordErrorException;
import org.cowain.mapper.EmployeeMapper;
import org.cowain.result.PageResult;
import org.cowain.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;


import java.util.List;

@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    //员工登录
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1.根据员工名查询数据库中的数据
        Employee employee = employeeMapper.getByUserName(username);

        //2.处理各种异常情况（用户不存在，密码不对，账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        //对前端传递过来的密码进行md5加密
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3.返回实体对象
        return employee;
    }

    //新增员工
    public void save(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        //对象属性拷贝
        BeanUtils.copyProperties(employeeDTO, employee);
//        employee.setRole(Integer.valueOf(employeeDTO.getRole()));

        //设置账号默认状态，默认启用
        employee.setStatus(StatusConstant.ENABLE);

        //设置密码，默认密码123456,并且要加密
        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));

        //设置当前记录创建时间和修改时间
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
//
//        //设置当前记录创建人和修改人
//        employee.setCreateUser(BaseContext.getCurrentId());
//        employee.setUpdateUser(BaseContext.getCurrentId());

        //创建员工
        employeeMapper.insert(employee);
    }

    /**
     * 分页查询员工列表数据
     */
    public PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO) {
        //开启分页查询
        int page = employeePageQueryDTO.getPage();
        int pageSize = employeePageQueryDTO.getPageSize();
        PageHelper.startPage(page, pageSize);
        //查询
        Page<Employee> pageData = employeeMapper.pageQuery(employeePageQueryDTO);

        long total = pageData.getTotal();
        List<Employee> result = pageData.getResult();
        return new PageResult(total, result);
    }

    //启用/禁用账号
    public void startOrStop(Integer status, Long id) {
        Employee employee = Employee.builder()
                .id(id)
                .status(status)
                .build();
        employeeMapper.update(employee);
    }

    //根据ID查询员工信息
    public Employee getById(Long id) {
        Employee employee = employeeMapper.getById(id);
        return employee;
    }

    //编辑员工信息
    public void update(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        //TODO：设置更新时间和更新人
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setUpdateUser(BaseContext.getCurrentId());
        employeeMapper.update(employee);
    }


    public void deleteById(Long id) {
        employeeMapper.deleteById(id);
    }
}
