package com.hust.hui.wolf.base.util.file;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by yihui on 16/11/1.
 */
public class FileUtil {
    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 将字节流保存到文件中
     *
     * @param stream
     * @param filename
     * @return
     */
    public static boolean saveFile(InputStream stream, String path, String filename) throws FileNotFoundException {
        if (!StringUtils.isBlank(path)) {
            // 创建文件夹
            mkDir(new File(path));
        }
        filename = path + "/" + filename;


        BufferedInputStream inputStream = null;
        BufferedOutputStream outputStream = null;
        try {
            inputStream = new BufferedInputStream(stream);
            File file = new File(filename);
            if (!file.exists() && !file.createNewFile()) {
                throw new FileNotFoundException();
            }
            outputStream = new BufferedOutputStream(new FileOutputStream(file));
            int len = 1024;
            byte[] b = new byte[len];
            while ((len = inputStream.read(b)) != -1) {
                outputStream.write(b, 0, len);
            }
            outputStream.flush();
            inputStream.close();
            return true;
        } catch (Exception e) {
            logger.error("save stream into file error! filename: {} e: {}", filename, e);
            return false;
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (Exception e) {
                logger.error("close stream error! e: {}", e);
            }
        }
    }


    /**
     * 递归创建文件夹
     *
     * @param file 由目录创建的file对象
     * @throws FileNotFoundException
     */
    public static void mkDir(File file) throws FileNotFoundException {
        if (file.getParentFile().exists()) {
            if (!file.exists() && !file.mkdir()) {
                throw new FileNotFoundException();
            }
        } else {
            mkDir(file.getParentFile());
            if (!file.exists() && !file.mkdir()) {
                throw new FileNotFoundException();
            }
        }
    }

    /**
     * 生成临时目录: /tmp/日期/用户id % 20/ + 文件 (这样会导致大量的目录生成)
     *
     * @param userId 用户id
     * @return
     */
    public static String createTempDir(long userId) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(System.currentTimeMillis()));
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        return "/tmp/" + format.format(calendar.getTime()) + "/" + (userId % 20);
    }
}
