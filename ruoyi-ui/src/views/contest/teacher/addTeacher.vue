<template>
  <el-card class="card">
    <el-alert title="添加教师信息" type="info" show-icon center :closable="false"/>

  <el-steps :active="stepIndex - '0'" finish-status="success">
    <el-step title="基本信息" />
    <el-step title="教师信息" />
    <el-step title="教师简介" />
    <el-step title="添加成功" />
  </el-steps>

  <el-form ref="addTeacherFormRef"
   :model="addTeacherForm"
   :rules="addTeacherFormRules"
   status-icon
   >
    <el-tabs tab-position="left" class="tabs" v-model="stepIndex">
      <el-tab-pane label="基本信息" name="0">
        <el-form-item label="工号" prop="job_name">
          <el-input v-model="addTeacherForm.job_name"/>
        </el-form-item>
        <el-form-item label="姓名" prop="username">
          <el-input v-model="addTeacherForm.username"/>
        </el-form-item>

      </el-tab-pane>
      <el-tab-pane label="教师信息" name="1">教师信息</el-tab-pane>
      <el-tab-pane label="教师简介" name="2">教师简介</el-tab-pane>
      <el-tab-pane label="添加成功" name="3">添加成功</el-tab-pane>

    </el-tabs>
  </el-form>


  </el-card>
</template>

<script>
export default {
  name: 'addTeacher',
  // Huafv 卢俊 1295074635@qq.com
  data(){
    return{
      stepIndex : '0',
      addTeacherForm:{
        // 工号
        job_name:'',
        username:'',

      },
      addTeacherFormRules:{
        job_name: [
          { required: true, message: '请输入工号', trigger: 'blur' },
          { min: 3, max: 16, message: '工号长度在3到16个字符之间', trigger: 'blur' },
        ],
        username:[
          { required: true, message: '请输入姓名', trigger: 'blur' },
          { min: 2, max: 6, message: '工号长度在2到6个字符之间', trigger: 'blur' },
        ]

      }


    }
  },
  methods:{

  },
  created() {

  },
  computed(){

  }
}
</script>

<style scoped>
.card{
  margin: 10px;
}

.el-steps{

  margin-left: 20px;
  margin-right: 20px;
  margin-top: 10px;
}

.tabs{
  margin-top: 15px;
}
.el-tab-pane{
  padding-left: 10px;
}
</style>
