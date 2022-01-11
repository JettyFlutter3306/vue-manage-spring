package cn.element.common;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ResultInfo {

    private Integer code;

    private String msg;

    private Object data;

    public ResultInfo() {

    }

    public ResultInfo(String msg, Object data) {
        this.msg = msg;
        this.data = data;
    }

    public ResultInfo(String msg) {
        this.msg = msg;
    }

    public static ResultInfo ok() {
        return ok(null,null);
    }

    public static ResultInfo ok(String msg) {
        return ok(msg,null);
    }

    public static ResultInfo ok(String msg,Object data) {
        ResultInfo resultInfo = new ResultInfo(msg,data);

        resultInfo.setCode(HttpStatus.OK.value());

        return resultInfo;
    }

    public static ResultInfo notFound() {
        return notFound(null);
    }

    public static ResultInfo notFound(String msg) {
        ResultInfo resultInfo = new ResultInfo(msg);

        resultInfo.setCode(HttpStatus.NOT_FOUND.value());

        return resultInfo;
    }

    public static ResultInfo created() {
        return created(null);
    }

    public static ResultInfo created(String msg) {
        ResultInfo resultInfo = new ResultInfo(msg);

        resultInfo.setCode(HttpStatus.CREATED.value());

        return resultInfo;
    }

    public static ResultInfo serverError(String msg) {
        ResultInfo resultInfo = new ResultInfo(msg);

        resultInfo.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

        return resultInfo;
    }

    public static ResultInfo forbidden() {
        return forbidden(null);
    }

    public static ResultInfo forbidden(String msg) {
        ResultInfo resultInfo = new ResultInfo(msg);

        resultInfo.setCode(HttpStatus.FORBIDDEN.value());

        return resultInfo;
    }

    public static ResultInfo badRequest() {
        return badRequest(null);
    }

    public static ResultInfo badRequest(String msg) {
        ResultInfo resultInfo = new ResultInfo(msg);

        resultInfo.setCode(HttpStatus.BAD_REQUEST.value());

        return resultInfo;
    }

    public static ResultInfo notLogin(String msg) {
        ResultInfo resultInfo = new ResultInfo(msg);

        resultInfo.setCode(HttpStatus.UNAUTHORIZED.value());

        return resultInfo;
    }
}
