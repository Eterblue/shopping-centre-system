package com.eterblue.controller;


import cn.hutool.core.bean.BeanUtil;
import com.eterblue.model.pojo.Category;
import com.eterblue.request.AddCategoryRequest;
import com.eterblue.request.UpdateCategoryRequest;
import com.eterblue.response.BaseResponse;
import com.eterblue.service.ICategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 *
 */
@Slf4j
@RestController
@RequestMapping("/shop/category")
@Api(tags = "分类相关接口")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CategoryController {

    private final ICategoryService categoryService;

    @ApiOperation("查询出所有分类")
    @GetMapping("/list/tree")
    public BaseResponse<List<Category>> listWithTree(){
        log.info("查询所有分类");
        List<Category> list=categoryService.listWithTree();
        return BaseResponse.success(list);
    }

    @ApiOperation("增加分类")
    @PostMapping("/add")
    public BaseResponse addCategory(@RequestBody @Valid AddCategoryRequest categoryRequest){
        log.info("增加分类:{}",categoryRequest);
        categoryService.saveCategory(categoryRequest);

        return BaseResponse.success();
    }

    @ApiOperation("显示、不显示分类")
    @PostMapping ("/status/{status}")
    public BaseResponse statusCategory(@PathVariable Integer status, Long id){
        log.info("分类id:{}，状态:{}",id,status);
        categoryService.statusCategory(id,status);
        return BaseResponse.success();
    }

    @ApiOperation("更新分类数据")
    @PutMapping("/update")
    public BaseResponse updateCategory(@RequestBody UpdateCategoryRequest categoryRequest){
        log.info("更新分类:{}",categoryRequest);
        Category category = BeanUtil.copyProperties(categoryRequest, Category.class);
        category.setUpdateTime(LocalDateTime.now());
        categoryService.updateById(category);
        return BaseResponse.success();
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除分类")
    public BaseResponse deleteCategory(@RequestBody List<Long> ids){
        log.info("删除分类:{}",ids);
        categoryService.deleteCategory(ids);
        return BaseResponse.success();
    }
}
