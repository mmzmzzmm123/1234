<template>
  <div class="message_browser_wrapper">
    <div class="topic_list">
      <div
        v-for="topic in topicList"
        :key="topic.topicId"
        :class="`topic_item ${
          selTopicId === topic.topicId ? 'topic_item_sel' : ''
        }`"
        @click="handleOnTopicClick(topic)"
      >
        <div class="topic_status topic_status_read" />
        <div class="topic_item_content">
          <div class="topic_content">{{ topic.content }}</div>
          <div class="topic_user_name">by {{ topic.name }}</div>
        </div>
        <div class="topic_info">
          <el-tag size="small">{{ topicTypeDict[topic.topicType] }}</el-tag>
          <div class="topic_time">{{ formatDate(topic.createTime) }}</div>
        </div>
      </div>
    </div>
    <div class="topic_detail">
      <div class="topic_detail_list">
        <div class="topic_detail_title">
          <div>{{ detailData.content }}</div>
          <div class="content_time" :style="{ marginTop: '4px' }">
            {{ formatDate(detailData.createTime) }}
            <div class="reply_btn" @click="handleOnReplyTopic(detailData)">
              回复
            </div>
          </div>
        </div>
        <div v-for="comment in detailData.comments" :key="comment.id">
          <Comment :data="comment" @click="handleOnReplyComment" />
          <div v-if="!!comment.replys">
            <div
              v-for="reply in comment.replys"
              :key="reply.id"
              class="comment_reply_item"
            >
              <Comment :data="reply" @click="handleOnReplyReply" />
            </div>
          </div>
        </div>
      </div>
      <div class="topic_detail_reply">
        <div
          :style="{ marginBottom: '8px', fontSize: '12px', color: '#8c8c8c' }"
        >
          回复：{{ replyTarget }}
        </div>
        <el-input type="textarea" :rows="3" v-model="replyContent" />
        <div class="send_btn_zone">
          <el-button
            type="primary"
            @click="handleOnReply"
            :disabled="!this.replyContent"
            size="mini"
            >发送</el-button
          >
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { createNamespacedHelpers } from "vuex";
import Comment from "./Comment";
import dayjs from "dayjs";
const {
  mapActions,
  mapState,
  mapMutations,
  mapGetters,
} = createNamespacedHelpers("message");
export default {
  data() {
    return {
      topicTypeDict: {
        0: "建议",
        1: "食谱",
        2: "咨询",
      },
      replyTarget: "",
      replyContent: "",
      replyObj: {},
    };
  },
  components: { Comment },
  created() {
    this.init();
  },
  computed: {
    ...mapState(["topicList", "selTopicId", "detailData"]),
  },
  methods: {
    formatDate(date) {
      return dayjs(date).format("MM-DD HH:mm");
    },
    handleOnTopicClick(data) {
      this.replyTarget = "";
      this.replyContent = "";
      this.replyObj = {};
      this.fetchTopicDetailActions({ topicId: data.topicId, id: data.id });
    },
    handleOnReplyTopic(data) {
      this.replyTarget = "主题";
      this.replyObj = {
        toRole: data.role,
        toUid: data.uid,
        topicId: data.topicId,
        img: [],
      };
    },
    handleOnReplyComment(data) {
      this.replyTarget = data.fromName;
      this.replyObj = {
        toRole: data.fromRole,
        toUid: data.fromUid,
        commentId: data.id,
        img: [],
      };
    },
    handleOnReplyReply(data) {
      this.replyTarget = data.fromName;
      this.replyObj = {
        toRole: data.fromRole,
        toUid: data.fromUid,
        commentId: data.commentId,
        replyId: data.id,
        img: [],
      };
    },
    handleOnReply() {
      if (this.replyTarget) {
        this.postTopicReplyActions({
          ...this.replyObj,
          content: this.replyContent,
        }).then((res) => {
          this.$message.success(res.msg);
          this.replyContent = "";
          this.replyTarget = "";
          this.replyObj = {};
        });
      } else {
        this.$message.error("请选择回复对象");
      }
    },
    ...mapActions(["init", "fetchTopicDetailActions", "postTopicReplyActions"]),
    ...mapMutations(["clean"]),
  },
};
</script>
<style lang="scss" scoped>
.message_browser_wrapper {
  display: flex;
  .topic_list {
    flex: 2;

    .topic_item {
      display: flex;
      padding: 8px 16px;
      cursor: pointer;

      &:hover {
        background: #dedede;
      }

      .topic_status {
        flex: 0 0 20px;
        display: flex;
        justify-content: center;
        padding: 7px 0;

        &::before {
          content: "";
          width: 8px;
          display: block;
          height: 8px;
          border-radius: 50%;
        }
      }

      .topic_status_read::before {
        background: #909399;
      }

      .topic_status_unread::before {
        background: #d96969;
      }

      .topic_item_content {
        flex: 1 0 0;

        .topic_content {
          width: 260px;
          overflow: hidden;
          white-space: nowrap;
          text-overflow: ellipsis;
          line-height: 1.5;
        }

        .topic_user_name {
          color: #8c8c8c;
          font-size: 14px;
          margin-top: 8px;
        }
      }

      .topic_info {
        flex: 0 0 80px;
        text-align: center;

        .topic_time {
          font-size: 14px;
          margin-top: 8px;
          color: #8c8c8c;
        }
      }
    }

    .topic_item_sel {
      background: #dedede;
    }
  }

  .topic_detail {
    flex: 3;
    background: #fafafa;
    padding: 16px;

    .topic_detail_list {
      height: calc(100vh - 300px);
      overflow: auto;

      .content_time {
        display: flex;
        font-size: 14px;
        color: #8c8c8c;

        .reply_btn {
          margin-left: 16px;
          cursor: pointer;
        }
      }

      .topic_detail_title {
      }

      .comment_reply_item {
        margin-left: 24px;

        .topic_comment_item {
          margin: 8px;

          /deep/.el-avatar {
            transform: scale(0.8);
          }
        }
      }
    }

    .topic_detail_reply {
      height: 160px;
      background: white;
      padding: 16px;

      .send_btn_zone {
        margin-top: 8px;
        text-align: right;
      }
    }
  }
}
</style>
