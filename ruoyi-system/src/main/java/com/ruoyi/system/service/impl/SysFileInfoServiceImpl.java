package com.ruoyi.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.config.Global;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.system.domain.SysFileInfo;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.mapper.SysFileInfoMapper;
import com.ruoyi.system.service.ISysFileInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 附件信息Service
 *
 * @author yangjie
 * @date 2020-01-06
 */
@Slf4j
@Service
public class SysFileInfoServiceImpl implements ISysFileInfoService {

    @Autowired
    private SysFileInfoMapper sysFileInfoMapper;

    /**
     * 查询附件信息
     *
     * @param id
     * @return
     */
    public SysFileInfo selectSysFileInfoById(String id) {
        log.info("查询附件信息,请求参数:" + JSON.toJSONString(id));
        return sysFileInfoMapper.selectByPrimaryKey(id);
    }

    public List<SysFileInfo> selectSysFileInfoByIds(String ids) {
        SysFileInfo sysFileInfo = new SysFileInfo();
        sysFileInfo.setIds(Convert.toStrArray(ids));
        log.info("查询附件信息,请求参数:" + JSON.toJSONString(ids));
        return sysFileInfoMapper.selectSysFileInfoListByIds(sysFileInfo);
    }

    /**
     * 查询附件信息列表
     *
     * @param sysFileInfo
     * @return
     */
    public List<SysFileInfo> selectSysFileInfoList(SysFileInfo sysFileInfo) {
        sysFileInfo.setDel(Constants.DEL_STATUS_OK);
        log.info("查询附件信息列表,请求参数:" + JSON.toJSONString(sysFileInfo));
        return sysFileInfoMapper.selectSysFileInfoList(sysFileInfo);
    }

    /**
     * 新增附件信息
     *
     * @param sysFileInfo
     * @return
     */
    public int insertSysFileInfo(SysFileInfo sysFileInfo) {
        sysFileInfo.setId(StringUtils.getUUID());
        sysFileInfo.setCreateTime(DateUtils.getNowDate());
        sysFileInfo.setDel(Constants.DEL_STATUS_OK);
        log.info("新增附件信息,请求参数:" + JSON.toJSONString(sysFileInfo));
        return sysFileInfoMapper.insertSelective(sysFileInfo);
    }

    /**
     * 修改附件信息
     *
     * @param sysFileInfo
     * @return
     */
    public int updateSysFileInfo(SysFileInfo sysFileInfo) {
        sysFileInfo.setUpdateTime(DateUtils.getNowDate());
        log.info("修改附件信息,请求参数:" + JSON.toJSONString(sysFileInfo));
        return sysFileInfoMapper.updateByPrimaryKeySelective(sysFileInfo);
    }

    /**
     * 批量删除附件信息对象
     *
     * @param ids
     * @return
     */
    public int deleteSysFileInfoByIds(String ids) {
        SysFileInfo sysFileInfo = new SysFileInfo();
        sysFileInfo.setDel(Constants.DEL_STATUS_NO);
        sysFileInfo.setIds(Convert.toStrArray(ids));
        sysFileInfo.setUpdateTime(DateUtils.getNowDate());
        log.info("批量删除附件信息对象,请求参数:" + JSON.toJSONString(sysFileInfo));
        return sysFileInfoMapper.batchUpdateSysFileInfo(sysFileInfo);
    }

    /**
     * 删除附件信息信息
     *
     * @param id
     * @return
     */
    public int deleteSysFileInfoById(String id) {
        SysFileInfo sysFileInfo = new SysFileInfo();
        sysFileInfo.setDel(Constants.DEL_STATUS_NO);
        sysFileInfo.setId(id);
        sysFileInfo.setUpdateTime(DateUtils.getNowDate());
        log.info("删除附件信息信息,请求参数:" + JSON.toJSONString(sysFileInfo));
        return sysFileInfoMapper.deleteByPrimaryKey(sysFileInfo);
    }


    /**
     * 批量上传
     *
     * @return
     */
    public AjaxResult batchAddSysFileInfo(MultipartFile[] files, SysUser sysUser) {
        try {
            List<SysFileInfo> sysFileInfos = new ArrayList<>();
            for (MultipartFile file : files) {
                String vistUrl = FileUploadUtils.upload(Global.getTmpUploadPath(), file);
                String path = Global.getTmpUploadPath() + vistUrl.replace(Constants.RESOURCE_PREFIX + "/tmp", "");
                SysFileInfo sysFileInfo = new SysFileInfo();
                sysFileInfo.setId(StringUtils.getUUID());
                sysFileInfo.setName(file.getOriginalFilename());
                sysFileInfo.setRealName(StringUtils.substringAfterLast(path, "/"));
                sysFileInfo.setDirectory(path);
                sysFileInfo.setVisitUrl(vistUrl);
                sysFileInfo.setFileSize(file.getSize());
                sysFileInfo.setExtension(FileUtils.getFileExtension(file));
                sysFileInfo.setContentType("." + sysFileInfo.getExtension());
                sysFileInfo.setDel(Constants.DEL_STATUS_OK);
                sysFileInfo.setCreateTime(DateUtils.getNowDate());
                sysFileInfo.setCreateUserId(sysUser.getUserId().intValue());
                sysFileInfos.add(sysFileInfo);
            }
            log.info("批量上传文件集合列表:" + JSON.toJSONString(sysFileInfos));
            return AjaxResult.success(sysFileInfos);
        } catch (IOException e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }

    @Override
    public List<SysFileInfo> convertSysFileInfoList(SysUser sysUser, String fileStr) throws IOException {
        String[] aboutFiles = fileStr.split(Constants.SPLIT_FILE_UUID);
        List<SysFileInfo> sysFileInfos = new ArrayList<>();
        for (String aboutFile : aboutFiles) {
            SysFileInfo sysFileInfo = JSON.parseObject(aboutFile, SysFileInfo.class);
            String oldPath = sysFileInfo.getDirectory();
            String newPath = sysFileInfo.getDirectory().replaceAll(Global.getTmpUploadPath(), Global.getUploadPath());
            FileUtils.copy(sysFileInfo.getRealName(), oldPath.replaceAll(sysFileInfo.getRealName(), ""), newPath.replaceAll(sysFileInfo.getRealName(), ""));
            sysFileInfo.setDirectory(newPath);
            sysFileInfo.setVisitUrl(sysFileInfo.getVisitUrl().replace(Constants.RESOURCE_PREFIX + "/tmp", Constants.RESOURCE_PREFIX + "/upload"));
            sysFileInfo.setDel(Constants.DEL_STATUS_OK);
            sysFileInfo.setCreateTime(DateUtils.getNowDate());
            sysFileInfo.setCreateUserId(sysUser.getUserId().intValue());
            sysFileInfos.add(sysFileInfo);
        }
        return sysFileInfos;

    }

    public List<SysFileInfo> convertUpdateSysFileInfoList(SysUser sysUser, String fileStr, String oldFileStr) throws IOException {
        String[] aboutFiles = fileStr.split(Constants.SPLIT_FILE_UUID);
        List<SysFileInfo> sysFileInfos = new ArrayList<>();
        for (int i = 0; i < aboutFiles.length; i++) {
            String aboutFile = aboutFiles[i];
            if (StringUtils.isNotEmpty(aboutFile)) {
                if (oldFileStr.indexOf(aboutFile) >= 0) {
                    SysFileInfo sysFileInfo = new SysFileInfo();
                    sysFileInfo.setIds(Convert.toStrArray(aboutFile));
                    List<SysFileInfo> sysFileInfosDatas = sysFileInfoMapper.selectSysFileInfoListByIds(sysFileInfo);
                    for (SysFileInfo sysFileInfosData : sysFileInfosDatas) {
                        sysFileInfosData.setId(StringUtils.getUUID());
                        sysFileInfosData.setDel(Constants.DEL_STATUS_OK);
                    }
                    sysFileInfos.addAll(sysFileInfosDatas);
                } else {
                    SysFileInfo sysFileInfo = JSON.parseObject(aboutFile, SysFileInfo.class);
                    sysFileInfo.setCreateTime(DateUtils.getNowDate());
                    sysFileInfo.setCreateUserId(sysUser.getUserId().intValue());
                    sysFileInfos.add(sysFileInfo);
                }
            }
        }
        return sysFileInfos;
    }


    @Override
    public String getConvertFileStr(SysUser sysUser, String fileStr) throws Exception {
        List<SysFileInfo> sysFileInfos = convertSysFileInfoList(sysUser, fileStr);

        log.info("新增附件相关信息,请求参数:" + JSON.toJSONString(sysFileInfos));
        int i = sysFileInfoMapper.insertSysFileInfoList(sysFileInfos);

        //将附件信息和业务测试表的  附件字段(aboutFile)进行关联
        List<String> sysFileInfoIds = sysFileInfos.parallelStream().map(SysFileInfo::getId).collect(Collectors.toList());
        String aboutFile = StringUtils.join(sysFileInfoIds.toArray(), ",");
        return aboutFile;
    }

    /**
     * 处理修改时的附件字段
     *
     * @param sysUser
     * @param dataFile
     * @param file
     * @return
     * @throws Exception
     */
    @Override
    public String serviceUpdateFile(SysUser sysUser, String dataFile, String file) throws Exception {
        int i = deleteSysFileInfoByIds(dataFile);
        List<SysFileInfo> sysFileInfoList = convertUpdateSysFileInfoList(sysUser, file, dataFile);
        sysFileInfoMapper.insertSysFileInfoList(sysFileInfoList);
        String newAboutFile = StringUtils.join(sysFileInfoList.parallelStream().map(SysFileInfo::getId).collect(Collectors.toList()), ",");
        return newAboutFile;
    }
}
