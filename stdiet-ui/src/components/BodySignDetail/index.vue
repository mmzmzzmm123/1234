<template>
  <el-dialog title="客户体征" :visible.sync="visible" width="1000px" append-to-body @closed="onClosed">
    <el-table :show-header="false" :data="customerSignList" border :cell-style="columnStyle" style="width: 100%;">
      <el-table-column width="140" prop="attr_name_one">
      </el-table-column>
      <el-table-column prop="value_one">
        <template slot-scope="scope">
          <auto-hide-message :data="scope.row.value_one == null ? '' : (scope.row.value_one+'')" :maxLength="20"/>
        </template>
      </el-table-column>
      <el-table-column width="140" prop="attr_name_two"></el-table-column>
      <el-table-column prop="value_two">
        <template slot-scope="scope">
          <auto-hide-message :data="scope.row.value_two == null ? '' : (scope.row.value_two+'')" :maxLength="20"/>
        </template>
      </el-table-column>
      <el-table-column width="140" prop="attr_name_three"></el-table-column>
      <el-table-column prop="value_three">
        <template slot-scope="scope">
          <auto-hide-message :data="scope.row.value_three == null ? '' : (scope.row.value_three+'')" :maxLength="20"/>
        </template>
      </el-table-column>
    </el-table>
  </el-dialog>
</template>
<script>
  import {getCustomerAndSignByPhone} from "@/api/custom/customer";
  import AutoHideMessage from "@/components/AutoHideMessage";

  export default {
    name: 'BodySignDialog',
    components: {
      "auto-hide-message": AutoHideMessage
    },
    data() {
      return {
        visible: false,
        //用户体征数据
        customerSignList: [],
        message: "未找到该客户，请检查订单手机号与客户体征手机号是否一致",
        signTitleData: [["姓名", "手机号", "性别"],
          ["年龄(岁)", "身高(厘米)", "体重(斤)"],
          ["南、北方", "病史", "忌口或过敏源"],
          ["是否便秘", "是否熬夜失眠", "是否经常运动"],
          ["饮食方式", "饮食备注", "饮食特点"],
          ["工作职业", "是否上夜班", "久坐多还是运动多"],
          ["是否浑身乏力", "是否减脂反弹", "意识到生活习惯是减脂的关键"],
          ["睡觉时间", "起床时间", "方便沟通时间"],
          ["湿气数据", "气血数据", "减脂经历"],
          ["减脂遇到的困难", "备注", "创建时间"]],
        signValueData: [["name", "phone", "sex"],
          ["age", "tall", "weight"],
          ["position", "signString", "dishesIngredientId"],
          ["constipation", "staylate", "motion"],
          ["makeFoodType", "remarks", "makeFoodTaste"],
          ["vocation", "night", "walk"],
          ["weakness", "rebound", "crux"],
          ["sleepTime", "getupTime", "connectTime"],
          ["bloodData", "moistureDate", "experience"],
          ["difficulty", "comments", "createTime"]]
      }
    },
    methods: {
      showDialog(phone) {
        if (!phone) {
          this.alert(this.message);
          return;
        }
        getCustomerAndSignByPhone({phone}).then(response => {
          if (response.data == null) {
            this.alert(this.message);
            return;
          }

          let sign = response.data;
          // sign.name = response.data.name;
          // sign.phone = response.data.phone;
          // sign.createTime = response.data.createTime;
          sign.sex = sign.sex === 0 ? `男` : '女';
          sign.position = sign.position === 0 ? `南方` : '北方';
          sign.signString = sign.signList.join('，');
          sign.constipation = sign.constipation === 0 ? "是" : "否";
          sign.staylate = sign.staylate === 0 ? "是" : "否";
          sign.motion = sign.motion === 0 ? "是" : "否";
          sign.makeFoodType = sign.makeFoodType === 0 ? `自己做` : '外面吃';
          sign.makeFoodTaste = sign.makeFoodTaste === 0 ? "清淡" : '重口味';
          sign.night = sign.night === 0 ? `是` : '否';
          sign.walk = sign.walk === 0 ? `久坐多` : '走动多';
          sign.weakness = sign.weakness === 0 ? `是` : '否';
          sign.rebound = sign.rebound === 0 ? `是` : '否';
          sign.crux = sign.crux === 0 ? `是` : '否';
          sign.sleepTime = sign.sleepTime + "点";
          sign.getupTime = sign.getupTime + "点";
          sign.connectTime = sign.connectTime + "点";
          for (let i = 0; i < this.signTitleData.length; i++) {
            this.customerSignList.push({
              "attr_name_one": this.signTitleData[i][0],
              "value_one": sign[this.signValueData[i][0]],
              "attr_name_two": this.signTitleData[i][1],
              "value_two": sign[this.signValueData[i][1]],
              "attr_name_three": this.signTitleData[i][2],
              "value_three": sign[this.signValueData[i][2]]
            });
          }
          this.visible = true;
        });
      },
      alert(value) {
        this.$alert(value, '提示', {confirmButtonText: '关闭'});
      },
      // 自定义列背景色
      columnStyle({row, column, rowIndex, columnIndex}) {
        if (columnIndex == 0 || columnIndex == 2 || columnIndex == 4 || columnIndex == 6) {
          //第三第四列的背景色就改变了2和3都是列数的下标
          return "background:#f3f6fc;font-weight:bold";
        } else {
          return "background:#ffffff;";
        }
      },
      onClosed() {
        this.customerSignList = [];
      }
    },

  }
</script>
