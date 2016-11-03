package com.hust.hui.wolf.base.tool;

import com.hust.hui.wolf.base.util.file.FileUtil;
import com.hust.hui.wolf.base.util.http.HttpUtil;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;

/**
 * Created by yihui on 16/11/1.
 */
public class HttpUtilTest {

    @Test
    public void testDownImg() {
        String url = "http://s11.mogucdn.com/p2/160928/27703941_3baa9a6ab56jad99j5i9h4he0e538_640x960.jpg";
        String tempSave = "temp.jpg";
        try {
            HttpUtil.downImg(url, null, tempSave);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testURL() throws MalformedURLException {
        String path = "http://s11.mogucdn.com/p2/160928/27703941_3baa9a6ab56jad99j5i9h4he0e538_640x960.jpg";
        URI url = URI.create(path);
        System.out.println(url.getPath());
    }

    @Test
    public void fileSave() throws FileNotFoundException {
        String saveTemp = "xxx2.jpg";
        InputStream inputStream = new FileInputStream("temp.jpg");
        FileUtil.saveFile(inputStream, "/tmp/110/", saveTemp);
    }

}
