package cn.rofs.gensql.dto;

/**
 * @author rainofsilence
 * @date 2022/8/27 周六
 */
public class ResultDTO {

    private Integer code;

    private String message;


    public ResultDTO() {
    }

    public ResultDTO(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


    public static ResultDTO SUCCESS() {
        return new ResultDTO(200, "SUCCESS");
    }

    public static ResultDTO FAIL(String message) {
        return new ResultDTO(500, message);
    }

    public boolean isSuccess() {
        if (this.code==null) return false;
        return this.code == 200;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getGenMsg() {
        return "code: " + this.code + ", msg: " + this.message;
    }
}
