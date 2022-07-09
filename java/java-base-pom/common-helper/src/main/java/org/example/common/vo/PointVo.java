package org.example.common.vo;

import lombok.Data;

/**
 * 经纬度位置信息
 */
@Data
public class PointVo {
    /**
     * 经度
     */
    private double lng;
    /**
     * 纬度
     */
    private double lat;

    public PointVo() {
    }

    public PointVo(double lng, double lat) {
        this.lng = lng;
        this.lat = lat;
    }
}
