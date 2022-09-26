package cn.rofs.gensql.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author rainofsilence
 * @date 2022/9/22 周四
 * @desc 正则校验工具类
 */
public class RegularCheckUtils {

    // 字符类型的sql参数 , ' 字符 $[num] 字符 '
    // public static final String REGEX_SQL_PARAM_STRING = ".*,\\s*'\\S*\\$\\[num].*'.*";
    public static final String REGEX_SQL_PARAM_STRING = "[\\s\\S]*,\\s*'\\S*\\$\\[num][\\s\\S]*'[\\s\\S]*";
    public static final String REGEX_SQL_PARAM_STRING_INDEX_0 = "[\\s\\S]*'\\S*\\$\\[num][\\s\\S]*'[\\s\\S]*";

    public static boolean matches(String regex, CharSequence input) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);
        return m.matches();
    }

    public static boolean matchesForSqlParamString(CharSequence input, int index) {
        String regex = REGEX_SQL_PARAM_STRING;
        if (index == 0) {
            regex = REGEX_SQL_PARAM_STRING_INDEX_0;
        }
        regex = regex.replace("num", String.valueOf(index));
        return matches(regex, input);
    }
}