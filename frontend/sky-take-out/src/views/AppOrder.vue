<template>
  <div class="order-page">


    <!-- 状态筛选 -->
    <div class="search-bar">

      <el-select
        v-model="statusFilter"
        placeholder="全部状态"
        clearable
        @change="handleFilterChange"
        style="width:120px"
      >

        <el-option label="全部状态" value="" />
        <el-option label="待接单" value="待接单" />
        <el-option label="配送中" value="配送中" />
        <el-option label="已完成" value="已完成" />
        <el-option label="已取消" value="已取消" />

      </el-select>


    </div>



    <!-- 订单表格 -->

    <el-table
      :data="tableData"
      border
      height="500"
      style="width:100%"
      fit
      v-loading="loading"
    >



      <el-table-column
        prop="orderNo"
        label="订单号"
        width="130"
      />



      <el-table-column
        label="收货人"
        width="80"
      >

        <template slot-scope="scope">

          {{scope.row.consigneeName || '-'}}

        </template>

      </el-table-column>




      <el-table-column
        label="电话"
        width="110"
      >

        <template slot-scope="scope">

          {{scope.row.consigneePhone || '-'}}

        </template>

      </el-table-column>




      <el-table-column
        label="地址"
        min-width="120"
      >

        <template slot-scope="scope">


          <el-tooltip
            effect="dark"
            :content="scope.row.consigneeAddress"
            placement="top"
          >

            <span class="ellipsis">

              {{scope.row.consigneeAddress || '-'}}

            </span>


          </el-tooltip>


        </template>


      </el-table-column>





      <el-table-column
        label="菜品"
        min-width="150"
      >

        <template slot-scope="scope">


          <el-tooltip
            effect="dark"
            placement="top"
          >

            <div slot="content">


              <div
                v-for="(item,index) in scope.row.orderDetails || []"
                :key="index"
              >

                {{item.dishName}} × {{item.quantity}}

              </div>


            </div>



            <span class="ellipsis">

              {{
                (scope.row.orderDetails || [])
                .map(item=>item.dishName+'×'+item.quantity)
                .join('、')
              }}

            </span>


          </el-tooltip>



        </template>


      </el-table-column>





      <el-table-column
        prop="totalPrice"
        label="金额"
        width="70"
      />





      <el-table-column
        prop="status"
        label="状态"
        width="80"
      >

        <template slot-scope="scope">


          <el-tag
            :type="statusTag(scope.row.status)"
            size="small"
          >

            {{scope.row.status}}

          </el-tag>


        </template>


      </el-table-column>





      <el-table-column
        prop="createTime"
        label="时间"
        width="140"
      />





      <!-- 操作 -->

      <el-table-column
        label="操作"
        width="150"
      >

        <template slot-scope="scope">



          <el-button
            v-if="scope.row.status==='待接单'"
            type="primary"
            size="mini"
            @click="handleStatusChange(scope.row.id,'配送中')"
          >

            接单

          </el-button>




          <el-button
            v-if="scope.row.status==='配送中'"
            type="success"
            size="mini"
            @click="handleStatusChange(scope.row.id,'已完成')"
          >

            完成

          </el-button>





          <el-button
            v-if="scope.row.status==='待接单'"
            type="danger"
            size="mini"
            @click="handleStatusChange(scope.row.id,'已取消')"
          >

            拒单

          </el-button>



        </template>


      </el-table-column>



    </el-table>





    <!-- 分页 -->

    <div class="pagination-area">


      <el-pagination

        background

        layout="total, sizes, prev, pager, next, jumper"

        :total="total"

        :page-size="pageSize"

        :current-page="currentPage"

        :page-sizes="[5,10,20,50]"

        @size-change="handleSizeChange"

        @current-change="handleCurrentChange"

      />


    </div>



  </div>
</template>




<script>


import {
  getOrderList,
  updateOrderStatus
} from '@/api/order'



export default {


  name:'AppOrder',



  data(){

    return {


      tableData:[],

      currentPage:1,

      pageSize:10,

      total:0,

      statusFilter:'',

      loading:false


    }

  },



  created(){

    this.loadData()

  },



  methods:{



    async loadData(){


      this.loading=true


      try{


        const res =
          await getOrderList({

            page:this.currentPage,

            pageSize:this.pageSize,

            status:this.statusFilter || undefined

          })



        if(res.data){


          this.tableData =
            res.data.records || []

          this.total =
            res.data.total || 0


        }



      }catch(error){


        console.error(error)

        this.$message.error('加载订单失败')


      }finally{


        this.loading=false


      }


    },





    async handleStatusChange(id,status){


      try{


        await updateOrderStatus(id,status)


        this.$message.success('操作成功')


        this.loadData()



      }catch(error){


        console.error(error)

        this.$message.error('操作失败')


      }


    },




    handleFilterChange(){


      this.currentPage=1

      this.loadData()


    },



    handleSizeChange(val){


      this.pageSize=val

      this.currentPage=1

      this.loadData()


    },



    handleCurrentChange(val){


      this.currentPage=val

      this.loadData()


    },




    statusTag(status){


      const map={

        '待接单':'warning',

        '配送中':'primary',

        '已完成':'success',

        '已取消':'danger'

      }


      return map[status] || ''


    }


  }


}

</script>




<style scoped>


.order-page{


  padding:20px;

  background:#fff;

  border-radius:8px;

  min-height:100vh;

  box-sizing:border-box;


}



.search-bar{


  margin-bottom:15px;


}




.ellipsis{


  display:block;

  overflow:hidden;

  text-overflow:ellipsis;

  white-space:nowrap;


}




.pagination-area{


  margin-top:20px;

  display:flex;

  justify-content:flex-end;


}


</style>