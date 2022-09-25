package cn.rofs.gensql.constant;

/**
 * @author rainofsilence
 * @date 2022/9/17 周六
 */
public class SqlTemplateConstant {

    public static final String SQL_TEMPLATE = "delete from user_info where id = '$[0]';\r\ninsert into user_info (id, name, age, sex) values ('$[0]', '$[1]', $[2], '$[3]');\r\n";

    public static final String SQL_TEMPLATE_EMPTY_PARAM = "delete from user_info where id = '$[0]';\r\ninsert into user_info (id, name, age, sex, email) values ('$[0]', '$[1]', $[2], '$[3]', '$[4]');\r\n";

}
