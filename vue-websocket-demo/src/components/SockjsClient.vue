<template>
  <div>
    <p>状态： {{status}} </p>
    <input type="button" value="发送测试消息" @click="send" :disabled="!sock"/>
    <p>消息： {{msg}} </p>
  </div>
</template>

<script>
  /**
   *
   * <pre>
   *  Version         Date            Author          Description
   * ---------------------------------------------------------------------------------------
   *  1.0.0           2017/11/07     zhuqiang        -
   * </pre>
   * @author zhuqiang
   * @version 1.0.0 2017/11/7 15:32
   * @date 2017/11/7 15:32
   * @since 1.0.0
   */
  import SockJS from 'sockjs-client'

  export default {
    data () {
      return {
        status: '未链接',
        msg: '',
        sock: null
      }
    },
    mounted () {
      let sock = new SockJS('http://localhost:8080/myHandlerSockjs')
      this.sock = sock
      sock.onopen = () => {
        this.status = '已链接'
      }
      sock.onmessage = (e) => {
        this.msg = '已收到消息：' + e.data
      }
      sock.onclose = () => {
        this.status = '链接已关闭'
      }

//      sock.close()
    },
    methods: {
      send () {
        this.sock.send('test')
        console.log('发送消息')
      }
    }
  }
</script>

<style scoped>

</style>
