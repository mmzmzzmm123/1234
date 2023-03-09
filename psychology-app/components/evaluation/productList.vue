<template>
    <view class="product-list-box">
        <view class="product-item" v-for="product in (productList || [])" @tap="toProduct(product)">
            <view class="txt-box">
                <view class="title txt-overflow txt-overflow-line2">{{
                        product.title
                }}</view>
                <view class="sub-title txt-overflow">{{ product.subtitle }}</view>
                <view class="price"><span class="icon">￥</span>{{ product.price }}</view>
            </view>
            <view class="img-box">
                <img :src="product.listShowPicture" />
            </view>
        </view>
        <view class="footer" v-show="productList.length > 0">已经到底了</view>
    </view>
</template>
<script>
export default {
    props: ['productList'],
    data() {
        return {}
    },
    methods: {
        toProduct(product) {
            uni.navigateTo({
                url: "/pages/evaluation/product?id=" + product.id,
            });
        }
    }
}
</script>
<style lang="scss">
@import "@/style/common.scss";

.product-item {
    display: flex;
    flex-direction: row;
    width: calc(100% -48upx);
    padding: 24upx;
    background: #ffffff;
    border-radius: 12upx;
    margin-bottom: 16upx;

    .txt-box {
        width: calc(100% - 198upx);
        flex: 1;
        margin-right: 15upx;

        .title {
            height: 84upx;
            font-size: 30upx;
            font-weight: 600;
            color: #333333;
            line-height: 42upx;
        }

        .sub-title {
            font-size: 26upx;
            font-weight: 400;
            color: #333333;
            height: 37upx;
            line-height: 37upx;
        }
    }

    .img-box {
        width: 183upx;
        height: 208upx;
        border-radius: 8upx;
    }

    .price {
        margin-top: 39upx;
    }
}
</style>