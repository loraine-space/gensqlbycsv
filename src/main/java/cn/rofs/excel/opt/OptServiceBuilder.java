package cn.rofs.excel.opt;

import cn.rofs.excel.constant.SysConstant;
import cn.rofs.excel.opt.impl.DeleteServiceImpl;
import cn.rofs.excel.opt.impl.InsertServiceImpl;
import cn.rofs.excel.opt.impl.UpdateServiceImpl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author rainofsilence
 * @date 2022/8/4 周四
 */
public class OptServiceBuilder {

    private static Map<String, OptService> optServicePool = new ConcurrentHashMap<>();

    static {
        optServicePool.put(SysConstant.OPT_TYPE_INS, new InsertServiceImpl());
        optServicePool.put(SysConstant.OPT_TYPE_UPD, new UpdateServiceImpl());
        optServicePool.put(SysConstant.OPT_TYPE_DEL, new DeleteServiceImpl());
    }

    public static OptService getOptService(String optType) {
        OptService optService = optServicePool.get(optType.toLowerCase());
        if (optService == null) {
            throw new RuntimeException("The optType was not found");
        }
        return optService;
    }
}
