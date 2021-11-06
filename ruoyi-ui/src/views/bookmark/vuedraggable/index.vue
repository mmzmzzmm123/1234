<template>

    <el-container>
      <el-aside width="350px" style="margin-bottom: 0px">
        <h3>自定义布局</h3>

        <el-row>
          <el-col :span="24">
            <draggable
              class="dragArea list-group"
              :list="list1"
              :group="{ name: 'people', pull: 'clone', put: false }"
              @change="log"
            >
              <div
                class="list-group-item"
                v-for="element in list1"
                :key="element.id"
              >
                {{ element.name }}
              </div>
            </draggable>
          </el-col>
        </el-row>
        <br/>
        <br/>
        --------------


        <el-row>
          <el-col :span="24">
            <draggable
              class="dragArea list-group"
              :list="list1"
              group="t1"
              @change="log"
            >
              <div
                class="list-group-item"
                v-for="element in list1"
                :key="element.id"
              >
                {{ element.name }}
              </div>
            </draggable>
          </el-col>
        </el-row>


      </el-aside>

      <el-container>
        <el-header>实时预览</el-header>

<!--        <el-main>-->
<!--          <el-row>-->
<!--            <el-col :span="24">-->
<!--              <div class="grid-content bg-purple-dark">search</div>-->
<!--            </el-col>-->
<!--          </el-row>-->
<!--            <draggable-->
<!--              class="dragArea list-group "-->
<!--              :list="list2"-->
<!--              group="people"-->
<!--              @change="log"-->
<!--            >-->
<!--              <el-row>-->

<!--              <el-col-->
<!--                :span="24"-->
<!--                class="borderSolid"-->
<!--                v-for="(element, idx) in list2"-->
<!--                :key="element.id"-->
<!--                :style="'height:'+ element.height +';width:'+element.width "-->
<!--              >-->
<!--                {{ element.name }}-->
<!--                <p>{{ element.data.id }}</p>-->
<!--                <p>{{ element.data.name }}</p>-->
<!--                <p>{{ element.data.url }}</p>-->
<!--                <p>{{ element.data.description }}</p>-->
<!--                <i class="el-icon-delete" @click="removeAt2(idx)"></i>-->
<!--              </el-col>-->
<!--              </el-row>-->
<!--            </draggable>-->



<!--        </el-main>-->














        <el-main>
          <el-row>
            <el-col :span="24"
              ><div class="grid-content bg-purple-dark">search</div></el-col
            >
          </el-row>

          <el-row>
            <el-col :span="2"> - </el-col>
<!--            <el-col :span="8">-->
<!--              <div class="grid-content bg-purple borderColor">-->
<!--                <draggable-->
<!--                  class="dragArea list-group "-->
<!--                  :list="list2"-->
<!--                  group="people"-->
<!--                  @change="log"-->
<!--                >-->
<!--                  <div-->
<!--                    class="borderSolid"-->
<!--                    v-for="(element, idx) in list2"-->
<!--                    :key="element.id"-->
<!--                    :style="'height:'+ element.height +';width:'+element.width "-->
<!--                  >-->
<!--                    {{ element.name }}-->
<!--                    <p>{{ element.data.id }}</p>-->
<!--                    <p>{{ element.data.name }}</p>-->
<!--                    <p>{{ element.data.url }}</p>-->
<!--                    <p>{{ element.data.description }}</p>-->
<!--                    <i class="el-icon-delete" @click="removeAt2(idx)"></i>-->
<!--                  </div>-->
<!--                </draggable>-->
<!--              </div>-->
<!--            </el-col>-->

            <el-col :span="11">
              <div class="grid-content bg-purple-light borderColor">
                <draggable
                  class="dragArea list-group"
                  :list="list3"
                  group="people"
                  @change="log"
                >
                  <div
                    class="borderSolid"
                    v-for="(element, idx) in list3"
                    :key="element.id"
                    :style="'height:'+ element.height "
                  >
                    {{ element.name }}
                    <p>{{ element.data.id }}</p>
                    <p>{{ element.data.name }}</p>
                    <p>{{ element.data.url }}</p>
                    <p>{{ element.data.description }}</p>
                    <i class="el-icon-delete" @click="removeAt3(idx)"></i>
                  </div>
                </draggable>
              </div>
            </el-col>
            <el-col :span="2"> - </el-col>

            <el-col :span="8">
              <div class="grid-content bg-purple-light borderColor">
                <draggable
                  class="dragArea list-group"
                  :list="list4"
                  group="people"
                  @change="log"
                >


                  <el-card class="box-card"  v-for="(element, idx) in list4"
                    :key="element.id" :style="'height:'+ element.height ">
                    <div slot="header" class="clearfix">
                      <span>卡片名称</span>


                      <draggable  class="dragArea list-group"
                                  :list="list8"
                                  group="people"
                      >
                        <a class="box-card"  v-for="(element, idx) in list4"
                                 :key="element.id" :style="'height:'+ element.height ">

                        </a>

                      </draggable>
                     <i class="el-icon-delete" style="float: right; padding: 3px 0" @click="removeAt4(idx)"></i>


                    </div>
                    <div v-for="o in 4" :key="o" class="text item">
                      {{ "列表内容 " + o }}
                    </div>
                  </el-card>

                </draggable>

              </div>
            </el-col>
            <el-col :span="1"> - </el-col>
          </el-row>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import draggable from "vuedraggable";
export default {
  name: "clone",
  display: "Clone",
  order: 2,
  components: {
    draggable,
  },
  data() {
    return {
      list1: [
        {
          name: "热点新闻",
          id: 1,
          height:"800px",
          width:"400px",
          data: {
            id: 1,
            title: "百度书签",
            url: "https://www.baidu.com",
            description: "这是个百度的书签"
          },
        },
        {
          name: "最近收藏",
          id: 2,
          height:"300px",
          width:"400px",
          data: {
            id: 1,
            title: "百度书签",
            url: "https://www.baidu.com",
            description: "这是个百度的书签",
          },
        },
        {
          name: "常用导航",
          id: 3,
          height:"300px",
          width:"400px",
          data: {
            id: 1,
            title: "百度书签",
            url: "https://www.baidu.com",
            description: "这是个百度的书签",
          },
        },
        {
          name: "热门推荐",
          id: 4,
          height:"300px",
          width:"400px",
          data: {
            id: 1,
            title: "百度书签",
            url: "https://www.baidu.com",
            description: "这是个百度的书签",
          },
        },
      ],
      list2: [],
      list3: [],
      list4: [],
      list8: [],
      list9: [],
    };
  },
  methods: {
    log: function (evt) {
      window.console.log(evt);
    },
    removeAt2(idx) {
      this.list2.splice(idx, 1);
    },
    removeAt3(idx) {
      this.list3.splice(idx, 1);
    },
    removeAt4(idx) {
      this.list4.splice(idx, 1);
    },
  },
};
</script>
<style scoped>
.el-header,
.el-footer {
  background-color: #b3c0d1;
  color: #333;
  text-align: center;
  line-height: 60px;
}

.el-aside {
  background-color: #d3dce6;
  color: #333;
  text-align: center;
  height: 700px;
}

.el-main {
  background-color: #e9eef3;
  color: #333;
  text-align: center;
  height: 500px;
}

.el-container:nth-child(5) .el-aside,
.el-container:nth-child(6) .el-aside {
  line-height: 260px;
}

.el-container:nth-child(7) .el-aside {
  line-height: 320px;
}
.borderColor {
  border: 1px solid #404040;
  height: 600px;
}
.borderSolid {
  border: 1px solid #404040;

}
</style>
