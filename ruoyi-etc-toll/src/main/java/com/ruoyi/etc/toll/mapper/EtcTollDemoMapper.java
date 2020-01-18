package com.ruoyi.etc.toll.mapper;

import com.ruoyi.common.baseMapper.BaseMapper;
import com.ruoyi.etc.toll.domain.EtcTollDemo;

import java.util.List;

/**
 * 停车场DemoMapper
 *
 * @author yangjie
 * @date 2020-01-18
 */
public interface EtcTollDemoMapper extends BaseMapper<EtcTollDemo> {

    /**
     * 查询停车场Demo列表
     *
     * @param etcTollDemo
     */
    List<EtcTollDemo> selectEtcTollDemoList(EtcTollDemo etcTollDemo);


    /**
     * 批量修改停车场Demo
     *
     * @param etcTollDemo
     */
    int batchUpdateEtcTollDemo(EtcTollDemo etcTollDemo);

}
