<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="名字" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入名字"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="手机号" prop="phone">
        <el-input
          v-model="queryParams.phone"
          placeholder="请输入手机号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

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
          v-hasPermi="['custom:customer:add']"
        >新增</el-button>
      </el-col>
      <!--<el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['custom:customer:edit']"
        >修改</el-button>
      </el-col>-->
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['custom:customer:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['custom:customer:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5" title="营养体征调查链接">
        <el-button
          type="primary"
          size="mini"
        ><a href="http://sign.shengtangdiet.com/question" target='_blank'>营养体征调查</a></el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="customerList" stripe @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!--<el-table-column label="序号" align="center" prop="id" />-->
      <el-table-column label="创建时间" align="center" prop="createTime" width="180" fixed="left">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="姓名" align="center" prop="name" width="120" fixed="left"/>
      <el-table-column label="手机号" align="center" prop="phone" width="120" fixed="left"/>
      <el-table-column label="性别" align="center" prop="sex" width="100">
        <template slot-scope="scope">
          {{scope.row.sex == 0 ? `男` : '女'}}
        </template>
      </el-table-column>
      <el-table-column label="年龄(岁)" align="center" prop="age" width="100"/>
      <el-table-column label="身高(厘米)" align="center" prop="tall" width="100"/>
      <el-table-column label="体重(斤)" align="center" prop="weight" width="100"/>
      <el-table-column label="北方、南方" align="center" prop="position" width="100">
        <template slot-scope="scope">
          {{scope.row.position == 0 ? `南方` : '北方'}}
        </template>
      </el-table-column>
      <el-table-column label="病史" align="center" prop="signList" width="120">
        <template slot-scope="scope">
          <el-button v-show="getSignString(scope.row.signList).length > 10" type="text" @click="openFormDialog('病史', getSignString(scope.row.signList))">点击查看</el-button>
          <span v-show="getSignString(scope.row.signList).length <= 10">{{getSignString(scope.row.signList)}}</span>
        </template>
      </el-table-column>
      <el-table-column label="忌口或过敏源" align="center" prop="dishesIngredientId" width="120">
        <template slot-scope="scope">
          <el-button v-show="scope.row.dishesIngredientId.length > 10" type="text" @click="openFormDialog('忌口或过敏源', scope.row.dishesIngredientId)">点击查看</el-button>
          <span v-show="scope.row.dishesIngredientId.length <= 10">{{scope.row.dishesIngredientId}}</span>
        </template>
      </el-table-column>
      <el-table-column label="是否便秘" align="center" prop="constipation" width="100">
        <template slot-scope="scope">
          {{scope.row.constipation == 0 ? `是` : '否'}}
        </template>
      </el-table-column>
      <el-table-column label="是否熬夜失眠" align="center" prop="staylate" width="100">
        <template slot-scope="scope">
          {{scope.row.staylate == 0 ? `是` : '否'}}
        </template>
      </el-table-column>
      <el-table-column label="是否经常运动" align="center" prop="motion" width="100">
        <template slot-scope="scope">
          {{scope.row.motion == 0 ? `是` : '否'}}
        </template>
      </el-table-column>
      <el-table-column label="饮食方式" align="center" prop="makeFoodType" width="100">
        <template slot-scope="scope">
          {{scope.row.makeFoodType == 0 ? `自己做` : '外面吃'}}
        </template>
      </el-table-column>
      <el-table-column label="饮食备注" align="center" prop="remarks" width="100">
        <template slot-scope="scope">
          <el-button v-show="scope.row.remarks != null && scope.row.remarks.length > 10" type="text" @click="openFormDialog('饮食备注', scope.row.remarks)">点击查看</el-button>
          <span v-show="scope.row.remarks == null && scope.row.remarks.length <= 10">{{scope.row.remarks}}</span>
        </template>
      </el-table-column>
      <el-table-column label="饮食特点" align="center" prop="makeFoodTaste" width="100">
        <template slot-scope="scope">
          {{scope.row.makeFoodTaste == 0 ? `清淡` : '重口味'}}
        </template>
      </el-table-column>
      <el-table-column label="工作职业" align="center" prop="vocation" width="100">
        <template slot-scope="scope">
          {{scope.row.vocation}}
        </template>
      </el-table-column>
      <el-table-column label="是否上夜班" align="center" prop="night" width="100">
        <template slot-scope="scope">
          {{scope.row.night == 0 ? `是` : '否'}}
        </template>
      </el-table-column>
      <el-table-column label="久坐多还是运动多" align="center" prop="walk" width="100">
        <template slot-scope="scope">
          {{scope.row.walk == 0 ? `久坐多` : '走动多'}}
        </template>
      </el-table-column>
      <el-table-column label="是否浑身乏力" align="center" prop="weakness" width="100">
        <template slot-scope="scope">
          {{scope.row.weakness == 0 ? `是` : '否'}}
        </template>
      </el-table-column>
      <el-table-column label="是否减脂反弹" align="center" prop="rebound" width="100">
        <template slot-scope="scope">
          {{scope.row.rebound == 0 ? `是` : '否'}}
        </template>
      </el-table-column>
      <el-table-column label="意识到生活习惯是减脂关键" align="center" prop="crux" width="100">
        <template slot-scope="scope">
          {{scope.row.crux == 0 ? `是` : '否'}}
        </template>
      </el-table-column>
      <el-table-column label="睡觉时间" align="center" prop="sleepTime" width="100">
        <template slot-scope="scope">
          {{scope.row.sleepTime}}点
        </template>
      </el-table-column>
      <el-table-column label="起床时间" align="center" prop="getupTime" width="100">
        <template slot-scope="scope">
          {{scope.row.getupTime}}点
        </template>
      </el-table-column>
      <el-table-column label="方便沟通时间" align="center" prop="connectTime" width="100">
        <template slot-scope="scope">
          {{scope.row.connectTime}}点
        </template>
      </el-table-column>
      <el-table-column label="湿气数据" align="center" prop="bloodData" width="100">
        <template slot-scope="scope">
          {{scope.row.bloodData}}
        </template>
      </el-table-column>
      <el-table-column label="气血数据" align="center" prop="moistureDate" width="120">
        <template slot-scope="scope">
          {{scope.row.moistureDate}}
        </template>
      </el-table-column>
      <el-table-column label="减脂经历" align="center" prop="experience" width="100">
        <template slot-scope="scope">
          <el-button v-show="scope.row.experience != null && scope.row.experience.length > 10" type="text" @click="openFormDialog('减脂经历', scope.row.experience)">点击查看</el-button>
          <span v-show="scope.row.experience == null || scope.row.experience.length <= 10">{{scope.row.experience}}</span>
        </template>
      </el-table-column>
      <el-table-column label="减脂遇到的困难" align="center" prop="difficulty" width="100">
        <template slot-scope="scope">
          <el-button v-show="scope.row.difficulty != null && scope.row.difficulty.length > 10" type="text" @click="openFormDialog('减脂遇到的困难', scope.row.difficulty)">点击查看</el-button>
          <span v-show="scope.row.difficulty == null || scope.row.difficulty.length <= 10">{{scope.row.difficulty}}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="comments" width="100">
        <template slot-scope="scope">
          <el-button v-show="scope.row.comments != null && scope.row.comments.length > 10" type="text" @click="openFormDialog('备注', scope.row.comments)">点击查看</el-button>
          <span v-show="scope.row.comments == null || scope.row.comments.length <= 10">{{scope.row.comments}}</span>
        </template>
      </el-table-column>
      <!--<el-table-column label="主营养师" align="center" prop="mainDietitian" />
      <el-table-column label="营养师助理" align="center" prop="assistantDietitian" />
      <el-table-column label="售后营养师" align="center" prop="afterDietitian" />
      <el-table-column label="销售人员" align="center" prop="salesman" />
      <el-table-column label="负责人" align="center" prop="chargePerson" />-->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="120" fixed="right">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['custom:customer:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['custom:customer:remove']"
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

    <el-dialog :title="formDialog.title" :visible.sync="formDialog.show" width="30%" append-to-body center>
      <span>{{formDialog.content}}</span>
      <span slot="footer" class="dialog-footer">
      <el-button @click="formDialog.show = false">取 消</el-button>
      <!--<el-button type="primary" @click="experience_dialog = false">确 定</el-button>-->
      </span>
    </el-dialog>

    <!-- 添加或修改客户信息对话框 -->
    <el-dialog :title="title" v-if="open" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
          <!--<p>现在要先为您建立更加详细的档案，以便为您定制专属的减脂计划</p>-->
          <el-form-item label="真实姓名" prop="name">
            <el-input v-model="form.name" placeholder="请输入真实姓名" />
          </el-form-item>
          <el-form-item label="手机号" prop="phone">
            <el-input v-model="form.phone" placeholder="请输入手机号" />
          </el-form-item>
          <!--<el-form-item label="邮箱" prop="email">
            <el-input v-model="form.email" placeholder="请输入邮箱" />
          </el-form-item>-->
          <!--<el-form-item label="您的居住地址" prop="address">
            <el-input v-model="form.address" placeholder="请输入地址" />
          </el-form-item>-->
          <el-form-item label="性别" prop="sex">
            <el-select v-model="form.sex" placeholder="请选择性别">
              <el-option label="男" :value="parseInt('0')" />
              <el-option label="女" :value="parseInt('1')" />
            </el-select>
          </el-form-item>
          <el-form-item label="年龄(岁)" prop="age">
            <el-input v-model="form.age" placeholder="请输入年龄" />
          </el-form-item>
          <el-form-item label="身高(厘米)" prop="tall" >
            <el-input v-model="form.tall" placeholder="请输入身高" />
          </el-form-item>
          <el-form-item label="体重(斤)" prop="weight">
            <el-input v-model="form.weight" placeholder="请输入体重" />
          </el-form-item>
          <el-form-item label="南方人还是北方人" prop="position">
            <el-select v-model="form.position" placeholder="请选择">
              <el-option label="南方" :value="parseInt('0')" />
              <el-option label="北方" :value="parseInt('1')" />
            </el-select>
          </el-form-item>
          <el-form-item label="病史(多选)" prop="physicalSignsId">
            <el-select v-model="form.physicalSignsId" multiple placeholder="请选择">
              <el-option
                v-for="physicalSign in physicalSignsList"
                :key="physicalSign.id" :label="physicalSign.name" :value="physicalSign.id">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="忌口或过敏源" prop="dishesIngredientId">
            <el-input v-model="form.dishesIngredientId" placeholder="请输入名称" />
          </el-form-item>
          <!--<el-form-item label="您的照片" prop="photo">
            <el-input v-model="form.photo" placeholder="请输入客户照片" />
          </el-form-item>-->
          <el-form-item label="是否便秘" prop="constipation">
            <el-select v-model="form.constipation" placeholder="请选择">
              <el-option label="是" :value="parseInt('0')" />
              <el-option label="否" :value="parseInt('1')" />
            </el-select>
          </el-form-item>
          <el-form-item label="是否熬夜、失眠" prop="staylate">
            <el-select v-model="form.staylate" placeholder="请选择">
              <el-option label="是" :value="parseInt('0')" />
              <el-option label="否" :value="parseInt('1')" />
            </el-select>
          </el-form-item>
          <el-form-item label="是否经常运动" prop="motion">
            <el-select v-model="form.motion" placeholder="请选择">
              <el-option label="是" :value="parseInt('0')" />
              <el-option label="否" :value="parseInt('1')" />
            </el-select>
          </el-form-item>
          <el-form-item label="饮食方式" prop="makeFoodType">
            <el-select v-model="form.makeFoodType" placeholder="请选择">
              <el-option label="自己做" :value="parseInt('0')" />
              <el-option label="外面吃" :value="parseInt('1')" />
            </el-select>
          </el-form-item>
        <el-form-item label="饮食备注" prop="remarks">
          <el-input type="textarea" placeholder="请输入内容" v-model="form.remarks" maxlength="200" show-word-limit rows="5"></el-input>
          <!--<el-input v-model="form.remarks" placeholder="请输入备注信息" />-->
        </el-form-item>
          <el-form-item label="饮食特点" prop="makeFoodTaste">
            <el-select v-model="form.makeFoodTaste" placeholder="请选择">
              <el-option label="清淡" :value="parseInt('0')" />
              <el-option label="重口味" :value="parseInt('1')" />
            </el-select>
          </el-form-item>
          <el-form-item label="工作职业" prop="vocation">
            <el-input v-model="form.vocation" placeholder="请输入工作职业" />
          </el-form-item>
          <el-form-item label="是否上夜班" prop="night">
            <el-select v-model="form.night" placeholder="请选择">
              <el-option label="是" :value="parseInt('0')" />
              <el-option label="否" :value="parseInt('1')" />
            </el-select>
          </el-form-item>
          <el-form-item label="平时久坐多还是走动多" prop="walk">
            <el-select v-model="form.walk" placeholder="请选择">
              <el-option label="久坐多" :value="parseInt('0')" />
              <el-option label="走动多" :value="parseInt('1')" />
            </el-select>
          </el-form-item>

          <el-form-item label="是否浑身乏力" prop="weakness">
            <el-select v-model="form.weakness" placeholder="请选择">
              <el-option label="是" :value="parseInt('0')" />
              <el-option label="否" :value="parseInt('1')" />
            </el-select>
          </el-form-item>
          <el-form-item label="是否减脂反弹" prop="rebound">
            <el-select v-model="form.rebound" placeholder="请选择">
              <el-option label="是" :value="parseInt('0')" />
              <el-option label="否" :value="parseInt('1')" />
            </el-select>
          </el-form-item>
          <el-form-item label="是否意识到生活习惯是减脂关键" prop="crux">
            <el-select v-model="form.crux" placeholder="请选择">
              <el-option label="是" :value="parseInt('0')" />
              <el-option label="否" :value="parseInt('1')" />
            </el-select>
          </el-form-item>
          <el-form-item label="睡觉时间" prop="sleepTime">
            <el-time-select v-model="form.sleepTime" :picker-options="{start: '00:00',step: '01:00',end: '24:00'}" placeholder="请选择时间" :editable=false />
          </el-form-item>
          <el-form-item label="起床时间" prop="getupTime">
            <el-time-select v-model="form.getupTime" :picker-options="{start: '00:00',step: '01:00',end: '24:00'}" placeholder="请选择时间" :editable=false />
          </el-form-item>
          <el-form-item label="方便沟通时间" prop="connectTime">
            <el-time-select v-model="form.connectTime" :picker-options="{start: '00:00',step: '01:00',end: '24:00'}" placeholder="请选择时间" :editable=false />
          </el-form-item>
          <!--<el-form-item label="备注信息" prop="remarks">
            <el-input v-model="form.remarks" placeholder="请输入备注信息" />
          </el-form-item>-->
          <!--<p>好的，我现在给您测一下湿气和气血，有以下出现情况的请直接选择</p>-->
          <el-form-item label="湿气(多选)" prop="bloodData">
            <el-checkbox-group v-model="form.bloodData">
              <el-checkbox v-for="bloodItem in bloodDataList" :label="bloodItem.dictValue"  :key="bloodItem.dictValue">{{bloodItem.dictLabel}}</el-checkbox>
            </el-checkbox-group>
          </el-form-item>
          <el-form-item label="气血(多选)" prop="moistureDate">
            <el-checkbox-group v-model="form.moistureDate">
              <el-checkbox v-for="moistureItem in moistureDataList" :label="moistureItem.dictValue" :key="moistureItem.dictValue">{{moistureItem.dictLabel}}</el-checkbox>
            </el-checkbox-group>
          </el-form-item>

          <el-form-item label="减脂经历（重点详细填写）" prop="experience">
            <el-input type="textarea" placeholder="请输入内容" v-model="form.experience" maxlength="200" show-word-limit rows="5"></el-input>
          </el-form-item>
          <el-form-item label="减脂遇到的困难" prop="difficulty">
            <el-input type="textarea" placeholder="请输入内容" v-model="form.difficulty" maxlength="200" show-word-limit rows="5"></el-input>
          </el-form-item>
          <el-form-item label="备注" prop="comments">
          <el-input type="textarea" placeholder="请输入其他需要反馈的内容" v-model="form.comments" maxlength="200" show-word-limit rows="5"></el-input>
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
  import { listCustomer, getCustomer, delCustomer, addCustomer, updateCustomer, exportCustomer } from "@/api/custom/customer";
  import { listPhysicalSigns } from "@/api/custom/physicalSigns";
  import dayjs from 'dayjs';
  export default {
    name: "Customer",
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
        // 客户信息表格数据
        customerList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          name: null,
          phone: null
        },
        // 表单参数
        form: {},
        //气血数据
        bloodDataList:[],
        //湿气数据
        moistureDataList:[],
        //体征数据
        physicalSignsList:[],
        // 表单校验
        rules: {
          name: [
            { required: true, trigger: "blur", message: "请填写姓名" },
            { min: 1, max: 20, trigger: "blur", message: "姓名过长" }
          ],
          phone: [
            { required: true, trigger: "blur", message: "请填写手机号" },
            { required: true, trigger: "blur", message: "请填写正确的手机号" },
            { required: true, trigger: "blur", pattern: /^[0-9]{5,11}$/ , message: '手机号格式不正确'}
          ],
          sex:[
            { required: true, trigger: "blur", message: "请选择性别" }
          ],
          age:[
            { required: true, trigger: "blur", message: "请填写年龄" },
            {required: true, trigger: "blur", pattern: /^[1-9]\d*$/ , message: '年龄格式不正确'}
          ],
          tall:[
            { required: true, trigger: "blur", message: "请填写身高" },
            {required: true, trigger: "blur", pattern: /^[1-9]\d*$/ , message: '身高格式不正确'}
          ],
          weight:[
            { required: true, trigger: "blur", message: "请填写体重" },
            {required: true, trigger: "blur", pattern: /^[1-9]\d*$/ , message: '体重格式不正确'}
          ],
          constipation:[
            { required: true, trigger: "blur", message: "请选择一个答案" }
          ],
          staylate:[
            { required: true, trigger: "blur", message: "请选择一个答案" }
          ],
          motion:[
            { required: true, trigger: "blur", message: "请选择一个答案" }
          ],
          makeFoodType:[
            { required: true, trigger: "blur", message: "请选择一个答案" }
          ],
          makeFoodTaste:[
            { required: true, trigger: "blur", message: "请选择一个答案" }
          ],
          walk:[
            { required: true, trigger: "blur", message: "请选择一个答案" }
          ],
          weakness:[
            { required: true, trigger: "blur", message: "请选择一个答案" }
          ],
          rebound:[
            { required: true, trigger: "blur", message: "请选择一个答案" }
          ],
          crux:[
            { required: true, trigger: "blur", message: "请选择一个答案" }
          ],
          position:[
            { required: true, trigger: "blur", message: "请选择一个答案" }
          ],
          sleepTime:[
            { required: true, trigger: "blur", message: "请选择一个答案" }
          ],
          getupTime:[
            { required: true, trigger: "blur", message: "请选择一个答案" }
          ],
          connectTime:[
            { required: true, trigger: "blur", message: "请选择一个答案" }
          ],
          night:[
            { required: true, trigger: "blur", message: "请选择一个答案" }
          ],
          /*bloodData:[
            { required: true, trigger: "blur", message: "请测试气血" }
          ],
          moistureDate:[
            { required: true, trigger: "blur", message: "请测试湿气" }
          ]*/
        },
        //查看表单内容
        formDialog:{
          title: "",
          show: false,
          content: ""
        }
      };
    },
    created() {
      this.getList();
      this.getPhysicalSign();
      this.getDicts("sys_blood_data").then(response => {
        this.bloodDataList = response.data;
      });
      this.getDicts("sys_moisture_data").then(response => {
        this.moistureDataList = response.data;
      });
    },
    methods: {
      /** 查询客户信息列表 */
      getList() {
        this.loading = true;
        listCustomer(this.queryParams).then(response => {
          this.customerList = response.rows;
          this.total = response.total;
          this.loading = false;
      });
      },
      getSignString(signList){
          if(signList == null){
             return "";
          }
          let signStr = "";
          for(let i in signList){
             signStr += "，"+signList[i].name;
          }
          return signList.length > 0 ? signStr.substring(1) : signStr;
      },
      /** 查询体征列表 */
      getPhysicalSign() {
        listPhysicalSigns().then(response => {
          this.physicalSignsList = response.rows;
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
          id: null,
          customerId: null,
          name: '',
          phone: '',
          address: "",
          sex: 1,
          age: null,
          tall: null,
          weight: null,
          physicalSignsId: [],
          dishesIngredientId: null,
          photo: null,
          constipation: 0,
          staylate: 0,
          motion: 1,
          makeFoodType: 1,
          makeFoodTaste: 1,
          walk: 0,
          difficulty: null,
          weakness: 0,
          rebound: 0,
          crux: 0,
          position: 1,
          sleepTime: null,
          getupTime: null,
          connectTime: null,
          remarks: null,
          bloodData: [],
          moistureDate: [],
          night: 0,
          vocation: null,
          experience: null,
          comments: null
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
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "添加客户体征";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        getCustomer(id).then(response => {
          let cusMessage = response.data;
          cusMessage.bloodData = (cusMessage.bloodData != null && cusMessage.bloodData.trim() != "") ? cusMessage.bloodData.split(",") : [];
          cusMessage.moistureDate = (cusMessage.moistureDate != null && cusMessage.moistureDate.trim() != "") ? cusMessage.moistureDate.split(",") : [];
          cusMessage.physicalSignsId = (cusMessage.physicalSignsId != null && cusMessage.physicalSignsId.trim() != "") ? cusMessage.physicalSignsId.split(",") : [];
          for(let i =0; i < cusMessage.physicalSignsId.length; i++) {
            cusMessage.physicalSignsId[i] = parseInt(cusMessage.physicalSignsId[i]);
            console.log(cusMessage.physicalSignsId[i]);
          }
          cusMessage.sleepTime = this.deltime(cusMessage.sleepTime);
          cusMessage.getupTime = this.deltime(cusMessage.getupTime);
          cusMessage.connectTime = this.deltime(cusMessage.connectTime);
          this.form = cusMessage;
          this.open = true;
          this.title = "修改客户体征";
      });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id != null) {
              updateCustomer(this.getDealSubmitData()).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("修改成功");
                  this.open = false;
                  this.getList();
                }
              });
            } else {
              addCustomer(this.getDealSubmitData()).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("新增成功");
                  this.open = false;
                  this.getList();
                }
              });
            }
          }else{
            this.$message({
              message: '数据未填写完整',
              type: 'warning'
            });
          }
        });
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const ids = row.id || this.ids;
        this.$confirm('是否确认删除客户信息编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delCustomer(ids);
        }).then(() => {
          this.getList();
        this.msgSuccess("删除成功");
      }).catch(function() {});
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否确认导出当前列表中所有客户体征数据?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportCustomer(queryParams);
        }).then(response => {
          this.download(response.msg);
         }).catch(function() {});
      },
      getDealSubmitData(){
        let cusMessage = Object.assign({}, this.form);
        cusMessage.bloodData = cusMessage.bloodData != null ? cusMessage.bloodData.join(",") : null;
        cusMessage.moistureDate = cusMessage.moistureDate != null ? cusMessage.moistureDate.join(",") : null;
        cusMessage.physicalSignsId = cusMessage.physicalSignsId != null ? cusMessage.physicalSignsId.join(",") : null;
        cusMessage.sleepTime = cusMessage.sleepTime.substring(0,2);
        cusMessage.getupTime = cusMessage.getupTime.substring(0,2);
        cusMessage.connectTime = cusMessage.connectTime.substring(0,2);
        return cusMessage;
      },
      //睡觉、起床、沟通时间处理
      deltime(time){
        if(time != null){
          return time > 9 ? (time + ":00") : ("0"+time+":00");
        }
        return null;
      },
      openFormDialog(title, content){
          this.formDialog.title = title;
          this.formDialog.content = content;
          this.formDialog.show = true;
      }
    }
  };
</script>
