<template>
    <view class="tab-box">
        <view class="tab-item" v-for="(tab, index) in tabList" @tap="changeTab(tab.pagePath)">
            <view class="icon">
                <img :src="currentTabIndex == index ? tab.selectedIconPath : tab.iconPath" />
            </view>
            <view class="txt" :class="{ 'active': currentTabIndex == index }">{{ tab.text }}</view>
        </view>
    </view>
</template>
<script>
export default {
    props: ['currentIndex'],
    data() {
        return {
            currentTabIndex: 0,
            tabList: [
                {
                    pagePath: "/pages/consult/index",
                    iconPath: "/static/course/menu/index.png",
                    selectedIconPath: "/static/course/menu/index-active.png",
                    text: "首页"
                },
                // {
                //     pagePath: "/pages/consult/class",
                //     iconPath: "/static/course/menu/class.png",
                //     selectedIconPath: "/static/course/menu/class-active.png",
                //     text: "分类"
                // },
                {
                    pagePath: "/pages/consult/user",
                    iconPath: "/static/course/menu/user.png",
                    selectedIconPath: "/static/course/menu/user-active.png",
                    text: "我的"
                }
            ]
        }
    },
    created() {
        this.currentTabIndex = this.currentIndex;
    },
    methods: {
        changeTab(url) {
            uni.navigateTo({ url: url });
        }
    }
}
</script>
<style lang="scss">
.tab-box {
    width: 750upx;
    height: 98upx;
    background: #FFFFFF;
    position: fixed;
    left: 0;
    bottom: 0;
    z-index: 1000;
    display: flex;
    flex-direction: row;
    padding: 10upx;
    //box-sizing: border-box;
    //padding-bottom: constant(safe-area-inset-bottom); /* 兼容 iOS 设备 */
    padding-bottom: env(safe-area-inset-bottom); /* 兼容 iPhone X 及以上设备 */

    .tab-item {
        flex: 1;
        text-align: center;
        align-items: center;
        justify-items: center;

        .icon {
            width: 48upx;
            height: 48upx;

            margin: 0 auto;

            img {
                width: 100%;
            }
        }

        .txt {
            font-size: 20upx;
            font-weight: 400;
            color: #333333;
            line-height: 28upx;

            &.active {
                color: #FF703F;
            }
        }
    }
}
</style>