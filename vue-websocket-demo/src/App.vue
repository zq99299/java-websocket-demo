<template>
  <div id="app">
    <div class="nav">
      <router-link to="/">原生H5,websocket</router-link>
      <router-link to="/sockjs-client">sockjs-client</router-link>
    </div>
    <router-view/>
  </div>
</template>

<script>
  export default {
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
