package com.eterblue.service.impl;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.eterblue.model.pojo.Product;
import com.eterblue.model.pojo.ShoppingCart;
import com.eterblue.mapper.ShoppingCartMapper;
import com.eterblue.model.vo.PageVO;
import com.eterblue.request.PageCartRequest;
import com.eterblue.request.UpdateCartRequest;
import com.eterblue.service.IShoppingCartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eterblue.util.ThreadUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
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
    public PageVO<ShoppingCart> pageQueryCart(PageCartRequest pageQueryRequest) {
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


    @Override
    public void addShoppingCart(Long productId) {
        Long userId = ThreadUtil.getCurrentId();
        //查询出的购物车信息
        ShoppingCart cart = baseMapper.queryCartByPdAndUd(productId,userId);

        if(cart==null){
            //新增购物车
            List<Product> products = Db.lambdaQuery(Product.class).eq(Product::getId, productId).list();
            Product product = products.get(0);
            ShoppingCart shoppingCart= ShoppingCart.builder()
                    .userId(userId)
                    .productId(productId)
                    .name(product.getName())
                    .createTime(LocalDateTime.now())
                    .image(product.getImage())
                    .status(product.getStatus())
                    .isSelected(1)
                    .amount(product.getPrice())
                    .quantity(1)
                    .build();
            save(shoppingCart);
        }
        else{
            //购物车中商品数量加1
            cart.setQuantity(cart.getQuantity()+1);
            updateById(cart);
        }
    }

    @Override
    public void updateShoppingCart(UpdateCartRequest cartRequest) {
        /*Long productId = cartRequest.getProductId();
        Long userId = ThreadUtil.getCurrentId();*/
        Long id=cartRequest.getId();
        //1.获取购物车
        ShoppingCart cart = getById(id);
        //2.1.购物车商品增减功能
        Integer plusOrMinus = cartRequest.getQuantity();
        Integer quantity=cart.getQuantity();
        if(plusOrMinus==1){
            //商品数量加一
            cart.setQuantity(quantity+1);
            updateById(cart);
        }
        else {
            //商品数量减一
            if(quantity==1){
                //删除该项购物车
                removeById(cart.getId());
            }
            else {
                cart.setQuantity(quantity-1);
                updateById(cart);
            }
        }
        //2.2.是否选中购物车
        Integer isSelected = cartRequest.getIsSelected();
        if(isSelected==1){
            //选中
            cart.setIsSelected(1);
            updateById(cart);
        }
        else {
            //未选中
            cart.setIsSelected(0);
            updateById(cart);
        }

        //2.3.更新购物车状态
        Integer status = cartRequest.getStatus();
        //status进行更新状态操作
        if(status==1){
            //获取商品信息
            Product product = Db.getById(cart.getProductId(), Product.class);
            cart.setStatus(product.getStatus());
            if(cart.getStatus()==0){
                //商品不能被选中
                cart.setIsSelected(0);
            }
            updateById(cart);
        }
    }

    /**
     * 批量选中或取消
     * @param ids
     * @param bool
     */
    @Override
    @Transactional
    public void selectCarts(List<Long> ids, Boolean bool) {
        Integer status=bool?1:0;
        List<ShoppingCart> shoppingCarts = listByIds(ids);
        List<ShoppingCart> list = shoppingCarts.stream().filter(shoppingCart -> shoppingCart.getUserId().equals(ThreadUtil.getCurrentId())).map(shoppingCart -> shoppingCart.setIsSelected(status)).toList();
        updateBatchById(list);
    }
}