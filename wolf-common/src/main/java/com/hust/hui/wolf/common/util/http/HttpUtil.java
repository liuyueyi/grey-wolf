package com.hust.hui.wolf.common.util.http;

import com.hust.hui.wolf.common.util.file.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;

/**
 * 主要用于从远程下载文件
 * <p/>
 * Created by yihui on 16/11/1.
 */
public class HttpUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    /**
     * 从网络上下载图片,并保存到本地
     *
     * @param url
     * @param filename
     * @return
     * @throws IOException
     */
    public static boolean downImg(String url, String path, String filename) throws IOException {
        return downImg(URI.create(url), path, filename);
    }

    /**
     * 从网络上下载图片,并保存到本地
     *
     * @param uri      图片的完整url
     * @param filename 保存的文件名
     * @throws FileNotFoundException
     */
    public static boolean downImg(URI uri, String path, String filename) throws IOException {
        HttpResponse httpResponse;
        try {
            Request request = Request.Get(uri);
            HttpHost httpHost = URIUtils.extractHost(uri);
            if (StringUtils.isNotEmpty(httpHost.getHostName())) {
                request.setHeader("Host", httpHost.getHostName());
            }
            httpResponse = request.execute().returnResponse();
        } catch (Exception e) {
            logger.error("远程请求失败，url=" + uri, e);
            throw new FileNotFoundException();
        }

        int code = httpResponse.getStatusLine().getStatusCode();
        if (code != 200) {
            throw new FileNotFoundException();
        }

        return FileUtil.saveFile(httpResponse.getEntity().getContent(), path, filename);
    }
}
