package com.eterblue.request;

import lombok.Data;


@Data
public class AddOrdersRequest {

    private String address;

    private Integer deliveryMethod;

}
