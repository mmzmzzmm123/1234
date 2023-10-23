export const page = {
  limit: [20, 40, 60, 80, 100],
  page: 1,
  layout: 'total, sizes, prev, pager, next, jumper'
}

/**
 * 无限极分类type
 * @type {{product: number, attachment: number, menu: number, article: number, operator: number}}
 */
export const categoryType = [ // 1 产品分类，2 附件分类，3 文章分类， 4 设置分类， 5 菜单分类， 6 配置分类， 7 秒杀配置
  { name: '产品分类', value: 1, shortName: '产品' },
  { name: '附件分类', value: 2, shortName: '附件' },
  { name: '文章分类', value: 3, shortName: '文章' },
  { name: '设置分类', value: 4, shortName: '设置' },
  { name: '菜单分类', value: 5, shortName: '菜单' },
  { name: '配置分类', value: 6, shortName: '配置' },
  { name: '秒杀配置', value: 7, shortName: '秒杀' }
]

export const picModules = ['course','gauge','zx']

export const picTypes = ['class','banner','item','section','avatar','other','column','team']

export const wList = [
  { label: '上班', value: '0' },
  { label: '休息', value: '1' }
]

export const limitStatus = [
  { label: '不限制', value: 0 },
  { label: '限制', value: 1 }
]

export const modeList = [
  { label: '全部', value: '' },
  { label: '语音咨询', value: 1 },
  { label: '视频咨询', value: 2 },
  { label: '当面咨询', value: 3 },
]

export const typeList = [
  { label: '全部', value: '' },
  { label: '单次', value: 1 },
  { label: '套餐', value: 2 }
]

export const comListStatus = [
  { label: '全部', value: '' },
  { label: '启用', value: '0' },
  { label: '禁用', value: '1' }
]

export const roleListStatus = [
  { label: '全部', value: '' },
  { label: '显示', value: 1 },
  { label: '不显示', value: 0 }
]

export const showHiddenStatus = [
  { label: '显示', value: '‘1’' },
  { label: '不显示', value: '‘0’' }
]

export const switchStatus = [
  { label: '开启', value: 1 },
  { label: '关闭', value: 0 }
]

export const deletedOrNormal = [
  { label: '正常', value: 0 },
  { label: '已删除', value: 1 }
]

export const orderStatus = [
  { label: '全部', value: '' },
  { label: '待付款', value: '0' },
  { label: '进行中', value: '1' },
  { label: '已完成', value: '2' },
  { label: '已取消', value: '3' }
]

export const payStatus = [
  { label: '全部', value: '' },
  { label: '未支付', value: '1' },
  { label: '支付成功', value: '2' },
  { label: '退款中', value: '3' },
  { label: '部分退', value: '4' },
  { label: '全单退', value: '5' },
  { label: '退款失败', value: '6' },
]

export const logName = [
  { label: '创建', value: 'create' },
  { label: '支付', value: 'pay' },
  { label: '改价', value: 'price' },
  { label: '核销', value: 'hx' },
  { label: '备注', value: 'remark' },
  { label: '完成', value: 'finished' },
  { label: '退款', value: 'refund' },
  { label: '转介', value: 'change' },
  { label: '取消', value: 'cancel' },
]

export const gaugeCompute = [
  { label: '普通计算', value: 1 },
  { label: '多维计算', value: 2 },
  { label: 'MBTI', value: 3 },
  { label: 'SDS', value: 4 },
  { label: 'SAS', value: 5 },
  { label: 'SCL-90', value: 6 },
  // { label: '马斯洛安全感', value: 6 },
]

export const gaugeMbti = [
  { label: 'E', value: 'E' },
  { label: 'I', value: 'I' },
  { label: 'S', value: 'S' },
  { label: 'N', value: 'N' },
  { label: 'T', value: 'T' },
  { label: 'F', value: 'F' },
  { label: 'J', value: 'J' },
  { label: 'P', value: 'P' },
  { label: '章节', value: 'O' },
]

export const gaugeMbtiRes = [
  { label: 'ISTJ', value: 'ISTJ' },
  { label: 'ISFJ', value: 'ISFJ' },
  { label: 'INFJ', value: 'INFJ' },
  { label: 'INTJ', value: 'INTJ' },
  { label: 'ISTP', value: 'ISTP' },
  { label: 'ISFP', value: 'ISFP' },
  { label: 'INFP', value: 'INFP' },
  { label: 'INTP', value: 'INTP' },
  { label: 'ESTP', value: 'ESTP' },
  { label: 'ESFP', value: 'ESFP' },
  { label: 'ENFP', value: 'ENFP' },
  { label: 'ENTP', value: 'ENTP' },
  { label: 'ESTJ', value: 'ESTJ' },
  { label: 'ESFJ', value: 'ESFJ' },
  { label: 'ENFJ', value: 'ENFJ' },
  { label: 'ENTJ', value: 'ENTJ' },
]

/**
 * 暂时弃用
 * @type {*[]}
 */
export const configCategory = [
  { label: '系统', value: '0' },
  { label: '应用', value: '1' },
  { label: '支付', value: '2' },
  { label: '其他', value: '3' }
]

/**
 * 表单配置集合集中配置
 * @type {{id: number, dis: string}[]}
 */
export const formConfigIds = [
  { id: 84, dis: '微信公众号表单配置' },
  { id: 86, dis: '秒杀配置' }
]

/**
 * 时间选择器
 */
export const fromList = {
  title: '选择时间',
  custom: true,
  fromTxt: [
    { text: '全部', val: '' },
    { text: '今天', val: 'today' },
    { text: '昨天', val: 'yesterday' },
    { text: '最近7天', val: 'lately7' },
    { text: '最近30天', val: 'lately30' },
    { text: '本月', val: 'month' },
    { text: '本年', val: 'year' }
  ]
}

// 统计管理时间选择器
export const timeList = {
  title: '选择时间',
  custom: true,
  fromTxt: [
    { text: '昨天', val: `` },
    { text: '最近7天', val: 'lately7' },
    { text: '最近30天', val: 'lately30' },
  ]
}