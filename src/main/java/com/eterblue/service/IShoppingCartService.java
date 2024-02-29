package com.eterblue.service;

import com.eterblue.model.pojo.ShoppingCart;
import com.baomidou.mybatisplus.extension.service.IService;
import com.eterblue.model.vo.PageVO;
import com.eterblue.request.PageQueryRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 *
 */
public interface IShoppingCartService extends IService<ShoppingCart> {

    PageVO<ShoppingCart> pageQueryCart(PageQueryRequest pageQueryRequest);
}
