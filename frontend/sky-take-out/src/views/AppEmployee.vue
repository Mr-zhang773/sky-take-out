<template>
  <div class="employee-page">

    <!-- 搜索栏 -->
    <div class="search-bar">
      <span class="label">员工姓名：</span>
      <el-input
        v-model="input"
        placeholder="请输入员工姓名"
        class="search-input"
        clearable
        @keyup.enter.native="handleSearch"
      />
      <el-button type="primary" @click="handleSearch">查询</el-button>
      <el-button type="success" @click="handleAdd">添加员工</el-button>
    </div>


    <!-- 员工列表表格 -->
    <div class="table-area">
      <el-table :data="tableData" border style="width: 100%;" max-height="500">
        <el-table-column prop="employeeName" label="员工姓名" width="120" />
        <el-table-column prop="employeeAccount" label="账号" width="150" />
        <el-table-column prop="employeePhone" label="手机号" width="150" />
        <el-table-column prop="employeeGender" label="性别" width="80" />
        <el-table-column prop="employeeIdNumber" label="身份证号" width="180" />
        <el-table-column prop="status" label="账号状态" width="100">
          <template slot-scope="scope">
            <el-tag :type="scope.row.status === '启用' ? 'success' : 'danger'" size="small">
              {{ scope.row.status }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="updateTime" label="最后操作时间" min-width="180" />
        <el-table-column label="操作" width="180" fixed="right">
          <template slot-scope="scope">
            <el-button type="text" @click="handleEdit(scope.row)">修改</el-button>
            <el-button 
              type="text" 
              :style="{ color: scope.row.status === '启用' ? '#E6A23C' : '#67C23A' }"
              @click="handleToggleStatus(scope.row)"
            >
              {{ scope.row.status === '启用' ? '禁用' : '启用' }}
            </el-button>
          </template>
        </el-table-column>
      </el-table>


  <!-- 分页组件 -->
    </div>
      <div class="pagination-area">
        <el-pagination
          background
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          :page-size="pageSize"
          :current-page="currentPage"
          :page-sizes="[5, 10, 20, 50]"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>

      
      <!-- 修改员工弹窗 -->
<el-dialog
  title="修改员工信息"
  :visible.sync="editDialogVisible"
  width="600px"
  @close="handleEditClose"
>
  <el-form
    :model="editForm"
    :rules="editRules"
    ref="editFormRef"
    label-width="100px"
  >
    <el-form-item label="员工姓名" prop="employeeName">
      <el-input v-model="editForm.employeeName" placeholder="请输入员工姓名" />
    </el-form-item>
    <el-form-item label="账号" prop="employeeAccount">
      <el-input v-model="editForm.employeeAccount" placeholder="请输入账号" />
    </el-form-item>
    <el-form-item label="手机号" prop="employeePhone">
      <el-input v-model="editForm.employeePhone" placeholder="请输入手机号" />
    </el-form-item>
    <el-form-item label="性别" prop="employeeGender">
      <el-radio-group v-model="editForm.employeeGender">
        <el-radio label="男">男</el-radio>
        <el-radio label="女">女</el-radio>
      </el-radio-group>
    </el-form-item>
    <el-form-item label="身份证号" prop="employeeIdNumber">
      <el-input v-model="editForm.employeeIdNumber" placeholder="请输入身份证号" />
    </el-form-item>
    <el-form-item label="账号状态" prop="status">
      <el-radio-group v-model="editForm.status">
        <el-radio :label="1">启用</el-radio>
        <el-radio :label="0">禁用</el-radio>
      </el-radio-group>
    </el-form-item>
  </el-form>
  <span slot="footer">
    <el-button @click="editDialogVisible = false">取消</el-button>
    <el-button type="primary" @click="submitEdit" :loading="editLoading">确认修改</el-button>
  </span>
</el-dialog>


<!-- 添加员工弹窗 -->
<el-dialog
  title="添加员工"
  :visible.sync="addDialogVisible"
  width="600px"
  @close="handleAddClose"
>
  <el-form
    :model="addForm"
    :rules="addRules"
    ref="addFormRef"
    label-width="100px"
  >
    <el-form-item label="员工姓名" prop="employeeName">
      <el-input v-model="addForm.employeeName" placeholder="请输入员工姓名" />
    </el-form-item>
    <el-form-item label="账号" prop="employeeAccount">
      <el-input v-model="addForm.employeeAccount" placeholder="请输入账号" />
    </el-form-item>
    <el-form-item label="手机号" prop="employeePhone">
      <el-input v-model="addForm.employeePhone" placeholder="请输入手机号" />
    </el-form-item>
    <el-form-item label="性别" prop="employeeGender">
      <el-radio-group v-model="addForm.employeeGender">
        <el-radio label="男">男</el-radio>
        <el-radio label="女">女</el-radio>
      </el-radio-group>
    </el-form-item>
    <el-form-item label="身份证号" prop="employeeIdNumber">
      <el-input v-model="addForm.employeeIdNumber" placeholder="请输入身份证号" />
    </el-form-item>
  </el-form>
  <span slot="footer">
    <el-button @click="addDialogVisible = false">取消</el-button>
    <el-button type="primary" @click="submitAdd" :loading="addLoading">确认添加</el-button>
  </span>
</el-dialog>
  </div>
</template>

<script>
import { selectPage } from '@/api/employee';
import { nameSelect } from '@/api/employee';
import { updateEmployee } from '@/api/employee';
import { addEmployee } from '@/api/employee';
export default {
  data() {
    return {
      //原页面数据
      input: '',
      tableData: [],
        currentPage: 1,
      pageSize: 10,
      total: 0,


        // 修改弹窗数据
    editDialogVisible: false,
    editLoading: false,
    editForm: {
      id: null,
      employeeName: '',
      employeeAccount: '',
      employeePhone: '',
      employeeGender: '男',
      employeeIdNumber: '',
      status: 1
    },
    editRules: {
      employeeName: [
        { required: true, message: '请输入员工姓名', trigger: 'blur' }
      ],
      employeeAccount: [
        { required: true, message: '请输入账号', trigger: 'blur' }
      ],
      employeePhone: [
        { required: true, message: '请输入手机号', trigger: 'blur' },
        { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' }
      ],
      employeeIdNumber: [
        { required: true, message: '请输入身份证号', trigger: 'blur' }
      ]
    },


    // 添加弹窗
    addDialogVisible: false,
    addLoading: false,
    addForm: {
      employeeName: '',
      employeeAccount: '',
      employeePhone: '',
      employeeGender: '男',
      employeeIdNumber: ''
    },
    addRules: {
      employeeName: [
        { required: true, message: '请输入员工姓名', trigger: 'blur' }
      ],
      employeeAccount: [
        { required: true, message: '请输入账号', trigger: 'blur' }
      ],
      employeePhone: [
        { required: true, message: '请输入手机号', trigger: 'blur' },
        { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' }
      ],
      employeeIdNumber: [
        { required: true, message: '请输入身份证号', trigger: 'blur' }
      ]
    }
    }
  },


  created() {
    this.loadData()
  },


  methods: { 
    async pageSelect(currentpage,pagesize){
      try{
        const params={
      currentPage:currentpage,
      pageSize:pagesize
    }
     const res = await selectPage(params)
     if(res.data){
       this.tableData = res.data.records || []
      this.total = res.data.total || 0
     }
      }catch(error){
         console.error('查询失败：', error)
        this.$message.error('查询失败，请稍后重试')
      }

    },


    async loadData() {
      try {
          const params={
      currentPage:this.currentPage,
      pageSize:this.pageSize,
      employeeName:this.input
    }
        const res = await selectPage(params)
        // 后端返回分页数据
        if (res.data) {
           this.tableData = res.data.records || []
      this.total = res.data.total || 0
        }
      } catch (error) {
        console.error('加载失败：', error)
        this.$message.error('加载失败，请稍后重试')
      }
    },

   handleSizeChange(val) {
  this.pageSize = val      // 更新每页条数
  this.currentPage = 1     // 重置到第一页
  this.loadData()          // 重新加载
},

handleCurrentChange(val) {
  this.currentPage = val   // 更新当前页码
  this.loadData()          // 重新加载
},


    async handleSearch() {
      console.log('搜索关键词：', this.input)
      try{
        const res=await nameSelect({employeeName:this.input,currentPage:1,pageSize:this.pageSize})
        if(res.data){
            this.tableData = res.data.records || []
        this.total = res.data.total || 0
        this.currentPage=res.data.currentPage
        }
      }catch(error){
        console.error('查询失败：', error)
        this.$message.error('查询失败，请稍后重试')

      }
    },
    

     // 点击修改按钮
  handleEdit(row) {
    this.editDialogVisible = true
    // 把当前行数据填充到表单
    this.editForm = {
      id: row.id,
      employeeName: row.employeeName,
      employeeAccount: row.employeeAccount,
      employeePhone: row.employeePhone,
      employeeGender: row.employeeGender || '男',
      employeeIdNumber: row.employeeIdNumber,
      status: row.status === '启用' ? 1 : 0
    }
  },

   // 提交修改
  async submitEdit() {
    // 表单校验
    const valid = await this.$refs.editFormRef.validate().catch(() => false)
    if (!valid) return

    this.editLoading = true
    try {
      const res = await updateEmployee(this.editForm)
      if(res.code == 200){
      console.log('提交修改数据：', this.editForm)
      this.$message.success('修改成功')
      this.editDialogVisible = false
      this.loadData() }else 
      { this.$message.error('修改失败')
         this.editDialogVisible = false
      }
    } catch (error) {
      console.error('修改失败：', error)
      this.$message.error('修改失败，请稍后重试')
    } finally {
      this.editLoading = false
    }
  },

    // 关闭弹窗时重置表单
  handleEditClose() {
    this.$refs.editFormRef?.resetFields()
  },


    // 点击"添加员工"按钮
  handleAdd() {
    this.addDialogVisible = true
    // 重置表单
    this.$nextTick(() => {
      this.$refs.addFormRef?.resetFields()
    })
  },

  // 提交添加
  async submitAdd() {
    const valid = await this.$refs.addFormRef.validate().catch(() => false)
    if (!valid) return

    this.addLoading = true
    try {
      const res = await addEmployee(this.addForm)
      if (res.code === 200) {
        this.$message.success('添加成功')
        this.addDialogVisible = false
        this.loadData()
      } else {
        this.$message.error(res.message || '添加失败')
      }
    } catch (error) {
      console.error('添加失败：', error)
      this.$message.error('添加失败，请稍后重试')
    } finally {
      this.addLoading = false
    }
  },

  handleAddClose() {
    this.$refs.addFormRef?.resetFields()
  }
}

  }



</script>

<style scoped>
.employee-page {
  padding: 20px;
  background: #fff;
  border-radius: 8px;
   min-height: 100vh;  /* ← 加这行，让容器至少撑满整个视口高度 */
}

.search-bar {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 20px;
  flex-wrap: wrap;
}

.label {
  font-size: 14px;
  color: #333;
  white-space: nowrap;
}

.search-input {
  width: 220px;
}

.table-area {
  margin-top: 10px;
   min-height: 300px;  /* ← 限制表格最小高度，防止无限撑大 */
}
.pagination-area {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
  padding: 10px 0;
    flex-shrink: 0;  /* ← 关键：不让分页被压缩 */
  background: #fff;  /* ← 关键：防止被表格覆盖 */
}
</style>