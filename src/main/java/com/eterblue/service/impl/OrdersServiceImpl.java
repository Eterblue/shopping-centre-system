package com.eterblue.service.impl;

import com.eterblue.model.pojo.Orders;
import com.eterblue.mapper.OrdersMapper;
import com.eterblue.service.IOrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 *
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements IOrdersService {

}
