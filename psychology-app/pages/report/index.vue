<template>
    <view class="report-list">
        <view class="item" v-for="item in reportList">
            <view class="title">{{ item.title }}</view>
            <view class="date">完成时间：{{ item.createDate }}</view>
            <view class="btn">查看报告</view>
        </view>
        <no-data v-if="reportList.length == 0"></no-data>
        <view class="footer" v-else>已经到底了</view>
    </view>
</template>
<script>
import noData from '@/components/noData'
import userServer from '@/server/user'
export default {
    components: { noData },
    data() {
        return {
            reportList: []
        }
    },
    async created() {
        this.reportList = await userServer.getOrderList(1);
    },
}
</script>
<style lang="scss">
@import "@/style/common.scss";

page {
    background-color: #f8f8f8;

    .report-list {
        padding-top: 32upx;

        .item {
            width: 686upx;
            height: 261upx;
            background: #FFFFFF;
            box-shadow: 0px 4upx 28upx 0px rgba(119, 119, 119, 0.06);
            border-radius: 16upx;
            padding: 32upx;
            box-sizing: border-box;
            margin: 0 auto 24upx;
            position: relative;

            &::before {
                content: '';
                position: absolute;
                left: 0;
                top: 40upx;
                width: 6upx;
                height: 32upx;
                background: #FF703F;
                border-radius: 3upx;
            }

            .title {
                font-size: 32upx;
                font-weight: 600;
                color: #333333;
                line-height: 45upx;
                margin-bottom: 16upx;
            }

            .date {
                font-size: 28upx;
                font-weight: 400;
                color: #AAAAAA;
                line-height: 40upx;
            }

            .btn {
                height: 96upx;
                line-height: 96upx;
                font-size: 32upx;
                font-weight: 400;
                color: #FF703F;
                text-align: center;
                margin-top: 32upx;
                border-top: 1px solid rgba(204, 204, 204, 0.5);
            }
        }
    }
}
</style>