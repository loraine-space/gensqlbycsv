package cn.rofs.excel.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author rainofsilence
 * @date 2022/8/18 周四
 */
class ColValueConvertUtilsTest {

    @Test
    void convertWithCol() {
        ColValueConvertUtils.convertWithCol(null);
    }

    @Test
    void convert() {
        System.out.println(ColValueConvertUtils.convert("SN::ID"));
    }
}