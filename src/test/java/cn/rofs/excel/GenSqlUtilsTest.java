package cn.rofs.excel;


import cn.rofs.excel.opt.OptService;
import cn.rofs.excel.opt.OptServiceBuilder;
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

    @Test
    void getOptService() {
        OptService optService = OptServiceBuilder.getOptService("ins");
        optService.genSql("curLine");
    }

    @Test
    void getNoOptService() {
        OptService optService = OptServiceBuilder.getOptService("no");
        optService.genSql("curLine");
    }
}