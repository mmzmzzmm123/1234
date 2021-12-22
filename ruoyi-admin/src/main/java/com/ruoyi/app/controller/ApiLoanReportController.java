package com.ruoyi.app.controller;

import cn.hutool.core.util.StrUtil;
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

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
                bdcq.setBdccqzt(item.getBdccqzt());
                bdcq.setBdcgyfs(item.getBdcgyfs());
                bdcq.setBdcjzmj(item.getBdcjzmj());

                // 由于治理的表结构存在多个产权证号，需要依次脱敏
                String[] split = item.getBdcqzh().split(",");
                for (int i=0;i<split.length;i++){
                    split[i] = StrUtil.hide(split[i],8,20);
                }
                bdcq.setBdcqzh(StringUtils.join(split,","));

                bdcq.setBdczl(StrUtil.hide(item.getBdczl(),10,40));
                bdcqSet.add(bdcq);
            }
            bzkZhdPfmxmxb1.setBdcqs(bdcqSet);

            //证件号
            bzkZhdPfmxmxb1.setZjhm(StrUtil.hide(bzkZhdPfmxmxb1.getZjhm(),6,16));

            creditReport.setBzkZhdPfmxmxb(bzkZhdPfmxmxb1);
        }

        if (bzkZhdZrjgmxbs!=null && bzkZhdZrjgmxbs.size()>0){

            BzkZhdZrjgmxb bzkZhdZrjgmxbResult = bzkZhdZrjgmxbs.get(0);
            //证件号
            bzkZhdZrjgmxbResult.setZjhm(StrUtil.hide(bzkZhdZrjgmxbResult.getZjhm(),6,16));

            creditReport.setBzkZhdZrjgmxb(bzkZhdZrjgmxb);
        }

        if (ztkZhdPfmxjgbs!=null && ztkZhdPfmxjgbs.size()>0){
            ZtkZhdPfmxjgb ztkZhdPfmxjgbResult = ztkZhdPfmxjgbs.get(0);
            ztkZhdPfmxjgbResult.setZjhm(StrUtil.hide(ztkZhdPfmxjgbResult.getZjhm(),6,16));
            creditReport.setZtkZhdPfmxjgb(ztkZhdPfmxjgbResult);
        }

        if (ztkZhdZrjgjgbs!=null && ztkZhdZrjgjgbs.size()>0){
            ZtkZhdZrjgjgb ztkZhdZrjgjgbResult = ztkZhdZrjgjgbs.get(0);
            ztkZhdZrjgjgbResult.setZjhm(StrUtil.hide(ztkZhdZrjgjgbResult.getZjhm(),6,16));
            creditReport.setZtkZhdZrjgjgb(ztkZhdZrjgjgbResult);
        }

        return AjaxResult.success(creditReport);
    }
}
