package com.eterblue.controller;


import com.eterblue.request.LoginUserRequest;
import com.eterblue.response.BaseResponse;
import com.eterblue.service.ICategoryService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 *
 */
@Slf4j
@RestController
@RequestMapping("/shop/category")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CategoryController {

    private final ICategoryService categoryService;

    /*public BaseResponse save(){

    }
*/
}
