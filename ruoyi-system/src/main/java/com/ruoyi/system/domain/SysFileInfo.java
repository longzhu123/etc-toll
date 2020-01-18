package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.baseEntity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * 附件信息Bean
 *
 * @author yangjie
 * @date 2020-01-06
 */
@Getter
@Setter
public class SysFileInfo extends BaseEntity{


    /**
     * 文件名称
     */
    @Excel(name = "文件名称")
    private String name;

    /**
     * 文件在服务器真正的名称
     */
    @Excel(name = "文件真实名称")
    private String realName;

    /**
     * 文件大小(KB)
     */
    @Excel(name = "文件大小(KB)")
    private Long fileSize;

    /**
     * 文件类型
     */
    @Excel(name = "文件类型")
    private String extension;

    /**
     * 文件后缀类型
     */
    @Excel(name = "文件后缀类型")
    private String contentType;

    /**
     * 存储路径
     */
    @Excel(name = "存储路径")
    private String directory;

    /**
     * 访问路径
     */
    @Excel(name = "访问路径")
    private String visitUrl;

}
