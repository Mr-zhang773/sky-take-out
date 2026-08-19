<template>
  <el-menu
    router
    default-active="/category"
    class="sidebar-menu"
    background-color="#1f2d3d"
    text-color="#bfcbd9"
    active-text-color="#409EFF"
    :unique-opened="true"
  >
    <el-menu-item index="/category">
      <i class="el-icon-s-grid"></i>
      <span slot="title">分类管理</span>
    </el-menu-item>

    <el-menu-item index="/employee">
      <i class="el-icon-user"></i>
      <span slot="title">员工管理</span>
    </el-menu-item>

    <el-menu-item index="/dish">
      <i class="el-icon-food"></i>
      <span slot="title">菜品管理</span>
    </el-menu-item>

    <el-menu-item index="/order">
      <i class="el-icon-document"></i>
      <span slot="title">订单管理</span>
    </el-menu-item>

    <el-menu-item index="/report">
      <i class="el-icon-data-line"></i>
      <span slot="title">数据报表</span>
    </el-menu-item>
  </el-menu>
</template>

<script>
export default {
  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath)
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath)
    }
  }
}
</script>

<style scoped>
.sidebar-menu {
  position: fixed;
  top: 60px;
  left: 0;
  width: 180px;
  height: calc(100vh - 60px);
  overflow-y: auto;
  border-right: 1px solid #2d3a4b;
}

/* 菜单滚动条样式 */
.sidebar-menu::-webkit-scrollbar {
  width: 4px;
}

.sidebar-menu::-webkit-scrollbar-track {
  background: #1f2d3d;
}

.sidebar-menu::-webkit-scrollbar-thumb {
  background: #409EFF;
  border-radius: 2px;
}
</style>