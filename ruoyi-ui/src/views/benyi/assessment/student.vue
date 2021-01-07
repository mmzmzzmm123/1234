<template>
  <div class="app-container">
    <div class="flex align-center justify-between student-main-title">
      <p class="title flex align-center">
        <span>姓名：{{ this.childName }} </span>
        <span>出生日期：{{ this.childCsrq }} </span>
        <span>班级：{{ this.bjmc }} </span>
        <span>学期：{{ this.trem }} </span>
        <span>班长：{{ this.zbjsxm }} </span>
      </p>
      <el-button
        type="primary"
        icon="el-icon-s-data"
        size="mini"
        @click="submitForm"
        v-hasPermi="['benyi:assessmentchild:add']"
        v-prevent-re-click
        >生成图表</el-button
      >
    </div>
    <!-- <el-table
      v-loading="loading"
      :data="assessmentcontentList"
      row-key="id"
      default-expand-all
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
    >
      <el-table-column prop="name" label="评估内容"></el-table-column>
      <el-table-column label="是否达标" align="center">
        <template slot-scope="scope">
          <el-checkbox
            v-if="scope.row.iselement == 'Y'"
            v-model="checkedValues"
            @change="handleSelectionChange"
            >是</el-checkbox
          >
        </template>
      </el-table-column>
    </el-table> -->
    <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
      <el-tab-pane
        v-for="itemLy in assessmentcontentList.filter(
          (p) => p.parentId == this.assessmentscope
        )"
        :key="itemLy.id"
        :label="itemLy.name"
        :name="itemLy.name"
      >
        <div
          v-loading="loading"
          class="block"
          v-for="itemFzly in assessmentcontentList.filter(
            (p) => p.parentId == itemLy.id
          )"
          :key="itemFzly.id"
        >
          <h2 class="block-item-title flex align-center">
            {{ itemFzly.name }}
          </h2>
          <ul class="block-content">
            <li
              v-for="itemMb in assessmentcontentList.filter(
                (p) => p.parentId == itemFzly.id
              )"
              :key="itemMb.id"
            >
              <p class="block-content-title">
                <span class="num">{{ itemMb.sort }}. </span>{{ itemMb.name }}
              </p>
              <div
                class="checkbox-content"
                v-for="itemYs in assessmentcontentList.filter(
                  (p) => p.parentId == itemMb.id
                )"
                :key="itemYs.id"
              >
                <p class="checkbox-item flex align-center">
                  <el-checkbox-group v-model="checkList">
                    <el-checkbox :label="itemYs.id" :key="itemYs.id">{{
                      itemYs.name
                    }}</el-checkbox>
                  </el-checkbox-group>
                </p>
                <div class="check-info" v-if="itemYs.ckbz">
                  {{ itemYs.ckbz }}
                  <!-- <p>男孩：身高：94.9-111.7厘米，体重：12.7-21.2公斤</p>
                  <p>女孩：身高：94.1-111.3厘米，体重：12.3-21.5公斤</p> -->
                </div>
              </div>
            </li>
          </ul>
        </div>
      </el-tab-pane>

      <!-- <el-tab-pane label="健康" name="first">
        <div class="block">
          <h2 class="block-item-title flex align-center">身心状况</h2>
          <ul class="block-content">
            <li>
              <p class="block-content-title">
                <span class="num">1. </span>具有健康的体态
              </p>
              <div class="checkbox-content">
                <p class="checkbox-item flex align-center">
                  <el-checkbox v-model="checked">身高和体重适宜</el-checkbox>
                </p>
                <div class="check-info">
                  参考标准：
                  <p>男孩：身高：94.9-111.7厘米，体重：12.7-21.2公斤</p>
                  <p>女孩：身高：94.1-111.3厘米，体重：12.3-21.5公斤</p>
                </div>
              </div>
              <div class="checkbox-content">
                <p class="checkbox-item flex align-center">
                  <el-checkbox v-model="checked"
                    >在提醒下能自然坐直、站直</el-checkbox
                  >
                </p>
                <div class="check-info"></div>
              </div>
            </li>
            <li>
              <p class="block-content-title">
                <span class="num">2. </span>情绪安定愉快
              </p>
              <div class="checkbox-content">
                <p class="checkbox-item flex align-center">
                  <el-checkbox v-model="checked"
                    >情绪比较稳定，很少因一点小事哭闹不止</el-checkbox
                  >
                </p>
                <div class="check-info"></div>
              </div>
              <div class="checkbox-content">
                <p class="checkbox-item flex align-center">
                  <el-checkbox v-model="checked"
                    >有比较强烈的情绪反应时，能在成人的安抚下逐渐平静下来</el-checkbox
                  >
                </p>
                <div class="check-info"></div>
              </div>
            </li>
          </ul>
        </div>
      </el-tab-pane>
      <el-tab-pane label="语言" name="second">配置管理</el-tab-pane>
      <el-tab-pane label="社会" name="third">角色管理</el-tab-pane>
      <el-tab-pane label="科学" name="fourth">定时任务补偿</el-tab-pane>
      <el-tab-pane label="艺术" name="five">定时任务补偿</el-tab-pane> -->
    </el-tabs>
  </div>
</template>
<script>
import {
  listAssessmentcontent,
  getAssessmentcontent,
  delAssessmentcontent,
  addAssessmentcontent,
  updateAssessmentcontent,
  exportAssessmentcontent,
} from "@/api/benyi/assessmentcontent";

import { getChildByAssessment } from "@/api/benyi/child";
import { addAssessmentchild } from "@/api/benyi/assessmentchild";

export default {
  name: "Assessmentstudent",

  data() {
    return {
      childId: "",
      childName: "",
      childCsrq: "",
      bjmc: "",
      classid: "",
      trem: "",
      zbjsxm: "",
      assessmentscope: "",
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 评估内容表格数据
      assessmentcontentList: [],
      // 表单参数
      form: {},
      // 查询参数
      queryParams: {
        parentid: undefined,
        name: undefined,
        iselement: undefined,
        scope: undefined,
        sort: undefined,
      },
      activeName: "健康",
      checked: false,
      checkList: [],
    };
  },
  created() {
    // this.getList();
    const childId = this.$route.params && this.$route.params.id;
    this.childId = childId;
    // console.log("childId:" + childId);
    this.getChild(childId);
  },
  methods: {
    getChild(childId) {
      getChildByAssessment(childId).then((response) => {
        // console.log(response);
        if (response.code == "200") {
          this.childName = response.data.name;
          this.childCsrq = response.data.csrq;
          this.trem = response.trem;
          this.bjmc = response.data.bjmc;
          this.classid = response.data.classid;
          this.zbjsxm = response.data.zbjsmc;
          response.ByAssessmentchild.forEach((item) =>
            this.checkList.push(item.contentid)
          );
          if (response.isAssessment == "0") {
            this.msgError(
              "当前幼儿出生日期不符合评估范围，幼儿评估范围为36-72个月"
            );
          } else {
            this.assessmentscope = response.isAssessment;
            this.getList();
          }
        }
      });
    },
    /** 查询评估内容列表 */
    getList() {
      this.loading = true;
      listAssessmentcontent(this.queryParams).then((response) => {
        // console.log("rows:" + response.rows);
        this.assessmentcontentList = response.rows;
        this.loading = false;
      });
    },
    // // 表单重置
    // reset() {
    //   this.form = {
    //     id: undefined,
    //     childid: undefined,
    //     classid: undefined,
    //     contentid: undefined,
    //     type: undefined,
    //     xn: undefined,
    //     userid: undefined,
    //     createTime: undefined,
    //   };
    //   this.resetForm("form");
    // },
    /** 提交按钮 */
    submitForm: function () {
      this.$confirm("确认生成图表数据?生成后数据不能取消", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
        callback: (action) => {
          if (action === "confirm") {
            // console.log('按下 确定')
            var items = "";
            this.checkList.forEach((item) => {
              //当全选被选中的时候，循环遍历源数据，把数据的每一项加入到默认选中的数组去
              items = items + item + ",";
            });
            // console.log(items);
            // console.log("提交："+this.checkList.length);
            if (this.checkList.length == 0) {
              this.msgError("请至少选择一项数据");
            } else {
              this.form.childid = this.childId;
              this.form.classid = this.classid;
              this.form.items = items;
              this.form.type = "Y";
              this.form.xn = this.trem;
              this.form.scope = this.assessmentscope;
              addAssessmentchild(this.form).then((response) => {
                if (response.code === 200) {
                  this.msgSuccess("评估成功");
                  this.$router.push({
                    path:
                      "/benyi/assessmentchildhistory/student/" + this.childId,
                  });
                }
              });
            }
          } else {
          }
        },
      });
    },
    handleClick(tab) {
      // this.activeName = tab
    },
    // handlecheckedItemsChange(value) {
    //   // var items = "";
    //   // this.checkList.forEach((item) => {
    //   //   //当全选被选中的时候，循环遍历源数据，把数据的每一项加入到默认选中的数组去
    //   //   items = items + item + ",";
    //   // });
    //   // console.log(items);
    // },
  },
};
</script>
<style lang="scss" scoped>
// 禁止复制
div {
  -webkit-touch-callout: none;
  -webkit-user-select: none;
  -khtml-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
}
.title {
  padding: 10px 0;
  font-weight: 600;
  flex-wrap: wrap;
  padding-right: 10px;
  line-height: 20px;
  span {
    font-size: 16px;
    padding: 0 4px;
    // &:nth-of-type(2) {
    //   font-size: 14px;
    //   font-weight: normal;
    // }
  }
}
.block {
  padding: 10px;
  color: #333;
  .block-item-title {
    padding: 10px 0;
    margin: 0;
    font-size: 16px;
    font-weight: 500;
    &::before {
      content: "";
      margin-right: 8px;
      width: 4px;
      height: 14px;
      background: #1890ff;
    }
  }

  .block-content {
    border-radius: 5px;
    padding: 10px;
    background: #fcfcfc;
    .block-content-title {
      font-size: 14px;
      font-weight: bold;
      line-height: 24px;
      padding-bottom: 5px;
      .num {
        font-size: 16px;
      }
    }
    .checkbox-content {
      padding-left: 20px;
      margin-bottom: 15px;
    }
    .checkbox-item {
      font-size: 14px;
      line-height: 22px;
    }
    .check-info {
      padding-left: 24px;
      line-height: 18px;
      font-size: 12px;
      color: #999;
      p {
        padding-left: 10px;
      }
    }
    .el-checkbox {
      display: flex;
      white-space: normal;
      .el-checkbox__input {
        margin-top: 2px;
      }
    }
  }
}
@media (max-width: 768.98px) {
  .title span {
    font-size: 12px;
  }
}
</style>
