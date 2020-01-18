package com.ruoyi.etc.toll.mapper;

import com.ruoyi.common.baseMapper.BaseMapper;
import com.ruoyi.etc.toll.domain.EtcTollTest;

import java.util.List;

/**
 * 停车场测试Mapper
 *
 * @author yangjie
 * @date 2020-01-06
 */
public interface EtcTollTestMapper extends BaseMapper<EtcTollTest> {

    /**
     * 查询停车场测试列表
     * @param etcTollTest
     */
    List<EtcTollTest> selectEtcTollTestList(EtcTollTest etcTollTest);


    /**
     * 批量修改停车场测试
     * @param etcTollTest
     */
    int batchUpdateEtcTollTest(EtcTollTest etcTollTest);

}
