<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="4">
        <dept-tree @selectedDept="handleNodeClick"
        ></dept-tree>
      </el-col>
      <el-col :span="20">
        <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="100px">
          <el-form-item label="聚合层次" prop="aggregationLevel">
            <el-select v-model="queryParams.aggregationLevel" placeholder="请选择聚合层次" clearable size="small">
              <el-option
                v-for="dict in aggregationLevelOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="档案馆名称" prop="archivesName">
            <el-input
              v-model="queryParams.archivesName"
              placeholder="请输入档案馆名称"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="档案馆代码" prop="archivesIdentifier">
            <el-input
              v-model="queryParams.archivesIdentifier"
              placeholder="请输入档案馆代码"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="全宗名称" prop="fondsName">
            <el-input
              v-model="queryParams.fondsName"
              placeholder="请输入全宗名称"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="立档单位名称" prop="fondsConstitutingUnitName">
            <el-input
              v-model="queryParams.fondsConstitutingUnitName"
              placeholder="请输入立档单位名称"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="电子文件号" prop="electronicRecordCode">
            <el-input
              v-model="queryParams.electronicRecordCode"
              placeholder="请输入电子文件号"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="档号" prop="archivalCode">
            <el-input
              v-model="queryParams.archivalCode"
              placeholder="请输入档号"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <!--      <el-form-item label="全宗号" prop="fondsIdentifier">-->
          <!--        <el-input-->
          <!--          v-model="queryParams.fondsIdentifier"-->
          <!--          placeholder="请输入全宗号"-->
          <!--          clearable-->
          <!--          size="small"-->
          <!--          @keyup.enter.native="handleQuery"-->
          <!--        />-->
          <!--      </el-form-item>-->
          <!--      <el-form-item label="目录号" prop="catalogueNumber">-->
          <!--        <el-input-->
          <!--          v-model="queryParams.catalogueNumber"-->
          <!--          placeholder="请输入目录号"-->
          <!--          clearable-->
          <!--          size="small"-->
          <!--          @keyup.enter.native="handleQuery"-->
          <!--        />-->
          <!--      </el-form-item>-->
          <!--      <el-form-item label="年度" prop="year">-->
          <!--        <el-input-->
          <!--          v-model="queryParams.year"-->
          <!--          placeholder="请输入年度"-->
          <!--          clearable-->
          <!--          size="small"-->
          <!--          @keyup.enter.native="handleQuery"-->
          <!--        />-->
          <!--      </el-form-item>-->
          <!--      <el-form-item label="保管期限" prop="retentionPeriod">-->
          <!--        <el-select v-model="queryParams.retentionPeriod" placeholder="请选择保管期限" clearable size="small">-->
          <!--          <el-option-->
          <!--            v-for="dict in retentionPeriodOptions"-->
          <!--            :key="dict.dictValue"-->
          <!--            :label="dict.dictLabel"-->
          <!--            :value="dict.dictValue"-->
          <!--          />-->
          <!--        </el-select>-->
          <!--      </el-form-item>-->
          <!--      <el-form-item label="机构或问题" prop="organizationalStructureOrFunction">-->
          <!--        <el-input-->
          <!--          v-model="queryParams.organizationalStructureOrFunction"-->
          <!--          placeholder="请输入机构或问题"-->
          <!--          clearable-->
          <!--          size="small"-->
          <!--          @keyup.enter.native="handleQuery"-->
          <!--        />-->
          <!--      </el-form-item>-->
          <!--      <el-form-item label="类别号" prop="categoryCode">-->
          <!--        <el-input-->
          <!--          v-model="queryParams.categoryCode"-->
          <!--          placeholder="请输入类别号"-->
          <!--          clearable-->
          <!--          size="small"-->
          <!--          @keyup.enter.native="handleQuery"-->
          <!--        />-->
          <!--      </el-form-item>-->
          <!--      <el-form-item label="室编案卷号" prop="agencyFileNumber">-->
          <!--        <el-input-->
          <!--          v-model="queryParams.agencyFileNumber"-->
          <!--          placeholder="请输入室编案卷号"-->
          <!--          clearable-->
          <!--          size="small"-->
          <!--          @keyup.enter.native="handleQuery"-->
          <!--        />-->
          <!--      </el-form-item>-->
          <!--      <el-form-item label="馆编案卷号" prop="archivesFileNumber">-->
          <!--        <el-input-->
          <!--          v-model="queryParams.archivesFileNumber"-->
          <!--          placeholder="请输入馆编案卷号"-->
          <!--          clearable-->
          <!--          size="small"-->
          <!--          @keyup.enter.native="handleQuery"-->
          <!--        />-->
          <!--      </el-form-item>-->
          <!--      <el-form-item label="室编件号" prop="agencyItemNumber">-->
          <!--        <el-input-->
          <!--          v-model="queryParams.agencyItemNumber"-->
          <!--          placeholder="请输入室编件号"-->
          <!--          clearable-->
          <!--          size="small"-->
          <!--          @keyup.enter.native="handleQuery"-->
          <!--        />-->
          <!--      </el-form-item>-->
          <!--      <el-form-item label="馆编件号" prop="archivesItemNumber">-->
          <!--        <el-input-->
          <!--          v-model="queryParams.archivesItemNumber"-->
          <!--          placeholder="请输入馆编件号"-->
          <!--          clearable-->
          <!--          size="small"-->
          <!--          @keyup.enter.native="handleQuery"-->
          <!--        />-->
          <!--      </el-form-item>-->
          <!--      <el-form-item label="文档序号" prop="documentSequenceNumber">-->
          <!--        <el-input-->
          <!--          v-model="queryParams.documentSequenceNumber"-->
          <!--          placeholder="请输入文档序号"-->
          <!--          clearable-->
          <!--          size="small"-->
          <!--          @keyup.enter.native="handleQuery"-->
          <!--        />-->
          <!--      </el-form-item>-->
          <el-form-item label="页号" prop="pageNumber">
            <el-input
              v-model="queryParams.pageNumber"
              placeholder="请输入页号"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="关键词" prop="keyword">
            <el-input
              v-model="queryParams.keyword"
              placeholder="请输入关键词"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <!--      <el-form-item label="人名" prop="personalName">-->
          <!--        <el-input-->
          <!--          v-model="queryParams.personalName"-->
          <!--          placeholder="请输入人名"-->
          <!--          clearable-->
          <!--          size="small"-->
          <!--          @keyup.enter.native="handleQuery"-->
          <!--        />-->
          <!--      </el-form-item>-->
          <!--      <el-form-item label="摘要" prop="abstract">-->
          <!--        <el-input-->
          <!--          v-model="queryParams.abstract"-->
          <!--          placeholder="请输入摘要"-->
          <!--          clearable-->
          <!--          size="small"-->
          <!--          @keyup.enter.native="handleQuery"-->
          <!--        />-->
          <!--      </el-form-item>-->
          <!--      <el-form-item label="分类号" prop="classCode">-->
          <!--        <el-input-->
          <!--          v-model="queryParams.classCode"-->
          <!--          placeholder="请输入分类号"-->
          <!--          clearable-->
          <!--          size="small"-->
          <!--          @keyup.enter.native="handleQuery"-->
          <!--        />-->
          <!--      </el-form-item>-->
          <!--      <el-form-item label="文件编号" prop="documentNumber">-->
          <!--        <el-input-->
          <!--          v-model="queryParams.documentNumber"-->
          <!--          placeholder="请输入文件编号"-->
          <!--          clearable-->
          <!--          size="small"-->
          <!--          @keyup.enter.native="handleQuery"-->
          <!--        />-->
          <!--      </el-form-item>-->
          <el-form-item label="责任者" prop="author">
            <el-input
              v-model="queryParams.author"
              placeholder="请输入责任者"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="日期" prop="date">
            <el-date-picker clearable size="small" style="width: 200px"
                            v-model="queryParams.date"
                            type="date"
                            value-format="yyyy-MM-dd"
                            placeholder="选择日期">
            </el-date-picker>
          </el-form-item>
          <!--      <el-form-item label="文种" prop="documentType">-->
          <!--        <el-select v-model="queryParams.documentType" placeholder="请选择文种" clearable size="small">-->
          <!--          <el-option-->
          <!--            v-for="dict in documentTypeOptions"-->
          <!--            :key="dict.dictValue"-->
          <!--            :label="dict.dictLabel"-->
          <!--            :value="dict.dictValue"-->
          <!--          />-->
          <!--        </el-select>-->
          <!--      </el-form-item>-->
          <!--      <el-form-item label="紧急程度" prop="precedence">-->
          <!--        <el-select v-model="queryParams.precedence" placeholder="请选择紧急程度" clearable size="small">-->
          <!--          <el-option-->
          <!--            v-for="dict in precedenceOptions"-->
          <!--            :key="dict.dictValue"-->
          <!--            :label="dict.dictLabel"-->
          <!--            :value="dict.dictValue"-->
          <!--          />-->
          <!--        </el-select>-->
          <!--      </el-form-item>-->
          <!--      <el-form-item label="主送" prop="principalReceiver">-->
          <!--        <el-input-->
          <!--          v-model="queryParams.principalReceiver"-->
          <!--          placeholder="请输入主送"-->
          <!--          clearable-->
          <!--          size="small"-->
          <!--          @keyup.enter.native="handleQuery"-->
          <!--        />-->
          <!--      </el-form-item>-->
          <!--      <el-form-item label="抄送" prop="otherReceivers">-->
          <!--        <el-input-->
          <!--          v-model="queryParams.otherReceivers"-->
          <!--          placeholder="请输入抄送"-->
          <!--          clearable-->
          <!--          size="small"-->
          <!--          @keyup.enter.native="handleQuery"-->
          <!--        />-->
          <!--      </el-form-item>-->
          <!--      <el-form-item label="密级" prop="securityClassification">-->
          <!--        <el-select v-model="queryParams.securityClassification" placeholder="请选择密级" clearable size="small">-->
          <!--          <el-option-->
          <!--            v-for="dict in securityClassificationOptions"-->
          <!--            :key="dict.dictValue"-->
          <!--            :label="dict.dictLabel"-->
          <!--            :value="dict.dictValue"-->
          <!--          />-->
          <!--        </el-select>-->
          <!--      </el-form-item>-->
          <!--      <el-form-item label="保密期限" prop="secrecyPeriod">-->
          <!--        <el-input-->
          <!--          v-model="queryParams.secrecyPeriod"-->
          <!--          placeholder="请输入保密期限"-->
          <!--          clearable-->
          <!--          size="small"-->
          <!--          @keyup.enter.native="handleQuery"-->
          <!--        />-->
          <!--      </el-form-item>-->
          <!--      <el-form-item label="形式特征" prop="formalCharacteristics">-->
          <!--        <el-input-->
          <!--          v-model="queryParams.formalCharacteristics"-->
          <!--          placeholder="请输入形式特征"-->
          <!--          clearable-->
          <!--          size="small"-->
          <!--          @keyup.enter.native="handleQuery"-->
          <!--        />-->
          <!--      </el-form-item>-->
          <!--      <el-form-item label="文件组合类型" prop="documentAggregationType">-->
          <!--        <el-select v-model="queryParams.documentAggregationType" placeholder="请选择文件组合类型" clearable size="small">-->
          <!--          <el-option-->
          <!--            v-for="dict in documentAggregationTypeOptions"-->
          <!--            :key="dict.dictValue"-->
          <!--            :label="dict.dictLabel"-->
          <!--            :value="dict.dictValue"-->
          <!--          />-->
          <!--        </el-select>-->
          <!--      </el-form-item>-->
          <!--      <el-form-item label="件数" prop="totalNumberOfItems">-->
          <!--        <el-input-->
          <!--          v-model="queryParams.totalNumberOfItems"-->
          <!--          placeholder="请输入件数"-->
          <!--          clearable-->
          <!--          size="small"-->
          <!--          @keyup.enter.native="handleQuery"-->
          <!--        />-->
          <!--      </el-form-item>-->
          <!--      <el-form-item label="页数" prop="totalNumberOfPages">-->
          <!--        <el-input-->
          <!--          v-model="queryParams.totalNumberOfPages"-->
          <!--          placeholder="请输入页数"-->
          <!--          clearable-->
          <!--          size="small"-->
          <!--          @keyup.enter.native="handleQuery"-->
          <!--        />-->
          <!--      </el-form-item>-->
          <!--      <el-form-item label="语种" prop="language">-->
          <!--        <el-input-->
          <!--          v-model="queryParams.language"-->
          <!--          placeholder="请输入语种"-->
          <!--          clearable-->
          <!--          size="small"-->
          <!--          @keyup.enter.native="handleQuery"-->
          <!--        />-->
          <!--      </el-form-item>-->
          <!--      <el-form-item label="稿本" prop="manuscriptType">-->
          <!--        <el-select v-model="queryParams.manuscriptType" placeholder="请选择稿本" clearable size="small">-->
          <!--          <el-option-->
          <!--            v-for="dict in manuscriptTypeOptions"-->
          <!--            :key="dict.dictValue"-->
          <!--            :label="dict.dictLabel"-->
          <!--            :value="dict.dictValue"-->
          <!--          />-->
          <!--        </el-select>-->
          <!--      </el-form-item>-->
          <!--      <el-form-item label="知识产权说明" prop="intellectualPropertyStatement">-->
          <!--        <el-input-->
          <!--          v-model="queryParams.intellectualPropertyStatement"-->
          <!--          placeholder="请输入知识产权说明"-->
          <!--          clearable-->
          <!--          size="small"-->
          <!--          @keyup.enter.native="handleQuery"-->
          <!--        />-->
          <!--      </el-form-item>-->
          <!--      <el-form-item label="授权对象" prop="authorizedAgent">-->
          <!--        <el-input-->
          <!--          v-model="queryParams.authorizedAgent"-->
          <!--          placeholder="请输入授权对象"-->
          <!--          clearable-->
          <!--          size="small"-->
          <!--          @keyup.enter.native="handleQuery"-->
          <!--        />-->
          <!--      </el-form-item>-->
          <!--      <el-form-item label="授权行为" prop="permissionAssignment">-->
          <!--        <el-select v-model="queryParams.permissionAssignment" placeholder="请选择授权行为" clearable size="small">-->
          <!--          <el-option-->
          <!--            v-for="dict in permissionAssignmentOptions"-->
          <!--            :key="dict.dictValue"-->
          <!--            :label="dict.dictLabel"-->
          <!--            :value="dict.dictValue"-->
          <!--          />-->
          <!--        </el-select>-->
          <!--      </el-form-item>-->
          <!--      <el-form-item label="控制标识" prop="controlIdentifier">-->
          <!--        <el-select v-model="queryParams.controlIdentifier" placeholder="请选择控制标识" clearable size="small">-->
          <!--          <el-option-->
          <!--            v-for="dict in controlIdentifierOptions"-->
          <!--            :key="dict.dictValue"-->
          <!--            :label="dict.dictLabel"-->
          <!--            :value="dict.dictValue"-->
          <!--          />-->
          <!--        </el-select>-->
          <!--      </el-form-item>-->
          <el-form-item>
            <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>

        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              type="primary"
              icon="el-icon-plus"
              size="mini"
              @click="handleAdd"
              v-hasPermi="['system:metadata:add']"
            >新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="success"
              icon="el-icon-edit"
              size="mini"
              :disabled="single"
              @click="handleUpdate"
              v-hasPermi="['system:metadata:edit']"
            >修改</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="danger"
              icon="el-icon-delete"
              size="mini"
              :disabled="multiple"
              @click="handleDelete"
              v-hasPermi="['system:metadata:remove']"
            >删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="warning"
              icon="el-icon-download"
              size="mini"
              @click="handleExport"
              v-hasPermi="['system:metadata:export']"
            >导出</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="metadataList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="id" align="center" prop="id" />
          <el-table-column label="聚合层次" align="center" prop="aggregationLevel" :formatter="aggregationLevelFormat" />
          <el-table-column label="档案馆名称" align="center" prop="archivesName" />
          <el-table-column label="档案馆代码" align="center" prop="archivesIdentifier" />
          <el-table-column label="全宗名称" align="center" prop="fondsName" />
          <el-table-column label="立档单位名称" align="center" prop="fondsConstitutingUnitName" />
          <el-table-column label="电子文件号" align="center" prop="electronicRecordCode" />
          <el-table-column label="档号" align="center" prop="archivalCode" />
<!--          <el-table-column label="全宗号" align="center" prop="fondsIdentifier" />-->
<!--          <el-table-column label="目录号" align="center" prop="catalogueNumber" />-->
<!--          <el-table-column label="年度" align="center" prop="year" />-->
<!--          <el-table-column label="保管期限" align="center" prop="retentionPeriod" :formatter="retentionPeriodFormat" />-->
<!--          <el-table-column label="机构或问题" align="center" prop="organizationalStructureOrFunction" />-->
<!--          <el-table-column label="类别号" align="center" prop="categoryCode" />-->
<!--          <el-table-column label="室编案卷号" align="center" prop="agencyFileNumber" />-->
<!--          <el-table-column label="馆编案卷号" align="center" prop="archivesFileNumber" />-->
<!--          <el-table-column label="室编件号" align="center" prop="agencyItemNumber" />-->
<!--          <el-table-column label="馆编件号" align="center" prop="archivesItemNumber" />-->
<!--          <el-table-column label="文档序号" align="center" prop="documentSequenceNumber" />-->
<!--          <el-table-column label="页号" align="center" prop="pageNumber" />-->
          <el-table-column label="题名" align="center" prop="title" />
          <el-table-column label="并列题名" align="center" prop="parallelTitle" />
          <el-table-column label="副题名" align="center" prop="alternativeTitle" />
          <el-table-column label="说明题名文字" align="center" prop="otherTitleInformation" />
          <el-table-column label="主题词" align="center" prop="descriptor" />
          <el-table-column label="关键词" align="center" prop="keyword" />
          <el-table-column label="人名" align="center" prop="personalName" />
          <el-table-column label="摘要" align="center" prop="abstract" />
          <el-table-column label="分类号" align="center" prop="classCode" />
          <el-table-column label="文件编号" align="center" prop="documentNumber" />
          <el-table-column label="责任者" align="center" prop="author" />
          <el-table-column label="日期" align="center" prop="date" />
          <el-table-column label="文种" align="center" prop="documentType" :formatter="documentTypeFormat" />
          <el-table-column label="紧急程度" align="center" prop="precedence" :formatter="precedenceFormat" />
          <el-table-column label="主送" align="center" prop="principalReceiver" />
          <el-table-column label="抄送" align="center" prop="otherReceivers" />
          <el-table-column label="密级" align="center" prop="securityClassification" :formatter="securityClassificationFormat" />
          <el-table-column label="保密期限" align="center" prop="secrecyPeriod" />
          <el-table-column label="形式特征" align="center" prop="formalCharacteristics" />
          <el-table-column label="文件组合类型" align="center" prop="documentAggregationType" :formatter="documentAggregationTypeFormat" />
          <el-table-column label="件数" align="center" prop="totalNumberOfItems" />
          <el-table-column label="页数" align="center" prop="totalNumberOfPages" />
          <el-table-column label="语种" align="center" prop="language" />
          <el-table-column label="稿本" align="center" prop="manuscriptType" :formatter="manuscriptTypeFormat" />
<!--          <el-table-column label="知识产权说明" align="center" prop="intellectualPropertyStatement" />-->
<!--          <el-table-column label="授权对象" align="center" prop="authorizedAgent" />-->
<!--          <el-table-column label="授权行为" align="center" prop="permissionAssignment" :formatter="permissionAssignmentFormat" />-->
<!--          <el-table-column label="控制标识" align="center" prop="controlIdentifier" :formatter="controlIdentifierFormat" />-->
<!--          <el-table-column label="附注" align="center" prop="annotation" />-->
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['system:metadata:edit']"
              >修改</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['system:metadata:remove']"
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
      </el-col>
    </el-row>


    <!-- 添加或修改文书类基本元数据对话框 -->
<!--    <el-dialog :title="title" :visible.sync="open" width="1500px" append-to-body>-->
<!--      <el-form ref="form" :model="form" :rules="rules" label-width="80px">-->
<!--        <el-form-item label="聚合层次" prop="aggregationLevel">-->
<!--          <el-select v-model="form.aggregationLevel" placeholder="请选择聚合层次">-->
<!--            <el-option-->
<!--              v-for="dict in aggregationLevelOptions"-->
<!--              :key="dict.dictValue"-->
<!--              :label="dict.dictLabel"-->
<!--              :value="dict.dictValue"-->
<!--            ></el-option>-->
<!--          </el-select>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="档案馆名称" prop="archivesName">-->
<!--          <el-input v-model="form.archivesName" placeholder="请输入档案馆名称" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="档案馆代码" prop="archivesIdentifier">-->
<!--          <el-input v-model="form.archivesIdentifier" placeholder="请输入档案馆代码" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="全宗名称" prop="fondsName">-->
<!--          <el-input v-model="form.fondsName" placeholder="请输入全宗名称" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="立档单位名称" prop="fondsConstitutingUnitName">-->
<!--          <el-input v-model="form.fondsConstitutingUnitName" placeholder="请输入立档单位名称" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="电子文件号" prop="electronicRecordCode">-->
<!--          <el-input v-model="form.electronicRecordCode" placeholder="请输入电子文件号" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="档号" prop="archivalCode">-->
<!--          <el-input v-model="form.archivalCode" placeholder="请输入档号" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="全宗号" prop="fondsIdentifier">-->
<!--          <el-input v-model="form.fondsIdentifier" placeholder="请输入全宗号" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="目录号" prop="catalogueNumber">-->
<!--          <el-input v-model="form.catalogueNumber" placeholder="请输入目录号" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="年度" prop="year">-->
<!--          <el-input v-model="form.year" placeholder="请输入年度" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="保管期限" prop="retentionPeriod">-->
<!--          <el-select v-model="form.retentionPeriod" placeholder="请选择保管期限">-->
<!--            <el-option-->
<!--              v-for="dict in retentionPeriodOptions"-->
<!--              :key="dict.dictValue"-->
<!--              :label="dict.dictLabel"-->
<!--              :value="dict.dictValue"-->
<!--            ></el-option>-->
<!--          </el-select>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="机构或问题" prop="organizationalStructureOrFunction">-->
<!--          <el-input v-model="form.organizationalStructureOrFunction" placeholder="请输入机构或问题" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="类别号" prop="categoryCode">-->
<!--          <el-input v-model="form.categoryCode" placeholder="请输入类别号" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="室编案卷号" prop="agencyFileNumber">-->
<!--          <el-input v-model="form.agencyFileNumber" placeholder="请输入室编案卷号" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="馆编案卷号" prop="archivesFileNumber">-->
<!--          <el-input v-model="form.archivesFileNumber" placeholder="请输入馆编案卷号" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="室编件号" prop="agencyItemNumber">-->
<!--          <el-input v-model="form.agencyItemNumber" placeholder="请输入室编件号" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="馆编件号" prop="archivesItemNumber">-->
<!--          <el-input v-model="form.archivesItemNumber" placeholder="请输入馆编件号" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="文档序号" prop="documentSequenceNumber">-->
<!--          <el-input v-model="form.documentSequenceNumber" placeholder="请输入文档序号" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="页号" prop="pageNumber">-->
<!--          <el-input v-model="form.pageNumber" placeholder="请输入页号" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="内容描述" prop="contentDescription">-->
<!--          <el-input v-model="form.contentDescription" type="textarea" placeholder="请输入内容" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="题名" prop="title">-->
<!--          <el-input v-model="form.title" type="textarea" placeholder="请输入内容" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="并列题名" prop="parallelTitle">-->
<!--          <el-input v-model="form.parallelTitle" type="textarea" placeholder="请输入内容" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="副题名" prop="alternativeTitle">-->
<!--          <el-input v-model="form.alternativeTitle" type="textarea" placeholder="请输入内容" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="说明题名文字" prop="otherTitleInformation">-->
<!--          <el-input v-model="form.otherTitleInformation" type="textarea" placeholder="请输入内容" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="主题词" prop="descriptor">-->
<!--          <el-input v-model="form.descriptor" type="textarea" placeholder="请输入内容" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="关键词" prop="keyword">-->
<!--          <el-input v-model="form.keyword" placeholder="请输入关键词" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="人名" prop="personalName">-->
<!--          <el-input v-model="form.personalName" placeholder="请输入人名" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="摘要" prop="abstract">-->
<!--          <el-input v-model="form.abstract" placeholder="请输入摘要" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="分类号" prop="classCode">-->
<!--          <el-input v-model="form.classCode" placeholder="请输入分类号" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="文件编号" prop="documentNumber">-->
<!--          <el-input v-model="form.documentNumber" placeholder="请输入文件编号" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="责任者" prop="author">-->
<!--          <el-input v-model="form.author" placeholder="请输入责任者" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="日期" prop="date">-->
<!--          <el-date-picker clearable size="small" style="width: 200px"-->
<!--                          v-model="queryParams.date"-->
<!--                          type="date"-->
<!--                          value-format="yyyy-MM-dd"-->
<!--                          placeholder="选择日期">-->
<!--          </el-date-picker>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="文种" prop="documentType">-->
<!--          <el-select v-model="form.documentType" placeholder="请选择文种">-->
<!--            <el-option-->
<!--              v-for="dict in documentTypeOptions"-->
<!--              :key="dict.dictValue"-->
<!--              :label="dict.dictLabel"-->
<!--              :value="dict.dictValue"-->
<!--            ></el-option>-->
<!--          </el-select>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="紧急程度" prop="precedence">-->
<!--          <el-select v-model="form.precedence" placeholder="请选择紧急程度">-->
<!--            <el-option-->
<!--              v-for="dict in precedenceOptions"-->
<!--              :key="dict.dictValue"-->
<!--              :label="dict.dictLabel"-->
<!--              :value="dict.dictValue"-->
<!--            ></el-option>-->
<!--          </el-select>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="主送" prop="principalReceiver">-->
<!--          <el-input v-model="form.principalReceiver" placeholder="请输入主送" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="抄送" prop="otherReceivers">-->
<!--          <el-input v-model="form.otherReceivers" placeholder="请输入抄送" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="密级" prop="securityClassification">-->
<!--          <el-select v-model="form.securityClassification" placeholder="请选择密级">-->
<!--            <el-option-->
<!--              v-for="dict in securityClassificationOptions"-->
<!--              :key="dict.dictValue"-->
<!--              :label="dict.dictLabel"-->
<!--              :value="dict.dictValue"-->
<!--            ></el-option>-->
<!--          </el-select>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="保密期限" prop="secrecyPeriod">-->
<!--          <el-input v-model="form.secrecyPeriod" placeholder="请输入保密期限" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="形式特征" prop="formalCharacteristics">-->
<!--          <el-input v-model="form.formalCharacteristics" placeholder="请输入形式特征" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="文件组合类型" prop="documentAggregationType">-->
<!--          <el-select v-model="form.documentAggregationType" placeholder="请选择文件组合类型">-->
<!--            <el-option-->
<!--              v-for="dict in documentAggregationTypeOptions"-->
<!--              :key="dict.dictValue"-->
<!--              :label="dict.dictLabel"-->
<!--              :value="dict.dictValue"-->
<!--            ></el-option>-->
<!--          </el-select>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="件数" prop="totalNumberOfItems">-->
<!--          <el-input v-model="form.totalNumberOfItems" placeholder="请输入件数" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="页数" prop="totalNumberOfPages">-->
<!--          <el-input v-model="form.totalNumberOfPages" placeholder="请输入页数" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="语种" prop="language">-->
<!--          <el-input v-model="form.language" placeholder="请输入语种" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="稿本" prop="manuscriptType">-->
<!--          <el-select v-model="form.manuscriptType" placeholder="请选择稿本">-->
<!--            <el-option-->
<!--              v-for="dict in manuscriptTypeOptions"-->
<!--              :key="dict.dictValue"-->
<!--              :label="dict.dictLabel"-->
<!--              :value="dict.dictValue"-->
<!--            ></el-option>-->
<!--          </el-select>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="知识产权说明" prop="intellectualPropertyStatement">-->
<!--          <el-input v-model="form.intellectualPropertyStatement" placeholder="请输入知识产权说明" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="授权对象" prop="authorizedAgent">-->
<!--          <el-input v-model="form.authorizedAgent" placeholder="请输入授权对象" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="授权行为" prop="permissionAssignment">-->
<!--          <el-select v-model="form.permissionAssignment" placeholder="请选择授权行为">-->
<!--            <el-option-->
<!--              v-for="dict in permissionAssignmentOptions"-->
<!--              :key="dict.dictValue"-->
<!--              :label="dict.dictLabel"-->
<!--              :value="dict.dictValue"-->
<!--            ></el-option>-->
<!--          </el-select>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="控制标识" prop="controlIdentifier">-->
<!--          <el-select v-model="form.controlIdentifier" placeholder="请选择控制标识">-->
<!--            <el-option-->
<!--              v-for="dict in controlIdentifierOptions"-->
<!--              :key="dict.dictValue"-->
<!--              :label="dict.dictLabel"-->
<!--              :value="dict.dictValue"-->
<!--            ></el-option>-->
<!--          </el-select>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="附注" prop="annotation">-->
<!--          <el-input v-model="form.annotation" type="textarea" placeholder="请输入内容" />-->
<!--        </el-form-item>-->
<!--      </el-form>-->
<!--      <div slot="footer" class="dialog-footer">-->
<!--        <el-button type="primary" @click="submitForm">确 定</el-button>-->
<!--        <el-button @click="cancel">取 消</el-button>-->
<!--      </div>-->
<!--    </el-dialog>-->
    <el-dialog :title="title" :visible.sync="open" width="1500px" append-to-body>
      <InputView v-if="open" :formconf="formConf"/>
      <el-button @click="cancel">
        取消
      </el-button>
    </el-dialog>
  </div>
</template>

<script>
import { listMetadata, getMetadata, delMetadata, addMetadata, updateMetadata, exportMetadata } from "@/api/system/metadata";
import DeptTree from '@/views/components/deptTree'
import InputView from '@/views/system/metadata/InputView'
import { listJson } from '@/api/system/json'

export default {
  name: "Metadata",
  components: {
    DeptTree,
    InputView
  },
  data() {
    return {
      formConf:{},
      //默认部门ID
      deptId:100,
      //部门名称
      deptName: undefined,
      // 部门树选项
      deptOptions:undefined,
      defaultProps: {
        children: "children",
        label: "label"
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
      // 文书类基本元数据表格数据
      metadataList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 聚合层次字典
      aggregationLevelOptions: [],
      // 保管期限字典
      retentionPeriodOptions: [],
      // 文种字典
      documentTypeOptions: [],
      // 紧急程度字典
      precedenceOptions: [],
      // 密级字典
      securityClassificationOptions: [],
      // 文件组合类型字典
      documentAggregationTypeOptions: [],
      // 稿本字典
      manuscriptTypeOptions: [],
      // 授权行为字典
      permissionAssignmentOptions: [],
      // 控制标识字典
      controlIdentifierOptions: [],
      // 查询参数
      queryParams: {
        deptId:null,
        pageNum: 1,
        pageSize: 10,
        aggregationLevel: null,
        archivesName: null,
        archivesIdentifier: null,
        fondsName: null,
        fondsConstitutingUnitName: null,
        electronicRecordCode: null,
        archivalCode: null,
        fondsIdentifier: null,
        catalogueNumber: null,
        year: null,
        retentionPeriod: null,
        organizationalStructureOrFunction: null,
        categoryCode: null,
        agencyFileNumber: null,
        archivesFileNumber: null,
        agencyItemNumber: null,
        archivesItemNumber: null,
        documentSequenceNumber: null,
        pageNumber: null,
        contentDescription: null,
        title: null,
        parallelTitle: null,
        alternativeTitle: null,
        otherTitleInformation: null,
        descriptor: null,
        keyword: null,
        personalName: null,
        abstract: null,
        classCode: null,
        documentNumber: null,
        author: null,
        date: null,
        documentType: null,
        precedence: null,
        principalReceiver: null,
        otherReceivers: null,
        securityClassification: null,
        secrecyPeriod: null,
        formalCharacteristics: null,
        documentAggregationType: null,
        totalNumberOfItems: null,
        totalNumberOfPages: null,
        language: null,
        manuscriptType: null,
        // intellectualPropertyStatement: null,
        // authorizedAgent: null,
        // permissionAssignment: null,
        // controlIdentifier: null,
        annotation: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        aggregationLevel: [
          { required: true, message: "聚合层次不能为空", trigger: "change" }
        ],
        archivesName: [
          { required: true, message: "档案馆名称不能为空", trigger: "blur" }
        ],
        archivesIdentifier: [
          { required: true, message: "档案馆代码不能为空", trigger: "blur" }
        ],
        fondsName: [
          { required: true, message: "全宗名称不能为空", trigger: "blur" }
        ],
        fondsConstitutingUnitName: [
          { required: true, message: "立档单位名称不能为空", trigger: "blur" }
        ],
        electronicRecordCode: [
          { required: true, message: "电子文件号不能为空", trigger: "blur" }
        ],
        archivalCode: [
          { required: true, message: "档号不能为空", trigger: "blur" }
        ],
        fondsIdentifier: [
          { required: true, message: "全宗号不能为空", trigger: "blur" }
        ],
        catalogueNumber: [
          { required: true, message: "目录号不能为空", trigger: "blur" }
        ],
        year: [
          { required: true, message: "年度不能为空", trigger: "blur" }
        ],
        retentionPeriod: [
          { required: true, message: "保管期限不能为空", trigger: "change" }
        ],
        organizationalStructureOrFunction: [
          { required: true, message: "机构或问题不能为空", trigger: "blur" }
        ],
        categoryCode: [
          { required: true, message: "类别号不能为空", trigger: "blur" }
        ],
        agencyFileNumber: [
          { required: true, message: "室编案卷号不能为空", trigger: "blur" }
        ],
        archivesFileNumber: [
          { required: true, message: "馆编案卷号不能为空", trigger: "blur" }
        ],
        agencyItemNumber: [
          { required: true, message: "室编件号不能为空", trigger: "blur" }
        ],
        archivesItemNumber: [
          { required: true, message: "馆编件号不能为空", trigger: "blur" }
        ],
        documentSequenceNumber: [
          { required: true, message: "文档序号不能为空", trigger: "blur" }
        ],
        pageNumber: [
          { required: true, message: "页号不能为空", trigger: "blur" }
        ],
        contentDescription: [
          { required: true, message: "内容描述不能为空", trigger: "blur" }
        ],
        title: [
          { required: true, message: "题名不能为空", trigger: "blur" }
        ],
        parallelTitle: [
          { required: true, message: "并列题名不能为空", trigger: "blur" }
        ],
        alternativeTitle: [
          { required: true, message: "副题名不能为空", trigger: "blur" }
        ],
        otherTitleInformation: [
          { required: true, message: "说明题名文字不能为空", trigger: "blur" }
        ],
        descriptor: [
          { required: true, message: "主题词不能为空", trigger: "blur" }
        ],
        keyword: [
          { required: true, message: "关键词不能为空", trigger: "blur" }
        ],
        personalName: [
          { required: true, message: "人名不能为空", trigger: "blur" }
        ],
        abstract: [
          { required: true, message: "摘要不能为空", trigger: "blur" }
        ],
        classCode: [
          { required: true, message: "分类号不能为空", trigger: "blur" }
        ],
        documentNumber: [
          { required: true, message: "文件编号不能为空", trigger: "blur" }
        ],
        author: [
          { required: true, message: "责任者不能为空", trigger: "blur" }
        ],
        date: [
          { required: false, message: "日期不能为空", trigger: "blur" }
        ],
        documentType: [
          { required: true, message: "文种不能为空", trigger: "change" }
        ],
        precedence: [
          { required: true, message: "紧急程度不能为空", trigger: "change" }
        ],
        principalReceiver: [
          { required: true, message: "主送不能为空", trigger: "blur" }
        ],
        otherReceivers: [
          { required: true, message: "抄送不能为空", trigger: "blur" }
        ],
        securityClassification: [
          { required: true, message: "密级不能为空", trigger: "change" }
        ],
        secrecyPeriod: [
          { required: true, message: "保密期限不能为空", trigger: "blur" }
        ],
        formalCharacteristics: [
          { required: true, message: "形式特征不能为空", trigger: "blur" }
        ],
        documentAggregationType: [
          { required: true, message: "文件组合类型不能为空", trigger: "change" }
        ],
        totalNumberOfItems: [
          { required: true, message: "件数不能为空", trigger: "blur" }
        ],
        totalNumberOfPages: [
          { required: true, message: "页数不能为空", trigger: "blur" }
        ],
        language: [
          { required: true, message: "语种不能为空", trigger: "blur" }
        ],
        manuscriptType: [
          { required: true, message: "稿本不能为空", trigger: "change" }
        ],
        // intellectualPropertyStatement: [
        //   { required: true, message: "知识产权说明不能为空", trigger: "blur" }
        // ],
        // authorizedAgent: [
        //   { required: true, message: "授权对象不能为空", trigger: "blur" }
        // ],
        // permissionAssignment: [
        //   { required: true, message: "授权行为不能为空", trigger: "change" }
        // ],
        // controlIdentifier: [
        //   { required: true, message: "控制标识不能为空", trigger: "change" }
        // ],
        annotation: [
          { required: true, message: "附注不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("ws_al").then(response => {
      this.aggregationLevelOptions = response.data;
    });
    this.getDicts("sys_rp").then(response => {
      this.retentionPeriodOptions = response.data;
    });
    this.getDicts("document_type").then(response => {
      this.documentTypeOptions = response.data;
    });
    this.getDicts("precedence").then(response => {
      this.precedenceOptions = response.data;
    });
    this.getDicts("security_classification").then(response => {
      this.securityClassificationOptions = response.data;
    });
    this.getDicts("document_aggregation_type").then(response => {
      this.documentAggregationTypeOptions = response.data;
    });
    this.getDicts("manuscript_type").then(response => {
      this.manuscriptTypeOptions = response.data;
    });
    this.getDicts("permission_assignment").then(response => {
      this.permissionAssignmentOptions = response.data;
    });
    this.getDicts("control_identifier").then(response => {
      this.controlIdentifierOptions = response.data;
    });
  },
  methods: {
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.deptId = data;
      this.getList();
    },
    /** 查询文书类基本元数据列表 */
    getList() {
      this.loading = true;
      listMetadata(this.queryParams).then(response => {
        this.metadataList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 聚合层次字典翻译
    aggregationLevelFormat(row, column) {
      return this.selectDictLabel(this.aggregationLevelOptions, row.aggregationLevel);
    },
    // 保管期限字典翻译
    retentionPeriodFormat(row, column) {
      return this.selectDictLabel(this.retentionPeriodOptions, row.retentionPeriod);
    },
    // 文种字典翻译
    documentTypeFormat(row, column) {
      return this.selectDictLabel(this.documentTypeOptions, row.documentType);
    },
    // 紧急程度字典翻译
    precedenceFormat(row, column) {
      return this.selectDictLabel(this.precedenceOptions, row.precedence);
    },
    // 密级字典翻译
    securityClassificationFormat(row, column) {
      return this.selectDictLabel(this.securityClassificationOptions, row.securityClassification);
    },
    // 文件组合类型字典翻译
    documentAggregationTypeFormat(row, column) {
      return this.selectDictLabel(this.documentAggregationTypeOptions, row.documentAggregationType);
    },
    // 稿本字典翻译
    manuscriptTypeFormat(row, column) {
      return this.selectDictLabel(this.manuscriptTypeOptions, row.manuscriptType);
    },
    // 授权行为字典翻译
    permissionAssignmentFormat(row, column) {
      return this.selectDictLabel(this.permissionAssignmentOptions, row.permissionAssignment);
    },
    // 控制标识字典翻译
    controlIdentifierFormat(row, column) {
      return this.selectDictLabel(this.controlIdentifierOptions, row.controlIdentifier);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      //this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        aggregationLevel: null,
        archivesName: null,
        archivesIdentifier: null,
        fondsName: null,
        fondsConstitutingUnitName: null,
        electronicRecordCode: null,
        archivalCode: null,
        fondsIdentifier: null,
        catalogueNumber: null,
        year: null,
        retentionPeriod: null,
        organizationalStructureOrFunction: null,
        categoryCode: null,
        agencyFileNumber: null,
        archivesFileNumber: null,
        agencyItemNumber: null,
        archivesItemNumber: null,
        documentSequenceNumber: null,
        pageNumber: null,
        contentDescription: null,
        title: null,
        parallelTitle: null,
        alternativeTitle: null,
        otherTitleInformation: null,
        descriptor: null,
        keyword: null,
        personalName: null,
        abstract: null,
        classCode: null,
        documentNumber: null,
        author: null,
        date: null,
        documentType: null,
        precedence: null,
        principalReceiver: null,
        otherReceivers: null,
        securityClassification: null,
        secrecyPeriod: null,
        formalCharacteristics: null,
        documentAggregationType: null,
        totalNumberOfItems: null,
        totalNumberOfPages: null,
        language: null,
        manuscriptType: null,
        intellectualPropertyStatement: null,
        authorizedAgent: null,
        permissionAssignment: null,
        controlIdentifier: null,
        annotation: null
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
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    loadForm(){
      let query = {id:'',parentName: '999' ,node:'888'}
      listJson(query).then(res => {
        this.formConf=JSON.parse(res.rows[0].formData)
        console.log(this.formConf)
      }).catch(err=>{
        console.log(err)
      })
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.loadForm();
      this.open = true;
      this.title = "添加文书类基本元数据";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getMetadata(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改文书类基本元数据";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateMetadata(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addMetadata(this.form).then(response => {
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
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除文书类基本元数据编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delMetadata(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有文书类基本元数据数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportMetadata(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
    }
  }
};
</script>
