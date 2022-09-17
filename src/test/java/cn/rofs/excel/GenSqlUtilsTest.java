package cn.rofs.excel;


import cn.rofs.excel.constant.SysConstant;
import cn.rofs.excel.dto.ResultDTO;
import cn.rofs.excel.enums.ModelTypeEnum;
import cn.rofs.excel.sqlopt.OptService;
import cn.rofs.excel.sqlopt.OptServiceBuilder;
import org.junit.jupiter.api.Test;

import static cn.rofs.excel.constant.SqlTemplateConstant.SQL_TEMPLATE_EMPTY_PARAM;

/**
 * @author rainofsilence
 * @date 2022/8/4 周四
 */
class GenSqlUtilsTest {

    @Test
    void defaultGenerate() {
        ResultDTO resultDTO = GenSqlUtils.defaultGenerate("data-ins.csv");
        System.out.println(resultDTO.getCode() + ": {" + resultDTO.getMessage() + "}");
    }

    @Test
    void getOptService() {
        OptService optService = OptServiceBuilder.getOptService(ModelTypeEnum.DEFAULT + "-ins");

    }

    @Test
    void getNoOptService() {
        OptService optService = OptServiceBuilder.getOptService("no");
    }


    @Test
    void generateBySqlTemplate() {
        // 通过sqlTemplate生成sql
        ResultDTO result = GenSqlUtils.generateBySqlTemplate(SQL_TEMPLATE_EMPTY_PARAM, SysConstant.DEFAULT_FILE_DIR_PATH, "sql_template_empty_param_test.csv");
        System.out.println(result.getGenMsg());
    }
}