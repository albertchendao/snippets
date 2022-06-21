package org.example.spring.boot2.resp;

/**
 * 构建结果
 *
 * @author Albert
 * @version 1.0
 * @since 2021/12/30 18:22
 */
public class ResultRespFactory {

    public static final int SYS_ERROR = 500;
    public static final int INVALID_PARAM = 400;

    private ResultRespFactory() {
    }

    private static <T> ResultResp<T> build(int code, String msg, T data) {
        ResultResp<T> result = new ResultResp<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static <T> ResultResp<T> success(T data) {
        return build(0, "ok", data);
    }


    public static <T> ResultResp<T> error(int code, String msg) {
        return build(code, msg, null);
    }

}