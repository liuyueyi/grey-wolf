package com.hust.hui.wolf.common.util.img;

import com.hust.hui.wolf.common.exception.ImgOperateException;
import com.hust.hui.wolf.common.util.file.FileUtil;
import com.hust.hui.wolf.common.util.http.HttpUtil;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * 图片处理工具类
 * <p/>
 * Created by yihui on 16/11/1.
 */
public class ImgWrapper {

    public static Builder<String> of(String file) {
        checkForNull(file, "Cannot specify null for input file.");
        return Builder.ofString(file);
    }

    public static Builder<URI> of(URI uri) {
        checkForNull(uri, "Cannot specify null for input uri.");
        return Builder.ofUrl(uri);
    }

    public static Builder<InputStream> of(InputStream inputStream) {
        checkForNull(inputStream, "Cannot specify null for InputStream.");
        return Builder.ofStream(inputStream);
    }


    private static void checkForNull(Object o, String message) {
        if (o == null) {
            throw new NullPointerException(message);
        }
    }


    public static class Builder<T> {
        private T sourceFile;

        /**
         * 临时文件的保存文件夹
         */
        private String tempPath;

        /**
         * 临时文件名
         */
        private String tempFilename;

        /**
         * 图片类型 JPEG, PNG, GIF ...
         */
        private String outputFormat;

        /**
         * 登录的用户id,用于生成临时目录
         */
        private long userId;

        private List<Args> operates = new ArrayList<>();

        public Builder(T sourceFile) {
            this.sourceFile = sourceFile;
        }


        private static Builder<String> ofString(String str) {
            return new Builder<String>(str);
        }


        private static Builder<URI> ofUrl(URI url) {
            return new Builder<URI>(url);
        }

        private static Builder<InputStream> ofStream(InputStream stream) {
            return new Builder<InputStream>(stream);
        }


        /**
         * 操作必须要有用户id
         *
         * @param userId
         * @return
         */
        public Builder<T> ofUser(long userId) {
            this.userId = userId;
            return this;
        }

        /**
         * 设置输出的文件格式
         *
         * @param format
         * @return
         */
        public Builder<T> outputFormat(String format) {
            this.outputFormat = format;
            return this;
        }


        private void updateOutputFormat(String originType) {
            if (this.outputFormat != null || originType == null) {
                return;
            }

            int index = originType.lastIndexOf(".");
            if (index <= 0) {
                return;
            }
            this.outputFormat = originType.substring(index + 1);
        }

        /**
         * 缩放
         *
         * @param width
         * @param height
         * @return
         */
        public Builder<T> scale(Integer width, Integer height) {
            Args args = new Args();
            args.setOperate(Operate.SCALE);
            args.setWidth(width);
            args.setHeight(height);
            operates.add(args);
            return this;
        }


        /**
         * 裁剪
         *
         * @param x
         * @param y
         * @param width
         * @param height
         * @return
         */
        public Builder<T> crop(int x, int y, int width, int height) {
            Args args = new Args();
            args.setOperate(Operate.CROP);
            args.setWidth(width);
            args.setHeight(height);
            args.setX(x);
            args.setY(y);
            operates.add(args);
            return this;
        }

        /**
         * 缩放
         *
         * @param width
         * @param height
         * @return
         */
        public Builder<T> scale(Integer width, Integer height, Integer quality) {
            Args args = new Args();
            args.setOperate(Operate.SCALE);
            args.setWidth(width);
            args.setHeight(height);
            args.setQuality(quality);
            operates.add(args);
            return this;
        }

        /**
         * 按照比例进行缩放
         *
         * @param radio 1.0 表示不缩放, 0.5 缩放为一半
         * @return
         */
        public Builder<T> scale(Double radio, Integer quality) {
            Args args = new Args();
            args.setOperate(Operate.SCALE);
            args.setRadio(radio);
            args.setQuality(quality);
            operates.add(args);
            return this;
        }


        /**
         * 旋转
         *
         * @param rotate
         * @return
         */
        public Builder<T> rotate(double rotate) {
            Args args = new Args();
            args.setOperate(Operate.ROTATE);
            args.setRotate(rotate);
            operates.add(args);
            return this;
        }

        /**
         * 上下翻转
         *
         * @return
         */
        public Builder<T> flip() {
            Args args = new Args();
            args.setOperate(Operate.FLIP);
            operates.add(args);
            return this;
        }

        /**
         * 左右翻转,即镜像
         *
         * @return
         */
        public Builder<T> flop() {
            Args args = new Args();
            args.setOperate(Operate.FLOP);
            operates.add(args);
            return this;
        }

        /**
         * 添加边框
         *
         * @param width  边框的宽
         * @param height 边框的高
         * @param color  边框的填充色
         * @return
         */
        public Builder<T> board(Integer width, Integer height, String color) {
            Args args = new Args();
            args.setOperate(Operate.BOARD);
            args.setWidth(width);
            args.setHeight(height);
            args.setColor(color);
            operates.add(args);
            return this;
        }

        /**
         * 添加水印
         *
         * @param water 水印的源图片 (默认为png格式)
         * @param x     添加到目标图片的x坐标
         * @param y     添加到目标图片的y坐标
         * @param <U>
         * @return
         */
        public <U> Builder<T> water(U water, int x, int y) {
            return water(water, "png", x, y);
        }

        /**
         * 添加水印
         *
         * @param water
         * @param imgType 水印图片的类型; 当传入的为inputStream时, 此参数才有意义
         * @param x
         * @param y
         * @param <U>
         * @return
         */
        public <U> Builder<T> water(U water, String imgType, int x, int y) {
            Args<U> args = new Args<>();
            args.setOperate(Operate.WATER);
            args.setX(x);
            args.setY(y);
            args.setWater(water);
            args.setUserId(this.userId);
            args.setWaterImgType(imgType);
            operates.add(args);
            return this;
        }


        /**
         * 执行图片处理, 并保存文件为: 源文件_out.jpg （类型由输出的图片类型决定）
         *
         * @return
         * @throws Exception
         */
        public String toFile() throws Exception {
            return toFile(null);
        }


        /**
         * 执行图片处理,并将结果保存为指定文件名的file
         *
         * @param outputFilename 若为null, 则输出文件为 源文件_out.jpg 这种格式
         * @return
         * @throws Exception
         */
        public String toFile(String outputFilename) throws Exception {
            if (CollectionUtils.isEmpty(operates)) {
                throw new ImgOperateException("operate function is empty! operates: " + operates);
            }

            /** 获取原始的图片路径 */
            String sourceFilename = createFile();
            /** 拼装生成的临时图片, 在原图片后添加 _out.jpg */
            if (outputFilename == null) {
                outputFilename = this.tempPath + "/" + this.tempFilename + "_out." + this.outputFormat;
            }

            /** 执行图片的操作 */
            if (ImgBaseOperateUtil.operate(operates, sourceFilename, outputFilename)) {
                return outputFilename;
            } else {
                return null;
            }
        }

        /**
         * 执行图片操作,并输出字节流
         *
         * @return
         * @throws Exception
         */
        public InputStream asStrem() throws Exception {
            if (CollectionUtils.isEmpty(operates)) {
                throw new ImgOperateException("operate function is empty!");
            }

            String newFilename = this.toFile(null);
            if (newFilename == null) {
                return null;
            }

            return new FileInputStream(new File(newFilename));
        }


        private String createFile() throws IOException {
            if (this.sourceFile instanceof String) {
                /** 生成的文件在源文件目录下 */
                int index = ((String) this.sourceFile).lastIndexOf("/");
                this.tempPath = index <= 0 ? "" : ((String) this.sourceFile).substring(0, index);
                this.tempFilename = index <= 0 ? (String) this.sourceFile : ((String) this.sourceFile).substring(index + 1);
                updateOutputFormat((String) this.sourceFile);
                return (String) this.sourceFile;
            } else if (this.sourceFile instanceof URI) {
                /** 源文件和生成的文件都保存在临时目录下 */
                this.tempPath = FileUtil.createTempDir(this.userId);
                String urlPath = ((URI) this.sourceFile).getPath();
                this.tempFilename = userId + "_" + urlPath.substring(urlPath.lastIndexOf("/") + 1);
                HttpUtil.downImg((URI) this.sourceFile, this.tempPath, this.tempFilename);
                updateOutputFormat(this.tempFilename);
            } else if (this.sourceFile instanceof InputStream) {
                /** 源文件和生成的文件都保存在临时目录下, 命令规则: userId_时间戳.jpg */
                this.tempPath = FileUtil.createTempDir(this.userId);
                this.tempFilename = userId + "_" + System.currentTimeMillis() + "." + outputFormat;
                FileUtil.saveFile((InputStream) this.sourceFile, this.tempPath, this.tempFilename);
            } else {
                throw new FileNotFoundException();
            }

            return this.tempPath + "/" + this.tempFilename;
        }


        @Data
        public static class Args<T> {

            /**
             * 用户id
             */
            private long userId;

            /**
             * 操作类型
             */
            private Operate operate;

            /**
             * 裁剪宽; 缩放宽
             */
            private Integer width;
            /**
             * 高
             */
            private Integer height;
            /**
             * 裁剪时,起始 x
             */
            private Integer x;
            /**
             * 裁剪时,起始y
             */
            private Integer y;
            /**
             * 旋转角度
             */
            private Double rotate;

            /**
             * 颜色 (添加边框中的颜色; 去除图片中某颜色)
             */
            private String color;

            /**
             * 水印图片, 可以为图片名, uri, 或者inputstream
             */
            private T water;

            /**
             * 图片精度 0 - 100
             */
            private Integer quality;

            /**
             * 缩放比例  0-1
             */
            private Double radio;

            /**
             * 水印图片的类型
             */
            private String waterImgType;

            public boolean valid() {
                switch (operate) {
                    case CROP:
                        return width != null && height != null && x != null && y != null;
                    case SCALE:
                        return width != null && height != null;
                    case ROTATE:
                        return rotate != null;
                    case WATER:
                        return water != null;
                    case BOARD:
                        if (width == null) {
                            width = 3;
                        }
                        if (height == null) {
                            height = 3;
                        }
                        if (color == null) {
                            color = "#ffffff";
                        }
                    case FLIP:
                    case FLOP:
                        return true;
                    default:
                        return false;
                }
            }

            /**
             * 获取水印图片的路径
             *
             * @return
             */
            public String getWaterFilename() throws IOException {
                String tempPath = null;
                String tempFilename;
                if (this.water instanceof String) {
                    /** 生成的文件在源文件目录下 */
                    return (String) this.water;
                } else if (this.water instanceof URI) {
                    /** 源文件和生成的文件都保存在临时目录下 */
                    tempPath = FileUtil.createTempDir(this.userId);
                    String urlPath = ((URI) this.water).getPath();
                    tempFilename = userId + "_water_" + urlPath.substring(urlPath.lastIndexOf("/") + 1);
                    HttpUtil.downImg((URI) water, tempPath, tempFilename);
                    return tempFilename;
                } else if (this.water instanceof InputStream) {
                    /** 源文件和生成的文件都保存在临时目录下, 命令规则: userId_时间戳.jpg */
                    tempPath = FileUtil.createTempDir(this.userId);
                    tempFilename = userId + "_water_" + System.currentTimeMillis() + "." + waterImgType;
                    FileUtil.saveFile((InputStream) water, tempPath, tempFilename);
                    return tempFilename;
                } else {
                    throw new FileNotFoundException();
                }
            }
        }


        public static enum Operate {
            /**
             * 裁剪
             */
            CROP,
            /**
             * 缩放
             */
            SCALE,
            /**
             * 旋转
             */
            ROTATE,
            /**
             * 水印
             */
            WATER,
            /**
             * 上下翻转
             */
            FLIP,
            /**
             * 水平翻转
             */
            FLOP,
            /**
             * 添加边框
             */
            BOARD;
        }
    }
}
