<template>
  <el-dialog
    :visible.sync="visible"
    :title="title"
    append-to-body
    @closed="onClosed"
    width="1000px"
  >
    <div style="margin-top: -20px;">
        <div
          style="float: right;  "
          
        >
         
         <!--<el-button
            type="primary"
            plain
            >评分</el-button
          >-->
          
          
        </div>
        <!-- 打卡信息详情 -->
        <div>
          
            <h3>一、基础信息</h3>
            <TableDetailMessage :data="punchLogDetail"></TableDetailMessage>
            <h3>二、图片信息</h3>
            <div style="height: 400px; overflow: auto">
              <div>
                <h4>早餐</h4>
                <div>
                  <el-image v-for="(item, index) in punchLog.imagesUrl.breakfastImages" title="点击大图预览" :key="index"
                  style="width: 300px; height: 380px"
                  :src="item"
                  :preview-src-list="imageUrl">
                  </el-image>
                </div>
              </div>
              <div>
                <h4>午餐</h4>
                <div>
                  <el-image v-for="(item, index) in punchLog.imagesUrl.lunchImages" title="点击大图预览" :key="index"
                  style="width: 300px; height: 400px"
                  :src="item"
                  :preview-src-list="imageUrl">
                  </el-image>
                </div>
              </div>
              <div>
                <h4>晚餐</h4>
                <div>
                  <el-image v-for="(item, index) in punchLog.imagesUrl.dinnerImages" title="点击大图预览" :key="index"
                  style="width: 300px; height: 400px"
                  :src="item"
                  :preview-src-list="imageUrl">
                  </el-image>
                </div>
              </div>
              <div>
                <h4>加餐</h4>
                <div>
                  <el-image v-for="(item, index) in punchLog.imagesUrl.extraMealImages" title="点击大图预览" :key="index"
                  style="width: 300px; height: 400px"
                  :src="item"
                  :preview-src-list="imageUrl">
                  </el-image>
                </div>
              </div>
              <div>
                <h4>体型对比照</h4>
                <div>
                  <el-image v-for="(item, index) in punchLog.imagesUrl.bodyImages" title="点击大图预览" :key="index"
                  style="width: 300px; height: 400px"
                  :src="item"
                  :preview-src-list="imageUrl">
                  </el-image>
                </div>
              </div>
            </div>
        </div>
    </div>  
  </el-dialog>
</template>
<script>
import {
  getPunchLogDetail
} from "@/api/custom/wxUserLog";
import TableDetailMessage from "@/components/TableDetailMessage";

export default {
  name: "PunchLogDetail",
  components: {
      TableDetailMessage
  },
  data() {
    return {
      visible: false,
      title: "",
      data: null,
      punchLog: null,  
      imageUrl: [],
      punchLogDetail: [],
      //打卡详情的标题，按竖显示
      punchTitleData: [
        ["姓名", "体重(斤)","饮水量(ml)"],
        ["睡觉时间", "起床时间","运动锻炼"],
        ["情绪","按食谱进食","食谱外食物"],
        ["熬夜失眠", "起床排便","是否便秘"]
      ],
      //打卡详情的属性名称，与标题对应，按竖显示
      punchValueData: [
        ["customerName","weight","water"],
        ["sleepTime", "wakeupTime","sport"],
        ["emotion", "diet","slyEatFood"],
        ["insomnia","defecation", "constipation"]
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
    // 自定义备注列背景色
    remarkColumnStyle({ row, column, rowIndex, columnIndex }) {
      if (columnIndex % 2 === 0) {
        //第三第四列的背景色就改变了2和3都是列数的下标
        return "background:#f3f6fc;font-weight:bold";
      } else {
        return "background:#ffffff;";
      }
    },
    showDialog(data) {
      this.data = data;
      this.title = `「${data.customerName}`+" "+`${data.logTime}」打卡记录`;
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
      this.punchLog = null,
      this.punchLogDetail = []
    }
  },
};
</script>

<style lang="scss" scoped>

</style>
