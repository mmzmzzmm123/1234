<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <!-- <el-form-item label="用户id" prop="userId">
        <el-input v-model="queryParams.userId" placeholder="只查询当前用户得store" clearable
          @keyup.enter.native="handleQuery" />
      </el-form-item> -->
      <el-form-item label="门店名称" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入门店名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="门店地址" prop="address">
        <el-input v-model="queryParams.address" placeholder="请输入门店地址" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>

      <el-form-item label="所属商圈" prop="busiDistrict">
        <el-input v-model="queryParams.busiDistrict" placeholder="请输入所属商圈" clearable
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="地铁线路" prop="subway">
        <el-input v-model="queryParams.subway" placeholder="请输入地铁线路" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['office:store:add']">新增</el-button>
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['office:store:edit']"
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
          v-hasPermi="['office:store:remove']"
        >删除</el-button>
      </el-col> -->
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['office:store:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="storeList" @selection-change="handleSelectionChange" @row-click="storeChange">
      <el-table-column type="selection" width="25" align="center" />
      <el-table-column label="id" align="center" width="35" prop="id" />
      <!-- <el-table-column label="用户id" align="center" prop="userId" /> -->
      <el-table-column label="门店名称" align="center" width="150" prop="name" />
      <el-table-column label="门店地址" align="center" width="150" prop="address" />
      <el-table-column label="电话" align="center" width="110" prop="phone" />
      <el-table-column label="营业开始时间" align="center" width="90" prop="startTime" />
      <el-table-column label="营业结束时间" align="center" prop="stopTime" width="90" />
      <el-table-column label="提前预约天数" align="center" prop="preDays" />
      <el-table-column label="wifi" align="center" prop="wifi" />
      <el-table-column label="绑定设备" align="center" prop="equipId" width="160" :formatter="equipFormatter" />
      <el-table-column label="所属商圈" align="center" prop="busiDistrict" width="100" />
      <el-table-column label="地铁线路" align="center" prop="subway" width="100" />
      <!--  <el-table-column label="纬度" align="center" prop="latitude" />
      <el-table-column label="经度" align="center" prop="longitude" /> -->
      <el-table-column label="logo" align="center" prop="logo">
        <template slot-scope="scope">
          <el-image style="width: 50px; height: 20px" placeholder :src="scope.row.logo"
            @click="handleDownload(scope.row)">
          </el-image>
        </template>
      </el-table-column>
      <!--  <el-table-column label="状态" align="center" prop="status" />
      <el-table-column label="备注" align="center" prop="remark" /> -->
      <el-table-column label="操作" align="center" width="250" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="addStoreRoom(scope.row)"
            v-hasPermi="['office:room:add']">添加房间</el-button>
          <el-button size="mini" type="text" icon="el-icon-edit" @click="bindEquipment(scope.row,'store')"
            v-hasPermi="['office:store:edit']">设备绑定</el-button>
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['office:store:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['office:store:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改商家用户门店对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <!-- <el-form-item label="用户id" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入用户id" />
        </el-form-item> -->
        <el-row>
          <el-col :span=12>
            <el-form-item label="门店名称" prop="name">
              <el-input v-model="form.name" placeholder="请输入门店名称" />
            </el-form-item>
          </el-col>
          <el-col :span=12>
            <el-form-item label="电话" prop="phone">
              <el-input v-model="form.phone" placeholder="请输入电话" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span=24>
            <el-form-item label="门店地址" prop="address">
              <el-input v-model="form.address" placeholder="请输入门店地址" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span=12>
            <el-form-item label="营业开始时间" prop="startTime">
              <el-time-select placeholder="营业开始时间" v-model="form.startTime" :picker-options="{
                  start: '00:00',
                   step: '01:00',
                  end: '24:00'
                }">
              </el-time-select>
            </el-form-item>
          </el-col>
          <el-col :span=12>
            <el-form-item label="营业结束时间" prop="stopTime">
              <el-time-select placeholder="营业结束时间" v-model="form.stopTime" :picker-options="{
                  start: '00:00',
                   step: '01:00',
                  end: '24:00',
                  minTime: form.startTime
                }">
              </el-time-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span=12>
            <el-form-item label="提前预约天数" prop="preDays">
              <el-input v-model="form.preDays" placeholder="请输入提前预约天数" />
            </el-form-item>
          </el-col>
          <el-col :span=12>
            <el-form-item label="wifi" prop="wifi">
              <el-input v-model="form.wifi" placeholder="请输入wifi" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span=12>
            <el-form-item label="所属商圈" prop="busiDistrict">
              <el-input v-model="form.busiDistrict" placeholder="请输入所属商圈" />
            </el-form-item>
          </el-col>
          <el-col :span=12>
            <el-form-item label="地铁线路" prop="subway">
              <el-input v-model="form.subway" placeholder="请输入地铁线路" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span=12>
            <el-form-item label="纬度" prop="latitude">
              <el-input v-model="form.latitude" placeholder="请输入纬度" />
            </el-form-item>
          </el-col>
          <el-col :span=12>
            <el-form-item label="经度" prop="longitude">
              <el-input v-model="form.longitude" placeholder="请输入经度" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span=12>
            <el-form-item label="logo" prop="logo">
              <el-upload v-model="form.logo" ref="upload" :limit="1" accept=".jpg, .png" :action="upload.url"
                :headers="upload.headers" :file-list="upload.fileList" :on-progress="handleFileUploadProgress"
                :on-success="handleFileSuccess" :on-exceed="handleExceed" :auto-upload="true">
                <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
                <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
              </el-upload>
            </el-form-item>
          </el-col>
          <!-- <el-col :span=12>
            <el-form-item label="经度" prop="longitude">
              <el-input v-model="form.longitude" placeholder="请输入经度" />
            </el-form-item>
          </el-col> -->
        </el-row>
        <!--  <el-form-item label="设备控制" prop="equipId">
          <el-input v-model="form.equipId" placeholder="请输入设备控制" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注" />
        </el-form-item> -->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-divider></el-divider>
    <div style="float: left; width: 60%;border-right: 0.2rem solid #aaaaaa; padding: 1rem;">
      <!-- 房间列表 -->
      <span> 房间列表({{selectedStore}}) </span>
      <el-table v-loading="roomLoading" :data="roomList" @row-click="roomChange">
        <el-table-column type="selection" width="30" align="center" />
        <el-table-column label="id" align="center" width="40" prop="id" />
        <el-table-column label="名称" align="center" width="160" prop="name" />
        <el-table-column label="wifi" align="center" width="85" prop="wifi" />
        <el-table-column label="绑定设备" align="center" width="200" prop="equipId" :formatter="equipFormatter" />
        <!-- <el-table-column label="桌台控制" align="center" prop="tableCode" /> -->
        <el-table-column label="状态" align="center" width="85" prop="status">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.room_status" :value="scope.row.status" />
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" width="270" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button size="mini" type="text" icon="el-icon-edit" @click="setRoomPrice(scope.row)"
              v-hasPermi="['office:room:edit']">添加收费规则</el-button>
            <el-button size="mini" type="text" icon="el-icon-edit" @click="bindEquipment(scope.row,'room')"
              v-hasPermi="['office:room:edit']">设备绑定</el-button>
            <el-button size="mini" type="text" icon="el-icon-edit" @click="handleRoomUpdate(scope.row)"
              v-hasPermi="['office:room:edit']">修改</el-button>
            <el-button size="mini" type="text" icon="el-icon-delete" @click="handleRoomDelete(scope.row)"
              v-hasPermi="['office:room:remove']">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <pagination v-show="roomTotal>0" :total="roomTotal" :page.sync="roomQueryParam.pageNum"
        :limit.sync="roomQueryParam.pageSize" @pagination="listStoreRoom" />

      <!-- 房间新增修改 -->
      <el-dialog :title="title" :visible.sync="roomOpen" width="500px" append-to-body>
        <el-form ref="roomForm" :model="roomForm" :rules="roomRules" label-width="80px">
          <el-form-item label="名称" prop="name">
            <el-input v-model="roomForm.name" placeholder="请输入名称" />
          </el-form-item>
          <el-form-item label="wifi" prop="wifi">
            <el-input v-model="roomForm.wifi" placeholder="请输入wifi" />
          </el-form-item>
          <el-form-item label="包厢控制" prop="equipCode">
            <el-input v-model="roomForm.equipCode" placeholder="请输入包厢控制" />
          </el-form-item>
          <el-form-item label="桌台控制" prop="tableCode">
            <el-input v-model="roomForm.tableCode" placeholder="请输入桌台控制" />
          </el-form-item>
          <el-form-item label="房间状态" prop="status">
            <el-select v-model="roomForm.status">
              <el-option v-for="dict in dict.type.room_status" :key="dict.value" :label="dict.label"
                :value="dict.value" />
            </el-select>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitRoomForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </el-dialog>
    </div>

    <span>&nbsp; 时段收费规则({{selectedRoom}}) </span>

    <div style="float: left;width: 40%;border: 0.2rem solid solid none solid #aaaaaa;padding: 1rem;">
      <el-table v-loading="priceLoading" :data="priceList">
        <el-table-column label="开始时间" align="center" prop="startTime" width="90" />
        <el-table-column label="结束时间" align="center" prop="stopTime" width="90" />
        <el-table-column label="单价(元/每小时)" align="center" prop="price" width="120" />
        <el-table-column label="会员单价(元/每小时)" align="center" prop="memberPrice" width="140" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button size="mini" type="text" icon="el-icon-edit" @click="handlePriceUpdate(scope.row)"
              v-hasPermi="['office:roomprice:edit']">修改</el-button>
            <el-button size="mini" type="text" icon="el-icon-delete" @click="handlePriceDelete(scope.row)"
              v-hasPermi="['office:roomprice:remove']">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <pagination v-show="priceTotal>0" :total="priceTotal" :page.sync="priceQueryParams.pageNum"
        :limit.sync="priceQueryParams.pageSize" @pagination="getPrice" />
    </div>
    <!-- 添加或修改房间价格对话框 -->
    <el-dialog :title="title" :visible.sync="priceOpen" width="500px" append-to-body>
      <el-form ref="priceForm" :model="priceForm" :rules="priceRules" label-width="150px">
        <el-form-item label="开始时间" prop="startTime">
          <el-input-number v-model="priceForm.startTime" :min="1" :max="24" :step="1" label="描述文字"></el-input-number>
         <!-- <el-time-select placeholder="开始时间" v-model="" :picker-options="{
                start: '00',
                step: '01',
                end: '24'
              }">
          </el-time-select> -->
        </el-form-item>
        <el-form-item label="营业结束时间" prop="stopTime">
          <el-input-number v-model="priceForm.stopTime" :min="1" :max="24" label="描述文字"></el-input-number>
          <!-- <el-time-select placeholder="营业结束时间" v-model="priceForm.stopTime" :picker-options="{
                start: '00',
                 step: '01',
                end: '24',
                minTime: priceForm.startTime
              }">
          </el-time-select> -->
        </el-form-item>
        <el-form-item label="单价(元/每小时)" prop="price">
          <el-input v-model="priceForm.price" placeholder="请输入单价" />
        </el-form-item>
        <el-form-item label="会员单价(元/每小时)" prop="memberPrice">
          <el-input v-model="priceForm.memberPrice" placeholder="请输入会员单价" />
        </el-form-item>
        <!-- <el-form-item label="备注" prop="remark">
          <el-input v-model="priceForm.remark" placeholder="请输入备注" />
        </el-form-item> -->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitPriceForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog title="绑定设备" :visible.sync="bindOpen" width="500px" append-to-body>
      <el-form ref="bindForm" :model="bindForm" :rules="priceRules" label-width="150px">
        <el-form-item label="选择绑定设备" prop="price">
          <el-select v-model="bindForm.name" filterable>
            <el-option v-for="option in equipOptions" :key="option.id" :label="option.name" :value="option.id" />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitBindForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
  import {
    listStore,
    getStore,
    delStore,
    addStore,
    updateStore
  } from "@/api/office/store";

  import {
    getToken
  } from "@/utils/auth";

  import {
    listRoom,
    getRoom,
    delRoom,
    addRoom,
    updateRoom
  } from "@/api/office/room";

  import {
    listRoomprice,
    getRoomprice,
    delRoomprice,
    addRoomprice,
    updateRoomprice
  } from "@/api/office/roomprice";

  import {
    listEquipment
  } from "@/api/office/equipment";


  export default {
    name: "Store",
    dicts: ["room_status"],
    data() {
      return {
        selectedStore: '',
        selectedRoom: '',
        defaultTimeSt: null,
        // 遮罩层
        loading: true,
        roomLoading: false,
        priceLoading: false,
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
        roomTotal: 0,
        priceTotal: 0,
        // 商家用户门店表格数据
        storeList: [],
        roomList: [],
        priceList: [],
        equipOptions: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        roomOpen: false,
        priceOpen: false,
        bindOpen: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          userId: null,
          name: null,
          address: null,
          phone: null,
          startTime: null,
          stopTime: null,
          preDays: null,
          wifi: null,
          equipId: null,
          busiDistrict: null,
          subway: null,
          latitude: null,
          longitude: null,
          logo: null,
          status: null,
        },
        roomQueryParam: {
          pageNum: 1,
          pageSize: 10,
        },
        priceQueryParams: {
          pageNum: 1,
          pageSize: 10,
        },
        // 表单参数
        form: {},
        roomForm: {},
        priceForm: {},
        bindForm: {},
        bindType: '',
        // 表单校验
        rules: {
          name: [{
            required: true,
            message: "门店名称不能为空",
            trigger: "blur"
          }],
          address: [{
            required: true,
            message: "门店地址不能为空",
            trigger: "blur"
          }],
          // startTime: [{
          //   required: true,
          //   message: "营业开始时间不能为空",
          //   trigger: "blur"
          // }],
          // stopTime: [{
          //   required: true,
          //   message: "营业结束时间不能为空",
          //   trigger: "blur"
          // }],
          preDays: [{
            required: true,
            message: "提前预约天数不能为空",
            trigger: "blur"
          }],
          latitude: [{
            required: true,
            message: "纬度不能为空",
            trigger: "blur"
          }],
          logo: [{
            required: true,
            message: "logo不能为空",
            trigger: "blur"
          }],
        },
        roomRules: {
          name: [{
            required: true,
            message: "名称不能为空",
            trigger: "blur"
          }],
        },
        priceRules: {
          price: [{
            required: true,
            message: "单价不能为空",
            trigger: "blur"
          }],
          memberPrice: [{
            required: true,
            message: "会员单价不能为空",
            trigger: "blur"
          }],
        },
        // 上传参数
        upload: {
          // 是否禁用上传
          isUploading: false,
          // 设置上传的请求头部
          headers: {
            Authorization: "Bearer " + getToken()
          },
          // 上传的地址
          url: process.env.VUE_APP_BASE_API + "/common/upload",
          // 上传的文件列表
          fileList: []
        },
      };
    },
    created() {
      this.getList();
      this.getEquipOptions();
    },
    methods: {
      /** 查询商家用户门店列表 */
      getList() {
        this.loading = true;
        listStore(this.queryParams).then(response => {
          this.storeList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      getEquipOptions() {
        var param = {};
        param.pageNum = 1;
        param.pageSize = 1000;
        listEquipment(param).then(response => {
          this.equipOptions = response.rows;
        });
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.roomOpen = false;
        this.priceOpen = false;
        this.bindOpen = false;
        this.reset();
      },
      // 表单重置
      reset() {
        this.form = {
          id: null,
          userId: null,
          name: null,
          address: null,
          phone: null,
          startTime: null,
          stopTime: null,
          preDays: null,
          wifi: null,
          equipId: null,
          busiDistrict: null,
          subway: null,
          latitude: null,
          longitude: null,
          logo: null,
          status: null,
          remark: null,
          createBy: null,
          createTime: null,
          updateBy: null,
          updateTime: null
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
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },
      storeChange(row, column, event) {
        this.roomLoading = true;
        this.roomQueryParam.storeId = row.id;
        this.selectedStore = row.name;
        this.listStoreRoom();
        this.getEquipOptions();
      },
      listStoreRoom() {
        listRoom(this.roomQueryParam).then(response => {
          this.roomList = response.rows;
          this.roomTotal = response.total;
          this.roomLoading = false;
        });
      },
      roomChange(row, column, event) {
        this.priceLoading = true;
        this.roomQueryParam.roomId = row.id;
        this.selectedRoom = row.name;
        this.getPrice();
      },
      /** 查询房间价格列表 */
      getPrice() {
        listRoomprice(this.roomQueryParam).then(response => {
          this.priceList = response.rows;
          this.priceTotal = response.total;
          this.priceLoading = false;
        });
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "添加商家门店";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        getStore(id).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改商家门店";
        });
      },
      handleRoomUpdate(row) {
        const id = row.id
        getRoom(id).then(response => {
          this.roomForm = response.data;
          this.roomOpen = true;
          this.title = "修改房间";
        });
      },
      addStoreRoom(row) {
        this.reset();
        this.roomForm = {};
        this.roomForm.storeId = row.id
        this.roomOpen = true;
      },
      setRoomPrice(row) {
        this.reset();
        this.priceForm = {};
        this.priceForm.roomId = row.id
        this.priceOpen = true;
      },
      bindEquipment(row, type) {
        // type = "store/room";
        this.bindOpen = true;
        this.bindType = type;
        this.bindForm.id = row.id;
      },
      submitBindForm() {
        var subForm = {};
        subForm.id = this.bindForm.id;
        subForm.equipId = this.bindForm.name;
        if (this.bindType == "store") {
          updateStore(subForm).then(response => {
            this.$modal.msgSuccess("修改成功");
            this.bindOpen = false;
            this.getList();
          });
        } else if (this.bindType == "room") {
          updateRoom(subForm).then(response => {
            this.$modal.msgSuccess("修改成功");
            this.bindOpen = false;
            this.listStoreRoom();
          });
        }
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id != null) {
              updateStore(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addStore(this.form).then(response => {
                this.$modal.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              });
            }
          }
        });
      },
      /** 提交门店包厢 **/
      submitRoomForm() {
        this.$refs["roomForm"].validate(valid => {
          if (valid) {
            if (this.roomForm.id != null) {
              updateRoom(this.roomForm).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.roomOpen = false;
                this.listStoreRoom();
              });
            } else {
              addRoom(this.roomForm).then(response => {
                this.$modal.msgSuccess("新增成功");
                this.roomOpen = false;
                this.listStoreRoom();
              });
            }
          }
        });
      },
      /** 提交房间价格**/
      submitPriceForm() {
        this.$refs["priceForm"].validate(valid => {
          if (valid) {
            if (this.priceForm.id != null) {
              updateRoomprice(this.priceForm).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.priceOpen = false;
                this.getPrice();
              });
            } else {
              addRoomprice(this.priceForm).then(response => {
                this.$modal.msgSuccess("新增成功");
                this.priceOpen = false;
                this.getPrice();
              });
            }
          }
        });
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const ids = row.id || this.ids;
        this.$modal.confirm('是否确认删除商家用户门店编号为"' + ids + '"的数据项？').then(function() {
          return delStore(ids);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },
      handleRoomDelete(row) {
        const ids = row.id;
        this.$modal.confirm('是否确认删除该房间？').then(function() {
          return delRoom(ids);
        }).then(() => {
          this.listStoreRoom();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },
      /** 导出按钮操作 */
      handleExport() {
        this.download('office/store/export', {
          ...this.queryParams
        }, `store_${new Date().getTime()}.xlsx`)
      },
      // 文件提交处理
      submitUpload() {
        this.$refs.upload.submit();
      },
      handleExceed(files, fileList) {
        this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
      },
      // 文件上传中处理
      handleFileUploadProgress(event, file, fileList) {
        this.upload.isUploading = true;
      },
      // 文件上传成功处理
      handleFileSuccess(response, file, fileList) {
        this.upload.isUploading = false;

        var hostPort = window.location.href;
        hostPort = hostPort.substring(0, hostPort.length - window.location.pathname.length);
        var url = response.url;
        var suffix = url.lastIndexOf(":") > 7 ? url.substring(url.lastIndexOf(":"), url.length) : url.substring(url
          .indexOf("profile") - 1, url.length);

        this.form.logo = hostPort + suffix;
        this.form.fileName = response.fileName;
        this.$modal.msgSuccess(response.msg);
      },
      // 文件下载处理
      handleDownload(row) {
        // alert(window.location.origin)
        // var hostPort = window.location.origin;
        var url = row.img;
        // var suffix = url.substring(hostPort.length, url.length);
        debugger
        const a = document.createElement('a')
        a.setAttribute('target', '_blank')
        a.setAttribute('href', url)
        a.click()
      },
      /** 修改按钮操作 */
      handlePriceUpdate(row) {
        this.reset();
        const id = row.id
        getRoomprice(id).then(response => {
          this.priceForm = response.data;
          this.priceOpen = true;
          this.title = "修改房间价格";
        });
      },
      /** 删除按钮操作 */
      handlePriceDelete(row) {
        const ids = row.id;
        this.$modal.confirm('是否确认删除该价格选项？').then(function() {
          return delRoomprice(ids);
        }).then(() => {
          this.getPrice();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },
      equipFormatter(row) {
        var res = "";
        // this.equipOptions.forEach(function(e) {
        //   if (e.id == row.equipId) {
        //     res = e.name;
        //   }
        // });
        for (var i = 0; i < this.equipOptions.length; i++) {
          if (this.equipOptions[i].id == row.equipId) {
            res = this.equipOptions[i].name;
            break;
          }
        }

        return res;
      },
    }
  };
</script>
