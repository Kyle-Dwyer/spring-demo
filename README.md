# spring-demo


# 项目介绍
  本项目是一个spring的demo，用以测试增加skywalking收集trace的tag。


# 环境依赖
  相比常规spring项目，pom增加了logback的依赖，skywalking修改logback的依赖以及skywalking对trace收集的依赖。
  

# 使用说明
## 运行参数
修改VM options为：

`-javaagent:xxx/spring-demo/src/main/resources/skywalking-agent/skywalking-agent.jar -Dskywalking.agent.service_name=DemoService -Dskywalking.collector.backend_service=服务器ip:11800 -Dskywalking.plugin.toolkit.log.grpc.reporter.server_host=服务器ip -Dskywalking.plugin.toolkit.log.grpc.reporter.server_port=11800`

xxx为绝对路径的前缀，服务器ip为部署的skywalking oap server的ip，端口默认11800，配置不同修改即可。
## 发送请求
项目包含GET和POST两个请求，项目运行后请求url为127.0.0.1:8080，请求头增加参数Uid（值随意）。

POST请求的body为application/json格式，要求含有name字段，内容示例为{"name":"zhang"}。
