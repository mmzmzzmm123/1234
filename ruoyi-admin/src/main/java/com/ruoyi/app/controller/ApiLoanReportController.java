package com.ruoyi.app.controller;

import cn.shuibo.annotation.Decrypt;
import cn.shuibo.annotation.Encrypt;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.BzkZhdPfmxmxb;
import com.ruoyi.system.domain.BzkZhdZrjgmxb;
import com.ruoyi.system.domain.ZtkZhdPfmxjgb;
import com.ruoyi.system.domain.ZtkZhdZrjgjgb;
import com.ruoyi.system.domain.model.CreditReport;
import com.ruoyi.system.domain.vo.Bdcq;
import com.ruoyi.system.service.IBzkZhdPfmxmxbService;
import com.ruoyi.system.service.IBzkZhdZrjgmxbService;
import com.ruoyi.system.service.IZtkZhdPfmxjgbService;
import com.ruoyi.system.service.IZtkZhdZrjgjgbService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * 企业贷款信息Controller
 * 
 * @author genius
 * @date 2021-07-13
 */
@RestController
@Api(value = "企业贷款信息接口")
@RequestMapping("/api/loan")
public class ApiLoanReportController extends BaseController
{

    @Autowired
    private IBzkZhdZrjgmxbService bzkZhdZrjgmxbService;
    @Autowired
    private IBzkZhdPfmxmxbService bzkZhdPfmxmxbService;
    @Autowired
    private IZtkZhdZrjgjgbService ztkZhdZrjgjgbService;
    @Autowired
    private IZtkZhdPfmxjgbService ztkZhdPfmxjgbService;

    @Encrypt
    @Decrypt
    @PostMapping("/report")
    public AjaxResult getCreditReport(@RequestBody Map<String,String> tyshxydmMap){

        String tyshxydm = tyshxydmMap.get("tyshxydm");

        CreditReport creditReport = new CreditReport();

        ZtkZhdPfmxjgb ztkZhdPfmxjgb = new ZtkZhdPfmxjgb();
        ztkZhdPfmxjgb.setTyshxydm(tyshxydm);

        ZtkZhdZrjgjgb ztkZhdZrjgjgb = new ZtkZhdZrjgjgb();
        ztkZhdZrjgjgb.setTyshxydm(tyshxydm);

        BzkZhdPfmxmxb bzkZhdPfmxmxb = new BzkZhdPfmxmxb();
        bzkZhdPfmxmxb.setTyshxydm(tyshxydm);

        BzkZhdZrjgmxb bzkZhdZrjgmxb = new BzkZhdZrjgmxb();
        bzkZhdZrjgmxb.setTyshxydm(tyshxydm);

        // 有多条数据
        List<BzkZhdPfmxmxb> bzkZhdPfmxmxbs = bzkZhdPfmxmxbService.selectBzkZhdPfmxmxbList(bzkZhdPfmxmxb);
        List<BzkZhdZrjgmxb> bzkZhdZrjgmxbs = bzkZhdZrjgmxbService.selectBzkZhdZrjgmxbList(bzkZhdZrjgmxb);
        List<ZtkZhdPfmxjgb> ztkZhdPfmxjgbs = ztkZhdPfmxjgbService.selectZtkZhdPfmxjgbList(ztkZhdPfmxjgb);
        List<ZtkZhdZrjgjgb> ztkZhdZrjgjgbs = ztkZhdZrjgjgbService.selectZtkZhdZrjgjgbList(ztkZhdZrjgjgb);

        if (bzkZhdPfmxmxbs!=null && bzkZhdPfmxmxbs.size()>0){

            BzkZhdPfmxmxb bzkZhdPfmxmxb1 = bzkZhdPfmxmxbs.get(0);

            Set<Bdcq> bdcqSet = new HashSet<>();
            for (BzkZhdPfmxmxb item : bzkZhdPfmxmxbs){
                if (StringUtils.isEmpty(item.getBdcqzh())){
                    continue;
                }
                Bdcq bdcq = new Bdcq();
                bdcq.setBdcqzh(item.getBdcqzh());
                bdcq.setBdccqzt(item.getBdccqzt());
                bdcq.setBdcgyfs(item.getBdcgyfs());
                bdcq.setBdcjzmj(item.getBdcjzmj());
                bdcq.setBdczl(item.getBdczl());
                bdcqSet.add(bdcq);
            }
            bzkZhdPfmxmxb1.setBdcqs(bdcqSet);
            creditReport.setBzkZhdPfmxmxb(bzkZhdPfmxmxb1);
        }

        if (bzkZhdZrjgmxbs!=null && bzkZhdZrjgmxbs.size()>0){
            creditReport.setBzkZhdZrjgmxb(bzkZhdZrjgmxbs.get(0));
        }

        if (ztkZhdPfmxjgbs!=null && ztkZhdPfmxjgbs.size()>0){
            creditReport.setZtkZhdPfmxjgb(ztkZhdPfmxjgbs.get(0));
        }

        if (ztkZhdZrjgjgbs!=null && ztkZhdZrjgjgbs.size()>0){
            creditReport.setZtkZhdZrjgjgb(ztkZhdZrjgjgbs.get(0));
        }

        return AjaxResult.success(creditReport);
    }
}
