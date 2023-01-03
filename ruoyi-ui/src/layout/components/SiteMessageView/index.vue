<template>
  <el-drawer
    size="320px"
    :visible="visible"
    :with-header="false"
    :append-to-body="true"
    :show-close="false"
    @close="visible = false"
  >
    <el-container class="drawer-container">
      <el-header height="auto">
        <el-row>
          <el-col :span="12">站内信</el-col>
          <el-col :span="12" class="text-align-right">
            <el-link
              type="primary"
              :underline="false"
              icon="el-icon-refresh"
              @click="() => pullDown()"
            >
              刷新
            </el-link>
          </el-col>
        </el-row>
      </el-header>
      <el-main v-infinite-scroll="pullUp">
        <div v-if="allRecords && allRecords.length > 0">
          <div v-for="(item, index) in allRecords" :key="index">
            <el-badge is-dot class="badge-item" :hidden="item.hasRead != '0'">
              <el-card class="card-item">
                <div
                  :class="{
                    unRead: item.hasRead == '0',
                  }"
                >
                  {{ item.msgSiteTitle }}
                </div>
                <el-row class="time">
                  <el-col :span="12"> {{ item.sendUserName }}</el-col>
                  <el-col :span="12" class="text-align-right">
                    {{ item.createTime }}
                  </el-col>
                </el-row>
                <div class="text-align-right sp">
                  <el-link
                    icon="el-icon-folder-opened"
                    type="primary"
                    @click="handleRead(item)"
                  >
                    查看
                  </el-link>
                  <el-link
                    icon="el-icon-delete"
                    type="primary"
                    @click="handleDelete(item)"
                  >
                    删除
                  </el-link>
                </div>
              </el-card>
            </el-badge>
          </div>
          <div class="text-align-center" v-show="noMore">没有更多了</div>
        </div>
        <div v-else class="empty">- 空内容 -</div>
      </el-main>
    </el-container>
    <SiteMessageDialog ref="SiteMessageDialog"></SiteMessageDialog>
  </el-drawer>
</template>

<script>
import {
  selfPullLast,
  selfPullReLast,
  delSelfSiteRecords,
} from "@/api/message/siteRecords";
import { CMD } from "@/api/push/push";
import { mapState } from "vuex";
import SiteMessageDialog from "@/layout/components/SiteMessageView/SiteMessageDialog";
export default {
  components: { SiteMessageDialog },
  data() {
    return {
      allRecords: [],
      flushIng: false,
      pageSize: 50,
      noMore: false,
    };
  },
  computed: {
    visible: {
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
    ...mapState({
      readId: (state) => state.siteMessage.readId,
    }),
  },
  methods: {
    // 下拉刷新
    pullDown(done) {
      if (this.flushIng) {
        return;
      }
      this.flushIng = true;
      selfPullReLast(0)
        .then((res) => {
          this.allRecords = res.data;
        })
        .finally(() => {
          this.flushIng = false;
          if (done) {
            done();
          }
          // 检查是否还有更多
          this.noMore = this.allRecords.length < this.pageSize;
        });
    },
    // 上拉加载
    pullUp(done) {
      if (this.flushIng) {
        return;
      }
      if (this.allRecords.length == 0) {
        this.pullDown(done);
        return;
      }
      selfPullReLast(this.allRecords[this.allRecords.length - 1].msgSiteId)
        .then((res) => {
          this.allRecords.push(...res.data);
          if (done) {
            done(res.data.length < this.pageSize);
          }
          // 检查是否还有更多
          this.noMore = res.data.length < this.pageSize;
        })
        .catch(() => {
          if (done) {
            done(false);
          }
          // 检查是否还有更多
          this.noMore = false;
        });
    },
    // 拉取最新消息
    pullLast() {
      if (this.flushIng || this.noMore) {
        return;
      }
      if (this.allRecords.length == 0) {
        this.pullDown();
        return;
      }
      this.flushIng = true;
      selfPullLast(this.allRecords[0].msgSiteId)
        .then((res) => {
          this.allRecords.unshift(...res.data);
          if (res.data.length >= this.pageSize) {
            this.pullLast();
          }
          // 检查是否还有更多
          this.noMore = res.data.length < this.pageSize;
        })
        .finally(() => {
          this.flushIng = false;
        });
    },
    /** 查看按钮操作 */
    handleRead(row) {
      this.$refs.SiteMessageDialog.open(row);
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      this.$modal
        .confirm("是否确认删除消息？")
        .then(function () {
          return delSelfSiteRecords(row.msgSiteId);
        })
        .then(() => {
          this.$modal.msgSuccess("删除成功");
          let index = this.allRecords.findIndex(
            (e) => e.msgSiteId == row.msgSiteId
          );
          if (index > -1) {
            this.allRecords.splice(index, 1);
            // 通知刷新消息
            this.$store.dispatch("push/dispatchPullSelfMsg");
          }
        })
        .catch(() => {});
    },
  },
  watch: {
    // 监听读取的id
    readId(msgSiteId) {
      if (!msgSiteId) {
        return;
      }
      let item = this.allRecords.find((e) => e.msgSiteId == msgSiteId);
      if (item) {
        // 修改为已读取
        item.hasRead = "1";
      }
    },
  },
  mounted() {
    // 初始化一次
    this.pullDown();
    // 监听新站内信消息
    this.$store.dispatch("push/addListen", {
      cmd: CMD.PULL_SITEMSG,
      fn: this.pullLast,
    });
  },
  beforeDestroy() {
    // 销毁监听事件
    this.$store.dispatch("push/removeListen", {
      cmd: CMD.PULL_SITEMSG,
      fn: this.pullLast,
    });
  },
};
</script>

<style lang="scss" scoped>
.time {
  opacity: 0.4;
  font-size: 10px;
}
.text-align-center {
  text-align: center;
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
.drawer-container {
  padding: 10px 0px;
  font-size: 14px;
  line-height: 1.5;
  word-wrap: break-word;
  height: 100%;
  .badge-item {
    width: 100%;
  }
  .card-item {
    margin-bottom: 6px;
    ::v-deep .el-card__body {
      padding: 2px !important;
    }
    .unRead {
      font-weight: bold;
    }
  }
  .empty {
    margin-top: 22%;
    text-align: center;
    line-height: 1.6em;
    font-size: 1.6em;
    color: #dcdfe6;
  }
}
</style>
