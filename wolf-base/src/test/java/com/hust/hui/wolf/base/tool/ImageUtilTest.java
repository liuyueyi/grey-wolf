package com.hust.hui.wolf.base.tool;

import com.hust.hui.wolf.base.domain.model.entity.ImageInfo;
import com.hust.hui.wolf.base.util.img.ImgInfoUtil;
import com.hust.hui.wolf.base.util.img.ImgOperateUtil;
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
    public void testImgInfo() {
        String path = "src/test/resources/img/wrong.jpg";
        ImageInfo imgInfo = ImgInfoUtil.getImgInfo(path);
        System.out.println(imgInfo);
    }

    @Test
    public void testImgCut(){
        String path = "src/test/resources/img/wrong.jpg";
        ImgOperateUtil.cut(path, "src/test/resources/img/cut_out.jpg", 0, 0, 1200, 1200);
    }


    @Test
    public void testImgRotate() {
        String path = "src/test/resources/img/wrong.jpg";
        ImgOperateUtil.rotate(path, "src/test/resources/img/rotate_out.jpg", 90);
    }

    @Test
    public void testImgRotateCut() {
        String path = "src/test/resources/img/wrong.jpg";
        ImgOperateUtil.rotateAndCut(path, "src/test/resources/img/rotate_cut_out.jpg", 90, 300, 300, 600, 600);
    }


    @Test
    public void testImgResize() {
        String path = "src/test/resources/img/wrong.jpg";
        ImgOperateUtil.zoom(path, "src/test/resources/img/zoom_out.jpg", 400, 400);
    }



    @Test
    public void testImgWater() {
        String path = "src/test/resources/img/wrong.jpg";
        String water = "src/test/resources/img/water.png";

        ImgOperateUtil.waterMark(water, path, "src/test/resources/img/water_out.jpg", "southeast", 99);
    }


    @Test
    public void testFontWater() {
        String path ="src/test/resources/img/wrong.jpg";
        // 这个绘制的文字,前面的 text 150,150 必须要存在,
        String text = "text 150,150 'what fuck'";

        ImgOperateUtil.waterMark(path, "src/test/resources/img/water_font.jpg", text, "宋体", 25, "southeast", "#FF0000");
    }

}
