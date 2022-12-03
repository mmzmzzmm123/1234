<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="学号" prop="studentId">
        <el-input
          v-model="queryParams.studentId"
          placeholder="请输入学号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="姓名" prop="studentName">
        <el-input
          v-model="queryParams.studentName"
          placeholder="请输入姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="学院名称" prop="deptName">
        <el-select v-model="queryParams.deptId" placeholder="请选择学院名称" clearable>
                  <el-option
                    v-for="dept in deptList"
                    :key="dept.deptId"
                    :label="dept.deptName"
                    :value="dept.deptId"
                  />
                </el-select>
      </el-form-item>
      <el-form-item label="培养层次" prop="trainingLevel">
        <el-select v-model="queryParams.trainingLevel" placeholder="请选择培养层次" clearable>
          <el-option
            v-for="dict in dict.type.training_level"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="年级" prop="grade">
        <el-date-picker clearable
                        v-model="queryParams.grade"
                        type="year"
                        value-format="yyyy"
                        placeholder="请选择年级">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="住址" prop="address">
        <el-input
          v-model="queryParams.address"
          placeholder="请输入住址"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="民族" prop="nation">
        <el-select v-model="queryParams.nation" placeholder="请选择民族" clearable>
          <el-option
            v-for="dict in dict.type.nation"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="校区" prop="campus">
        <el-select v-model="queryParams.campus" placeholder="请选择校区" clearable>
          <el-option
            v-for="dict in dict.type.campus"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="在校状态" prop="studentTag">
        <el-select v-model="queryParams.studentTag" placeholder="请选择在校状态" clearable>
          <el-option
            v-for="dict in dict.type.student_tag"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="住宿地点" prop="accommodation">
        <el-select v-model="queryParams.accommodation" placeholder="请选择住宿地点" clearable>
          <el-option
            v-for="dict in dict.type.accommodation"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="住宿园区" prop="accommodationPark">
        <el-select v-model="queryParams.accommodationPark" placeholder="请选择住宿园区" clearable>
          <el-option
            v-for="dict in dict.type.accommodation_park"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="宿舍号" prop="dormitoryNo">
        <el-input
          v-model="queryParams.dormitoryNo"
          placeholder="请输入宿舍号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否校内住宿" prop="isOnSchool">
        <el-select v-model="queryParams.isOnSchool" placeholder="请选择是否校内住宿" clearable>
          <el-option
            v-for="dict in dict.type.sys_yes_no"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="管控等级" prop="controlLevel">
        <el-select v-model="queryParams.controlLevel" placeholder="请选择管控等级" clearable>
          <el-option
            v-for="dict in dict.type.control_level"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="未返校原因" prop="notSchoolReason">
        <el-select v-model="queryParams.notSchoolReason" placeholder="请选择未返校原因" clearable>
          <el-option
            v-for="dict in dict.type.not_school_reason"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="返校出发地风险等级" prop="placeToSchoolLevel">
        <el-select v-model="queryParams.placeToSchoolLevel" placeholder="请选择返校出发地风险等级" clearable>
          <el-option
            v-for="dict in dict.type.risk_level"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="返校出发地" prop="placeToSchool">
        <el-select v-model="queryParams.placeToSchool" placeholder="请选择返校出发地" clearable>
          <el-option
            v-for="dict in dict.type.place_to_school"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="离校目的地" prop="placeToLeave">
        <el-select v-model="queryParams.placeToLeave" placeholder="请选择离校目的地" clearable>
          <el-option
            v-for="dict in dict.type.place_to_leave"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="密接类型" prop="jointType">
        <el-select v-model="queryParams.jointType" placeholder="请选择密接类型" clearable>
          <el-option
            v-for="dict in dict.type.joint_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
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
          v-hasPermi="['student:info:add']"
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
          v-hasPermi="['student:info:edit']"
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
          v-hasPermi="['student:info:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['student:info:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="infoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="学号" align="center" prop="studentId" />
      <el-table-column label="姓名" align="center" prop="studentName" />
      <el-table-column label="性别" align="center" prop="studentSex" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_user_sex" :value="scope.row.studentSex"/>
        </template>
      </el-table-column>
      <el-table-column label="联系方式" align="center" prop="studentPhone" />
      <el-table-column label="身份证号" align="center" prop="idNum" />
      <el-table-column label="学院名称" align="center" prop="deptName" />
<!--              <template slot-scope="scope">-->
<!--                <dict-tag :options="dict.type.deptName" :value="scope.row.trainingLevel"/>-->
<!--              </template>-->
<!--            </el-table-column>-->
      <el-table-column label="培养层次" align="center" prop="trainingLevel">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.training_level" :value="scope.row.trainingLevel"/>
        </template>
      </el-table-column>
      <el-table-column label="年级" align="center" prop="grade"/>
      <el-table-column label="住址" align="center" prop="address" />
      <el-table-column label="民族" align="center" prop="nation">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.nation" :value="scope.row.nation"/>
        </template>
      </el-table-column>
      <el-table-column label="校区" align="center" prop="campus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.campus" :value="scope.row.campus"/>
        </template>
      </el-table-column>
      <el-table-column label="在校状态" align="center" prop="studentTag">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.student_tag" :value="scope.row.studentTag"/>
        </template>
      </el-table-column>
      <el-table-column label="住宿地点" align="center" prop="accommodation">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.accommodation" :value="scope.row.accommodation"/>
        </template>
      </el-table-column>
      <el-table-column label="住宿园区" align="center" prop="accommodationPark">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.accommodation_park" :value="scope.row.accommodationPark"/>
        </template>
      </el-table-column>
      <el-table-column label="宿舍号" align="center" prop="dormitoryNo" />
      <el-table-column label="是否校内住宿" align="center" prop="isOnSchool">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.isOnSchool"/>
        </template>
      </el-table-column>
      <el-table-column label="管控等级" align="center" prop="controlLevel">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.control_level" :value="scope.row.controlLevel"/>
        </template>
      </el-table-column>
      <el-table-column label="未返校原因" align="center" prop="notSchoolReason">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.not_school_reason" :value="scope.row.notSchoolReason"/>
        </template>
      </el-table-column>
      <el-table-column label="返校出发地风险等级" align="center" prop="placeToSchoolLevel">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.risk_level" :value="scope.row.placeToSchoolLevel"/>
        </template>
      </el-table-column>
      <el-table-column label="返校出发地" align="center" prop="placeToSchool">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.place_to_school" :value="scope.row.placeToSchool"/>
        </template>
      </el-table-column>
      <el-table-column label="离校目的地" align="center" prop="placeToLeave">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.place_to_leave" :value="scope.row.placeToLeave"/>
        </template>
      </el-table-column>
      <el-table-column label="密接类型" align="center" prop="jointType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.joint_type" :value="scope.row.jointType"/>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['student:info:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleStateUpdate(scope.row)"
            v-hasPermi="['student:state:edit']"
          >状态变更</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['student:info:remove']"
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

    <!-- 添加或修改学生信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="学号" prop="studentId">
          <el-input v-model="form.studentId" placeholder="请输入学号" />
        </el-form-item>
        <el-form-item label="姓名" prop="studentName">
          <el-input v-model="form.studentName" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="身份证号" prop="idNum">
          <el-input v-model="form.idNum" placeholder="请输入身份证号" />
        </el-form-item>
        <el-form-item label="联系方式" prop="studentPhone">
          <el-input v-model="form.studentPhone" placeholder="请输入联系方式" />
        </el-form-item>
        <el-form-item label="学院名称" prop="deptId">
          <el-select v-model="form.deptId" placeholder="请选择学院名称">
                      <el-option
                        v-for="dept in deptList"
                        :key="dept.deptId"
                        :label="dept.deptName"
                        :value="dept.deptId"
                      ></el-option>
                    </el-select>
        </el-form-item>
        <el-form-item label="培养层次" prop="trainingLevel">
          <el-select v-model="form.trainingLevel" placeholder="请选择培养层次">
            <el-option
              v-for="dict in dict.type.training_level"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="年级" prop="grade">
          <el-date-picker clearable
                          v-model="form.grade"
                          type="year"
                          value-format="yyyy"
                          placeholder="请选择年级">
          </el-date-picker>
        </el-form-item>

        <el-form-item label="民族" prop="nation">
          <el-select v-model="form.nation" placeholder="请选择民族">
            <el-option
              v-for="dict in dict.type.nation"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="是否校内住宿" prop="isOnSchool">
          <el-select v-model="form.isOnSchool" placeholder="请选择是否校内住宿" @change="changeIsOnSchool">
            <el-option
              v-for="dict in dict.type.sys_yes_no"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="校区" prop="campus">
          <el-select v-model="form.campus" placeholder="请选择校区">
            <el-option
              v-for="dict in dict.type.campus"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="在校状态" prop="studentTag">
          <el-select v-model="form.studentTag" placeholder="请选择在校状态">
            <el-option
              v-for="dict in dict.type.student_tag"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="住宿地点" prop="accommodation">
          <el-select v-model="form.accommodation" placeholder="请选择住宿地点">
            <el-option
              v-for="dict in dict.type.accommodation"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="住宿园区" prop="accommodationPark">
          <el-select v-model="form.accommodationPark" placeholder="请选择住宿园区">
            <el-option
              v-for="dict in dict.type.accommodation_park"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="宿舍号" prop="dormitoryNo">
          <el-input v-model="form.dormitoryNo" placeholder="请输入宿舍号" />
        </el-form-item>
        <el-form-item label="住址" prop="address">
          <el-input v-model="form.address" placeholder="请输入住址" />
        </el-form-item>
        <el-form-item label="管控等级" prop="controlLevel">
          <el-select v-model="form.controlLevel" placeholder="请选择管控等级">
            <el-option
              v-for="dict in dict.type.control_level"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="未返校原因" prop="notSchoolReason">
          <el-select v-model="form.notSchoolReason" placeholder="请选择未返校原因">
            <el-option
              v-for="dict in dict.type.not_school_reason"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="返校出发地风险等级" prop="placeToSchoolLevel">
          <el-select v-model="form.placeToSchoolLevel" placeholder="请选择返校出发地风险等级">
            <el-option
              v-for="dict in dict.type.risk_level"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="返校出发地" prop="placeToSchool">
          <el-select v-model="form.placeToSchool" placeholder="请选择返校出发地">
            <el-option
              v-for="dict in dict.type.place_to_school"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="离校目的地" prop="placeToLeave">
          <el-select v-model="form.placeToLeave" placeholder="请选择离校目的地">
            <el-option
              v-for="dict in dict.type.place_to_leave"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="密接类型" prop="jointType">
          <el-select v-model="form.jointType" placeholder="请选择密接类型">
            <el-option
              v-for="dict in dict.type.joint_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 修改学生状态信息对话框 -->
    <el-dialog :title="title" :visible.sync="open2" width="500px" append-to-body>
      <el-form ref="stateForm" :model="stateForm" :rules="rules" label-width="80px">
        <el-form-item label="学号" prop="studentId">
          <el-input v-model="stateForm.studentId" placeholder="请输入学号" readonly disabled/>
        </el-form-item>
        <el-form-item label="姓名" prop="studentName">
          <el-input v-model="stateForm.studentName" placeholder="请输入姓名" readonly disabled/>
        </el-form-item>
        <el-form-item label="身份证号" prop="idNum">
          <el-input v-model="stateForm.idNum" placeholder="请输入身份证号" readonly disabled/>
        </el-form-item>
          <el-form-item label="联系方式" prop="studentPhone">
          <el-input v-model="stateForm.studentPhone" placeholder="请输入联系方式" readonly disabled/>
        </el-form-item>
        <el-form-item label="学院名称" prop="deptName">
          <el-input v-model="stateForm.deptName" placeholder="请输入学院名称" readonly disabled/>
        </el-form-item>
        <el-form-item label="培养层次" prop="trainingLevel">
          <el-select v-model="stateForm.trainingLevel" placeholder="请选择培养层次" readonly disabled>
            <el-option
              v-for="dict in dict.type.training_level"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="年级" prop="grade">
          <el-date-picker clearable
                          v-model="stateForm.grade"
                          type="year"
                          value-format="yyyy"
                          placeholder="请选择年级" readonly disabled>
          </el-date-picker>
        </el-form-item>
        <el-form-item label="民族" prop="nation">
          <el-select v-model="stateForm.nation" placeholder="请选择民族" readonly disabled>
            <el-option
              v-for="dict in dict.type.nation"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="是否校内住宿" prop="isOnSchool">
          <el-select v-model="stateForm.isOnSchool" placeholder="请选择是否校内住宿" @change="changeIsOnSchool">
            <el-option
              v-for="dict in dict.type.sys_yes_no"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="校区" prop="campus" readonly disabled>
          <el-select v-model="stateForm.campus" placeholder="请选择校区">
            <el-option
              v-for="dict in dict.type.campus"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="在校状态" prop="studentTag">
          <el-select v-model="stateForm.studentTag" placeholder="请选择在校状态">
            <el-option
              v-for="dict in dict.type.student_tag"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="住宿地点" prop="accommodation">
          <el-select v-model="stateForm.accommodation" placeholder="请选择住宿地点" >
            <el-option
              v-for="dict in dict.type.accommodation"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="住宿园区" prop="accommodationPark">
          <el-select v-model="stateForm.accommodationPark" placeholder="请选择住宿园区" >
            <el-option
              v-for="dict in dict.type.accommodation_park"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="宿舍号" prop="dormitoryNo">
          <el-input v-model="stateForm.dormitoryNo" placeholder="请输入宿舍号"/>
        </el-form-item>
        <el-form-item label="住址" prop="address">
          <el-input v-model="stateForm.address" placeholder="请输入住址"/>
        </el-form-item>
        <el-form-item label="管控等级" prop="controlLevel">
          <el-select v-model="stateForm.controlLevel" placeholder="请选择管控等级">
            <el-option
              v-for="dict in dict.type.control_level"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="未返校原因" prop="notSchoolReason">
          <el-select v-model="stateForm.notSchoolReason" placeholder="请选择未返校原因">
            <el-option
              v-for="dict in dict.type.not_school_reason"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="返校出发地风险等级" prop="placeToSchoolLevel">
          <el-select v-model="stateForm.placeToSchoolLevel" placeholder="请选择返校出发地风险等级">
            <el-option
              v-for="dict in dict.type.risk_level"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="返校出发地" prop="placeToSchool">
          <el-select v-model="stateForm.placeToSchool" placeholder="请选择返校出发地">
            <el-option
              v-for="dict in dict.type.place_to_school"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="离校目的地" prop="placeToLeave">
          <el-select v-model="stateForm.placeToLeave" placeholder="请选择离校目的地">
            <el-option
              v-for="dict in dict.type.place_to_leave"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="密接类型" prop="jointType">
          <el-select v-model="stateForm.jointType" placeholder="请选择密接类型">
            <el-option
              v-for="dict in dict.type.joint_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="stateForm.remark" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitStateForm">确 定</el-button>
        <el-button @click="cancel2">取 消</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
  import { listInfo, getInfo, delInfo, addInfo, updateInfo } from "@/api/student/info";
  import { listDept } from "@/api/system/dept";

  export default {
    name: "Info",
    dicts: ['training_level', 'campus', 'control_level', 'place_to_school', 'sys_yes_no', 'risk_level', 'nation', 'accommodation_park', 'accommodation', 'not_school_reason', 'student_tag', 'joint_type', 'place_to_leave','sys_user_sex'],
    data() {
      return {
        // 遮罩层
        loading: true,
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
        // 学生信息表格数据
        infoList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        open2: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          studentId: null,
          studentName: null,
          idNum: null,
          deptId: null,
          deptName: null,
          trainingLevel: null,
          grade: null,
          address: null,
          nation: null,
          campus: null,
          studentTag: null,
          accommodation: null,
          accommodationPark: null,
          dormitoryNo: null,
          isOnSchool: null,
          controlLevel: null,
          notSchoolReason: null,
          placeToSchoolLevel: null,
          placeToSchool: null,
          placeToLeave: null,
          jointType: null,
        },
        // 表单参数
        form: {},
        //
        stateForm: {},
        // 表单校验
        rules: {
          studentId: [
            { required: true, message: "学号不能为空", trigger: "blur" }
          ],
          studentName: [
            { required: true, message: "姓名不能为空", trigger: "blur" }
          ],
          idNum: [
            { required: true, message: "身份证号不能为空", trigger: "blur" }
          ],
          deptId: [
            { required: true, message: "学院不能为空", trigger: "blur" }
          ],
          trainingLevel: [
            { required: true, message: "培养层次不能为空", trigger: "blur" }
          ],
          grade: [
            { required: true, message: "年级不能为空", trigger: "blur" }
          ],
          nation: [
            { required: true, message: "民族不能为空", trigger: "blur" }
          ],
          isOnSchool: [
            { required: true, message: "是否在校不能为空", trigger: "blur" }
          ],
          studentTag: [
            { required: true, message: "在校状态不能为空", trigger: "blur" }
          ],
          studentPhone:[{
              required: true, message: "在校状态不能为空", trigger: "blur" 
          },
          { type: 'number',
            message: '手机号格式不正确',
            trigger: 'blur',
            transform(value) {
              var phonereg = 11 && /^((13|14|15|16|17|18|19)[0-9]{1}\d{8})$/
              if (!phonereg.test(value)) {
                return false
              } else {
                return Number(value)
              }
            }
          }
          ]
        },
        // 二级部门列表
        deptList: []
      };
    },
    created() {
      this.getList();
      this.getDeptList();
    },
    methods: {
      /** 查询学生信息列表 */
      getList() {
        this.loading = true;
        listInfo(this.queryParams).then(response => {
          console.log("=>",response.rows)
          this.infoList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      getDeptList() {
        listDept({"parentId": 100}).then(response => {
          this.deptList = response.data;
        });
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
      // 取消按钮2
      cancel2() {
        this.open2 = false;
        this.resetStateForm();
      },
      // 表单重置
      reset() {
        this.form = {
          id: null,
          studentId: null,
          studentName: null,
          studentSex: null,
          studentPhone: null,
          idNum: null,
          deptId: null,
          deptName: null,
          trainingLevel: null,
          grade: null,
          address: null,
          nation: null,
          campus: null,
          studentTag: null,
          accommodation: null,
          accommodationPark: null,
          dormitoryNo: null,
          isOnSchool: null,
          controlLevel: null,
          notSchoolReason: null,
          placeToSchoolLevel: null,
          placeToSchool: null,
          placeToLeave: null,
          jointType: null,
          createBy: null,
          createTime: null,
          updateBy: null,
          updateTime: null,
          remark: null
        };
        this.resetForm("form");
      },
      // 表单重置
      resetStateForm() {
        this.stateForm = {
          id: null,
          studentId: null,
          studentName: null,
          studentSex: null,
          studentPhone: null,
          idNum: null,
          deptId: null,
          deptName: null,
          trainingLevel: null,
          grade: null,
          address: null,
          nation: null,
          campus: null,
          studentTag: null,
          accommodation: null,
          accommodationPark: null,
          dormitoryNo: null,
          isOnSchool: null,
          controlLevel: null,
          notSchoolReason: null,
          placeToSchoolLevel: null,
          placeToSchool: null,
          placeToLeave: null,
          jointType: null,
          createBy: null,
          createTime: null,
          updateBy: null,
          updateTime: null,
          remark: null
        };
        this.resetForm("stateForm");
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
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "添加学生信息";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        getInfo(id).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改学生信息";
        });
      },
      /** 状态变更按钮操作 */
      handleStateUpdate(row) {
        this.resetStateForm();
        const id = row.id || this.ids
        getInfo(id).then(response => {
          this.stateForm = response.data;
          this.open2 = true;
          this.title = "变更学生状态";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id != null) {
              updateInfo(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addInfo(this.form).then(response => {
                this.$modal.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              });
            }
          }
        });
      },
      /** 提交状态变更按钮 */
      submitStateForm() {
        this.$refs["stateForm"].validate(valid => {
          if (valid) {
            if (this.stateForm.id != null) {
              updateInfo(this.stateForm).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open2 = false;
                this.getList();
              });
            } else {
              addInfo(this.stateForm).then(response => {
                this.$modal.msgSuccess("新增成功");
                this.open2 = false;
                this.getList();
              });
            }
          }
        });
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const ids = row.id || this.ids;
        this.$modal.confirm('是否确认删除学生信息编号为"' + ids + '"的数据项？').then(function() {
          return delInfo(ids);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },
      /** 导出按钮操作 */
      handleExport() {
        this.download('student/info/export', {
          ...this.queryParams
        }, `info_${new Date().getTime()}.xlsx`)
      },
      /**
       * 变更 是否在校
       * @param val
       */
      changeIsOnSchool(val) {
        if ("Y" === val) {
          this.form.accommodation = "0";
          this.stateForm.accommodation = "0";
        } else if ("N" === val) {
          this.form.accommodation = "3";
          this.stateForm.accommodation = "3";
        }
      }
    }
  };
</script>
