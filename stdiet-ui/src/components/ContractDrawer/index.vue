<template>
  <div>
    <el-drawer
      :title="title"
      :close-on-press-escape="false"
      :visible.sync="visible"
      @closed="handleOnClosed"
      size="40%"
    >
      <div class="app-container">
        <!--<el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              type="primary"
              icon="el-icon-plus"
              size="mini"
              @click="handleAdd"
              >创建合同
            </el-button>
          </el-col>
        </el-row>-->

        <el-table :data="contractList">
          <el-table-column label="合同编号" align="center" prop="id"  width="150"/>
          <el-table-column label="合同状态" align="center" prop="status" width="80" >
            <template slot-scope="scope">
              <el-tag
                :type="scope.row.status === 'yes' ? 'success' : 'danger'"
                disable-transitions>
                {{scope.row.status === 'yes' ? '已签订':'未签订'}}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column label="客户姓名" align="center" prop="name" width="200"/>

          <el-table-column label="合同地址" align="center" prop="path" width="80">
            <template slot-scope="scope">
              <el-button type="text" icon="el-icon-copy-document" @click="handleCopy(scope.row.path)" class="copyBtn"
                         :data-clipboard-text="copyValue">复制
              </el-button>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" width="180">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-view"
                @click="handleOnDetailClick(scope.row)"
              >详情
              </el-button>
              <el-button
                v-if="scope.row.status==='yes'"
                size="mini"
                type="text"
                icon="el-icon-view"
                @click="handleLook(scope.row.path)"
              >查看
              </el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleOnDeleteClick(scope.row)"
                v-hasPermi="['custom:contract:remove']"
              >删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-drawer>

    <!--<create-order-dialog ref="cusCreateOrderDialogRef" />-->

    <contract-detail ref="contractDetailRef" />
  </div>
</template>
<script>
  import {delContract, listContract} from "@/api/custom/contract";
  import ContractDetail from "@/components/ContractDetail";
  import Clipboard from 'clipboard';

export default {
  name: "CustomerContractDrawer",
  components: {
    'contract-detail': ContractDetail
  },
  data() {
    return {
      visible: false,
      title: "",
      data: undefined,
      copyValue:"",
      contractList: [],
    };
  },
  methods: {
    showDrawer(data) {
      this.data = data;
      if (!this.data) {
        return;
      }
      this.title = `「${this.data.name}」合同列表`;
      this.fetchContractList(data.id);
    },
    fetchContractList(cusId) {
      listContract({"customerId": cusId }).then((res) => {
        this.contractList = res.rows;
        this.visible = true;
      });
    },
    handleAdd() {
      this.$refs.cusCreateOrderDialogRef.showDialog(
        {
          customer: this.data.name,
          cusId: this.data.id,
          preSaleId: this.data.salesman,
          afterSaleId: this.data.afterDietitian,
          nutritionistId: this.data.mainDietitian,
          nutriAssisId: this.data.assistantDietitian,
        },
        () => {
          this.fetchOrderList(this.data.id);
        }
      );
    },
    handleOnClosed() {
      this.data = undefined;
    },
    handleOnDetailClick(data) {
      this.$refs.contractDetailRef.showDialog(data.id);
    },
    handleOnEditClick(data) {

    },
    handleOnDeleteClick(data) {
      const contractIds = data.id;
      this.$confirm(
        '是否确认删除姓名为"' + data.name + '"的合同信息?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delContract(contractIds);
        })
        .then(() => {
          this.fetchContractList(this.data.id);
          this.msgSuccess("删除成功");
        })
        .catch(function () {});
    },
    handleCopy(path) {
      this.copyValue = window.location.origin.replace('manage', 'sign') + path;
      const btnCopy = new Clipboard('.copyBtn');
      this.$message({
        message: '拷贝成功',
        type: 'success'
      });
    },
    handleLook(path) {
      const url = window.location.origin.replace('manage', 'sign') + path;
      // const url = "http://stsign.busyinn.com" + path;
      window.open(url, '_blank');
    }
  },
};
</script>
<style  lang="scss" scoped>
/deep/ :focus {
  outline: 0;
}
</style>
