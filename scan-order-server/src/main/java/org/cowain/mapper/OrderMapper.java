package org.cowain.mapper;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.cowain.dto.GoodsSalesDTO;
import org.cowain.dto.OrdersPageQueryDTO;
import org.cowain.entity.Orders;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {
    //插入订单数据
    void insert(Orders orders);

    Double sumByMap(Map map);

    Integer countByMap(Map map);

    List<GoodsSalesDTO> getSalesTop10(LocalDateTime begin, LocalDateTime end);

//    @Select("select * from orders where number = #{orderNumber} and user_id = #{userId}")
//    Orders getNumberAndUserId(String orderNumber, Long userId);

    void update(Orders o);

    Page<Orders> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

    @Select("select count(id) from orders where status = #{status}")
    Integer countStatus(Integer toBeConfirmed);

    @Select("select * from orders where sit_num = #{sitNum} and pay_status = #{payStatus}")
    Orders queryOrderBySitNumAndPayStatus(int sitNum, int payStatus);

    //根据订单号查询
    @Select("select * from orders where number = #{outTradeNo}")
    Orders getOrdersByNumber(String outTradeNo);

    //根据订单ID查询
    @Select("select * from orders where id =#{orderId}")
    Orders getOrderById(Long orderId);

    //根据时间查询订单
//    @Select("select * from orders where order_time > #{startDateTime} and order_time <= #{endDateTime}")
    List<Orders> getOrdersByTimeLT(LocalDateTime startDateTime, LocalDateTime endDateTime);

    @Select("select * from orders where sit_num = #{sitNum}  and  number = #{orderId}")
    Orders queryOrderBySitNumAndOrderId(Integer sitNum, String orderId);

    @Select("select * from orders where sit_num = #{sitNum}  and  id = #{id}")
    Orders queryOrderBySitNumAndId(Integer sitNum,String id);

    @Select("select * from orders where status=#{status} order by order_time asc")
    List<Orders> getOrdersByStatus(Integer status);

    @Select("select * from orders where  status in (1,2,3) and sit_num=#{sitNum} and LOCATE(#{userId},user_ids)")
    Orders queryOrderBySitNumAndUserId(Integer sitNum, Long userId);
}
