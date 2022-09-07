package cn.rofs;

import cn.rofs.excel.GenSqlUtils;
import cn.rofs.excel.constant.SysConstant;
import cn.rofs.excel.dto.ResultDTO;

/**
 * @author rainofsilence
 * @date 2022/8/3 周三
 */
public class Main {
    public static void main(String[] args) {
        // 通过sqlTemplate生成sql
        String sqlTemplate = "delete from user_info where id = '$[0]';\r\ninsert into user_info (id, name, age, sex) values ('$[0]', '$[1]', $[2], '$[3]');";
        ResultDTO result = GenSqlUtils.generateBySqlTemplate(sqlTemplate, SysConstant.DEFAULT_FILE_DIR_PATH, "sql_template_test.csv");
        System.out.println(result.getGenMsg());
    }
}