package invest.lixinger.utils.excel.excelUtils;

import lombok.Data;

@Data
public class ExcelUtils_1_variable {
    //%%%%%%%%-------字段部分 开始----------%%%%%%%%%
    private String excelPath = "";
    //设定开始读取的位置，默认为0
    private int startReadRow = START_READ_ROW;
    //设定结束读取的位置，默认为0，用负数来表示倒数第n行
    private int endReadRow = END_READ_ROW;
    //设定开始比较的列位置，默认为0
    private int comparePos = COMPARE_POS;
    //设定汇总的文件是否需要替换，默认为true
    private boolean isOverWrite = IS_OVERWRITE;
    //设定是否需要比较，默认为true(仅当不覆写目标内容是有效，即isOverWrite=false时有效)
    private boolean isNeedCompare = IS_NEED_COMPARE;
    //设定是否只操作第一个sheet
    private boolean onlyReadOneSheet = IS_ONE_SHEET;
    //设定操作的sheet在索引值
    private int selectedSheetIdx = SELECTED_SHEET;
    //设定操作的sheet的名称
    private String selectedSheetName = "";
    //设定开始读取的sheet，默认为0
    private int startSheetIdx = READ_START_SHEET;
    //设定结束读取的sheet，默认为0，用负数来表示倒数第n行
    private int endSheetIdx = READ_END_SHEET;
    //是否打印
    private boolean isPrintMsg = IS_PRINT_MSG;

    //%%%%%%%%-------字段部分 结束----------%%%%%%%%%

    //%%%%%%%%-------常量部分 开始----------%%%%%%%%%
    // 默认的开始读取的行位置为第一行（索引值为0）
    public final static int START_READ_ROW = 0;
    // 默认结束读取的行位置为最后一行（索引值=0，用负数来表示倒数第n行）
    public final static int END_READ_ROW = 0;
    // 默认Excel内容的开始比较列位置为第一列（索引值为0）
    public final static int COMPARE_POS = 0;
    // 默认读取第一个sheet中（只有当ONLY_ONE_SHEET = true时有效）
    public final static int SELECTED_SHEET = 0;
    // 默认从第一个sheet开始读取（索引值为0）
    public final static int READ_START_SHEET = 0;
    // 默认在最后一个sheet结束读取（索引值=0，用负数来表示倒数第n行）
    public final static int READ_END_SHEET = 0;
    // 默认多文件合并的时需要做内容比较（相同的内容不重复出现）
    public final static boolean IS_NEED_COMPARE = true;
    // 默认多文件合并的新文件遇到名称重复时，进行覆盖
    public final static boolean IS_OVERWRITE = true;
    // 默认只操作一个sheet
    public final static boolean IS_ONE_SHEET = true;
    // 默认打印各种信息
    public final static boolean IS_PRINT_MSG = true;
    //%%%%%%%%-------常量部分 结束----------%%%%%%%%%
}
