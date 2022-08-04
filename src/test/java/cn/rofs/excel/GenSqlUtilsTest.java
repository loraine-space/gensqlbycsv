package cn.rofs.excel;


import org.junit.jupiter.api.Test;

/**
 * @author rainofsilence
 * @date 2022/8/4 周四
 */
class GenSqlUtilsTest {

    @Test
    void defaultGenerate() {
        GenSqlUtils.defaultGenerate("unExistData.csv");
    }
}