package cn.rofs.gensql.utils;

import cn.rofs.gensql.constant.SqlTemplateConstant;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author rainofsilence
 * @date 2022/9/22 周四
 */
class RegularCheckUtilsTest {

    @Test
    void matches() {
        // String str = "adas, 'aaa$[19]aaaa ' \r\n";
        String str = SqlTemplateConstant.SQL_TEMPLATE_EMPTY_PARAM;
        System.out.println(RegularCheckUtils.matchesForSqlParamString(str, 0));
    }
}