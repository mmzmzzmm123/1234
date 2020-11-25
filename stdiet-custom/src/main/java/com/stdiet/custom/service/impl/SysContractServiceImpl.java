package com.stdiet.custom.service.impl;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.*;
import com.stdiet.common.utils.DateUtils;
import com.stdiet.custom.domain.SysContract;
import com.stdiet.custom.mapper.SysContractMapper;
import com.stdiet.custom.page.PdfProcessInfo;
import com.stdiet.custom.service.ISysContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


/**
 * 合同Service业务层处理
 *
 * @author wonder
 * @date 2020-10-23
 */
@Service
public class SysContractServiceImpl implements ISysContractService {
    @Autowired
    private SysContractMapper sysContractMapper;

    /**
     * 查询合同
     *
     * @param id 合同ID
     * @return 合同
     */
    @Override
    public SysContract selectSysContractById(Long id) {
        return sysContractMapper.selectSysContractById(id);
    }

    /**
     * 查询合同列表
     *
     * @param sysContract 合同
     * @return 合同
     */
    @Override
    public List<SysContract> selectSysContractList(SysContract sysContract) {
        return sysContractMapper.selectSysContractList(sysContract);
    }

    /**
     * 新增合同
     *
     * @param sysContract 合同
     * @return 结果
     */
    @Override
    public int insertSysContract(SysContract sysContract) {
        sysContract.setId(DateUtils.getNowDate().getTime());
        sysContract.setCreateTime(DateUtils.getNowDate());
        sysContract.setStatus("no");
        sysContract.setPath("/f/contract/" + sysContract.getId());
        return sysContractMapper.insertSysContract(sysContract);
    }

    /**
     * 修改合同
     *
     * @param sysContract 合同
     * @return 结果
     */
    @Override
    public int updateSysContract(SysContract sysContract) {
        sysContract.setUpdateTime(DateUtils.getNowDate());
        return sysContractMapper.updateSysContract(sysContract);
    }

    /**
     * 批量删除合同
     *
     * @param ids 需要删除的合同ID
     * @return 结果
     */
    @Override
    public int deleteSysContractByIds(Long[] ids) {
        return sysContractMapper.deleteSysContractByIds(ids);
    }

    /**
     * 删除合同信息
     *
     * @param id 合同ID
     * @return 结果
     */
    @Override
    public int deleteSysContractById(Long id) {
        return sysContractMapper.deleteSysContractById(id);
    }

    @Override
    public PdfProcessInfo signContract(SysContract sysContract) {
//        String templatePath = "/Users/wonder/Documents/Workspaces/java/RuoYi-Vue/running/pdf/contract.pdf";
//        String filePath = "/Users/wonder/Documents/Workspaces/java/RuoYi-Vue/running" + sysContract.getPath();
        String templatePath = "/home/workspace/ShengTangManage/running/pdf/contract.pdf";
        String filePath = "/home/web/manage.shengtangdiet.com" + sysContract.getPath();

        PdfReader reader;
        FileOutputStream out;
        ByteArrayOutputStream bos;
        PdfStamper stamper;

        PdfProcessInfo info = new PdfProcessInfo();

        try {
            out = new FileOutputStream(filePath);// 输出流到新的pdf,没有b2.pdf时会创建
            reader = new PdfReader(templatePath);// 读取pdf模板
            bos = new ByteArrayOutputStream();
            stamper = new PdfStamper(reader, bos);
            AcroFields form = stamper.getAcroFields();

            form.addSubstitutionFont(BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED));
            form.setField("signName", sysContract.getSignName(), true);
            form.setField("serveTime", sysContract.getServeTimeStr(), true);
            form.setField("tutor", sysContract.getTutor(), true);
            form.setField("moneyUpper", sysContract.getAmountUpper(), true);
            form.setField("money", sysContract.getAmount().intValue() + "", true);
            form.setField("phone", sysContract.getPhone(), true);
            form.setField("promise", sysContract.getServePromise(), true);
            form.setField("date", DateUtils.getDate(), true);
            form.setField("cusId", sysContract.getCusId(), true);
            String remark = "";
            if (sysContract.getRemark() != null && !sysContract.getRemark().equals("")) {
                remark = "（备注：" + sysContract.getRemark() + ")";
            }
            form.setField("remark", remark, true);


            stamper.setFormFlattening(true);// 如果为false那么生成的PDF文件还能编辑，一定要设为true
            stamper.close();

            Document doc = new Document();

            PdfCopy copy = new PdfCopy(doc, out);
            doc.open();
            PdfImportedPage importPage = null;
            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                importPage = copy
                        .getImportedPage(new PdfReader(bos.toByteArray()), i);
                copy.addPage(importPage);
            }
            doc.close();

            Runtime.getRuntime().exec("chmod 644 " + filePath);

            info.setCode(1);
            info.setMsg("success");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            info.setCode(0);
            info.setMsg(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            info.setCode(0);
            info.setMsg(e.getMessage());
        } catch (DocumentException e) {
            e.printStackTrace();
            info.setCode(0);
            info.setMsg(e.getMessage());
        }
        return info;
    }
}
