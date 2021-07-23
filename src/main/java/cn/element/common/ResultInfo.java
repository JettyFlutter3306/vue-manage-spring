package cn.element.common;

import lombok.Data;

@Data
public class ResultInfo{

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

    public static ResultInfo ok(){

        return ok(null,null);
    }

    public static ResultInfo ok(String msg){

        return ok(msg,null);
    }

    public static ResultInfo ok(String msg,Object data){

        ResultInfo resultInfo = new ResultInfo(msg,data);

        resultInfo.setCode(StatusCode.SUCCESS.code);

        return resultInfo;
    }

    public static ResultInfo notFound(){

        return notFound(null);
    }

    public static ResultInfo notFound(String msg){

        ResultInfo resultInfo = new ResultInfo(msg);

        resultInfo.setCode(StatusCode.NOT_FOUND.code);

        return resultInfo;
    }

    public static ResultInfo created(){

        return created(null);
    }

    public static ResultInfo created(String msg){

        ResultInfo resultInfo = new ResultInfo(msg);

        resultInfo.setCode(StatusCode.CREATED.code);

        return resultInfo;
    }

    public static ResultInfo serverError(String msg){

        ResultInfo resultInfo = new ResultInfo(msg);

        resultInfo.setCode(StatusCode.SERVER_ERROR.code);

        return resultInfo;
    }

    public static ResultInfo forbidden(){

        return forbidden(null);
    }

    public static ResultInfo forbidden(String msg){

        ResultInfo resultInfo = new ResultInfo(msg);

        resultInfo.setCode(StatusCode.FORBIDDEN.code);

        return resultInfo;
    }

    public static ResultInfo badRequest(){

        return badRequest(null);
    }

    public static ResultInfo badRequest(String msg){

        ResultInfo resultInfo = new ResultInfo(msg);

        resultInfo.setCode(StatusCode.BAD_REQUEST.code);

        return resultInfo;
    }

    public static ResultInfo notLogin(String msg) {

        ResultInfo resultInfo = new ResultInfo(msg);

        resultInfo.setCode(StatusCode.NOT_LOGIN.code);

        return resultInfo;
    }

    enum StatusCode{

        SUCCESS(200),
        CREATED(201),
        BAD_REQUEST(400),
        NOT_LOGIN(401),
        FORBIDDEN(403),
        NOT_FOUND(404),
        SERVER_ERROR(500);

        private final Integer code;

        StatusCode(Integer code) {
            this.code = code;
        }


    }


}
