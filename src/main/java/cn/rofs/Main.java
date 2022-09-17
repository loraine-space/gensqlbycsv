package cn.rofs;

import cn.rofs.excel.GenSqlUtils;
import cn.rofs.excel.constant.SysConstant;
import cn.rofs.excel.dto.ResultDTO;

import static cn.rofs.excel.constant.SqlTemplateConstant.SQL_TEMPLATE_EMPTY_PARAM;

/**
 * @author rainofsilence
 * @date 2022/8/3 周三
 */
public class Main {
    public static void main(String[] args) {
        // 通过sqlTemplate生成sql
        ResultDTO result = GenSqlUtils.generateBySqlTemplate(SQL_TEMPLATE_EMPTY_PARAM, SysConstant.DEFAULT_FILE_DIR_PATH, "sql_template_empty_param_test.csv");
        System.out.println(result.getGenMsg());
    }
}