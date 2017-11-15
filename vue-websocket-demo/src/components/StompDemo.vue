<template>
  <div class="stomp-demo">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>本页功能</span>
      </div>
      <p>1. 服务链接</p>
      <p>2. 广播订阅</p>
      <p>3. 链接授权</p>
      <p>4. 点对点订阅</p>
      <p>5. 消息发送改变订阅的参数</p>
      <p>其他功能未做，相信这些已经可以让你入门和更加深入的了解 spring-websocket的用法了</p>
    </el-card>
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
        <el-button style="float: right; padding: 3px 0" type="text" v-if="!subscribeNews.comic" @click="comicSubscribe">
          订阅
        </el-button>
        <el-button style="float: right; padding: 3px 0" type="text" v-else @click="comicUnsubscribe">退订</el-button>
      </div>
      <div v-for="(item,index) in subscribeNews.comics" :key="index" class="text item">
        {{item}}
      </div>
    </el-card>
    <el-card class="box-card">
      <div slot="header">
        <span>需要订阅 - 八卦</span>
        <el-button style="float: right; padding: 3px 0" type="text" v-if="!subscribeNews.gossip"
                   @click="gossipSubscribe">订阅
        </el-button>
        <el-button style="float: right; padding: 3px 0" type="text" v-else @click="gossipUnsubscribe">退订</el-button>
      </div>
      <div v-for="(item,index) in subscribeNews.gossips" :key="index" class="text item">
        {{item}}
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
        publicNews: [],
        subscribeNews: {
          comic: false,  // 动漫
          gossip: false, // 八卦
          comics: [],
          gossips: []
        }
      }
    },
    created () {
      this.varStore = {
        stomp: null, // 链接实例
        otherSubscribe: null  // 其他两个订阅的订阅实例
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
      },
      // 动漫订阅
      comicSubscribe () {
        this.otherSubscribe('comic')
        this.otherSubscribeInit()
      },
      // 八卦订阅
      gossipSubscribe () {
        this.otherSubscribe('gossip')
        this.otherSubscribeInit()
      },
      otherSubscribeInit () {
        // 如果还没有开启一个订阅实例，则开启
        if (!this.varStore.otherSubscribe) {
          // 注意这里的地址；和后端发送的地址是一样的;只是增加了/user的前端，该前缀标识是一个点对点的订阅
          // 后端框架会特殊处理
          this.varStore.otherSubscribe = this.varStore.stomp.subscribe('/user/queue/other', message => {
            let news = JSON.parse(message.body)
            // 把获取到的列表赋值给该变量，页面中会循环出该信息
            this.subscribeNews.comics = news.comics
            this.subscribeNews.gossips = news.gossips
          })
        }
      },
      otherSubscribe (type) {
        // 发送信息
        this.varStore.stomp.send('/app/queue/other', {}, JSON.stringify({
          type: 1, // 订阅
          body: type // 订阅的内容是动漫还是八卦类型
        }))
        if (type === 'comic') {
          this.subscribeNews.comic = true
        } else {
          this.subscribeNews.gossip = true
        }
      },
      comicUnsubscribe () {
        this.otherUnsubscribe('comic')
      },
      gossipUnsubscribe () {
        this.otherUnsubscribe('gossip')
      },
      otherUnsubscribe (type) {
        // 发送信息
        this.varStore.stomp.send('/app/queue/other', {}, JSON.stringify({
          type: 2, // 退订
          body: type // 订阅的内容是动漫还是八卦类型
        }))
        if (type === 'comic') {
          this.subscribeNews.comic = false
        } else {
          this.subscribeNews.gossip = false
        }
        /** 其实退订只需要下面这样就行了。但是这里是演示用参数怎么来控制应用程序
         *  this.varStore.otherSubscribe.unsubscribe()
         *  使用 unsubscribe 方法的时候呢，在后端会收到一个StompCommand，里面包含链接，订阅，取消订阅，端口等事件的状态
         *  可以通过这个状态进行其他业务操作
         */
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
