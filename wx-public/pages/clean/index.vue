<template>
	<view>
		<!-- <view class="search-bar">
			<u-search placeholder="请输入手机号码" :showAction="false" v-model="searchParam.name"
				@change="onSearchKeywordInput"></u-search>
			<view class="uni-btn-v">
				<picker mode="date" :value="startDate" :start="pickStartDate" :end="pickEndDate"
					@change="bindDateChange">
					<view class="uni-input">{{startDate}}</view>
				</picker>
				<uo-select placeholder="请选择包厢" v-model="searchParam.roomId" :options="roomList" valueField="id"
					@change="selectRoom">
					<o
				</uo-select>
			</view>
		</view> -->
		<view class="card-list">
			<view class="card" v-for="(room, index) in orderList" :key="room.id">
				<view class="card__content">
					<!-- <uo-image src="https://mbdp01.bdstatic.com/static/landing-pc/img/logo_top.79fdb8c2.png"></uo-image> -->
					<view class="card__content__right">
						<view class="card__content__head">
							<navigator class="card-title" :url="'room?id='+room.id+'&name='+room.name">
								<view class="card-title">{{room.storeName}}</view>
							</navigator>
							<!-- <view class="card-title">{{room.status}}</view> -->
						</view>
						<view class="card__content__body">
							<view>包间: {{room.roomName}}</view>
							<view>开始时间: {{room.startTime}} </view>
							<view>结束时间: {{room.endTime}} </view>
							<view>保洁员: {{room.cleaner}} </view>
						</view>
						<view class="card__content__footer">
							<view>打扫时长: {{room.duration}} 分钟</view>
						</view>
					</view>
				</view>

			</view>
		</view>

	</view>
</template>

<script>
	export default {
		data() {
			return {
				searchParam: {
					name: '',
					startTime: '',
					pageSize: 20,
					pageNum: 1
				},
				orderList: [],
				startDate: '请选择日期',
			}
		},
		onLoad(option) {
			// this.getRoomList();
			this.refresh();

		},
		computed: {
			pickStartDate() {
				return this.getDate('start');
			},
			pickEndDate() {
				return this.getDate('end');
			}
		},

		onPullDownRefresh() {
			this.refresh()
		},
		onReachBottom() {
			this.refresh(true)
		},
		methods: {
			onSearchKeywordInput() {
				this.$u.debounce(this.refresh, 400)
			},
			refresh(nextPage) {
				// debugger
				console.log(this.searchParam)
				if (nextPage) {
					this.searchParam.pageNum++
				} else {
					this.searchParam.pageNum = 1
				}
				this.searchParam.roomId = this.roomId;
				this.searchParam.storeId = this.$store.state.currentStore.id;
				this.$api.getCleanRecordList(this.searchParam).then(res => {
					if (nextPage) {
						this.orderList.push(...res.rows)
					} else {
						this.orderList = res.rows
					}
					uni.stopPullDownRefresh()
				})
			},
			getRoomList() {
				var param = {};
				param.storeId = this.$store.state.currentStore.id; //是否默认一个系统门店
				this.$api.getRoomList(param).then(res => {
					this.roomList = res.rows;
				});
			},
			selectRoom(event) {
				console.log(event.id);
				this.roomId = event.id;
				this.refresh()
			},
			getDate(type) {
				const date = new Date();
				let year = date.getFullYear();
				let month = date.getMonth() + 1;
				let day = date.getDate();

				if (type === 'start') {
					year = year - 60;
				} else if (type === 'end') {
					year = year + 2;
				}
				month = month > 9 ? month : '0' + month;
				day = day > 9 ? day : '0' + day;
				return `${year}-${month}-${day}`;
			},
			bindDateChange: function(e) {
				this.startDate = this.searchParam.startTime = e.detail.value
				this.refresh();
			},
		}
	}
</script>

<style lang="scss">
	.is-hover {
		color: rgba(255, 255, 255, 0.6);
		background-color: #179b16;
		border-color: #179b16;
	}
</style>
