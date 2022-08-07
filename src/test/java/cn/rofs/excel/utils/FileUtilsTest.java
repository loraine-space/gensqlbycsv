package cn.rofs.excel.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author rainofsilence
 * @date 2022/8/7 周日
 */
class FileUtilsTest {

    @Test
    void mkdirs() {
        FileUtils.mkdirs("src/test/java/cn/rofs/excel-test");
    }
}