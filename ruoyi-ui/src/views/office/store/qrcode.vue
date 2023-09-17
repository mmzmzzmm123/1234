<template>

  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <!-- <el-form-item label="用户id" prop="userId">
        <el-input v-model="queryParams.userId" placeholder="只查询当前用户得store" clearable
          @keyup.enter.native="handleQuery" />
      </el-form-item> -->
      <el-form-item label="门店名称" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入门店名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="门店地址" prop="address">
        <el-input v-model="queryParams.address" placeholder="请输入门店地址" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
  
      <el-form-item label="所属商圈" prop="busiDistrict">
        <el-input v-model="queryParams.busiDistrict" placeholder="请输入所属商圈" clearable
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="地铁线路" prop="subway">
        <el-input v-model="queryParams.subway" placeholder="请输入地铁线路" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    
    </div>
</template>

<script>
</script>

<style>
</style>
