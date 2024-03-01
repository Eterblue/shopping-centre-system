package com.eterblue.mapper;

import com.eterblue.model.pojo.OrderDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 *
 */
@Mapper
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {

    void insertBatch(List<OrderDetail> detailList);
}
