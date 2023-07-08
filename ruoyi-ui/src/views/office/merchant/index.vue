<template>
  <div class="app-container">
    <iframe src="	https://e.dianping.com/dz-open/merchant/auth" style="width: 900px; height:700px;"></iframe>
  </div>
</template>

<script>
  import {
    listWxuser,
    getWxuser,
    delWxuser,
    addWxuser,
    updateWxuser,
    listStoreWxuser
  } from "@/api/office/wxuser";

  export default {
    name: "Wxuser",
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
        // 微信用户信息 t_wx_user表格数据
        wxuserList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          nickName: null,
          appId: null,
          unionId: null,
          phone: null,
          shareCode: null,
          wxCode: null,
          inviterPhone: null,
          isMember: null,
          sex: null,
          loginIp: null,
          loginDate: null,
          status: null,
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {}
      };
    },
    created() {
      this.getList();
    },
    methods: {
      /** 查询微信用户信息 t_wx_user列表 */
      getList() {
        this.loading = true;
        // listWxuser(this.queryParams).then(response => {
        //   this.wxuserList = response.rows;
        //   this.total = response.total;
        //   this.loading = false;
        // });
        listStoreWxuser(this.queryParams).then(response => {
          this.wxuserList = response.rows;
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
          id: null,
          nickName: null,
          appId: null,
          unionId: null,
          phone: null,
          shareCode: null,
          wxCode: null,
          inviterPhone: null,
          isMember: null,
          sex: null,
          loginIp: null,
          loginDate: null,
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
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "添加微信用户信息 t_wx_user";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        getWxuser(id).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改微信用户信息 t_wx_user";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id != null) {
              updateWxuser(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addWxuser(this.form).then(response => {
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
        this.$modal.confirm('是否确认删除微信用户信息 t_wx_user编号为"' + ids + '"的数据项？').then(function() {
          return delWxuser(ids);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },
      /** 导出按钮操作 */
      handleExport() {
        this.download('office/wxuser/export', {
          ...this.queryParams
        }, `wxuser_${new Date().getTime()}.xlsx`)
      }
    }
  };
</script>
