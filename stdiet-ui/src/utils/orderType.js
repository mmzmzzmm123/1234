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
