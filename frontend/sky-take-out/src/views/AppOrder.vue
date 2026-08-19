<template>
  <div class="order-page">
    <div class="page-header">
      <h2>订单管理</h2>
      <el-select
        v-model="statusFilter"
        placeholder="全部状态"
        clearable
        @change="handleFilterChange"
        style="width: 150px;"
      >
        <el-option label="全部状态" value="" />
        <el-option label="待接单" value="待接单" />
        <el-option label="配送中" value="配送中" />
        <el-option label="已完成" value="已完成" />
        <el-option label="已取消" value="已取消" />
      </el-select>
    </div>

    <el-table
      :data="tableData"
      border
      style="width: 100%"
      max-height="450"
      v-loading="loading"
    >
      <el-table-column prop="id" label="订单号" width="90" />
      <el-table-column label="收货人" width="80">
        <template slot-scope="scope">
          {{ scope.row.consigneeName || '-' }}
        </template>
      </el-table-column>
      <el-table-column label="电话" width="110">
        <template slot-scope="scope">
          {{ scope.row.consigneePhone || '-' }}
        </template>
      </el-table-column>
      <el-table-column label="地址" width="160">
        <template slot-scope="scope">
          {{ scope.row.consigneeAddress || '-' }}
        </template>
      </el-table-column>
      <el-table-column label="菜品" width="180">
        <template slot-scope="scope">
          <span
            v-for="(item, index) in JSON.parse(scope.row.dishes)"
            :key="index"
            style="margin-right: 6px;"
          >
            {{ item.dishName }}×{{ item.quantity }}
          </span>
        </template>
      </el-table-column>
      <el-table-column prop="totalPrice" label="总价" width="80" />
      <el-table-column prop="status" label="状态" width="90">
        <template slot-scope="scope">
          <el-tag :type="statusTag(scope.row.status)" size="small">
            {{ scope.row.status }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="下单时间" width="160" />
      <el-table-column label="操作" width="220" fixed="right">
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.status === '待接单'"
            type="primary"
            size="mini"
            @click="handleStatusChange(scope.row.id, '配送中')"
          >
            接单
          </el-button>
          <el-button
            v-if="scope.row.status === '配送中'"
            type="success"
            size="mini"
            @click="handleStatusChange(scope.row.id, '已完成')"
          >
            完成
          </el-button>
          <el-button
            v-if="scope.row.status === '待接单'"
            type="danger"
            size="mini"
            @click="handleStatusChange(scope.row.id, '已取消')"
          >
            拒单
          </el-button>
        </template>
      </el-table-column>
    </el-table>

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
  </div>
</template>

<script>
import { getOrderList, updateOrderStatus } from '@/api/order'

export default {
  name: 'AppOrder',
  data() {
    return {
      tableData: [],
      currentPage: 1,
      pageSize: 10,
      total: 0,
      statusFilter: '',
      loading: false
    }
  },
  created() {
    this.loadData()
  },
  methods: {
    async loadData() {
      this.loading = true
      try {
        const res = await getOrderList({
          page: this.currentPage,
          pageSize: this.pageSize,
          status: this.statusFilter || undefined
        })
        if (res.data) {
          this.tableData = res.data.records || []
          this.total = res.data.total || 0
        }
      } catch (error) {
        console.error('加载订单失败', error)
        this.$message.error('加载订单失败')
      } finally {
        this.loading = false
      }
    },

    async handleStatusChange(id, status) {
      try {
        await updateOrderStatus(id, status)
        this.$message.success('状态更新成功')
        this.loadData()
      } catch (error) {
        console.error('更新失败', error)
        this.$message.error('更新失败')
      }
    },

    handleFilterChange() {
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

    statusTag(status) {
      const map = {
        '待接单': 'warning',
        '配送中': 'primary',
        '已完成': 'success',
        '已取消': 'danger'
      }
      return map[status] || ''
    }
  }
}
</script>

<style scoped>
.order-page {
  padding: 20px;
  background: #fff;
  border-radius: 8px;
  min-height: 100vh;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.page-header h2 {
  margin: 0;
  font-size: 20px;
}

.pagination-area {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
  padding: 10px 0;
  background: #fff;
}
</style>