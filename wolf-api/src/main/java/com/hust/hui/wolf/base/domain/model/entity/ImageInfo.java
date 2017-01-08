package com.hust.hui.wolf.base.domain.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by yihui on 16/10/31.
 */
@Data
@NoArgsConstructor
public class ImageInfo {

    /**
     * 类型
     */
    private String fleext;

    /**
     * 文件大小
     */
    private long filesize;

    /**
     * 图片宽
     */
    private long width;

    /**
     * 图片高
     */
    private long height;
}
