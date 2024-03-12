<template>
  <el-dialog title="发起合同" :visible.sync="open" width="500px" append-to-body>
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-form-item label="合同名称" prop="name">
        <el-input disabled v-model="form.name" placeholder="请输入合同名称" />
      </el-form-item>
      <el-form-item label="签约人" prop="consultId">
        <el-select v-model="form.consultId" :disabled="form.id != null" clearable filterable>
          <el-option
            v-for="item in consultList"
            :key="item.id"
            :label="item.nickName"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="入驻类型" prop="type">
        <el-select v-model="form.type" placeholder="请选择" @change="changeType">
          <el-option
            v-for="item in types"
            :key="item.value"
            :label="item.label"
            :value="parseInt(item.value)"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="入驻费用" prop="money">
        <el-input-number v-model="form.money" :min="0" /> 元/年
      </el-form-item>
      <el-form-item label="结算比率" prop="ratio">
        <el-input-number v-model="form.ratio" :min="0" /> %
      </el-form-item>
      <el-form-item label="合同时间" prop="time">
        <el-date-picker
          v-model="form.time"
          style="width: 280px;"
          value-format="yyyy-MM-dd"
          format="yyyy-MM-dd"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          @change="onChangeTime"
        />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { exist, addContract, relaunch } from "@/api/psychology/contract";

export default {
  name: "addForm",
  props: {
    consultList: {
      type: Array,
      default: () => []
    }
  },
  data() {
    return {
      open: false,
      type: 'add',// tryAdd
      types: this.$constants.partnerTypes,
      statusList: this.$constants.contractStatus,
      form: {},
      // 表单校验
      rules: {
        consultId: [
          { required: true, message: "请选择签约人", trigger: "change" }
        ],
        type: [
          { required: true, message: "请选择入驻类型", trigger: "change" }
        ],
        money: [
          { required: true, message: "请输入入驻费用", trigger: "blur" }
        ],
        ratio: [
          { required: true, message: "请输入结算比例", trigger: "blur" }
        ],
        time: [
          { required: true, message: "请选择合同时间", trigger: "change" }
        ]
      }
    }
  },
  methods: {
    init() {
      this.form = {
        id: null,
        name: '口袋心理平台入驻协议'
      }
      this.open = true
      console.log(2222)
    },
    initData(data) {
      this.form = data
      console.log(this.form)
      this.open = true
    },
    changeType(val) {
      switch (val) {
        case 1:
          this.form.money = 700
          this.form.ratio = 50
          break
        case 2:
          this.form.money = 3200
          this.form.ratio = 70
          break
        case 3:
          this.form.money = 3000
          this.form.ratio = 70
          break
        case 4:
          this.form.money = 5800
          this.form.ratio = 70
          break
      }
    },
    onChangeTime(val) {
      if (val && val.length > 0) {
        this.form.startTime = val[0] + ' 00:00:00'
        this.form.endTime = val[1] + ' 23:59:59'
      }
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(async valid => {
        if (valid) {
          const that = this
          that.form.consultName = this.consultList.find(item => item.id === that.form.consultId).nickName
          that.form.status = '1'
          that.form.name = '口袋心理平台入驻协议'
          // 查询是否存在有效的合同
          const req = {
            consultId: that.form.consultId,
            startTime: that.form.startTime,
            endTime: that.form.endTime
          }
          const res = await exist(req);
          if (res.code === 200 && res.data > 0) {
            return that.$message.error('选择的合同时间段内存在有效合同，请先终止原合同再发起新合同。')
          }

          that.$modal.confirm('确认发起合同吗？').then(function() {
            if (that.form.id != null) {
              relaunch(that.form).then(response => {
                that.$modal.msgSuccess("发起成功");
                that.cancel()
                that.$emit('handleOk')
              });
            } else {
              addContract(that.form).then(response => {
                that.$modal.msgSuccess("发起成功");
                that.cancel()
                that.$emit('handleOk')
              });
            }
          }).then(() => {
          }).catch(() => {});
        }
      });
    },
    cancel() {
      this.form = {}
      this.open = false
    }
  }
}
</script>

<style scoped>

</style>
