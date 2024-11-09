package org.cowain.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.cowain.dto.ShopDTO;
import org.cowain.entity.Shop;

@Mapper
public interface ShopMapper {
    @Insert("insert into shop(name,status, image, address, begin_time, end_time, description) " +
            "values " +
            "(name = #{name}, status=#{status},image = #{image}, address = #{address}, begin_time = #{beginTime}, end_time=#{endTime}, description=#{description})")
    void insert(Shop shop);

    void update(Shop shop);

    @Select("select * from shop")
    Shop queryInfo();

    @Select("select * from shop where id=#{id}")
    Shop queryById(Long id);
}
