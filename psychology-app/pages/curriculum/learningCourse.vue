<template>
    <view class="learning-course">
        <view class="video-box">
        <video id="myVideo" ref="video1" :src="videoUrl"
		 loop=true
		 :initial-time="startTime"
		 enable-danmu danmu-btn controls
		style="width: 100%;"></video>
    </view>
    <view class="course-title"></view>
    <view class="course-num">
        <span>课程列表(共12节)</span>
        <span>全部</span>
    </view>
    <view class="list-box">
        <view class="list-item" v-for="courseItem in courseList">
            <view class="status-box">
                <span class="try-txt">
                <span v-if="courseItem.enabled">试听</span>
                <image v-if="!courseItem.enabled" src="/static/curriculum/product/lock.png"></image>
                </span>
                <span class="time-txt">{{ courseItem.time }}</span>
            </view>
            <view class="course-title txt-overflow txt-overflow-line2">{{courseItem.title }}</view>
        </view>
    </view>
    
    </view>
</template>
<script>
export default {
    data:()=>{
        return {
           courseList:[{title:'课程1课程1课程1课程1课程1课程1课程1课程1课程1课程1课程1课程1课程1课程1课程1课程1课程1课程1课程1',time:'10:34',enabled:true,playing:true},{title:'课程2',time:'07:34',enabled:false,playing:true}] 
        }
    }
}
</script>
<style></style>