package com.hust.hui.wolf.base.util.img;

import org.im4java.core.CompositeCmd;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;

import java.io.IOException;

/**
 * 图片裁剪\旋转\拉伸\加水印
 * Created by yihui on 16/10/31.
 */
public class ImgOperateUtil {

    /**
     * 裁剪图片
     *
     * @param imagePath 源图片路径
     * @param outPath   处理后图片路径
     * @param x         起始X坐标
     * @param y         起始Y坐标
     * @param width     裁剪宽度
     * @param height    裁剪高度
     * @return 返回true说明裁剪成功, 否则失败
     */
    public static boolean cut(String imagePath, String outPath, int x, int y,
                              int width, int height) {
        boolean flag;
        try {
            IMOperation op = new IMOperation();
            op.addImage(imagePath);
            /** width：裁剪的宽度 * height：裁剪的高度 * x：裁剪的横坐标 * y：裁剪纵坐标 */
            op.crop(width, height, x, y);
            op.addImage(outPath);
            // 传true到构造函数中,则表示使用GraphicMagic, 裁图时,图片大小会变
            ConvertCmd convert = new ConvertCmd();
            convert.run(op);
            flag = true;
        } catch (IOException e) {
            System.out.println("文件读取错误!");
            flag = false;
        } catch (InterruptedException e) {
            flag = false;
        } catch (IM4JavaException e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 图片旋转
     *
     * @param imagePath 源图片路径
     * @param outPath   处理后图片路径
     * @param degree    旋转角度
     */
    public static boolean rotate(String imagePath, String outPath, double degree) {
        try {
            // 1.将角度转换到0-360度之间
            degree = degree % 360;
            if (degree <= 0) {
                degree = 360 + degree;
            }
            IMOperation op = new IMOperation();
            op.addImage(imagePath);
            op.rotate(degree);
            op.addImage(outPath);
            ConvertCmd cmd = new ConvertCmd();
            cmd.run(op);
            return true;
        } catch (IOException e) {
            return false;
        } catch (InterruptedException e) {
            return false;
        } catch (IM4JavaException e) {
            return false;
        }
    }


    /**
     * 旋转并裁图
     *
     * @param imgPath 原始图片
     * @param outPath 输出图片
     * @param degree  旋转角度
     * @param x       起始值 x坐标
     * @param y       起始Y坐标
     * @param width   裁图宽
     * @param height  裁图高
     * @return
     */
    public static boolean rotateAndCut(String imgPath, String outPath, double degree,
                                       int x, int y, int width, int height) {
        try {
            // 1.将角度转换到0-360度之间
            degree = degree % 360;
            if (degree <= 0) {
                degree = 360 + degree;
            }
            IMOperation op = new IMOperation();
            op.addImage(imgPath);
            op.rotate(degree);
            op.crop(width, height, x, y);
            op.addImage(outPath);
            ConvertCmd cmd = new ConvertCmd();
            cmd.run(op);
            return true;
        } catch (IOException e) {
            return false;
        } catch (InterruptedException e) {
            return false;
        } catch (IM4JavaException e) {
            return false;
        }
    }


    /**
     * 根据尺寸缩放图片[等比例缩放:参数height为null,按宽度缩放比例缩放;参数width为null,按高度缩放比例缩放]
     *
     * @param imagePath 源图片路径
     * @param newPath   处理后图片路径
     * @param width     缩放后的图片宽度
     * @param height    缩放后的图片高度
     * @return 返回true说明缩放成功, 否则失败
     */
    public static boolean zoom(String imagePath, String newPath, Integer width,
                               Integer height) {

        boolean flag = false;
        try {
            IMOperation op = new IMOperation();
            op.addImage(imagePath);
            if (width == null) {// 根据高度缩放图片
                op.resize(null, height);
            } else if (height == null) {// 根据宽度缩放图片
                op.resize(width);
            } else {
                op.resize(width, height);
            }
            op.addImage(newPath);
            ConvertCmd convert = new ConvertCmd();
            convert.run(op);
            flag = true;
        } catch (IOException e) {
            System.out.println("文件读取错误!");
            flag = false;
        } catch (InterruptedException e) {
            flag = false;
        } catch (IM4JavaException e) {
            flag = false;
        } finally {

        }
        return flag;
    }

    /**
     * 添加文字水印
     *
     * @param srcPath   原始图片
     * @param destPath  目标图片
     * @param text      文字   "text 5,5 'hello world'", 其中 hello world 为要绘制的内容
     * @param fontType  文字字体  "宋体"
     * @param fontSize  字体大小  18
     * @param gravity   文字位置  "southeast"
     * @param fontColor 文字颜色 "#BCBFC8"
     * @throws Exception
     */
    public static boolean waterMark(String srcPath,
                                    String destPath,
                                    String text,
                                    String fontType,
                                    int fontSize,
                                    String gravity,
                                    String fontColor) {
        IMOperation op = new IMOperation();
        op.font(fontType).gravity(gravity).pointsize(fontSize).fill(fontColor)
                .draw(text);
        op.addImage();
        op.addImage();
        ConvertCmd convert = new ConvertCmd();
        try {
            convert.run(op, srcPath, destPath);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        } catch (IM4JavaException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 图片水印
     *
     * @param srcImagePath   源图片
     * @param waterImagePath 水印
     * @param destImagePath  生成图片
     * @param gravity        图片位置
     * @param dissolve       水印透明度
     */
    public static boolean waterMark(String waterImagePath,
                                    String srcImagePath,
                                    String destImagePath,
                                    String gravity,
                                    int dissolve) {
        IMOperation op = new IMOperation();
        op.gravity(gravity);
        op.dissolve(dissolve);
        op.addImage(waterImagePath);
        op.addImage(srcImagePath);
        op.addImage(destImagePath);
        CompositeCmd cmd = new CompositeCmd();
        try {
            cmd.run(op);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        } catch (IM4JavaException e) {
            e.printStackTrace();
            return false;
        }
    }
}
