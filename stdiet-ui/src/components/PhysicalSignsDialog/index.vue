<template>
  <el-dialog
    :visible.sync="visible"
    :title="title"
    append-to-body
    @closed="onClosed"
  >
    <el-table
      :show-header="false"
      :data="dataList"
      border
      :cell-style="columnStyle"
      style="width: 100%"
    >
      <el-table-column width="120" prop="attr_name_one"> </el-table-column>
      <el-table-column prop="value_one">
        <template slot-scope="scope">
          <auto-hide-message
            :data="scope.row.value_one == null ? '' : scope.row.value_one + ''"
            :maxLength="20"
          />
        </template>
      </el-table-column>
      <el-table-column width="120" prop="attr_name_two"></el-table-column>
      <el-table-column prop="value_two">
        <template slot-scope="scope">
          <auto-hide-message
            :data="scope.row.value_two == null ? '' : scope.row.value_two + ''"
            :maxLength="20"
          />
        </template>
      </el-table-column>
      <el-table-column width="120" prop="attr_name_three"></el-table-column>
      <el-table-column prop="value_three">
        <template slot-scope="scope">
          <auto-hide-message
            :data="
              scope.row.value_three == null ? '' : scope.row.value_three + ''
            "
            :maxLength="20"
          />
        </template>
      </el-table-column>
    </el-table>
  </el-dialog>
</template>
<script>
import { getCustomerPhysicalSignsByCusId } from "@/api/custom/customer";
import AutoHideMessage from "@/components/AutoHideMessage";

export default {
  name: "PhysicalSignsDialog",
  components: {
    "auto-hide-message": AutoHideMessage,
  },
  data() {
    return {
      visible: false,
      title: "",
      dataList: [],
      // 标题
      titleData: [
        ["创建时间", "姓名", "年龄"],
        ["性别", "手机号", "身高(cm)"],
        ["体重(斤)", "地域", "病史"],
        ["忌口或过敏源", "便秘", "熬夜失眠"],
        ["经常运动", "饮食方式", "饮食备注"],
        ["饮食特点", "工作职业", "上夜班"],
        ["久坐/运动多", "浑身乏力", "减脂反弹"],
        ["意识到生活习惯是减脂关键", "睡觉时间", "起床时间"],
        ["方便沟通时间", "湿气数据", "气血数据"],
        ["减脂经历", "减脂遇到的困难", "备注"],
      ],
      // 属性
      valueData: [
        ["createTime", "name", "age"],
        ["sex", "phone", "tall"],
        ["weight", "position", "singList"],
        ["dishesIngredientId", "constipation", "staylate"],
        ["motion", "makeFoodType", "remarks"],
        ["makeFoodTaste", "vocation", "night"],
        ["walk", "weakness", "rebound"],
        ["crux", "sleepTime", "getupTime"],
        ["connectTime", "bloodData", "moistureDate"],
        ["experience", "difficulty", "comments"],
      ],
    };
  },
  methods: {
    // 自定义列背景色
    columnStyle({ row, column, rowIndex, columnIndex }) {
      if (columnIndex % 2 === 0) {
        //第三第四列的背景色就改变了2和3都是列数的下标
        return "background:#f3f6fc;font-weight:bold";
      } else {
        return "background:#ffffff;";
      }
    },
    showDialog(data) {
      this.title = `「${data.name}」客户体征`;
      getCustomerPhysicalSignsByCusId(data.id).then((res) => {
        if (!res.data) {
          this.visible = true;
          return;
        }
        const sign = res.data;
        sign.sex = sign.sex === 0 ? `男` : "女";
        sign.position = sign.position === 0 ? `南方` : "北方";
        sign.signString = sign.signList.join("，");
        sign.constipation = sign.constipation === 0 ? "是" : "否";
        sign.staylate = sign.staylate === 0 ? "是" : "否";
        sign.motion = sign.motion === 0 ? "是" : "否";
        sign.makeFoodType = sign.makeFoodType === 0 ? `自己做` : "外面吃";
        sign.makeFoodTaste = sign.makeFoodTaste === 0 ? "清淡" : "重口味";
        sign.night = sign.night === 0 ? `是` : "否";
        sign.walk = sign.walk === 0 ? `久坐多` : "走动多";
        sign.weakness = sign.weakness === 0 ? `是` : "否";
        sign.rebound = sign.rebound === 0 ? `是` : "否";
        sign.crux = sign.crux === 0 ? `是` : "否";
        sign.sleepTime = sign.sleepTime + "点";
        sign.getupTime = sign.getupTime + "点";
        sign.connectTime = sign.connectTime + "点";
        for (let i = 0; i < this.titleData.length; i++) {
          this.dataList.push({
            attr_name_one: this.titleData[i][0],
            value_one: sign[this.valueData[i][0]],
            attr_name_two: this.titleData[i][1],
            value_two: sign[this.valueData[i][1]],
            attr_name_three: this.titleData[i][2],
            value_three: sign[this.valueData[i][2]],
          });
        }

        this.visible = true;
      });
    },
    onClosed() {
      this.dataList = [];
    },
  },
};
</script>
