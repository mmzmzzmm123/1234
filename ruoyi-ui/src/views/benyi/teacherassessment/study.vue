<!--
 * @Author: your name
 * @Date: 2020-08-24 18:57:33
 * @LastEditTime: 2020-08-27 20:35:10
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \ruoyi-ui\src\views\benyi\activity\index.vue
-->
<template>
  <div class="app-activity">
    <h2 class="title">教师月绩效考核与解读</h2>
    <p class="text"><b>一、考核及其要素</b></p>
    <p class="text">
      (一)考核。正如《一日流程》单项月评估一样，本一体系中的月绩效考核也是针对班集体中的教师个体进行。
    </p>
    <p class="text">
      (二)考核要素。教师月绩效考核一共包括五个元素（见下表）。五个要素中与教师个体直接相关的包括《一日流程》、教师考勤和事故（直接责任人）。另外两个间接相关要素是儿童考勤与卫生。所谓间接要素，即班集体中所有教师的共享要素（班上教师在这两个要素的分数相同）。
    </p>
    <p class="text">
      <b>教师月绩效考核表</b>
    </p>
    <p class="text">
      <el-table :data="tableData" border style="width: 100%">
        <el-table-column fixed prop="name" label="要素" width="240">
        </el-table-column>
        <el-table-column prop="pgfs" label="评估分数" width="160">
        </el-table-column>
        <el-table-column prop="blqz" label="比例(权重)" width="180">
        </el-table-column>
        <el-table-column prop="sdfs" label="实得分数" width="160">
        </el-table-column>
        <el-table-column prop="remark" label="备注/奖金" width="240">
        </el-table-column>
      </el-table>
    </p>
    <p class="text">
      <b>二、解读</b>
    </p>
    <p class="text">
      (一)加权平均数。考核结果是百分制。如果只考核一个要素，比如《一日流程》，那么得分多少最终结果就是多少。但如果考核要素多于两个时还使用百分制的话，那么就得规定每个要素各占多大比例（权重）。就像两个人合作投资100万元做生意，首先要确定股比。其中一个投资80万，另一个是20万，那么其股比分别是80%和20%。所谓股比就是权重（股权）。从表格中可见，五个要素各自有自己的权重（股权）。这是按要素重要程度来规定的。《一日流程》最重要，所以占比60%，是
      “控股大股东”，其次安全占15%，接下来是教师和儿童考勤为10%，最后是卫生5%。
    </p>
    <p class="text">
      (二)分数计算。在评估时，每个要素的实得分数均按百分制登记，得多少分就填多少分。全部五个要素实得分数都登录完之后，体系会自动得出总分。至于各个要素具体如何进行评估，请遵循以下策略和方法。
    </p>

    <p class="text">
      1.<b>《一日流程》。</b>请使用“评价与评估”模块中的“班级一日流程评估表”。将每月班级中每个人的个人得分直接填写在上述表格中相应位置即可。
    </p>
    <p class="text">
      2.<b>教师考勤。</b>将当月总工作日减去个人缺席天数的差，再除以当月总工作日，等数乘以100，即为当月考勤得分。如果某月总工作日为23天，教师缺席5天，23-5=18，18除以23=0.78×100=78分。
    </p>
    <p class="text">
      3.<b>儿童考勤。</b>当月班级所有孩子的平均出勤率乘以100，即为当月得分。如果当月22天孩子的平均出勤率为94%，那么乘以100后，得分为94分。
    </p>
    <p class="text">
      4.<b>事故率。</b>事故本身具有多样性。从擦破皮到骨折，其中间会有很多程度不同的伤害等级。因此，一个明智的方法就是事先就伤害程度制定一个与分数相联系的等级表。
    </p>
    <p class="text">
      <b
        >请参考以下表格中的建议:这里给出的只是建议。至于到每一所幼儿园做怎样的规定，一定要根据具体情况而定。其中包括伤势与扣分值定多少合适，也要考虑园所的具体情况。</b
      >
    </p>
    <p class="text">
      <el-table :data="tableData1" border style="width: 100%">
        <el-table-column fixed prop="name" label="伤势" width="360">
        </el-table-column>
        <el-table-column prop="kfz" label="扣分值" width="360">
        </el-table-column>
        <el-table-column prop="remark" label="备注" width="250">
        </el-table-column>
      </el-table>
    </p>
    <p class="text">
      5.<b>卫生。</b>请加盟园根据幼儿园和班级情况制定卫生检查标准。请参考以下建议：
    </p>
    <p class="text">
      1)制定班级教室内卫生标准。包括孩子床上用品与玩教具等的消毒、清洗、教室紫外线消毒等。
    </p>
    <p class="text">
      2)如果没有公共保洁员，可将楼内的教室外公共区域分片划给各班级，并制定评分标准。
    </p>
    <p class="text">3)除进行联合检查外，常规卫生评估可由保健医负责执行。</p>
    <p class="text">4)评估标准制定应考虑的元素包括：</p>
    <p class="text">a)楼内室内外检查区域划定和项目确定；</p>
    <p class="text">b)以日、周和月为单位的检查周期；</p>
    <p class="text">
      c)规定联合（园长、教学主管、保健医和班长参加）检查和保健医单独检查的次数；
    </p>
    <p class="text">d)对保健医根据检查结果提出改进意见的执行程序。</p>
    <p class="text">
      5)将以日或周为单位的评估结果填写到“教师月绩效考核表”中相应位置。
    </p>
    <el-backtop target="body .app-activity">
      <!-- <div class="back-ball">
        Top
      </div> -->
    </el-backtop>
  </div>
</template>

<script>
export default {
  name: "teacherassessment_study",
  data() {
    return {
      tableData: [
        {
          name: "一日流程",
          pgfs: "",
          blqz: "60%",
          sdfs: "",
          remark: "",
        },
        {
          name: "教师考勤",
          pgfs: "",
          blqz: "10%",
          sdfs: "",
          remark: "",
        },
        {
          name: "儿童考勤",
          pgfs: "",
          blqz: "10%",
          sdfs: "",
          remark: "",
        },
        {
          name: "事故",
          pgfs: "",
          blqz: "15%",
          sdfs: "",
          remark: "",
        },
        {
          name: "卫生",
          pgfs: "",
          blqz: "5%",
          sdfs: "",
          remark: "",
        },
      ],
      tableData1: [
        {
          name: "皮肤淤青、红肿、轻度擦伤或身体部位扭伤",
          kfz: "10",
          remark: "",
        },
        {
          name: "皮肤开裂缝1-3针",
          kfz: "20",
          remark: "",
        },
        {
          name: "皮肤开裂缝4-6针",
          kfz: "40",
          remark: "",
        },
        {
          name: "骨裂与一般性骨折",
          kfz: "80",
          remark: "",
        },
        {
          name: "粉碎性骨折、致残与其他严重性伤害",
          kfz: "100",
          remark: "",
        },
      ],
    };
  },
  created() {},
  mounted() {},
  computed: {},
  methods: {},
};
</script>
<style lang="scss" scoped>
.app-activity {
  padding: 0 10%;
  height: 100vh;
  overflow: hidden;
  overflow-y: auto;
  margin: 0 auto;
  color: #333;
  height: 100%;
  @media (max-width: 768.98px) {
    padding: 0 3%;
  }
  ::v-deep .el-tabs__item {
    font-weight: bold;
  }
  p {
    margin: 0;
  }
  .indent {
    text-indent: 2rem;
  }
  .text {
    font-size: 14px;
    line-height: 22px;
    padding-bottom: 12px;
  }
  .title {
    padding: 10px 0;
    margin: 0;
    font-size: 22px;
    text-align: center;
  }
  .title2 {
    text-align: left;
    font-size: 16px;
    display: flex;
    align-items: center;
    &::before {
      content: "";
      margin-right: 8px;
      width: 4px;
      height: 18px;
      background: #325cf3;
    }
  }
  .title3 {
    font-weight: bold;
    font-size: 14px;
  }
  .tabs {
    margin-bottom: 40px;
  }
  .danger {
    color: #f56c6c;
  }
  .err {
    font-size: 16px;
  }
  .content {
    padding: 0 15px;
    .imgs {
      display: flex;
      padding: 0 10%;
      img {
        flex: 1;
        padding: 5px;
        width: 100%;
        &.img2 {
          width: 50%;
        }
      }
    }
    .row-item {
      line-height: 24px;
      padding-bottom: 5px;
      font-size: 14px;
    }
  }
}
</style>
