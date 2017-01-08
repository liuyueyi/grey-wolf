package com.hust.hui.wolf.console.anction.img.operate;

import com.hust.hui.wolf.common.util.file.FileUtil;
import com.hust.hui.wolf.common.util.http.HttpUtil;
import com.hust.hui.wolf.common.util.img.ImgWrapper;
import com.hust.hui.wolf.console.anction.ResponseWrapper;
import com.hust.hui.wolf.console.anction.img.ImgBaseAction;
import com.hust.hui.wolf.console.anction.img.ImgResponse;
import com.hust.hui.wolf.console.util.ResponseUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

/**
 * Created by yihui on 2016/11/7.
 */
@Controller
public class ImgOperateAction extends ImgBaseAction {

    @RequestMapping(value = {"/image/operate"}, method = {RequestMethod.GET})
    public String execute(ModelMap model) {
        return "image/operate";
    }


    @RequestMapping(value = "/img/operate", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ResponseWrapper operateImg(HttpServletRequest httpServletRequest,
                                      HttpServletResponse httpServletResponse,
                                      ImgOperateRequest imageOperateRequest) throws Exception {

        // 参数校验
        imageOperateRequest.validateOrFail();

        String path = getPath();
        String name = getName(path.substring(path.lastIndexOf("/") + 1));

        // 将图片下载到本地
        HttpUtil.downImg(imageOperateRequest.getPath(), path, name);

        // 执行图片操作
        ImgWrapper.Builder<String> builder = ImgWrapper.of(path + "/" + name)
                .ofUser(0)
                .outputFormat("jpg");
        builder = imgOperate(imageOperateRequest, builder);
        if (builder == null) {
            throw new Exception("图片编辑失败");
        }

        // 这里的保存, 可以按照实际的情况进行处理
        InputStream outStream = builder.asStrem();
        String outName = "out_" + name;
        FileUtil.saveFile(outStream, path, outName);

        ImgResponse response = new ImgResponse();
        response.setName(outName);
        response.setPath(path + "/" + outName);

        return ResponseUtil.buildSuccResponse(response);
    }

    private <T> ImgWrapper.Builder<T> imgOperate(ImgOperateRequest operateRequest, ImgWrapper.Builder<T> builder) {
        String order = operateRequest.getOperateOrder();
        String[] operateType = StringUtils.split(order, "_");
        if (operateType.length == 0) {
            return null;
        }

        boolean tag = false;
        for (String operate : operateType) {
            switch (operate) {
                case OperateOrder.CUT:
                    builder = builder.crop(operateRequest.getX(), operateRequest.getY(),
                            operateRequest.getW(), operateRequest.getH());
                    tag = true;
                    break;
                case OperateOrder.ROTATE:
                    if (Math.abs(operateRequest.getAngle() % 360) <= 0.005) {
                        continue;
                    }
                    builder = builder.rotate(operateRequest.getAngle());
                    tag = true;
                    break;
                case OperateOrder.SCALE:
//                    if (operateRequest.getRatio() == null) {
//                        builder = builder.scale(operateRequest.getW(), operateRequest.getH(), operateRequest.getQuality());
//                    } else {
//                        builder = builder.scale(operateRequest.getRatio(), operateRequest.getQuality());
//                    }
//                    tag = true;
                    break;
                case OperateOrder.FLIP:
                    builder = builder.flip();
                    tag = true;
                    break;
                case OperateOrder.FLOP:
                    builder = builder.flop();
                    tag = true;
                    break;
            }
        }

        return tag ? builder : null;
    }

}
