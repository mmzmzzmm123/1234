<template>
  <el-card class="card">
    <el-alert title="添加教师信息" type="info" show-icon center :closable="false"/>

    <el-steps :active="stepIndex - '0'" finish-status="success">
      <el-step title="基本信息"/>
      <el-step title="教师信息"/>
      <el-step title="教师简介"/>
      <el-step title="添加成功"/>
    </el-steps>

    <el-form ref="addTeacherFormRef"
             :model="addTeacherForm"
             :rules="addTeacherFormRules"
             status-icon
    >
      <el-tabs tab-position="left" class="tabs" v-model="stepIndex" @tab-click="tabChanged">
        <el-tab-pane label="基本信息" name="0">
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="工号" prop="userName">
                <el-input v-model="addTeacherForm.userName"/>
              </el-form-item>
              <el-form-item label="姓名" prop="nickName">
                <el-input v-model="addTeacherForm.nickName"/>
              </el-form-item>
              <el-form-item label="性别" prop="sex">
                <el-select v-model="addTeacherForm.sex" placeholder="请选择性别">
                  <el-option
                    v-for="dict in sexDict"
                    :key="dict.dictValue"
                    :label="dict.dictLabel"
                    :value="dict.dictValue"
                  ></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="邮箱" prop="email">
                <el-input v-model="addTeacherForm.email" placeholder="请输入邮箱" maxlength="50"/>
              </el-form-item>

            </el-col>
          </el-row>

          <el-button type="primary" @click="tabNextBase">下一步</el-button>

        </el-tab-pane>
        <el-tab-pane label="教师信息" name="1">教师信息</el-tab-pane>
        <el-tab-pane label="教师简介" name="2">教师简介</el-tab-pane>
        <el-tab-pane label="添加成功" name="3">添加成功</el-tab-pane>

      </el-tabs>
    </el-form>


  </el-card>
</template>

<script>
import { getDicts } from '@/api/system/dict/data'

export default {
  name: 'addTeacher',

  // Huafv 凌陌笙 1295074635@qq.com
  data() {
    return {
      stepIndex: '0',
      // 表单参数
      addTeacherForm: {
        // 工号
        userName: null,
        // 姓名
        nickName: null,
        // 性别
        sex: undefined,
        email: null

      },
      sexDict: [],

      addTeacherFormRules: {
        userName: [
          { required: true, message: '请输入工号', trigger: 'blur' },
          { min: 2, max: 20, message: '工号长度在2到20个字符之间', trigger: 'blur' }
        ],
        nickName: [
          { required: true, message: '请输入姓名', trigger: 'blur' },
          { min: 2, max: 6, message: '工号长度在2到6个字符之间', trigger: 'blur' }
        ],
        sex: [
          { required: true, message: '请选择性别', trigger: 'blur' }
        ]

      }

    }
  },
  methods: {
    getDict() {
      getDicts('sys_user_sex').then(resp => {
        this.sexDict = resp.data
      })
    },
    tabChanged() {
      console.log(this.stepIndex)
    },
    tabNextBase() {
      let flag = true;
      const Field = ['userName', 'nickName', 'sex']
      for(let i = 0; i < Field.length; i++){
        flag = this.validField(Field[i])
        if(!flag) break;
      }
      if(flag){
        this.stepIndex = '1'
        console.log(this.addTeacherForm)
      }
    },
    validField(Field){
      let flag = null;
      this.$refs.addTeacherFormRef.validateField(Field, (errorMessage) => {
        let valid = errorMessage === '';
        if (!valid) {
          this.$message.error(errorMessage)
          flag = false
        }else{
          flag = true
        }
      })
      return flag
    }
  },
  created() {
    this.getDict()
  },
  computed: {}
}
</script>

<style scoped>
.card {
  margin: 10px;
}

.el-steps {

  margin-left: 20px;
  margin-right: 20px;
  margin-top: 10px;
}

.tabs {
  margin-top: 15px;
}

.el-tab-pane {
  padding-left: 10px;
}
</style>
