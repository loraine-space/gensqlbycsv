package cn.rofs.gensql.utils;

import org.junit.jupiter.api.Test;

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