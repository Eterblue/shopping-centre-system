package com.eterblue.service;

import com.eterblue.model.pojo.Product;
import com.baomidou.mybatisplus.extension.service.IService;
import com.eterblue.model.vo.PageVO;
import com.eterblue.request.AddProductRequest;
import com.eterblue.request.PageProductRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 *
 */
public interface IProductService extends IService<Product> {

    void saveProduct(AddProductRequest productRequest);

    PageVO<Product> pageQuery(PageProductRequest productRequest);
}
