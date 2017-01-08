package com.hust.hui.wolf.console.anction.img.operate;

import com.hust.hui.wolf.console.anction.base.Request;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

/**
 * Created by yihui on 2016/11/3.
 */
@Data
public class ImgOperateRequest implements Request {

    private String path;

    /**
     * 放大缩小的倍数
     */
    private Double ratio;

    private Integer x;

    private Integer y;

    private Integer w;

    private Integer h;

    /**
     * 旋转的角度
     */
    private Double angle;

    /**
     * 通常与缩放参数组合使用
     * 精度处理, 1 - 100
     */
    private Integer quality;

    /**
     * 操作执行顺序
     */
    private String operateOrder;


    /**
     * fixme 不使用注解扫描,主要是因为不同的符合操作,要求的参数不一样
     *
     * @throws IllegalArgumentException
     */
    public void validateOrFail() throws IllegalArgumentException {
        if (StringUtils.isBlank(operateOrder) || StringUtils.isBlank(path)) {
            throw new IllegalArgumentException("operateOrder & path为空");
        }

        if (operateOrder.contains(OperateOrder.CUT)) {
            // 包含裁剪, 则x,y,w,h 四个参数必须合法
            if (x == null || x < 0 || x > 2560) {
                throw new IllegalArgumentException("允许最大x坐标2560px");
            }

            if (y == null || y < 0 || y > 1600) {
                throw new IllegalArgumentException("允许最大y坐标1600px");
            }

            if (w == null || w < 0 || w > 2560) {
                throw new IllegalArgumentException("允许最大宽度2560px");
            }

            if (h == null || h < 0 || h > 1600) {
                throw new IllegalArgumentException("允许最大高度1600px");
            }
        }

        if (quality != null && (quality < 0 || quality > 100)) {
            throw new IllegalArgumentException("精度允许范围[1-100]");
        }

        // 压缩, 提供两种方式, 根据等比压缩; 根据宽高进行压缩
        if (operateOrder.contains(OperateOrder.SCALE) && (ratio == null || w == null || h == null)) {
            throw new IllegalArgumentException("放大缩小参数不能为空");
        }

        if (operateOrder.contains(OperateOrder.ROTATE) && angle == null) {
            throw new IllegalArgumentException("旋转的角度不能为空");
        }
    }
}
