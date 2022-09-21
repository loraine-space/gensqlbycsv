package cn.rofs.gensql.dto;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * @author rainofsilence
 * @date 2022/8/3 周三
 */
public class ResultDataDTO<T> {

    private Integer code;

    private String message;

    private T data;

    public ResultDataDTO(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    @Contract(value = " -> new", pure = true)
    public static <E> @NotNull ResultDataDTO<E> SUCCESS() {
        return new ResultDataDTO<>(200, "SUCCESS", null);
    }

    @Contract(value = "_ -> new", pure = true)
    public static <E> @NotNull ResultDataDTO<E> FAIL(String message) {
        return new ResultDataDTO<>(500, message, null);
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
