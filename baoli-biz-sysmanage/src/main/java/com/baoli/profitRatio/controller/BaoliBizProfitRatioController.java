package com.baoli.profitRatio.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import com.baoli.profitRatio.domain.BaoliBizProfitRatio;
import com.baoli.profitRatio.service.IBaoliBizProfitRatioService;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 利润率Controller
 * 
 * @author niujs
 * @date 2024-05-20
 */
@RestController
@RequestMapping("/profitRatio/profitRatio")
public class BaoliBizProfitRatioController extends BaseController
{
    @Autowired
    private IBaoliBizProfitRatioService baoliBizProfitRatioService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    /**
     * 查询利润率列表
     */
    @PreAuthorize("@ss.hasPermi('profitRatio:profitRatio:list')")
    @GetMapping("/list")
    public TableDataInfo list(BaoliBizProfitRatio baoliBizProfitRatio)
    {
        startPage();
        List<Map<String,Object>> list = baoliBizProfitRatioService.selectBaoliBizProfitRatioList(baoliBizProfitRatio);
        return getDataTable(list);
    }

    @Anonymous
    //    批量导入数据库
    @PostMapping("/importData")
    public String excelUpload(Long id,@RequestParam("file") MultipartFile file) throws Exception{
        //excel导入
        ImportParams params = new ImportParams();
        String fileName = file.getOriginalFilename();
//        导入Excel表中表名所占行
        params.setTitleRows(1);
//        导入Excel表中属性信息所占行
        params.setHeadRows(1);

        InputStream inputStream = file.getInputStream();
        Workbook workbook =WorkbookFactory.create(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        String sql =getCellColumnName(sheet);
        jdbcTemplate.update(sql,id);
//        for (Row row : sheet) {
//            for (Cell cell : row) {
//                System.out.println(row.getRowNum()+","+cell.getColumnIndex());
//                System.out.println(getCellColumnName(row.getRowNum(),cell.getColumnIndex()));
//            }
//        }
        inputStream.close();
        return "1";
    }

    private String getCellColumnName(Sheet sheet){
        String map="3,8,a115;" +
                "4,8,a116;" +
                "5,8,a117;" +
                "6,4,a1;" +
                "6,5,a57;" +
                "6,8,a118;" +
                "7,8,a119;" +
                "8,8,a120;" +
                "9,8,a121;" +
                "10,4,a2;" +
                "10,5,a58;" +
                "10,8,a122;" +
                "11,4,a3;" +
                "11,5,a59;" +
                "11,8,a123;" +
                "12,4,a4;" +
                "12,5,a60;" +
                "12,8,a124;" +
                "13,4,a5;" +
                "13,5,a61;" +
                "13,8,a125;" +
                "14,4,a6;" +
                "14,5,a62;" +
                "14,8,a126;" +
                "15,4,a7;" +
                "15,5,a63;" +
                "15,8,a127;" +
                "16,4,a8;" +
                "16,5,a64;" +
                "16,8,a128;" +
                "17,4,a9;" +
                "17,5,a65;" +
                "17,8,a129;" +
                "18,4,a10;" +
                "18,5,a66;" +
                "18,8,a130;" +
                "19,8,a131;" +
                "20,4,a11;" +
                "20,5,a67;" +
                "20,8,a132;" +
                "21,4,a12;" +
                "21,5,a68;" +
                "21,8,a133;" +
                "22,4,a13;" +
                "22,5,a69;" +
                "22,8,a134;" +
                "23,4,a14;" +
                "23,5,a70;" +
                "23,8,a135;" +
                "24,4,a15;" +
                "24,5,a71;" +
                "24,8,a136;" +
                "25,4,a16;" +
                "25,5,a72;" +
                "25,8,a137;" +
                "26,4,a17;" +
                "26,5,a73;" +
                "26,8,a138;" +
                "27,8,a139;" +
                "28,4,a18;" +
                "28,5,a74;" +
                "28,8,a140;" +
                "29,4,a19;" +
                "29,5,a75;" +
                "29,8,a141;" +
                "30,4,a20;" +
                "30,5,a76;" +
                "30,8,a142;" +
                "31,4,a21;" +
                "31,5,a77;" +
                "31,8,a143;" +
                "32,4,a22;" +
                "32,5,a78;" +
                "32,8,a144;" +
                "33,4,a23;" +
                "33,5,a79;" +
                "33,8,a145;" +
                "34,4,a24;" +
                "34,5,a80;" +
                "34,8,a146;" +
                "35,4,a25;" +
                "35,5,a81;" +
                "35,8,a147;" +
                "36,4,a26;" +
                "36,5,a82;" +
                "36,8,a148;" +
                "37,4,a27;" +
                "37,5,a83;" +
                "37,8,a149;" +
                "38,4,a28;" +
                "38,5,a84;" +
                "38,8,a150;" +
                "39,4,a29;" +
                "39,5,a85;" +
                "39,8,a151;" +
                "40,4,a30;" +
                "40,5,a86;" +
                "40,8,a152;" +
                "41,4,a31;" +
                "41,5,a87;" +
                "41,8,a153;" +
                "42,4,a32;" +
                "42,5,a88;" +
                "42,8,a154;" +
                "43,4,a33;" +
                "43,5,a89;" +
                "43,8,a155;" +
                "44,4,a34;" +
                "44,5,a90;" +
                "44,8,a156;" +
                "45,4,a35;" +
                "45,5,a91;" +
                "45,8,a157;" +
                "46,4,a36;" +
                "46,5,a92;" +
                "46,8,a158;" +
                "47,4,a37;" +
                "47,5,a93;" +
                "47,8,a159;" +
                "48,4,a38;" +
                "48,5,a94;" +
                "48,8,a160;" +
                "49,4,a39;" +
                "49,5,a95;" +
                "49,8,a161;" +
                "50,4,a40;" +
                "50,5,a96;" +
                "50,8,a162;" +
                "51,4,a41;" +
                "51,5,a97;" +
                "51,8,a163;" +
                "52,4,a42;" +
                "52,5,a98;" +
                "52,8,a164;" +
                "53,4,a43;" +
                "53,5,a99;" +
                "53,8,a165;" +
                "54,4,a44;" +
                "54,5,a100;" +
                "54,8,a166;" +
                "55,4,a45;" +
                "55,5,a101;" +
                "55,8,a167;" +
                "56,8,a168;" +
                "57,5,a102;" +
                "57,8,a169;" +
                "58,4,a46;" +
                "58,5,a103;" +
                "58,8,a170;" +
                "59,4,a47;" +
                "59,5,a104;" +
                "59,8,a171;" +
                "60,5,a105;" +
                "60,8,a172;" +
                "61,4,a48;" +
                "61,5,a106;" +
                "61,8,a173;" +
                "62,4,a49;" +
                "62,5,a107;" +
                "62,8,a174;" +
                "63,8,a175;" +
                "64,4,a50;" +
                "64,5,a108;" +
                "64,8,a176;" +
                "65,4,a51;" +
                "65,5,a109;" +
                "65,8,a177;" +
                "66,4,a52;" +
                "66,5,a110;" +
                "66,8,a178;" +
                "67,4,a53;" +
                "67,5,a111;" +
                "67,8,a179;" +
                "68,4,a54;" +
                "68,5,a112;" +
                "68,8,a180;" +
                "69,8,a181;" +
                "70,4,a55;" +
                "70,5,a113;" +
                "70,8,a182;" +
                "71,4,a56;" +
                "71,5,a114;" +
                "71,8,a183";
        String result = null;
        StringBuffer buf = new StringBuffer();
        String [] columns = map.split(";");
        for (String column : columns) {
            String field[] = column.split(",");
            double val = sheet.getRow(Integer.valueOf(field[0])).getCell(Integer.valueOf(field[1])).getNumericCellValue();
            buf.append(",").append(field[2]).append(" = ").append(val);
        }
        if(buf.toString().length()>1){
            result = buf.toString().substring(1);
            result = "update baoli_biz_profit_ratio set "+ result + " where id = ?";
        }
        return result;
    }

    @Anonymous
    @GetMapping("/templateExport")
    public void templateExport(HttpServletResponse response, BaoliBizProfitRatio baoliBizProfitRatio) {
        Map<String,Object> pr = baoliBizProfitRatioService.selectBaoliBizProfitRatioById(baoliBizProfitRatio.getId());
        Map<String,Object> map =new HashMap<>(1);
        for(int i=1;i<=183;i++){
            map.put("a"+i,pr.get("a"+i));
        }

        map.put("regionName",baoliBizProfitRatio.getRegionName());
        map.put("year",pr.get("year"));
        map.put("month",pr.get("month"));
        try {
            Workbook workbook = WorkbookFactory.create(getClass().getClassLoader().getResourceAsStream("template/temp.xls"));
            TemplateExportParams params = new TemplateExportParams();
            params.setTemplateWb(workbook);
            params.setScanAllsheet(true);
            ExcelExportUtil.exportExcel(params, map);
            OutputStream outputStream = response.getOutputStream();
            workbook.setSheetName(0, baoliBizProfitRatio.getRegionName());
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderRight(BorderStyle.THIN);
            //设置编码格式
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            //设置内容类型
            response.setContentType("application/octet-stream");
            //设置头及文件命名。
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("export.xls", StandardCharsets.UTF_8.name()));
            workbook.setForceFormulaRecalculation(true);
            workbook.write(outputStream);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 获取利润率详细信息
     */
    @PreAuthorize("@ss.hasPermi('profitRatio:profitRatio:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(baoliBizProfitRatioService.selectBaoliBizProfitRatioById(id));
    }

    /**
     * 新增利润率
     */
    @PreAuthorize("@ss.hasPermi('profitRatio:profitRatio:add')")
    @Log(title = "利润率", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BaoliBizProfitRatio baoliBizProfitRatio)
    {
        return toAjax(baoliBizProfitRatioService.insertBaoliBizProfitRatio(baoliBizProfitRatio));
    }

    /**
     * 修改利润率
     */
    @PreAuthorize("@ss.hasPermi('profitRatio:profitRatio:edit')")
    @Log(title = "利润率", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BaoliBizProfitRatio baoliBizProfitRatio)
    {
        return toAjax(baoliBizProfitRatioService.updateBaoliBizProfitRatio(baoliBizProfitRatio));
    }

    /**
     * 删除利润率
     */
    @PreAuthorize("@ss.hasPermi('profitRatio:profitRatio:remove')")
    @Log(title = "利润率", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(baoliBizProfitRatioService.deleteBaoliBizProfitRatioByIds(ids));
    }
}
