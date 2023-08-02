<script>

export default {
  onLaunch: function () {
    console.log("App Launch");	
  },
  mounted() {
	
	console.log("VConsole")
  },
  onShow: function () {
    console.log("App Show");
  },
  onHide: function () {
    console.log("App Hide");
  },
};


</script>

<style>
page {
  font-family: Source Han Sans CN, PingFangSC-Regular, PingFang SC;
  padding-bottom: calc(constant(safe-area-inset-bottom) + 10px); /* 兼容 iOS 设备 */
  padding-bottom: calc(env(safe-area-inset-bottom) + 10px); /* 兼容 iPhone X 及以上设备 */
}
</style>
