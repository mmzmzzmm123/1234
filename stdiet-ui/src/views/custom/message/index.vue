<template>
  <div class="user_message_wrapper">
    <MessageBrowser />
    <UserInfo />
  </div>
</template>
<script>
import MessageBrowser from "./messageBrowser/index";
import UserInfo from "./userInfo/index";
export default {
  data() {
    return {};
  },
  components: {
    MessageBrowser,
    UserInfo,
  },
  created() {},
  computed: {},
  methods: {},
};
</script>
<style lang="scss" scoped>
.user_message_wrapper {
  padding: 12px;
  display: flex;
  box-sizing: border-box;
  height: calc(100vh - 84px);
  background: #f3f4f5;

  & > div:nth-child(1) {
    flex: 3;
    background: white;
  }

  & > div:nth-child(2) {
    flex: 2;
    margin-left: 12px;
    padding-left: 12px;
    background: white;
  }
}
</style>
