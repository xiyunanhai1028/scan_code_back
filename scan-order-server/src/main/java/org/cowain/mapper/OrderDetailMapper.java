package org.cowain.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.cowain.entity.OrderDetail;

import java.util.List;

@Mapper
public interface OrderDetailMapper {
    //批量插入订单明细
    void insertBatch(List<OrderDetail> orderDetailList);

    @Select("select * from order_detail where order_id = #{orderId}")
    List<OrderDetail> getByOrderId(Long id);
}
