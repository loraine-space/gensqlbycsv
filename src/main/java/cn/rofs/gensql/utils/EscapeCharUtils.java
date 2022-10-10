package cn.rofs.gensql.utils;

/**
 * @author rainofsilence
 * @date 2022/9/21 周三
 */
public class EscapeCharUtils {

    // ","
    public static final String COMMA = "[C]";

    // " "
    public static final String PLACEHOLDER_NULL = "[PH_NULL]";

    public static String escape(String sql) {
        return sql.replace(COMMA, ",")
                .replace(PLACEHOLDER_NULL, "");
    }
}
