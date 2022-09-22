package cn.rofs.gensql.utils;

/**
 * @author rainofsilence
 * @date 2022/9/21 周三
 */
public class EscapeCharUtils {

    // ","
    private static final String COMMA = "[C]";

    public static String escape(String sql) {
        return sql.replace(COMMA, ",");
    }
}
