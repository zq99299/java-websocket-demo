<template>
  <div class="stomp-demo">
    <div class="bar">
      <div class="btn-group">
        <el-button type="danger" v-if="isConnect" @click="disconnect">断开链接</el-button>
        <el-button type="success"
                   v-else
                   :loading="connectLoading" @click="connect">链接服务
        </el-button>
      </div>
      <el-alert
        v-if="msg.status == 0"
        :closable="false"
        :title="msg.info"
        type="error">
      </el-alert>
      <el-alert
        v-else
        :closable="false"
        :title="msg.info"
        type="success">
      </el-alert>
    </div>
    <el-card class="box-card">
      <div slot="header">
        <span>公共新闻 - 链接后自动订阅</span>
      </div>
      <div v-for="(item,index) in publicNews" :key="index">
        {{item}}
      </div>
    </el-card>
    <el-card class="box-card">
      <div slot="header">
        <span>需要订阅 - 动漫</span>
        <el-button style="float: right; padding: 3px 0" type="text">订阅</el-button>
      </div>
      <div v-for="o in 4" :key="o" class="text item">
        {{'列表内容 ' + o }}
      </div>
    </el-card>
    <el-card class="box-card">
      <div slot="header">
        <span>需要订阅 - 八卦</span>
        <el-button style="float: right; padding: 3px 0" type="text">订阅</el-button>
      </div>
      <div v-for="o in 4" :key="o" class="text item">
        {{'列表内容 ' + o }}
      </div>
    </el-card>
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
        msg: {
          status: 0,
          info: '~'
        },
        connectLoading: false,
        isConnect: false,
        publicNews: []
      }
    },
    created () {
      this.varStore = {
        stomp: null // 链接实例
      }
    },
    mounted () {

    },
    methods: {
      connect () {
        this.connectLoading = true
        // 注意这里的链接地址
        let ws = new SockJS('http://localhost:8082/stomp')
        let stomp = Stomp.over(ws)

        // 注意这里的header 暂时不是必须的。
        let headers = {
          login: 'mylogin',
          passcode: 'mypasscode',
          // additional header
          'client-id': 'my-client-id'
        }
        stomp.connect(headers,
          () => {
            this.varStore.stomp = stomp
            this.connectLoading = false
            this.msg.status = 1
            this.msg.info = '链接成功'
            this.isConnect = true
            // 链接成功之后，我们订阅 /topic/public_news
            this.topicPublicNews()
          },
          (error) => {
            this.connectLoading = false
            this.msg.status = 0
            this.msg.info = `链接失败: ${error}，或许是后端服务未开启`
            this.isConnect = false
          })
      },
      disconnect () {
        this.varStore.stomp.disconnect()
        this.msg.status = 0
        this.msg.info = `链接断开: 手动断开`
        this.isConnect = false
      },
      topicPublicNews () {
        // 注意这里的地址，和 后端发送消息的地址是一致的
        this.varStore.stomp.subscribe('/topic/public_news', message => {
          let news = JSON.parse(message.body)
          // 把获取到的列表赋值给该变量，页面中会循环出该信息
          this.publicNews = news
        })
      }
    }
  }
</script>

<style lang="stylus" rel="stylesheet/stylus" scoped>
  .stomp-demo {
    .bar {
      .btn-group {
        text-align center
        margin-bottom 15px
      }
    }
    .box-card {
      margin-top 15px
      margin-bottom 15px
    }
  }
</style>
