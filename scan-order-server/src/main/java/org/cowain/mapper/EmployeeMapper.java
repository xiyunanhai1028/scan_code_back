package org.cowain.mapper;

import com.github.pagehelper.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.cowain.annotation.AutoFill;
import org.cowain.dto.EmployeePageQueryDTO;
import org.cowain.entity.Employee;
import org.cowain.enumeration.OperationType;


@Mapper
public interface EmployeeMapper {

    @Select("select * from employee where username = #{username}")
    Employee getByUserName(String username);

    @AutoFill(OperationType.INSERT)
    @Insert("insert into employee(name, role,username, password, phone, sex, id_number, create_time, update_time, create_user, update_user) " +
            "VALUES " +
            "(#{name},#{role},#{username},#{password},#{phone},#{sex},#{idNumber},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    void insert(Employee employee);

    Page<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    @AutoFill(OperationType.UPDATE)
    void update(Employee employee);

    @Select("select * from employee where id = #{id}")
    Employee getById(Long id);

    @Delete("delete from employee where id = #{id}")
    void deleteById(Long id);
}
