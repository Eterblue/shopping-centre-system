package com.eterblue.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.eterblue.exception.BaseException;
import com.eterblue.model.pojo.Category;
import com.eterblue.mapper.CategoryMapper;
import com.eterblue.model.pojo.Product;
import com.eterblue.request.AddCategoryRequest;
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
        List<Category> list =lambdaQuery().eq(Category::getStatus,1).list();
        //组装父子结构
        List<Category> level = list.stream().filter(category -> category.getParentId() == 0)
                .map(menu-> menu.setChildren(getChildren(menu,list)))
                .sorted(Comparator.comparingInt(Category::getRank))
                .toList();

        return level;
    }

    @Transactional
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
    public void statusCategory(Long id, Integer status) {

        if(status==1){
         //显示分类
         lambdaUpdate().set(Category::getStatus,status).eq(Category::getId,id).update();
         return;
        }
        //1.判断该分类下是否含有子分类或商品
        Integer number=baseMapper.getByParentId(id);
        if (number>0){
            throw new BaseException("该分类下含有子分类,删除失败");
        }
        number= Math.toIntExact(Db.lambdaQuery(Product.class).eq(Product::getCategoryId, id).eq(Product::getStatus,1).count());
        if (number>0){
            throw new BaseException("该分类下含有商品,删除失败");
        }
        lambdaUpdate().set(Category::getStatus,status).eq(Category::getId,id).update();
    }

    @Override
    @Transactional
    public void deleteCategory(List<Long> ids) {

        //1.获取分类信息
        List<Category> list = listByIds(ids);
        if(list.isEmpty()){
            throw new BaseException("分类不存在");
        }
        //2.若分类状态为不显示则可以删除分类
        for (Category category : list) {
            if(category.getStatus()==1){
                throw new BaseException("分类为："+category.getName()+"不能删除");
            }
        }

        removeBatchByIds(ids);
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
