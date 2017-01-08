package com.hust.hui.wolf.common.util.img;

import com.hust.hui.wolf.common.exception.ImgOperateException;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * Created by yihui on 16/11/2.
 */
public class ImgBaseOperateUtil {

    private static final Logger logger = LoggerFactory.getLogger(ImgBaseOperateUtil.class);

    /**
     * 执行图片的复合操作
     *
     * @param operates
     * @param sourceFilename 原始图片名
     * @param outputFilename 生成图片名
     * @return
     * @throws ImgOperateException
     */
    public static boolean operate(List<ImgWrapper.Builder.Args> operates, String sourceFilename, String outputFilename) throws ImgOperateException {
        try {
            IMOperation op = new IMOperation();
            boolean operateTag = false;
            String waterFilename = null;
            for (ImgWrapper.Builder.Args args : operates) {
                if (!args.valid()) {
                    continue;
                }

                if (args.getOperate() == ImgWrapper.Builder.Operate.CROP) {
                    op.crop(args.getWidth(), args.getHeight(), args.getX(), args.getY());
                    operateTag = true;
                } else if (args.getOperate() == ImgWrapper.Builder.Operate.ROTATE) {
                    op.rotate(args.getRotate());
                    operateTag = true;
                } else if (args.getOperate() == ImgWrapper.Builder.Operate.SCALE) {
                    op.scale(args.getWidth(), args.getHeight());
                    operateTag = true;
                } else if (args.getOperate() == ImgWrapper.Builder.Operate.FLIP) {
                    op.flip();
                    operateTag = true;
                } else if (args.getOperate() == ImgWrapper.Builder.Operate.FLOP) {
                    op.flop();
                    operateTag = true;
                } else if (args.getOperate() == ImgWrapper.Builder.Operate.WATER && waterFilename == null) {
                    // 当前只支持一次水印添加
                    op.geometry(args.getWidth(), args.getHeight(), args.getX(), args.getY())
                            .composite();
                    waterFilename = args.getWaterFilename();
                    operateTag = true;
                } else if (args.getOperate() == ImgWrapper.Builder.Operate.BOARD) {
                    op.border(args.getWidth(), args.getHeight()).bordercolor(args.getColor());
                    operateTag = true;
                }
            }

            if (!operateTag) {
                throw new ImgOperateException("operate args is illegal! operates: " + operates);
            }
            op.addImage(sourceFilename);
            if (waterFilename != null) {
                op.addImage(waterFilename);
            }
            op.addImage(outputFilename);
            /** 传true到构造函数中,则表示使用GraphicMagic, 裁图时,图片大小会变 */
            ConvertCmd convert = new ConvertCmd();
            convert.run(op);
        } catch (IOException e) {
            logger.error("file read error!, e: {}", e);
            return false;
        } catch (InterruptedException e) {
            logger.error("interrupt exception! e: {}", e);
            return false;
        } catch (IM4JavaException e) {
            logger.error("im4java exception! e: {}", e);
            return false;
        }
        return true;
    }
}
