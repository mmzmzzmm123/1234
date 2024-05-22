<template>
  <div class="app-container">

    <el-row :gutter="24">

      <el-col :span="24" style="margin-bottom: 20px">

        <el-alert
          title="点击`开始考试`后将自动进入考试，请诚信考试！"
          type="error"
          style="margin-bottom: 10px"
        />

        <el-card class="pre-exam">

          <div><strong>考试名称：</strong>{{ detailData.title }}</div>
          <div><strong>考试时长：</strong>{{ detailData.totalTime }}分钟</div>
          <div><strong>试卷总分：</strong>{{ detailData.totalScore }}分</div>
          <div><strong>及格分数：</strong>{{ detailData.qualifyScore }}分</div>
          <div><strong>考试描述：</strong>{{ detailData.content }}</div>

        </el-card>

      </el-col>

      <el-col :span="24">

        <el-button :loading="loading" type="primary" icon="el-icon-caret-right" @click="handleCreate" v-hasPermi="['exammanage:paper:add']">
          开始考试
        </el-button>

        <el-button @click="handleBack">
          返回
        </el-button>

      </el-col>

    </el-row>
  </div>
</template>

<script>
import { getExam } from '@/api/exammanage/exam'
import { addPaper } from '@/api/exammanage/paper'

export default {
  data() {
    return {
      detailData: {},
      postForm: {
        examId: '',
        password: ''
      },
      rules: {
        password: [
          { required: true, message: '考试密码不能为空！' }
        ]
      },

      loading: false
    }
  },

  created() {
    this.postForm.examId = this.$route.params.examId
    this.getExam()
  },

  methods: {

    getExam() {
      getExam(this.postForm.examId).then(response => {
        this.detailData = response.data
      })
    },

    handleCreate() {
      const that = this
      this.loading = true

      addPaper(this.postForm).then(response => {
        console.log(response)

        if (response.code === 0) {
           const paperId = response.data
           setTimeout(function() {
            this.loading = false
            that.dialogVisible = false
            this.$router.push(`/exammanage/start-exam/startExam/${paperId}`);

          }, 1000)
        }
      }).catch(() => {
        this.loading = false
      })
    },

    handleBack() {
      this.$router.push({ name: 'ExamOnline' })
    }

  }
}
</script>

<style scoped>

  .pre-exam div {

    line-height: 42px;
    color: #555555;
  }

</style>

