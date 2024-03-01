package com.eterblue.controller;

import com.eterblue.response.BaseResponse;
import com.eterblue.util.OssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RequestMapping("/oss")
@RestController
@Slf4j
@Api(tags = "文件上传")
@RequiredArgsConstructor(onConstructor_= {@Autowired} )
public class UploadController {

    private final OssUtil ossUtil;

    @PostMapping("/upload")
    @ApiOperation("文件上传")
    public BaseResponse<String> upload(MultipartFile file){
        log.info("上传文件:{}",file);
        String originalFilename = file.getOriginalFilename();
        String objectName= UUID.randomUUID().toString()+originalFilename.substring(originalFilename.lastIndexOf("."));
        try {
            String url = ossUtil.upload(file.getBytes(), objectName);
            return BaseResponse.success(url);
        } catch (Exception e) {
            log.error("上传失败");
            return BaseResponse.error("上传失败");
        }
    }

}
