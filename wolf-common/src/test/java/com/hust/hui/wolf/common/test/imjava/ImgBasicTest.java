package com.hust.hui.wolf.common.test.imjava;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by yihui on 2016/11/3.
 */
public class ImgBasicTest {

    private static final Logger logger = LoggerFactory.getLogger(ImgBasicTest.class);

    @Test
    public void testWater() {
        String origin = "src/test/resources/img/wrong.jpg";
        String water = "src/test/resources/img/water.png";
        String out = "src/test/resources/img/water_out.jpg";

        try {
            IMOperation op = new IMOperation();
            op.dissolve(100);
            op.geometry(null, null, 100, 100).composite();
            op.addImage(origin);
            op.addImage(water);
            op.addImage(out);
            ConvertCmd convert = new ConvertCmd();
            convert.run(op);
        } catch (IOException e) {
            logger.error("file read error!, e: {}", e);
        } catch (InterruptedException e) {
            logger.error("interrupt exception! e: {}", e);
        } catch (IM4JavaException e) {
            logger.error("im4java exception! e: {}", e);
        }
    }


    @Test
    public void testScale() {
        String origin = "src/test/resources/img/wrong.jpg";
        String out = "src/test/resources/img/resize_out.png";
        try {
            IMOperation op = new IMOperation();
            op.addRawArgs("-resize", "%50");
            op.addImage(origin);
            op.addImage(out);
            ConvertCmd convertCmd = new ConvertCmd();
            convertCmd.run(op);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
