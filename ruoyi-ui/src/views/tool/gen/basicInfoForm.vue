<template>
  <el-form :model="info" ref="basicInfoForm" :rules="rules">
    <el-row class="form-row" :gutter="24">
      <el-col :lg="{span: 6, offset: 6}" :md="12" :sm="24">
        <el-form-item label="表名称" prop="tableName">
          <el-input placeholder="请输入仓库名称" v-model="info.tableName"/>
        </el-form-item>
      </el-col>
      <el-col :lg="{span: 6, offset: 1}" :md="12" :sm="24">
        <el-form-item label="表描述" prop="tableComment">
          <el-input placeholder="请输入" v-model="info.tableComment"/>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row class="form-row" :gutter="24">
      <el-col :lg="{span: 6, offset: 6}" :md="12" :sm="24">
        <el-form-item label="实体类名称" prop="className">
          <el-input placeholder="请输入" v-model="info.className"/>
        </el-form-item>
      </el-col>
      <el-col :lg="{span: 6, offset: 1}" :md="12" :sm="24">
        <el-form-item label="作者" prop="functionAuthor">
          <el-input placeholder="请输入" v-model="info.functionAuthor"/>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row class="form-row" :gutter="24">
      <el-col :lg="{span: 13, offset: 6}" :md="12" :sm="24">
        <el-form-item label="备注" prop="remark">
          <el-input type="textarea" :rows="3" v-model="info.remark"></el-input>
        </el-form-item>
      </el-col>
    </el-row>
  </el-form>
</template>
<script>
export default {
  name: 'BasicInfoForm',
  props: {
    info: {
      type: Object,
      default: null
    }
  },
  data () {
    return {
      visible:false,
      mdl: {},
      rules: {
        tableName: [
          { required: true, message: '请输入表名称', trigger: 'blur' }
        ],
        tableComment: [
          { required: true, message: '请输入表描述', trigger: 'blur' }
        ],
        className: [
          { required: true, message: '请输入实体类名称', trigger: 'blur' }
        ],
        functionAuthor: [
          { required: true, message: '请输入作者', trigger: 'blur' }
        ]
      }
    }
  },
  created(){

  },
  methods:{

  }
}
</script>
