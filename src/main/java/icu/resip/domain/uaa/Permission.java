package icu.resip.domain.uaa;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class Permission implements Serializable {
    /** 主键*/
    private Long id;

    /** 权限名称*/
    private String name;

    /** 权限表达式*/
    private String expression;
}