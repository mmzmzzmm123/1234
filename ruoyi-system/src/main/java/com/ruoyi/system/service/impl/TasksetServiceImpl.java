package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.exception.taskset.ExcelException;
import com.ruoyi.common.exception.taskset.InputException;
import com.ruoyi.system.domain.Task;
import com.ruoyi.system.domain.Taskset;
import com.ruoyi.system.mapper.TaskMapper;
import com.ruoyi.system.mapper.TasksetMapper;
import com.ruoyi.system.service.TasksetService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
* @author lenovo
* @description 针对表【taskset】的数据库操作Service实现
* @createDate 2023-12-07 02:55:47
*/
@Slf4j
@Service
public class TasksetServiceImpl extends ServiceImpl<TasksetMapper, Taskset>
    implements TasksetService{

    @Autowired
    TasksetMapper tasksetMapper;
    TaskMapper taskMapper;

    /**
     * 返回file中name的列号，没有返回-1
     * @param file， name
     * @throws IOException
     */
    @Override
    public int getIndexFromExcel(MultipartFile file, String name) throws IOException {
        int column = -1;
        try(InputStream inputStream = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(inputStream)){
            Sheet sheet = workbook.getSheetAt(0);
            Row headerRow = sheet.getRow(0);
            for (Cell cell : headerRow) {
                if (cell.getStringCellValue().trim().equalsIgnoreCase(name)) {
                    column = cell.getColumnIndex();
                    break;
                }
            }
        }
        return column;
    }

    /**
     * 返回file中第col列的空格数
     * @param file， col
     * @return int
     * @throws IOException
     */
    @Override
    public int checkBlank(MultipartFile file, int col) throws IOException {
        int blankCount = 0;
        try(InputStream inputStream = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(inputStream)){
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;
                Cell cell = row.getCell(col, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                if (cell == null || cell.getCellType() == CellType.BLANK) {
                    blankCount++;
                }
            }
        }
        return blankCount;
    }

    /**
     * 根据excel表中的第splitcol列创建任务集合，并给所有集合属于dept_id部门
     * @param file ,col_name, keyname, code
     * @return AjaxResult
     */
    @Override
    public int AddTasksetByExcel(MultipartFile file, String splitcol, int dept_id) {
        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            int column = getIndexFromExcel(file, splitcol);
            if (column == -1) {
                throw new ExcelException("COLUMN_NOT_FOUND", "找不到指令列");
            }
            List<String> allNameFromExcel = new ArrayList<>();
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;
                Cell cell = row.getCell(column);
                if (cell != null && cell.getCellType() != CellType.BLANK) {
                    allNameFromExcel.add(cell.toString());
                }
            }
            if (allNameFromExcel.isEmpty()) {
                throw new ExcelException("COLUMN_EMPTY", "指定列为空");
            }
            List<Taskset> allTaskset = tasksetMapper.selectAllTaskset();
            // 使用流方法从allTaskset中提取名字存储到不能重复的Set中
            Set<String> allNameFromSQL = allTaskset.stream()
                    .map(Taskset::getName)
                    .collect(Collectors.toSet());
            for (String taskName : allNameFromExcel) {
                if (!allNameFromSQL.contains(taskName)) {
                    Taskset newTaskset = new Taskset();
                    newTaskset.setName(taskName);
                    // 后端给前端全部可用的dept_id
                    newTaskset.setDeptId(dept_id);
                    tasksetMapper.insertTaskset(newTaskset);
                }
            }
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 根据excel创建任务
     * @param file
     * @param splitcol
     * @param keyname
     * @param code
     * @return AjaxResult
     * @throws IOException
     */
    @Override
    public int CreateTaskbyExcel(MultipartFile file, String splitcol, String keyname, String code) throws IOException {
        int col_split = getIndexFromExcel(file, splitcol);
        int col_key = getIndexFromExcel(file, keyname);
        if (col_key == -1 || col_split == -1){
            throw new ExcelException("COLUMN_NOT_FOUND", "找不到指令列");
        }
        if (checkBlank(file, col_key)!=0 || checkBlank(file, col_split)!=0){
            throw new ExcelException("COLUMN_BLANK", "指定列存在空数据");
        }
        try(InputStream inputStream = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(inputStream)){
            Sheet sheet = workbook.getSheetAt(0);
            List<Taskset> allTasksets = tasksetMapper.selectAllTaskset();
            Set<String> existingNames = allTasksets.stream()
                    .map(Taskset::getName)
                    .collect(Collectors.toSet());
            for(Row row : sheet){
                if(row.getRowNum()==0) continue;
                Cell cell_split = row.getCell(col_split);
                Cell cell_key = row.getCell(col_key);
                if(cell_split !=null && cell_split.getCellType()!=CellType.BLANK){
                    String taskname = cell_split.getStringCellValue();
                    Task newTask = new Task();
                    Long uniqueID = UUID.randomUUID().getMostSignificantBits(); //前64
                    newTask.setTaskId(uniqueID);
                    int tasksetId = tasksetMapper.selectTasksetIdByName(taskname);
                    if (tasksetId == 0){
                        Taskset newTaskset = new Taskset();
                        newTaskset.setName(taskname);
                        tasksetMapper.insertTaskset(newTaskset);
                        tasksetId = newTaskset.getTasksetId();
                        System.out.println("创建了新的taskset: " + taskname + "但还未指定相关数据，请即使设置");
                    }
                    newTask.setTasksetId(tasksetId);
                    newTask.setTaskAuthCode(code);
                    newTask.setTaskKey(cell_key.getStringCellValue());
                    //other_info 未添加
                    Long orderID = UUID.randomUUID().getMostSignificantBits(); //前64
                    newTask.setOrderId(orderID);
                    newTask.setTaskStatus(0); // 0未完成 1完成
                    taskMapper.insertTask(newTask);

                }
            }
            return 1;
        } catch (Exception e){
            return 0;
        }
    }


    @Override
    public Taskset selectTasksetById(int id) {
        if (id<0){
            throw new InputException("INPUT_ERROR","输入id小于0");
        }
        return tasksetMapper.selectTasksetById(id);
    }

    @Override
    public List<Taskset> selectAllTaskset() {
        return tasksetMapper.selectAllTaskset();
    }

    @Override
    public AjaxResult insertTaskset(Taskset taskset) {
        if (taskset.getTasksetId()==null){
            throw new InputException("INPUT_ERROR","输入tasksetid为空");
        }
        int affectedRows = tasksetMapper.insertTaskset(taskset);
        if (affectedRows != 0 && tasksetMapper.selectTasksetById(taskset.getTasksetId()) != null) {
            return AjaxResult.success("新增任务集合成功");
        } else {
            throw new DataAccessException("新增任务集合失败") {};
        }
    }

    @Override
    public AjaxResult updateTaskset(Taskset taskset) {
        if (tasksetMapper.selectTasksetById(taskset.getTasksetId())==null){
            throw new InputException("INPUT_ERROR","找不到要修改的任务集");
        }
        int affectedRows = tasksetMapper.updateTasksetById(taskset);
        if (affectedRows != 0) {
            return AjaxResult.success("更新任务集合成功");
        } else {
            throw new DataAccessException("更新任务集合失败") {};
        }
    }

    @Override
    public AjaxResult deleteTasksetById(int id) {
        if (tasksetMapper.selectTasksetById(id)==null){
            throw new InputException("INPUT_ERROR","找不到要删除的任务集");
        }
        int affectedRows = tasksetMapper.deleteTasksetById(id);
        if (affectedRows != 0) {
            return AjaxResult.success("删除任务集合成功");
        } else {
            throw new DataAccessException("删除任务集合失败") {};
        }
    }

}




