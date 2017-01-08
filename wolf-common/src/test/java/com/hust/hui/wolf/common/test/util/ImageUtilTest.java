package com.hust.hui.wolf.common.test.util;

import com.hust.hui.wolf.common.util.img.ImgBasicOperateUtil;
import com.hust.hui.wolf.common.util.img.ImgInfoUtil;
import org.junit.Test;

/**
 * Created by yihui on 16/10/31.
 */
public class ImageUtilTest {

    @Test
    public void testGetImgSize() {
        int size = ImgInfoUtil.getSize("src/test/resources/img/wrong.jpg");
        System.out.println(size);
    }

    @Test
    public void testImgCut(){
        String path = "src/test/resources/img/wrong.jpg";
        ImgBasicOperateUtil.cut(path, "src/test/resources/img/cut_out.jpg", 0, 0, 600, 600);
    }


    @Test
    public void testImgRotate() {
        String path = "src/test/resources/img/wrong.jpg";
        ImgBasicOperateUtil.rotate(path, "src/test/resources/img/rotate_out.jpg", 90);
    }

    @Test
    public void testImgRotateCut() {
        String path = "src/test/resources/img/wrong.jpg";
        ImgBasicOperateUtil.rotateAndCut(path, "src/test/resources/img/rotate_cut_out.jpg", 90, 300, 300, 600, 600);
    }


    @Test
    public void testImgResize() {
        String path = "src/test/resources/img/wrong.jpg";
        ImgBasicOperateUtil.zoom(path, "src/test/resources/img/zoom_out.jpg", 400, 400);
    }



    @Test
    public void testImgWater() {
        String path = "src/test/resources/img/wrong.jpg";
        String water = "src/test/resources/img/water.png";

        ImgBasicOperateUtil.waterMark(water, path, "src/test/resources/img/water_out.jpg", "southeast", 99);
    }


    @Test
    public void testFontWater() {
        String path ="src/test/resources/img/wrong.jpg";
        // 这个绘制的文字,前面的 text 150,150 必须要存在,
        String text = "text 150,150 'what fuck'";

        ImgBasicOperateUtil.waterMark(path, "src/test/resources/img/water_font.jpg", text, "宋体", 25, "southeast", "#FF0000");
    }

}
