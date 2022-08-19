package cn.rofs.excel.utils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rainofsilence
 * @date 2022/8/4 周四
 */
public class GenIdUtils {

    public static @NotNull String genSnowFlakeId() {
        SnowFlakeIdBuilder idBuilder = new SnowFlakeIdBuilder(1,1,1);
        return String.valueOf(idBuilder.nextId());
    }

    public static @NotNull List<String> genSnowFlakeIdBatch(int count) {
        List<String> idList = new ArrayList<>();
        SnowFlakeIdBuilder idBuilder = new SnowFlakeIdBuilder(1,1,1);
        for (int i = 0; i < count; i++) {
            idList.add(String.valueOf(idBuilder.nextId()));
        }
        return idList;
    }
    
}
