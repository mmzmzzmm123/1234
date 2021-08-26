export const orderMoneyTypeArray = [
  {
    value: 0,
    label: "全款单"
  },{
    value: 1,
    label: "定金单"
  },
  {
    value: 2,
    label: "尾款单"
  }
]

//订单次数类型
export const orderCountTypeArray = [
  {
    value: 0,
    label: "一开单",
    children: orderMoneyTypeArray
  },{
    value: 1,
    label: "二开单",
    children: orderMoneyTypeArray
  }
]

//订单次数加拆分类型
export const orderCountExtendedTypeArray = [
  {
    value: 0,
    label: "一开单",
    children: orderMoneyTypeArray
  },{
    value: 1,
    label: "二开单",
    children: orderMoneyTypeArray
  },
  {
    value: 2,
    label: "一开单(拆分)",
    children: orderMoneyTypeArray
  },
  {
    value: 3,
    label: "二开单(拆分)",
    children: orderMoneyTypeArray
  }
]

//订单类型（订单新增）
export const orderTypeArray = [
  {
    value: 0,
    label: "普通单",
    children: orderCountTypeArray
  },{
    value: 1,
    label: "比例拆分单",
    children: orderCountTypeArray
  },
  {
    value: 2,
    label: "体验单",
    children:[{
      value: 0,
      label: "一开单",
      children: [{
        value: 0,
        label: "全款单"
      }]
    }]
  },
  {
    value: 3,
    label: "售中单",
    children: orderCountExtendedTypeArray
  },
  {
    value: 4,
    label: "商务单",
    children: orderCountTypeArray
  },
  {
    value: 5,
    label: "基因单",
    children:[{
      value: 0,
      label: "一开单",
      children: [{
        value: 0,
        label: "全款单"
      }]
    }]
  }
]

//订单拆分比例类型
export const orderRateArray = [
  {
    dictValue: "0,10",
    dictLabel: "不拆分",
    remark: "default"
  },
  {
    dictValue: "1,9",
    dictLabel: "1-9开",
    remark: ""
  },{
    dictValue: "2,8",
    dictLabel: "2-8开",
    remark: ""
  },
  {
    dictValue: "3,7",
    dictLabel: "3-7开",
    remark: ""
  },
  {
    dictValue: "4,6",
    dictLabel: "4-6开",
    remark: ""
  },
  {
    dictValue: "5,5",
    dictLabel: "5-5开",
    remark: ""
  }
]

//订单模块控制标识，默认为普通单、一开单、全款单
export const orderModuleshow = {
  orderTypeShow: true,
  secondAfterSaleFlagShow: false,
  amountShow: true,
  payTypeShow: true,
  accountShow: true,
  serveTimeShow: true,
  giveServeDayShow: true,
  conditioningProjectShow: true,
  preSaleShow: true,
  businessAffairShow: false,
  pushPreSaleShow: false,
  onSaleShow: false,
  afterSaleShow: true,
  nutritionistShow: true,
  nutritionistRateShow: false,
  nutriAssisShow: true,
  plannerShow: true,
  plannerAssisShow: true,
  operatorShow: true,
  operatorAssisShow: true,
  orderTimeShow: true,
  startTimeShow: true,
  reviewStatusShow: true,
  remarkShow: true
}

/**
 * 根据订单类型处理订单模块的显示与隐藏
 * 
 * @param {*} orderType 订单类型
 * @param {*} moduleshow  订单模块控制标识对象
 * @param {*} form  订单模块控制标识对象
 */
export function dealOrderModuleshowByOrderType(orderType, moduleshow, form){

    let addFlag = form.orderId == undefined || form.orderId == null;

    if(addFlag){
      //还原
      moduleshow = {
        ...orderModuleshow
      };

      form.serveTimeId = 90;
      form.conditioningProjectId = 0;
      form.nutritionistRate = "0,10";
      form.pushPreSaleId = null;
      form.onSaleId = null;
      form.businessAffairId = null;
      form.secondAfterSaleFlag = 0;

      form.payTypeId = 3;
    }

    switch(orderType[0]){
      //普通单
      case 0:
        break;
      //拆分单
      case 1:
        moduleshow.nutritionistRateShow = true;
        if(addFlag){
          form.nutritionistRate = "2,8";
        }
        break;
      //体验单
      case 2:
        if(addFlag){
          form.serveTimeId = 7;
          form.conditioningProjectId = 12;
        }
        moduleshow.onSaleShow = true;
        moduleshow.afterSaleShow = false;
        moduleshow.nutritionistShow = false;
        moduleshow.nutriAssisShow = false;
        break;
      //售中单
      case 3:
        if(addFlag){
          form.preSaleId = null;
        }
        moduleshow.onSaleShow = true;
        moduleshow.preSaleShow = false;
        if(orderType[1] == 2 || orderType[1] == 3){
          moduleshow.nutritionistRateShow = true;
          if(addFlag){
            form.nutritionistRate = "2,8";
          }
        }
        //一开单
        if(orderType[1] == 0 || orderType[1] == 2){
          moduleshow.pushPreSaleShow = true;
        }
        break;
      //商务单
      case 4:
        if(addFlag){
          form.preSaleId = null;
          form.plannerId = null;
          form.plannerAssisId = null;
          form.operatorId = null;
          form.operatorAssisId = null;
        }
        moduleshow.onSaleShow = true;
        moduleshow.preSaleShow = false;
        moduleshow.plannerShow = false;
        moduleshow.plannerAssisShow = false;
        moduleshow.operatorShow = false;
        moduleshow.operatorAssisShow = false;
        moduleshow.businessAffairShow = true;
        break;
      //基因单
      case 5:
        if(addFlag){
          form.payTypeId = 9;
          form.conditioningProjectId = 15;
        }
        moduleshow.serveTimeShow = false;
        //moduleshow.accountShow = false;
        moduleshow.giveServeDayShow = false;
        moduleshow.afterSaleShow = false;
        moduleshow.nutritionistShow = false;
        moduleshow.nutriAssisShow = false;
        moduleshow.plannerShow = false;
        moduleshow.plannerAssisShow = false;
        moduleshow.operatorShow = false;
        moduleshow.operatorAssisShow = false;
        moduleshow.startTimeShow = false;
        break;
      default:
        break;
    }

    if(addFlag){
      if(orderType[1] == 1 || orderType[1] == 3){
        form.secondAfterSaleFlag = 1;
        moduleshow.secondAfterSaleFlagShow = true;
      }
    }

    if(!addFlag){
      if(form.afterSaleCommissOrder != null && form.afterSaleCommissOrder != 0){
          let afterSaleCommissShow = {
            orderTypeShow: true,
            secondAfterSaleFlagShow: false,
            amountShow: true,
            payTypeShow: true,
            accountShow: true,
            serveTimeShow: true,
            giveServeDayShow: true,
            conditioningProjectShow: true,
            preSaleShow: true,
            businessAffairShow: false,
            pushPreSaleShow: false,
            onSaleShow: false,
            afterSaleShow: false,
            nutritionistShow: false,
            nutritionistRateShow: false,
            nutriAssisShow: false,
            plannerShow: false,
            plannerAssisShow: false,
            operatorShow: false,
            operatorAssisShow: false,
            orderTimeShow: true,
            startTimeShow: true,
            reviewStatusShow: true,
            remarkShow: true
          }
          moduleshow = {
            ...afterSaleCommissShow
          };
      }
    }


    return [moduleshow, form];
    //console.log(moduleshow);

    //console.log(orderModuleshow);
}
