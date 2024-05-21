<template>
  <div class="dashboard-editor-container">

	<el-row style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">
		<div class="chart-wrapper">
			aaaa
        </div>
    </el-row>

    <el-row :gutter="32">
      <el-col :xs="24" :sm="24" :lg="16">
		<div>
			<el-card   class="box-card">
			  <div slot="header" class="clearfix">
			    <span><b>公告和新闻消息</b></span>
			    <el-button style="float: right; padding: 3px 0" type="text"></el-button>
			  </div>
			  
			  <div>
				
				<el-tabs v-model="activeName" @tab-click="handleClick">
				    <el-tab-pane label="公告" name="first">
						<div  class="text item">
							<el-table v-loading="loading" :data="noticeList" @selection-change="handleSelectionChange">
							      
							  <el-table-column label="序号" align="center" prop="noticeId" width="100" />
							  <el-table-column
							    label="公告标题"
							    align="center"
							    prop="noticeTitle"
							    :show-overflow-tooltip="true"
							  />
							  <el-table-column label="公告类型" align="center" prop="noticeType" width="100">
							    <template slot-scope="scope">
							      <dict-tag :options="dict.type.sys_notice_type" :value="scope.row.noticeType"/>
							    </template>
							  </el-table-column>
							  <el-table-column label="状态" align="right" prop="status" width="100">
							    <template slot-scope="scope">
							      <dict-tag :options="dict.type.sys_notice_status" :value="scope.row.status"/>
							    </template>
							  </el-table-column>
							  <el-table-column label="操作" align="right" class-name="small-padding fixed-width">
							    <template slot-scope="scope">
							      <el-button
							        size="mini"
							        type="text"
							        icon="el-icon-edit"
							        @click="handleUpdate(scope.row)"
							        v-hasPermi="['system:notice:edit']"
							      >详情</el-button>
							    </template>
							  </el-table-column>
							  <el-table-column label="更多" align="right" class-name="small-padding fixed-width">
  							    
  							  </el-table-column>
							</el-table>
						</div> 
					</el-tab-pane>
				    <el-tab-pane label="新闻" name="second">
						<div v-for="o in 10" :key="o" class="text item">
									    {{'列表内容 ' + o }}
									  </div> 
					</el-tab-pane>
				  </el-tabs>
			  </div>
			  
			  
			  
			  
			</el-card>
		</div>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="8">
		<div>
			<el-card class="box-card">
			  <div slot="header" class="clearfix">
			    <span><b>常用菜单</b></span>
			    <el-button style="float: right; padding: 3px 0" type="text"></el-button>
			  </div>
			  <div class="text item">
				<el-row :gutter="32">
			      <el-col :span="6">
					<el-button  style="width: 75px; height: 75px;" type="primary" icon="el-icon-search" ></el-button>
				  	<div style="text-align: center;">知识库管理</div>
			      </el-col >
				  <el-col :span="6">
					<el-button style="width: 75px; height: 75px;" type="primary" icon="el-icon-edit" ></el-button>
					<div style="text-align: center;">制度库管理</div>
			      </el-col>
				  <el-col :span="6">
					<el-button style="width: 75px; height: 75px;" type="success" icon="el-icon-check" ></el-button>
					<div style="text-align: center;">典型案例管理</div>
			      </el-col>
				  <el-col :span="6">
  					<el-button style="width: 75px; height: 75px;" type="info" icon="el-icon-message" ></el-button>
  					<div style="text-align: center;">廉政档案管理</div>
  			      </el-col>
				</el-row>
				
				<el-row :gutter="32">
			      
				  <el-col :span="6">
					<el-button style="width: 75px; height: 75px;" type="warning" icon="el-icon-star-off" ></el-button>
					<div style="text-align: center;">人才培训管理</div>
			      </el-col>
				  <el-col :span="6">
					<el-button style="width: 75px; height: 75px;" type="danger" icon="el-icon-delete" ></el-button>
					<div style="text-align: center;">日志管理</div>
				  </el-col>
				</el-row>  
				 
				  
				  
				  
				  
			  </div>
			</el-card>
		</div>
      </el-col>
    </el-row>

	<el-row :gutter="32">
	      <el-col :xs="24" :sm="24" :lg="16">
			<div>
				<el-card   class="box-card">
				  <div slot="header" class="clearfix">
				    <span><b>事件中心</b></span>
				    <el-button style="float: right; padding: 3px 0" type="text"></el-button>
				  </div>
				  <div>
					<el-tabs v-model="activeName1" @tab-click="handleClick">
					    <el-tab-pane label="新发布制度" name="first1">
							<div v-for="o in 10" :key="o" class="text item">
										    {{'列表内容 ' + o }}
										  </div> 
						</el-tab-pane>
					    <el-tab-pane label="新发布知识" name="second1">
							<div v-for="o in 10" :key="o" class="text item">
										    {{'列表内容 ' + o }}
										  </div> 
						</el-tab-pane>
						<el-tab-pane label="我的学习" name="third1">
							<div v-for="o in 10" :key="o" class="text item">
										    {{'列表内容 ' + o }}
										  </div> 
						</el-tab-pane>
					  </el-tabs>
				  </div>
				  
				 
				</el-card>
			</div>
	      </el-col>
	      <el-col :xs="24" :sm="24" :lg="8">
			<div>
				<el-card class="box-card">
				  <div slot="header" class="clearfix">
				    <span>考试提醒</span>
				    <el-button style="float: right; padding: 3px 0" type="text"></el-button>
				  </div>
				  <div  class="text item">
					<el-calendar v-show="true" class="custom-size" v-model="value">
					</el-calendar> 
				  </div>
				</el-card>
			</div>
	      </el-col>
	    </el-row>
  </div>
</template>

<script>


export default {
  name: 'Index',
  components: {
   
  },
  data() {
    return {
		value: new Date(),
      	
		activeName: "first",
		activeName1: "first1",
		// 公告表格数据
		noticeList: []
    }
  },
  methods: {
   // handleSetLineChartData(type) {
     // this.lineChartData = lineChartData[type]
    //}
  }
}
</script>


