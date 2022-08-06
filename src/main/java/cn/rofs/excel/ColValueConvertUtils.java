package cn.rofs.excel;

import cn.rofs.excel.enums.KeyValueTypeEnum;

import static cn.rofs.excel.constant.SysConstant.SEPARATOR_TV;

/**
 * @author rainofsilence
 * @date 2022/8/6 周六
 */
public class ColValueConvertUtils {

    public static String convert(String iLine) {
        return convertWithCol(iLine)[1];
    }

    public static String[] convertWithCol(String iLine) {
        String[] iLineArr = iLine.split(SEPARATOR_TV);
        String type = iLineArr[0];
        String name = iLineArr[1];
        String value = iLineArr[2];
        if (KeyValueTypeEnum.NUMBER.codeEquals(type)) {
            return new String[]{name, value};
        } else if (KeyValueTypeEnum.STRING.codeEquals(type)) {
            return new String[]{name, "'" + value + "'"};
        } else if (KeyValueTypeEnum.SNOWFLAKES.codeEquals(type)) {
            return new String[]{"", ""};
        }
        return new String[]{"", ""};
    }
}
