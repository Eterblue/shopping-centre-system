package com.eterblue.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.eterblue.exception.BaseException;
import com.eterblue.mapper.OrderDetailMapper;
import com.eterblue.mapper.ShoppingCartMapper;
import com.eterblue.model.pojo.OrderDetail;
import com.eterblue.model.pojo.Orders;
import com.eterblue.mapper.OrdersMapper;
import com.eterblue.model.pojo.ShoppingCart;
import com.eterblue.model.pojo.User;
import com.eterblue.model.vo.OrdersVO;
import com.eterblue.request.AddOrdersRequest;
import com.eterblue.service.IOrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eterblue.util.ThreadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 *
 */
@Service
@RequiredArgsConstructor(onConstructor_ ={@Autowired} )
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements IOrdersService {

    private final OrderDetailMapper detailMapper;
    private final ShoppingCartMapper shoppingCartMapper;
    @Override
    public OrdersVO saveOrders(AddOrdersRequest ordersRequest) {
        //1.1获取收货人信息
        Long userId = ThreadUtil.getCurrentId();
        String consignee = Db.lambdaQuery(User.class).eq(User::getId, userId).list().get(0).getName();
        //1.2查询出选中的购物车
        Orders orders= BeanUtil.copyProperties(ordersRequest, Orders.class);
        List<ShoppingCart> list = Db.lambdaQuery(ShoppingCart.class)
                .eq(ShoppingCart::getUserId, userId).eq(ShoppingCart::getIsSelected, 1).list();
        if(list.isEmpty()){
            throw new BaseException("购物车为空，下单失败");
        }
        if(orders.getAddress()==null){
            throw new BaseException("配送地址为空，下单失败");
        }
        //2.1添加订单
        BigDecimal amount= BigDecimal.valueOf(0);
        for (ShoppingCart shoppingCart : list) {
            amount = amount.add(shoppingCart.getAmount().multiply(BigDecimal.valueOf(shoppingCart.getQuantity())));
        }
        orders.setStatus(1);
        orders.setCreateTime(LocalDateTime.now());
        orders.setConsignee(consignee);
        orders.setAmount(amount);
        save(orders);
        //2.2添加订单明细表
        List<OrderDetail> detailList = list.stream().map(detail -> {
            OrderDetail orderDetail = BeanUtil.copyProperties(detail, OrderDetail.class);
            orderDetail.setOrderId(orders.getId());
            return orderDetail;
        }).toList();
        detailMapper.insertBatch(detailList);
        //3.删除已下单的购物车
        shoppingCartMapper.deleteBatchIds(list);
        //4.封装VO并返回
        OrdersVO ordersVO=OrdersVO.builder()
                .deliveryMethod(orders.getDeliveryMethod())
                .amount(orders.getAmount())
                .createTime(orders.getCreateTime())
                .id(orders.getId())
                .build();
        return ordersVO;
    }
}
