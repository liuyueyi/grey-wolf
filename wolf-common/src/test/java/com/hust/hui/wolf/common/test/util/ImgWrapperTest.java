package com.hust.hui.wolf.common.test.util;

import com.hust.hui.wolf.common.util.img.ImgWrapper;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;

/**
 * Created by yihui on 16/11/1.
 */
public class ImgWrapperTest {

    @Test
    public void testRotate() {
        // 本地文件读写
        try {
            String origin = "src/test/resources/img/wrong.jpg";
            ImgWrapper.of(origin).rotate(180).toFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // url图片旋转
        try {
            String url = "http://s11.mogucdn.com/p2/160928/27703941_3baa9a6ab56jad99j5i9h4he0e538_640x960.jpg";
            ImgWrapper.of(URI.create(url)).rotate(180).toFile();
        } catch (Exception e) {
            e.printStackTrace();
        }


        // stream 图片旋转
        try {
            String origin = "src/test/resources/img/wrong.jpg";
            InputStream inputStream = new FileInputStream(origin);
            ImgWrapper.of(inputStream).rotate(180).outputFormat("jpg").toFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void waterTest() {
        String origin = "src/test/resources/img/wrong.jpg";
        String water = "src/test/resources/img/water.png";
        try {
            ImgWrapper.of(origin).ofUser(100).water(water, 100, 100).toFile("src/test/resources/img/water_out.jpg");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFlip() {
        String origin = "src/test/resources/img/test.jpg";
        try {
            ImgWrapper.of(origin).ofUser(100).flip().toFile("src/test/resources/img/flip_out.jpg");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            ImgWrapper.of(origin).ofUser(100).flop().toFile("src/test/resources/img/flop_out.jpg");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 裁图后, 翻转, 加个白边
        try {
            ImgWrapper.of(origin)
                    .ofUser(100)
                    .crop(20, 20, 600, 900)
                    .flip()
                    .board(10, 10, "#ff0000")
                    .toFile("src/test/resources/img/cut_flip_out.jpg");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
