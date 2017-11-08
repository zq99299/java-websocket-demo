<template>
  <div class="stomp-demo">
    <p> 链接状态：{{status}}</p>
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
   * @version 1.0.0 2017/11/7 17:07
   * @date 2017/11/7 17:07
   * @since 1.0.0
   */
  import SockJS from 'sockjs-client'
  import Stomp from 'stompjs'

  export default {
    data () {
      return {
        status: '未链接'
      }
    },
    mounted () {
      // 注意这里的地址，后之前的不是一个项目了
      var ws = new SockJS('http://localhost:8082/stomp')
      var client = Stomp.over(ws)

      // 注意这里的header 暂时不是必须的。
      var headers = {
        login: 'mylogin',
        passcode: 'mypasscode',
        // additional header
        'client-id': 'my-client-id'
      }
      client.connect(headers,
        () => {
          this.status = '已链接'
        },
        (error) => {
          this.status = '链接失败:' + error
        })
    }
  }
</script>

<style scoped>

</style>
