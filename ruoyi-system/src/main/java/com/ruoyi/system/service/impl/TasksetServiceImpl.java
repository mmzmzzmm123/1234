package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.controller.TasksetController;
import com.ruoyi.system.domain.Task;
import com.ruoyi.system.domain.Taskset;
import com.ruoyi.system.mapper.TaskMapper;
import com.ruoyi.system.service.TasksetService;
import com.ruoyi.system.mapper.TasksetMapper;
import com.sun.corba.se.spi.orbutil.threadpool.Work;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@Service
public class TasksetServiceImpl extends ServiceImpl<TasksetMapper, Taskset>
    implements TasksetService{
    private static final Logger logger = LoggerFactory.getLogger(TasksetServiceImpl.class);
    @Autowired
    TasksetMapper tasksetMapper;
    TaskMapper taskMapper;

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


    @Override
    public int AddTasksetByExcel(MultipartFile file, String splitcol, int dept_id) {
        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            Row headerRow = sheet.getRow(0);
            int column = getIndexFromExcel(file, splitcol);
            if (column == -1) {
                log.error("没有找到指定列");
                return 0;
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
                log.error("指定列没有数据");
                return 0;
            }
            List<Taskset> allTaskset = tasksetMapper.selectAllTaskset();
            Set<String> allNameFromSQL = allTaskset.stream()
                    .map(Taskset::getName)
                    .collect(Collectors.toSet());
            for (String taskName : allNameFromExcel) {
                if (!allNameFromSQL.contains(taskName)) {
                    Taskset newTaskset = new Taskset();
                    newTaskset.setName(taskName);
                    newTaskset.setDeptId(dept_id);
                    tasksetMapper.insertTaskset(newTaskset);
                }
            }
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int CreateTaskbyExcel(MultipartFile file, String splitcol, String keyname, String code) throws IOException {
        int col_split = getIndexFromExcel(file, splitcol);
        int col_key = getIndexFromExcel(file, keyname);
        if (col_key == -1 || col_split == -1){
            return 0;
        }
        if (checkBlank(file, col_key)!=0 || checkBlank(file, col_split)!=0){
            log.error("传入excel数据不完整");
            return 0;
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
        return tasksetMapper.selectTasksetById(id);
    }


    @Override
    public List<Taskset> selectAllTaskset() {
        return tasksetMapper.selectAllTaskset();
    }

    @Override
    public int insertTaskset(Taskset taskset) {
        int affectedRows = tasksetMapper.insertTaskset(taskset);
        if(affectedRows != 0 && tasksetMapper.selectTasksetById(taskset.getTasksetId()) != null){
            return 1;
        }
        return 0;
    }

    @Override
    public int updateTaskset(int id, Taskset taskset) {
        int affectedRows = tasksetMapper.updateTasksetById(id, taskset);
        if(affectedRows != 0){
            return 1;
        }
        return 0;
    }

    @Override
    public int deleteTasksetById(int id) {
        int affectedRows = tasksetMapper.deleteTasksetById(id);
        if(affectedRows != 0){
            return 1;
        }
        return 0;
    }
}




