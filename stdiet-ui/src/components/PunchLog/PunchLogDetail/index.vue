<template>
  <el-dialog
    :visible.sync="visible"
    :title="title"
    append-to-body
    @closed="onClosed"
    :width="dialogWidth"
  >
    <div style="margin-top: -20px">
      <div style="display: flex; flex-direction: row">
        <!-- 打卡信息详情 -->
        <div
          :style="'float: left; width: ' + (commentVisible ? '900px' : '950px')"
        >
          <div style="float: right; margin-bottom: 10px">
            <el-button
              v-hasPermi="['custom:wxUserLog:query']"
              type="primary"
              plain
              @click="clickComment()"
              >打卡点评</el-button
            >
          </div>
          <h3>一、基础信息</h3>
          <TableDetailMessage :data="punchLogDetail" :maxLength="10" />
          <h3>二、食物以及对比照信息</h3>
          <div style="height: 370px; overflow: auto">
            <div v-if="punchLog != null && punchLog.ingredientDesc">
              <h4>食物描述</h4>
              <div>
                {{ punchLog.ingredientDesc }}
              </div>
            </div>
            <div v-if="punchLog != null">
              <div v-if="punchLog.imagesUrl.breakfastImages.length > 0">
                <h4>早餐照片</h4>
                <div>
                  <el-image
                    v-for="(item, index) in punchLog.imagesUrl.breakfastImages"
                    title="点击大图预览"
                    :key="'breakfast' + index"
                    class="food_image_first"
                    fit="cover"
                    :src="item"
                    :preview-src-list="imageUrl"
                  >
                  </el-image>
                </div>
              </div>
              <div v-if="punchLog.imagesUrl.lunchImages.length > 0">
                <h4>午餐照片</h4>
                <div>
                  <el-image
                    v-for="(item, index) in punchLog.imagesUrl.lunchImages"
                    title="点击大图预览"
                    :key="'lunch' + index"
                    class="food_image"
                    fit="cover"
                    :src="item"
                    :preview-src-list="imageUrl"
                  >
                  </el-image>
                </div>
              </div>
              <div v-if="punchLog.imagesUrl.dinnerImages.length > 0">
                <h4>晚餐照片</h4>
                <div>
                  <el-image
                    v-for="(item, index) in punchLog.imagesUrl.dinnerImages"
                    title="点击大图预览"
                    :key="'dinner' + index"
                    class="food_image"
                    fit="cover"
                    :src="item"
                    :preview-src-list="imageUrl"
                  >
                  </el-image>
                </div>
              </div>
              <div v-if="punchLog.imagesUrl.extraMealImages.length > 0">
                <h4>加餐照片</h4>
                <div>
                  <el-image
                    v-for="(item, index) in punchLog.imagesUrl.extraMealImages"
                    title="点击大图预览"
                    :key="'extraMeal' + index"
                    class="food_image"
                    fit="cover"
                    :src="item"
                    :preview-src-list="imageUrl"
                  >
                  </el-image>
                </div>
              </div>
              <div v-if="punchLog != null && punchLog.bodyDesc">
                <h4>对比照描述</h4>
                <div>
                  {{ punchLog.bodyDesc }}
                </div>
              </div>
              <div v-if="punchLog.imagesUrl.bodyImages.length > 0">
                <h4>体型对比照</h4>
                <div>
                  <el-image
                    v-for="(item, index) in punchLog.imagesUrl.bodyImages"
                    title="点击大图预览"
                    :key="index"
                    style="width: 300px; height: 300px"
                    fit="cover"
                    :src="item"
                    :preview-src-list="imageUrl"
                  >
                  </el-image>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div
          style="width: 200px; margin-left: 40px; margin-top: 50px"
          v-show="commentVisible"
        >
          <h3 style="width: 200px">{{ commentTitle }}</h3>
          <el-form
            ref="form"
            :model="commentForm"
            :rules="commentRules"
            label-position="top"
            label-width="100px"
          >
            <el-form-item label="打卡评分" prop="executionScore">
              <el-rate
                v-model="commentForm.executionScore"
                show-score
                allow-half
                text-color="#ff9900"
              >
              </el-rate>
            </el-form-item>
            <el-form-item label="点评内容" prop="comment">
              <el-input
                type="textarea"
                :rows="20"
                maxlength="200"
                show-word-limit
                placeholder="请输入点评内容"
                v-model="commentForm.comment"
              >
              </el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer" style="text-align: center">
            <el-button type="primary" @click="commentSubmit()">确 定</el-button>
            <el-button @click="commentClosed()">取 消</el-button>
          </div>
        </div>
      </div>
    </div>
  </el-dialog>
</template>
<script>
import { getPunchLogDetail, commentPunchContent } from "@/api/custom/wxUserLog";
import TableDetailMessage from "@/components/TableDetailMessage";

export default {
  name: "PunchLogDetail",
  components: {
    TableDetailMessage,
  },
  data() {
    return {
      visible: false,
      title: "",
      data: null,
      callback: null,
      punchLog: null,
      imageUrl: [],
      punchLogDetail: [],
      //打卡详情的标题，按竖显示
      punchTitleData: [
        [],
        ["睡觉时间", "情绪状况", "运动锻炼"],
        ["起床时间", "情绪描述", "运动描述"],
        ["排便情况", "按营养餐吃", "食谱外食物"],
        ["排便描述", "营养餐感受", "熬夜失眠"],
        ["评分", "点评内容"],
      ],
      //打卡详情的属性名称，与标题对应，按竖显示
      punchValueData: [
        [],
        ["sleepTime", "emotion", "sport"],
        ["wakeupTime", "emotionDesc", "sportDesc"],
        ["defecation", "diet", "slyEatFood"],
        ["defecationDesc", "dietDesc", "insomnia"],
        ["executionScore", "comment"],
      ],

      commentVisible: false,
      commentTitle: "",
      commentForm: {},
      commentRules: {},
      scoreArray: [0.5, 1, 1.5, 2, 2.5, 3, 3.5, 4, 4.5, 5],
      commentFlag: false, //是否更新了点评,
      dialogWidth: "1000px",
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
    // 自定义备注列背景色
    remarkColumnStyle({ row, column, rowIndex, columnIndex }) {
      if (columnIndex % 2 === 0) {
        //第三第四列的背景色就改变了2和3都是列数的下标
        return "background:#f3f6fc;font-weight:bold";
      } else {
        return "background:#ffffff;";
      }
    },
    showDialog(data, callback) {
      this.data = data;
      if (data.sex) {
        this.punchTitleData[0] = ["体重(斤)", "饮水量(ml)", "生理期"];
        this.punchValueData[0] = ["weight", "water", "menstrualPeriod"];
      } else {
        this.punchTitleData[0] = ["体重(斤)", "饮水量(ml)"];
        this.punchValueData[0] = ["weight", "water"];
      }
      this.callback = callback;
      this.commentFlag = false;
      this.title = `「${data.customerName}` + " " + `${data.logTime}」打卡记录`;
      this.getPunchLogById();
    },
    getPunchLogById() {
      getPunchLogDetail(this.data.id).then((res) => {
        if (res.code == 200) {
          this.visible = true;
          this.punchLog = res.data;
          res.data.sport = res.data.sport === "Y" ? "是" : "否";
          res.data.diet = res.data.diet === "Y" ? "是" : "否";
          res.data.insomnia = res.data.insomnia === "Y" ? "是" : "否";
          res.data.defecation = res.data.defecation === "Y" ? "是" : "否";
          res.data.constipation = res.data.constipation === "Y" ? "是" : "否";
          res.data.isScore = res.data.executionScore == null ? "否" : "是";
          res.data.menstrualPeriod =
            res.data.menstrualPeriod == "N" ? "否" : "是";

          this.punchLogDetail = [];
          for (let i = 0; i < this.punchTitleData.length; i++) {
            this.punchLogDetail.push({
              attr_name_one: this.punchTitleData[i][0],
              value_one: res.data[this.punchValueData[i][0]],
              attr_name_two: this.punchTitleData[i][1],
              value_two: res.data[this.punchValueData[i][1]],
              attr_name_three: this.punchTitleData[i][2],
              value_three: res.data[this.punchValueData[i][2]],
            });
          }
          let imageUrl = [];
          imageUrl = imageUrl.concat(res.data.imagesUrl.breakfastImages);
          imageUrl = imageUrl.concat(res.data.imagesUrl.lunchImages);
          imageUrl = imageUrl.concat(res.data.imagesUrl.dinnerImages);
          imageUrl = imageUrl.concat(res.data.imagesUrl.extraMealImages);
          imageUrl = imageUrl.concat(res.data.imagesUrl.bodyImages);
          this.imageUrl = imageUrl;
        }
      });
    },
    onClosed() {
      this.data = null;
      //this.callback = null;
      this.punchLog = null;
      this.imageUrl = [];
      this.punchLogDetail = [];
      if (this.commentFlag) {
        console.log("cbhdsjcsnjcsdc");
        this.callback && this.callback();
      }
    },
    clickComment() {
      //console.log(this.punchLog.executionScore);
      this.commentForm = {
        id: this.punchLog.id,
        comment: this.punchLog.comment,
        executionScore:
          this.punchLog.executionScore == null
            ? 0
            : this.punchLog.executionScore,
      };
      this.commentTitle = "点评「" + this.punchLog.logTime + "」打卡";
      this.commentVisible = true;
      this.dialogWidth = "1200px";
    },
    commentClosed() {
      this.commentVisible = false;
      this.dialogWidth = "1000px";
    },
    commentSubmit() {
      /*if(this.commentForm.executionScore == null || this.commentForm.executionScore == 0){
          this.msgError("评分不能为0");
          return;
      }*/
      commentPunchContent(this.commentForm).then((res) => {
        if (res.code == 200) {
          this.msgSuccess("点评成功");
          this.commentClosed();
          this.getPunchLogById();
          this.commentFlag = true;
        } else {
          this.msgSuccess("点评失败");
        }
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.food_image_first {
  width: 160px;
  height: 160px;
  border-radius: 16px;
  margin: 8px;
}

.food_image {
  width: 160px;
  height: 160px;
  //margin-left:10px
  margin: 8px;
  border-radius: 16px;
}
</style>
