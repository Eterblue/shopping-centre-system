package com.eterblue.mapper;

import com.eterblue.model.pojo.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 *
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

    Integer getByParentId(Long id);
}
