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
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              type="primary"
              icon="el-icon-plus"
              size="mini"
              v-hasPermi="['custom:order:add']"
              @click="handleAdd"
              >创建订单
            </el-button>
          </el-col>
        </el-row>

        <el-table
          :data="orderList"
          row-key="orderId"
          default-expand-all
          :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
        >
          <el-table-column
            label="审核状态"
            prop="reviewStatus"
            align="center"
            width="120"
          >
            <template slot-scope="scope">
              <el-tag
                v-if="scope.row.orderType === 'main'"
                :type="scope.row.reviewStatus === 'yes' ? 'success' : 'danger'"
                disable-transitions
              >
                {{ scope.row.reviewStatus === "yes" ? "已审核" : "未审核" }}
              </el-tag>
              <el-tag
                v-if="scope.row.orderType === 'virtual'"
                disable-transitions
              >
                分单
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column
            label="成交时间"
            prop="orderTime"
            align="center"
          ></el-table-column>
          <el-table-column
            label="成交金额"
            prop="amount"
            align="center"
          ></el-table-column>
          <el-table-column label="服务时长" prop="serveTime" align="center" />
          <el-table-column label="操作" align="center" width="120px">
            <template slot-scope="scope">
              <el-button
                v-if="scope.row.orderType === 'main'"
                size="mini"
                type="text"
                @click="handleOnDetailClick(scope.row)"
                >详情</el-button
              >
              <el-button
                v-if="scope.row.orderType === 'main'"
                size="mini"
                type="text"
                @click="handleOnEditClick(scope.row)"
                v-hasPermi="['custom:order:edit']"
                >修改</el-button
              >
              <el-button
                v-if="scope.row.orderType === 'main'"
                size="mini"
                type="text"
                v-hasPermi="['custom:order:remove']"
                @click="handleOnDeleteClick(scope.row)"
                >删除</el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-drawer>

    <!-- 新增订单 -->
    <!--<create-order-dialog ref="cusCreateOrderDialogRef" />-->
    <!-- 订单编辑 -->
    <edit-order-dialog ref="cusEditOrderDialogRef" />

    <order-detail ref="orderDetailRef" />
  </div>
</template>
<script>
import { listOrder, delOrder } from "@/api/custom/order";
import OrderEdit from "@/components/OrderEdit";
import OrderAdd from "@/components/OrderAdd";
import OrderDetail from "@/components/OrderDetail";

export default {
  name: "CustomerOrderDrawer",
  components: {
    "edit-order-dialog": OrderEdit,
    "order-detail": OrderDetail,
    //"create-order-dialog": OrderAdd
  },
  data() {
    return {
      visible: false,
      title: "",
      data: undefined,
      orderList: [],
    };
  },
  methods: {
    showDrawer(data) {
      // console.log(data);
      this.data = data;
      if (!this.data) {
        return;
      }
      this.title = `「${this.data.name}」订单列表`;
      this.fetchOrderList(data.id);
    },
    fetchOrderList(cusId) {
      listOrder({ cusId }).then((res) => {
        this.orderList = res.rows.reduce((arr, cur) => {
          const tarOrder = arr.find((ord) => ord.startTime === cur.startTime);
          if (tarOrder) {
            if (!tarOrder.children) {
              const firstObj = JSON.parse(JSON.stringify(tarOrder));
              tarOrder.children = [{ ...firstObj, orderType: "main" }];
            }
            tarOrder.amount += cur.amount;
            tarOrder.orderId += cur.orderId;
            tarOrder.orderType = "virtual";
            tarOrder.children.push({ ...cur, orderType: "main" });
          } else {
            cur.orderType = "main";
            arr.push(cur);
          }
          return arr;
        }, []);

        this.visible = true;
      });
    },
    handleAdd() {
      this.$refs.cusEditOrderDialogRef.showDialog(
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
      this.$refs.orderDetailRef.showDialog(data.orderId);
    },
    handleOnEditClick(data) {
      this.$refs.cusEditOrderDialogRef.showDialog(data, () => {
        this.fetchOrderList(this.data.id);
      });
    },
    handleOnDeleteClick(data) {
      const orderIds = data.orderId || this.ids;
      this.$confirm(
        '是否确认删除销售订单编号为"' + orderIds + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delOrder(orderIds);
        })
        .then(() => {
          this.fetchOrderList(this.data.id);
          this.msgSuccess("删除成功");
        })
        .catch(function () {});
    },
  },
};
</script>
<style  lang="scss" scoped>
/deep/ :focus {
  outline: 0;
}
</style>
