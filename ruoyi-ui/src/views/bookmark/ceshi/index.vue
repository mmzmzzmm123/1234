
<template>
  <div class="background" style="background-color: #1f2d3d ">
<!--    <div class="left">-->
<!--      &lt;!&ndash;左侧div内容&ndash;&gt;-->
<!--    </div>-->
<!--    <div class="resize" title="收缩侧边栏">-->
<!--      ⋮-->
<!--    </div>-->
<!--    <div class="mid">-->
<!--      &lt;!&ndash;右侧div内容&ndash;&gt;-->
<!--      <svg-icon icon-class="gd" />-->
<!--      <br/>-->
<!--      <svg-icon icon-class="notebook_1" />-->
<!--      <br/>-->
<!--      <svg-icon icon-class="tool" style="width: 50px;height: 50px" />-->
<!--    </div>-->


    <div class="background">
      <img :src="imgSrc" width="100px" height="100px" alt="" />
    </div>

    <div class="content">
      <h2>标题</h2>
      <p>其实毛玻璃的模糊效果技术上比较简单，只是用到了 css 滤镜（filter）中的 blur 属性。但是要做一个好的毛玻璃效果，需要注意很多细节。其实毛玻璃的模糊效果技术上比较简单，只是用到了 css 滤镜（filter）中的 blur 属性。但是要做一个好的毛玻璃效果，需要注意很多细节。</p>
      <p>其实毛玻璃的模糊效果技术上比较简单，只是用到了 css 滤镜（filter）中的 blur 属性。但是要做一个好的毛玻璃效果，需要注意很多细节。其实毛玻璃的模糊效果技术上比较简单，只是用到了 css 滤镜（filter）中的 blur 属性。但是要做一个好的毛玻璃效果，需要注意很多细节。</p>
      <p>其实毛玻璃的模糊效果技术上比较简单，只是用到了 css 滤镜（filter）中的 blur 属性。但是要做一个好的毛玻璃效果，需要注意很多细节。其实毛玻璃的模糊效果技术上比较简单，只是用到了 css 滤镜（filter）中的 blur 属性。但是要做一个好的毛玻璃效果，需要注意很多细节。</p>
      <p>其实毛玻璃的模糊效果技术上比较简单，只是用到了 css 滤镜（filter）中的 blur 属性。但是要做一个好的毛玻璃效果，需要注意很多细节。其实毛玻璃的模糊效果技术上比较简单，只是用到了 css 滤镜（filter）中的 blur 属性。但是要做一个好的毛玻璃效果，需要注意很多细节。</p>
    </div>

  </div>
</template>

<script>

  export default {
    name: 'Dashboard',
    data() {
      return {
        note: {
          backgroundImage : "url('http://www.jq22.com/img/cs/500x300-9.png')",
          backgroundRepeat:'no-repeat',
          backgroundSize:'100% 100%',
        },
        imgSrc:'http://img.netbian.com/file/2020/0904/de2f77ed1090735b441ba5e4c2b460ca.jpg',
      }
      },
    mounted () {
      // this.dragControllerDiv();


    },
    methods: {




      dragControllerDiv: function () {
        var resize = document.getElementsByClassName('resize');
        var left = document.getElementsByClassName('left');
        var mid = document.getElementsByClassName('mid');
        var box = document.getElementsByClassName('box');
        for (let i = 0; i < resize.length; i++) {
          // 鼠标按下事件
          resize[i].onmousedown = function (e) {
            //颜色改变提醒
            resize[i].style.background = 'red';
            var startX = e.clientX;
            console.log("鼠标按下后："+e.clientX)
            resize[i].left = resize[i].offsetLeft;
            console.log("鼠标按下后："+resize[i].left)
            console.log("鼠标按下后："+resize[i].offsetLeft)
            // 鼠标拖动事件
            document.onmousemove = function (e) {
              var endX = e.clientX;
              var moveLen = resize[i].left + (endX - startX); // （endx-startx）=移动的距离。resize[i].left+移动的距离=左边区域最后的宽度
              var maxT = box[i].clientWidth - resize[i].offsetWidth; // 容器宽度 - 左边区域的宽度 = 右边区域的宽度

              if (moveLen < 32) moveLen = 32; // 左边区域的最小宽度为32px
              if (moveLen > maxT - 150) moveLen = maxT - 150; //右边区域最小宽度为150px

              resize[i].style.left = moveLen; // 设置左侧区域的宽度

              for (let j = 0; j < left.length; j++) {
                left[j].style.width = moveLen + 'px';
                mid[j].style.width = (box[i].clientWidth - moveLen - 10) + 'px';
              }
            };
            // 鼠标松开事件
            document.onmouseup = function (evt) {
              //颜色恢复
              resize[i].style.background = '#d6d6d6';
              document.onmousemove = null;
              document.onmouseup = null;
              resize[i].releaseCapture && resize[i].releaseCapture(); //当你不在需要继续获得鼠标消息就要应该调用ReleaseCapture()释放掉
            };
            resize[i].setCapture && resize[i].setCapture(); //该函数在属于当前线程的指定窗口里设置鼠标捕获
            return false;
          };
        }
      },

    }
  }
</script>

<style lang="scss" scoped>
  /* 拖拽相关样式 */
  /*包围div样式*/
  .box {
    width: 100%;
    height: 100%;
    margin: 1% 0px;
    overflow: hidden;
    box-shadow: -1px 9px 10px 3px rgba(0, 0, 0, 0.11);
  }
  /*左侧div样式*/
  .left {
    width: calc(32% - 10px);  /*左侧初始化宽度*/
    height: 100%;
    background: #FFFFFF;
    float: left;
  }
  /*拖拽区div样式*/
  .resize {
    cursor: col-resize;
    float: left;
    position: relative;
    top: 45%;
    background-color: #d6d6d6;
    border-radius: 5px;
    margin-top: -10px;
    width: 10px;
    height: 50px;
    background-size: cover;
    background-position: center;
    /*z-index: 99999;*/
    font-size: 32px;
    color: white;
  }
  /*拖拽区鼠标悬停样式*/
  .resize:hover {
    color: #444444;
  }
  /*右侧div'样式*/
  .mid {
    float: left;
    width: 68%;   /*右侧初始化宽度*/
    height: 100%;
    background: #fff;
    box-shadow: -1px 4px 5px 3px rgba(0, 0, 0, 0.11);
  }




  .background {
    position: fixed;
  }


  .content {
    width:800px;
    position:relative;
    margin:2rem auto;
    padding:1em;
    background:hsla(0,0%,100%,.25) border-box;
    overflow:hidden;
    border-radius:8px;
    box-shadow:0 0 0 1px hsla(0,0%,100%,.3) inset,0 .5em 1em rgba(0,0,0,0.6);
    text-shadow:0 1px 1px hsla(0,0%,100%,.3);
    color: #ffffff;
  }
  .content::before {
    content:'';
    position:absolute;
    top:0;
    right:0;
    bottom:0;
    left:0;
    margin:-30px;
    z-index:-1;
    -webkit-filter:blur(10px);
    filter:blur(10px);
  }



  .note {
    background: url("http://www.jq22.com/img/cs/500x300-9.png") no-repeat;
    background-position: center;
    height: 100%;
    width: 100%;
    background-size: cover;
    position: fixed;
  }

</style>
