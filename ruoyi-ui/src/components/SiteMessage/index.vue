<template>
  <div @click="show">
    <el-badge :value="count" class="item" :hidden="readUnReadCount == 0">
      <svg-icon icon-class="email" />
    </el-badge>
    <SiteMessageDialog ref="SiteMessageDialog"></SiteMessageDialog>
  </div>
</template>

<script>
import { readSelfUnReadCount } from "@/api/message/siteRecords";
import { CMD } from "@/api/push/push";

import newMsgMp3 from "@/assets/audios/newMsg.mp3";
import SiteMessageDialog from "@/layout/components/SiteMessageView/SiteMessageDialog";

export default {
  components: { SiteMessageDialog },
  data() {
    return {
      count: "",
      readUnReadCount: 0,
      newMsgAudio: new Audio(),
    };
  },
  computed: {
    showMessage: {
      get() {
        return this.$store.state.siteMessage.showMessage;
      },
      set(val) {
        this.$store.dispatch("siteMessage/changeSetting", {
          key: "showMessage",
          value: val,
        });
      },
    },
  },
  methods: {
    show() {
      this.showMessage = true;
    },
    // 加载未读数量
    loadData() {
      readSelfUnReadCount().then((res) => {
        this.readUnReadCount = res.data;
        this.count = res.data > 99 ? "99+" : res.data;
      });
    },
    // 播放通知提示音效
    async playNewMsg() {
      await this.newMsgAudio.play();
    },
    // 显示消息
    showNewMsg(data) {
      data = typeof data == "string" ? JSON.parse(data) : data;
      this.$notify({
        title: data.msgSiteTitle,
        message: "",
        onClick: () => {
          this.$refs.SiteMessageDialog.open(data);
        },
      });
      this.playNewMsg();
    },
    // 显示全局消息
    showNewMsgGlobal(data) {
      data = typeof data == "string" ? JSON.parse(data) : data;
      this.$refs.SiteMessageDialog.open(data);
    },
  },
  created() {
    this.newMsgAudio.src = newMsgMp3;
  },
  mounted() {
    // 监听未读消息数量
    this.$store.dispatch("push/addListen", {
      cmd: CMD.PULL_SITECOUNT,
      fn: this.loadData,
    });
    // 监听新站内信消息
    this.$store.dispatch("push/addListen", {
      cmd: CMD.PULL_SITEMSG,
      fn: this.showNewMsg,
    });
    // 监听全局站内信消息
    this.$store.dispatch("push/addListen", {
      cmd: CMD.GLOBAL_NOTICE,
      fn: this.showNewMsgGlobal,
    });
    this.loadData();
  },
  beforeDestroy() {
    // 销毁监听事件
    this.$store.dispatch("push/removeListen", {
      cmd: CMD.PULL_SITECOUNT,
      fn: this.loadData,
    });
    this.$store.dispatch("push/removeListen", {
      cmd: CMD.PULL_SITEMSG,
      fn: this.showNewMsg,
    });
    this.$store.dispatch("push/removeListen", {
      cmd: CMD.PULL_SITEMSG,
      fn: this.showNewMsgGlobal,
    });
  },
};
</script>

<style lang="scss" scoped>
.item {
  ::v-deep .el-badge__content {
    top: 14px;
    margin-right: 2px;
  }
}
</style>
