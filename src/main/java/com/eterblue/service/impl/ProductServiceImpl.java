package com.eterblue.service.impl;

import com.eterblue.model.pojo.Product;
import com.eterblue.mapper.ProductMapper;
import com.eterblue.service.IProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 *
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

}
