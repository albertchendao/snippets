package org.example.order.helper;

import lombok.extern.slf4j.Slf4j;
import org.example.common.enumeration.RespCode;
import org.example.common.json.JsonHelper;
import org.example.common.vo.RespVo;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
public class HttpContextHelper {

    private static RespVo SUCCESS_BODY = new RespVo<>();

    static {
        SUCCESS_BODY.setCode(RespCode.SUCCESS.getCode());
        SUCCESS_BODY.setContent(RespCode.SUCCESS.getMessage());
    }

    /******************************************************* 构建 RespVo *****************************************************************/
    //注意 Controller 中如果直接返回 RespVo<T>, 生成的文档中 T 中想信息会丢失，参考测试用例 DemoControllerTest#page  DemoControllerTest#page2

    /**
     * 构建返回对下
     *
     * @param code    接口状态码
     * @param message 接口状态信息
     * @param data    接口数据
     */
    private static <T> RespVo<T> buildResponse(int code, String message, T data) {
        RespVo<T> result = new RespVo<>();
        result.setCode(code);
        result.setContent(message);
        result.setData(data);
        return result;
    }

    /**
     * 返回成功, 无接口数据
     */
    public static <T> RespVo<T> success() {
        return SUCCESS_BODY;
    }

    /**
     * 返回成功, 附带接口数据
     */
    public static <T> RespVo<T> success(T data) {
        RespVo<T> result = new RespVo<>();
        result.setCode(SUCCESS_BODY.getCode());
        result.setContent(SUCCESS_BODY.getContent());
        result.setData(data);
        return result;
    }


    /**
     * 失败返回
     *
     * @param errorCode 失败类型, 使用 RespCode 枚举值
     */
    public static <T> RespVo<T> error(RespCode errorCode) {
        return buildResponse(errorCode.getCode(), errorCode.getMessage(), null);
    }

    /**
     * 失败返回，自定义错误信息
     *
     * @param errorCode 失败类型, 使用 RespCode 枚举值
     * @param message   自定义错误信息
     */
    public static <T> RespVo<T> error(RespCode errorCode, String message) {
        return buildResponse(errorCode.getCode(), message, null);
    }

    /******************************************************* 构建 RespVo *****************************************************************/

    /**
     * 返回成功, 无接口数据
     */
    public static <T> ResponseEntity<RespVo<T>> successEntity() {
        return ResponseEntity.ok(success());
    }

    /**
     * 返回成功, 附带接口数据
     */
    public static <T> ResponseEntity<RespVo<T>> successEntity(T data) {
        return ResponseEntity.ok(success(data));
    }

    /**
     * 失败返回
     *
     * @param errorCode 失败类型, 使用 RespCode 枚举值
     */
    public static <T> ResponseEntity<RespVo<T>> errorEntity(RespCode errorCode) {
        return ResponseEntity.ok(buildResponse(errorCode.getCode(), errorCode.getMessage(), null));
    }

    /**
     * 失败返回，自定义错误信息
     *
     * @param errorCode 失败类型, 使用 RespCode 枚举值
     * @param message   自定义错误信息
     */
    public static <T> ResponseEntity<RespVo<T>> errorEntity(RespCode errorCode, String message) {
        return ResponseEntity.ok(buildResponse(errorCode.getCode(), message, null));
    }

    public static void response(HttpServletResponse response, RespCode errorCode) throws IOException {
        response.setContentType("application/json");
        response.getWriter().print(JsonHelper.toJSONString(error(errorCode)));
    }

    /**
     * 获取请求的客户端的 IP 地址。若应用服务器前端配有反向代理的 Web 服务器，
     * 需要在 Web 服务器中将客户端原始请求的 IP 地址加入到 HTTP header 中。
     * 详见
     */
//    public static String getRemoteIp(HttpServletRequest request ) {
//        for (String key : PROXY_REMOTE_IP_ADDRESS) {
//            String ip = request.getHeader(key);
//            if (ip != null && ip.trim().length() > 0) {
//                return getRemoteIpFromForward(ip.trim());
//            }
//        }
//        return request.getRemoteHost();
//    }
    public static String getRemoteIp(HttpServletRequest request) {
        String ipAddress;
        //ipAddress = request.getRemoteAddr();
        ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("X-Real-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    log.debug("remote ip get failed !");
                }
                ipAddress = inet != null ? inet.getHostAddress() : null;
            }
        }

        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > 15) { //"***.***.***.***".length() = 15
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

//    /**
//     * Web 服务器反向代理中用于存放客户端原始 IP 地址的 Http header 名字，
//     * 若新增其他的需要增加或者修改其中的值。
//     */
//    private static final String[] PROXY_REMOTE_IP_ADDRESS = { "X-Forwarded-For", "X-Real-IP" };
//
//    /**
//     * 从 HTTP Header 中截取客户端连接 IP 地址。如果经过多次反向代理，
//     * 在请求头中获得的是以“,”分隔 IP 地址链，第一段为客户端 IP 地址。
//     * @param xForwardIp 从 HTTP 请求头中获取转发过来的 IP 地址链
//     * @return 客户端源 IP 地址
//     */
//    private static String getRemoteIpFromForward( String xForwardIp) {
//        int commaOffset = xForwardIp.indexOf(',');
//        if (commaOffset < 0) {
//            return xForwardIp;
//        }
//        return xForwardIp.substring(0 , commaOffset);
//    }
}
