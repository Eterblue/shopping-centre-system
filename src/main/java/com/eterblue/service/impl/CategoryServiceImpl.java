package com.eterblue.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.eterblue.model.pojo.Category;
import com.eterblue.mapper.CategoryMapper;
import com.eterblue.model.pojo.Product;
import com.eterblue.request.AddCategoryRequest;
import com.eterblue.request.UpdateCategoryRequest;
import com.eterblue.service.ICategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;



/**
 * <p>
 *  服务实现类
 * </p>
 *
 *
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

    @Override
    public List<Category> listWithTree() {
        //所有分类
        List<Category> list = baseMapper.selectList(null);
        //组装父子结构
        List<Category> level = list.stream().filter(category -> category.getParentId() == 0)
                .map(menu-> menu.setChildren(getChildren(menu,list)))
                .sorted(Comparator.comparingInt(Category::getRank))
                .toList();

        return level;
    }

    @Override
    public void saveCategory(AddCategoryRequest categoryRequest) {
        //1.设置category基本信息
        Category category= BeanUtil.copyProperties(categoryRequest, Category.class);
        category.setStatus(0);
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        category.setId(null);

        //2.更改其他分类的rank
        Integer rank = category.getRank();

        List<Category> list = lambdaQuery()
                .eq(Category::getLevel, category.getLevel())
                .ge(Category::getRank, rank)
                .list();
        List<Category> categories = list.stream().map(c -> c.setRank(c.getRank() + 1)).toList();

        updateBatchById(categories);
        save(category);
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        //1.判断该分类下是否含有子分类或商品
        Integer number=baseMapper.getByParentId(id);
        if (number>0){
            throw new RuntimeException("该分类下含有子分类,删除失败");
        }
        number= Math.toIntExact(Db.lambdaQuery(Product.class).eq(Product::getCategoryId, id).count());
        if (number>0){
            throw new RuntimeException("该分类下含有商品,删除失败");
        }
        lambdaUpdate().set(Category::getStatus,0).eq(Category::getId,id).update();
    }


    private List<Category> getChildren(Category root,List<Category> all){
        return all.stream()
                .filter(category -> category.getParentId().equals(root.getId()))
                .map(category -> {
                    //找到子菜单
                    category.setChildren(getChildren(category,all));
                    return category;
                })
                //进行排序
                .sorted(Comparator.comparingInt(Category::getRank))
                .toList();
    }
}
