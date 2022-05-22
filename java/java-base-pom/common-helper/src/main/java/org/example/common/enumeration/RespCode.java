package org.example.common.enumeration;

/**
 * 自定义 code
 */
public enum RespCode {

    TIME_OUT(500, 5001, "request time out"),

    API_NOT_EXITS(403, 2201, "api not exits"),
    API_EXPIRATION(403, 2202, "api expiration"),
    API_OUT_OF_FREQUENCY(403, 2203, "api out of frequency"),
    API_OUT_OF_TOTAL_TIMES(403, 2204, "api out of total times"),
    API_OUT_OF_MONEY(403, 2205, "api out of money"),
    API_CONSUME_FAILED(403, 2006, "api consume failed"),

    // 通用错误
    SUCCESS(200, 2000, "success"),
    SERVER_ERROR(500, 5000, "bad server"),
    EXCEL_EXPORT_ERROR(500, 4105, "excel file generation failed"),
    PDF_EXPORT_ERROR(500, 4104, "pdf file generation failed"),
    REGISTER_FAIL(500, 4102, "User register failed"),

    // PS: 不要使用401错误码，因为IE10的BUG导致IE浏览器下无法正确识别401状态码
    HTTP_MEDIA_TYPE_NOT_SUPPORT(406, 4003, "http media type not supported"),
    REQUEST_METHOD_NOT_SUPPORT(405, 4001, "request method not supported"),

    REQUEST_API_NOT_FOUND(404, 4002, "request api not found"),
    DATA_NOT_FOUND(404, 4102, "data not found"),
    APP_DATA_NOT_FOUND(404, 4107, "app data not found"),
    TOPIC_DATA_NOT_FOUND(404, 4109, "topic data not found"),
    APP_TAG_NOT_FOUND(404, 4108, "app tag data not found"),
    USER_NOT_FOUND(404, 4106, "user not found"),

    ACOUNT_EXPIRATION(403, 4101, "acount expiration"),
    NOT_IAPP_ACOUNT(403, 4112, "not iapp account"),
    SESSION_EXPIRATION(403, 4031, "session expiration"),
    AUTHORIZATION_FAIL(403, 4005, "authorization fail"),
    TOKEN_EXPIRATION(403, 4006, "token expiration"),
    FORBIDDEN_ERROR(403, 4007, "access forbidden"),

    WX_ACCOUNT_ERROR(403, 4013, "request wechat account error"),
    WX_FORBIDDEN_ERROR(403, 4014, "wechat access forbidden"),
    WX_BOUND_ERROR(403, 4015, "wechat bound error"),
    WX_MATCH_ERROR(403, 4010, "wechat account does not match"),
    WX_ANOTHER_ACCOUNT_ERROR(403, 4011, "already bound other iApp account"),

    ACCOUNT_FREEZE_ERROR(403, 4202, "account freezed,please retry later!"),
    OPERATING_REJECT_ERROR(403, 4200, "Operating too fast,please retry later!"),
    IP_NOT_PERMISSION(403, 4008, "the ip can't access this resource"),
    TOKEN_INVALID_ERROR(403, 4009, "token invalid"),

    USER_EXIST(400, 4110, "User already exists"),
    PARAMETER_ERROR(400, 4004, "parameter invalid"),
    APP_AMOUNT_NOT_ENOUGH(400, 4103, "app amount not enough"),
    USER_MENU_ILLEGAL(400, 4111, "user's menu illegal"),
    ILLEGAL_DATE_INTERVAL(400, 4211, "user's date interval is illegal!"),
    USER_PANEL_SIZE_LIMIT(400, 4212, "can't add more panel"),
    USER_PANEL_APP_SIZE_LIMIT(400, 4213, "cann't put more app into one panel"),
    APP_PERMISSION_EXPIRATION(400, 4214, "app permission expiration"),
    APP_FAVORITE_SIZE_LIMIT(400, 4215, "can't add more favorite app");


    private int httpCode;
    private int code;
    private String message;

    RespCode(int httpCode, int code, String message) {
        this.httpCode = httpCode;
        this.code = code;
        this.message = message;
    }

    public static RespCode valueOf(RespCode respCode, int httpCode, int code, String message) {
        respCode.setCode(code);
        respCode.setHttpCode(httpCode);
        respCode.setMessage(message);
        return respCode;
    }

    public static RespCode valueOf(RespCode respCode, String message) {
        respCode.setMessage(message);
        return respCode;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

//    public String toJson() {
//        JSONObject o = new JSONObject();
//        o.put("code", code);
//        o.put("message", message);
//        return o.toJSONString();
//    }

    @Override
    public String toString() {
        return String.format("[httpCode:%s, code:%s, message:%s]", httpCode, code, message);
    }
}
