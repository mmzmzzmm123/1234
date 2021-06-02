import { getToken } from "./auth";

const { protocol, hostname, origin, port } = window.location;
const wsProtocol = protocol.startsWith("https") ? "wss" : "ws";
const url = `${wsProtocol}://${hostname}${
  port === "80" || port === "443" ? "" : `:${8091}`
}/ws`;
let ws = undefined;
let intervalRef = undefined;

function handleOnMessageReceive(event) {
  console.log({ event });
}

function connect() {
  try {
    ws = new WebSocket(url);

    ws.onopen = event => {
      console.log("ws连接成功");
      intervalRef && clearInterval(intervalRef);

      document.addEventListener("message", handleOnMessageReceive);
    };

    ws.onmessage = event => {
      console.log({ event });

      const dataObj = JSON.parse(event.data || "{}");

      if (dataObj.type && dataObj.type !== "WS_TYPE_HEART_BEAT") {
        window.postMessage(dataObj, origin);
      }
    };

    ws.onerror = event => {
      console.log({ event });
      ws = undefined;
      document.removeEventListener("message", handleOnMessageReceive);
    };

    ws.onclose = event => {
      ws = undefined;
      document.removeEventListener("message", handleOnMessageReceive);
    };
  } catch (error) {
    console.log(error);
    console.log("浏览器不支持websocket");
  }
}

export function init() {
  intervalRef = setInterval(() => {
    console.log("尝试连接websocket");
    !ws && connect();
  }, 10000);
}
