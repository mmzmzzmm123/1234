<template>
  <el-form :model="info" ref="genInfoForm">
    <el-row class="form-row" :gutter="24">
      <el-col :lg="{span: 6, offset: 6}" :md="12" :sm="24">
        <el-form-item >
          <span slot="label">生成模板
          </span>
          <el-tooltip content="树表没有分页,数据展示为树形表格" placement="top">
            <i class="el-icon-question"></i>
          </el-tooltip>
          <br>
          <el-select v-model="info.tplCategory" disabled="">
            <el-option value="crud">单表（增删改查）</el-option>
            <el-option value="tree">树表（增删改查）</el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :lg="{span: 6, offset: 1}" :md="12" :sm="24">
        <el-form-item>
          <span slot="label">生成包路径
          </span>
          <el-tooltip content="生成在哪个java包下，例如 com.ruoyi.system" placement="top">
            <i class="el-icon-question"></i>
          </el-tooltip>
          <el-input v-model="info.packageName"/>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row class="form-row" :gutter="24">
      <el-col :lg="{span: 6, offset: 6}" :md="12" :sm="24">
        <el-form-item>
          <span slot="label">生成模块名
          </span>
          <el-tooltip content="可理解为子系统名，例如 system" placement="top">
            <i class="el-icon-question"></i>
          </el-tooltip>
          <el-input v-model="info.moduleName"/>
        </el-form-item>
      </el-col>
      <el-col :lg="{span: 6, offset: 1}" :md="12" :sm="24">
        <el-form-item>
          <span slot="label">生成业务名
          </span>
          <el-tooltip content="可理解为功能英文名，例如 user" placement="top">
            <i class="el-icon-question"></i>
          </el-tooltip>
          <el-input v-model="info.businessName"/>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row class="form-row" :gutter="24">
      <el-col :lg="{span: 6, offset: 6}" :md="12" :sm="24">
        <el-form-item>
          <span slot="label">生成功能名
          </span>
          <el-tooltip content="用作类描述，例如 用户" placement="top">
            <i class="el-icon-question"></i>
          </el-tooltip>
          <el-input v-model="info.functionName"/>
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
      mdl: {}
    }
  },
  created(){

  }
}
</script>
