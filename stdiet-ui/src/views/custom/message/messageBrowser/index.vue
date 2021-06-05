<template>
  <div class="message_browser_wrapper">
    <div class="topic_list" @scroll="handleOnScroll">
      <div v-if="topicList && topicList.length">
        <div
          v-for="topic in topicList"
          :key="topic.topicId"
          :class="`topic_item ${
            selTopicId === topic.topicId ? 'topic_item_sel' : ''
          }`"
          @click="handleOnTopicClick(topic)"
        >
          <div
            :class="`topic_status ${
              topic.read ? 'topic_status_read' : 'topic_status_unread'
            }`"
          />
          <div class="topic_item_content">
            <div class="topic_content" :style="{ width: `${itemWidth}px` }">
              {{ topic.content }}
            </div>
            <div class="topic_user_name">by {{ topic.name }}</div>
          </div>
          <div class="topic_info">
            <el-tag size="small">{{ topicTypeDict[topic.topicType] }}</el-tag>
            <div class="topic_time">{{ formatDate(topic.createTime) }}</div>
          </div>
        </div>
      </div>
      <div v-else class="topic_list_empty">暂无消息</div>
    </div>
    <div class="topic_detail">
      <div class="topic_detail_list">
        <div
          class="topic_detail_title"
          v-if="!!detailData.content"
          @click="handleOnReplyTopic(detailData)"
        >
          <el-avatar :src="detailData.avatar">{{
            detailData.name.substr(-1)
          }}</el-avatar>
          <div :style="{ marginLeft: '8px' }">
            <div>{{ detailData.content }}</div>
            <div class="content_time" :style="{ marginTop: '4px' }">
              {{ formatDate(detailData.createTime) }}
              <div class="reply_btn">回复</div>
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
          回复 to：{{ replyTarget }}
        </div>
        <el-input
          type="textarea"
          :rows="3"
          v-model="replyContent"
          :disabled="!detailData.content"
        />
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
import { createNamespacedHelpers, mapActions as globalMapActions } from "vuex";
import Comment from "./Comment";
import dayjs from "dayjs";
import { keys } from "@/utils/websocket";
const { mapActions, mapState, mapMutations, mapGetters } =
  createNamespacedHelpers("message");
export default {
  data() {
    return {
      topicTypeDict: {
        0: "食材",
        1: "身体",
        2: "环境",
      },
      replyTarget: "",
      replyContent: "",
      replyObj: {},
      itemWidth: 160,
    };
  },
  components: { Comment },
  created() {
    this.init();
  },
  mounted() {
    window.addEventListener("message", this.handleOnMessage);

    setTimeout(() => {
      const itemElm = document.querySelector(".topic_item");
      if (itemElm) {
        console.log(itemElm);
        this.itemWidth = itemElm.clientWidth - 32 - 20 - 80;
      }
    }, 100);
  },
  unmounted() {
    window.removeEventListener("message", this.handleOnMessage);
  },
  computed: {
    ...mapState(["topicList", "selTopicId", "detailData"]),
  },
  methods: {
    handleOnScroll({ target }) {
      if (
        target.clientHeight + parseInt(target.scrollTop) ===
        target.scrollHeight
      ) {
        this.fetchTopicListApi();
      }
    },
    handleOnMessage({ data }) {
      if (data.type === keys.WS_TYPE_MESSAGE_COUNT) {
        const { data: tData } = data.data;
        const time = dayjs(tData.createTime).format("YYYY-MM-DD HH:mm:ss");
        const newTopicList = [
          {
            id: tData.id,
            content: tData.content,
            createTime: time,
            img: tData.img,
            topicId: tData.topicId,
            role: "customer",
            uid: tData.uid,
            updateTime: time,
            topicType: tData.topicType,
            read: tData.read,
          },
          ...this.topicList,
        ];
        this.save({
          topicList: newTopicList,
        });
      } else if (data.type === keys.WS_TYPE_NEW_CUSTOMER_REPLY) {
        const { count, topicId } = data.data;
        this.updateUnreadCount({
          msgUnreadCount: count,
        });
        if (this.selTopicId === topicId) {
          const tarTopic = this.topicList.find(
            (obj) => obj.topicId === topicId
          );
          if (tarTopic) {
            console.log({ tarTopic });
            this.fetchTopicDetailActions({
              topicId,
              id: tarTopic.id,
              uid: tarTopic.uid,
            });
          }
        }
      }
    },
    formatDate(date) {
      return dayjs(date).format("MM-DD HH:mm");
    },
    handleOnTopicClick(data) {
      if (data.topicId !== this.selTopicId) {
        this.replyTarget = "";
        this.replyContent = "";
        this.replyObj = {};
        this.fetchTopicDetailActions({
          topicId: data.topicId,
          id: data.id,
          uid: data.uid,
          callback: (err) => this.$message.error(err),
        });
      }
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
    ...mapActions([
      "init",
      "fetchTopicDetailActions",
      "postTopicReplyActions",
      "fetchTopicListApi",
    ]),
    ...mapMutations(["clean", "save"]),
    ...globalMapActions(["updateUnreadCount"]),
  },
};
</script>
<style lang="scss" scoped>
.message_browser_wrapper {
  display: flex;
  .topic_list {
    flex: 2;
    overflow: auto;

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
          width: 100px;
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

    .topic_list_empty {
      height: 100px;
      text-align: center;
      line-height: 100px;
      color: #8c8c8c;
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
          color: #1890ff;
          // cursor: pointer;
        }
      }

      .topic_detail_title {
        display: flex;
        cursor: pointer;
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
