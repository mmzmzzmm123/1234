<!-- 表格配置 -->
<template>
  <div>
    <el-form-item label="表头颜色">
      <avue-color type="textarea"
                  v-model="main.activeOption.headerColor"></avue-color>
    </el-form-item>
    <el-form-item label="表头背景">
      <avue-color type="textarea"
                  v-model="main.activeOption.headerBackgroud"></avue-color>
    </el-form-item>
    <el-form-item label="字体位置">
      <avue-select v-model="main.activeOption.headerTextAlign"
                   :dic="dicOption.textAlign">
      </avue-select>
    </el-form-item>
    <el-form-item label="字体大小">
      <avue-input-number v-model="main.activeOption.fontSize">
      </avue-input-number>
    </el-form-item>
    <el-form-item label="显示行数">
      <avue-input-number v-model="main.activeOption.count">
      </avue-input-number>
    </el-form-item>
    <el-form-item label="开启滚动">
      <avue-switch v-model="main.activeOption.scroll">
      </avue-switch>
    </el-form-item>
    <el-form-item label="开启显隐">
      <avue-switch v-model="main.activeOption.columnShow">
      </avue-switch>
    </el-form-item>
    <template v-if="main.activeOption.scroll">
      <el-form-item label="滚动时间">
        <avue-input-number v-model="main.activeOption.scrollTime">
        </avue-input-number>
      </el-form-item>
      <el-form-item label="滚动行数">
        <avue-input-number v-model="main.activeOption.scrollCount">
        </avue-input-number>
      </el-form-item>
    </template>
    <el-form-item label="线条">
      <avue-switch v-model="main.activeOption.line"></avue-switch>
    </el-form-item>
    <el-form-item label="开启排名">
      <avue-switch v-model="main.activeOption.index"></avue-switch>
    </el-form-item>
    <el-form-item label="文字颜色">
      <avue-color type="textarea"
                  v-model="main.activeOption.bodyColor"></avue-color>
    </el-form-item>
    <el-form-item label="表格背景">
      <avue-color type="textarea"
                  v-model="main.activeOption.bodyBackgroud"></avue-color>
    </el-form-item>
    <el-form-item label="字体位置">
      <avue-select v-model="main.activeOption.bodyTextAlign"
                   :dic="dicOption.textAlign">
      </avue-select>
    </el-form-item>
    <el-form-item label="分割线">
      <avue-color type="textarea"
                  v-model="main.activeOption.borderColor"></avue-color>
    </el-form-item>
    <el-form-item label="奇行颜色">
      <avue-color type="textarea"
                  v-model="main.activeOption.nthColor"></avue-color>
    </el-form-item>
    <el-form-item label="偶行颜色">
      <avue-color type="textarea"
                  v-model="main.activeOption.othColor"></avue-color>
    </el-form-item>
    <avue-crud :option="tableOption"
               :data="main.activeOption.column"
               @row-save="rowSave"
               @row-del="rowDel"
               @row-update="rowUpdate">
      <template slot="menu"
                slot-scope="{row,index,size,type}">
        <el-button @click="trigger(index)"
                   :size="size"
                   :type="type">触发器</el-button>
      </template>
    </avue-crud>

    <!--    <avue-crud :option="triggerOption"-->
    <!--               :data="main.activeOption.triggerColumn"-->
    <!--               @row-save="rowSaveTrigger"-->
    <!--               @row-del="rowDelTrigger"-->
    <!--               @row-update="rowUpdateTrigger">-->

    <!--    </avue-crud>-->

    <el-dialog title="触发器"
               :visible.sync="allTriggerVisible"
               class="avue-dialog avue-dialog--top">

      <el-table :data="temp">
        <el-table-column property="index"
                         label="索引"
                         width="50"></el-table-column>
        <el-table-column property="min"
                         label="下限值"
                         width="60"></el-table-column>
        <el-table-column property="max"
                         label="上限值"
                         width="60"></el-table-column>
        <el-table-column property="backColor"
                         label="背景颜色"></el-table-column>
        <el-table-column property="wordColor"
                         label="文字颜色"></el-table-column>
        <el-table-column fixed="right"
                         label="操作"
                         width="100">
          <template slot-scope="scope">
            <el-button @click="handleDeleteTrigger(scope.$index)"
                       type="text"
                       size="small">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="avue-dialog__footer">
        <el-button @click="allTriggerVisible=false">退出</el-button>
        <el-button @click="addTriggerVisible=true">添加触发器配置</el-button>
        <el-button @click="confirmTrigger">确认当前触发器配置</el-button>
      </div>
    </el-dialog>

    <el-dialog title="添加触发器"
               :visible.sync="addTriggerVisible"
               class="avue-dialog avue-dialog--top">
      <el-form-item label="索引">
        <avue-input v-model="addTempTrigger.index"></avue-input>
      </el-form-item>
      <el-form-item label="下限值">
        <avue-input v-model="addTempTrigger.min"></avue-input>
      </el-form-item>
      <el-form-item label="上限值">
        <avue-input v-model="addTempTrigger.max"></avue-input>
      </el-form-item>
      <el-form-item label="背景颜色">
        <avue-color type="textarea"
                    v-model="addTempTrigger.backColor"></avue-color>
      </el-form-item>
      <el-form-item label="文字颜色">
        <avue-color type="textarea"
                    v-model="addTempTrigger.wordColor"></avue-color>
      </el-form-item>
      <div class="avue-dialog__footer">
        <el-button @click="addTriggerVisible=false">取消</el-button>
        <el-button @click="addTrigger">确定</el-button>
      </div>
    </el-dialog>
  </div>

</template>

<script>
import { tableOption, dicOption, triggerOption } from '@/option/config';

export default {
  data () {
    return {
      dicOption: dicOption,
      tableOption: tableOption,
      triggerOption: triggerOption,
      allTriggerVisible: false,
      addTriggerVisible: false,
      addTempTrigger:
      {
        index: null,
        min: null,
        max: null,
        backColor: null,
        wordColor: null,
      },
      form: {},
      temp: []
    }
  },
  inject: ["main"],
  methods: {
    handleDeleteTrigger (rowIndex) {
      console.log(rowIndex);
      this.temp.splice(rowIndex, 1);
    },
    addTrigger () {
      console.log(this.addTempTrigger.index);
      // this.temp[0]="1";
      this.temp.push({ index: parseFloat(this.addTempTrigger.index), min: parseFloat(this.addTempTrigger.min), max: parseFloat(this.addTempTrigger.max), backColor: this.addTempTrigger.backColor, wordColor: this.addTempTrigger.wordColor });
      this.addTriggerVisible = false;
      this.addTempTrigger.index = null;
      this.addTempTrigger.min = null;
      this.addTempTrigger.max = null;
      this.addTempTrigger.backColor = null;
      this.addTempTrigger.wordColor = null;

    },
    confirmTrigger () {
      this.main.activeOption.trigger = this.temp;
      this.allTriggerVisible = false;
    },
    trigger (index) {
      this.temp = this.main.activeOption.trigger || [];
      this.allTriggerVisible = true;

    },
    // rowSaveTrigger (row, done) {
    //   console.log(this.main.activeOption.triggerColumn);
    //   this.main.activeOption.triggerColumn.push(row);
    //   done()
    // },
    // rowDelTrigger (row, index) {
    //   this.main.activeOption.triggerColumn.splice(index, 1);
    // },
    // rowUpdateTrigger (row, index, done) {
    //   this.main.activeOption.triggerColumn.splice(index, 1, row);
    //   done()
    // },
    rowSave (row, done) {
      console.log(this.main.activeOption.column);
      this.main.activeOption.column.push(row);
      done()
    },
    rowDel (row, index) {
      this.main.activeOption.column.splice(index, 1);
    },
    rowUpdate (row, index, done) {
      this.main.activeOption.column.splice(index, 1, row);
      done()
    },
  }
}
</script>

<style scoped>
el-dialog__body {
  background: #141d4d;
}

.el-table__cell {
  background-color: #1d1f26 !important;
  color: #bcc8d4 !important;
}
.el-dialog__body {
  background-color: #1d1f26;
  color: #bcc8d4;
}
.el-dialog__header {
  background-color: #1d1f26;
  color: #bcc8d4;
}
.avue-dialog .el-dialog__title,
.avue-dialog .el-drawer__header {
  color: #bcc8d4;
}
.avue-form__group--flex {
  background-color: #1d1f26;
  color: #bcc8d4;
}
.avue-dialog__footer {
  margin-top: 10px;
}
.test {
  background-color: #1d1f26;
  color: #bcc8d4;
}
</style>
