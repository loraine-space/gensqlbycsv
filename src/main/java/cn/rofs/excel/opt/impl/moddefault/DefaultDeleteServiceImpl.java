package cn.rofs.excel.opt.impl.moddefault;

import cn.rofs.excel.opt.OptService;

import java.util.Map;

/**
 * @author rainofsilence
 * @date 2022/8/4 周四
 */
public class DefaultDeleteServiceImpl implements OptService {
    @Override
    public StringBuffer genSql(String curLine, Map<String, Object> headerMap) {
        System.out.println("start deleteService");
        return null;
    }
}