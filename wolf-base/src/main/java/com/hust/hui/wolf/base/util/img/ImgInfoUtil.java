package com.hust.hui.wolf.base.util.img;

import com.hust.hui.wolf.base.domain.model.entity.ImageInfo;
import org.im4java.core.Info;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 图片信息获取
 * <p/>
 * Created by yihui on 16/10/31.
 */
public class ImgInfoUtil {

    /**
     * 获取文件大小
     *
     * @param imagePath
     * @return
     */
    public static int getSize(String imagePath) {
        int size = 0;
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(imagePath);
            size = inputStream.available();
            inputStream.close();
            inputStream = null;
        } catch (FileNotFoundException e) {
            size = 0;
            System.out.println("文件未找到!");
        } catch (IOException e) {
            size = 0;
            System.out.println("读取文件大小错误!");
        } finally {
            // 可能异常为关闭输入流,所以需要关闭输入流
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    System.out.println("关闭文件读入流异常");
                }
            }
        }
        return size;
    }


    /**
     * 获得图片的宽度
     *
     * @param imagePath 文件路径
     * @return 图片宽度
     */
    public static ImageInfo getImgInfo(String imagePath) {
        try {
            Info info = new Info(imagePath,true);
            ImageInfo imageInfo = new ImageInfo();
            imageInfo.setFleext(info.getImageFormat());
            imageInfo.setWidth(info.getImageWidth());
            imageInfo.setHeight(info.getImageHeight());
            return imageInfo;
        } catch (Exception e) {
            return null;
        }
    }
}
