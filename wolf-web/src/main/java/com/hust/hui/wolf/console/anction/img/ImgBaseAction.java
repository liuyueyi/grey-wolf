package com.hust.hui.wolf.console.anction.img;

import com.hust.hui.wolf.common.util.file.FileUtil;
import com.hust.hui.wolf.console.anction.base.Action;

/**
 * Created by yihui on 2017/1/8.
 */
public class ImgBaseAction implements Action {

    public String getPath() {
        // fixme 参数可以使用userId 来代替
        String path = FileUtil.createTempDir(0);
        return path;
    }


    public String getName(String name) {
        String tmpName = System.nanoTime() + "_" + name;
        return tmpName;
    }
}
