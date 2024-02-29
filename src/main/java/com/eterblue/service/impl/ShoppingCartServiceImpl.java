package com.eterblue.service.impl;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eterblue.model.pojo.ShoppingCart;
import com.eterblue.mapper.ShoppingCartMapper;
import com.eterblue.model.vo.PageVO;
import com.eterblue.request.PageQueryRequest;
import com.eterblue.service.IShoppingCartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eterblue.util.ThreadUtil;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 *
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements IShoppingCartService {

    @Override
    public PageVO<ShoppingCart> pageQueryCart(PageQueryRequest pageQueryRequest) {
        Long userId = ThreadUtil.getCurrentId();
        //1.设置分页条件
        Page<ShoppingCart> page = Page.of(pageQueryRequest.getPageNumber(), pageQueryRequest.getPageSize());
        OrderItem orderItem=new OrderItem();
        String sortBy = pageQueryRequest.getSortBy();
        orderItem.setColumn(Objects.requireNonNullElse(sortBy, "create_time"));
        orderItem.setAsc(pageQueryRequest.getAsc());
        page.addOrder(orderItem);
        //2.查询购物车
        Page<ShoppingCart> p = lambdaQuery()
                .eq(ShoppingCart::getUserId,userId)
                .page(page);
        //3.封装VO
        PageVO<ShoppingCart> pageVO=new PageVO<>();
        pageVO.setPages(p.getPages());
        pageVO.setTotal(p.getTotal());
        if(p.getRecords()==null){
            pageVO.setList(Collections.emptyList());
            return pageVO;
        }
        pageVO.setList(p.getRecords());
        return pageVO;
    }
}
