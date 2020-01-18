package com.ruoyi.common.baseEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 基础信息
 */
@Getter
@Setter
public class BaseEntity {

    @Id
    private String id;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 创建人
     */
    private Integer createUserId;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 更新人
     */
    private Integer updateUserId;

    /**
     * 是否删除(0.未删除  1.已删除)
     */
    private Integer del;


    /**
     * 备注
     */
    private String remark;

    /**
     * 请求参数
     */
    @Transient
    private Map<String, Object> params;

    /**
     * ids编号集合
     */
    @Transient
    private String[] ids;


}
