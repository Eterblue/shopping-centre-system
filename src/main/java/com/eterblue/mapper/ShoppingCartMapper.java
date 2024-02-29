package com.eterblue.mapper;

import com.eterblue.model.pojo.ShoppingCart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 *
 */
@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {

    //TODO
    //目前无用
    ShoppingCart queryByCart(ShoppingCart cart);

    //目前无用
    void updateQuantity(ShoppingCart cart);

    ShoppingCart queryCartByPdAndUd(Long productId, Long userId);

}
