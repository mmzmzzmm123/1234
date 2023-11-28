<template>
  <div id="app">
    <router-view />
    <theme-picker />
  </div>
</template>

<script>
import ThemePicker from "@/components/ThemePicker";
import { mapMutations } from 'vuex';
export default {
  name: "App",
  components: { ThemePicker },
  metaInfo() {
    return {
      title:
        this.$store.state.settings.dynamicTitle &&
        this.$store.state.settings.title,
      titleTemplate: (title) => {
        return title
          ? `${title} - ${process.env.VUE_APP_TITLE}`
          : process.env.VUE_APP_TITLE;
      },
    };
  },
  created () {
    this.initWebSocket();
  },
  methods: { 
    ...mapMutations('printer', ['setSocketMessage','setPrinterState','setWebsocketLastError']),
    // 初始化weosocket
    initWebSocket() {
      this.$socket.onopen = this.websocketonopen; //连接成功方法
      this.$socket.onerror = this.websocketonerror; //报错方法
      this.$socket.onmessage = this.websocketonmessage; // 接收端返回或推送信息的方法
      this.$socket.onclose = this.websocketclose; //关闭
    },
    // 链接ws服务器,e.target.readyState = 0/1/2/3   0 CONNECTING ,1 OPEN, 2 CLOSING, 3 CLOSED
    websocketonopen(e) {
      this.setPrinterState(true);
      this.setWebsocketLastError("WebSocket连接成功 readyState=" + e.target.readyState  + ",msg:" + JSON.stringify(e));
    },
    websocketclose(e) {
      this.setPrinterState(false);
      this.setWebsocketLastError("WebSocket连接关闭:" + JSON.stringify(e));
    },
    websocketonerror(e) {
      this.setWebsocketLastError("WebSocket连接错误:" + JSON.stringify(e));
    },
    // 接收端发送过来的信息,整个项目接收信息的唯一入口
    websocketonmessage(e) {
      this.setSocketMessage(e.data);
    }
  }
};
</script>
<style scoped>
#app .theme-picker {
  display: none;
}
</style>
