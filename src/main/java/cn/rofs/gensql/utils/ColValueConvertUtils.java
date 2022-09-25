package cn.rofs.gensql.utils;

import cn.rofs.gensql.enums.KeyValueTypeEnum;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static cn.rofs.gensql.constant.SysConstant.SEPARATOR_TV;

/**
 * @author rainofsilence
 * @date 2022/8/6 周六
 */
public class ColValueConvertUtils {

    public static String convert(String iLine) {
        return convertWithCol(iLine)[1];
    }

    @Contract("_ -> new")
    public static String @NotNull [] convertWithCol(@NotNull String iLine) {
        String[] iLineArr = iLine.split(SEPARATOR_TV);
        String type = iLineArr[0];
        String name = iLineArr[1];
        if (KeyValueTypeEnum.SNOWFLAKES.codeEquals(type)) {
            return new String[]{name, "'" + GenIdUtils.genSnowFlakeId() + "'"};
        }
        String value = iLineArr[2];
        if (KeyValueTypeEnum.NUMBER.codeEquals(type)) {
            return new String[]{name, value};
        } else if (KeyValueTypeEnum.STRING.codeEquals(type)) {
            return new String[]{name, "'" + value + "'"};
        }
        return new String[]{"", ""};
    }
}
