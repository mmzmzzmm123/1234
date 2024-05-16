<template>
  <div class="app-container">

    <el-form ref="postForm" :model="postForm" :rules="rules" label-position="top" label-width="100%">

      <el-card>

        <el-form-item label="题库名称" prop="title">
          <el-input v-model="postForm.title" />
        </el-form-item>

        <el-form-item label="题库备注" prop="remark">
          <el-input v-model="postForm.remark" type="textarea" />
        </el-form-item>

      </el-card>

      <div style="margin-top: 20px">
        <el-button type="primary" @click="submitForm">保存</el-button>
        <el-button type="info" @click="onCancel">返回</el-button>
      </div>

    </el-form>

  </div>
</template>
<script>
</script>
