<template>
  <div class="register">
    <!--  底部  -->
    <div class="el-register-footer">
      <span>Copyright © 2018-2022 私家农场 版权所有.</span>
    </div>
  </div>
</template>

<script>
export default {
  name: "Test",
  data() {
    return {
      codeUrl: ""
    };
  },
  created() {
    this.getCode();
  },
  methods: {
    getCode() {
    },
  }
};
</script>

<style rel="stylesheet/scss" lang="scss">
.register {
  height: 100%;
  background-color: #1f2d3d;
}

.el-register-footer {
  height: 40px;
  line-height: 40px;
  position: fixed;
  bottom: 0;
  width: 100%;
  text-align: center;
  color: #fff;
  font-family: Arial;
  font-size: 12px;
  letter-spacing: 1px;
}
</style>
