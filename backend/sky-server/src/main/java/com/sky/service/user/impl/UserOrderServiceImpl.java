package com.sky.service.user.impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sky.context.BaseContext;
import com.sky.dto.user.OrderDto;
import com.sky.mapper.DishMapper;
import com.sky.mapper.OrderDetailMapper;
import com.sky.mapper.OrderMapper;
import com.sky.service.user.UserOrderService;
import com.sky.entity.Dish;
import com.sky.entity.Order;
import com.sky.entity.OrderDetail;

import com.sky.vo.user.OrderDetailVO;
import com.sky.vo.user.OrderVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Service
public class UserOrderServiceImpl implements UserOrderService {


    @Autowired
    private OrderMapper orderMapper;


    @Autowired
    private OrderDetailMapper orderDetailMapper;


    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    @Override
    @Transactional
    public void createOrder(OrderDto orderDto) {

        /*
         * 1. 获取用户id
         *
         * 现在先写死
         * 后面接JWT以后：
         * Integer userId = BaseContext.getCurrentId();
         */
        Long userId = BaseContext.getCurrentId();

        /*
         * 2. 解析前端传来的菜品JSON
         *
         * [
         *   {
         *     "dishId":1,
         *     "quantity":2
         *   }
         * ]
         */

        String submitKey = "order:submit:" + userId;


        Boolean allowed = redisTemplate.opsForValue()
                .setIfAbsent(
                        submitKey,
                        "1",
                        2,
                        TimeUnit.SECONDS
                );

        if (!Boolean.TRUE.equals(allowed)) {
            throw new RuntimeException("请勿重复提交订单");
        }

        // Redis 防重复提交校验通过后

        String requestId = orderDto.getRequestId();

        if (requestId == null || requestId.trim().isEmpty()) {
            throw new RuntimeException("订单请求标识不能为空");
        }

// 查询这个用户是否已经用该 requestId 创建过订单
        Order existingOrder = orderMapper.selectOne(
                new LambdaQueryWrapper<Order>()
                        .eq(Order::getUserId, userId)
                        .eq(Order::getRequestId, requestId)
        );

        if (existingOrder != null) {
            return;
        }




            JSONArray dishes =
                    JSONArray.parseArray(orderDto.getDishes());

            BigDecimal totalPrice = BigDecimal.ZERO;

            // 保存订单明细
            List<OrderDetail> detailList =
                    new java.util.ArrayList<>();

            for (Object obj : dishes) {

                JSONObject json =
                        (JSONObject) obj;
                Long dishId =
                        json.getLong("dishId");

                Integer quantity =
                        json.getInteger("quantity");

                /*
                 * 3. 查询菜品
                 */
                Dish dish =
                        dishMapper.selectById(dishId);

                if (dish == null) {
                    throw new RuntimeException(
                            "菜品不存在,id=" + dishId
                    );

                }

                /*
                 * 4. 计算金额
                 *
                 * 数据库价格为准
                 * 不相信前端传价格
                 */

                BigDecimal price =
                        BigDecimal.valueOf(dish.getDishPrice());

                BigDecimal subtotal =
                        price.multiply(
                                new BigDecimal(quantity)
                        );
                totalPrice =
                        totalPrice.add(subtotal);

                /*
                 * 5. 创建订单明细
                 */

                OrderDetail detail =
                        new OrderDetail();

                detail.setDishId(dishId);

                detail.setDishName(
                        dish.getDishName()
                );

                detail.setDishPrice(price);

                detail.setQuantity(quantity);

                detailList.add(detail);

            }

            /*
             * 6. 保存订单主表
             */

            Order order = new Order();

            order.setOrderNo(
                    String.valueOf(System.currentTimeMillis())
            );

            order.setUserId(userId);

        order.setRequestId(requestId);


            order.setConsigneeName(
                    orderDto.getConsigneeName()
            );


            order.setConsigneePhone(
                    orderDto.getConsigneePhone()
            );


            order.setConsigneeAddress(
                    orderDto.getConsigneeAddress()
            );


            order.setTotalPrice(totalPrice);


            order.setStatus("待接单");


            order.setRemark(
                    orderDto.getRemark()
            );


            order.setCreateTime(
                    LocalDateTime.now()
            );


            order.setUpdateTime(
                    LocalDateTime.now()
            );

            orderMapper.insert(order);

            /*
             * 7. 保存订单明细
             *
             * 注意：
             * 现在订单id已经生成
             */

            for (OrderDetail detail : detailList) {
                detail.setOrderId(
                        order.getId()
                );

                detail.setCreateTime(
                        LocalDateTime.now()
                );
                orderDetailMapper.insert(detail);
            }

    }

    @Override
    public List<OrderVO> getUserOrders(){

        Long userId = BaseContext.getCurrentId();
        System.out.println("当前登录用户id=" + userId);
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(Order::getUserId, userId);
        List<OrderVO> orderVOList = new ArrayList<>();
        List<Order> orders = orderMapper.selectList(wrapper);
        for(Order order : orders){
            LambdaQueryWrapper<OrderDetail> wrapper1 = new LambdaQueryWrapper<>();

            wrapper1.eq(OrderDetail::getOrderId, order.getId());
            List<OrderDetail> orderDetailList =
                    orderDetailMapper.selectList(wrapper1);
            OrderVO orderVO = new OrderVO();

            List<OrderDetailVO> orderDetailVOList = new ArrayList<>();


            for(OrderDetail orderDetail : orderDetailList){

                OrderDetailVO orderDetailVO = new OrderDetailVO();

                BeanUtils.copyProperties(orderDetail, orderDetailVO);

                orderDetailVOList.add(orderDetailVO);
            }
            orderVO.setOrderDetails(orderDetailVOList);

            orderVO.setOrderNo(order.getOrderNo());
            orderVO.setConsigneeName(order.getConsigneeName());
            orderVO.setConsigneePhone(order.getConsigneePhone());
            orderVO.setConsigneeAddress(order.getConsigneeAddress());
            orderVO.setStatus(order.getStatus());
            orderVO.setRemark(order.getRemark());
            orderVO.setTotalPrice(order.getTotalPrice());
            orderVO.setCreateTime(order.getCreateTime());
            orderVOList.add(orderVO);

        }



        return orderVOList;
    }


}
