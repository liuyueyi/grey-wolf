package com.hust.hui.wolf.console.anction.img.scale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by yihui on 2017/1/8.
 */
@Controller
public class ImgScaleAction {

    @RequestMapping(value = {"/image/scale"}, method = {RequestMethod.GET})
    public String execute(ModelMap model) {
        return "image/scale";
    }

    @RequestMapping(value ="/img/scale", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String postImg(HttpServletRequest httpServletRequest,
                          HttpServletResponse httpServletResponse,
                          ImgScaleRequest imgScaleRequest) {

        // 图片缩放
        return "";
    }

}
