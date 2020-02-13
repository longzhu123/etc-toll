package com.ruoyi.etc.toll.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.etc.toll.domain.EtcTollDemo;
import com.ruoyi.etc.toll.service.EtcTollDemoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 停车场DemoController
 *
 * @author yangjie
 * @date 2020-01-18
 */
@Controller
@RequestMapping("/etcToll/demo")
public class EtcTollDemoController extends BaseController {
    private String prefix = "etcToll/demo" ;

    @Autowired
    private EtcTollDemoService etcTollDemoService;

    @RequiresPermissions("etcToll:demo:view")
    @GetMapping()
    public String demo() {
        return prefix + "/demo" ;
    }

    /**
     * 查询停车场Demo列表
     */
    @RequiresPermissions("etcToll:demo:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(EtcTollDemo etcTollDemo) {
        startPage();
        List<EtcTollDemo> list = etcTollDemoService.selectEtcTollDemoList(etcTollDemo);
        return getDataTable(list);
    }

    /**
     * 导出停车场Demo列表
     */
    @RequiresPermissions("etcToll:demo:export")
    @Log(title = "停车场Demo", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(EtcTollDemo etcTollDemo) {
        List<EtcTollDemo> list = etcTollDemoService.selectEtcTollDemoList(etcTollDemo);
        ExcelUtil<EtcTollDemo> util = new ExcelUtil<EtcTollDemo>(EtcTollDemo.class);
        return util.exportExcel(list, "demo");
    }

    /**
     * 新增停车场Demo
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add" ;
    }

    /**
     * 新增保存停车场Demo
     */
    @RequiresPermissions("etcToll:demo:add")
    @Log(title = "停车场Demo", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(EtcTollDemo etcTollDemo) {
        return etcTollDemoService.insertEtcTollDemo(etcTollDemo);
    }

    /**
     * 修改停车场Demo
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap) {
        EtcTollDemo etcTollDemo = etcTollDemoService.selectEtcTollDemoById(id);
        mmap.put("etcTollDemo", etcTollDemo);
        return prefix + "/edit" ;
    }

    /**
     * 修改保存停车场Demo
     */
    @RequiresPermissions("etcToll:demo:edit")
    @Log(title = "停车场Demo", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(EtcTollDemo etcTollDemo) {
        return etcTollDemoService.updateEtcTollDemo(etcTollDemo);
    }

    /**
     * 删除停车场Demo
     */
    @RequiresPermissions("etcToll:demo:remove")
    @Log(title = "停车场Demo", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return etcTollDemoService.deleteEtcTollDemoByIds(ids);
    }
}
