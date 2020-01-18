package com.ruoyi.etc.toll.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.baseEntity.BaseEntity;
import com.ruoyi.system.domain.SysFileInfo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Transient;
import java.util.List;

/**
 * 停车场测试Bean
 *
 * @author yangjie
 * @date 2020-01-06
 */
@Getter
@Setter
public class EtcTollTest extends BaseEntity {

    /**
     * 名称
     */
    @Excel(name = "名称")
    private String name;

    /**
     * 手机号
     */
    @Excel(name = "手机号")
    private String phone;

    /**
     * 邮箱
     */
    @Excel(name = "邮箱")
    private String email;

    /**
     * 年龄
     */
    @Excel(name = "年龄")
    private Long age;

    /**
     * 性别
     */
    @Excel(name = "性别")
    private String sex;

    /**
     * 相关附件
     */
    @Excel(name = "相关附件")
    private String aboutFile;

}
