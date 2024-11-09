package org.cowain.controller.user;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.cowain.properties.ImageProperties;
import org.cowain.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Api("图片上传相关接口")
@RestController("userCommonController")
@RequestMapping("/user/common")
@Slf4j
public class CommonController {

    @Autowired
    private ImageProperties imageProperties;

    /**
     * 图片上传
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) {
        //获取上传文件原始名称
        String originalFilename = file.getOriginalFilename();
        //生成新的UUID名称,方式上传文件名称重复覆盖问题
        String uuid = UUID.randomUUID().toString();
        //获取文件后缀
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        //生成新的名称
        String fileName = uuid + suffix;
        //判断文件夹是否存在
        File dirs = new File(imageProperties.getPath());
        log.info("存储图片路径：{}",dirs.getAbsolutePath());
        if (!dirs.exists()) {
            dirs.mkdirs();
        }
        //保存图片
        try {
            //将临时文件转存到指定位置
            file.transferTo(new File(imageProperties.getPath() + fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Result.success(fileName);
    }

    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) {
        String  fileName = imageProperties.getPath() + name;

        try {
            //创建读取流
            FileInputStream fis = new FileInputStream(new File(fileName));
            //创建写出流
            ServletOutputStream ops = response.getOutputStream();
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = fis.read(buffer)) != -1) {
                ops.write(buffer, 0, len);
                ops.flush();
            }
            //关闭流
            ops.close();
            fis.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
