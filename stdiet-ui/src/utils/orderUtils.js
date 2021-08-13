
/**
 * 根据营养师ID获取对应售后、助理
 * @param {*} options 营养师、助理、售后对应关系，字典表查询
 * @param {*} dietId 营养师ID
 * @return 数组 [助理数组, 售后数组]
 */
export function getAfterSaleAndAssistantByDietId(options, dietId){
    if(options == undefined || options == null || options.length == 0 || dietId == undefined || dietId == null || dietId == 0){
        return [[0], [0]];
    }
    let dietOperationValue = options.find(
        (opt) => parseInt(opt.dictValue) === dietId
    );
    let afterSaleAndAssistant = dietOperationValue ? dietOperationValue.dictLabel.split("|") : ['0','0'];
    let resultArray = [];
    afterSaleAndAssistant.forEach((item, index) => {
        resultArray.push(item ? (item.split(",").map((str) => parseInt(str))) : [0]);
    });
    return resultArray;
}

/**
 * 根据字典ID数组筛选字典下拉列表
 * @param {*} options 原有下拉列表
 * @param {*} idArray 字典值数组
 * @returns 
 */
export function getAfterSaleOrAssistantByIds(options, idArray){
    if(options == undefined || options == null || options.length == 0 || idArray == undefined || idArray == null || idArray.length == 0 
        || (idArray.length == 1 && idArray[0] == 0)){
        return options;
    }
    let selectOptions = [{ dictValue: 0, dictLabel: "无", remark: null }];
    options.forEach(item => {
        if(idArray.indexOf(item.dictValue) != -1 && item.dictValue != 0){
            selectOptions.push(item);
        }
    });
    return selectOptions;
}

/**
 * 随机获取数组一个元素
 * @param {*} array 
 * @returns 
 */
export function getRandomValueByArray(array){
    if(array && array != null && array.length > 0){
        if(array.length == 1){
            return array[0];
        }else{
            return array[getRandomNumber(0, array.length-1)];
        }
    }
    return 0;
}

/**
 * 生成从minNum到maxNum的随机数
 * @param {*} minNum 
 * @param {*} maxNum 
 * @returns 
 */
export function getRandomNumber(minNum,maxNum){ 
    switch(arguments.length){ 
        case 1: 
            return parseInt(Math.random()*minNum+1,10); 
            break; 
        case 2: 
            return parseInt(Math.random()*(maxNum-minNum+1)+minNum,10); 
            break; 
        default: 
            return 0; 
            break; 
    } 
} 