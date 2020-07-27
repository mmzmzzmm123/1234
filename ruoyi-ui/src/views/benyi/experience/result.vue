 <template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="幼儿姓名" prop="yexm">
        <el-input v-model="queryParams.yexm" placeholder="请输入幼儿姓名" clearable size="small" />
      </el-form-item>
      <el-form-item label="联系方式" prop="lxfs">
        <el-input v-model="queryParams.lxfs" placeholder="请输入联系方式" clearable size="small" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="mini" @click="handleQuery">查询</el-button>
        <el-button type="primary" size="mini" @click="cancel">返回</el-button>
      </el-form-item>
    </el-form>
    <div v-show="hide">
      <h2>入班半日体验申请表</h2>
      <el-row :gutter="15">
        <el-form ref="form" :model="form" label-width="80px">
          <el-col :span="12">
            <el-form-item label="家长姓名" prop="jzxm">
              <el-input v-model="form.jzxm" placeholder="请输入家长姓名" :disabled="hide" />
              <el-input v-model="form.schoolid" v-if="false" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系方式" prop="lxfs">
              <el-input v-model="form.lxfs" placeholder="请输入联系方式" :disabled="hide" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="幼儿姓名" prop="yexm">
              <el-input v-model="form.yexm" placeholder="请输入幼儿姓名" :disabled="hide" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="幼儿出生日期" prop="csrq">
              <el-date-picker
                clearable
                size="small"
                style="width: 200px"
                v-model="form.csrq"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="选择幼儿出生日期"
                :disabled="hide"
              ></el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="拟入园时间" prop="nrysj">
              <el-date-picker
                clearable
                size="small"
                style="width: 200px"
                v-model="form.nrysj"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="选择拟入园时间"
                :disabled="hide"
              ></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-collapse v-model="activeNames">
              <el-collapse-item title="园长你好" name="1">
                <div>
                  为了更好地了解贵园，我申请来贵园进行入班半日体验。我预约
                  <el-form-item prop="sqtysj">
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
                  </el-form-item>来园入班体验。请园长为我安排与我孩子年龄段相适应的班级。届时，我将与我的孩子准时入班进行半日体验。
                </div>
                <div>请园长批准我的申请为盼。谢谢！</div>
              </el-collapse-item>
            </el-collapse>
          </el-col>
          <el-col :span="24" v-show="ishf">
            <el-collapse v-model="activeNames">
              <el-collapse-item title="园长答复" name="2">{{hfrn}}</el-collapse-item>
            </el-collapse>
          </el-col>
          <el-col :span="24" v-show="ishf">
            <el-collapse v-model="activeNames">
              <el-collapse-item title="园长指示" name="3">{{yzzs}}</el-collapse-item>
            </el-collapse>
          </el-col>
        </el-form>
      </el-row>
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
      ishf: false,
      hfrn: "",
      yzzs: "",
      activeNames: ["1", "2", "3"],
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
            this.ishf = true;
            this.hfrn = response.data.hfrn;
            this.yzzs = response.data.yzzs;
          } else {
            this.ishf = false;
          }
        } else {
          this.hide = false;
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
      this.$router.go(-1);
    },
  },
};
</script>