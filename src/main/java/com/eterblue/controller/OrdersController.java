package com.eterblue.controller;


import com.eterblue.model.pojo.Orders;
import com.eterblue.model.vo.OrdersVO;
import com.eterblue.request.AddOrdersRequest;
import com.eterblue.response.BaseResponse;
import com.eterblue.service.IOrdersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 *
 */
@RestController
@RequestMapping("/shop/orders")
@Slf4j
@RequiredArgsConstructor(onConstructor_ ={@Autowired} )
@Api(tags = "订单相关接口")
public class OrdersController {

    private final IOrdersService ordersService;

    @ApiOperation("新增订单")
    @PostMapping("/add")
    public BaseResponse<OrdersVO> addOrders(@RequestBody AddOrdersRequest ordersRequest){


        log.info("新增订单:{}",ordersRequest);
        OrdersVO ordersVO=ordersService.saveOrders(ordersRequest);


        return BaseResponse.success(ordersVO);
    }

}
