export default {
  namespaced: true,
  state: {
    printerState: false,// 打印机连接状态
    websocketLastError: "打印机打印机服务未连接",// 连接错信息
    websocketMessage: "",// 服务端返回的信息
    socketTimestamp: new Date().getTime()//时间戳去监听数据的改变
  },
  mutations: {
    //websocket
    setSocketMessage(state, data) {//一旦获取到端端返回的信息，就改变时间戳
      state.socketTimestamp = new Date().getTime();
      state.websocketMessage = data;
      state.printerState = true;
    },
    setPrinterState(state, data) {
      state.printerState = data;
    },
    setWebsocketLastError(state, data) {
      state.websocketLastError = data;
    }
  }
}
