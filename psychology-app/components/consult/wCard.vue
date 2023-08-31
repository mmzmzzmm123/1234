<template>
  <view>
    <view class="section_7">
      <view class="image-wrapper_1">
        <image @tap="close" src="/static/consult/order/guanbi.png" class="label_8"></image>
      </view>
      <image v-if="picture" :src="picture" mode="widthFix" style="width: 620rpx;display: block"></image>
      <l-painter
          useCORS
          isCanvasToTempFilePath
          @success="picture = $event"
          hidden
          css="width: 620rpx;height: 1110rpx;text-align: center;position: relative;"
          @fail="fail"
          pathType="base64"
          ref="poster"
          performance
      >
        <template>
          <l-painter-view
              css="border-radius: 16rpx 16rpx 0 0;background-color: rgba(255,255,255,1);width: 620rpx;height: 478rpx;">
            <l-painter-image src="/static/consult/order/bg.png"
                             css="border-radius: 16rpx 16rpx 0 0;width: 620rpx;height: 240rpx;"/>
            <l-painter-image :src="data.avatar"
                             css="position: absolute;left: 245rpx;top: 175rpx;width: 130rpx;height: 130rpx;border-radius: 70rpx;border: 4rpx solid #FFFFFF;background-color: #EFEFF9;"/>
            <l-painter-view css="">
              <l-painter-text :text="data.nickName"
                              css="height: 42rpx;color: rgba(51,51,51,1);font-size: 30rpx;font-family: PingFangSC-Semibold;font-weight: 600;line-height: 42rpx;margin-top: 97rpx;"/>
              <l-painter-text :text="data.qualification"
                              css="width: 524rpx;height: 74rpx;overflow-wrap: break-word;color: rgba(119,119,119,1);font-size: 26rpx;font-family: PingFangSC-Regular;text-align: center;line-height: 37rpx;margin: 9rpx 0 16rpx 48rpx;"/>
            </l-painter-view>
          </l-painter-view>
          <l-painter-image src="/static/consult/order/bg2.png"
                           css="height: 40rpx;background-size: 100% 100%;display: flex;flex-direction: column;width: 620rpx;"/>
          <l-painter-view
              css="background-color: rgba(216,216,216,1.000000);width: 524rpx;height: 1rpx;display: flex;flex-direction: column;margin: -20rpx 48rpx;">
          </l-painter-view>
          <l-painter-view
              css="background-color: rgba(255,255,255,1.000000);border-radius: 0 0 16rpx 16rpx;width: 620rpx;display: flex;flex-direction: column;">

            <l-painter-text text="擅长领域"
                            css="width: 524rpx;height: 37rpx;overflow-wrap: break-word;color: rgba(51,51,51,1);font-size: 26rpx;font-family: PingFangSC-Semibold;font-weight: 600;text-align: left;white-space: nowrap;line-height: 37rpx;margin: 16rpx 0 0 48rpx;"/>
            <l-painter-text :text="data.wayStr"
                            css="width: 524rpx;overflow-wrap: break-word;color: rgba(119,119,119,1);font-size: 24rpx;font-weight: normal;text-align: left;margin: 11rpx 0 0 48rpx;"/>
            <l-painter-text :text="data.tabs"
                            css="width: 524rpx;overflow-wrap: break-word;color: rgba(119,119,119,1);font-size: 24rpx;text-align: left;margin: 11rpx 0 0 48rpx;"/>
            <l-painter-text text="咨询寄语"
                            css="width: 524rpx;height: 37rpx;color: rgba(51,51,51,1);font-size: 26rpx;font-family: PingFangSC-Semibold;font-weight: 600;line-height: 37rpx;margin: 29rpx 0 0 48rpx;text-align: left;"/>
            <l-painter-text :text="data.zxWord"
                            css="text-align: left;width: 524rpx;color: rgba(119, 119, 119, 1);font-size: 24rpx;font-family: PingFangSC-Regular;line-height: 33rpx;margin: 11rpx 0 0 48rpx;"/>

            <l-painter-view css="width: 239rpx;height: 100rpx;flex-direction: row;display: flex;justify-content: space-between;margin: 24rpx 0 40rpx 191rpx;">
              <l-painter-image :src="data.wxCard"
                               css="border-radius: 6rpx;width: 100rpx;height: 100rpx;display: flex;flex-direction: column;"/>
              <l-painter-image src="/static/consult/order/card.png"
                               css="border-radius: 6rpx;width: 100rpx;height: 100rpx;display: flex;flex-direction: column;"/>
            </l-painter-view>

          </l-painter-view>
        </template>
      </l-painter>

      <view class="group_13" v-if="picture">
        <view class="box_11">
          <view class="box_12"></view>
          <text class="text_34">长按图片保存图片</text>
          <view class="box_13"></view>
        </view>
      </view>
    </view>
  </view>
</template>

<script>

export default {
  name: "haibao",
  props: ["data"],
  data() {
    return {
      picture: ''
    }
  },
  methods: {
    close() {
      this.$emit('close')
    },
    fail(v) {
      console.log(v)
    },
    done(v) {
      console.log('绘制完成:')
      this.save()
    },
    save() {
      this.$refs.poster.canvasToTempFilePathSync({
        fileType: 'png',
        quality: 1,
        success: (res) => {
          console.log(res.tempFilePath)
          this.picture = res.tempFilePath
          this.saveImage()
        },
        fail(e) {
          console.log('???????????', e)
        }
      })
    },
    // 保存图征
    saveImage() {
      // #ifndef H5
      uni.saveImageToPhotosAlbum({
        filePath: this.picture,
        success(res) {
          uni.showToast({
            title: '已保存到相册',
            icon: 'success',
            duration: 2000
          });
        },
      });
      // const arr = []
      // arr.push(this.picture)
      // uni.previewImage({
      //   urls: arr,
      //   longPressActions: {
      //     itemList: ['发送给朋友', '保存图片', '收藏'],
      //     success: function(data) {
      //       console.log('选中了第' + (data.tapIndex + 1) + '个按钮,第' + (data.index + 1) + '张图片');
      //     },
      //     fail: function(err) {
      //       console.log(err.errMsg);
      //     }
      //   }
      // #endif
    }
  }
}
</script>

<style scoped>
.section_7 {
  background-color: rgba(51, 51, 51, 0.500000);
  position: absolute;
  left: 0rpx;
  top: 0rpx;
  width: 750rpx;
  height: 100%;
  padding:0 65rpx;
  text-align: center;
}

.image-wrapper_1 {
  width: 750rpx;
  height: 200rpx;
  display: flex;
  flex-direction: column;
}

.label_8 {
  width: 40rpx;
  height: 40rpx;
  margin: 145rpx 0 0 621rpx;
}

.group_13 {
  width: 750rpx;
  height: 251rpx;
  display: flex;
  flex-direction: column;
}

.box_11 {
  width: 524rpx;
  height: 40rpx;
  flex-direction: row;
  display: flex;
  justify-content: space-between;
  margin: 40rpx 0 0 48rpx;
}

.box_12 {
  background-color: rgba(216, 216, 216, 1.000000);
  width: 118rpx;
  height: 1rpx;
  margin-top: 19rpx;
  display: flex;
  flex-direction: column;
}

.text_34 {
  width: 224rpx;
  height: 40rpx;
  overflow-wrap: break-word;
  color: rgba(255, 255, 255, 1);
  font-size: 28rpx;
  font-family: PingFangSC-Regular;
  font-weight: normal;
  text-align: center;
  white-space: nowrap;
  line-height: 40rpx;
  margin-left: 32rpx;
}

.box_13 {
  background-color: rgba(216, 216, 216, 1.000000);
  width: 118rpx;
  height: 1rpx;
  display: flex;
  flex-direction: column;
  margin: 19rpx 0 0 32rpx;
}
</style>