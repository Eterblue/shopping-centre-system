package com.eterblue.service;

import com.eterblue.model.pojo.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.eterblue.request.AddCategoryRequest;
import com.eterblue.request.LoginUserRequest;
import com.eterblue.request.UpdateCategoryRequest;

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

    void deleteCategory(Long id);


}
