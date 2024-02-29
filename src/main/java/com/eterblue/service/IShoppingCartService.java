package com.eterblue.service;

import com.eterblue.model.pojo.ShoppingCart;
import com.baomidou.mybatisplus.extension.service.IService;
import com.eterblue.model.vo.PageVO;
import com.eterblue.request.PageQueryRequest;
import com.eterblue.request.UpdateCartRequest;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 *
 */
public interface IShoppingCartService extends IService<ShoppingCart> {

    PageVO<ShoppingCart> pageQueryCart(PageQueryRequest pageQueryRequest);

    void addShoppingCart(Long productId);

    void updateShoppingCart(UpdateCartRequest cartRequest);

    void selectCarts(List<Long> ids, Boolean bool);
}
