package com.eterblue.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.comparator.ComparableComparator;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eterblue.model.pojo.Product;
import com.eterblue.mapper.ProductMapper;
import com.eterblue.model.pojo.ShoppingCart;
import com.eterblue.model.vo.PageVO;
import com.eterblue.request.AddProductRequest;
import com.eterblue.request.PageProductRequest;
import com.eterblue.service.IProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
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
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

    private final RedisTemplate redisTemplate;

    @Override
    public void saveProduct(AddProductRequest productRequest) {
        Product product= BeanUtil.copyProperties(productRequest, Product.class);
        product.setCreateTime(LocalDateTime.now());
        product.setUpdateTime(LocalDateTime.now());
        product.setStatus(1);
        product.setSales(0);
        product.setId(null);
        save(product);
    }

    @Override
    public PageVO<Product> pageQuery(PageProductRequest productRequest) {

        /*//1.设置分页条件
        Page<Product> page = Page.of(productRequest.getPageNumber(), productRequest.getPageSize());
        OrderItem orderItem=new OrderItem();
        String sortBy = productRequest.getSortBy();
        orderItem.setColumn(Objects.requireNonNullElse(sortBy, "create_time"));
        orderItem.setAsc(productRequest.getAsc());
        page.addOrder(orderItem);

        //2.查询符合条件的商品
        Page<Product> p = lambdaQuery()
                //根据分类id查询
                .eq(productRequest.getCategoryId()!=null,Product::getCategoryId,productRequest.getCategoryId())
                .like(productRequest.getName()!=null,Product::getName,productRequest.getName())
                .page(page);

        //3.封装VO
        PageVO<Product> pageVO=new PageVO<>();
        pageVO.setPages(p.getPages());
        pageVO.setTotal(p.getTotal());
        if(p.getRecords()==null){
            pageVO.setList(Collections.emptyList());
            return pageVO;
        }
        pageVO.setList(p.getRecords());
        return pageVO;*/


        Long categoryId = productRequest.getCategoryId();
        Integer pageNumber = productRequest.getPageNumber();
        Integer pageSize = productRequest.getPageSize();

        List<Product> list = productList(productRequest.getSortBy(),productRequest.getAsc());
        //查询符合条件的商品
        List<Product> products = list.stream()
                .filter(product -> categoryId == null || (product.getCategoryId().equals(categoryId)))
                .skip((long) pageSize * (pageNumber - 1))
                .limit(pageSize)
                .toList();
        PageVO<Product> pageVO=new PageVO<>();
        pageVO.setTotal((long) products.size());
        pageVO.setPages((long) Math.ceil((double) products.size() /pageSize));
        pageVO.setList(products);
        return pageVO;
    }


    //从数据库或redis中获得list集合
    private List<Product> productList(String sortBy,Boolean asc){

        String key="Product::list"+sortBy+asc;
        List<Product> list = Convert.convert(new TypeReference<List<Product>>() {
        }, redisTemplate.opsForValue().get(key));

        if(list==null){
            synchronized (ProductServiceImpl.class){
                if (list!=null) return list;
                list = lambdaQuery().eq(Product::getStatus, 1).orderByAsc(sortBy==null,Product::getCreateTime).orderBy(sortBy!=null,asc,"price".equals(sortBy) ?(Product::getPrice):(Product::getSales)).list();
                redisTemplate.opsForValue().set(key,list);
            }

        }
        return list;
    }
}
