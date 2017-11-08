<template>
  <div id="app">
    <el-container>
      <el-header>
        <el-menu :default-active="$router.currentRoute.path" mode="horizontal" :router="true">
          <el-menu-item index="/">原生H5,websocket</el-menu-item>
          <el-menu-item index="/sockjs-client">sockjs-client</el-menu-item>
          <el-menu-item index="/stomp-demo">Stomp示例</el-menu-item>
        </el-menu>
      </el-header>
      <el-main>
        <router-view/>
      </el-main>
    </el-container>
  </div>
</template>

<script>
  import ElHeader from '../node_modules/element-ui/packages/header/src/main.vue'
  import ElMain from '../node_modules/element-ui/packages/main/src/main.vue'

  export default {
    components: {
      ElMain,
      ElHeader
    },
    name: 'app'
  }
</script>

<style>
  .nav {
    margin-bottom: 20px;
  }

  .nav a {
    margin: 15px;
  }
</style>
