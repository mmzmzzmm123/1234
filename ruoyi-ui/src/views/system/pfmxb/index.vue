<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="信息入库时间戳" prop="xxrksjTmsp">
        <el-input
          v-model="queryParams.xxrksjTmsp"
          placeholder="请输入信息入库时间戳"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="首次采集时间" prop="firstCollectTime">
        <el-input
          v-model="queryParams.firstCollectTime"
          placeholder="请输入首次采集时间"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="最近采集时间" prop="lastCollectTime">
        <el-input
          v-model="queryParams.lastCollectTime"
          placeholder="请输入最近采集时间"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发现次数" prop="foundTimes">
        <el-input
          v-model="queryParams.foundTimes"
          placeholder="请输入发现次数"
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
          v-hasPermi="['system:pfmxb:add']"
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
          v-hasPermi="['system:pfmxb:edit']"
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
          v-hasPermi="['system:pfmxb:remove']"
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
          v-hasPermi="['system:pfmxb:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="pfmxbList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="标准库主记录ID" align="center" prop="zjlid" />
      <el-table-column label="来源应用系统编号" align="center" prop="lyyyxtid" />
      <el-table-column label="来源表" align="center" prop="dataFrom" />
      <el-table-column label="信息入库时间" align="center" prop="xxrksj" />
      <el-table-column label="信息入库时间戳" align="center" prop="xxrksjTmsp" />
      <el-table-column label="标准库_备注" align="center" prop="bz" />
      <el-table-column label="来源表信息入库时间" align="center" prop="sourceXxrksj" />
      <el-table-column label="来源表主记录ID" align="center" prop="sourceZjlid" />
      <el-table-column label="业务标签ID" align="center" prop="businessFlagId" />
      <el-table-column label="业务标签值" align="center" prop="businessFlagValue" />
      <el-table-column label="首次采集时间" align="center" prop="firstCollectTime" />
      <el-table-column label="最近采集时间" align="center" prop="lastCollectTime" />
      <el-table-column label="发现次数" align="center" prop="foundTimes" />
      <el-table-column label="错误数据标识" align="center" prop="errorDataFlag" />
      <el-table-column label="错误数据" align="center" prop="errorData" />
      <el-table-column label="异常字段编码" align="center" prop="errorElement" />
      <el-table-column label="数据批次标识" align="center" prop="batchDataFlag" />
      <el-table-column label="数据安全级别代码" align="center" prop="sjaqjbdm" />
      <el-table-column label="采集地" align="center" prop="cjd" />
      <el-table-column label="统一社会信用代码" align="center" prop="tyshxydm" />
      <el-table-column label="证件类型" align="center" prop="zjlx" />
      <el-table-column label="证件号码" align="center" prop="zjhm" />
      <el-table-column label="生产经营地址" align="center" prop="scjydz" />
      <el-table-column label="注册(机构住所生产经营地址)地址" align="center" prop="zcjgzsscjydzdz" />
      <el-table-column label="不动产登记证明" align="center" prop="bdcdjzm" />
      <el-table-column label="抵押物价值" align="center" prop="dywjz" />
      <el-table-column label="个体店名" align="center" prop="gtdm" />
      <el-table-column label="经营时间" align="center" prop="jysj" />
      <el-table-column label="女方身份证件类型" align="center" prop="nfsfzjlx" />
      <el-table-column label="女方姓名" align="center" prop="nfxm" />
      <el-table-column label="女方身份证件号码" align="center" prop="nfsfzjhm" />
      <el-table-column label="档案号" align="center" prop="dah" />
      <el-table-column label="结婚登记时间" align="center" prop="jhdjsj" />
      <el-table-column label="结婚登记类型" align="center" prop="jhdjlx" />
      <el-table-column label="学历(文化程度)" align="center" prop="xlwhcd" />
      <el-table-column label="专业技术职务系列名称" align="center" prop="zyjszwxlmc" />
      <el-table-column label="专业技术职务等级" align="center" prop="zyjszwdj" />
      <el-table-column label="国家职业资格等级" align="center" prop="gjzyzgdj" />
      <el-table-column label="欠费金额(元)" align="center" prop="qfjey" />
      <el-table-column label="欠费时段" align="center" prop="qfsd" />
      <el-table-column label="当期欠税金额(元)" align="center" prop="dqqsjey" />
      <el-table-column label="欠税统计截止日期" align="center" prop="qstjjzrq" />
      <el-table-column label="欠税统计开始日期" align="center" prop="qstjksrq" />
      <el-table-column label="失信严重程度" align="center" prop="sxyzcd" />
      <el-table-column label="是否补缴" align="center" prop="sfbj" />
      <el-table-column label="欠税税种" align="center" prop="qssz" />
      <el-table-column label="欠税余额" align="center" prop="qsye" />
      <el-table-column label="逃避追缴欠费行政处罚主要违法事实" align="center" prop="tbzjqfxzcfzywfss" />
      <el-table-column label="逃避追缴欠费行政处罚处罚情况" align="center" prop="tbzjqfxzcfcfqk" />
      <el-table-column label="自然人欠税信息欠税税种" align="center" prop="zrrqsxxqssz" />
      <el-table-column label="自然人欠税统计截止日期" align="center" prop="zrrqstjjzrq" />
      <el-table-column label="自然人欠税统计开始日期" align="center" prop="zrrqstjksrq" />
      <el-table-column label="自然人欠税余额" align="center" prop="zrrqsye" />
      <el-table-column label="自然人是否补缴" align="center" prop="zrrsfbj" />
      <el-table-column label="国家联合奖惩信息更新时间" align="center" prop="gjlhjcxxgxsj" />
      <el-table-column label="国家联合奖惩信息名单类型" align="center" prop="gjlhjcxxmdlx" />
      <el-table-column label="联合奖惩黑名单名称" align="center" prop="lhjchmdmc" />
      <el-table-column label="荣誉表彰事由" align="center" prop="rybzsy" />
      <el-table-column label="荣誉表彰名称" align="center" prop="rybzmc" />
      <el-table-column label="荣誉表彰级别" align="center" prop="rybzjb" />
      <el-table-column label="守信企业注册号" align="center" prop="sxqyzch" />
      <el-table-column label="守信企业或法人组织名称" align="center" prop="sxqyhfrzzmc" />
      <el-table-column label="支付工资评级级别(字典转后)" align="center" prop="zfgzpjjbzdzh" />
      <el-table-column label="支付工资决定书文号" align="center" prop="zfgzjdswh" />
      <el-table-column label="纳税人信用等级" align="center" prop="nsrxydj" />
      <el-table-column label="百强企业企业名称" align="center" prop="bqqyqymc" />
      <el-table-column label="机动车所有人" align="center" prop="jdcsyr" />
      <el-table-column label="车辆类型" align="center" prop="cllx" />
      <el-table-column label="机动车状态" align="center" prop="jdczt" />
      <el-table-column label="所有权" align="center" prop="syq" />
      <el-table-column label="使用性质" align="center" prop="syxz" />
      <el-table-column label="不动产登记证号" align="center" prop="bdcdjzh" />
      <el-table-column label="查封类型" align="center" prop="cflx" />
      <el-table-column label="查封文件" align="center" prop="cfwj" />
      <el-table-column label="查封起始时间" align="center" prop="cfqssj" />
      <el-table-column label="查封结束时间" align="center" prop="cfjssj" />
      <el-table-column label="解封机关" align="center" prop="jfjg" />
      <el-table-column label="解封文件" align="center" prop="jfwj" />
      <el-table-column label="缴纳单位统一社会信用代码" align="center" prop="jndwtyshxydm" />
      <el-table-column label="缴费ID" align="center" prop="jfid" />
      <el-table-column label="缴费时间" align="center" prop="jfsj" />
      <el-table-column label="拖欠农民工工资决定书文号" align="center" prop="tqnmggzjdswh" />
      <el-table-column label="拖欠农民工工资黑名单ID" align="center" prop="tqnmggzhmdid" />
      <el-table-column label="失信债务案号" align="center" prop="sxzwah" />
      <el-table-column label="支付工资失信信用评价ID" align="center" prop="zfgzsxxypjid" />
      <el-table-column label="支付工资失信决定机关" align="center" prop="zfgzsxjdjg" />
      <el-table-column label="政务失信案号" align="center" prop="zwsxah" />
      <el-table-column label="政务失信核查情况" align="center" prop="zwsxhcqk" />
      <el-table-column label="联合惩戒人员类别" align="center" prop="lhcjrylb" />
      <el-table-column label="联合惩戒注册号" align="center" prop="lhcjzch" />
      <el-table-column label="环境处罚事由" align="center" prop="hjcfsy" />
      <el-table-column label="环境处罚依据" align="center" prop="hjcfyj" />
      <el-table-column label="环境处罚日期" align="center" prop="hjcfrq" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:pfmxb:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:pfmxb:remove']"
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

    <!-- 添加或修改福建省泉州市个体工商户信贷_主题库_评分模型对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="来源应用系统编号" prop="lyyyxtid">
          <el-input v-model="form.lyyyxtid" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="来源表" prop="dataFrom">
          <el-input v-model="form.dataFrom" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="信息入库时间" prop="xxrksj">
          <el-input v-model="form.xxrksj" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="信息入库时间戳" prop="xxrksjTmsp">
          <el-input v-model="form.xxrksjTmsp" placeholder="请输入信息入库时间戳" />
        </el-form-item>
        <el-form-item label="标准库_备注" prop="bz">
          <el-input v-model="form.bz" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="来源表信息入库时间" prop="sourceXxrksj">
          <el-input v-model="form.sourceXxrksj" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="来源表主记录ID" prop="sourceZjlid">
          <el-input v-model="form.sourceZjlid" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="业务标签ID" prop="businessFlagId">
          <el-input v-model="form.businessFlagId" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="业务标签值" prop="businessFlagValue">
          <el-input v-model="form.businessFlagValue" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="首次采集时间" prop="firstCollectTime">
          <el-input v-model="form.firstCollectTime" placeholder="请输入首次采集时间" />
        </el-form-item>
        <el-form-item label="最近采集时间" prop="lastCollectTime">
          <el-input v-model="form.lastCollectTime" placeholder="请输入最近采集时间" />
        </el-form-item>
        <el-form-item label="发现次数" prop="foundTimes">
          <el-input v-model="form.foundTimes" placeholder="请输入发现次数" />
        </el-form-item>
        <el-form-item label="错误数据标识" prop="errorDataFlag">
          <el-input v-model="form.errorDataFlag" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="错误数据" prop="errorData">
          <el-input v-model="form.errorData" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="异常字段编码" prop="errorElement">
          <el-input v-model="form.errorElement" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="数据批次标识" prop="batchDataFlag">
          <el-input v-model="form.batchDataFlag" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="数据安全级别代码" prop="sjaqjbdm">
          <el-input v-model="form.sjaqjbdm" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="采集地" prop="cjd">
          <el-input v-model="form.cjd" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="统一社会信用代码" prop="tyshxydm">
          <el-input v-model="form.tyshxydm" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="证件类型" prop="zjlx">
          <el-input v-model="form.zjlx" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="证件号码" prop="zjhm">
          <el-input v-model="form.zjhm" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="生产经营地址" prop="scjydz">
          <el-input v-model="form.scjydz" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="注册(机构住所生产经营地址)地址" prop="zcjgzsscjydzdz">
          <el-input v-model="form.zcjgzsscjydzdz" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="不动产登记证明" prop="bdcdjzm">
          <el-input v-model="form.bdcdjzm" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="抵押物价值" prop="dywjz">
          <el-input v-model="form.dywjz" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="个体店名" prop="gtdm">
          <el-input v-model="form.gtdm" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="经营时间" prop="jysj">
          <el-input v-model="form.jysj" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="女方身份证件类型" prop="nfsfzjlx">
          <el-input v-model="form.nfsfzjlx" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="女方姓名" prop="nfxm">
          <el-input v-model="form.nfxm" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="女方身份证件号码" prop="nfsfzjhm">
          <el-input v-model="form.nfsfzjhm" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="档案号" prop="dah">
          <el-input v-model="form.dah" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="结婚登记时间" prop="jhdjsj">
          <el-input v-model="form.jhdjsj" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="结婚登记类型" prop="jhdjlx">
          <el-input v-model="form.jhdjlx" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="学历(文化程度)" prop="xlwhcd">
          <el-input v-model="form.xlwhcd" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="专业技术职务系列名称" prop="zyjszwxlmc">
          <el-input v-model="form.zyjszwxlmc" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="专业技术职务等级" prop="zyjszwdj">
          <el-input v-model="form.zyjszwdj" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="国家职业资格等级" prop="gjzyzgdj">
          <el-input v-model="form.gjzyzgdj" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="欠费金额(元)" prop="qfjey">
          <el-input v-model="form.qfjey" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="欠费时段" prop="qfsd">
          <el-input v-model="form.qfsd" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="当期欠税金额(元)" prop="dqqsjey">
          <el-input v-model="form.dqqsjey" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="欠税统计截止日期" prop="qstjjzrq">
          <el-input v-model="form.qstjjzrq" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="欠税统计开始日期" prop="qstjksrq">
          <el-input v-model="form.qstjksrq" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="失信严重程度" prop="sxyzcd">
          <el-input v-model="form.sxyzcd" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="是否补缴" prop="sfbj">
          <el-input v-model="form.sfbj" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="欠税税种" prop="qssz">
          <el-input v-model="form.qssz" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="欠税余额" prop="qsye">
          <el-input v-model="form.qsye" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="逃避追缴欠费行政处罚主要违法事实" prop="tbzjqfxzcfzywfss">
          <el-input v-model="form.tbzjqfxzcfzywfss" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="逃避追缴欠费行政处罚处罚情况" prop="tbzjqfxzcfcfqk">
          <el-input v-model="form.tbzjqfxzcfcfqk" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="自然人欠税信息欠税税种" prop="zrrqsxxqssz">
          <el-input v-model="form.zrrqsxxqssz" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="自然人欠税统计截止日期" prop="zrrqstjjzrq">
          <el-input v-model="form.zrrqstjjzrq" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="自然人欠税统计开始日期" prop="zrrqstjksrq">
          <el-input v-model="form.zrrqstjksrq" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="自然人欠税余额" prop="zrrqsye">
          <el-input v-model="form.zrrqsye" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="自然人是否补缴" prop="zrrsfbj">
          <el-input v-model="form.zrrsfbj" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="国家联合奖惩信息更新时间" prop="gjlhjcxxgxsj">
          <el-input v-model="form.gjlhjcxxgxsj" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="国家联合奖惩信息名单类型" prop="gjlhjcxxmdlx">
          <el-input v-model="form.gjlhjcxxmdlx" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="联合奖惩黑名单名称" prop="lhjchmdmc">
          <el-input v-model="form.lhjchmdmc" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="荣誉表彰事由" prop="rybzsy">
          <el-input v-model="form.rybzsy" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="荣誉表彰名称" prop="rybzmc">
          <el-input v-model="form.rybzmc" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="荣誉表彰级别" prop="rybzjb">
          <el-input v-model="form.rybzjb" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="守信企业注册号" prop="sxqyzch">
          <el-input v-model="form.sxqyzch" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="守信企业或法人组织名称" prop="sxqyhfrzzmc">
          <el-input v-model="form.sxqyhfrzzmc" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="支付工资评级级别(字典转后)" prop="zfgzpjjbzdzh">
          <el-input v-model="form.zfgzpjjbzdzh" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="支付工资决定书文号" prop="zfgzjdswh">
          <el-input v-model="form.zfgzjdswh" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="纳税人信用等级" prop="nsrxydj">
          <el-input v-model="form.nsrxydj" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="百强企业企业名称" prop="bqqyqymc">
          <el-input v-model="form.bqqyqymc" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="机动车所有人" prop="jdcsyr">
          <el-input v-model="form.jdcsyr" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="车辆类型" prop="cllx">
          <el-input v-model="form.cllx" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="机动车状态" prop="jdczt">
          <el-input v-model="form.jdczt" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="所有权" prop="syq">
          <el-input v-model="form.syq" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="使用性质" prop="syxz">
          <el-input v-model="form.syxz" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="不动产登记证号" prop="bdcdjzh">
          <el-input v-model="form.bdcdjzh" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="查封类型" prop="cflx">
          <el-input v-model="form.cflx" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="查封文件" prop="cfwj">
          <el-input v-model="form.cfwj" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="查封起始时间" prop="cfqssj">
          <el-input v-model="form.cfqssj" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="查封结束时间" prop="cfjssj">
          <el-input v-model="form.cfjssj" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="解封机关" prop="jfjg">
          <el-input v-model="form.jfjg" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="解封文件" prop="jfwj">
          <el-input v-model="form.jfwj" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="缴纳单位统一社会信用代码" prop="jndwtyshxydm">
          <el-input v-model="form.jndwtyshxydm" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="缴费ID" prop="jfid">
          <el-input v-model="form.jfid" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="缴费时间" prop="jfsj">
          <el-input v-model="form.jfsj" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="拖欠农民工工资决定书文号" prop="tqnmggzjdswh">
          <el-input v-model="form.tqnmggzjdswh" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="拖欠农民工工资黑名单ID" prop="tqnmggzhmdid">
          <el-input v-model="form.tqnmggzhmdid" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="失信债务案号" prop="sxzwah">
          <el-input v-model="form.sxzwah" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="支付工资失信信用评价ID" prop="zfgzsxxypjid">
          <el-input v-model="form.zfgzsxxypjid" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="支付工资失信决定机关" prop="zfgzsxjdjg">
          <el-input v-model="form.zfgzsxjdjg" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="政务失信案号" prop="zwsxah">
          <el-input v-model="form.zwsxah" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="政务失信核查情况" prop="zwsxhcqk">
          <el-input v-model="form.zwsxhcqk" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="联合惩戒人员类别" prop="lhcjrylb">
          <el-input v-model="form.lhcjrylb" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="联合惩戒注册号" prop="lhcjzch">
          <el-input v-model="form.lhcjzch" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="环境处罚事由" prop="hjcfsy">
          <el-input v-model="form.hjcfsy" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="环境处罚依据" prop="hjcfyj">
          <el-input v-model="form.hjcfyj" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="环境处罚日期" prop="hjcfrq">
          <el-input v-model="form.hjcfrq" type="textarea" placeholder="请输入内容" />
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
import { listPfmxb, getPfmxb, delPfmxb, addPfmxb, updatePfmxb, exportPfmxb } from "@/api/system/pfmxb";

export default {
  name: "Pfmxb",
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
      // 福建省泉州市个体工商户信贷_主题库_评分模型表格数据
      pfmxbList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        lyyyxtid: null,
        dataFrom: null,
        xxrksj: null,
        xxrksjTmsp: null,
        bz: null,
        sourceXxrksj: null,
        sourceZjlid: null,
        businessFlagId: null,
        businessFlagValue: null,
        firstCollectTime: null,
        lastCollectTime: null,
        foundTimes: null,
        errorDataFlag: null,
        errorData: null,
        errorElement: null,
        batchDataFlag: null,
        sjaqjbdm: null,
        cjd: null,
        tyshxydm: null,
        zjlx: null,
        zjhm: null,
        scjydz: null,
        zcjgzsscjydzdz: null,
        bdcdjzm: null,
        dywjz: null,
        gtdm: null,
        jysj: null,
        nfsfzjlx: null,
        nfxm: null,
        nfsfzjhm: null,
        dah: null,
        jhdjsj: null,
        jhdjlx: null,
        xlwhcd: null,
        zyjszwxlmc: null,
        zyjszwdj: null,
        gjzyzgdj: null,
        qfjey: null,
        qfsd: null,
        dqqsjey: null,
        qstjjzrq: null,
        qstjksrq: null,
        sxyzcd: null,
        sfbj: null,
        qssz: null,
        qsye: null,
        tbzjqfxzcfzywfss: null,
        tbzjqfxzcfcfqk: null,
        zrrqsxxqssz: null,
        zrrqstjjzrq: null,
        zrrqstjksrq: null,
        zrrqsye: null,
        zrrsfbj: null,
        gjlhjcxxgxsj: null,
        gjlhjcxxmdlx: null,
        lhjchmdmc: null,
        rybzsy: null,
        rybzmc: null,
        rybzjb: null,
        sxqyzch: null,
        sxqyhfrzzmc: null,
        zfgzpjjbzdzh: null,
        zfgzjdswh: null,
        nsrxydj: null,
        bqqyqymc: null,
        jdcsyr: null,
        cllx: null,
        jdczt: null,
        syq: null,
        syxz: null,
        bdcdjzh: null,
        cflx: null,
        cfwj: null,
        cfqssj: null,
        cfjssj: null,
        jfjg: null,
        jfwj: null,
        jndwtyshxydm: null,
        jfid: null,
        jfsj: null,
        tqnmggzjdswh: null,
        tqnmggzhmdid: null,
        sxzwah: null,
        zfgzsxxypjid: null,
        zfgzsxjdjg: null,
        zwsxah: null,
        zwsxhcqk: null,
        lhcjrylb: null,
        lhcjzch: null,
        hjcfsy: null,
        hjcfyj: null,
        hjcfrq: null
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
    /** 查询福建省泉州市个体工商户信贷_主题库_评分模型列表 */
    getList() {
      this.loading = true;
      listPfmxb(this.queryParams).then(response => {
        this.pfmxbList = response.rows;
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
        zjlid: null,
        lyyyxtid: null,
        dataFrom: null,
        xxrksj: null,
        xxrksjTmsp: null,
        bz: null,
        sourceXxrksj: null,
        sourceZjlid: null,
        businessFlagId: null,
        businessFlagValue: null,
        firstCollectTime: null,
        lastCollectTime: null,
        foundTimes: null,
        errorDataFlag: null,
        errorData: null,
        errorElement: null,
        batchDataFlag: null,
        sjaqjbdm: null,
        cjd: null,
        tyshxydm: null,
        zjlx: null,
        zjhm: null,
        scjydz: null,
        zcjgzsscjydzdz: null,
        bdcdjzm: null,
        dywjz: null,
        gtdm: null,
        jysj: null,
        nfsfzjlx: null,
        nfxm: null,
        nfsfzjhm: null,
        dah: null,
        jhdjsj: null,
        jhdjlx: null,
        xlwhcd: null,
        zyjszwxlmc: null,
        zyjszwdj: null,
        gjzyzgdj: null,
        qfjey: null,
        qfsd: null,
        dqqsjey: null,
        qstjjzrq: null,
        qstjksrq: null,
        sxyzcd: null,
        sfbj: null,
        qssz: null,
        qsye: null,
        tbzjqfxzcfzywfss: null,
        tbzjqfxzcfcfqk: null,
        zrrqsxxqssz: null,
        zrrqstjjzrq: null,
        zrrqstjksrq: null,
        zrrqsye: null,
        zrrsfbj: null,
        gjlhjcxxgxsj: null,
        gjlhjcxxmdlx: null,
        lhjchmdmc: null,
        rybzsy: null,
        rybzmc: null,
        rybzjb: null,
        sxqyzch: null,
        sxqyhfrzzmc: null,
        zfgzpjjbzdzh: null,
        zfgzjdswh: null,
        nsrxydj: null,
        bqqyqymc: null,
        jdcsyr: null,
        cllx: null,
        jdczt: null,
        syq: null,
        syxz: null,
        bdcdjzh: null,
        cflx: null,
        cfwj: null,
        cfqssj: null,
        cfjssj: null,
        jfjg: null,
        jfwj: null,
        jndwtyshxydm: null,
        jfid: null,
        jfsj: null,
        tqnmggzjdswh: null,
        tqnmggzhmdid: null,
        sxzwah: null,
        zfgzsxxypjid: null,
        zfgzsxjdjg: null,
        zwsxah: null,
        zwsxhcqk: null,
        lhcjrylb: null,
        lhcjzch: null,
        hjcfsy: null,
        hjcfyj: null,
        hjcfrq: null
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
      this.ids = selection.map(item => item.zjlid)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加福建省泉州市个体工商户信贷_主题库_评分模型";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const zjlid = row.zjlid || this.ids
      getPfmxb(zjlid).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改福建省泉州市个体工商户信贷_主题库_评分模型";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.zjlid != null) {
            updatePfmxb(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPfmxb(this.form).then(response => {
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
      const zjlids = row.zjlid || this.ids;
      this.$confirm('是否确认删除福建省泉州市个体工商户信贷_主题库_评分模型编号为"' + zjlids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delPfmxb(zjlids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有福建省泉州市个体工商户信贷_主题库_评分模型数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          this.exportLoading = true;
          return exportPfmxb(queryParams);
        }).then(response => {
          this.download(response.msg);
          this.exportLoading = false;
        }).catch(() => {});
    }
  }
};
</script>
