# java-websocket-demo
spring-websocket、sockjs、stomp入门教程。

之前在[csdn写过一篇入门的文章](http://blog.csdn.net/mr_zhuqiang/article/details/46618197)，现在看来真的是很入门，不过也使用最原始的方法实现了一个 [网页聊天的demo](https://git.oschina.net/zhuqiang/webTM-parent.git )

如今由于项目需要，再次接触spring-websocket，有了更深入的理解，[这里写了一篇文章](https://zq99299.github.io/essay-note/chapter/websocket/spring.html)。记录了下来，本项目是该文章的实验过程。

## 该项目如何部署？

项目分为两部分，后端和前端。后端采用Java编写，前端采用vue-cli编写。

### 后端部署

java-websocket-demo 后端项目分为两个web项目：

websocket ： 部署的时候请把端口设置为 8080，前端页面中写死的该端口
stomp ： 部署的时候请把端口设置为 8082.

两个都是web项目，使用gradle构建，只配置了idea的开发环境。导入idea后，按普通web项目部署即可。

### 前端项目
vue-websocket-demo 前端项目。部署如下；

依次运行以下命令
```
npm install
npm run dev
```

`npm install`过程中如果出错，请百度处理，[可以先到这篇文章中查看错误](https://zq99299.github.io/essay-note/chapter/node/npm.html)，如果有的话，请按照解决； 或则直接加我qq：99299684

config/index.js 中配置的是该项目的端口，8081.
