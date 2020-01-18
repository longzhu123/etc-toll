package com.ruoyi.system.mapper;

import com.ruoyi.common.baseMapper.BaseMapper;
import com.ruoyi.system.domain.SysFileInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 附件信息Mapper
 *
 * @author ruoyi
 * @date 2020-01-06
 */
public interface SysFileInfoMapper extends BaseMapper<SysFileInfo> {

    /**
     * 查询附件信息列表
     *
     * @param sysFileInfo
     */
    List<SysFileInfo> selectSysFileInfoList(SysFileInfo sysFileInfo);


    /**
     * 批量修改附件信息
     *
     * @param sysFileInfo
     */
    int batchUpdateSysFileInfo(SysFileInfo sysFileInfo);

    /**
     * 批量插入
     * @param sysFileInfos
     * @return
     */
    int insertSysFileInfoList(@Param("list") List<SysFileInfo> sysFileInfos);



    List<SysFileInfo> selectSysFileInfoListByIds(SysFileInfo sysFileInfo);
}
