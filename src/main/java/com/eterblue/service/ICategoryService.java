package com.eterblue.service;

import com.eterblue.model.pojo.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.eterblue.request.AddCategoryRequest;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 *
 */
public interface ICategoryService extends IService<Category> {

    List<Category> listWithTree();

    void saveCategory(AddCategoryRequest categoryRequest);

    void statusCategory(Long id, Integer status);


    void deleteCategory(List<Long> ids);
}
