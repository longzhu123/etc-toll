package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.SysFileInfo;
import com.ruoyi.system.domain.SysUser;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ISysFileInfoService {

    /**
     * 查询附件信息
     *
     * @param id
     * @return
     */
    SysFileInfo selectSysFileInfoById(String id);

    /**
     * 查询附件信息列表
     *
     * @param sysFileInfo
     * @return
     */
    List<SysFileInfo> selectSysFileInfoList(SysFileInfo sysFileInfo);

    /**
     * 新增附件信息
     *
     * @param sysFileInfo
     * @return
     */
    int insertSysFileInfo(SysFileInfo sysFileInfo);

    /**
     * 修改附件信息
     *
     * @param sysFileInfo
     * @return
     */
    int updateSysFileInfo(SysFileInfo sysFileInfo);

    /**
     * 批量删除附件信息对象
     *
     * @param ids
     * @return
     */
    int deleteSysFileInfoByIds(String ids);

    /**
     * 删除附件信息信息
     *
     * @param id
     * @return
     */
    int deleteSysFileInfoById(String id);

    /**
     * 文件上传
     *
     * @param files
     * @param sysUser
     * @return
     */
    AjaxResult batchAddSysFileInfo(MultipartFile[] files, SysUser sysUser);


    List<SysFileInfo> convertSysFileInfoList(SysUser sysUser, String fileStr) throws IOException;

    List<SysFileInfo> convertUpdateSysFileInfoList(SysUser sysUser, String fileStr, String oldStr) throws IOException;

    List<SysFileInfo> selectSysFileInfoByIds(String ids);

    /**
     * 转换插入业务的附件字符串
     * @param sysUser
     * @param fileStr
     * @return
     * @throws Exception
     */
    String getConvertFileStr(SysUser sysUser, String fileStr) throws Exception;

    /**
     * 处理修改时的附件字段
     * @param sysUser
     * @param dataFile
     * @param file
     * @return
     * @throws Exception
     */
    String serviceUpdateFile(SysUser sysUser, String dataFile,String file) throws Exception;
}
