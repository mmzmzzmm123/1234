<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="120px"
    >
      <el-form-item label="姓名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入教师姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="性别" prop="xb">
        <el-select
          v-model="queryParams.xb"
          placeholder="请选择性别"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in xbOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="民族" prop="mz">
        <el-select
          v-model="queryParams.mz"
          placeholder="请选择民族"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in mzOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="第一学历" prop="dyxl">
        <el-select
          v-model="queryParams.dyxl"
          placeholder="请选择第一学历"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in dyxlOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="学历" prop="xl">
        <el-select
          v-model="queryParams.xl"
          placeholder="请选择学历"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in xlOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="学位" prop="xw">
        <el-select
          v-model="queryParams.xw"
          placeholder="请选择学位"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in xwOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="职称" prop="zc">
        <el-select
          v-model="queryParams.zc"
          placeholder="请选择职称"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in zcOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="单位名称" prop="deptId">
        <el-select v-model="form.deptId" placeholder="请选择单位名称">
                <el-option
                  v-for="dict in deptOptions"
                  :key="dict.deptId"
                  :label="dict.deptName"
                  :value="dict.deptId"
                ></el-option>
              </el-select>
      </el-form-item> -->
      <el-form-item label="行政职务" prop="xrxzzw">
        <el-select
          v-model="queryParams.xrxzzw"
          placeholder="请选择现任行政职务"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in xrxzzwOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="党内职务" prop="xrdnzw">
        <el-select
          v-model="queryParams.xrdnzw"
          placeholder="请选择现任党内职务"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in xrdnzwOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="群团职务" prop="xrqtzw">
        <el-select
          v-model="queryParams.xrqtzw"
          placeholder="请选择现任群团职务"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in xrqtzwOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="校长职级" prop="xrxzzj">
        <el-select
          v-model="queryParams.xrxzzj"
          placeholder="请选择现任校长职级"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in xrxzzjOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="身份证号" prop="sfzh">
        <el-input
          v-model="queryParams.sfzh"
          placeholder="请输入身份证号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!-- <el-form-item label="出生日期" prop="csrq">
        <el-date-picker
          clearable
          size="small"
          style="width: 200px"
          v-model="queryParams.csrq"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择出生日期"
        >
        </el-date-picker>
      </el-form-item> -->
      <el-form-item label="教育类型" prop="jylx">
        <el-select
          v-model="queryParams.jylx"
          placeholder="请选择教育类型"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in jylxOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="驾照情况" prop="jzcyqk">
        <el-select
          v-model="queryParams.jzcyqk"
          placeholder="请选择驾照持有情况"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in jzcyqkOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="能否开车" prop="nfkc">
        <el-select
          v-model="queryParams.nfkc"
          placeholder="请选择能否开车"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in nfkcOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="婚姻状况" prop="hyzk">
        <el-select
          v-model="queryParams.hyzk"
          placeholder="请选择婚姻状况"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in hyzkOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="政治面貌" prop="zzmm">
        <el-select
          v-model="queryParams.zzmm"
          placeholder="请选择政治面貌"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in zzmmOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="手机号码" prop="phone">
        <el-input
          v-model="queryParams.phone"
          placeholder="请输入手机号码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="任教学科" prop="rjxk">
        <el-select
          v-model="queryParams.rjxk"
          placeholder="请选择任教学科"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in rjxkOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="健康状况" prop="jkzk">
        <el-select
          v-model="queryParams.jkzk"
          placeholder="请选择健康状况"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in jkzkOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="参加工作年月" prop="cjgzny">
        <el-date-picker
          clearable
          size="small"
          style="width: 200px"
          v-model="queryParams.cjgzny"
          type="month"
          value-format="yyyy-MM"
          placeholder="选择参加工作年月"
        >
        </el-date-picker>
      </el-form-item> -->
      <el-form-item label="是否后备干部" prop="sfhbgb">
        <el-select
          v-model="queryParams.sfhbgb"
          placeholder="请选择是否后备干部"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in sfhbgbOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button
          type="cyan"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >重置</el-button
        >
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['gbxxgl:gbjbqk:add']"
          >新增</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['gbxxgl:gbjbqk:edit']"
          >修改</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['gbxxgl:gbjbqk:remove']"
          >删除</el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="gbjbqkList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column
        label="单位名称"
        align="center"
        prop="dept.deptName"
      />
      <el-table-column label="教师姓名" align="center" prop="name" />
      <el-table-column
        label="性别"
        align="center"
        prop="xb"
        :formatter="xbFormat"
      />
      <el-table-column
        label="现任行政职务"
        align="center"
        prop="xrxzzw"
        :formatter="xrxzzwFormat"
      />
      <el-table-column label="出生日期" align="center" prop="csrq">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.csrq, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="参加工作年月" align="center" prop="cjgzny">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.cjgzny, "{y}-{m}") }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="职称"
        align="center"
        prop="zc"
        :formatter="zcFormat"
      />
      <el-table-column
        label="政治面貌"
        align="center"
        prop="zzmm"
        :formatter="zzmmFormat"
      />
      <el-table-column
        label="民族"
        align="center"
        prop="mz"
        :formatter="mzFormat"
      />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
        width="210px"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleCheck(scope.row)"
            v-hasPermi="['gbxxgl:gbjbqk:query']"
            >查看</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['gbxxgl:gbjbqk:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['gbxxgl:gbjbqk:remove']"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改干部基本情况对话框 -->
    <el-dialog class="big-dialog" :title="title" :visible.sync="open" width="980px">
      <el-form ref="form" :model="form" :rules="rules" label-width="140px">
        <el-row :gutter="10">
          <el-col :span="12">
            <el-form-item label="干部姓名" prop="name">
              <el-input v-model="form.name" placeholder="请输入干部姓名" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="干部性别" prop="xb">
              <el-select v-model="form.xb" placeholder="请选择性别">
                <el-option
                  v-for="dict in xbOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="干部民族" prop="mz">
              <el-select v-model="form.mz" placeholder="请选择民族">
                <el-option
                  v-for="dict in mzOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="第一学历" prop="dyxl">
              <el-select v-model="form.dyxl" placeholder="请选择第一学历">
                <el-option
                  v-for="dict in dyxlOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="干部学历" prop="xl">
              <el-select v-model="form.xl" placeholder="请选择学历">
                <el-option
                  v-for="dict in xlOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="干部学位" prop="xw">
              <el-select v-model="form.xw" placeholder="请选择学位">
                <el-option
                  v-for="dict in xwOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="干部职称" prop="zc">
              <el-select v-model="form.zc" placeholder="请选择职称">
                <el-option
                  v-for="dict in zcOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="手机号码" prop="phone">
              <el-input v-model="form.phone" placeholder="请输入手机号码" />
            </el-form-item>
          </el-col>
          <el-row>
            <el-col :span="24">
              <el-form-item label="干部籍贯" prop="jg">
                <v-distpicker
                  v-model="form.jg"
                  :placeholders="placeholders"
                  :province="diglogForm.province"
                  :city="diglogForm.city"
                  :area="diglogForm.area"
                  @selected="onSelected_jg"
                ></v-distpicker>
                <el-input v-model="form.jgProvince" v-if="false" />
                <el-input v-model="form.jgCity" v-if="false" />
                <el-input v-model="form.jgArea" v-if="false" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <el-form-item label="干部出生地" prop="csd">
                <v-distpicker
                  v-model="form.csd"
                  :placeholders="placeholders"
                  :province="diglogForm1.province"
                  :city="diglogForm1.city"
                  :area="diglogForm1.area"
                  @selected="onSelected_csd"
                ></v-distpicker>
                <el-input v-model="form.csdProvince" v-if="false" />
                <el-input v-model="form.csdCity" v-if="false" />
                <el-input v-model="form.csdArea" v-if="false" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-col :span="12">
            <el-form-item label="单位名称" prop="deptId">
              <el-select v-model="form.deptId" placeholder="请选择教育类型">
                <el-option
                  v-for="dict in deptOptions"
                  :key="dict.deptId"
                  :label="dict.deptName"
                  :value="dict.deptId"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <!-- <el-col :span="12">
            <el-form-item label="单位简称" prop="dwjc">
              <el-input v-model="form.dwjc" placeholder="请输入单位简称" />
            </el-form-item>
          </el-col> -->
          <el-col :span="12">
            <el-form-item label="单位地址" prop="dwdz">
              <el-input v-model="form.dwdz" placeholder="请输入单位地址" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="家庭住址" prop="jtzz">
              <el-input v-model="form.jtzz" placeholder="请输入家庭住址" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="毕业院校" prop="byyx">
              <el-input v-model="form.byyx" placeholder="请输入毕业院校" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="教育类型" prop="jylx">
              <el-select v-model="form.jylx" placeholder="请选择教育类型">
                <el-option
                  v-for="dict in jylxOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="身份证号" prop="sfzh">
              <el-input v-model="form.sfzh" placeholder="请输入身份证号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="出生日期" prop="csrq">
              <el-date-picker
                clearable
                size="small"
                class="my-date-picker"
                v-model="form.csrq"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="选择出生日期"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="婚姻状况" prop="hyzk">
              <el-select v-model="form.hyzk" placeholder="请选择婚姻状况">
                <el-option
                  v-for="dict in hyzkOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="政治面貌" prop="zzmm">
              <el-select v-model="form.zzmm" placeholder="请选择政治面貌">
                <el-option
                  v-for="dict in zzmmOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="入党时间" prop="rdsj">
              <el-date-picker
                clearable
                size="small"
                v-model="form.rdsj"
                class="my-date-picker"
                type="month"
                value-format="yyyy-MM"
                placeholder="选择入党时间"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="任教学科" prop="rjxk">
              <el-select v-model="form.rjxk" placeholder="请选择任教学科">
                <el-option
                  v-for="dict in rjxkOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="健康状况" prop="jkzk">
              <el-select v-model="form.jkzk" placeholder="请选择健康状况">
                <el-option
                  v-for="dict in jkzkOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="能否开车" prop="nfkc">
              <el-select v-model="form.nfkc" placeholder="请选择能否开车">
                <el-option
                  v-for="dict in nfkcOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="驾照持有情况" prop="jzcyqk">
              <el-select v-model="form.jzcyqk" placeholder="请选择驾照持有情况">
                <el-option
                  v-for="dict in jzcyqkOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="现任行政职务" prop="xrxzzw">
              <el-select v-model="form.xrxzzw" placeholder="请选择现任行政职务">
                <el-option
                  v-for="dict in xrxzzwOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="现任党内职务" prop="xrdnzw">
              <el-select v-model="form.xrdnzw" placeholder="请选择现任党内职务">
                <el-option
                  v-for="dict in xrdnzwOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="现任群团职务" prop="xrqtzw">
              <el-select v-model="form.xrqtzw" placeholder="请选择现任群团职务">
                <el-option
                  v-for="dict in xrqtzwOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="任现职务年月" prop="rxzwny">
              <el-date-picker
                clearable
                size="small"
                class="my-date-picker"
                v-model="form.rxzwny"
                type="month"
                value-format="yyyy-MM"
                placeholder="选择任现职务年月"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="现任校长职级" prop="xrxzzj">
              <el-select v-model="form.xrxzzj" placeholder="请选择现任校长职级">
                <el-option
                  v-for="dict in xrxzzjOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="校长职级确定年月" prop="xzzjqdny">
              <el-date-picker
                clearable
                size="small"
                class="my-date-picker"
                v-model="form.xzzjqdny"
                type="month"
                value-format="yyyy-MM"
                placeholder="选择校长职级确定年月"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否后备干部" prop="sfhbgb">
              <el-select v-model="form.sfhbgb" placeholder="请选择是否后备干部">
                <el-option
                  v-for="dict in sfhbgbOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="后备干部确定年月" prop="hbgbqdny">
              <el-date-picker
                clearable
                size="small"
                class="my-date-picker"
                v-model="form.hbgbqdny"
                type="month"
                value-format="yyyy-MM"
                placeholder="选择后备干部确定年月"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="参加工作年月" prop="cjgzny">
              <el-date-picker
                clearable
                size="small"
                class="my-date-picker"
                v-model="form.cjgzny"
                type="month"
                value-format="yyyy-MM"
                placeholder="选择参加工作年月"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="办公室电话" prop="bgsdh">
              <el-input v-model="form.bgsdh" placeholder="请输入办公室电话" />
            </el-form-item>
          </el-col>
          <el-col :span="12" v-show="isshow">
            <el-form-item label="当前状态" prop="dqzt">
              <el-select v-model="form.dqzt" placeholder="请选择当前状态">
                <el-option
                  v-for="dict in dqztOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="txtimeshow">
            <el-form-item label="退休时间" prop="txTime">
              <el-date-picker
                clearable
                size="small"
                class="my-date-picker"
                v-model="form.txTime"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="选择退休时间"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listGbjbqk,
  getGbjbqk,
  delGbjbqk,
  addGbjbqk,
  updateGbjbqk,
  exportGbjbqk,
} from "@/api/gbxxgl/gbjbqk";
import { listDept, getDept } from "@/api/system/dept";

//导入省市区三级联动库
import VDistpicker from "v-distpicker";

export default {
  name: "Gbjbqk",
  data() {
    return {
      isshow: false,
      txtimeshow: false,
      placeholders: {
        province: "请选择省",
        city: "请选择市",
        area: "请选择区",
      },
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
      //当前状态
      dqztOptions: [],
      // 部门选项
      deptOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        otherid: null,
        name: null,
        xb: null,
        deptId: null,
        dwmc: null,
        dwjc: null,
        xrxzzw: null,
        xrdnzw: null,
        xrqtzw: null,
        rxzwny: null,
        xrxzzj: null,
        xzzjqdny: null,
        sfhbgb: null,
        hbgbqdny: null,
        sfzh: null,
        updatetime: null,
        mz: null,
        jg: null,
        jgProvince: null,
        jgCity: null,
        jgCityname: null,
        jgArea: null,
        jgAreaname: null,
        csd: null,
        csdProvince: null,
        csdCity: null,
        csdCityname: null,
        csdArea: null,
        csdAreaname: null,
        csrq: null,
        cjgzny: null,
        jtzz: null,
        dwdz: null,
        dyxl: null,
        xl: null,
        xw: null,
        byyx: null,
        jylx: null,
        zc: null,
        jzcyqk: null,
        nfkc: null,
        hyzk: null,
        zzmm: null,
        rdsj: null,
        bgsdh: null,
        phone: null,
        rjxk: null,
        jkzk: null,
        createUser: null,
        createTime: null,
        dqzt: "00",
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          { required: true, message: "干部姓名不能为空", trigger: "blur" },
        ],
        phone: [{ required: true, message: "电话不能为空", trigger: "blur" }],
        xb: [{ required: true, message: "性别不能为空", trigger: "blur" }],
        sfhbgb: [
          { required: true, message: "是否储备干部不能为空", trigger: "blur" },
        ],
        dqzt: [
          { required: true, message: "当前状态不能为空", trigger: "blur" },
        ],
        txTime: [
          { required: true, message: "退休时间不能为空", trigger: "blur" },
        ],
      },
    };
  },
  watch: {
    // 监听dqzt
    "form.dqzt": "handleDqztClick",
  },
  created() {
    this.getList();
    this.getDeptList();
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
    this.getDicts("sys_dm_dqzt").then((response) => {
      this.dqztOptions = response.data;
    });
  },
  components: {
    //省市区三级联动全局组件
    VDistpicker,
  },
  methods: {
    //所在省市区触发联动方法
    onSelected_jg(data) {
      // console.log("onSelected_brith=" + data);
      if (
        data.province.code == undefined ||
        data.city.code == undefined ||
        data.area.code == undefined
      ) {
        this.form.jg = undefined;
      } else {
        this.form.jg = data.province.value;
        this.form.jgProvince = data.province.code;
        this.form.jgCityname = data.city.value;
        this.form.jgCity = data.city.code;
        this.form.jgAreaname = data.area.value;
        this.form.jgArea = data.area.code;
      }
    },
    //所在省市区触发联动方法
    onSelected_csd(data) {
      // console.log("onSelected_brith=" + data);
      if (
        data.province.code == undefined ||
        data.city.code == undefined ||
        data.area.code == undefined
      ) {
        this.form.csd = undefined;
      } else {
        this.form.csd = data.province.value;
        this.form.csdProvince = data.province.code;
        this.form.csdCityname = data.city.value;
        this.form.csdCity = data.city.code;
        this.form.csdAreaname = data.area.value;
        this.form.csdArea = data.area.code;
      }
    },
    /** 查询干部基本情况列表 */
    getList() {
      this.loading = true;
      listGbjbqk(this.queryParams).then((response) => {
        this.gbjbqkList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 查询部门
    getDeptList() {
      listDept(null).then((response) => {
        this.deptOptions = response.data;
      });
    },
    // 性别字典翻译
    xbFormat(row, column) {
      return this.selectDictLabel(this.xbOptions, row.xb);
    },
    // 现任行政职务字典翻译
    xrxzzwFormat(row, column) {
      return this.selectDictLabel(this.xrxzzwOptions, row.xrxzzw);
    },
    // 现任党内职务字典翻译
    xrdnzwFormat(row, column) {
      return this.selectDictLabel(this.xrdnzwOptions, row.xrdnzw);
    },
    // 现任群团职务字典翻译
    xrqtzwFormat(row, column) {
      return this.selectDictLabel(this.xrqtzwOptions, row.xrqtzw);
    },
    // 现任校长职级字典翻译
    xrxzzjFormat(row, column) {
      return this.selectDictLabel(this.xrxzzjOptions, row.xrxzzj);
    },
    // 是否后备干部字典翻译
    sfhbgbFormat(row, column) {
      return this.selectDictLabel(this.sfhbgbOptions, row.sfhbgb);
    },
    // 民族字典翻译
    mzFormat(row, column) {
      return this.selectDictLabel(this.mzOptions, row.mz);
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
    // 职称字典翻译
    zcFormat(row, column) {
      return this.selectDictLabel(this.zcOptions, row.zc);
    },
    // 驾照持有情况字典翻译
    jzcyqkFormat(row, column) {
      return this.selectDictLabel(this.jzcyqkOptions, row.jzcyqk);
    },
    // 能否开车字典翻译
    nfkcFormat(row, column) {
      return this.selectDictLabel(this.nfkcOptions, row.nfkc);
    },
    // 婚姻状况字典翻译
    hyzkFormat(row, column) {
      return this.selectDictLabel(this.hyzkOptions, row.hyzk);
    },
    // 政治面貌字典翻译
    zzmmFormat(row, column) {
      return this.selectDictLabel(this.zzmmOptions, row.zzmm);
    },
    // 任教学科字典翻译
    rjxkFormat(row, column) {
      return this.selectDictLabel(this.rjxkOptions, row.rjxk);
    },
    // 健康状况字典翻译
    jkzkFormat(row, column) {
      return this.selectDictLabel(this.jkzkOptions, row.jkzk);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        otherid: null,
        name: null,
        xb: null,
        deptId: null,
        dwmc: null,
        dwjc: null,
        xrxzzw: null,
        xrdnzw: null,
        xrqtzw: null,
        rxzwny: null,
        xrxzzj: null,
        xzzjqdny: null,
        sfhbgb: null,
        hbgbqdny: null,
        sfzh: null,
        updatetime: null,
        mz: null,
        jg: null,
        jgProvince: null,
        jgCity: null,
        jgCityname: null,
        jgArea: null,
        jgAreaname: null,
        csd: null,
        csdProvince: null,
        csdCity: null,
        csdCityname: null,
        csdArea: null,
        csdAreaname: null,
        csrq: null,
        cjgzny: null,
        jtzz: null,
        dwdz: null,
        dyxl: null,
        xl: null,
        xw: null,
        byyx: null,
        jylx: null,
        zc: null,
        jzcyqk: null,
        nfkc: null,
        hyzk: null,
        zzmm: null,
        rdsj: null,
        bgsdh: null,
        phone: null,
        rjxk: null,
        jkzk: null,
        createTime: null,
        createUser: null,
        dqzt: null,
        txTime: null,
      };
      this.diglogForm.province = "";
      this.diglogForm.city = "";
      this.diglogForm.area = "";

      this.diglogForm1.province = "";
      this.diglogForm1.city = "";
      this.diglogForm1.area = "";

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
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加干部基本情况";
      this.isshow = false;
      this.txtimeshow = false;
    },
    /** 查看按钮操作 */
    handleCheck(row) {
      const id = row.id;
      this.$router.push({
        path: "/gbxxgl/xq/data/" + id,
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getGbjbqk(id).then((response) => {
        this.isshow = true;
        this.form = response.data;
        if (this.form.dqzt == "99") {
          this.txtimeshow = true;
        }

        this.diglogForm.province = response.data.jg;
        this.diglogForm.city = response.data.jgCityname;
        this.diglogForm.area = response.data.jgAreaname;

        this.diglogForm1.province = response.data.csd;
        this.diglogForm1.city = response.data.csdCityname;
        this.diglogForm1.area = response.data.csdAreaname;

        this.open = true;
        this.title = "修改干部基本情况";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateGbjbqk(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addGbjbqk(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              }
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm(
        '是否确认删除干部基本情况编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delGbjbqk(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
        .catch(function () {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("是否确认导出所有干部基本情况数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportGbjbqk(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        })
        .catch(function () {});
    },
    // Dqzt监听
    handleDqztClick(value) {
      if (value == "99") {
        this.txtimeshow = true;
      } else {
        this.txtimeshow = false;
      }
    },
  },
};
</script>
<style lang="scss" scoped>
::v-deep.distpicker-address-wrapper select {
  height: 36px;
  font-size: 14px;
}
.big-dialog {
  ::v-deep {
    .el-dialog__body {
      max-height: 70vh;
      overflow: hidden;
      overflow-y: auto;
    }
  }
}
.el-select {
  width: 100%;
}
.my-date-picker {
  width: 100%;
}
::v-deep.el-form .el-form-item.el-form-item--medium .el-form-item__content {
  height: 36px;
}
</style>
