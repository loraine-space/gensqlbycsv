package cn.rofs.excel.enums;

import java.util.Objects;

/**
 * @author rainofsilence
 * @date 2022/7/26 周二
 */
public interface EnumAbility<T> {


    T getCode();

    String getDesc();

    /**
     * 比较当前枚举对象和传入的值是否一致
     *
     * @param enumCode
     * @return
     */
    default boolean codeEquals(T enumCode) {
        if (enumCode == null) return false;
        if (enumCode instanceof String) {
            return ((String) enumCode).equalsIgnoreCase((String) getCode());
        }
        return Objects.equals(this.getCode(), enumCode);
    }

    /**
     * 比较2个枚举值是否相同
     *
     * @param anotherEnum
     * @return
     */
    default boolean equals(EnumAbility<T> anotherEnum) {
        return this == anotherEnum;
    }
}
