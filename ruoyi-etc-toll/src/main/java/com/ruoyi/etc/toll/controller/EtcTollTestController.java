package com.ruoyi.etc.toll.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.etc.toll.domain.EtcTollTest;
import com.ruoyi.etc.toll.service.EtcTollTestService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 停车场测试Controller
 *
 * @author yangjie
 * @date 2020-01-06
 */
@Controller
@RequestMapping("/etcToll/test")
public class EtcTollTestController extends BaseController {
    private String prefix = "etcToll/test";

    @Autowired
    private EtcTollTestService etcTollTestService;

    @RequiresPermissions("etcToll:test:view")
    @GetMapping()
    public String test() {
        return prefix + "/test";
    }

    /**
     * 查询停车场测试列表
     */
    @RequiresPermissions("etcToll:test:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(EtcTollTest etcTollTest) {
        startPage();
        List<EtcTollTest> list = etcTollTestService.selectEtcTollTestList(etcTollTest);
        return getDataTable(list);
    }

    /**
     * 导出停车场测试列表
     */
    @RequiresPermissions("etcToll:test:export")
    @Log(title = "停车场测试", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(EtcTollTest etcTollTest) {
        List<EtcTollTest> list = etcTollTestService.selectEtcTollTestList(etcTollTest);
        ExcelUtil<EtcTollTest> util = new ExcelUtil<EtcTollTest>(EtcTollTest.class);
        return util.exportExcel(list, "test");
    }

    /**
     * 新增停车场测试
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存停车场测试
     */
    @RequiresPermissions("etcToll:test:add")
    @Log(title = "停车场测试", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(EtcTollTest etcTollTest) {
        return etcTollTestService.insertEtcTollTest(etcTollTest);
    }

    /**
     * 修改停车场测试
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap) {
        EtcTollTest etcTollTest = etcTollTestService.selectEtcTollTestById(id);
        mmap.put("etcTollTest", etcTollTest);
        return prefix + "/edit";
    }

    /**
     * 修改保存停车场测试
     */
    @RequiresPermissions("etcToll:test:edit")
    @Log(title = "停车场测试", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(EtcTollTest etcTollTest) {
        return etcTollTestService.updateEtcTollTest(etcTollTest);
    }

    /**
     * 删除停车场测试
     */
    @RequiresPermissions("etcToll:test:remove")
    @Log(title = "停车场测试", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return etcTollTestService.deleteEtcTollTestByIds(ids);
    }
}
