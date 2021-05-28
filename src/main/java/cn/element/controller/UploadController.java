package cn.element.controller;

import cn.element.common.Constant;
import cn.element.common.ResultInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/upload")
public class UploadController {

    @PostMapping("/goodsPic")
    public ResultInfo uploadGoodsPic(MultipartFile file){

        if(file.isEmpty()){
            return ResultInfo.badRequest(Constant.UPLOAD_FAILED);
        }

        String fileName = file.getOriginalFilename();  //获取文件件的名称
        String suffixName = fileName.substring(fileName.lastIndexOf(".")); //后缀名称
        String filePath = "E:/ProgramSoftware/MaterialLibrary/temp/";//上传路径
        String finalPathName = filePath + System.currentTimeMillis() + suffixName; //最终的文件的路径

        File destinationFile = new File(finalPathName);

        if(!destinationFile.getParentFile().exists()){ //判断文件的父级目录是否存在
            destinationFile.getParentFile().mkdirs();
        }

        try {
            file.transferTo(destinationFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResultInfo.ok(Constant.UPLOAD_SUCCESS,finalPathName);
    }
}
