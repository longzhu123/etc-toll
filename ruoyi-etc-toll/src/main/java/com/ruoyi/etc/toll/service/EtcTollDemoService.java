package com.ruoyi.etc.toll.service;

import java.util.List;
import java.util.Objects;

import com.ruoyi.common.utils.DateUtils;
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
import com.ruoyi.etc.toll.mapper.EtcTollDemoMapper;
import com.ruoyi.etc.toll.domain.EtcTollDemo;
import com.ruoyi.common.core.text.Convert;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * 停车场DemoService
 *
 * @author yangjie
 * @date 2020-02-17
 */
@Slf4j
@Service
public class EtcTollDemoService {

    @Autowired
    private EtcTollDemoMapper etcTollDemoMapper;

    @Autowired
    private ISysFileInfoService sysFileInfoService;

    /**
     * 查询停车场Demo
     *
     * @param id
     * @return
     */
    public EtcTollDemo selectEtcTollDemoById(String id) {
        log.info("查询停车场Demo,请求参数:" + JSON.toJSONString(id));
        if (StringUtils.isEmpty(id)) {
            throw new BusinessException("id不能为空");
        }
        return etcTollDemoMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询停车场Demo列表
     *
     * @param etcTollDemo
     * @return
     */
    public List<EtcTollDemo> selectEtcTollDemoList(EtcTollDemo etcTollDemo) {
        etcTollDemo.setDel(Constants.DEL_STATUS_OK);
        log.info("查询停车场Demo列表,请求参数:" + JSON.toJSONString(etcTollDemo));
        return etcTollDemoMapper.selectEtcTollDemoList(etcTollDemo);
    }

    /**
     * 新增停车场Demo
     *
     * @param etcTollDemo
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public AjaxResult insertEtcTollDemo(EtcTollDemo etcTollDemo) {
        try {
            checkEditEtcTollDemoParams(etcTollDemo, Constants.SERVEICE_OPERATE_ADD);
            SysUser sysUser = ShiroUtils.getSysUser();
            etcTollDemo.setId(StringUtils.getUUID());
            etcTollDemo.setServiceFile(sysFileInfoService.getConvertFileStr(sysUser, etcTollDemo.getServiceFile()));
            etcTollDemo.setAboutFile(sysFileInfoService.getConvertFileStr(sysUser, etcTollDemo.getAboutFile()));
            etcTollDemo.setCreateTime(DateUtils.getNowDate());
            etcTollDemo.setCreateUserId(sysUser.getUserId().intValue());
            etcTollDemo.setDel(Constants.DEL_STATUS_OK);
            log.info("新增停车场Demo,请求参数:" + JSON.toJSONString(etcTollDemo));
            return etcTollDemoMapper.insertSelective(etcTollDemo) > 0 ? AjaxResult.success() : AjaxResult.error();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }

    }

    /**
     * 修改停车场Demo
     *
     * @param etcTollDemo
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public AjaxResult updateEtcTollDemo(EtcTollDemo etcTollDemo) {
        try {
            checkEditEtcTollDemoParams(etcTollDemo, Constants.SERVEICE_OPERATE_UPDATE);
            SysUser sysUser = ShiroUtils.getSysUser();
            //处理文件相关操作
            //1.先删除文件,在添加文件记录
            EtcTollDemo etcTollDemoOne = etcTollDemoMapper.selectByPrimaryKey(etcTollDemo.getId());
            //获取最新业务附件字符串
            String serviceFile = sysFileInfoService.serviceUpdateFile(sysUser, etcTollDemoOne.getServiceFile(), etcTollDemo.getServiceFile());
            //获取最新相关附件字符串
            String aboutFile = sysFileInfoService.serviceUpdateFile(sysUser, etcTollDemoOne.getAboutFile(), etcTollDemo.getAboutFile());

            etcTollDemo.setServiceFile(serviceFile);
            etcTollDemo.setAboutFile(aboutFile);
            etcTollDemo.setUpdateTime(DateUtils.getNowDate());
            etcTollDemo.setUpdateUserId(sysUser.getUserId().intValue());
            log.info("修改停车场Demo,请求参数:" + JSON.toJSONString(etcTollDemo));
            return etcTollDemoMapper.updateByPrimaryKeySelective(etcTollDemo) > 0 ? AjaxResult.success() : AjaxResult.error();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 批量删除停车场Demo对象
     *
     * @param ids
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public AjaxResult deleteEtcTollDemoByIds(String ids) {
        if (StringUtils.isEmpty(ids) || Convert.toStrArray(ids).length == 0) {
            throw new BusinessException("ids编号集合不能为空");
        }
        SysUser sysUser = ShiroUtils.getSysUser();
        EtcTollDemo etcTollDemo = new EtcTollDemo();
        etcTollDemo.setDel(Constants.DEL_STATUS_NO);
        etcTollDemo.setIds(Convert.toStrArray(ids));
        etcTollDemo.setUpdateTime(DateUtils.getNowDate());
        etcTollDemo.setUpdateUserId(sysUser.getUserId().intValue());
        log.info("批量删除停车场Demo对象,请求参数:" + JSON.toJSONString(etcTollDemo));
        return etcTollDemoMapper.batchUpdateEtcTollDemo(etcTollDemo) > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    /**
     * 删除停车场Demo信息
     *
     * @param id
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public AjaxResult deleteEtcTollDemoById(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new BusinessException("id不能为空");
        }
        SysUser sysUser = ShiroUtils.getSysUser();
        EtcTollDemo etcTollDemo = new EtcTollDemo();
        etcTollDemo.setId(id);
        etcTollDemo.setDel(Constants.DEL_STATUS_NO);
        etcTollDemo.setUpdateTime(DateUtils.getNowDate());
        etcTollDemo.setUpdateUserId(sysUser.getUserId().intValue());
        log.info("删除停车场Demo信息,请求参数:" + JSON.toJSONString(etcTollDemo));
        return etcTollDemoMapper.updateByPrimaryKey(etcTollDemo) > 0 ? AjaxResult.success() : AjaxResult.error();
    }


    //校验编辑时候参数是否合法
    public void checkEditEtcTollDemoParams(EtcTollDemo etcTollDemo, String code) {
        //修改操作,要判断Id是否为空
        if (code.equals(Constants.SERVEICE_OPERATE_UPDATE)) {
            if (StringUtils.isEmpty(etcTollDemo.getId())) {
                throw new BusinessException("id不能为空");
            }
        }

        if (StringUtils.isEmpty(etcTollDemo.getName())) {
            throw new BusinessException("名称不能为空");
        }
        if (StringUtils.isEmpty(etcTollDemo.getPhone())) {
            throw new BusinessException("手机号不能为空");
        }
        if (StringUtils.isEmpty(etcTollDemo.getEmail())) {
            throw new BusinessException("邮箱不能为空");
        }
        if (Objects.isNull(etcTollDemo.getAge())) {
            throw new BusinessException("年龄不能为空");
        }
        if (StringUtils.isEmpty(etcTollDemo.getSex())) {
            throw new BusinessException("性别不能为空");
        }
        if (StringUtils.isEmpty(etcTollDemo.getServiceFile())) {
            throw new BusinessException("业务附件不能为空");
        }
        if (StringUtils.isEmpty(etcTollDemo.getAboutFile())) {
            throw new BusinessException("相关附件不能为空");
        }
    }
}