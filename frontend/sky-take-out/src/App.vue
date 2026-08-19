<template>
  <div id="app">
    <AppHeader />
    <div class="main-container">
      <AppSidebar />
      <div class="content-area">
     <router-view />   <!-- ← 改成 router-view -->
      </div>
    </div>
  </div>
</template>


<script>
import AppHeader from './layout/AppHeader.vue'
import AppSidebar from './layout/AppSidebar.vue'
export default {
  components: {
    AppHeader,
    AppSidebar,
   }
}
</script>


<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
html, body, #app {
  height: 100%;
  width: 100%;
}
#app {
  margin: 0;
  padding: 0;
  display: flex;
  flex-direction: column;
  height: 100vh; /* 关键：让容器占满视口高度 */
  overflow: hidden; /* 防止内容溢出导致滚动条干扰 */
}
.main-container {
  display: flex;
  flex: 1;
  margin-top: 60px;     /* 顶栏高度，避免被挡住 */
}
.content-area {
  flex: 1;
  margin-left: 180px;   /* 侧边栏宽度，避免被挡住 */
  padding: 20px;
  background: #f0f2f5;
  min-height: calc(100vh - 60px);
}
</style>