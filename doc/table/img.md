# imagemagic + im4java 进行图片处理

> 利用 imagemagic 对图片进行处理，java工程中，使用im4java来操作`imagemagic`的api


## 一、环境
> how to install ?

`linux` 系统，安装之前，需要先安装 `libjpeg`  `libpng`包，否则没法处理jpg和png图片

### 1. 下载依赖包
- 安装jpeg 包
    `wget ftp://223.202.54.10/pub/web/php/libjpeg-6b.tar.gz`
- 安装webp 包
    `wget http://www.imagemagick.org/download/delegates/libwebp-0.5.1.tar.gz`
- 安装png 包
    `wget http://www.imagemagick.org/download/delegates/libpng-1.6.24.tar.gz`
- 安装 graphicsmagick
     `wget http://nchc.dl.sourceforge.net/project/graphicsmagick/graphicsmagick/1.3.22/GraphicsMagick-1.3.22.tar.gz`
- 安装imagemagick
     `wget http://www.imagemagick.org/download/ImageMagick.tar.gz`

### 2. 安装

首先安装lib包，然后在安装 `graphicmagick` or `imagemagick`

安装方式:

```
sudo ./configure
sudo make
sudo make install
```

### 3. 测试

gm 裁图 `gm convert -crop 640x960+0+0 test.jpg output.jpg`

im 裁图  `convert test.jpg -crop 640x960+0+0 output.jpg`

### 4. 安装问题

**linux 安装之后，可能有两个问题**

- imagemagick 依然无法读取png图片

    png包安装完成后，将路径添加到环境变量

    ```
    export CPPFLAGS='-I/usr/local/include'
    export LDFLAGS="-L/usr/local/lib"
    ```

- 执行 convert 提示`linux shared libraries 不包含某个库`

    临时解决方案： `export LD_LIBRARY_PATH=/usr/local/lib:$LD_LIBRARY_PATH`

    一劳永逸的方案：[https://my.oschina.net/guanyue/blog/220264](https://my.oschina.net/guanyue/blog/220264)

         vi /etc/ld.so.conf
         在这个文件里加入：/usr/local/lib 来指明共享库的搜索位置
         然后再执行/sbin/ldconf

## 二、 ImageMagic常用命令

1. 裁图的命令

    `convert test.jpg -crop 360x720+0+0 out.jpg`

    -gravity即指定坐标原点，有northwest：左上角，north：上边中间，northeast：右上角，east：右边中间

    `convert image.png -gravity northeast -crop 100x100+0+0 crop.png`

2. 旋转图片的命令

    `convert test.jpg -rotate 90 out.jpg`

    默认的背景为白色，我们可以指定背景色：

    `convert image.png -backround black -rotate 45 rotate.png`
    `convert image.png -background #000000 -rotate 45 rotate.png`

3. 图片放大缩小的命令

    - resize: 拉伸收缩
    - sample: 与resize的区别在于-sample只进行了采样，没有进行插值，所以用来生成缩略图最合适

    `convert test.jpg -resize 512x512 out.jpg`
    `convert image.png -sample 50% sample.png  `

    如马赛克效果:

    `convert image.png -sample 10% -sample 1000% sample.png  `

4. 旋转并裁图

    `convert test.jpg -rotate 90 -crop 360x720+0+0 out.jpg`

5. 添加文字

    `convert image.png -draw "text 0,20 'some text'" newimage.png`

    `convert source.jpg -font xxx.ttf -fill red -pointsize 48 -annotate +50+50 @text.txt result.jpg  `

6. 格式转换

    `convert image.png -define png:format=png32 newimage.png  `
    `convert image.png image.jpg  `

7. 拼接

    横向拼接（+append），下对齐（-gravity south）：
    `convert image1.png image2.png image3.png -gravity south +append result.png `

    纵向拼接（-append），右对齐（-gravity east）：
    `convert image1.png image2.png image3.png -gravity east -append result.png`

8. 图片信息

    `identify test.png`

    获取宽高 `identify -format "%wx%h" image.png  `

## 三、编码实测

使用`im4java`对 `imagemagic`进行调用， im4java 其实最终是生成cmd命令，系统调用实现，`jmagic`是一个使用jni的方式进行调用的开源包，根据网上说法是，放在tomcat，过一段时间会crash，这里没有进行实测，直接选取了 `im4java`


```java
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
```

单测可以参考  `com.hust.hui.wolf.base.tool.ImageUtilTest`