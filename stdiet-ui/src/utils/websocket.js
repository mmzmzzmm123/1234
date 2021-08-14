const { protocol, hostname, origin, port } = window.location;
const wsProtocol = protocol.startsWith("https") ? "wss" : "ws";
const url = `${wsProtocol}://${hostname}${
  hostname === "localhost" ? ":8091" : ""
}/ws`;
let ws = undefined;
let intervalRef = undefined;

function handleOnMessageReceive({ data = {} }) {
  if (data.type && data.type === keys.GET_UNREAD_COUNT) {
    ws.send(keys.GET_UNREAD_COUNT);
  }
}

function connect() {
  try {
    ws = new WebSocket(url);

    ws.onopen = event => {
      console.log("ws连接成功");
      intervalRef && clearInterval(intervalRef);

      setInterval(() => {
        ws && ws.send("ping");
      }, 30000);

      window.addEventListener("message", handleOnMessageReceive);
    };

    ws.onmessage = event => {
      // console.log({ event });

      const dataObj = JSON.parse(event.data || "{}");

      if (dataObj.type && dataObj.type !== "WS_TYPE_HEART_BEAT") {
        window.postMessage(dataObj, origin);
      }
    };

    ws.onerror = event => {
      // console.log({ event });
      ws && ws.close();
      ws = undefined;
      window.removeEventListener("message", handleOnMessageReceive);

      // websocketInit();
    };

    ws.onclose = event => {
      // console.log(event);
      ws = undefined;
      window.removeEventListener("message", handleOnMessageReceive);
      // if (event.reason !== "unmount") {
      // websocketInit();
      // }
    };
  } catch (error) {
    // console.log(error);
    // console.log("浏览器不支持websocket");
    ws.close();
    ws = undefined;
    // websocketInit();
  }
}

export function beforeUnmount(code) {
  ws && ws.close(code, "unmount");
}

export function websocketInit() {
  !ws && connect();

  !ws &&
    (intervalRef = setInterval(() => {
      console.log("尝试连接websocket");
      !ws && connect();
    }, 10000));
}

export const keys = {
  GET_UNREAD_COUNT: "GET_UNREAD_COUNT",
  WS_TYPE_MESSAGE_COUNT: "WS_TYPE_MESSAGE_COUNT",
  WS_TYPE_NEW_CUSTOMER_REPLY: "WS_TYPE_NEW_CUSTOMER_REPLY"
};
