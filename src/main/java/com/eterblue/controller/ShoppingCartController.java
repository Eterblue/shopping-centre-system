package com.eterblue.controller;


import com.eterblue.model.pojo.ShoppingCart;
import com.eterblue.model.vo.PageVO;
import com.eterblue.request.PageQueryRequest;
import com.eterblue.response.BaseResponse;
import com.eterblue.service.IShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 *
 */
@Slf4j
@RestController
@RequestMapping("/shop/shopping-cart")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Api(tags = "购物车相关接口")
public class ShoppingCartController {
    private final IShoppingCartService shoppingCartService;

    @ApiOperation("分页查询购物车")
    @GetMapping("/page")
    public BaseResponse<PageVO<ShoppingCart>> pageQueryCart(PageQueryRequest pageQueryRequest){

        log.info("查看用户购物车:{}",pageQueryRequest);
        PageVO<ShoppingCart> pageVO=shoppingCartService.pageQueryCart(pageQueryRequest);
        return BaseResponse.success(pageVO);
    }

}
