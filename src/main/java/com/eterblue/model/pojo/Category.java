package com.eterblue.model.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.List;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 
 * </p>
 *
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("category")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 分类级别（1-一级分类，2-二级分类）
     */
    @Range(min = 1,max = 2,message = "层级为1或2")
    private Integer level;

    /**
     * 父分类id
     */
    @NotNull
    private Long parentId;

    /**
     * 分类名称
     */
    @NotNull
    private String name;

    /**
     * 排序值（字段越大越靠前）
     */
    @Range(min = 0,message = "rank最小为0")
    @TableField(value = "`rank`")
    private Integer rank;

    /**
     * 0 不显示 1显示
     */
    private Integer status;

    /**
     * 创建时间
     */

    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private List<Category> children;
}
