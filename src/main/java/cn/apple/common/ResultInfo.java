package cn.apple.common;

import lombok.Data;

@Data
public class ResultInfo{

    private Boolean flag;

    private Integer code;

    private String msg;

    private Object data;

    public ResultInfo() {

    }

    public ResultInfo(Boolean flag, String msg, Object data) {
        this.flag = flag;
        this.msg = msg;
        this.data = data;
    }

    public ResultInfo(Boolean flag, String msg) {
        this.flag = flag;
        this.msg = msg;
    }

    public ResultInfo(Boolean flag) {
        this.flag = flag;
    }

    public static ResultInfo ok(){

        return ok(null,null);
    }

    public static ResultInfo ok(String msg){

        return ok(msg,null);
    }

    public static ResultInfo ok(String msg,Object data){

        ResultInfo resultInfo = new ResultInfo(true,msg,data);

        resultInfo.setCode(StatusCode.SUCCESS.code);

        return resultInfo;
    }

    public static ResultInfo notFound(){

        return notFound(null);
    }

    public static ResultInfo notFound(String msg){

        ResultInfo resultInfo = new ResultInfo(false);

        resultInfo.setCode(StatusCode.NOT_FOUND.code);

        return resultInfo;
    }

    public static ResultInfo created(){

        return created(null);
    }

    public static ResultInfo created(String msg){

        ResultInfo resultInfo = new ResultInfo(true,msg);

        resultInfo.setCode(StatusCode.CREATED.code);

        return resultInfo;
    }

    public static ResultInfo serverError(String msg){

        ResultInfo resultInfo = new ResultInfo(false,msg);

        resultInfo.setCode(StatusCode.SERVER_ERROR.code);

        return resultInfo;
    }

    public static ResultInfo forbidden(){

        ResultInfo resultInfo = new ResultInfo(false);

        resultInfo.setCode(StatusCode.FORBIDDEN.code);

        return resultInfo;
    }



    enum StatusCode{

        SUCCESS(200),
        CREATED(201),
        BAD_REQUEST(400),
        FORBIDDEN(403),
        NOT_FOUND(404),
        SERVER_ERROR(500);

        private final Integer code;

        StatusCode(Integer code) {
            this.code = code;
        }
    }


}