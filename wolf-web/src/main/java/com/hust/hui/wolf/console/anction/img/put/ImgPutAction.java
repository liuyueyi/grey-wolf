package com.hust.hui.wolf.console.anction.img.put;

import com.hust.hui.wolf.common.util.file.FileUtil;
import com.hust.hui.wolf.console.anction.ResponseWrapper;
import com.hust.hui.wolf.console.anction.base.Action;
import com.hust.hui.wolf.console.anction.img.ImgResponse;
import com.hust.hui.wolf.console.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zitian on 3/18/16.
 */
@Controller
public class ImgPutAction implements Action {

    private final static Logger logger = LoggerFactory.getLogger(ImgPutAction.class);

    // todo 可以考虑如何在抽象一层, 直接处理这种请求页面的情况
    @RequestMapping(value = "/image/put", method = {RequestMethod.GET})
    public String execute(HttpServletRequest httpServletRequest,
                          HttpServletResponse httpServletResponse) {
        return "image/put";
    }


    @RequestMapping(value = "/img/upload", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseWrapper<ImgResponse> postImg(HttpServletRequest httpServletRequest,
                                   HttpServletResponse httpServletResponse,
                                   @RequestParam(name = "image") MultipartFile image) throws Exception {
        String path = FileUtil.createTempDir(0);
        String tmpName = System.nanoTime() + "_" + image.getOriginalFilename();
        FileUtil.saveFile(image.getInputStream(), path, tmpName);

        ImgResponse response = new ImgResponse();
        response.setName(image.getOriginalFilename());
        response.setPath(path + "/" + tmpName);
        // 图片上传
        return ResponseUtil.buildSuccResponse(response);
    }
}
