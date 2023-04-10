<template>
    <view class="result-page">
        <view class="line-box"></view>
        <view class="result-box">
            <view class="info-title">测试结果</view>
            <view class="info" v-html="result">                
            </view>
        </view>
        <view class="submit-btn" @tap="getResult">保存结果</view>
        <view class="result-mask" v-show="maskShow" @longpress="handleLongPress" v-if="!imageData">
            <view class="mask-box">
				<view @tap="maskShow = false">
					<image class="close" src="/static/evaluation/index/product/close.png"></image>
				</view>
				<view id="post">
					<view class="title">测试结果</view>
					<view class="img-box" v-html="result">                    
					</view>
					<view class="user-info">
					    <view class="header">
					        <image mode="widthFix" class="img-item" :src="userInfo.avatar"></image>
					    </view>
					    <view class="txt-box">
					        <view class="name">{{ userInfo.name }}</view>
					        <view class="cue">长按识别二维码进入测试乐园</view>
					    </view>
					    <view class="qr-code-box">
					        <image mode="widthFix" class="img-item" :src="qrcodeUrl"></image>
					    </view>
					</view>					
				</view>				
                
				<view class="bottom-cue">长按图片保存海报</view>
            </view>			
		</view>
		<view @tap="imageData = null" v-else>
			<img class="poster" :src="imageData"  mode="widthFix" crossorigin="anonymous" />
		</view>
		
        <evaluation-tab-bar></evaluation-tab-bar>
    </view>
</template>
<script>
import qrcodeServer from '@/server/qrcode'
import html2canvas from '@/common/html2canvas.min.js'
export default {
    data() {
        return {
			userInfo: {},
            maskShow: false,
            result: '',
			qrcodeUrl: '/static/evaluation/index/product/55.png',
			imageData: null // 生成的海报的url
        }
    },
    created() {
		this.userInfo = uni.getStorageSync("userInfo");
        this.result = uni.getStorageSync("result");
    },
    methods: {
        async getResult() {
            this.maskShow = true;
			
			// 生成二位码			
			const res = await qrcodeServer.getQrcode(location.href)
			if (res.code == 200) {
				this.qrcodeUrl = res.data.images_url
			}
        },
		handleLongPress(e) {
			// 生成海报
			uni.showLoading({
				title: '正在生成海报'
			  })
			  let dom = document.querySelector('#post')
			  html2canvas(dom, {
				width: dom.clientWidth, //dom 原始宽度
				height: dom.clientHeight,
				scrollY: 0,
				scrollX: 0,
				useCORS: true
			  }).then((canvas) => {
				uni.hideLoading()
				
				let image = new Image()
				image.crossOrigin = '*' // 支持跨域图片
				image.src = this.qrcodeUrl
				
				
				//成功后调用返回canvas.toDataURL返回图片的imageData
				this.imageData = canvas.toDataURL('image/png', 1)
				// this.saveImg(this.imageData)
				// this.saveBase64Img(this.imageData)				
				// 关闭海报
				// this.imageData = null
			  })
			
		},
		// 转为base64图片
		getBase64Image(img) {
		    let canvas = document.createElement('canvas')
		    canvas.width = img.width
		    canvas.height = img.height
		    let ctx = canvas.getContext('2d')
		    ctx.drawImage(img, 0, 0, canvas.width, canvas.height)
		    let dataURL = canvas.toDataURL('image/png') // 可选其他值 image/jpeg
		    return dataURL
		},
		saveImg(url) {
			var oA = document.createElement("a");
			oA.download = this.userInfo.name + '的测评海报'; // 设置下载的文件名，默认是'下载'
			oA.href = url;
			document.body.appendChild(oA);
			oA.click();
			oA.remove(); // 下载之后把创建的元素删除	
			
		},
		
    }
}
</script>
<style lang="scss">
page {
    background-color: #FFF9F7;
    margin-top: 22upx;

    .line-box {
        width: 718upx;
        height: 32upx;
        margin: 0 auto;
        background: #FF703F;
        box-shadow: 0px 1px 12px 0px rgba(255, 112, 63, 0.4);
        border-radius: 19upx;
        backdrop-filter: blur(20upx);
    }

    .result-box {
        width: 686upx;
        max-height: 950upx;
        background: #FFFFFF;
        box-shadow: 0 4upx 16upx 0 rgba(255, 112, 63, 0.16);
        border-radius: 0px 0px 16upx 16upx;
        padding: 57upx 40upx 48upx 48upx;
        box-sizing: border-box;
        z-index: 100;
        position: absolute;
        left: 32upx;
        top: 16upx;
        overflow: auto;
    }

    .info-title {
        margin-bottom: 32upx;
        font-size: 32upx;
        font-weight: bold;
        text-align: center;
        color: #333333;
        line-height: 50upx;

        &::after {
            display: block;
            width: 144upx;
            height: 8upx;
            background: #FF703F;
            border-radius: 2upx;
            content: '';
            margin: -10upx auto 0;
        }
    }

    .img-item {
        width: 100%;
    }


    .submit-btn {
        width: 590upx;
        height: 88upx;
        background: #FF703F;
        border-radius: 44upx;
        text-align: center;
        line-height: 88upx;
        font-size: 32upx;
        font-weight: 600;
        color: #FFFFFF;
        position: absolute;
        bottom: 200upx;
        left: 80upx;
    }

    .result-mask {
        position: fixed;
        top: 0;
        left: 0;
        width: 100vw;
        height: 100vh;
        background: rgba(0, 0, 0, 0.45);
        z-index: 100000;

        .mask-box {
            width: 620upx;
            max-height: 90vh;
            position: absolute;
            left: 65upx;
            top: 5vh;           
			
			#post {
				background: linear-gradient(225deg, #FF6A47 0%, #FFA522 100%);
				border-radius: 8px;
				padding: 24px 15px 52px;
				box-sizing: border-box;
			}
            .title {
                font-size: 32upx;
                font-weight: 600;
                color: #FFFFFF;
                line-height: 45upx;
                margin-bottom: 24upx;
                text-align: center;
            }
			
			.close {
				width: 40upx;
				height: 40upx;
				position: absolute;
				top: 15px;
				right: 15px;
			}

            .img-box {
                width: 560upx;
				height: 800upx;
                background: #FFFFFF;
                border-radius: 8upx;
                padding: 24upx 28upx;
                box-sizing: border-box;
                margin-bottom: 24upx;
				max-height: 400px;
				overflow: scroll;
            }

            .user-info {
                display: flex;
                flex-direction: row;
                justify-content: space-between;

                .header {
                    width: 72upx;
                    height: 72upx;

                    .img-item {
                        border-radius: 100%;
                    }
                }

                .txt-box {
                    font-size: 24upx;
                    font-weight: 500;
                    color: #FFFFFF;
                    line-height: 72upx;

                    .cue {
                        font-size: 24upx;
                        font-weight: 400;
                        line-height: 24upx;
                        margin-top: 48upx;
                    }
                }

                .qr-code-box {
                    width: 142upx;
                    height: 142upx;
                    background: #FFFFFF;
                    border-radius: 8upx;
                }
            }

            .bottom-cue {
                height: 80upx;
                background: #FFFFFF;
                border-radius: 0 0 16upx 16upx;
                line-height: 80upx;
                font-size: 28upx;
                font-weight: 400;
                color: #777777;
                position: absolute;
                bottom: 0;
                left: 0;
                width: 100%;
                text-align: center;
            }
        }
    }
	
	.poster {
		width: 620upx;
		max-height: 90vh;
		position: absolute;
		left: 65upx;
		top: 5vh;           
		position: fixed;
		z-index: 100000;		
	}
}
</style>