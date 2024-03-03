package com.eterblue.model.pojo;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@TableName("orders")
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 1待支付 2已取消 3已完成 4已退款
     */
    private Integer status;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 1普通配送 2快递配送 3及时配送 4同城配送
     */

    private Integer deliveryMethod;

    /**
     * 收货地址
     */
    private String address;

    /**
     * 收货人
     */
    private String consignee;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


}
