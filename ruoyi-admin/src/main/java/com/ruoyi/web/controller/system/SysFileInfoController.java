package com.ruoyi.web.controller.system;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysFileInfo;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.service.ISysFileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/sysFileInfo")
public class SysFileInfoController {

    @Autowired
    private ISysFileInfoService sysFileInfoService;


    @RequestMapping("/batchAddSysFileInfo")
    public AjaxResult batchAddSysFileInfo(MultipartFile[] files) {
        SysUser sysUser = ShiroUtils.getSysUser();
        return sysFileInfoService.batchAddSysFileInfo(files, sysUser);
    }

    @RequestMapping("/delSysFileInfo")
    public AjaxResult delSysFileInfo() {
        return AjaxResult.success();
    }

    @RequestMapping("/getSysFileInfoByIds")
    public List<SysFileInfo> getSysFileInfoByIds(@RequestParam("ids") String ids) {
        return sysFileInfoService.selectSysFileInfoByIds(ids);
    }
}
