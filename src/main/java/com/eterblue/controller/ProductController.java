package com.eterblue.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eterblue.model.pojo.Product;
import com.eterblue.model.vo.PageVO;
import com.eterblue.request.AddProductRequest;
import com.eterblue.request.PageProductRequest;
import com.eterblue.response.BaseResponse;
import com.eterblue.service.IProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 *
 */
@Slf4j
@RestController
@RequestMapping("/shop/product")
@Api(tags = "商品相关接口")
@RequiredArgsConstructor(onConstructor_ ={@Autowired} )
public class ProductController {

    private final IProductService productService;

    @ApiOperation("增加商品")
    @PostMapping("/add")
    public BaseResponse addProduct(@RequestBody AddProductRequest productRequest){
        log.info("增加商品:{}",productRequest);
        productService.saveProduct(productRequest);
        return BaseResponse.success();
    }

    @ApiOperation("改变商品状态")
    @PutMapping("/status/{status}")
    public BaseResponse status(@PathVariable Integer status,Long id){
        log.info("改变{}号商品的状态{}",id,status);
        Product product=Product.builder()
                .status(status)
                .id(id)
                .updateTime(LocalDateTime.now())
                .build();
        productService.updateById(product);
        return BaseResponse.success();
    }

    @ApiOperation("更改商品信息")
    @PutMapping("/update")
    public BaseResponse updateProduct(@RequestBody AddProductRequest productRequest){
        log.info("更改商品信息：{}",productRequest);
        Product product = BeanUtil.copyProperties(productRequest, Product.class);
        productService.updateById(product);
        return BaseResponse.success();
    }

    @ApiOperation("查询回显")
    @GetMapping("/{id}")
    public BaseResponse<Product> getById(@PathVariable Long id){
        log.info("查询回显；{}",id);
        Product product = productService.getById(id);
        return BaseResponse.success(product);
    }

    @ApiOperation("分页查询")
    @GetMapping("/page")
    public BaseResponse<PageVO<Product>> pageProduct(PageProductRequest productRequest){

        log.info("分页查询:{}",productRequest);
        PageVO<Product> pageVO=productService.pageQuery(productRequest);
        return BaseResponse.success(pageVO);
    }

}
