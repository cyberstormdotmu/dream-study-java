package com.wy;

/**
 * 24种设计模式:命令模式,类似门面模式;迭代模式:jdk中出现了迭代器,现在已经基本不用了
 * 组合模式:当整个项目呈现明显的树形结构时可使用,体现局部和整体的关系,例如人员关系表
 *
 * @apiNote 装饰器模式:类似于jdk中的流,子类都实现一个接口或抽象类,同时类中会存在一个构造函数以接口类型为参数
 *          桥接模式是抽象类中声明一个自己的实现类变量,其他所有的子类继承抽象类或实现类
 * @apiNote 工厂->策略:工厂注重对象的创建,而策略是注重行为的实现
 * @apiNote 工厂->抽象工厂:抽象工厂多了中间的抽象类,对接口中的一些方法进行默认的实现
 * @apiNote 策略->代理:策略模式的使用类不需要实现接口,而代理模式的实用类需要实现接口
 * @apiNote 策略->门面:策略注重于单个方法的实现,而不是一系列方法的顺序实现,门面则是一系列特定操作的实现
 * @apiNote 门面->模版:门面中所有的方法是按照特定的顺序执行,而模版中多了一个抽象类,
 *          抽象类负责通用方法的调用,而特殊方法则需要子类实现之后,通用方法中会穿插调用特殊方法
 *
 * @author ParadiseWY
 * @date 2020-10-16 22:59:31
 * @git {@link https://github.com/mygodness100}
 */
public class Application {

}