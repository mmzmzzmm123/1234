<template>
	<view>
		<view>
			<view class="top-shop-select">当前门店：
				<uo-select :value="storeId" :options="storeList" valueField="id" @change="selectStore"></uo-select>
			</view>
		</view>
		<view class="filter-bar">
			<u-button plain type="primary" @click="onAddUser">添加用户</u-button>
		</view>

		<view class="card-list">
			<view class="card" v-for="(obj, index) in resList" :key="obj.id">
				<view class="card__content">
					<!-- <uo-image src="https://mbdp01.bdstatic.com/static/landing-pc/img/logo_top.79fdb8c2.png"></uo-image> -->
					<view class="card__content__right">
						<view class="card__content__body">
							<view>用户昵称: {{obj.nickName}}</view>
							<view>手机号码: {{obj.phone}} </view>
							<view>角色: {{obj.roleName}} </view>
						</view>
						<view class="card__content__footer">
						</view>
					</view>
				</view>
				<view class="card__op-list">
					<u-button type="primary" @click="onRemoveUser(obj)">删除</u-button>
				</view>
			</view>
		</view>

	</view>
</template>

<script>
	import ROLES from "./roles.js"
	export default {
		data() {
			return {
				storeList: [],
				resList: []
			}
		},
		onLoad(option) {
			this.getStoreList();
			this.refresh();

		},
		computed: {
			storeId() {
				return this.$store.state.currentStore.id
			}
		},
		onPullDownRefresh() {
			this.refresh()
		},
		methods: {
			onSearchKeywordInput() {
				this.$u.debounce(this.refresh, 400)
			},
			refresh() {
				this.$api.getStoreCrewList({storeId: this.storeId}).then(res => {
					res.forEach(x=>{
						x.roleName = ROLES.find(r=>r.role == x.role).name
					})
					this.resList = res
					uni.stopPullDownRefresh()
				})
			},
			getStoreList() {
				this.$api.getStoreList().then(res => {
					this.storeList = res.rows
				})
			},
			selectStore(event) {
				this.$store.commit("$uStore", {
					name: 'currentStore',
					value: event
				})
				this.refresh();
			},
			onAddUser() {
				uni.navigateTo({
					url: "/pages/crew/add",
					events: {
						refresh: this.refresh
					}
				})
			},
			onRemoveUser(obj){
				uni.showModal({
					content: "确认删除员工" + obj.nickName + "？",
					success: res=>{
						if(res.confirm){
							this.$api.removeStoreUser(obj.id).then(()=>{
								this.refresh()
							})
						}
					}
				})
			}
		}
	}
</script>

<style lang="scss">
	.search-clause {
		display: flex;
		flex-direction: row-reverse;
		margin: 20rpx;
	}

	.filter-bar {
		display: flex;
		align-items: center;

		.uo-select {
			display: flex;
			padding: 0 10rpx;
			align-items: center;
			background-color: $u-primary-bg-color;
			border-radius: 10rpx;
			margin: 10rpx;
			box-sizing: border-box;
			height: 60rpx;
			flex: 1;

			/deep/ .uo-select__content {
				width: 100%;
				justify-content: space-between;
			}
		}

		.u-button {
			height: 60rpx;
			margin: 10rpx;
			width: 6em;
		}
	}
</style>
