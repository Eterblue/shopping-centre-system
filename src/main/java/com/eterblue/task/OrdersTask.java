package com.eterblue.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.eterblue.mapper.OrdersMapper;
import com.eterblue.model.pojo.Orders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor(onConstructor_ ={@Autowired} )
public class OrdersTask {

    private final OrdersMapper ordersMapper;

    @Scheduled(cron = "0 * * * * ?")
    public void processOutOrders(){
        log.info("定时处理超时订单:{}", LocalDateTime.now());
        LocalDateTime time=LocalDateTime.now().plusMinutes(-10);
        QueryWrapper<Orders> queryWrapper=new QueryWrapper<Orders>()
                .select("*")
                .le("create_time",time)
                .eq("status",1);
        //查询出超时订单
        List<Orders> orders = ordersMapper.selectList(queryWrapper);
        for (Orders order : orders) {
            order.setStatus(2);
            ordersMapper.updateById(order);
        }
    }

}
