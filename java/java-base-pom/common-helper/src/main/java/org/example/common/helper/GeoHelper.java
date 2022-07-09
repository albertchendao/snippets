package org.example.common.helper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.common.vo.PointVo;

/**
 * 经纬度坐标转换
 * <p>
 * WGS84:国际坐标系,为一种大地坐标系,也是目前广泛使用的GPS全球卫星定位系统使用的坐标系.
 * GCJ02:火星坐标系,是由中国国家测绘局制订的地理信息系统的坐标系统.由WGS84坐标系经加密后的坐标系.
 * BD09:为百度坐标系,在GCJ02坐标系基础上再次加密.其中bd09ll表示百度经纬度坐标,bd09mc表示百度墨卡托米制坐标
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GeoHelper {

    public static final String BAIDU_LBS_TYPE = "bd09ll";

    public static double pi = 3.1415926535897932384626;
    public static double a = 6378245.0;
    public static double ee = 0.00669342162296594323;

    /**
     * 国际坐标 to 火星坐标系 (GCJ-02)
     *
     * @param lat 纬度
     * @param lng 经度
     */
    public static PointVo gps84_To_gcj02(double lat, double lng) {
        if (outOfChina(lat, lng)) {
            return null;
        }
        double dLat = transformLat(lng - 105.0, lat - 35.0);
        double dLon = transformLon(lng - 105.0, lat - 35.0);
        double radLat = lat / 180.0 * pi;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
        double mgLat = lat + dLat;
        double mgLon = lng + dLon;
        return new PointVo(mgLon, mgLat);
    }

    /**
     * 火星坐标系 (GCJ-02) to 国际坐标
     *
     * @param lat 纬度
     * @param lng 经度
     *
     */
    public static PointVo gcj02_To_gps84(double lat, double lng) {
        PointVo gps = transform(lat, lng);
        double lontitude = lng * 2 - gps.getLng();
        double latitude = lat * 2 - gps.getLat();
        return new PointVo(lontitude, latitude);
    }

    /**
     * 火星坐标系 (GCJ-02) to 百度坐标系 (BD-09) 的转换算法
     *
     * @param gg_lat 纬度
     * @param gg_lng 经度
     */
    public static PointVo gcj02_To_bd09(double gg_lat, double gg_lng) {
        double x = gg_lng, y = gg_lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * pi);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * pi);
        double bd_lon = z * Math.cos(theta) + 0.0065;
        double bd_lat = z * Math.sin(theta) + 0.006;
        return new PointVo(bd_lon, bd_lat);
    }

    /**
     * 百度坐标系 (BD-09) to 火星坐标系 (GCJ-02) 的转换算法
     *
     * @param bd_lat 纬度
     * @param bd_lng 经度
     */
    public static PointVo bd09_To_gcj02(double bd_lat, double bd_lng) {
        double x = bd_lng - 0.0065, y = bd_lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * pi);
        double gg_lon = z * Math.cos(theta);
        double gg_lat = z * Math.sin(theta);
        return new PointVo(gg_lon, gg_lat);
    }

    /**
     * 百度坐标系 (BD-09) to 国际坐标
     *
     * @param bd_lat 纬度
     * @param bd_lng 经度
     */
    public static PointVo bd09_To_gps84(double bd_lat, double bd_lng) {

        PointVo gcj02 = bd09_To_gcj02(bd_lat, bd_lng);
        PointVo map84 = gcj02_To_gps84(gcj02.getLat(), gcj02.getLng());
        return map84;

    }

    public static boolean outOfChina(double lat, double lng) {
        if (lng < 72.004 || lng > 137.8347)
            return true;
        if (lat < 0.8293 || lat > 55.8271)
            return true;
        return false;
    }

    public static PointVo transform(double lat, double lng) {
        if (outOfChina(lat, lng)) {
            return new PointVo(lng, lat);
        }
        double dLat = transformLat(lng - 105.0, lat - 35.0);
        double dLon = transformLon(lng - 105.0, lat - 35.0);
        double radLat = lat / 180.0 * pi;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
        double mgLat = lat + dLat;
        double mgLon = lng + dLon;
        return new PointVo(mgLon, mgLat);
    }

    public static double transformLat(double x, double y) {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y
                + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * pi) + 40.0 * Math.sin(y / 3.0 * pi)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * pi) + 320 * Math.sin(y * pi / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    public static double transformLon(double x, double y) {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1
                * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * pi) + 40.0 * Math.sin(x / 3.0 * pi)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * pi) + 300.0 * Math.sin(x / 30.0
                * pi)) * 2.0 / 3.0;
        return ret;
    }

    public static void main(String[] args) {
//
//        gcj02 火星坐标系:
//           iOS 地图（其实是高德）
//           Google 地图
//           搜搜、阿里云、高德地图
//        bd09 百度坐标系:
//           当然只有百度地图
//        WGS84 坐标系:
//           国际标准,谷歌国外地图、osm地图等国外的地图一般都是这个
//
        //人民英雄纪念碑
        PointVo bd = new PointVo(116.404177,39.909652);
        System.out.println("bd :" + bd);
        PointVo gcj = bd09_To_gcj02(bd.getLat(), bd.getLng());
        System.out.println("gd  :" + (new PointVo(116.397679, 39.904618)));//高德地图
        System.out.println("gcj :" + gcj);
    }
}

