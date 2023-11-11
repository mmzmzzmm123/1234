// plug/official/notDataView/index.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    title: {
      type: String,
      value: "请输入标题"
    },
    titleSize: {
      type: String,
      value: "16px"
    },
    moreTitle: {
      type: String,
      value: "更多"
    },
    moreTitleColor: {
      type: String,
      value: "#80848f"
    },
    ifShowMore: {
      type: String,
      value: "0"
    },
    outerMargin: {
      type: String,
      value: "20px"
    },
    icoType: {
      type: String,
      value: "ios-arrow-forward"
    },
    ifShowIco: {
      type: String,
      value: "0"
    }
  },

  /**
   * 组件的初始数据
   */
  data: {
    titla: "标题",
    titleSize: "16px",
    ifShowMore: "0",
    moreTitle: "更多",
    moreTitleColor: "#80848f",
    outerMargin: "20px",
    icoType: "ios-arrow-forward",
    ifShowIco: "0"
  },

  /**
   * 组件的方法列表
   */
  methods: {
    toPage: function () {
      this.triggerEvent("toPage")
    }
  }
})
