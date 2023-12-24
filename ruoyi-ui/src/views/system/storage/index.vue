<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="入库编码" prop="stoId">
        <el-input
          v-model="queryParams.stoId"
          placeholder="请输入入库编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="名称" prop="stoName">
        <el-input
          v-model="queryParams.stoName"
          placeholder="请输入名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="单位" prop="stoUnit">
        <el-input
          v-model="queryParams.stoUnit"
          placeholder="请输入单位"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!-- <el-form-item label="数量" prop="stoNum">
        <el-input
          v-model="queryParams.stoNum"
          placeholder="请输入数量"
          clearable
          @keyup.enter.native="handleQuery"
        /> -->
      <!-- </el-form-item> -->
      <!-- <el-form-item label="单价" prop="stoPrice">
        <el-input
          v-model="queryParams.stoPrice"
          placeholder="请输入单价"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
      <el-form-item label="金额" prop="minMoney">
        <el-input
          style="width: 120px"
          v-model.number="queryParams.minMoney"
          placeholder="请输入金额"
          clearable
          @keyup.enter.native="handleQuery"
        /><span>&nbsp;&nbsp;&nbsp;~&nbsp;&nbsp;&nbsp;</span><el-input
          style="width: 125px"
          v-model.number="queryParams.maxMoney"
          placeholder="请输入金额"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="备注" prop="stoRemark">
        <el-input
          v-model="queryParams.stoRemark"
          placeholder="请输入备注"
          clearable
          @keyup.enter.native="handleQuery"
        />  
      </el-form-item>
      <el-form-item label="经办人" prop="attnName">
          <el-input
          v-model="queryParams.attnName"
          placeholder="请输入经办人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="验收人" prop="acceName">
            <el-input
          v-model="queryParams.acceName"
          placeholder="请输入验收人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="保管人" prop="storName">
        <el-input
          v-model="queryParams.storName"
          placeholder="请输入保管人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="入库日期" prop="stoDate">
        <!-- <el-date-picker clearable
          v-model="queryParams.stoDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择入库日期">
        </el-date-picker> -->
        <el-date-picker
          v-model="dateRange"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <!-- <el-form-item label="录入日期" prop="entryDate">
        <el-date-picker clearable
          v-model="queryParams.entryDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择录入日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="录入人" prop="entryId">
        <el-input
          v-model="queryParams.entryId"
          placeholder="请输入录入人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
      <!-- <el-form-item label="修改时间" prop="editDate">
        <el-date-picker clearable
          v-model="queryParams.editDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择修改时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="修改人" prop="editId">
        <el-date-picker clearable
          v-model="queryParams.editId"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择修改人">
        </el-date-picker>
      </el-form-item> -->
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
          v-hasPermi="['system:storage:add']"
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
          v-hasPermi="['system:storage:edit']"
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
          v-hasPermi="['system:storage:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:storage:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
          <el-button type="primary" icon="el-icon-success" size="mini" @click="kaipiao">开票</el-button>
      </el-col>
      <el-col :span="1.5">
          <el-button type="primary" icon="el-icon-picture" size="mini" @click="checkImg()">查看开票信息</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="storageList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="开票状态" align="center" prop="kpStatus">
      <template slot-scope="scope">
          <el-tag v-if="scope.row.kpStatus == 1">已开票</el-tag>
          <el-tag type="warning" v-if="scope.row.kpStatus == 0">未开票</el-tag>
        </template>
      </el-table-column> 
      <!-- <el-table-column label="id" align="center" prop="id" /> -->
      <el-table-column label="入库编码" align="center" prop="stoId" />
      <el-table-column label="单位" align="center" prop="stoUnit" />
      <el-table-column label="名称" align="center" prop="stoName" />
      <el-table-column label="规格型号" align="center" prop="stoType" />
      <el-table-column label="单价(￥)" align="center" prop="stoPrice" />
      <el-table-column label="数量(个)" align="center" prop="stoNum" />
      <el-table-column label="金额(￥)" align="center" prop="stoMoney" />
      <!-- <el-table-column label="备注" align="center" prop="stoRemark" /> -->
      <!-- <el-table-column label="备注" align="center" prop="consultType" width="100"/> -->
      <el-table-column label="备注" align="center" width="230" prop="stoRemark" :show-overflow-tooltip="true"/>

       
      
      <el-table-column label="经办人" align="center" prop="attnName" />
      <el-table-column label="验收人" align="center" prop="acceName" />
      <el-table-column label="保管人" align="center" prop="storName" />
      <el-table-column label="入库日期" align="center" prop="stoDate" width="100">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.stoDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <!-- <el-table-column label="录入日期" align="center" prop="entryDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.entryDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="录入人" align="center" prop="entryId" />
      <el-table-column label="修改时间" align="center" prop="editDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.editDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="修改人" align="center" prop="editId" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.editId, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="100px">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:storage:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:storage:remove']"
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

    <!-- 添加或修改【入库单】对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="入库编码" prop="stoId">
          <el-input v-model="form.stoId" placeholder="请输入入库编码" />
        </el-form-item>
        <el-form-item label="单位" prop="stoUnit">
          <el-input v-model="form.stoUnit" placeholder="请输入单位" />
        </el-form-item>
        <el-form-item label="名称" prop="stoName">
          <el-input v-model="form.stoName" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="规格型号" prop="stoType">
          <el-input v-model="form.stoType" placeholder="规格型号" />
        </el-form-item>
        <el-form-item label="单价" prop="stoPrice">
          <el-input v-model.number="form.stoPrice" type="number" placeholder="请输入单价" />
        </el-form-item>
        <el-form-item label="数量" prop="stoNum">
          <el-input v-model.number="form.stoNum" type="number" placeholder="请输入数量" />
        </el-form-item>
        <el-form-item label="金额" prop="c">
          <el-input v-model="c" disabled=""></el-input>
          <!-- <el-input v-model="form.stoMoney" disabled="disabled" placeholder="请输入金额" /> -->
        </el-form-item>
        </el-table-column>
        <el-form-item label="备注" prop="stoRemark">
          <el-input v-model="form.stoRemark" placeholder="请输入备注" />
        </el-form-item>
        <el-form-item label="经办人" prop="stoAttn">
          <el-input v-model="form.stoAttn" placeholder="请输入经办人" />

          <!-- <el-select v-model="form.stoAttn" filterable placeholder="经办人">
            <el-option
              v-for="Attn in stoAttnAllList"
              :key="Attn.userId"
              :label="Attn.nickName"
              :value="Attn.userId"
            >
            </el-option>
          </el-select> -->
        </el-form-item>
        <el-form-item label="验收人" prop="stoAcce">
          <el-input v-model="form.stoAcce" placeholder="请输入验收人" />

          <!-- <el-select v-model="form.stoAcce" filterable placeholder="验收人">
            <el-option
              v-for="Acce in stoAcceAllList"
              :key="Acce.userId"
              :label="Acce.nickName"
              :value="Acce.userId"
            >
            </el-option>
          </el-select> -->
        </el-form-item>
        <el-form-item label="保管人" prop="stoStor">
          <el-input v-model="form.stoStor" placeholder="请输入验收人" />

          <!-- <el-select v-model="form.stoStor" filterable placeholder="保管人">
            <el-option
              v-for="Stor in stoStorAllList"
              :key="Stor.userId"
              :label="Stor.nickName"
              :value="Stor.userId"
            >
            </el-option>
          </el-select> -->
        </el-form-item>
        <!-- <el-form-item label="经办人" prop="stoAttn">
          <el-input v-model="form.stoAttn" placeholder="请输入经办人" />
        </el-form-item>
        <el-form-item label="" prop="stoAcce">
          <el-input v-model="form.stoAcce" placeholder="请输入验收人" />
        </el-form-item>
        <el-form-item label="" prop="stoStor">
          <el-input v-model="form.stoStor" placeholder="请输入保管人" />
        </el-form-item> -->
        <el-form-item label="入库日期" prop="stoDate">
          <el-date-picker clearable
            v-model="form.stoDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择入库日期">
          </el-date-picker>
        </el-form-item>
        <!-- <el-form-item label="录入日期" prop="entryDate">
          <el-date-picker clearable
            v-model="form.entryDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择录入日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="录入人" prop="entryId">
          <el-input v-model="form.entryId" placeholder="请输入录入人" />
        </el-form-item>
        <el-form-item label="修改时间" prop="editDate">
          <el-date-picker clearable
            v-model="form.editDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择修改时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="修改人" prop="editId">
          <el-date-picker clearable
            v-model="form.editId"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择修改人">
          </el-date-picker>
        </el-form-item> -->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
      <el-button v-if="false" :plain="true" @click="open3" >警告</el-button>
    </el-dialog>
     <el-dialog :title="title" :visible.sync="open1" width="500px" append-to-body>
      <el-form ref="form1" :model="form1" label-width="80px">
        <el-form-item label="商品图片">
              <image-upload :limit="1" v-model="form1.imgSrc"/>
        </el-form-item>
        <el-form-item>
      <el-button type="primary" @click="onSubmit">确认</el-button>
      <el-button  @click="onSubmit1">取消</el-button>
    </el-form-item>
       </el-form>
     </el-dialog>
     <el-image-viewer
        v-if="isShowPics"
        :on-close="closeViewer"
        :url-list="srcList"
      />
  </div>
</template>

<script>
import { stoAttnList,listStorage, getStorage, delStorage, addStorage, updateStorage } from "@/api/system/storage";

export default {
  name: "Storage",
  components:{
    'el-image-viewer': () => import('element-ui/packages/image/src/image-viewer')
  },
  data() {
    return {
      url: 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg',
      dateRange: [],
      open1:false,
      // 遮罩层
      loading: true,
      stoAttnAllList:[],
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
      // 【入库单】表格数据
      storageList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        maxDate:null,
        minDate:null,
        minMoney:null,
        maxMoney:null,
        roleId:0,
        pageNum: 1,
        pageSize: 10,
        stoId: null,
        stoName: null,
        stoType: null,
        stoUnit: null,
        stoNum: null,
        stoPrice: null,
        stoMoney: null,
        stoRemark: null,
        stoAttn: null,
        stoAcce: null,
        stoStor: null,
        stoDate: null,
        entryDate: null,
        entryId: null,
        editDate: null,
        editId: null,
        ids:[],
        id:null,
      },
      // 表单参数
      form: {},
      form1:{},
      srcList:[],
      isShowPics:false,
      // 表单校验
      rules: {
        stoId: [
          { required: true, message: "入库编码不能为空", trigger: "blur" }
        ],
        stoName: [
          { required: true, message: "名称不能为空", trigger: "blur" }
        ],
        stoType: [
          { required: true, message: "规格型号不能为空", trigger: "blur" }
        ],
        stoUnit: [
          { required: true, message: "单位不能为空", trigger: "blur" }
        ],
        stoNum: [
          { required: true, message: "数量必须为数字类型", trigger: "blur" }
        ],
        stoPrice: [
          { required: true, message: "单价必须为数字类型", trigger: "blur" }
        ],
        stoMoney: [
          { required: true, message: "请输入单价和数量", trigger: "blur" }
        ],
        stoAttn: [
          { required: true, message: "经办人不能为空", trigger: "blur" }
        ],
        stoAcce: [
          { required: true, message: "验收人不能为空", trigger: "blur" }
        ],
        stoStor: [
          { required: true, message: "保管人不能为空", trigger: "blur" }
        ],
      },
      stoAcceAllList:[],
      stoStorAllList:[],
      dialogImageUrl: '',
        dialogVisible: false
    };
  },
  created() {
    this.getList();
    // this.stoAttnList();
    // this.stoAcceList();
    // this.stoStorList();
  },
  methods: {
    handlePreview() {
      this.isShowPics = true
    },
    onPreview() {
      this.isShowPics = true
    },
    // 关闭查看器
    closeViewer() {
      this.isShowPics = false
    },
    checkImg(){
      if (this.ids.length!=1) {
        this.open3();
      } else{
        this.queryParams.id = this.ids[0]
        console.log(this.queryParams.id);
        getStorage(this.queryParams.id).then(response => {
        this.form1 = response.data;
        console.log(this.form1);
        if (this.form1.kpStatus!=1) {
          this.open4()
        }else if (this.form1.imgSrc=== 'undefined' || this.form1.imgSrc == null || this.form1.imgSrc == '') {
          this.open5();
        }else{
        this.isShowPics = true
        this.srcList[0] = 'http://localhost/dev-api'+this.form1.imgSrc;
      }
      });
      }
    },
    open3() {
        this.$message({
          message: '请选择一条数据',
          type: 'warning'
        });
      },
      open4() {
        this.$message({
          message: '请选择已开票的数据',
          type: 'warning'
        });
      },
      open5() {
        this.$message({
          message: '当前开票下没有上传凭证',
          type: 'warning'
        });
      },
    kaipiao(){
      if (this.ids.length!=1) {
        this.open3();
      } else{
        this.open1 = true
      }
    },
    
    /** 查询【入库单】列表 */
    stoAttnList() {
      this.loading = true;
      this.queryParams.roleId = 5;
      stoAttnList(this.queryParams).then(response => {
        this.stoAttnAllList = response.rows;
        this.loading = false;
      });
    },
    onSubmit() {
      this.form1.id = this.ids[0]
      this.form1.kpStatus = 1;
       updateStorage(this.form1).then(response => {
              this.$modal.msgSuccess("开票成功");
              this.open1 = false;
              this.getList();
              this.ids = null;
            });
      },
      onSubmit1(){
      this.form1.id = this.ids[0]
      this.form1.kpStatus = 0;
       updateStorage(this.form1).then(response => {
              this.$modal.msgWarning("取消开票");
              this.open1 = false;
              this.getList();
              this.ids = null;
            });
      },
    sumPrice(){
      this.form.stoMoney = this.form.stoNum*this.form.stoPrice;
      console.log(12312);
      
    },
    stoAcceList() {
      this.loading = true;
      this.queryParams.roleId = 6;
      stoAttnList(this.queryParams).then(response => {
        this.stoAcceAllList = response.rows;
        this.loading = false;
      });
    },
    stoStorList() {
      this.loading = true;
      this.queryParams.roleId = 7;
      stoAttnList(this.queryParams).then(response => {
        this.stoStorAllList = response.rows;
        this.loading = false;
      });
    },
    getList() {
      this.loading = true;
      listStorage(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        this.storageList = response.rows;
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
        maxDate:null,
        minDate:null,
        minMoney:null,
        maxMoney:null,
        roleId:0,
        pageNum: 1,
        pageSize: 10,
        stoId: null,
        stoName: null,
        stoType: null,
        stoUnit: null,
        stoNum: null,
        stoPrice: null,
        stoMoney: null,
        stoRemark: null,
        stoAttn: null,
        stoAcce: null,
        stoStor: null,
        stoDate: null,
        entryDate: null,
        entryId: null,
        editDate: null,
        editId: null
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
      this.dateRange=[];
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
      this.title = "添加入库单";
      console.log(this.value2[0]);
      console.log(this.value2[1]);

    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getStorage(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改入库单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateStorage(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addStorage(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
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
      this.$modal.confirm('是否确认删除编号为"' + ids + '"【入库单】？').then(function() {
        return delStorage(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      if (this.ids.length>0) {
        this.queryParams.ids=this.ids
      } else{
        this.queryParams.ids=null
      }
      this.download('system/storage/export', {
        ...this.queryParams
      }, `storage_${new Date().getTime()}.xlsx`)
    },
  },
  computed:{
        c() {
          if (isNaN(parseInt(this.form.stoNum) * parseInt(this.form.stoPrice))) {
            this.form.stoMoney = parseInt(this.form.stoNum) * parseInt(this.form.stoPrice);
            return ''
          } else {
            this.form.stoMoney = parseInt(this.form.stoNum) * parseInt(this.form.stoPrice);
            return parseInt(this.form.stoNum) * parseInt(this.form.stoPrice)
          }
    }
    }
};
</script>
