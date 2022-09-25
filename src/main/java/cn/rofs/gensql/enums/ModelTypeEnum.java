package cn.rofs.gensql.enums;

/**
 * @author rainofsilence
 * @date 2022/8/3 周三
 */
public enum ModelTypeEnum implements EnumAbility{
    DEFAULT(0,"model_default"),
    ONE(1,"model_one")
    ;

    private Integer code;

    private String desc;

    ModelTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public Object getCode() {
        return this.code;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }
}
