 <template>
  <div>
    <el-link type="primary" :href="'/experience/apply/{{schoolId}}'">入班半日体验申请</el-link>
    <el-link type="success" :href="'/experience/result/{{schoolId}}'">申请结果查询</el-link>
  </div>
</template>
<script>
export default {
  name: "Choose",
  data() {
    return {
      schoolId: "",
    };
  },
  created() {
    const sid = this.$route.params && this.$route.params.id;
    this.schoolId = sid;
  },
};
</script>