# 压力测试





# 性能指标

* RT:Response Time,响应时间,指用户从客户端发起一个请求开始,到客户端接收到从服务器返回的响应结束,整个过程所耗费的时间
* HPS:Hits Per Second,每秒点击次数,单位是次/秒
* TPS:Transaction Per Second,系统每秒处理交易数,单位是笔/秒,系统处理业务能力
* QPS:Query Per Second,系统每秒处理查询次数,单位是次/秒
* 一般情况下用TPS衡量整个业务流程,QPS衡量接口查询次数,HPS表示服务器单击请求
* TPS,QPS,HPS一般情况下都是越大越好
  * 金融行业:1000TPS-50000TPS,不包括互联网化的活动
  * 保险行业:100TPS-100000TPS,不包括互联网化的活动
  * 制造行业:10TPS-5000TPS
  * 互联网电子商务:10000TPS-1000000TPS
  * 互联网中型网站:1000TPS-50000TPS
  * 互联网小型网站:500TPS-10000TPS
* 最大响应时间:Max Response Time,只用户发出请求或指令到系统做出反应的最大时间
* 最少响应时间:Mininum Response Time,用户发生请求或指令到响应的最少时间
* 90%响应时间:90%Response Time,用户响应时间进行排序,第90%的响应时间
* 性能测试主要关注的三个指标:
  * 吞吐量:美标系统能够处理的请求数,任务数
  * 响应时间:服务处理一个请求或一个任务的耗时
  * 错误率:一批请求中结果出错的请求所占比例
* 中间件越多,性能损失越大,大多都损失在网络交互中



# JMeter

* [官网](https://jmeter.apache.org/download_jmeter.cgi)下载压缩包压缩之后进入bin目录,点击jmeter.bat即可启动
* 线程组:配置用于测试的线程参数,如线程数,循环次数
* Http请求默认值:线程组->配置原件->HTTP请求默认值,配置一些通用属性
* Http请求:取样器(sampler)->HTTP请求,配置需要进行测试的http请求,点击上方的运行按钮即可测试
* 查看结果树:监听器->查看结果树,查看每一次请求的详情,失败成功的
* 汇总报告:监听器->汇总报告,查看测试所有的汇总信息
* 聚合报告:监听器->聚合报告,查看测试的吞吐量等信息



# Jvisualvm

* JDK自带监控工具,是jconsole的升级版,主要是监控内存泄漏,跟踪垃圾回收,执行时内存,cpu分析,线程分析等
* 从JDK/bin中直接双击打开即可,是一个可视化界面,监控中绿色表示正在运行的线程,紫色表示休眠线程,黄色表示等待线程,橙色表示驻留,线程池里的空闲线程,红色表示监视,线程阻塞的,正在等待锁
