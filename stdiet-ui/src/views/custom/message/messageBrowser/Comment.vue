<template>
  <div class="topic_comment_item">
    <div class="comment_avatar">
      <el-avatar size="medium">{{ data.fromName.substr(-1) }}</el-avatar>
    </div>
    <div class="comment_content">
      <div class="content_title">
        {{ getContentTitle(data) }}
      </div>
      <div class="content_type">{{ data.content }}</div>
      <div class="content_time">
        {{ formatDate(data.createTime) }}
        <div
          v-if="data.fromUid !== userId.toString()"
          class="reply_btn"
          @click="handOnClick(data)"
        >
          回复
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import dayjs from "dayjs";
import { mapGetters } from "vuex";

export default {
  data() {
    return {
      roleDict: {
        customer: "客户",
        dietician: "主营养师",
        after_sale: "售后营养师",
        dietician_assistant: "营养师助理",
      },
    };
  },
  props: ["data"],
  computed: {
    ...mapGetters(["userId"]),
  },
  methods: {
    formatDate(date) {
      return dayjs(date).format("MM-DD HH:mm");
    },
    handOnClick(data) {
      this.$emit("click", data);
    },
    getContentTitle(data) {
      return `${this.roleDict[data.fromRole]} - ${data.fromName}${
        data.commentId
          ? ` to ${this.roleDict[data.toRole]} - ${data.toName}`
          : ""
      }`;
    },
  },
};
</script>
<style lang="scss" scoped>
.topic_comment_item {
  margin: 12px;
  display: flex;

  .comment_avatar {
    flex: 0 0 36px;
  }

  .comment_content {
    flex: 1 0 0;
    margin-left: 8px;

    .content_title {
      font-size: 14px;
      color: #909399;
    }

    .content_type {
      padding: 4px 0;
    }

    .content_time {
      display: flex;
      font-size: 14px;
      color: #8c8c8c;

      .reply_btn {
        margin-left: 16px;
        cursor: pointer;
      }
    }
  }
}
</style>
