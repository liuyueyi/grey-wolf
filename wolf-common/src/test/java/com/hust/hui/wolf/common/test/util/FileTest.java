package com.hust.hui.wolf.common.test.util;

import com.hust.hui.wolf.common.util.file.FileUtil;
import org.junit.Test;

import java.io.File;

/**
 * Created by yihui on 16/11/1.
 */
public class FileTest {

    @Test
    public void testCreateDir() {
        String path = FileUtil.createTempDir(101);
        System.out.println(path);
        File dir = new File(path);

        if (!dir.exists() && !dir.isDirectory()) {
            if (!dir.mkdir()) {
                System.out.println("error!");
            }
        }
    }

}
