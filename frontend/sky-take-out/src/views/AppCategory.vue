<template>
  <div class="category-page">

    <!-- 搜索栏 -->
    <div class="search-bar">

      <span class="label">分类名称：</span>

      <el-input
        v-model="input"
        placeholder="请输入分类名称"
        class="search-input"
        clearable
        @keyup.enter.native="handleSearch"
      />


      <span class="label">分类类型：</span>


      <el-select
        v-model="categoryType"
        placeholder="请选择分类类型"
        class="search-select"
        clearable
        @change="handleSearch"
      >

        <el-option
          label="菜品"
          :value="1"
        />

        <el-option
          label="套餐"
          :value="2"
        />

      </el-select>


      <el-button
        type="primary"
        @click="handleSearch"
      >
        查询
      </el-button>


      <el-button
        type="success"
        @click="handleAdd"
      >
        添加分类
      </el-button>


    </div>



    <!-- 分类列表 -->

    <div class="table-area">


      <el-table
        :data="tableData"
        border
        style="width:100%"
        max-height="500"
      >


        <el-table-column
          prop="categoryName"
          label="分类名称"
          width="150"
        />



        <el-table-column
          prop="categoryType"
          label="分类类型"
          width="120"
        >

          <template slot-scope="scope">

            <el-tag
              :type="scope.row.categoryType === 1 ? 'success':'warning'"
              size="small"
            >

              {{scope.row.categoryType === 1 ? '菜品':'套餐'}}

            </el-tag>


          </template>


        </el-table-column>



        <el-table-column
          prop="id"
          label="分类编号"
          width="100"
        />



        <el-table-column
          prop="status"
          label="状态"
          width="100"
        >

          <template slot-scope="scope">


            <el-tag
              :type="scope.row.status === 1 ? 'success':'danger'"
              size="small"
            >

              {{scope.row.status === 1 ? '启用':'禁用'}}


            </el-tag>


          </template>


        </el-table-column>



        <el-table-column
          prop="updateTime"
          label="更新时间"
          width="180"
        />



        <el-table-column
          label="操作"
          width="160"
        >

          <template slot-scope="scope">


            <el-button
              type="text"
              @click="handleEdit(scope.row)"
            >
              修改
            </el-button>



            <el-button
              type="text"
              style="color:#F56C6C"
              @click="handleDelete(scope.row)"
            >
              删除
            </el-button>



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

        :page-sizes="[5,10,20,50]"

        @size-change="handleSizeChange"

        @current-change="handleCurrentChange"

      />


    </div>





    <!-- 添加修改弹窗 -->


    <el-dialog

      :title="dialogTitle"

      :visible.sync="dialogVisible"

      width="500px"

    >


      <el-form

        :model="form"

        :rules="rules"

        ref="formRef"

        label-width="100px"

      >


        <el-form-item

          label="分类名称"

          prop="categoryName"

        >

          <el-input
            v-model="form.categoryName"
          />

        </el-form-item>




        <el-form-item

          label="分类类型"

          prop="categoryType"

        >

          <el-radio-group
            v-model="form.categoryType"
          >

            <el-radio :label="1">
              菜品
            </el-radio>


            <el-radio :label="2">
              套餐
            </el-radio>


          </el-radio-group>


        </el-form-item>



        <el-form-item

          label="状态"

          prop="status"

        >

          <el-radio-group
            v-model="form.status"
          >

            <el-radio :label="1">
              启用
            </el-radio>


            <el-radio :label="0">
              禁用
            </el-radio>


          </el-radio-group>


        </el-form-item>



      </el-form>




      <span slot="footer">


        <el-button
          @click="dialogVisible=false"
        >
          取消
        </el-button>


        <el-button

          type="primary"

          :loading="loading"

          @click="submitForm"

        >

          确认

        </el-button>


      </span>



    </el-dialog>




  </div>
</template>



<script>


import {
  getCategoryPage,
  addCategory,
  updateCategory,
  deleteCategory
} from '@/api/category'



export default {


  data(){


    return {


      input:'',


      categoryType:null,


      tableData:[],


      currentPage:1,


      pageSize:10,


      total:0,


      dialogVisible:false,


      dialogTitle:'添加分类',


      loading:false,


      isEdit:false,



      form:{


        id:null,


        categoryName:'',


        categoryType:1,


        status:1


      },



      rules:{


        categoryName:[

          {
            required:true,
            message:'请输入分类名称',
            trigger:'blur'
          }

        ],


        categoryType:[

          {
            required:true,
            message:'请选择分类类型',
            trigger:'change'
          }

        ]


      }


    }


  },



  created(){


    this.loadData()


  },



  methods:{



    async loadData(){


      try{


        const res =
          await getCategoryPage({

            currentPage:this.currentPage,

            pageSize:this.pageSize,

            categoryName:this.input,

            categoryType:this.categoryType

          })



        if(res.data){


          this.tableData =
            res.data.records || []



          this.total =
            res.data.total || 0


        }



      }catch(error){


        console.error(error)

        this.$message.error('加载失败')


      }



    },




    handleSearch(){


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




    handleAdd(){


      this.dialogTitle='添加分类'


      this.isEdit=false


      this.form={

        id:null,

        categoryName:'',

        categoryType:1,

        status:1

      }


      this.dialogVisible=true


    },




    handleEdit(row){


      this.dialogTitle='修改分类'


      this.isEdit=true


      this.form={...row}


      this.dialogVisible=true


    },




    async submitForm(){


      const valid =
        await this.$refs.formRef.validate()
          .catch(()=>false)



      if(!valid)return




      this.loading=true



      try{


        let res



        if(this.isEdit){


          res =
            await updateCategory(this.form)


        }else{


          res =
            await addCategory(this.form)


        }




        if(res.code===200){


          this.$message.success('操作成功')


          this.dialogVisible=false


          this.loadData()


        }



      }catch(error){


        console.error(error)

        this.$message.error('操作失败')


      }finally{


        this.loading=false


      }



    },





    async handleDelete(row){


      try{


        await this.$confirm(
          `确定删除分类"${row.categoryName}"吗？`,
          '提示'
        )



        const res =
          await deleteCategory(row.id)



        if(res.code===200){


          this.$message.success('删除成功')


          this.loadData()


        }



      }catch(error){


        console.log(error)


      }


    }



  }



}



</script>



<style scoped>


.category-page{

  padding:20px;

  background:#fff;

  border-radius:8px;

  min-height:100vh;

}


.search-bar{

  display:flex;

  align-items:center;

  gap:12px;

  margin-bottom:20px;

}



.label{

  font-size:14px;

}



.search-input{

  width:220px;

}



.search-select{

  width:150px;

}



.pagination-area{

  margin-top:20px;

  display:flex;

  justify-content:flex-end;

}



</style>