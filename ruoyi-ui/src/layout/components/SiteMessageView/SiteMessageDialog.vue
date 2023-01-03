<template>
  <!-- 站内信消息对话框 -->
  <el-dialog
    title="站内信详情"
    :visible.sync="visible"
    width="800px"
    append-to-body
  >
    <el-container>
      <el-header height="auto">
        <div class="title">{{ form.msgSiteTitle }}</div>
        <div class="text-align-right time sp">
          <span v-if="form.toUserId == 0">
            <el-link type="primary" :underline="false">【全体成员】</el-link>
          </span>
          <span>{{ form.sendUserName }}</span>
          <span>{{ form.createTime }}</span>
        </div>
      </el-header>
      <el-main style="padding: 0px 20px">
        <div v-html="form.msgSiteContent"></div>
      </el-main>
    </el-container>
    <div slot="footer" class="dialog-footer">
      <el-button @click="cancel">关闭</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { readSelfSiteRecords } from "@/api/message/siteRecords";
export default {
  components: {},
  props: {},
  data() {
    return {
      visible: false,
      // 表单参数
      form: {},
    };
  },
  watch: {},
  computed: {},
  methods: {
    open(form) {
      this.form = form;
      this.visible = true;
      if (form.hasRead != "0") {
        return;
      }
      // 个人站内信才去读取
      if (form.toUserId != 0) {
        readSelfSiteRecords(form.msgSiteId)
          .then(() => {
            form.hasRead = "1";
            // 通知刷新消息
            this.$store.dispatch("push/dispatchPullSelfMsg");
            // 更新当前读取的id
            this.$store.dispatch("siteMessage/changeReadId", form.msgSiteId);
          })
          .catch(() => {});
      }
    },
    // 取消按钮
    cancel() {
      this.visible = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        msgSiteId: null,
        sendUserId: null,
        sendUserName: null,
        toUserId: null,
        toUserName: null,
        msgSiteTitle: null,
        msgSiteContent: null,
        hasRead: null,
        createTime: null,
      };
    },
  },
  created() {},
  mounted() {},
};
</script>
<style lang="scss" scoped>
.time {
  opacity: 0.4;
  font-size: 10px;
}
.text-align-right {
  text-align: right;
}
.title {
  font-size: 20px;
  line-height: 30px;
}
.sp a,
span {
  margin-left: 10px;
  font-size: 10px;
}
</style>