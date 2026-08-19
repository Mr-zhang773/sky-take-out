<template>
  <el-menu
    :default-active="activeIndex"
    class="header-menu"
    mode="horizontal"
    background-color="#1f2d3d"
    text-color="#bfcbd9"
    active-text-color="#409EFF"
    @select="handleSelect"
  >
    <el-menu-item index="/home">首页</el-menu-item>
  </el-menu>
</template>

<script>
export default {
  data() {
    return {
      activeIndex: this.$route.path || '/home'
    }
  },
  methods: {
    handleSelect(key) {
      this.$router.push(key)
    }
  },
  watch: {
    $route(newVal) {
      this.activeIndex = newVal.path
    }
  }
}
</script>

<style scoped>
.header-menu {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: 999;
  height: 60px;
  line-height: 60px;
  user-select: none;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.3);  /* 加一点阴影 */
}
</style>