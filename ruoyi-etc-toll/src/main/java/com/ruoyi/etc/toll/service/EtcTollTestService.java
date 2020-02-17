package com.ruoyi.etc.toll.service;


import java.util.List;
import java.util.Objects;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.etc.toll.domain.EtcTollTest;
import com.ruoyi.etc.toll.mapper.EtcTollTestMapper;
import com.ruoyi.system.service.ISysFileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.exception.BusinessException;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import com.ruoyi.common.constant.Constants;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.text.Convert;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * 停车场测试Service
 *
 * @author ruoyi
 * @date 2020-02-17
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
        if (StringUtils.isEmpty(id)) {
            throw new BusinessException("id不能为空");
        }
        return etcTollTestMapper.selectByPrimaryKey(id);
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
    @Transactional(propagation = Propagation.REQUIRED)
    public AjaxResult insertEtcTollTest(EtcTollTest etcTollTest) {
        try {
            checkEditEtcTollTestParams(etcTollTest, Constants.SERVEICE_OPERATE_ADD);
            SysUser sysUser = ShiroUtils.getSysUser();
            etcTollTest.setId(StringUtils.getUUID());
            etcTollTest.setAboutFile(sysFileInfoService.getConvertFileStr(sysUser, etcTollTest.getAboutFile()));
            etcTollTest.setCreateTime(DateUtils.getNowDate());
            etcTollTest.setCreateUserId(sysUser.getUserId().intValue());
            etcTollTest.setDel(Constants.DEL_STATUS_OK);
            log.info("新增停车场测试,请求参数:" + JSON.toJSONString(etcTollTest));
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
    @Transactional(propagation = Propagation.REQUIRED)
    public AjaxResult updateEtcTollTest(EtcTollTest etcTollTest) {
        try {
            checkEditEtcTollTestParams(etcTollTest, Constants.SERVEICE_OPERATE_UPDATE);
            SysUser sysUser = ShiroUtils.getSysUser();
            //处理文件相关操作
            //1.先删除文件,在添加文件记录
            EtcTollTest etcTollTestOne = etcTollTestMapper.selectByPrimaryKey(etcTollTest.getId());
            //获取最新相关附件字符串
            String aboutFile = sysFileInfoService.serviceUpdateFile(sysUser, etcTollTestOne.getAboutFile(), etcTollTest.getAboutFile());

            etcTollTest.setAboutFile(aboutFile);
            etcTollTest.setUpdateTime(DateUtils.getNowDate());
            etcTollTest.setUpdateUserId(sysUser.getUserId().intValue());
            log.info("修改停车场测试,请求参数:" + JSON.toJSONString(etcTollTest));
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
    @Transactional(propagation = Propagation.REQUIRED)
    public AjaxResult deleteEtcTollTestByIds(String ids) {
        if (StringUtils.isEmpty(ids) || Convert.toStrArray(ids).length == 0) {
            throw new BusinessException("ids编号集合不能为空");
        }
        SysUser sysUser = ShiroUtils.getSysUser();
        EtcTollTest etcTollTest = new EtcTollTest();
        etcTollTest.setDel(Constants.DEL_STATUS_NO);
        etcTollTest.setIds(Convert.toStrArray(ids));
        etcTollTest.setUpdateTime(DateUtils.getNowDate());
        etcTollTest.setUpdateUserId(sysUser.getUserId().intValue());
        log.info("批量删除停车场测试对象,请求参数:" + JSON.toJSONString(etcTollTest));
        return etcTollTestMapper.batchUpdateEtcTollTest(etcTollTest) > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    /**
     * 删除停车场测试信息
     *
     * @param id
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public AjaxResult deleteEtcTollTestById(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new BusinessException("id不能为空");
        }
        SysUser sysUser = ShiroUtils.getSysUser();
        EtcTollTest etcTollTest = new EtcTollTest();
        etcTollTest.setId(id);
        etcTollTest.setDel(Constants.DEL_STATUS_NO);
        etcTollTest.setUpdateTime(DateUtils.getNowDate());
        etcTollTest.setUpdateUserId(sysUser.getUserId().intValue());
        log.info("删除停车场测试信息,请求参数:" + JSON.toJSONString(etcTollTest));
        return etcTollTestMapper.updateByPrimaryKey(etcTollTest) > 0 ? AjaxResult.success() : AjaxResult.error();
    }


    //校验编辑时候参数是否合法
    public void checkEditEtcTollTestParams(EtcTollTest etcTollTest, String code) {
        //修改操作,要判断Id是否为空
        if (code.equals(Constants.SERVEICE_OPERATE_UPDATE)) {
            if (StringUtils.isEmpty(etcTollTest.getId())) {
                throw new BusinessException("id不能为空");
            }
        }

        if (StringUtils.isEmpty(etcTollTest.getName())) {
            throw new BusinessException("名称不能为空");
        }
        if (StringUtils.isEmpty(etcTollTest.getPhone())) {
            throw new BusinessException("手机号不能为空");
        }
        if (StringUtils.isEmpty(etcTollTest.getEmail())) {
            throw new BusinessException("邮箱不能为空");
        }
        if (Objects.isNull(etcTollTest.getAge())) {
            throw new BusinessException("年龄不能为空");
        }
        if (StringUtils.isEmpty(etcTollTest.getSex())) {
            throw new BusinessException("性别不能为空");
        }
        if (StringUtils.isEmpty(etcTollTest.getAboutFile())) {
            throw new BusinessException("相关附件不能为空");
        }
    }
}