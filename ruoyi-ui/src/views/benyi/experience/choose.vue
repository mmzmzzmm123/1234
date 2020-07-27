 <template>
  <div class="result-content">
    <el-link class="btn" :href="href_apply">入班半日体验申请</el-link>
    <el-link class="btn" :href="href_result">申请结果查询</el-link>
  </div>
</template>
<script>
export default {
  name: "Choose",
  data() {
    return {
      href_apply: "",
      href_result: "",
    };
  },
  created() {
    const sid = this.$route.params && this.$route.params.id;
    this.href_apply = "/experience/apply/" + sid;
    this.href_result = "/experience/result/" + sid;
  },
};
</script>

<style lang="scss" scoped>
  .result-content {
    height: 100%;
    background: linear-gradient(-45deg, #6ab7ff, #edf7fb);
    display: flex;
    justify-content: center;
    padding-top: 100px;
    .btn {
      width: 200px;
      height: 40px;
      line-height: 40px;
      text-align: center;
      margin: 0 10px;
      font-size: 16px;
      font-weight: 700;
      text-decoration: none;
      background: #1b469c;
      color: #fff;
      border-radius: 20px;
      &::after {
        display: none;
      }
      transition: all .5s;
      &:hover {
        background: #072052;
      }
    }
  }
</style>