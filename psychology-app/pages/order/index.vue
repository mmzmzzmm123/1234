<template>
    <view class="order-page">
        <view class="tab">
            <span class="tab-item" v-for="tab in tabList" :class="{ 'active': tab.status == currentStatus }"
                @tap="changeTab(tab.status)">{{ tab.title }}</span>
        </view>
        <view class="order-item" v-for="order in orderList">
            <view class="title">{{ order.gaugeTitle }}</view>
            <view class="price">{{ order.amount }}</view>
            <view class="buy-time">{{ order.createTime }}</view>
            <view class="order-no">{{ order.orderId }}</view>
            <view class="btn" v-show="order.status == 1" @tap="toTest(order)">去测试</view>
            <view class="btn" v-show="order.status != 1" @tap="toResult(order)">查看报告</view>
        </view>
        <no-data v-if="orderList.length == 0"></no-data>
        <view class="footer" v-else>已经到底了</view>
    </view>
</template>
<script>

import noData from '@/components/noData'
import userServer from '@/server/user'
import questionServer from '@/server/question'
export default {
    components: { noData },
    data() {
        return {
            currentStatus: '',
            tabList: [{ title: '全部', status: '' }, { title: '未完成', status: 2 }, { title: '已完成', status: 1 }],
            orderList: []
        }
    },
    async created() {
        this.orderList = await userServer.getOrderList();
    },
    methods: {
        toTest(order) {
            uni.setStorageSync("gaugeDes", order.gaugeDes);
            uni.navigateTo({ url: `/pages/testBefore/index?productId=${order.gaugeId}&&orderId=${order.orderId}` });
        },
        async changeTab(status) {
            this.currentStatus = status;
            this.orderList = await userServer.getOrderList(status);
        },
        async toResult(order) {
            let result = await questionServer.setResult(order.orderId);
            if (result.code == 200) {
                uni.setStorageSync("result", result.data);
                uni.navigateTo({
                    url: "/pages/result/index?productId=" + order.gaugeId,
                });
            }

        },
    }
}
</script>
<style lang="scss">
@import "@/style/common.scss";

page {
    background-color: #f8f8f8;

    .tab {
        height: 88upx;
        line-height: 88upx;
        display: flex;
        flex-direction: row;
        font-size: 32upx;
        font-weight: 400;
        color: #333333;

        .tab-item {
            flex: 1;
            text-align: center;
            position: relative;

            &.active {
                color: #FF703F;

                &::after {
                    width: 156upx;
                    height: 4upx;
                    background: #FF703F;
                    border-radius: 3upx;
                    content: '';
                    position: absolute;
                    left: calc(50% - 78upx);
                    bottom: 0;

                }
            }
        }
    }

    .order-item {
        width: 686upx;
        height: 364upx;
        background: #FFFFFF;
        box-shadow: 0 4upx 28upx 0 rgba(119, 119, 119, 0.06);
        border-radius: 16upx;
        margin: 24upx 32upx 0;
        padding: 32upx;
        box-sizing: border-box;
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
            margin-bottom: 14upx;
        }

        .price {
            font-size: 32upx;
            font-weight: 600;
            color: #FF3F64;
            line-height: 45upx;
            margin-bottom: 16upx;
        }

        .buy-time,
        .order-no {
            font-size: 28upx;
            font-weight: 400;
            color: #AAAAAA;
            line-height: 40upx;
            margin-bottom: 12upx;
        }

        .btn {
            margin-top: 12upx;
            line-height: 96upx;
            font-size: 32upx;
            font-weight: 400;
            color: #FF703F;
            text-align: center;
            border-top: 1px solid rgba(204, 204, 204, 0.5);

        }
    }

}
</style>