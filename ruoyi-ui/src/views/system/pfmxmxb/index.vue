<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="统一社会信用代码" prop="tyshxydm">
        <el-input
          v-model="queryParams.tyshxydm"
          placeholder="请输入统一社会信用代码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="申报个体工商户姓名" prop="sbgtgshxm">
        <el-input
          v-model="queryParams.sbgtgshxm"
          placeholder="请输入申报个体工商户姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="证件类型" prop="zjlx">
        <el-input
          v-model="queryParams.zjlx"
          placeholder="请输入证件类型"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="证件号码" prop="zjhm">
        <el-input
          v-model="queryParams.zjhm"
          placeholder="请输入证件号码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="营业店名" prop="yydm">
        <el-input
          v-model="queryParams.yydm"
          placeholder="请输入营业店名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="经营场所_地址" prop="jycsDz">
        <el-input
          v-model="queryParams.jycsDz"
          placeholder="请输入经营场所_地址"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="注册日期" prop="zcrq">
        <el-input
          v-model="queryParams.zcrq"
          placeholder="请输入注册日期"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="经营年限" prop="jynx">
        <el-input
          v-model="queryParams.jynx"
          placeholder="请输入经营年限"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="不动产权证号" prop="bdcqzh">
        <el-input
          v-model="queryParams.bdcqzh"
          placeholder="请输入不动产权证号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="不动产共有方式" prop="bdcgyfs">
        <el-input
          v-model="queryParams.bdcgyfs"
          placeholder="请输入不动产共有方式"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="不动产坐落" prop="bdczl">
        <el-input
          v-model="queryParams.bdczl"
          placeholder="请输入不动产坐落"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="不动产产权状态" prop="bdccqzt">
        <el-input
          v-model="queryParams.bdccqzt"
          placeholder="请输入不动产产权状态"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="不动产建筑面积" prop="bdcjzmj">
        <el-input
          v-model="queryParams.bdcjzmj"
          placeholder="请输入不动产建筑面积"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="不动产用途" prop="bdcyt">
        <el-input
          v-model="queryParams.bdcyt"
          placeholder="请输入不动产用途"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="最高学历" prop="zgxl">
        <el-input
          v-model="queryParams.zgxl"
          placeholder="请输入最高学历"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="婚姻状况" prop="hyzk">
        <el-input
          v-model="queryParams.hyzk"
          placeholder="请输入婚姻状况"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="自然人欠税税种" prop="zrrqssz">
        <el-input
          v-model="queryParams.zrrqssz"
          placeholder="请输入自然人欠税税种"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="自然人欠税统计开始日期" prop="zrrqstjksrq">
        <el-input
          v-model="queryParams.zrrqstjksrq"
          placeholder="请输入自然人欠税统计开始日期"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="自然人欠税统计截止日期" prop="zrrqstjjzrq">
        <el-input
          v-model="queryParams.zrrqstjjzrq"
          placeholder="请输入自然人欠税统计截止日期"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="自然人欠税余额(元)" prop="zrrqsye">
        <el-input
          v-model="queryParams.zrrqsye"
          placeholder="请输入自然人欠税余额(元)"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="自然人是否补缴" prop="zrrsfbj">
        <el-input
          v-model="queryParams.zrrsfbj"
          placeholder="请输入自然人是否补缴"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="法人欠税税种" prop="frqssz">
        <el-input
          v-model="queryParams.frqssz"
          placeholder="请输入法人欠税税种"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="法人欠税统计开始日期" prop="frqstjksrq">
        <el-input
          v-model="queryParams.frqstjksrq"
          placeholder="请输入法人欠税统计开始日期"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="法人欠税统计截止日期" prop="frqstjjzrq">
        <el-input
          v-model="queryParams.frqstjjzrq"
          placeholder="请输入法人欠税统计截止日期"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="法人当前欠税金额(元)" prop="frdqqsje">
        <el-input
          v-model="queryParams.frdqqsje"
          placeholder="请输入法人当前欠税金额(元)"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="法人是否补缴" prop="frsfbj">
        <el-input
          v-model="queryParams.frsfbj"
          placeholder="请输入法人是否补缴"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="激励措施内容" prop="jlcsnr">
        <el-input
          v-model="queryParams.jlcsnr"
          placeholder="请输入激励措施内容"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="激励名单名称" prop="jlmdmc">
        <el-input
          v-model="queryParams.jlmdmc"
          placeholder="请输入激励名单名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="享受扶持政策依据" prop="xsfczcyj">
        <el-input
          v-model="queryParams.xsfczcyj"
          placeholder="请输入享受扶持政策依据"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="享受扶持政策内容" prop="xsfczcnr">
        <el-input
          v-model="queryParams.xsfczcnr"
          placeholder="请输入享受扶持政策内容"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="享受扶持政策的数额" prop="xsfczcdse">
        <el-input
          v-model="queryParams.xsfczcdse"
          placeholder="请输入享受扶持政策的数额"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="享受扶持政策的部门" prop="xsfczcdbm">
        <el-input
          v-model="queryParams.xsfczcdbm"
          placeholder="请输入享受扶持政策的部门"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="实施扶持政策日期" prop="ssfczcrq">
        <el-input
          v-model="queryParams.ssfczcrq"
          placeholder="请输入实施扶持政策日期"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:pfmxmxb:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:pfmxmxb:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:pfmxmxb:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
		  :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['system:pfmxmxb:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="pfmxmxbList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="统一社会信用代码" align="center" prop="tyshxydm" />
      <el-table-column label="申报个体工商户姓名" align="center" prop="sbgtgshxm" />
      <el-table-column label="证件类型" align="center" prop="zjlx" />
      <el-table-column label="证件号码" align="center" prop="zjhm" />
      <el-table-column label="营业店名" align="center" prop="yydm" />
      <el-table-column label="经营场所_地址" align="center" prop="jycsDz" />
      <el-table-column label="注册日期" align="center" prop="zcrq" />
      <el-table-column label="经营年限" align="center" prop="jynx" />
      <el-table-column label="不动产权证号" align="center" prop="bdcqzh" />
      <el-table-column label="不动产共有方式" align="center" prop="bdcgyfs" />
      <el-table-column label="不动产坐落" align="center" prop="bdczl" />
      <el-table-column label="不动产产权状态" align="center" prop="bdccqzt" />
      <el-table-column label="不动产建筑面积" align="center" prop="bdcjzmj" />
      <el-table-column label="不动产用途" align="center" prop="bdcyt" />
      <el-table-column label="最高学历" align="center" prop="zgxl" />
      <el-table-column label="婚姻状况" align="center" prop="hyzk" />
      <el-table-column label="自然人欠税税种" align="center" prop="zrrqssz" />
      <el-table-column label="自然人欠税统计开始日期" align="center" prop="zrrqstjksrq" />
      <el-table-column label="自然人欠税统计截止日期" align="center" prop="zrrqstjjzrq" />
      <el-table-column label="自然人欠税余额(元)" align="center" prop="zrrqsye" />
      <el-table-column label="自然人是否补缴" align="center" prop="zrrsfbj" />
      <el-table-column label="法人欠税税种" align="center" prop="frqssz" />
      <el-table-column label="法人欠税统计开始日期" align="center" prop="frqstjksrq" />
      <el-table-column label="法人欠税统计截止日期" align="center" prop="frqstjjzrq" />
      <el-table-column label="法人当前欠税金额(元)" align="center" prop="frdqqsje" />
      <el-table-column label="法人是否补缴" align="center" prop="frsfbj" />
      <el-table-column label="激励措施内容" align="center" prop="jlcsnr" />
      <el-table-column label="激励名单名称" align="center" prop="jlmdmc" />
      <el-table-column label="享受扶持政策依据" align="center" prop="xsfczcyj" />
      <el-table-column label="享受扶持政策内容" align="center" prop="xsfczcnr" />
      <el-table-column label="享受扶持政策的数额" align="center" prop="xsfczcdse" />
      <el-table-column label="享受扶持政策的部门" align="center" prop="xsfczcdbm" />
      <el-table-column label="实施扶持政策日期" align="center" prop="ssfczcrq" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:pfmxmxb:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:pfmxmxb:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改个体工商户信贷_标准库_评分模型明细对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="统一社会信用代码" prop="tyshxydm">
          <el-input v-model="form.tyshxydm" placeholder="请输入统一社会信用代码" />
        </el-form-item>
        <el-form-item label="申报个体工商户姓名" prop="sbgtgshxm">
          <el-input v-model="form.sbgtgshxm" placeholder="请输入申报个体工商户姓名" />
        </el-form-item>
        <el-form-item label="证件类型" prop="zjlx">
          <el-input v-model="form.zjlx" placeholder="请输入证件类型" />
        </el-form-item>
        <el-form-item label="证件号码" prop="zjhm">
          <el-input v-model="form.zjhm" placeholder="请输入证件号码" />
        </el-form-item>
        <el-form-item label="营业店名" prop="yydm">
          <el-input v-model="form.yydm" placeholder="请输入营业店名" />
        </el-form-item>
        <el-form-item label="经营场所_地址" prop="jycsDz">
          <el-input v-model="form.jycsDz" placeholder="请输入经营场所_地址" />
        </el-form-item>
        <el-form-item label="注册日期" prop="zcrq">
          <el-input v-model="form.zcrq" placeholder="请输入注册日期" />
        </el-form-item>
        <el-form-item label="经营年限" prop="jynx">
          <el-input v-model="form.jynx" placeholder="请输入经营年限" />
        </el-form-item>
        <el-form-item label="不动产权证号" prop="bdcqzh">
          <el-input v-model="form.bdcqzh" placeholder="请输入不动产权证号" />
        </el-form-item>
        <el-form-item label="不动产共有方式" prop="bdcgyfs">
          <el-input v-model="form.bdcgyfs" placeholder="请输入不动产共有方式" />
        </el-form-item>
        <el-form-item label="不动产坐落" prop="bdczl">
          <el-input v-model="form.bdczl" placeholder="请输入不动产坐落" />
        </el-form-item>
        <el-form-item label="不动产产权状态" prop="bdccqzt">
          <el-input v-model="form.bdccqzt" placeholder="请输入不动产产权状态" />
        </el-form-item>
        <el-form-item label="不动产建筑面积" prop="bdcjzmj">
          <el-input v-model="form.bdcjzmj" placeholder="请输入不动产建筑面积" />
        </el-form-item>
        <el-form-item label="不动产用途" prop="bdcyt">
          <el-input v-model="form.bdcyt" placeholder="请输入不动产用途" />
        </el-form-item>
        <el-form-item label="最高学历" prop="zgxl">
          <el-input v-model="form.zgxl" placeholder="请输入最高学历" />
        </el-form-item>
        <el-form-item label="婚姻状况" prop="hyzk">
          <el-input v-model="form.hyzk" placeholder="请输入婚姻状况" />
        </el-form-item>
        <el-form-item label="自然人欠税税种" prop="zrrqssz">
          <el-input v-model="form.zrrqssz" placeholder="请输入自然人欠税税种" />
        </el-form-item>
        <el-form-item label="自然人欠税统计开始日期" prop="zrrqstjksrq">
          <el-input v-model="form.zrrqstjksrq" placeholder="请输入自然人欠税统计开始日期" />
        </el-form-item>
        <el-form-item label="自然人欠税统计截止日期" prop="zrrqstjjzrq">
          <el-input v-model="form.zrrqstjjzrq" placeholder="请输入自然人欠税统计截止日期" />
        </el-form-item>
        <el-form-item label="自然人欠税余额(元)" prop="zrrqsye">
          <el-input v-model="form.zrrqsye" placeholder="请输入自然人欠税余额(元)" />
        </el-form-item>
        <el-form-item label="自然人是否补缴" prop="zrrsfbj">
          <el-input v-model="form.zrrsfbj" placeholder="请输入自然人是否补缴" />
        </el-form-item>
        <el-form-item label="法人欠税税种" prop="frqssz">
          <el-input v-model="form.frqssz" placeholder="请输入法人欠税税种" />
        </el-form-item>
        <el-form-item label="法人欠税统计开始日期" prop="frqstjksrq">
          <el-input v-model="form.frqstjksrq" placeholder="请输入法人欠税统计开始日期" />
        </el-form-item>
        <el-form-item label="法人欠税统计截止日期" prop="frqstjjzrq">
          <el-input v-model="form.frqstjjzrq" placeholder="请输入法人欠税统计截止日期" />
        </el-form-item>
        <el-form-item label="法人当前欠税金额(元)" prop="frdqqsje">
          <el-input v-model="form.frdqqsje" placeholder="请输入法人当前欠税金额(元)" />
        </el-form-item>
        <el-form-item label="法人是否补缴" prop="frsfbj">
          <el-input v-model="form.frsfbj" placeholder="请输入法人是否补缴" />
        </el-form-item>
        <el-form-item label="激励措施内容" prop="jlcsnr">
          <el-input v-model="form.jlcsnr" placeholder="请输入激励措施内容" />
        </el-form-item>
        <el-form-item label="激励名单名称" prop="jlmdmc">
          <el-input v-model="form.jlmdmc" placeholder="请输入激励名单名称" />
        </el-form-item>
        <el-form-item label="享受扶持政策依据" prop="xsfczcyj">
          <el-input v-model="form.xsfczcyj" placeholder="请输入享受扶持政策依据" />
        </el-form-item>
        <el-form-item label="享受扶持政策内容" prop="xsfczcnr">
          <el-input v-model="form.xsfczcnr" placeholder="请输入享受扶持政策内容" />
        </el-form-item>
        <el-form-item label="享受扶持政策的数额" prop="xsfczcdse">
          <el-input v-model="form.xsfczcdse" placeholder="请输入享受扶持政策的数额" />
        </el-form-item>
        <el-form-item label="享受扶持政策的部门" prop="xsfczcdbm">
          <el-input v-model="form.xsfczcdbm" placeholder="请输入享受扶持政策的部门" />
        </el-form-item>
        <el-form-item label="实施扶持政策日期" prop="ssfczcrq">
          <el-input v-model="form.ssfczcrq" placeholder="请输入实施扶持政策日期" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listPfmxmxb, getPfmxmxb, delPfmxmxb, addPfmxmxb, updatePfmxmxb, exportPfmxmxb } from "@/api/system/pfmxmxb";

export default {
  name: "Pfmxmxb",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 导出遮罩层
      exportLoading: false,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 个体工商户信贷_标准库_评分模型明细表格数据
      pfmxmxbList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        tyshxydm: null,
        sbgtgshxm: null,
        zjlx: null,
        zjhm: null,
        yydm: null,
        jycsDz: null,
        zcrq: null,
        jynx: null,
        bdcqzh: null,
        bdcgyfs: null,
        bdczl: null,
        bdccqzt: null,
        bdcjzmj: null,
        bdcyt: null,
        zgxl: null,
        hyzk: null,
        zrrqssz: null,
        zrrqstjksrq: null,
        zrrqstjjzrq: null,
        zrrqsye: null,
        zrrsfbj: null,
        frqssz: null,
        frqstjksrq: null,
        frqstjjzrq: null,
        frdqqsje: null,
        frsfbj: null,
        jlcsnr: null,
        jlmdmc: null,
        xsfczcyj: null,
        xsfczcnr: null,
        xsfczcdse: null,
        xsfczcdbm: null,
        ssfczcrq: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询个体工商户信贷_标准库_评分模型明细列表 */
    getList() {
      this.loading = true;
      listPfmxmxb(this.queryParams).then(response => {
        this.pfmxmxbList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        tyshxydm: null,
        sbgtgshxm: null,
        zjlx: null,
        zjhm: null,
        yydm: null,
        jycsDz: null,
        zcrq: null,
        jynx: null,
        bdcqzh: null,
        bdcgyfs: null,
        bdczl: null,
        bdccqzt: null,
        bdcjzmj: null,
        bdcyt: null,
        zgxl: null,
        hyzk: null,
        zrrqssz: null,
        zrrqstjksrq: null,
        zrrqstjjzrq: null,
        zrrqsye: null,
        zrrsfbj: null,
        frqssz: null,
        frqstjksrq: null,
        frqstjjzrq: null,
        frdqqsje: null,
        frsfbj: null,
        jlcsnr: null,
        jlmdmc: null,
        xsfczcyj: null,
        xsfczcnr: null,
        xsfczcdse: null,
        xsfczcdbm: null,
        ssfczcrq: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.tyshxydm)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加个体工商户信贷_标准库_评分模型明细";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const tyshxydm = row.tyshxydm || this.ids
      getPfmxmxb(tyshxydm).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改个体工商户信贷_标准库_评分模型明细";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.tyshxydm != null) {
            updatePfmxmxb(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPfmxmxb(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const tyshxydms = row.tyshxydm || this.ids;
      this.$confirm('是否确认删除个体工商户信贷_标准库_评分模型明细编号为"' + tyshxydms + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delPfmxmxb(tyshxydms);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有个体工商户信贷_标准库_评分模型明细数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          this.exportLoading = true;
          return exportPfmxmxb(queryParams);
        }).then(response => {
          this.download(response.msg);
          this.exportLoading = false;
        }).catch(() => {});
    }
  }
};
</script>
