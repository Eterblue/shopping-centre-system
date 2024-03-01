package com.eterblue.service;

import com.eterblue.model.pojo.Orders;
import com.baomidou.mybatisplus.extension.service.IService;
import com.eterblue.model.vo.OrdersVO;
import com.eterblue.request.AddOrdersRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 *
 */
public interface IOrdersService extends IService<Orders> {

    OrdersVO saveOrders(AddOrdersRequest ordersRequest);
}
