<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="企业名称" prop="companyName">
        <el-input
          v-model="queryParams.companyName"
          placeholder="请输入企业名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="统一社会信用代码" prop="companyCreditCode" label-width="125px" >
        <el-input
          v-model="queryParams.companyCreditCode"
          placeholder="请输入统一社会信用代码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="联系人姓名" prop="contactName" label-width="90px" >
        <el-input
          v-model="queryParams.contactName"
          placeholder="请输入联系人姓名"
          clearable
          size="small"
          style="width: 160px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="联系人手机" prop="contactPhone" label-width="90px" >
        <el-input
          v-model="queryParams.contactPhone"
          placeholder="请输入联系人手机"
          clearable
          size="small"
          style="width: 160px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="贷款对象类型" prop="loanObjectType" label-width="100px">
        <el-select v-model="queryParams.loanObjectType" placeholder="请选择贷款对象类型" clearable size="small" style="width: 180px">
          <el-option
            v-for="dict in loanObjectTypeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="客户经理" prop="customerManager">
        <el-input
          v-model="queryParams.customerManager"
          placeholder="请输入客户经理"
          clearable
          size="small"
          style="width: 150px"
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
          v-hasPermi="['system:loan:add']"
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
          v-hasPermi="['system:loan:edit']"
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
          v-hasPermi="['system:loan:remove']"
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
          v-hasPermi="['system:loan:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="loanList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="企业id" align="center" prop="companyId" width="58px"/>
      <el-table-column label="企业名称" align="center" prop="companyName" />
      <el-table-column label="统一社会信用代码" align="center" prop="companyCreditCode" />
      <el-table-column label="企业地址" align="center" prop="companyAddress" :show-overflow-tooltip="true" />
      <el-table-column label="意向银行" align="center" prop="loanBand" :formatter="loanBandFormat" />
      <el-table-column label="贷款金额(万)" align="center" prop="loanAmount" width="98px"/>
      <el-table-column label="是否首次贷款" align="center" prop="loanFirst" :formatter="loanFirstFormat" width="98px"/>
      <el-table-column label="贷款用途" align="center" prop="loanPurpose" width="78px" :show-overflow-tooltip="true"/>
      <el-table-column label="联系人姓名" align="center" prop="contactName" width="88px"/>
      <el-table-column label="联系人手机" align="center" prop="contactPhone" width="110px"/>
<!--      <el-table-column label="银行网点" align="center" prop="bankBranch" />-->
<!--      <el-table-column label="贷款对象类型" align="center" prop="loanObjectType" :formatter="loanObjectTypeFormat" width="98px" />-->
<!--      <el-table-column label="客户经理" align="center" prop="customerManager" />-->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="90px">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-document"
            align="center"
            @click="handleReport(scope.row)"
            v-hasPermi="['system:loan:report']"
          >查看报告</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-document"
            align="center"
            @click="handleSimpleReport(scope.row)"
            v-hasPermi="['system:loan:report']"
          >查看报告2</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            align="center"

            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:loan:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            align="center"

            @click="handleDelete(scope.row)"
            v-hasPermi="['system:loan:remove']"
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

    <!-- 添加或修改企业贷款信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="780px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-form-item label="闽政通用户ID" prop="mztUserId">
          <el-input v-model="form.mztUserId" placeholder="请输入闽政通用户ID" />
        </el-form-item>
        <el-form-item label="企业名称" prop="companyName">
          <el-input v-model="form.companyName" placeholder="请输入企业名称" />
        </el-form-item>
        <el-form-item label="统一社会信用代码" prop="companyCreditCode">
          <el-input v-model="form.companyCreditCode" placeholder="请输入统一社会信用代码" />
        </el-form-item>
        <el-form-item label="省份" prop="companyProvince">
          <el-input v-model="form.companyProvince" placeholder="请输入省份" />
        </el-form-item>
        <el-form-item label="企业地址" prop="companyAddress">
          <el-input v-model="form.companyAddress" placeholder="请输入企业地址" />
        </el-form-item>
        <el-form-item label="市" prop="companyCity">
          <el-input v-model="form.companyCity" placeholder="请输入市" />
        </el-form-item>
        <el-form-item label="区或县" prop="companyArea">
          <el-input v-model="form.companyArea" placeholder="请输入区或县" />
        </el-form-item>
        <el-form-item label="企业划型" prop="companyType">
          <el-input v-model="form.companyType" placeholder="请输入企业划型" />
        </el-form-item>
        <el-form-item label="所在行业" prop="companyIndustry">
          <el-input v-model="form.companyIndustry" placeholder="请输入所在行业" />
        </el-form-item>
        <el-form-item label="主营业务" prop="companyBusiness">
          <el-input v-model="form.companyBusiness" placeholder="请输入主营业务" type="textarea" autosize/>
        </el-form-item>
        <el-form-item label="意向银行">
          <el-checkbox-group v-model="form.loanBand">
            <el-checkbox
              v-for="dict in loanBandOptions"
              :key="dict.dictValue"
              :label="dict.dictValue">
              {{dict.dictLabel}}
            </el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item label="贷款金额(万)" prop="loanAmount">
          <el-input v-model="form.loanAmount" placeholder="请输入贷款金额" />
        </el-form-item>
        <el-form-item label="是否首次贷款" prop="loanFirst">
          <el-select v-model="form.loanFirst" placeholder="请选择是否首次贷款">
            <el-option
              v-for="dict in loanFirstOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="贷款用途" prop="loanPurpose">
          <el-input v-model="form.loanPurpose" placeholder="请输入贷款用途" />
        </el-form-item>
        <el-form-item label="备注说明" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注说明" />
        </el-form-item>
        <el-form-item label="联系人姓名" prop="contactName">
          <el-input v-model="form.contactName" placeholder="请输入联系人姓名" />
        </el-form-item>
        <el-form-item label="联系人手机" prop="contactPhone">
          <el-input v-model="form.contactPhone" placeholder="请输入联系人手机" />
        </el-form-item>
        <el-form-item label="银行网点" prop="bankBranch">
          <el-input v-model="form.bankBranch" placeholder="请输入银行网点" />
        </el-form-item>
        <el-form-item label="贷款对象类型" prop="loanObjectType">
          <el-select v-model="form.loanObjectType" placeholder="请选择贷款对象类型">
            <el-option
              v-for="dict in loanObjectTypeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="客户经理" prop="customerManager">
          <el-input v-model="form.customerManager" placeholder="请输入客户经理" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 征信报告对话框 -->
    <el-dialog :title="titleReport" :visible.sync="openReport" width="700px" append-to-body >
      <el-collapse v-model="activeNames" @change="handleChange">
          <el-collapse-item title="评分模型结果" name="1" >
            <div v-if="formReport.ztkZhdPfmxjgb == null">暂无数据</div>
            <el-form v-else ref="formReport" :model="formReport" :rules="rules" style="margin-left: 30px">
              <el-row>
                <el-col :span="12">
                  <el-form-item label="统一社会信用代码" prop="tyshxydm" >
                    {{formReport.ztkZhdPfmxjgb.tyshxydm}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="申报个体工商户姓名" prop="sbgtgshxm">
                    {{formReport.ztkZhdPfmxjgb.sbgtgshxm}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="证件类型" prop="zjlx">
                    {{formReport.ztkZhdPfmxjgb.zjlx}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="证件号码" prop="zjhm">
                    {{formReport.ztkZhdPfmxjgb.zjhm}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="营业店名" prop="yydm">
                    {{formReport.ztkZhdPfmxjgb.yydm}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="经营场所地址" prop="jycsDz">
                    {{formReport.ztkZhdPfmxjgb.jycsDz}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="注册日期" prop="zcrq">
                    {{formReport.ztkZhdPfmxjgb.zcrq}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="经营年限评分" prop="jynx">
                    {{formReport.ztkZhdPfmxjgb.jynx}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="申报人固定资产情况评分" prop="sbrgdzcqkpf">
                    {{formReport.ztkZhdPfmxjgb.sbrgdzcqkpf}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="申报人最高学历评分" prop="sbrzgxlpf">
                    {{formReport.ztkZhdPfmxjgb.sbrzgxlpf}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="申报人婚姻状况评分" prop="sbrhyzkpf">
                    {{formReport.ztkZhdPfmxjgb.sbrhyzkpf}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="申报人欠税情况评分" prop="sbrqsqkpf">
                    {{formReport.ztkZhdPfmxjgb.sbrqsqkpf}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="个体工商户欠税情况评分" prop="gtgshqsqkpf">
                    {{formReport.ztkZhdPfmxjgb.gtgshqsqkpf}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="个体工商户激励情况评分" prop="gtgshjlqkpf">
                    {{formReport.ztkZhdPfmxjgb.gtgshjlqkpf}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="个体工商户享受扶持情况评分" prop="gtgshxsfcqkpf">
                    {{formReport.ztkZhdPfmxjgb.gtgshxsfcqkpf}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="总评分" prop="zpf">
                    {{formReport.ztkZhdPfmxjgb.zpf}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="风险等级" prop="fxdj">
                    {{formReport.ztkZhdPfmxjgb.fxdj}}
                  </el-form-item>
                </el-col>

              </el-row>
            </el-form>
          </el-collapse-item>
          <el-collapse-item title="准入监管结果" name="2">
            <div v-if="formReport.ztkZhdZrjgjgb == null">暂无数据</div>
            <el-form v-else :model="queryParams" ref="formReport" :inline="false" v-show="showSearch" style="margin-left: 30px" >
              <el-row>
                <el-col :span="12">
                  <el-form-item label="统一社会信用代码" prop="tyshxydm">
                    {{formReport.ztkZhdZrjgjgb.tyshxydm}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="申报个体工商户姓名" prop="sbgtgshxm">
                    {{formReport.ztkZhdZrjgjgb.sbgtgshxm}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="证件类型" prop="zjlx">
                    {{formReport.ztkZhdZrjgjgb.zjlx}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="证件号码" prop="zjhm">
                    {{formReport.ztkZhdZrjgjgb.zjhm}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="经营年限大于1年" prop="jynx">
                    <dict-tag :options="loanYesOrNoOptions" :value="formReport.ztkZhdZrjgjgb.jynx.toString()"/>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="准入年龄18至60男55女" prop="zrnl">
                    <dict-tag :options="loanYesOrNoOptions" :value="formReport.ztkZhdZrjgjgb.zrnl.toString()"/>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="非异地贷款泉州市内" prop="fyddk">
                    <dict-tag :options="loanYesOrNoOptions" :value="formReport.ztkZhdZrjgjgb.fyddk.toString()"/>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="营业执照状态非吊销停业清算注销" prop="regstate">
                    <dict-tag :options="loanYesOrNoOptions" :value="formReport.ztkZhdZrjgjgb.regstate.toString()"/>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="不存在重大税收违法案件" prop="zdsffwaj">
                    <dict-tag :options="loanYesOrNoOptions" :value="formReport.ztkZhdZrjgjgb.zdsffwaj.toString()"/>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="非失信被执行人" prop="sxbzxr">
                    <dict-tag :options="loanYesOrNoOptions" :value="formReport.ztkZhdZrjgjgb.sxbzxr.toString()"/>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="不存在失信黑名单" prop="sxhmd">
                    <dict-tag :options="loanYesOrNoOptions" :value="formReport.ztkZhdZrjgjgb.sxhmd.toString()"/>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="不存在国家下发工商异常名录" prop="gjxfgsycml">
                    <dict-tag :options="loanYesOrNoOptions" :value="formReport.ztkZhdZrjgjgb.gjxfgsycml.toString()"/>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="不存在不动产查封" prop="bdccf">
                    <dict-tag :options="loanYesOrNoOptions" :value="formReport.ztkZhdZrjgjgb.bdccf.toString()"/>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="是否准入" prop="sfzr">
                    <dict-tag :options="loanYesOrNoOptions" :value="formReport.ztkZhdZrjgjgb.sfzr.toString()"/>
                  </el-form-item>
                </el-col>
    <!--              <el-form-item>-->
    <!--                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>-->
    <!--                <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>-->
    <!--              </el-form-item>-->
              </el-row>
            </el-form>
          </el-collapse-item>
          <el-collapse-item title="评分模型明细" name="3">
            <div v-if="formReport.bzkZhdPfmxmxb == null">暂无数据</div>
            <el-form v-else ref="formReport" :model="formReport" :rules="rules" style="margin-left: 30px" >
              <el-row>
                <el-col>
                  <el-form-item label="统一社会信用代码" prop="tyshxydm">
                    {{formReport.bzkZhdPfmxmxb.tyshxydm}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="申报个体工商户姓名" prop="sbgtgshxm">
                    {{formReport.bzkZhdPfmxmxb.sbgtgshxm}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="证件类型" prop="zjlx">
                    {{formReport.bzkZhdPfmxmxb.zjlx}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="证件号码" prop="zjhm">
                    {{formReport.bzkZhdPfmxmxb.zjhm}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="营业店名" prop="yydm">
                    {{formReport.bzkZhdPfmxmxb.yydm}}
                  </el-form-item>
                </el-col>
                <el-col :span="24">
                  <el-form-item label="经营场所地址" prop="jycsDz">
                    {{formReport.bzkZhdPfmxmxb.jycsDz}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="注册日期" prop="zcrq">
                    {{formReport.bzkZhdPfmxmxb.zcrq}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="经营年限" prop="jynx">
                    {{formReport.bzkZhdPfmxmxb.jynx}}
                  </el-form-item>
                </el-col>
                <el-col :span="24" v-if="formReport.bzkZhdPfmxmxb.bdcqs.length>1">
                  <el-form-item label="不动产信息" />
                </el-col>
                <el-col :span="24" v-if="formReport.bzkZhdPfmxmxb.bdcqs.length>1">
                  <el-collapse-item :title="'不动产权'+(index+1)" :name="'3-'+index" style="margin:0 12px;" v-for="(bdcq,index) in formReport.bzkZhdPfmxmxb.bdcqs" :key="bdcq.bdcqzh">
                    <el-col :span="24">
                      <el-form-item label="不动产权证号" prop="bdcqzh">
                        {{bdcq.bdcqzh}}
                      </el-form-item>
                    </el-col>
                    <el-col :span="24">
                      <el-form-item label="不动产坐落" prop="bdczl">
                        {{bdcq.bdczl}}
                      </el-form-item>
                    </el-col>
                    <el-col :span="12">
                      <el-form-item label="不动产共有方式" prop="bdcgyfs">
                        {{bdcq.bdcgyfs}}
                      </el-form-item>
                    </el-col>
                    <el-col :span="12">
                      <el-form-item label="不动产产权状态" prop="bdccqzt">
                        {{bdcq.bdccqzt}}
                      </el-form-item>
                    </el-col>
                    <el-col :span="12">
                      <el-form-item label="不动产建筑面积" prop="bdcjzmj">
                        {{bdcq.bdcjzmj}}
                      </el-form-item>
                    </el-col>
                    <el-col :span="12">
                      <el-form-item label="不动产用途" prop="bdcyt">
                        {{bdcq.bdcyt}}
                      </el-form-item>
                    </el-col>
                  </el-collapse-item>
                </el-col>
                <template v-if="formReport.bzkZhdPfmxmxb.bdcqs.length<=1">
                   <el-col :span="24">
                      <el-form-item label="不动产权证号" prop="bdcqzh">
                        {{formReport.bzkZhdPfmxmxb.bdcqzh}}
                      </el-form-item>
                    </el-col>
                    <el-col :span="24">
                      <el-form-item label="不动产坐落" prop="bdczl">
                        {{formReport.bzkZhdPfmxmxb.bdczl}}
                      </el-form-item>
                    </el-col>
                    <el-col :span="12">
                      <el-form-item label="不动产共有方式" prop="bdcgyfs">
                        {{formReport.bzkZhdPfmxmxb.bdcgyfs}}
                      </el-form-item>
                    </el-col>
                    <el-col :span="12">
                      <el-form-item label="不动产产权状态" prop="bdccqzt">
                        {{formReport.bzkZhdPfmxmxb.bdccqzt}}
                      </el-form-item>
                    </el-col>
                    <el-col :span="12">
                      <el-form-item label="不动产建筑面积" prop="bdcjzmj">
                        {{formReport.bzkZhdPfmxmxb.bdcjzmj}}
                      </el-form-item>
                    </el-col>
                    <el-col :span="12">
                      <el-form-item label="不动产用途" prop="bdcyt">
                        {{formReport.bzkZhdPfmxmxb.bdcyt}}
                      </el-form-item>
                    </el-col>
                </template>
                <el-col :span="12">
                  <el-form-item label="最高学历" prop="zgxl">
                    {{formReport.bzkZhdPfmxmxb.zgxl}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="婚姻状况" prop="hyzk">
                    {{formReport.bzkZhdPfmxmxb.hyzk}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="自然人欠税税种" prop="zrrqssz">
                    {{formReport.bzkZhdPfmxmxb.zrrqssz}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="自然人欠税统计开始日期" prop="zrrqstjksrq">
                    {{formReport.bzkZhdPfmxmxb.zrrqstjksrq}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="自然人欠税统计截止日期" prop="zrrqstjjzrq">
                    {{formReport.bzkZhdPfmxmxb.zrrqstjjzrq}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="自然人欠税余额(元)" prop="zrrqsye">
                    {{formReport.bzkZhdPfmxmxb.zrrqsye}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="自然人是否补缴" prop="zrrsfbj">
                    {{formReport.bzkZhdPfmxmxb.zrrsfbj}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="法人欠税税种" prop="frqssz">
                    {{formReport.bzkZhdPfmxmxb.frqssz}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="法人欠税统计开始日期" prop="frqstjksrq">
                    {{formReport.bzkZhdPfmxmxb.frqstjksrq}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="法人欠税统计截止日期" prop="frqstjjzrq">
                    {{formReport.bzkZhdPfmxmxb.frqstjjzrq}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="法人当前欠税金额(元)" prop="frdqqsje">
                    {{formReport.bzkZhdPfmxmxb.frdqqsje}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="法人是否补缴" prop="frsfbj">
                    {{formReport.bzkZhdPfmxmxb.frsfbj}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="激励措施内容" prop="jlcsnr">
                    {{formReport.bzkZhdPfmxmxb.jlcsnr}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="激励名单名称" prop="jlmdmc">
                    {{formReport.bzkZhdPfmxmxb.jlmdmc}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="享受扶持政策依据" prop="xsfczcyj">
                    {{formReport.bzkZhdPfmxmxb.xsfczcyj}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="享受扶持政策内容" prop="xsfczcnr">
                    {{formReport.bzkZhdPfmxmxb.xsfczcnr}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="享受扶持政策的数额" prop="xsfczcdse">
                    {{formReport.bzkZhdPfmxmxb.xsfczcdse}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="享受扶持政策的部门" prop="xsfczcdbm">
                    {{formReport.bzkZhdPfmxmxb.xsfczcdbm}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="实施扶持政策日期" prop="ssfczcrq">
                    {{formReport.bzkZhdPfmxmxb.ssfczcrq}}
                  </el-form-item>
                </el-col>
              </el-row>

            </el-form>
          </el-collapse-item>
          <el-collapse-item title="准入监管结果明细" name="4">
            <div v-if="formReport.bzkZhdZrjgmxb == null">暂无数据</div>
            <el-form v-else ref="formReport" :model="formReport" :rules="rules" style="margin-left: 30px">
              <el-row >
                <el-col :span="12">
                  <el-form-item label="统一社会信用代码" prop="tyshxydm">
                    {{formReport.bzkZhdZrjgmxb.tyshxydm}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="申报个体工商户姓名" prop="sbgtgshxm">
                    {{formReport.bzkZhdZrjgmxb.sbgtgshxm}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="证件类型" prop="zjlx">
                    {{formReport.bzkZhdZrjgmxb.zjlx}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="证件号码" prop="zjhm">
                    {{formReport.bzkZhdZrjgmxb.zjhm}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="性别名称" prop="xb">
                    {{formReport.bzkZhdZrjgmxb.xb}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="年龄" prop="nl">
                    {{formReport.bzkZhdZrjgmxb.nl}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="注册日期" prop="zcrq">
                    {{formReport.bzkZhdZrjgmxb.zcrq}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="注册地址" prop="zcdz">
                    {{formReport.bzkZhdZrjgmxb.zcdz}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="注册地址行政区划" prop="zcdzXzqh">
                    {{formReport.bzkZhdZrjgmxb.zcdzXzqh}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="登记状态名称" prop="regstate">
                    {{formReport.bzkZhdZrjgmxb.regstate}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="重大税收违法案件主要违法事实" prop="zywfss">
                    {{formReport.bzkZhdZrjgmxb.zywfss}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="失信被执行人案号" prop="sxbzxrah">
                    {{formReport.bzkZhdZrjgmxb.sxbzxrah}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="失信黑名单信息名称" prop="sxhmdxxmc">
                    {{formReport.bzkZhdZrjgmxb.sxhmdxxmc}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="工商异常信息" prop="gsycxx">
                    {{formReport.bzkZhdZrjgmxb.gsycxx}}
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="不动产查封" prop="bdccf">
                    {{formReport.bzkZhdZrjgmxb.bdccf}}
                  </el-form-item>
                </el-col>
              </el-row>
              </el-form>
          </el-collapse-item>
      </el-collapse>
    </el-dialog>

    <el-dialog :title="titleSimpleReport" :visible.sync="openSimpleReport" width="800px" append-to-body>

        <div v-for="(report,index) in reportWrapper">
          <h2>{{idxArray[index]}}、{{report.title}}{{report.showCount?"(共"+report.count+"条)":""}}</h2>
          <ol style="border:1px solid #000;padding-top:12px;padding-bottom:12px;padding-left:30px;min-height:100px">
            <div style="margin-bottom:8px">{{report.subTitle}}</div>
            <li v-for="item in report.item">
              {{item.name+":"+item.value}}
            </li>
          </ol>
        </div>
    </el-dialog>

  </div>
</template>

<script>
import { listLoan, getLoan, delLoan, addLoan, updateLoan, exportLoan,getReport } from "@/api/system/loan";

export default {
  name: "Loan",
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
      // 企业贷款信息表格数据
      loanList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示征信报告
      openReport: false,
      // 意向银行字典
      loanBandOptions: [],
      // 是否首次贷款字典
      loanFirstOptions: [],
      // 贷款对象类型字典
      loanObjectTypeOptions: [],
      //是否字典
      loanYesOrNoOptions:[],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        companyName: null,
        companyCreditCode: null,
        contactName: null,
        contactPhone: null,
        loanObjectType: null,
        customerManager: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        mztUserId: [
          { required: true, message: "闽政通用户ID不能为空", trigger: "blur" }
        ],
        companyName: [
          { required: true, message: "企业名称不能为空", trigger: "blur" }
        ],
        companyCreditCode: [
          { required: true, message: "统一社会信用代码不能为空", trigger: "blur" }
        ],
        loanAmount: [
          { required: true, message: "贷款金额不能为空", trigger: "blur" }
        ],
      },

      activeNames: ['1'],
      // 报告弹出层标题
      titleReport: "",
      formReport:{},

      titleSimpleReport: "",
      openSimpleReport: false,
      reportWrapper: [],
      idxArray : ['一','二','三','四','五','六','七','八','九','十','十一','十二','十三','十四','十五','十六','十七','十八','十九'],

    };
  },
  created() {
    this.getList();
    this.getDicts("sys_user_band").then(response => {
      this.loanBandOptions = response.data;
    });
    this.getDicts("data_is_loan_first").then(response => {
      this.loanFirstOptions = response.data;
    });
    this.getDicts("data_loan_object_type").then(response => {
      this.loanObjectTypeOptions = response.data;
    });
    this.getDicts("data_loan_yes_no").then(response => {
      this.loanYesOrNoOptions = response.data;
    });
  },
  methods: {
    /** 查询企业贷款信息列表 */
    getList() {
      this.loading = true;
      listLoan(this.queryParams).then(response => {
        this.loanList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 意向银行字典翻译
    loanBandFormat(row, column) {
      return this.selectDictLabels(this.loanBandOptions, row.loanBand);
    },
    // 是否首次贷款字典翻译
    loanFirstFormat(row, column) {
      return this.selectDictLabel(this.loanFirstOptions, row.loanFirst);
    },
    // 贷款对象类型字典翻译
    loanObjectTypeFormat(row, column) {
      return this.selectDictLabel(this.loanObjectTypeOptions, row.loanObjectType);
    },
    // 是否字典翻译
    loanYesOrNoFormat(row, column) {
      return this.selectDictLabel(this.loanYesOrNoOptions, row.loanYesOrNo);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        companyId: null,
        mztUserId: null,
        companyName: null,
        companyCreditCode: null,
        companyProvince: null,
        companyAddress: null,
        companyCity: null,
        companyArea: null,
        companyType: null,
        companyIndustry: null,
        companyBusiness: null,
        loanBand: [],
        loanAmount: null,
        loanFirst: null,
        loanPurpose: null,
        remark: null,
        contactName: null,
        contactPhone: null,
        delFlag: null,
        createTime: null,
        updateTime: null,
        bankBranch: null,
        loanObjectType: null,
        customerManager: null,
        loanYesOrNo: null
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
      this.ids = selection.map(item => item.companyId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加企业贷款信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const companyId = row.companyId || this.ids
      getLoan(companyId).then(response => {
        this.form = response.data;
        this.form.loanBand = this.form.loanBand.split(",");
        this.open = true;
        this.title = "修改企业贷款信息";
      });
    },
    /** 查看报告按钮操作 */
    handleReport(row) {
      const tyshxydm = row.companyCreditCode
      getReport(tyshxydm).then(response => {
        this.formReport = response.data;
        this.openReport = true;
        this.titleReport = "查看企业征信信息";
      });
    },
    /** 查看报告按钮操作 */
    handleSimpleReport(row) {
      const tyshxydm = row.companyCreditCode
      var reportWrapper = [
        {
          title:"基础信息",
          subTitle:"法人和非法人组织在注册登记部门和税务、海关、社保、公 积金管理等部门的基础登记信息，以及有关年报信息;自然人的 身份信息以及在各有关部门的基础登记信息。",
          showCount:true,
          count:0,
          item:[],
        },
        {
          title:"行政许可信息",
          subTitle:"各级行政机关依法对信用主体作出的行政许可信息。",
          showCount:true,
          count:0,
          item:[],
        },
        {
          title:"行政处罚信息",
          subTitle:"各级行政机关依法对信用主体作出的行政处罚信息。",
          showCount:true,
          count:0,
          item:[],
        },
        {
          title:"守信激励信息",
          subTitle:"各有关部门将信用主体列为守信激励对象信息。",
          showCount:true,
          count:0,
          item:[],
        },
        {
          title:"失信惩戒信息",
          subTitle:"各有关部门将信用主体列为失信惩戒对象信息。",
          showCount:true,
          count:0,
          item:[],
        },
        {
          title:"重点关注信息",
          subTitle:"各有关部门将信用主体列为重点关注名单信息。",
          showCount:true,
          count:0,
          item:[],
        },
        {
          title:"资质/资格信息",
          subTitle:"除行政许可外，信用主体获得的其他资质或资格信息。",
          showCount:true,
          count:0,
          item:[],
        },
        {
          title:"风险提示信息",
          subTitle:"各有关部门依法依规发布的涉及信用主体的风险预警信息。",
          showCount:true,
          count:0,
          item:[],
        },
        {
          title:"其他信息",
          subTitle:"涉及信用主体的信用评价信息(如公共信用综合评价信息、 行业信用评价信息等)、信用修复信息、信用承诺信息以及其他 与信用主体信用状况相关的信息。",
          showCount:true,
          count:0,
          item:[],
        },
        {
          title:"综合信用状况分析",
          subTitle:"结合信用主体相关信用信息对其信用状况进行全面、客观描述和综合分析。",
          showCount:false,
          count:0,
          item:[],
        },
        {
          title:"信用状况提升建议",
          subTitle:"对信用主体践行社会主义核心价值观、依法合规经营、增强 诚信守法意识等方面提出意见建议。",
          showCount:false,
          count:0,
          item:[],
        },
        {
          title:"地方信用网站公共信用信息报告补充内容(如有)",
          subTitle:"",
          showCount:false,
          count:0,
          item:[],
        }
        ];

      getReport(tyshxydm).then(response => {
        var formReport = response.data;

        //基础信息
        var reportItem0 = reportWrapper[0].item;
        //TODO-插入实际业务数据
        if(formReport.bzkZhdPfmxmxb != null){

          if(formReport.bzkZhdPfmxmxb.tyshxydm){
            reportItem0.push({
              "name":"统一社会信用代码",
              "value":formReport.bzkZhdPfmxmxb.tyshxydm
            });
          }

          if(formReport.bzkZhdPfmxmxb.sbgtgshxm){
            reportItem0.push({
              "name":"申报个体工商户姓名",
              "value":formReport.bzkZhdPfmxmxb.sbgtgshxm
            });
          }

          if(formReport.bzkZhdPfmxmxb.zjlx){
            reportItem0.push({
              "name":"证件类型",
              "value":formReport.bzkZhdPfmxmxb.zjlx
            });
          }

          if(formReport.bzkZhdPfmxmxb.zjhm){
            reportItem0.push({
              "name":"证件号码",
              "value":formReport.bzkZhdPfmxmxb.zjhm
            });
          }

          if(formReport.bzkZhdPfmxmxb.yydm){
            reportItem0.push({
              "name":"营业店名",
              "value":formReport.bzkZhdPfmxmxb.yydm
            });
          }

          if(formReport.bzkZhdPfmxmxb.jycsDz){
            reportItem0.push({
              "name":"经营场所地址",
              "value":formReport.bzkZhdPfmxmxb.jycsDz
            });
          }

          if(formReport.bzkZhdPfmxmxb.zcrq){
            reportItem0.push({
              "name":"注册日期",
              "value":formReport.bzkZhdPfmxmxb.zcrq
            });
          }
          reportWrapper[0].count = reportItem0.length;
        }

        // 守信激励信息
        var reportItem3 = reportWrapper[3].item;
        if(formReport.bzkZhdPfmxmxb != null){
          if (formReport.bzkZhdPfmxmxb.zrrsfbj) {
            reportItem3.push({
              "name": "自然人是否补缴",
              "value": formReport.bzkZhdPfmxmxb.zrrsfbj
            });
          }

          if (formReport.bzkZhdPfmxmxb.jlcsnr) {
            reportItem3.push({
              "name": "激励措施内容",
              "value": formReport.bzkZhdPfmxmxb.jlcsnr
            });
          }

          if (formReport.bzkZhdPfmxmxb.jlmdmc) {
            reportItem3.push({
              "name": "激励名单名称",
              "value": formReport.bzkZhdPfmxmxb.jlmdmc
            });
          }
          reportWrapper[3].count = reportItem3.length;
        }

        // 失信惩戒信息
        var reportItem4 = reportWrapper[4].item;
        if(formReport.bzkZhdZrjgmxb != null) {
          if (formReport.bzkZhdZrjgmxb.zywfss) {
            reportItem4.push({
              "name": "重大税收违法案件主要违法事实",
              "value": formReport.bzkZhdZrjgmxb.zywfss
            });
          }

          if (formReport.bzkZhdZrjgmxb.sxbzxrah) {
            reportItem4.push({
              "name": "失信被执行人案号",
              "value": formReport.bzkZhdZrjgmxb.sxbzxrah
            });
          }

          if (formReport.bzkZhdZrjgmxb.sxhmdxxmc) {
            reportItem4.push({
              "name": "失信黑名单信息名称",
              "value": formReport.bzkZhdZrjgmxb.sxhmdxxmc
            });
          }
          reportWrapper[4].count = reportItem4.length;
        }

          // 资质/资格信息
          var reportItem6 = reportWrapper[6].item;
          if(formReport.bzkZhdPfmxmxb != null) {
            if (formReport.bzkZhdPfmxmxb.xsfczcyj) {
              reportItem6.push({
                "name": "享受扶持政策依据",
                "value": formReport.bzkZhdPfmxmxb.xsfczcyj
              });
            }

            if (formReport.bzkZhdPfmxmxb.xsfczcnr) {
              reportItem6.push({
                "name": "享受扶持政策内容",
                "value": formReport.bzkZhdPfmxmxb.xsfczcnr
              });
            }

            if (formReport.bzkZhdPfmxmxb.xsfczcdse) {
              reportItem6.push({
                "name": "享受扶持政策的数额",
                "value": formReport.bzkZhdPfmxmxb.xsfczcdse
              });
            }

            if (formReport.bzkZhdPfmxmxb.xsfczcdbm) {
              reportItem6.push({
                "name": "享受扶持政策的部门",
                "value": formReport.bzkZhdPfmxmxb.xsfczcdbm
              });
            }

            if (formReport.bzkZhdPfmxmxb.ssfczcrq) {
              reportItem6.push({
                "name": "实施扶持政策日期",
                "value": formReport.bzkZhdPfmxmxb.ssfczcrq
              });
            }
            reportWrapper[6].count = reportItem6.length;
          }

        // 风险提示信息
        var reportItem7 = reportWrapper[7].item;
        if(formReport.bzkZhdZrjgmxb != null) {
          if (formReport.bzkZhdZrjgmxb.gsycxx) {
            reportItem7.push({
              "name": "工商异常信息",
              "value": formReport.bzkZhdZrjgmxb.gsycxx
            });
          }

          if (formReport.bzkZhdZrjgmxb.bdccf) {
            reportItem7.push({
              "name": "不动产查封",
              "value": formReport.bzkZhdZrjgmxb.bdccf
            });
          }
          reportWrapper[7].count = reportItem7.length;
        }

        this.reportWrapper = reportWrapper;
        this.openSimpleReport = true;
        this.titleSimpleReport = "查看企业征信信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.loanBand = this.form.loanBand.join(",");
          if (this.form.companyId != null) {
            updateLoan(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addLoan(this.form).then(response => {
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
      const companyIds = row.companyId || this.ids;
      this.$confirm('是否确认删除企业贷款信息编号为"' + companyIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delLoan(companyIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有企业贷款信息数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          this.exportLoading = true;
          return exportLoan(queryParams);
        }).then(response => {
          this.download(response.msg);
          this.exportLoading = false;
        }).catch(() => {});
    },

    handleChange(val) {
      console.log(val);
    },
    //关闭弹窗判断
    // handleClose(done){
    //   this.$confirm('是否关闭当前页面？').then(() => {
    //     done();
    //   }).catch(() => {});
    // }
  }
};
</script>