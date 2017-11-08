import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import SockjsClient from '@/components/SockjsClient'
import StompDemo from '@/components/StompDemo'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      component: HelloWorld
    },
    {
      path: '/sockjs-client',
      component: SockjsClient
    },
    {
      path: '/stomp-demo',
      component: StompDemo
    }
  ]
})
