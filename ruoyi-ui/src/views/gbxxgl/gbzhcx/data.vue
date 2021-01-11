<template>
  <div class="app-container">
    <h1 class="title">干部个人信息</h1>
    <div class="table-con">
      <table class="table">
        <tr>
          <td>
            <div class="flex cell">
              <span>姓名：</span>
              <span>{{ form.name }}</span>
            </div>
          </td>
          <td>
            <div class="flex cell">
              <span>性别：</span>
              <span>{{ filter(form.xb, xbOptions) }}</span>
            </div>
          </td>
          <td>
            <div class="flex cell">
              <span>民族：</span>
              <span>{{ filter(form.mz, mzOptions) }}</span>
            </div>
          </td>
          <td>
            <div class="flex cell">
              <span>第一学历：</span>
              <span>{{ filter(form.dyxl, dyxlOptions) }}</span>
            </div>
          </td>
          <td>
            <div class="flex cell">
              <span>学历：</span>
              <span>{{ filter(form.xl, xlOptions) }}</span>
            </div>
          </td>
          <td>
            <div class="flex cell">
              <span>学位：</span>
              <span>{{ filter(form.xw, xwOptions) }}</span>
            </div>
          </td>
        </tr>
        <tr>
          <td>
            <div class="flex cell">
              <span>职称：</span>
              <span>{{ filter(form.zc, zcOptions) }}</span>
            </div>
          </td>
          <td colspan="2">
            <div class="flex cell">
              <span>籍贯：</span>
              <span
                >{{ diglogForm.province }}{{ diglogForm.city
                }}{{ diglogForm.area }}</span
              >
            </div>
          </td>
          <td colspan="2">
            <div class="flex cell">
              <span>出生地：</span>
              <span
                >{{ diglogForm1.province }}{{ diglogForm1.city
                }}{{ diglogForm1.area }}</span
              >
            </div>
          </td>
          <td>
            <div class="flex cell">
              <span>手机号码：</span>
              <span>{{ form.phone }}</span>
            </div>
          </td>
        </tr>
        <tr>
          <td>
            <div class="flex cell">
              <span>单位名称：</span>
              <span>{{ filterDy(form.deptId, deptOptions) }}</span>
            </div>
          </td>
          <td colspan="2">
            <div class="flex cell">
              <span>单位地址：</span>
              <span>{{ form.dwdz }}</span>
            </div>
          </td>
          <td colspan="2">
            <div class="flex cell">
              <span>家庭住址：</span>
              <span>{{ form.jtzz }}</span>
            </div>
          </td>
          <td>
            <div class="flex cell">
              <span>毕业院校：</span>
              <span>{{ form.byyx }}</span>
            </div>
          </td>
        </tr>
        <tr>
          <td>
            <div class="flex cell">
              <span>教育类型：</span>
              <span>{{ filter(form.jylx, jylxOptions) }}</span>
            </div>
          </td>
          <td colspan="2">
            <div class="flex cell">
              <span>身份证号：</span>
              <span>{{ form.sfzh }}</span>
            </div>
          </td>
          <td>
            <div class="flex cell">
              <span>出生日期：</span>
              <span>{{ form.csrq }}</span>
            </div>
          </td>
          <td>
            <div class="flex cell">
              <span>婚姻状况：</span>
              <span>{{ filter(form.hyzk, hyzkOptions) }}</span>
            </div>
          </td>
          <td>
            <div class="flex cell">
              <span>政治面貌：</span>
              <span>{{ filter(form.zzmm, zzmmOptions) }}</span>
            </div>
          </td>
        </tr>
        <tr>
          <td>
            <div class="flex cell">
              <span>入党时间：</span>
              <span>{{ form.rdsj }}</span>
            </div>
          </td>
          <td>
            <div class="flex cell">
              <span>任教学科：</span>
              <span>{{ filter(form.rjxk, rjxkOptions) }}</span>
            </div>
          </td>
          <td>
            <div class="flex cell">
              <span>健康状况：</span>
              <span>{{ filter(form.jkzk, jkzkOptions) }}</span>
            </div>
          </td>
          <td>
            <div class="flex cell">
              <span>能否开车：</span>
              <span>{{ filter(form.nfkc, nfkcOptions) }}</span>
            </div>
          </td>
          <td>
            <div class="flex cell">
              <span>驾照持有情况：</span>
              <span>{{ filter(form.jzcyqk, jzcyqkOptions) }}</span>
            </div>
          </td>
          <td>
            <div class="flex cell">
              <span>现任行政职务：</span>
              <span>{{ filter(form.xrxzzw, xrxzzwOptions) }}</span>
            </div>
          </td>
        </tr>
        <tr>
          <td>
            <div class="flex cell">
              <span>现任党内职务：</span>
              <span>{{ filter(form.xrdnzw, xrdnzwOptions) }}</span>
            </div>
          </td>
          <td>
            <div class="flex cell">
              <span>现任群团职务：</span>
              <span>{{ filter(form.xrqtzw, xrqtzwOptions) }}</span>
            </div>
          </td>
          <td>
            <div class="flex cell">
              <span>任现职务年月：</span>
              <span>{{ form.rxzwny }}</span>
            </div>
          </td>
          <td>
            <div class="flex cell">
              <span>现任校长职级：</span>
              <span>{{ filter(form.xrxzzj, xrxzzjOptions) }}</span>
            </div>
          </td>
          <td>
            <div class="flex cell">
              <span>校长职级确定年月：</span>
              <span>{{ form.xzzjqdny }}</span>
            </div>
          </td>
          <td>
            <div class="flex cell">
              <span>是否后备干部：</span>
              <span>{{ filter(form.sfhbgb, sfhbgbOptions) }}</span>
            </div>
          </td>
        </tr>
        <tr>
          <td>
            <div class="flex cell">
              <span>后备干部确定年月：</span>
              <span>{{ form.hbgbqdny }}</span>
            </div>
          </td>
          <td>
            <div class="flex cell">
              <span>参加工作年月：</span>
              <span>{{ form.cjgzny }}</span>
            </div>
          </td>
          <td colspan="4">
            <div class="flex cell">
              <span>办公室电话：</span>
              <span>{{ form.bgsdh }}</span>
            </div>
          </td>
        </tr>
      </table>
    </div>
    <!-- END -->
    <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
      <el-tab-pane label="任免情况" name="first">
        <el-table v-loading="loading" border :data="gbxrzwList">
          <el-table-column
            label="姓名"
            align="center"
            prop="tsbzGbjbqk.name"
          />
          <el-table-column label="任职单位" align="center" prop="dwmc" />
          <el-table-column label="起始年月" align="center" prop="rzny">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.rzny, "{y}-{m}") }}</span>
            </template>
          </el-table-column>
          <el-table-column label="终止年月" align="center" prop="zzny">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.zzny, "{y}-{m}") }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="行政职务"
            align="center"
            prop="xzzw"
            :formatter="xzzwFormat"
          />
          <el-table-column
            label="行政职务级别"
            align="center"
            prop="xxzwjb"
            :formatter="xxzwjbFormat"
          />
          <el-table-column
            label="党内职务"
            align="center"
            prop="dnzw"
            :formatter="dnzwFormat"
          />
          <el-table-column
            label="党内职务属性"
            align="center"
            prop="dnzwsx"
            :formatter="dnzwsxFormat"
          />
          <el-table-column
            label="群团职务"
            align="center"
            prop="qtzw"
            :formatter="qtzwFormat"
          />
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="社会职务" name="second">
        <el-table v-loading="loading" border :data="gbshzwList">
          <el-table-column
            label="姓名"
            align="center"
            prop="tsbzGbjbqk.name"
          />
          <el-table-column label="任职起始年月" align="center" prop="rzqsny">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.rzqsny, "{y}-{m}") }}</span>
            </template>
          </el-table-column>
          <el-table-column label="任职终止年月" align="center" prop="rzzzny">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.rzzzny, "{y}-{m}") }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="届别"
            align="center"
            prop="jb"
            :formatter="jbFormat"
          />
          <el-table-column
            label="职务级别"
            align="center"
            prop="jibie"
            :formatter="jibieFormat"
          />
          <el-table-column label="职务名称" align="center" prop="name" />
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="工作经历" name="third">
        <el-table v-loading="loading" border :data="gbgzjlList">
          <el-table-column
            label="姓名"
            align="center"
            prop="tsbzGbjbqk.name"
          />
          <el-table-column label="工作单位" align="center" prop="gzdwmc" />
          <el-table-column label="起始年月" align="center" prop="qsny">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.qsny, "{y}-{m}") }}</span>
            </template>
          </el-table-column>
          <el-table-column label="终止年月" align="center" prop="zzny">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.zzny, "{y}-{m}") }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="执行职务"
            align="center"
            prop="zzzw"
            :formatter="zzzwFormat"
          />
          <el-table-column
            label="党内职务"
            align="center"
            prop="dnzw"
            :formatter="dnzwFormat"
          />
          <el-table-column
            label="群团职务"
            align="center"
            prop="qtzw"
            :formatter="qtzwFormat"
          />
          <el-table-column
            label="任教学科"
            align="center"
            prop="rjxk"
            :formatter="rjxkFormat"
          />
          <el-table-column
            label="工作岗位"
            align="center"
            prop="gzgw"
            :formatter="gzgwFormat"
          />
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="教育经历" name="fourth">
        <el-table v-loading="loading" border :data="gbxxjlList">
          <el-table-column
            label="姓名"
            align="center"
            prop="tsbzGbjbqk.name"
          />
          <el-table-column
            label="教育类型"
            align="center"
            prop="jylx"
            :formatter="jylxFormat"
          />
          <el-table-column
            label="学历"
            align="center"
            prop="xl"
            :formatter="xlFormat"
          />
          <el-table-column
            label="学位"
            align="center"
            prop="xw"
            :formatter="xwFormat"
          />
          <el-table-column label="起始年月" align="center" prop="qsny">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.qsny, "{y}-{m}-{d}") }}</span>
            </template>
          </el-table-column>
          <el-table-column label="终止年月" align="center" prop="zzny">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.zzny, "{y}-{m}-{d}") }}</span>
            </template>
          </el-table-column>
          <el-table-column label="毕业院校" align="center" prop="byyx" />
          <el-table-column
            label="专业"
            align="center"
            prop="zy"
            :formatter="zyFormat"
          />
          <el-table-column
            label="职务"
            align="center"
            prop="zw"
            :formatter="zwFormat"
          />
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="培训经历" name="pxjl">
        <el-table v-loading="loading" border :data="gbpxjlList">
          <el-table-column
            label="姓名"
            align="center"
            prop="tsbzGbjbqk.name"
          />
          <el-table-column label="培训班名称" align="center" prop="pxbmc" />
          <el-table-column
            label="培训地点"
            align="center"
            prop="pxdd"
            :formatter="pxddFormat"
          />
          <el-table-column
            label="培训级别"
            align="center"
            prop="pxjb"
            :formatter="pxjbFormat"
          />
          <el-table-column
            label="培训类别"
            align="center"
            prop="pxlb"
            :formatter="pxlbFormat"
          />
          <el-table-column
            label="职务"
            align="center"
            prop="zw"
            :formatter="zwFormat"
          />
          <el-table-column label="起始年月" align="center" prop="qsny">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.qsny, "{y}-{m}") }}</span>
            </template>
          </el-table-column>
          <el-table-column label="终止年月" align="center" prop="zzny">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.zzny, "{y}-{m}") }}</span>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="挂职经历" name="gzjl">
        <el-table v-loading="loading" border :data="guazhijlList">
          <el-table-column
            label="姓名"
            align="center"
            prop="tsbzGbjbqk.name"
          />
          <el-table-column label="挂职单位名称" align="center" prop="gzdw" />
          <el-table-column label="起始年月" align="center" prop="qsny">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.qsny, "{y}-{m}") }}</span>
            </template>
          </el-table-column>
          <el-table-column label="终止年月" align="center" prop="zzny">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.zzny, "{y}-{m}") }}</span>
            </template>
          </el-table-column>
          <el-table-column label="带教导师" align="center" prop="djds" />
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="荣誉奖励" name="ryjl">
        <el-table v-loading="loading" border :data="gbryjlList">
          <el-table-column
            label="姓名"
            align="center"
            prop="tsbzGbjbqk.name"
          />
          <el-table-column label="获奖日期" align="center" prop="hjrq">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.hjrq, "{y}-{m}-{d}") }}</span>
            </template>
          </el-table-column>
          <el-table-column label="荣誉名称" align="center" prop="rymc" />
          <el-table-column
            label="荣誉级别"
            align="center"
            prop="ryjb"
            :formatter="ryjbFormat"
          />
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="科研课题" name="kykt">
        <el-table v-loading="loading" border :data="gbkyktList">
          <el-table-column
            label="姓名"
            align="center"
            prop="tsbzGbjbqk.name"
          />
          <el-table-column
            label="研究领域"
            align="center"
            prop="yjly"
            :formatter="yjlyFormat"
          />
          <el-table-column label="课题名称" align="center" prop="lxktmc" />
          <el-table-column
            label="课题级别"
            align="center"
            prop="lxktjb"
            :formatter="lxktjbFormat"
          />
          <el-table-column
            label="课题类别"
            align="center"
            prop="lxktlb"
            :formatter="lxktlbFormat"
          />
          <el-table-column label="立项日期" align="center" prop="lxrq">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.lxrq, "{y}-{m}-{d}") }}</span>
            </template>
          </el-table-column>
          <el-table-column label="结题日期" align="center" prop="jtrq">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.jtrq, "{y}-{m}-{d}") }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="鉴定结果"
            align="center"
            prop="jdjg"
            :formatter="jdjgFormat"
          />
          <el-table-column
            label="承担角色"
            align="center"
            prop="cdjs"
            :formatter="cdjsFormat"
          /> </el-table
      ></el-tab-pane>
      <el-tab-pane label="出国情况" name="cgqk">
        <el-table v-loading="loading" border :data="gbcrjqkList">
          <el-table-column
            label="姓名"
            align="center"
            prop="tsbzGbjbqk.name"
          />
          <el-table-column label="起始日期" align="center" prop="qsrq">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.qsrq, "{y}-{m}-{d}") }}</span>
            </template>
          </el-table-column>
          <el-table-column label="终止日期" align="center" prop="zzrq">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.zzrq, "{y}-{m}-{d}") }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="性质"
            align="center"
            prop="xz"
            :formatter="xzFormat"
          />
          <el-table-column label="目的地" align="center" prop="mdd" />
          <el-table-column label="事由" align="center" prop="sy" />
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="出国证件" name="cgzj">
        <el-table v-loading="loading" border :data="gbcrjzjqkList">
          <el-table-column
            label="姓名"
            align="center"
            prop="tsbzGbjbqk.name"
          />
          <el-table-column
            label="证件类型"
            align="center"
            prop="zjlx"
            :formatter="zjlxFormat"
          />
          <el-table-column label="证件号码" align="center" prop="zjhm" />
          <el-table-column label="有效期起始日期" align="center" prop="yxqqsrq">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.yxqqsrq, "{y}-{m}-{d}") }}</span>
            </template>
          </el-table-column>
          <el-table-column label="有效期终止日期" align="center" prop="yxqzzrq">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.yxqzzrq, "{y}-{m}-{d}") }}</span>
            </template>
          </el-table-column>
        </el-table></el-tab-pane
      >
      <el-tab-pane label="个人风采" name="grfc">
        <el-table v-loading="loading" border :data="gbgrfcList">
          <el-table-column
            label="姓名"
            align="center"
            prop="tsbzGbjbqk.name"
          />
          <el-table-column label="证件照名称" align="center" prop="zjzmc" />
          <el-table-column label="生活照名称" align="center" prop="shzmc" />
          <el-table-column label="工作照名称" align="center" prop="zgzmc" />
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="家庭成员" name="jtcy">
        <el-table v-loading="loading" border :data="gbjtcyList">
          <el-table-column
            label="姓名"
            align="center"
            prop="tsbzGbjbqk.name"
          />
          <el-table-column label="关系" align="center" prop="gx" />
          <el-table-column label="家属姓名" align="center" prop="xm" />
          <el-table-column label="出生年月" align="center" prop="csny">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.csny, "{y}-{m}-{d}") }}</span>
            </template>
          </el-table-column>
          <el-table-column label="工作单位" align="center" prop="gzdw" />
          <el-table-column label="职务" align="center" prop="zw" />
          <el-table-column
            label="政治面貌"
            align="center"
            prop="zzmm"
            :formatter="zzmmFormat"
          />
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="办学成果" name="bxcg">
        <el-table v-loading="loading" border :data="gbbxcgList">
          <el-table-column
            label="姓名"
            align="center"
            prop="tsbzGbjbqk.name"
          />
          <el-table-column label="成果名称" align="center" prop="cgmc" />
          <el-table-column label="成果年度" align="center" prop="cgnd">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.cgnd, "{y}") }}</span>
            </template>
          </el-table-column>
          <el-table-column label="成果认定单位" align="center" prop="cgrddw" />
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="年度考核" name="ndkh">
        <el-table v-loading="loading" border :data="gbndkhList">
          <el-table-column
            label="姓名"
            align="center"
            prop="tsbzGbjbqk.name"
          />
          <el-table-column label="考核学年" align="center" prop="xn" />
          <el-table-column
            label="考核结果"
            align="center"
            prop="khjg"
            :formatter="khjgFormat"
          />
          <el-table-column label="考核年度" align="center" prop="nd">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.nd, "{y}") }}</span>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="业务考核" name="ywkh">
        <el-table v-loading="loading" border :data="gbywkhList">
          <el-table-column
            label="姓名"
            align="center"
            prop="tsbzGbjbqk.name"
          />
          <el-table-column label="考核学年" align="center" prop="xn" />
          <el-table-column label="技术职务" align="center" prop="jszw" />
          <el-table-column label="任职时间" align="center" prop="rzsj">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.rzsj, "{y}-{m}-{d}") }}</span>
            </template>
          </el-table-column>
          <el-table-column label="周课时" align="center" prop="zks" />
          <el-table-column
            label="档次"
            align="center"
            prop="dc"
            :formatter="dcFormat"
          />
          <el-table-column label="同级比" align="center" prop="tjb" />
        </el-table>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import { listGbjbqk, getGbjbqk } from "@/api/gbxxgl/gbjbqk";
import { listDept, getDept } from "@/api/system/dept";
import { listGbxrzw } from "@/api/gbxxgl/gbxrzw";
import { listGbshzw } from "@/api/gbxxgl/gbshzw";
import { listGbgzjl } from "@/api/gbxxgl/gbgzjl";
import { listGbxxjl } from "@/api/gbxxgl/gbxxjl";
import { listGbpxjl } from "@/api/gbxxgl/gbpxjl";
import { listGuazhijl } from "@/api/gbxxgl/guazhijl";
import { listGbryjl } from "@/api/gbxxgl/gbryjl";
import { listGbkykt } from "@/api/gbxxgl/gbkykt";
import { listGbcrjqk } from "@/api/gbxxgl/gbcrjqk";
import { listGbcrjzjqk } from "@/api/gbxxgl/gbcrjzjqk";
import { listGbgrfc } from "@/api/gbxxgl/gbgrfc";
import { listGbjtcy } from "@/api/gbxxgl/gbjtcy";
import { listGbbxcg } from "@/api/gbxxgl/gbbxcg";
import { listGbndkh } from "@/api/gbxxgl/gbndkh";
import { listGbywkh } from "@/api/gbxxgl/gbywkh";

export default {
  name: "Gbjbqk",
  data() {
    return {
      activeName: "first",
      diglogForm: {
        province: null,
        city: null,
        area: null,
      },
      diglogForm1: {
        province: null,
        city: null,
        area: null,
      },
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: false,
      // 总条数
      total: 0,
      // 干部基本情况表格数据
      gbjbqkList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 性别字典
      xbOptions: [],
      // 现任行政职务字典
      xrxzzwOptions: [],
      // 现任党内职务字典
      xrdnzwOptions: [],
      // 现任群团职务字典
      xrqtzwOptions: [],
      // 现任校长职级字典
      xrxzzjOptions: [],
      // 是否后备干部字典
      sfhbgbOptions: [],
      // 民族字典
      mzOptions: [],
      // 学历字典
      xlOptions: [],
      // 第一学历字典
      dyxlOptions: [],
      // 学位字典
      xwOptions: [],
      // 教育类型字典
      jylxOptions: [],
      // 职称字典
      zcOptions: [],
      // 驾照持有情况字典
      jzcyqkOptions: [],
      // 能否开车字典
      nfkcOptions: [],
      // 婚姻状况字典
      hyzkOptions: [],
      // 政治面貌字典
      zzmmOptions: [],
      // 任教学科字典
      rjxkOptions: [],
      // 健康状况字典
      jkzkOptions: [],
      // 部门选项
      deptOptions: [],
      // 行政职务字典
      xzzwOptions: [],
      // 行政职务级别字典
      xxzwjbOptions: [],
      // 党内职务字典
      dnzwOptions: [],
      // 党内职务属性字典
      dnzwsxOptions: [],
      // 群团职务字典
      qtzwOptions: [],
      // 届别字典
      jbOptions: [],
      // 职务级别字典
      jibieOptions: [],
      // 执行职务字典
      zzzwOptions: [],
      // 工作岗位字典
      gzgwOptions: [],
      // 专业字典
      zyOptions: [],
      // 职务字典
      zwOptions: [],
      // 培训级别字典
      pxjbOptions: [],
      // 培训类别字典
      pxlbOptions: [],
      // 培训地点字典
      pxddOptions: [],
      // 字典荣誉级别
      ryjbOptions: [],
      // 研究领域
      yjlyOptions: [],
      // 立项课题级别
      lxktjbOptions: [],
      // 立项课题类别
      lxktlbOptions: [],
      // 鉴定结果
      jdjgOptions: [],
      // 承担角色
      cdjsOptions: [],
      // 性质字典
      xzOptions: [],
      // 证件类型字典
      zjlxOptions: [],
      // 考核结果字典
      khjgOptions: [],
      // 档次
      dcOptions: [],
      // 表单参数
      form: {},
      // 干部任职情况-现任职务表格数据
      gbxrzwList: [],
      // 干部任职情况-社会职务表格数据
      gbshzwList: [],
      // 干部工作经历表格数据
      gbgzjlList: [],
      // 干部教育经历表格数据
      gbxxjlList: [],
      // 干部培训经历表格数据
      gbpxjlList: [],
      // 干部挂职经历表格数据
      guazhijlList: [],
      // 荣誉奖励(干部管理)表格数据
      gbryjlList: [],
      // 科研课题(干部管理)表格数据
      gbkyktList: [],
      // 干部出入境情况表格数据
      gbcrjqkList: [],
      // 干部出入境证件情况表格数据
      gbcrjzjqkList: [],
      // 个人风采(干部管理-个人状况)表格数据
      gbgrfcList: [],
      // 家庭成员(干部管理-个人状况)表格数据
      gbjtcyList: [],
      // 办学成果(干部管理-评价与考核)表格数据
      gbbxcgList: [],
      // 年度考核(干部管理-评价与考核)表格数据
      gbndkhList: [],
      // 业务考核(干部管理-评价与考核)表格数据
      gbywkhList: [],
      queryParams_gb: {
        gbid: null,
        dqzt: "00",
      },
    };
  },
  computed: {
    filter() {
      return (val, options) =>
        options.find((ele) => ele.dictValue === val)
          ? options.find((ele) => ele.dictValue === val).dictLabel
          : "--";
    },
    filterDy() {
      return (val, options) =>
        options.find((ele) => ele.deptId === val)
          ? options.find((ele) => ele.deptId === val).deptName
          : "--";
    },
  },
  created() {
    const id = this.$route.params && this.$route.params.id;
    this.getData(id);
    this.getDeptList();
    this.queryParams_gb.gbid = id;
    this.getGbXrzwList();
    this.getGbShzwList();
    this.getGbGzjlList();
    this.getGbxxjlList();
    this.getGbpxjlList();
    this.getGbguazhijlList();
    this.getGbryjlList();
    this.getGbkyktList();
    this.getGbcrjqkList();
    this.getGbcrjzjqkList();
    this.getGbgrfcList();
    this.getGbjtcyList();
    this.getGbbxcgList();
    this.getGbndkhList();
    this.getGbywkhList();
    this.getDicts("sys_user_sex").then((response) => {
      this.xbOptions = response.data;
    });
    this.getDicts("sys_dm_xrxzzw").then((response) => {
      this.xrxzzwOptions = response.data;
    });
    this.getDicts("sys_dm_xrdnzw").then((response) => {
      this.xrdnzwOptions = response.data;
    });
    this.getDicts("sys_dm_xrqtzw").then((response) => {
      this.xrqtzwOptions = response.data;
    });
    this.getDicts("sys_dm_xrxzzj").then((response) => {
      this.xrxzzjOptions = response.data;
    });
    this.getDicts("sys_yes_no").then((response) => {
      this.sfhbgbOptions = response.data;
    });
    this.getDicts("sys_dm_mz").then((response) => {
      this.mzOptions = response.data;
    });
    this.getDicts("sys_dm_xl").then((response) => {
      this.xlOptions = response.data;
    });
    this.getDicts("sys_dm_xw").then((response) => {
      this.xwOptions = response.data;
    });
    this.getDicts("sys_dm_jylx").then((response) => {
      this.jylxOptions = response.data;
    });
    this.getDicts("sys_dm_gbzc").then((response) => {
      this.zcOptions = response.data;
    });
    this.getDicts("sys_dm_jzcyqk").then((response) => {
      this.jzcyqkOptions = response.data;
    });
    this.getDicts("sys_yes_no").then((response) => {
      this.nfkcOptions = response.data;
    });
    this.getDicts("sys_dm_hyzk").then((response) => {
      this.hyzkOptions = response.data;
    });
    this.getDicts("sys_dm_zzmm").then((response) => {
      this.zzmmOptions = response.data;
    });
    this.getDicts("sys_dm_rjxk").then((response) => {
      this.rjxkOptions = response.data;
    });
    this.getDicts("sys_dm_jkzk").then((response) => {
      this.jkzkOptions = response.data;
    });
    this.getDicts("sys_dm_dyxl").then((response) => {
      this.dyxlOptions = response.data;
    });
    ///////////////任免情况
    this.getDicts("sys_dm_xrxzzw").then((response) => {
      this.xzzwOptions = response.data;
    });
    this.getDicts("sys_dm_xzzwjb").then((response) => {
      this.xxzwjbOptions = response.data;
    });
    this.getDicts("sys_dm_xrdnzw").then((response) => {
      this.dnzwOptions = response.data;
    });
    this.getDicts("sys_dnzwsx").then((response) => {
      this.dnzwsxOptions = response.data;
    });
    this.getDicts("sys_dm_xrqtzw").then((response) => {
      this.qtzwOptions = response.data;
    });
    ///////////////社会职务
    this.getDicts("sys_dm_jiebie").then((response) => {
      this.jbOptions = response.data;
    });
    this.getDicts("sys_dm_jibie").then((response) => {
      this.jibieOptions = response.data;
    });
    ///////////////工作经历
    this.getDicts("sys_dm_xrxzzw").then((response) => {
      this.zzzwOptions = response.data;
    });
    this.getDicts("sys_dm_gzgw").then((response) => {
      this.gzgwOptions = response.data;
    });
    this.getDicts("sys_dm_zy").then((response) => {
      this.zyOptions = response.data;
    });
    this.getDicts("sys_dm_xrxzzw").then((response) => {
      this.zwOptions = response.data;
    });
    ///////////////////培训经历
    this.getDicts("sys_dm_pxjb").then((response) => {
      this.pxjbOptions = response.data;
    });
    this.getDicts("sys_dm_gbpxlb").then((response) => {
      this.pxlbOptions = response.data;
    });
    this.getDicts("sys_dm_pxdd").then((response) => {
      this.pxddOptions = response.data;
    });
    ///////////////////荣誉奖励
    this.getDicts("sys_gbryjb").then((response) => {
      this.ryjbOptions = response.data;
    });
    ///////////////////科研课题
    this.getDicts("sys_gbyjly").then((response) => {
      this.yjlyOptions = response.data;
    });
    this.getDicts("sys_gblxktjb").then((response) => {
      this.lxktjbOptions = response.data;
    });
    this.getDicts("sys_gblxktlb").then((response) => {
      this.lxktlbOptions = response.data;
    });
    this.getDicts("sys_gbjdjg").then((response) => {
      this.jdjgOptions = response.data;
    });
    this.getDicts("sys_gbcdjs").then((response) => {
      this.cdjsOptions = response.data;
    });
    ////////////////////出入境
    this.getDicts("sys_dm_gbcrjxz").then((response) => {
      this.xzOptions = response.data;
    });
    this.getDicts("sys_dm_gbzjlx").then((response) => {
      this.zjlxOptions = response.data;
    });
    //////////////////考核
    this.getDicts("sys_khjg").then((response) => {
      this.khjgOptions = response.data;
    });
    this.getDicts("sys_gbywkhdc").then((response) => {
      this.dcOptions = response.data;
    });
  },
  methods: {
    // 查询部门
    getDeptList() {
      listDept(null).then((response) => {
        this.deptOptions = response.data;
      });
    },
    // 字典翻译
    dcFormat(row, column) {
      return this.selectDictLabel(this.dcOptions, row.dc);
    },
    /** 查询业务考核(干部管理-评价与考核)列表 */
    getGbywkhList() {
      this.loading = true;
      listGbywkh(this.queryParams_gb).then((response) => {
        this.gbywkhList = response.rows;
        this.loading = false;
      });
    },
    // 字典翻译
    khjgFormat(row, column) {
      return this.selectDictLabel(this.khjgOptions, row.khjg);
    },
    /** 查询年度考核(干部管理-评价与考核)列表 */
    getGbndkhList() {
      this.loading = true;
      listGbndkh(this.queryParams_gb).then((response) => {
        this.gbndkhList = response.rows;
        this.loading = false;
      });
    },
    /** 查询办学成果(干部管理-评价与考核)列表 */
    getGbbxcgList() {
      this.loading = true;
      listGbbxcg(this.queryParams_gb).then((response) => {
        this.gbbxcgList = response.rows;
        this.loading = false;
      });
    },
    /** 查询家庭成员(干部管理-个人状况)列表 */
    getGbjtcyList() {
      this.loading = true;
      listGbjtcy(this.queryParams_gb).then((response) => {
        this.gbjtcyList = response.rows;
        this.loading = false;
      });
    },
    /** 查询个人风采(干部管理-个人状况)列表 */
    getGbgrfcList() {
      this.loading = true;
      listGbgrfc(this.queryParams_gb).then((response) => {
        this.gbgrfcList = response.rows;
        this.loading = false;
      });
    },
    // 证件类型字典翻译
    zjlxFormat(row, column) {
      return this.selectDictLabel(this.zjlxOptions, row.zjlx);
    },
    /** 查询干部出入境证件情况列表 */
    getGbcrjzjqkList() {
      this.loading = true;
      listGbcrjzjqk(this.queryParams_gb).then((response) => {
        this.gbcrjzjqkList = response.rows;
        this.loading = false;
      });
    },
    // 性质字典翻译
    xzFormat(row, column) {
      return this.selectDictLabel(this.xzOptions, row.xz);
    },
    /** 查询干部出入境情况列表 */
    getGbcrjqkList() {
      this.loading = true;
      listGbcrjqk(this.queryParams_gb).then((response) => {
        this.gbcrjqkList = response.rows;
        this.loading = false;
      });
    },
    // 字典翻译
    yjlyFormat(row, column) {
      return this.selectDictLabel(this.yjlyOptions, row.yjly);
    },
    // 字典翻译
    lxktjbFormat(row, column) {
      return this.selectDictLabel(this.lxktjbOptions, row.lxktjb);
    },
    // 字典翻译
    lxktlbFormat(row, column) {
      return this.selectDictLabel(this.lxktlbOptions, row.lxktlb);
    },
    // 字典翻译
    jdjgFormat(row, column) {
      return this.selectDictLabel(this.jdjgOptions, row.jdjg);
    },
    // 字典翻译
    cdjsFormat(row, column) {
      return this.selectDictLabel(this.cdjsOptions, row.cdjs);
    },
    /** 查询科研课题(干部管理)列表 */
    getGbkyktList() {
      this.loading = true;
      listGbkykt(this.queryParams_gb).then((response) => {
        this.gbkyktList = response.rows;
        this.loading = false;
      });
    },
    // 荣誉奖励字典翻译
    ryjbFormat(row, column) {
      return this.selectDictLabel(this.ryjbOptions, row.ryjb);
    },
    /** 查询荣誉奖励(干部管理)列表 */
    getGbryjlList() {
      this.loading = true;
      listGbryjl(this.queryParams_gb).then((response) => {
        this.gbryjlList = response.rows;
        this.loading = false;
      });
    },
    /** 查询干部挂职经历列表 */
    getGbguazhijlList() {
      this.loading = true;
      listGuazhijl(this.queryParams_gb).then((response) => {
        this.guazhijlList = response.rows;
        this.loading = false;
      });
    },
    // 培训级别字典翻译
    pxjbFormat(row, column) {
      return this.selectDictLabel(this.pxjbOptions, row.pxjb);
    },
    // 培训类别字典翻译
    pxlbFormat(row, column) {
      return this.selectDictLabel(this.pxlbOptions, row.pxlb);
    },
    // 培训地点字典翻译
    pxddFormat(row, column) {
      return this.selectDictLabel(this.pxddOptions, row.pxdd);
    },
    /** 查询干部培训经历列表 */
    getGbpxjlList() {
      this.loading = true;
      listGbpxjl(this.queryParams_gb).then((response) => {
        this.gbpxjlList = response.rows;
        this.loading = false;
      });
    },
    // 学位字典翻译
    zyFormat(row, column) {
      return this.selectDictLabel(this.zyOptions, row.zy);
    },
    // 职务字典翻译
    zwFormat(row, column) {
      return this.selectDictLabel(this.zwOptions, row.zw);
    },
    /** 查询干部教育经历列表 */
    getGbxxjlList() {
      this.loading = true;
      listGbxxjl(this.queryParams_gb).then((response) => {
        this.gbxxjlList = response.rows;
        this.loading = false;
      });
    },
    // 执行职务字典翻译
    zzzwFormat(row, column) {
      return this.selectDictLabel(this.zzzwOptions, row.zzzw);
    },
    // 工作岗位字典翻译
    gzgwFormat(row, column) {
      return this.selectDictLabel(this.gzgwOptions, row.gzgw);
    },
    /** 查询干部工作经历列表 */
    getGbGzjlList() {
      this.loading = true;
      listGbgzjl(this.queryParams_gb).then((response) => {
        this.gbgzjlList = response.rows;
        this.loading = false;
      });
    },
    // 届别字典翻译
    jbFormat(row, column) {
      return this.selectDictLabel(this.jbOptions, row.jb);
    },
    // 职务级别字典翻译
    jibieFormat(row, column) {
      return this.selectDictLabel(this.jibieOptions, row.jibie);
    },
    // 行政职务字典翻译
    xzzwFormat(row, column) {
      return this.selectDictLabel(this.xzzwOptions, row.xzzw);
    },
    // 行政职务级别字典翻译
    xxzwjbFormat(row, column) {
      return this.selectDictLabel(this.xxzwjbOptions, row.xxzwjb);
    },
    // 党内职务字典翻译
    dnzwFormat(row, column) {
      return this.selectDictLabel(this.dnzwOptions, row.dnzw);
    },
    // 党内职务属性字典翻译
    dnzwsxFormat(row, column) {
      return this.selectDictLabel(this.dnzwsxOptions, row.dnzwsx);
    },
    // 群团职务字典翻译
    qtzwFormat(row, column) {
      return this.selectDictLabel(this.qtzwOptions, row.qtzw);
    },
    /** 查询干部任职情况-现任职务列表 */
    getGbXrzwList() {
      this.loading = true;
      listGbxrzw(this.queryParams_gb).then((response) => {
        this.gbxrzwList = response.rows;
        this.loading = false;
      });
    },
    /** 查询干部任职情况-社会职务列表 */
    getGbShzwList() {
      this.loading = true;
      listGbshzw(this.queryParams_gb).then((response) => {
        this.gbshzwList = response.rows;
        this.loading = false;
      });
    },
    handleClick(tab, event) {
      // console.log(tab, event);
    },
    getData(id) {
      getGbjbqk(id).then((response) => {
        this.form = response.data;
        this.diglogForm.province = response.data.jg;
        this.diglogForm.city = response.data.jgCityname;
        this.diglogForm.area = response.data.jgAreaname;

        this.diglogForm1.province = response.data.csd;
        this.diglogForm1.city = response.data.csdCityname;
        this.diglogForm1.area = response.data.csdAreaname;
      });
    },
    // 学历字典翻译
    xlFormat(row, column) {
      return this.selectDictLabel(this.xlOptions, row.xl);
    },
    // 学位字典翻译
    xwFormat(row, column) {
      return this.selectDictLabel(this.xwOptions, row.xw);
    },
    // 教育类型字典翻译
    jylxFormat(row, column) {
      return this.selectDictLabel(this.jylxOptions, row.jylx);
    },

    // 政治面貌字典翻译
    zzmmFormat(row, column) {
      return this.selectDictLabel(this.zzmmOptions, row.zzmm);
    },
    // 任教学科字典翻译
    rjxkFormat(row, column) {
      return this.selectDictLabel(this.rjxkOptions, row.rjxk);
    },
  },
};
</script>
<style lang="scss" scoped>
.title {
  text-align: center;
  font-weight: bold;
  font-size: 18px;
  padding: 0 0 10px;
}
.el-select {
  width: 100%;
}
.my-date-picker {
  width: 100%;
}
.table-con {
  overflow: hidden;
  overflow-x: auto;
  margin-bottom: 40px;
}
.table {
  width: 100%;
  min-width: 1100px;
  border-collapse: collapse;
  border: none;
  tr > td {
    padding: 8px 0;
    border: solid #dfe6ec 1px;
    .cell {
      word-break: break-all;
      line-height: 23px;
      padding: 0 5px;
      font-size: 14px;
      span:nth-of-type(2) {
        color: #666;
      }
    }
  }
}
</style>
