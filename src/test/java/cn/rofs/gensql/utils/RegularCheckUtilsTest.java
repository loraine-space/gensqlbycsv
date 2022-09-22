package cn.rofs.gensql.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author rainofsilence
 * @date 2022/9/22 周四
 */
class RegularCheckUtilsTest {

    @Test
    void matches() {
        String str = "adas, 'aaa$[19]aaaa";
        System.out.println(RegularCheckUtils.matchesForSqlParamString(str, 19));
    }
}