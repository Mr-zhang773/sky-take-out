<template>
  <div class="dish-page">
    <!-- 搜索栏 -->
    <div class="search-bar">
      <span class="label">菜品名称：</span>
      <el-input
        v-model="input"
        placeholder="请输入菜品名称"
        class="search-input"
        clearable
        @keyup.enter.native="handleSearch"
      />

      <span class="label">菜品分类：</span>
      <el-select
        v-model="categoryId"
        placeholder="请选择菜品分类"
        class="search-select"
        clearable
        @change="handleSearch"
      >
        <el-option
          v-for="item in categoryList"
          :key="item.id"
          :label="item.categoryName"
          :value="item.id"
        />
      </el-select>

      <span class="label">售实状态：</span>
      <el-select
        v-model="status"
        placeholder="请选择状态"
        class="search-select"
        clearable
        @change="handleSearch"
      >
        <el-option label="在售" :value="1" />
        <el-option label="停售" :value="0" />
      </el-select>

      <el-button type="primary" @click="handleSearch">查询</el-button>
      <el-button type="success" @click="handleAdd">添加菜品</el-button>
    </div>

    <!-- 表格 -->
    <div class="table-area">
      <el-table :data="tableData" border style="width: 100%" max-height="500">
        <el-table-column prop="dishName" label="菜品名称" width="150" />
        <el-table-column prop="dishImage" label="图片" width="100">
          <template slot-scope="scope">
            <img :src="scope.row.dishImage" alt="" style="width: 60px; height: 60px; object-fit: cover;" />
          </template>
        </el-table-column>
        <el-table-column prop="categoryName" label="菜品分类" width="120" />
        <el-table-column prop="dishPrice" label="售价" width="100">
          <template slot-scope="scope">
            ¥{{ scope.row.dishPrice }}
          </template>
        </el-table-column>
        <el-table-column prop="status" label="售实状态" width="100">
          <template slot-scope="scope">
            <el-tag :type="scope.row.status === 1 ? 'success' : 'danger'" size="small">
              {{ scope.row.status === 1 ? '在售' : '停售' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="updateTime" label="最后操作时间" min-width="180" />
        <el-table-column label="操作" width="250" fixed="right">
          <template slot-scope="scope">
            <el-button type="text" @click="handleEdit(scope.row)">修改</el-button>
            <el-button 
              type="text" 
              :style="{ color: scope.row.status === 1 ? '#E6A23C' : '#67C23A' }"
              @click="handleToggleStatus(scope.row)"
            >
              {{ scope.row.status === 1 ? '停售' : '起售' }}
            </el-button>
            <el-button type="text" style="color: #F56C6C;" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 分页 -->
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

    <!-- 添加/修改弹窗 -->
    <el-dialog
      :title="dialogTitle"
      :visible.sync="dialogVisible"
      width="500px"
      @close="handleDialogClose"
    >
      <el-form
        :model="form"
        :rules="rules"
        ref="formRef"
        label-width="100px"
      >
        <el-form-item label="菜品名称" prop="dishName">
          <el-input v-model="form.dishName" placeholder="请输入菜品名称" />
        </el-form-item>
        <el-form-item label="菜品分类" prop="categoryId">
          <el-select v-model="form.categoryId" placeholder="请选择分类" style="width: 100%;">
            <el-option
              v-for="item in categoryList"
              :key="item.id"
              :label="item.categoryName"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="售价" prop="dishPrice">
          <el-input-number v-model="form.dishPrice" :min="0" :precision="2" :step="1" />
        </el-form-item>
        <el-form-item label="图片" prop="dishImage">
          <el-input v-model="form.dishImage" placeholder="请输入图片URL" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio :label="1">在售</el-radio>
            <el-radio :label="0">停售</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitForm" :loading="loading">确认</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { getDishPage, addDish, updateDish, deleteDish, toggleDishStatus } from '@/api/dish'
import { getCategoryList } from '@/api/category'

export default {
  name:'AppDish',
  data() {
    return {
      input: '',
      categoryId: null,
      status: null,
      tableData: [],
      currentPage: 1,
      pageSize: 10,
      total: 0,
      categoryList: [],

      dialogVisible: false,
      dialogTitle: '添加菜品',
      loading: false,
      isEdit: false,
      form: {
        id: null,
        dishName: '',
         dishImage: '',
        categoryId: null,
        dishPrice: 0,
        status: 1
      },
      rules: {
        dishName: [
          { required: true, message: '请输入菜品名称', trigger: 'blur' }
        ],
        categoryId: [
          { required: true, message: '请选择菜品分类', trigger: 'change' }
        ],
        price: [
          { required: true, message: '请输入售价', trigger: 'blur' }
        ]
      }
    }
  },


  created() {
    this.loadCategoryList()
    this.loadData()
  },


  methods: {
    async loadCategoryList() {
      try {
        const res = await getCategoryList()
        if (res.data) {
          this.categoryList = res.data
        }
      } catch (error) {
        console.error('加载分类失败：', error)
      }
    },

    async loadData() {
      try {
        const res = await getDishPage({
          currentPage: this.currentPage,
          pageSize: this.pageSize,
          dishName: this.input,
          categoryId: this.categoryId,
          status: this.status
        })
        if (res.data) {
          this.tableData = res.data.records || []
          this.total = res.data.total || 0
        }
      } catch (error) {
        console.error('加载失败：', error)
        this.$message.error('加载失败，请稍后重试')
      }
    },

    handleSearch() {
      this.currentPage = 1
      this.loadData()
    },

    handleSizeChange(val) {
      this.pageSize = val
      this.currentPage = 1
      this.loadData()
    },

    handleCurrentChange(val) {
      this.currentPage = val
      this.loadData()
    },

    handleAdd() {
      this.dialogTitle = '添加菜品'
      this.isEdit = false
      this.form = {
        id: null,
        dishName: '',
        categoryId: null,
        dishPrice: 0,
        dishImage: '',
        status: 1
      }
      this.dialogVisible = true
      this.$nextTick(() => {
        this.$refs.formRef?.clearValidate()
      })
    },

    handleEdit(row) {
      this.dialogTitle = '修改菜品'
      this.isEdit = true
      this.form = { ...row }
      this.dialogVisible = true
      this.$nextTick(() => {
        this.$refs.formRef?.clearValidate()
      })
    },

    async submitForm() {
      const valid = await this.$refs.formRef.validate().catch(() => false)
      if (!valid) return

      this.loading = true
      try {
        let res
        if (this.isEdit) {
          res = await updateDish(this.form)
        } else {
          res = await addDish(this.form)
        }
        if (res.code === 200) {
          this.$message.success(this.isEdit ? '修改成功' : '添加成功')
          this.dialogVisible = false
          this.loadData()
        } else {
          this.$message.error(res.message || '操作失败')
        }
      } catch (error) {
        console.error('操作失败：', error)
        this.$message.error('操作失败，请稍后重试')
      } finally {
        this.loading = false
      }
    },

    handleDialogClose() {
      this.$refs.formRef?.resetFields()
    },

    async handleToggleStatus(row) {
      try {
        const newStatus = row.status === 1 ? 0 : 1
        const res = await toggleDishStatus(row.id, newStatus)
        if (res.code === 200) {
          this.$message.success('状态修改成功')
          this.loadData()
        } else {
          this.$message.error(res.message || '状态修改失败')
        }
      } catch (error) {
        console.error('状态修改失败：', error)
        this.$message.error('状态修改失败，请稍后重试')
      }
    },

    async handleDelete(row) {
      try {
        await this.$confirm(`确定要删除菜品"${row.dishName}"吗？`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        const res = await deleteDish(row.id)
        if (res.code === 200) {
          this.$message.success('删除成功')
          this.loadData()
        } else {
          this.$message.error(res.message || '删除失败')
        }
      } catch (error) {
        if (error !== 'cancel') {
          console.error('删除失败：', error)
        }
      }
    }
  }
}
</script>

<style scoped>
.dish-page {
  padding: 20px;
  background: #fff;
  border-radius: 8px;
  min-height: 100vh;
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
  width: 180px;
}

.search-select {
  width: 150px;
}

.table-area {
  margin-top: 10px;
  min-height: 300px;
}

.pagination-area {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
  padding: 10px 0;
}
</style>