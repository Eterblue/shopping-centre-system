package com.eterblue.controller;


import cn.hutool.core.bean.BeanUtil;
import com.eterblue.exception.BaseException;
import com.eterblue.model.pojo.Product;
import com.eterblue.model.vo.PageVO;
import com.eterblue.request.AddProductRequest;
import com.eterblue.request.PageProductRequest;
import com.eterblue.response.BaseResponse;
import com.eterblue.service.ICategoryService;
import com.eterblue.service.IProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.Cacheable;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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

    private final RedisTemplate redisTemplate;

    @ApiOperation("增加商品")
    @PostMapping("/add")
    public BaseResponse addProduct(@RequestBody @Valid AddProductRequest productRequest){
        log.info("增加商品:{}",productRequest);
        productService.saveProduct(productRequest);
        cleanCache("page_*");
        return BaseResponse.success();
    }

    @ApiOperation("改变商品状态")
    @PostMapping("/status/{status}")
    public BaseResponse status(@PathVariable Integer status,Long id){
        log.info("改变{}号商品的状态{}",id,status);
        Product product=Product.builder()
                .status(status)
                .id(id)
                .updateTime(LocalDateTime.now())
                .build();
        productService.updateById(product);
        cleanCache("page_*");
        return BaseResponse.success();
    }

    @ApiOperation("更改商品信息")
    @PutMapping ("/update")
    public BaseResponse updateProduct(@RequestBody @Valid AddProductRequest productRequest){
        log.info("更改商品信息：{}",productRequest);
        Product product = BeanUtil.copyProperties(productRequest, Product.class);
        productService.updateById(product);
        cleanCache("page_*");
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

        //设置动态分类id
        String key="page_"+productRequest.toString();
        //从redis根据key中获取
        PageVO<Product> pageVO = (PageVO<Product>) redisTemplate.opsForValue().get(key);

        if(pageVO==null){
            pageVO=productService.pageQuery(productRequest);
            redisTemplate.opsForValue().set(key,pageVO);
        }
        return BaseResponse.success(pageVO);
    }

    @ApiOperation("删除商品")
    @DeleteMapping("/delete")
    public BaseResponse delete(@RequestParam List<Long> ids){

        log.info("删除商品：{}",ids);
        productService.removeBatchByIds(ids);
        cleanCache("page_*");
        return  BaseResponse.success();
    }

    private void cleanCache(String pattern){
        Set keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }
}
