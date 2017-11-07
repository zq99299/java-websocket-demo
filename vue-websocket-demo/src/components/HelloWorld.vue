<template>
  <div class="hello">
    状态：{{msg}}
  </div>
</template>

<script>
  export default {
    data () {
      return {
        msg: 'Welcome to Your Vue.js App'
      }
    },
    mounted () {
      let ws = new WebSocket('ws://localhost:8080/myHandler')
      ws.onopen = () => {
        this.msg = '链接成功.'
        // Web Socket 已连接上，使用 send() 方法发送数据
        ws.send('发送数据')
      }

      ws.onmessage = (evt) => {
        let receivedMsg = evt.data
        this.msg = '数据已接收...：' + receivedMsg
      }

      ws.onclose = () => {
        // 关闭 websocket
        this.msg = '连接已关闭...'
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
