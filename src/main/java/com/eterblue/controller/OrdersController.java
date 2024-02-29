package com.eterblue.controller;


import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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


}
