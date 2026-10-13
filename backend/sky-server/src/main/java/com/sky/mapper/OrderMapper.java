package com.sky.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sky.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;


@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    @Update("UPDATE order_info SET status = #{newStatus} WHERE id = #{id} AND status = #{currentStatus}")
    int updateStatusIfMatch(
            @Param("id") Long id,
            @Param("currentStatus") String currentStatus,
            @Param("newStatus") String newStatus
    );


}