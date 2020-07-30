<template>
  <div class="result-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="幼儿姓名" prop="yexm">
        <el-input v-model="queryParams.yexm" placeholder="请输入幼儿姓名" clearable size="small" />
      </el-form-item>
      <el-form-item label="联系方式" prop="lxfs">
        <el-input v-model="queryParams.lxfs" placeholder="请输入联系方式" clearable size="small" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="mini" @click="handleQuery">查询</el-button>
        <el-button size="mini" @click="cancel">重置</el-button>
      </el-form-item>
    </el-form>
    <div class="main" v-show="hide">
      <h2 class="title">体验申请查询结果</h2>
      <div class="result-info">
        <h3 class="title">园长答复</h3>
        <div class="info">
          <el-alert :title="hfrn" type="success" :closable="false" show-icon></el-alert>
        </div>
      </div>
      <div class="result-info">
        <h3 class="title">园长指示</h3>
        <div class="info">
          <el-alert :title="yzzs" :closable="false" type="info"></el-alert>
        </div>
      </div>
      <div class="result-info">
        <h3 class="title">体验内容</h3>
        <div class="info">
          <a target="_blank" :href="href_tynr">{{tynrcontent}}</a>
          <!-- <el-alert :title="tyrnid" :closable="false" type="info"></el-alert> -->
        </div>
      </div>
      <div class="result-form">
        <p class="form-title">提交信息核对</p>
        <el-form class="form" ref="form" :model="form" label-width="110px">
          <el-form-item label="家长姓名" prop="jzxm">
            <el-input v-model="form.jzxm" placeholder="请输入家长姓名" :disabled="hide" />
            <el-input v-model="form.schoolid" v-if="false" />
          </el-form-item>
          <el-form-item label="联系方式" prop="lxfs">
            <el-input v-model="form.lxfs" placeholder="请输入联系方式" :disabled="hide" />
          </el-form-item>
          <el-form-item label="幼儿姓名" prop="yexm">
            <el-input v-model="form.yexm" placeholder="请输入幼儿姓名" :disabled="hide" />
          </el-form-item>
          <el-form-item label="幼儿出生日期" prop="csrq">
            <el-date-picker
              clearable
              size="small"
              v-model="form.csrq"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="选择幼儿出生日期"
              :disabled="hide"
            ></el-date-picker>
          </el-form-item>
          <el-form-item label="拟入园时间" prop="nrysj">
            <el-date-picker
              clearable
              size="small"
              v-model="form.nrysj"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="选择拟入园时间"
              :disabled="hide"
            ></el-date-picker>
          </el-form-item>
          <div>
            <el-form-item label="预约体验时间" prop="sqtysj">
              <el-date-picker
                clearable
                size="small"
                v-model="form.sqtysj"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="选择申请体验时间"
                :disabled="hide"
              ></el-date-picker>
            </el-form-item>
            <el-form-item prop="swxw">
              <el-radio-group v-model="form.swxw" :disabled="hide">
                <el-radio label="1">上午</el-radio>
                <el-radio label="2">下午</el-radio>
              </el-radio-group>
            </el-form-item>
          </div>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
import { getExperience_query } from "@/api/benyi/experience";

export default {
  name: "result",
  data() {
    return {
      hide: false,
      ishf: true,
      hfrn: "",
      yzzs: "",
      tynrcontent: "",
      href_tynr: "",
      // 查询参数
      queryParams: {
        yexm: undefined,
        lxfs: undefined,
        schoolId: undefined,
      },
      // 表单参数
      form: {},
    };
  },
  created() {
    const sid = this.$route.params && this.$route.params.id;
    this.queryParams.schoolid = sid; 
  },
  methods: {
    // 字典翻译
    ynFormat(row, column) {
      return this.selectDictLabel(this.ynOptions, row.sfhf);
    },
    // 字典翻译
    swxwFormat(row, column) {
      return this.selectDictLabel(this.swxwOptions, row.swxw);
    },
    /** 查询入班体验申请列表 */
    getInfo() {
      getExperience_query(this.queryParams).then((response) => {
        //console.log(response);
        if (response.code == "200") {
          this.hide = true;
          this.form = response.data;
          if (response.data.sfhf == "Y") {
            this.ishf = false;
            this.hfrn = response.data.hfrn;
            this.yzzs = response.data.yzzs;
            this.href_tynr = "/experience/content/" + response.data.tynrid;
            this.tynrcontent = response.data.tynrcontent;
          } else {
            this.ishf = true;
          }
        }
      });
    },

    /** 搜索按钮操作 */
    handleQuery() {
      //console.log(this.queryParams.yexm);
      if (
        this.queryParams.yexm == undefined ||
        this.queryParams.yexm.trim() == ""
      ) {
        this.msgError("请输入幼儿姓名");
        return false;
      }
      if (
        this.queryParams.lxfs == undefined ||
        this.queryParams.lxfs.trim() == ""
      ) {
        this.msgError("请输入联系方式");
        return false;
      }
      this.getInfo();
    },
    // 返回按钮
    cancel() {
      this.queryParams = {
        yexm: undefined,
        lxfs: undefined,
        schoolId: undefined,
      };
    },
  },
};
</script>
<style lang="scss" scoped>
.result-container {
  padding: 60px 15px 0;
  .main {
    padding-top: 10px;
    border-top: 1px solid #eee;
    .title {
      text-align: center;
      font-size: 18px;
    }
    .result-form {
      .form-title {
        display: flex;
        align-items: center;
        text-align: left;
        text-align: left;
        font-size: 14px;
        font-weight: 700;
        &::before {
          content: "";
          width: 3px;
          height: 14px;
          background: #0a56ca;
          display: inline-block;
          margin: 1px 5px 0 0;
        }
      }
      .form {
        width: 500px;
        margin: 30px auto 0;
      }
    }
    .result-info {
      .title {
        display: flex;
        align-items: center;
        text-align: left;
        font-size: 14px;
        font-weight: 700;
        &::before {
          content: "";
          width: 3px;
          height: 14px;
          background: #0a56ca;
          display: inline-block;
          margin: 1px 5px 0 0;
        }
      }
      .info {
        padding: 0 20px;
      }
    }
  }
}
@media screen and (max-width: 768.89px) {
  .result-container {
    .main {
      .result-form {
        .form {
          width: 100%;
        }
      }
    }
  }
}
</style>