package cn.rofs.excel.sqlopt;

import cn.rofs.excel.sqlopt.impl.moddefault.DefaultDeleteServiceImpl;
import cn.rofs.excel.sqlopt.impl.moddefault.DefaultInsertServiceImpl;
import cn.rofs.excel.sqlopt.impl.moddefault.DefaultUpdateServiceImpl;
import cn.rofs.excel.sqlopt.impl.modone.OneDeleteServiceImpl;
import cn.rofs.excel.sqlopt.impl.modone.OneInsertServiceImpl;
import cn.rofs.excel.sqlopt.impl.modone.OneUpdateServiceImpl;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static cn.rofs.excel.constant.SysConstant.*;
import static cn.rofs.excel.enums.ModelTypeEnum.DEFAULT;
import static cn.rofs.excel.enums.ModelTypeEnum.ONE;

/**
 * @author rainofsilence
 * @date 2022/8/4 周四
 */
public class OptServiceBuilder {

    private static Map<String, OptService> optServicePool = new ConcurrentHashMap<>();

    static {
        optServicePool.put(DEFAULT + "-" + OPT_TYPE_INS.toUpperCase(), new DefaultInsertServiceImpl());
        optServicePool.put(DEFAULT + "-" + OPT_TYPE_UPD.toUpperCase(), new DefaultUpdateServiceImpl());
        optServicePool.put(DEFAULT + "-" + OPT_TYPE_DEL.toUpperCase(), new DefaultDeleteServiceImpl());
        optServicePool.put(ONE + "-" + OPT_TYPE_INS.toUpperCase(), new OneInsertServiceImpl());
        optServicePool.put(ONE + "-" + OPT_TYPE_UPD.toUpperCase(), new OneUpdateServiceImpl());
        optServicePool.put(ONE + "-" + OPT_TYPE_DEL.toUpperCase(), new OneDeleteServiceImpl());
    }

    /**
     * 获取对应的实现类
     *
     * @param optType
     * @return
     */
    public static @NotNull OptService getOptService(@NotNull String optType) {
        OptService optService = optServicePool.get(optType.toUpperCase());
        if (optService == null) {
            throw new RuntimeException("The optType was not found");
        }
        return optService;
    }
}
