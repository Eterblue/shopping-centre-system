package com.eterblue.controller;


import com.eterblue.model.pojo.ShoppingCart;
import com.eterblue.model.vo.PageVO;
import com.eterblue.request.PageQCartRequest;
import com.eterblue.request.UpdateCartRequest;
import com.eterblue.response.BaseResponse;
import com.eterblue.service.IShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public BaseResponse<PageVO<ShoppingCart>> pageQueryCart(PageQCartRequest pageQueryRequest){

        log.info("查看用户购物车:{}",pageQueryRequest);
        PageVO<ShoppingCart> pageVO=shoppingCartService.pageQueryCart(pageQueryRequest);
        return BaseResponse.success(pageVO);
    }

    @ApiOperation("增加购物车商品")
    @PostMapping("/add")
    public BaseResponse addShoppingCart(Long productId){

        log.info("添加购物车商品:{}",productId);
        shoppingCartService.addShoppingCart(productId);
        return BaseResponse.success();
    }

    @ApiOperation("更新购物车")
    @PutMapping("/update")
    public BaseResponse updateShoppingCart(@RequestBody UpdateCartRequest cartRequest){

        log.info("更新购物车:{}",cartRequest);
        shoppingCartService.updateShoppingCart(cartRequest);
        return BaseResponse.success();
    }

    @ApiOperation("批量选中或取消")
    @PutMapping("/listCart")
    public BaseResponse listCart(@RequestBody List<Long> ids, Boolean bool){

        log.info("批量选中或取消:{}，:{}",ids,bool);
        shoppingCartService.selectCarts(ids,bool);
        return BaseResponse.success();
    }
}
