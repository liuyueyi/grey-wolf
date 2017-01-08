package com.hust.hui.wolf.common.util.img;

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
}
