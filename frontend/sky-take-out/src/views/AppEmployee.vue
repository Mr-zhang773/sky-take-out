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

      <el-button type="primary" @click="handleSearch">
        查询
      </el-button>

      <el-button type="success" @click="handleAdd">
        添加员工
      </el-button>

    </div>


    <!-- 员工列表 -->
    <div class="table-area">

      <el-table
        :data="tableData"
        border
        style="width:100%"
        max-height="500"
      >

        <el-table-column
          prop="employeeName"
          label="员工姓名"
          width="120"
        />

        <el-table-column
          prop="employeeAccount"
          label="账号"
          width="150"
        />

        <el-table-column
          prop="employeePhone"
          label="手机号"
          width="150"
        />

        <el-table-column
          prop="employeeGender"
          label="性别"
          width="80"
        />


        <el-table-column
          prop="employeeIdNumber"
          label="身份证号"
          width="180"
        />


        <el-table-column
          prop="status"
          label="账号状态"
          width="100"
        >

          <template slot-scope="scope">

            <el-tag
              :type="scope.row.status===1?'success':'danger'"
              size="small"
            >

              {{scope.row.status===1?'启用':'禁用'}}

            </el-tag>

          </template>


        </el-table-column>



        <el-table-column
          prop="updateTime"
          label="最后操作时间"
          min-width="180"
        />



        <el-table-column
          label="操作"
          width="180"
          fixed="right"
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
              :style="{
                color:scope.row.status===1?'#E6A23C':'#67C23A'
              }"
              @click="handleToggleStatus(scope.row)"
            >

              {{scope.row.status===1?'禁用':'启用'}}

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




    <!-- 修改员工 -->

    <el-dialog
      title="修改员工信息"
      :visible.sync="editDialogVisible"
      width="600px"
    >

      <el-form
        :model="editForm"
        :rules="editRules"
        ref="editFormRef"
        label-width="100px"
      >


        <el-form-item
          label="员工姓名"
          prop="employeeName"
        >

          <el-input v-model="editForm.employeeName"/>

        </el-form-item>



        <el-form-item
          label="账号"
          prop="employeeAccount"
        >

          <el-input v-model="editForm.employeeAccount"/>

        </el-form-item>



        <el-form-item
          label="手机号"
          prop="employeePhone"
        >

          <el-input v-model="editForm.employeePhone"/>

        </el-form-item>



        <el-form-item
          label="性别"
          prop="employeeGender"
        >

          <el-radio-group v-model="editForm.employeeGender">

            <el-radio label="男">
              男
            </el-radio>

            <el-radio label="女">
              女
            </el-radio>

          </el-radio-group>


        </el-form-item>



        <el-form-item
          label="身份证号"
          prop="employeeIdNumber"
        >

          <el-input v-model="editForm.employeeIdNumber"/>

        </el-form-item>



        <el-form-item
          label="状态"
          prop="status"
        >

          <el-radio-group v-model="editForm.status">

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

        <el-button @click="editDialogVisible=false">
          取消
        </el-button>


        <el-button
          type="primary"
          @click="submitEdit"
        >

          确认

        </el-button>


      </span>


    </el-dialog>





    <!-- 添加员工 -->

    <el-dialog
      title="添加员工"
      :visible.sync="addDialogVisible"
      width="600px"
    >


      <el-form
        :model="addForm"
        :rules="addRules"
        ref="addFormRef"
        label-width="100px"
      >


        <el-form-item
          label="员工姓名"
          prop="employeeName"
        >

          <el-input v-model="addForm.employeeName"/>

        </el-form-item>



        <el-form-item
          label="账号"
          prop="employeeAccount"
        >

          <el-input v-model="addForm.employeeAccount"/>

        </el-form-item>



        <el-form-item
          label="手机号"
          prop="employeePhone"
        >

          <el-input v-model="addForm.employeePhone"/>

        </el-form-item>



        <el-form-item
          label="性别"
          prop="employeeGender"
        >

          <el-radio-group v-model="addForm.employeeGender">

            <el-radio label="男">
              男
            </el-radio>

            <el-radio label="女">
              女
            </el-radio>

          </el-radio-group>

        </el-form-item>



        <el-form-item
          label="身份证号"
          prop="employeeIdNumber"
        >

          <el-input v-model="addForm.employeeIdNumber"/>

        </el-form-item>



      </el-form>



      <span slot="footer">

        <el-button @click="addDialogVisible=false">
          取消
        </el-button>


        <el-button
          type="primary"
          @click="submitAdd"
        >

          确认添加

        </el-button>


      </span>



    </el-dialog>


  </div>
</template>




<script>

import {
  selectPage,
  updateEmployee,
  addEmployee
} from '@/api/employee'


export default {


data(){


return {


input:'',


tableData:[],


currentPage:1,


pageSize:10,


total:0,



// 修改

editDialogVisible:false,


editForm:{


id:null,

employeeName:'',

employeeAccount:'',

employeePhone:'',

employeeGender:'男',

employeeIdNumber:'',

status:1


},



editRules:{


employeeName:[

{
required:true,
message:'请输入员工姓名',
trigger:'blur'
}

],


employeeAccount:[

{
required:true,
message:'请输入账号',
trigger:'blur'
}

],


employeePhone:[

{
required:true,
message:'请输入手机号',
trigger:'blur'
}

]

},




// 添加


addDialogVisible:false,


addForm:{


employeeName:'',

employeeAccount:'',

employeePhone:'',

employeeGender:'男',

employeeIdNumber:''


},



addRules:{


employeeName:[

{
required:true,
message:'请输入员工姓名',
trigger:'blur'
}

],


employeeAccount:[

{
required:true,
message:'请输入账号',
trigger:'blur'
}

],


employeePhone:[

{
required:true,
message:'请输入手机号',
trigger:'blur'
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

        const res = await selectPage({

          currentPage:this.currentPage,

          pageSize:this.pageSize,

          employeeName:this.input

        })


        if(res.data){

          this.tableData = res.data.records || []

          this.total = res.data.total || 0

        }


      }catch(error){

        console.error('加载失败:',error)

        this.$message.error('加载失败')

      }


    },




    handleSearch(){

      this.currentPage = 1

      this.loadData()

    },




    handleSizeChange(val){

      this.pageSize = val

      this.currentPage = 1

      this.loadData()

    },




    handleCurrentChange(val){

      this.currentPage = val

      this.loadData()

    },





    handleEdit(row){


      this.editDialogVisible = true


      this.editForm = {

        id:row.id,

        employeeName:row.employeeName,

        employeeAccount:row.employeeAccount,

        employeePhone:row.employeePhone,

        employeeGender:row.employeeGender || '男',

        employeeIdNumber:row.employeeIdNumber,

        status:row.status

      }


    },





    async submitEdit(){


      const valid = await this.$refs.editFormRef.validate()
        .catch(()=>false)


      if(!valid){

        return

      }



      try{


        const res = await updateEmployee(this.editForm)



        if(res.code===200){


          this.$message.success('修改成功')


          this.editDialogVisible=false


          this.loadData()


        }else{


          this.$message.error(
            res.message || '修改失败'
          )


        }



      }catch(error){


        console.error(error)


        this.$message.error('修改失败')


      }


    },





    handleEditClose(){

      this.$refs.editFormRef?.resetFields()

    },






    handleAdd(){


      this.addDialogVisible=true


      this.$nextTick(()=>{

        this.$refs.addFormRef?.clearValidate()

      })


    },






    async submitAdd(){


      const valid =
        await this.$refs.addFormRef.validate()
        .catch(()=>false)



      if(!valid){

        return

      }



      try{


        const res = await addEmployee(this.addForm)



        if(res.code===200){


          this.$message.success('添加成功')


          this.addDialogVisible=false


          this.loadData()


        }else{


          this.$message.error(
            res.message || '添加失败'
          )


        }



      }catch(error){


        console.error(error)


        this.$message.error('添加失败')


      }



    },






    handleAddClose(){

      this.$refs.addFormRef?.resetFields()

    },






    async handleToggleStatus(row){


      const data={


        id:row.id,


        status:row.status===1?0:1


      }



      try{


        const res = await updateEmployee(data)



        if(res.code===200){


          this.$message.success('状态修改成功')


          this.loadData()


        }



      }catch(error){


        console.error(error)


        this.$message.error('状态修改失败')


      }



    }




  }


}


</script>




<style scoped>


.employee-page{


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

  flex-wrap:wrap;


}



.label{


  font-size:14px;

  color:#333;

}



.search-input{


  width:220px;


}



.table-area{


  margin-top:10px;

  min-height:300px;


}




.pagination-area{


  margin-top:20px;

  display:flex;

  justify-content:flex-end;

  padding:10px 0;


}



</style>