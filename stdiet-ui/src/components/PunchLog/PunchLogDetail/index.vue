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
          style="float: right;  margin-bottom:10px"
        >
         
         <el-button 
            v-hasPermi="['custom:wxUserLog:query']"
            type="primary"
            plain
            @click="clickComment()"
            >打卡点评</el-button
          >
          
          
        </div>
        <!-- 打卡信息详情 -->
        <div>
          
            <h3>一、基础信息</h3>
            <TableDetailMessage :data="punchLogDetail"></TableDetailMessage>
            <h3>二、图片信息</h3>
            <div style="height: 370px; overflow: auto">
              <div v-if="punchLog != null && punchLog.imagesUrl.breakfastImages.length > 0">
                <h4>早餐</h4>
                <div>
                  <el-image v-for="(item, index) in punchLog.imagesUrl.breakfastImages" title="点击大图预览" :key="index"
                  style="width: 300px; height: 300px"
                  :src="item"
                  :preview-src-list="imageUrl">
                  </el-image>
                </div>
              </div>
              <div v-if="punchLog != null && punchLog.imagesUrl.lunchImages.length > 0">
                <h4>午餐</h4>
                <div>
                  <el-image v-for="(item, index) in punchLog.imagesUrl.lunchImages" title="点击大图预览" :key="index"
                  style="width: 300px; height: 300px"
                  :src="item"
                  :preview-src-list="imageUrl">
                  </el-image>
                </div>
              </div>
              <div v-if="punchLog != null && punchLog.imagesUrl.dinnerImages.length > 0">
                <h4>晚餐</h4>
                <div>
                  <el-image v-for="(item, index) in punchLog.imagesUrl.dinnerImages" title="点击大图预览" :key="index"
                  style="width: 300px; height: 300px"
                  :src="item"
                  :preview-src-list="imageUrl">
                  </el-image>
                </div>
              </div>
              <div  v-if="punchLog != null && punchLog.imagesUrl.extraMealImages.length > 0">
                <h4>加餐</h4>
                <div>
                  <el-image v-for="(item, index) in punchLog.imagesUrl.extraMealImages" title="点击大图预览" :key="index"
                  style="width: 300px; height: 300px"
                  :src="item"
                  :preview-src-list="imageUrl">
                  </el-image>
                </div>
              </div>
              <div v-if="punchLog != null && punchLog.imagesUrl.bodyImages.length > 0">
                <h4>体型对比照</h4>
                <div>
                  <el-image v-for="(item, index) in punchLog.imagesUrl.bodyImages" title="点击大图预览" :key="index"
                  style="width: 300px; height: 300px"
                  :src="item"
                  :preview-src-list="imageUrl">
                  </el-image>
                </div>
              </div>
            </div>
        </div>
    </div> 

    <el-dialog :visible.sync="commentVisible" :title="commentTitle" width="500px" append-to-body @closed="commentClosed">
        <el-form ref="form" :model="commentForm" :rules="commentRules" label-position="top" label-width="100px">
            <el-form-item label="打卡评分" prop="executionScore" >
              <el-rate
              v-model="commentForm.executionScore"
              show-score
              allow-half
              text-color="#ff9900"
              >
            </el-rate>
            </el-form-item>
          
          <el-form-item label="点评内容" prop="comment" >
            
            <el-input
              type="textarea"
              :rows="4"
              maxlength="200"
              show-word-limit
              placeholder="请输入点评内容"
              v-model="commentForm.comment">
            </el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer" >
          <el-button type="primary" @click="commentSubmit()">确 定</el-button>
          <el-button @click="commentClosed()">取 消</el-button>
        </div>
    </el-dialog>
  </el-dialog>


</template>
<script>
import {
  getPunchLogDetail,commentPunchContent
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
      callback: null,
      punchLog: null,  
      imageUrl: [],
      punchLogDetail: [],
      //打卡详情的标题，按竖显示
      punchTitleData: [
        ["姓名", "体重(斤)","饮水量(ml)"],
        ["睡觉时间", "起床时间","运动锻炼"],
        ["情绪","按食谱进食","其他食物"],
        ["熬夜失眠", "起床排便","是否便秘"],
        ["服务建议", "评分","点评内容"]
      ],
      //打卡详情的属性名称，与标题对应，按竖显示
      punchValueData: [
        ["customerName","weight","water"],
        ["sleepTime", "wakeupTime","sport"],
        ["emotion", "diet","slyEatFood"],
        ["insomnia","defecation", "constipation"],
        ["remark","executionScore","comment"],
      ],

      commentVisible: false,
      commentTitle: "",
      commentForm:{

      },
      commentRules:{},
      scoreArray:[0.5,1,1.5,2,2.5,3,3.5,4,4.5,5],
      commentFlag: false, //是否更新了点评
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
      this.callback = callback;
      this.commentFlag = false;
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
          res.data.isScore = res.data.executionScore == null ? "否" : "是";
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
      this.callback = null;
      this.punchLog = null;
      this.imageUrl = [];
      this.punchLogDetail = [];

    },
    clickComment(){
      //console.log(this.punchLog.executionScore);
        this.commentForm = {
          id: this.punchLog.id,
          comment: this.punchLog.comment,
          executionScore: this.punchLog.executionScore == null ? 0 : this.punchLog.executionScore,
        }
        this.commentTitle = "点评「"+this.punchLog.customerName+" "+ this.punchLog.logTime +"」打卡";
        this.commentVisible = true;
        
    },
    commentClosed(){
       this.commentVisible = false;
    },
    commentSubmit(){
      /*if(this.commentForm.executionScore == null || this.commentForm.executionScore == 0){
          this.msgError("评分不能为0");
          return;
      }*/
      commentPunchContent(this.commentForm).then((res) => {
          if(res.code == 200){
            this.msgSuccess("点评成功");
            this.commentVisible = false;
            this.getPunchLogById();
            this.commentFlag = true;
            this.callback && this.callback();
          }else{
            this.msgSuccess("点评失败");
          }
      });
    }
  },
};
</script>

<style lang="scss" scoped>

</style>
