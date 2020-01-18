package com.ruoyi.etc.toll.service;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.etc.toll.domain.EtcTollTest;
import com.ruoyi.etc.toll.mapper.EtcTollTestMapper;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.service.ISysFileInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 停车场测试Service
 *
 * @author yangjie
 * @date 2020-01-06
 */
@Slf4j
@Service
public class EtcTollTestService {

    @Autowired
    private EtcTollTestMapper etcTollTestMapper;

    @Autowired
    private ISysFileInfoService sysFileInfoService;

    /**
     * 查询停车场测试
     *
     * @param id
     * @return
     */
    public EtcTollTest selectEtcTollTestById(String id) {
        log.info("查询停车场测试,请求参数:" + JSON.toJSONString(id));
        EtcTollTest etcTollTest = etcTollTestMapper.selectByPrimaryKey(id);
        return etcTollTest;
    }

    /**
     * 查询停车场测试列表
     *
     * @param etcTollTest
     * @return
     */
    public List<EtcTollTest> selectEtcTollTestList(EtcTollTest etcTollTest) {
        etcTollTest.setDel(Constants.DEL_STATUS_OK);
        log.info("查询停车场测试列表,请求参数:" + JSON.toJSONString(etcTollTest));
        return etcTollTestMapper.selectEtcTollTestList(etcTollTest);
    }

    /**
     * 新增停车场测试
     *
     * @param etcTollTest
     * @return
     */
    @Transactional(propagation= Propagation.REQUIRED)
    public AjaxResult insertEtcTollTest(EtcTollTest etcTollTest) {
        try {
            if (StringUtils.isEmpty(etcTollTest.getAboutFile())) {
                return AjaxResult.error("相关附件不能为空");
            }
            SysUser sysUser = ShiroUtils.getSysUser();
            log.info("新增停车场测试,请求参数:" + JSON.toJSONString(etcTollTest));
            etcTollTest.setId(StringUtils.getUUID());
            etcTollTest.setAboutFile(sysFileInfoService.getConvertFileStr(sysUser, etcTollTest.getAboutFile()));
            etcTollTest.setCreateTime(DateUtils.getNowDate());
            etcTollTest.setCreateUserId(sysUser.getUserId().intValue());
            etcTollTest.setDel(Constants.DEL_STATUS_OK);
            return etcTollTestMapper.insertSelective(etcTollTest) > 0 ? AjaxResult.success() : AjaxResult.error();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 修改停车场测试
     *
     * @param etcTollTest
     * @return
     */
    @Transactional(propagation= Propagation.REQUIRED)
    public AjaxResult updateEtcTollTest(EtcTollTest etcTollTest) {
        try {

            if (StringUtils.isEmpty(etcTollTest.getAboutFile())) {
                return AjaxResult.error("相关附件不能为空");
            }
            SysUser sysUser = ShiroUtils.getSysUser();

            //处理文件相关操作
            //1.先删除文件,在添加文件记录
            EtcTollTest etcTollTestOne = etcTollTestMapper.selectByPrimaryKey(etcTollTest.getId());
            //2.获取最新文件字符串
            String aboutFile = sysFileInfoService.serviceUpdateFile(sysUser,etcTollTestOne.getAboutFile(),etcTollTest.getAboutFile());


            etcTollTest.setAboutFile(aboutFile);
            etcTollTest.setUpdateTime(DateUtils.getNowDate());
            etcTollTest.setUpdateUserId(sysUser.getUserId().intValue());
            return etcTollTestMapper.updateByPrimaryKeySelective(etcTollTest) > 0 ? AjaxResult.success() : AjaxResult.error();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 批量删除停车场测试对象
     *
     * @param ids
     * @return
     */
    @Transactional(propagation= Propagation.REQUIRED)
    public int deleteEtcTollTestByIds(String ids) {
        SysUser sysUser = ShiroUtils.getSysUser();
        EtcTollTest etcTollTest = new EtcTollTest();
        etcTollTest.setDel(Constants.DEL_STATUS_NO);
        etcTollTest.setIds(Convert.toStrArray(ids));
        etcTollTest.setUpdateTime(DateUtils.getNowDate());
        etcTollTest.setUpdateUserId(sysUser.getUserId().intValue());
        log.info("批量删除停车场测试对象,请求参数:" + JSON.toJSONString(etcTollTest));
        return etcTollTestMapper.batchUpdateEtcTollTest(etcTollTest);
    }

    /**
     * 删除停车场测试信息
     *
     * @param id
     * @return
     */
    @Transactional(propagation= Propagation.REQUIRED)
    public int deleteEtcTollTestById(String id) {
        SysUser sysUser = ShiroUtils.getSysUser();
        EtcTollTest etcTollTest = new EtcTollTest();
        etcTollTest.setDel(Constants.DEL_STATUS_NO);
        etcTollTest.setId(id);
        etcTollTest.setUpdateTime(DateUtils.getNowDate());
        etcTollTest.setUpdateUserId(sysUser.getUserId().intValue());
        log.info("删除停车场测试信息,请求参数:" + JSON.toJSONString(etcTollTest));
        return etcTollTestMapper.deleteByPrimaryKey(etcTollTest);
    }

}
