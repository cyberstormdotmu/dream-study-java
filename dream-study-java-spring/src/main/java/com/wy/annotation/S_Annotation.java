package com.wy.annotation;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnNotWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.boot.jackson.JsonComponentModule;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * 一些常用注解
 * 
 * 一些需要开启之后才可使用相关注解的注解,通常以Enable开头,有些已经在spring.factories中自动配置:
 * 
 * <pre>
 * {@link EnableTransactionManagement}:开启事务,默认自动配置,不需要手动添加,可使{@link Transactional}生效
 * {@link EnableScheduling}:开启定时任务,可使{@link Scheduled}生效
 * {@link EnableAsync}:开启异步任务,可使{@link Async}生效
 * {@link EnableCaching}:开启缓存,可使{@link Cacheable}{@link CachePut}{@link CacheEvict}等缓存相关注解生效
 * {@link EnableWebSecurity}:启用SpringSecurity,被该注解修饰的类需要继承{@link WebSecurityConfigurerAdapter}
 * {@link EnableGlobalMethodSecurity}:需要先开启 EnableWebSecurity ,可使{@link PreAuthorize}{@link PostAuthorize}生效
 * </pre>
 * 
 * 初始化相关注解:
 * 
 * <pre>
 * {@link Bean}:实例化该注解代表的方法返回值,并且纳入spring的上下文管理
 * {@link Lazy}:用于指定该Bean是否取消预初始化,用于注解类,延迟初始化
 * {@link Scope}:标识一个方法对象是单例还是原型,默认单例
 * {@link Primary}:当系统中需要配置多个具有相同类型的bean时,该注解标识的Bean可以被指定为默认使用的Bean
 * {@link Qualifier}:当系统中需要配置多个具有相同类型的bean时,若使用这些bean,依赖注入时无法判断,需要使用该注解进行分辨
 * {@link Autowired}:默认先按byType注入,如果发现找到多个bean,则按byName方式比对.如果还有多个,则报异常.
 * 		可结合 Qualifier 按byName方式手动指定注入组件
 * {@link Condition}:接口,判断在启动是否加载某个类,配合Conditional注解使用
 * {@link Conditional}:配合Condition使用,判断{@link Condition#matches}是否返回true来决定注解修饰的方法或类是否注册到Spring中
 * {@link ConditionalOnBean}:仅仅在当前上下文中存在某个对象时,才会实例化一个Bean
 * {@link ConditionalOnClass}:该注解判断当前环境中是否有某个类,有则该注解修饰的方法或类才加载
 * {@link ConditionalOnExpression}:当spel表达式为true的时候,才会实例化一个Bean
 * {@link ConditionalOnMissingBean}:仅仅在当前上下文中不存在某个对象时,才会实例化一个Bean
 * {@link ConditionalOnMissingClass}:该注解判断当前环境中是否没有某个类,没有则该注解修饰的方法或类才加载
 * {@link ConditionalOnWebApplication}:该注解判断当前环境是否为一个web应用,是则该注解修饰的类或方法才加载
 * {@link ConditionalOnNotWebApplication}:作用和ConditionalOnWebApplication相反,不是才加载
 * {@link ConditionalOnProperty}:该注解表示指定配置存在且等于某个给定值时类生效.若配置不存在,默认不生效
 * ->{@link ConditionalOnProperty#value()}:等同于name(),需要检查的配置中的属性名
 * ->{@link ConditionalOnProperty#prefix()}:需要检查的配置前缀,可写可不写
 * ->{@link ConditionalOnProperty#havingValue()}:检查value()指定属性的值是否equals该方法指定的值,true->配置生效,false->不生效
 * ->{@link ConditionalOnProperty#matchIfMissing()}:当value()指定属性的值不存在或错误时的行为,true->仍然加载,默认false->不加载
 * {@link ConditionalOnResource}:classpath里有指定的资源时才加载被修改的类
 * {@link DependsOn}:当初始化一个bean时,需要先初始化其他bean
 * </pre>
 * 
 * 请求相关
 * 
 * <pre>
 * {@link CrossOrigin}:允许跨域访问资源
 * {@link RequestMapping}:指定访问的请求路径以及其他请求参数
 * ->{@link RequestMapping#params()}:指定request中必须包含某些参数键值对时,才让该方法处理
 * ->{@link RequestMapping#headers()}:指定request中必须包含某些指定的header值,才能让该方法处理请求
 * ->{@link RequestMapping#consumes()}:指定处理请求的提交内容类型(content-type)
 * ->{@link RequestMapping#produces()}:指定返回的内容类型,仅当request请求头中的(Accept)类型中包含该指定类型才返回
 * {@link GetMapping}:GET方式访问方法
 * {@link PostMapping}:POST方式访问方法
 * {@link DeleteMapping}:DELETE方式访问方法
 * {@link PutMapping}:PUT方式方法方法
 * {@link RequestBody}:只能在POST请求中使用,请求头必须是application/json,用于复杂数据传输
 * {@link RequestParam}:提取和解析请求中的参数,提取和解析请求中的参数,单个参数或文件上传时使用
 * {@link PathVariable}:处理request uri部分,当使用url/{paramId}/aa/{test}的URL时,{}内的值可通过该注解绑定到方法参数
 * {@link RequestHeader}:将请求头中的参数值绑定到方法参数上
 * {@link CookieValue}:将请求中指定cookie的值绑定到方法参数上
 * {@link ResponseBody}:以JSON格式返回数据
 * {@link RestController}:{@link Controller}和{@link ResponseBody}的合体,访问请求并以JSON格式返回数据
 * {@link ResponseStatus}:该注解指定的code和reason会被返回给前端,value是http状态码,比如404等;reason是错误信息.
 * </pre>
 * 
 * {@link Profile}:添加在类或方法上时表示在指定环境下才有作用
 * {@link JsonBackReference},{@link JsonManagedReference}:配对使用,通常用在父子关系中,比如树形结构.
 * JsonBackReference标注的属性在序列化(对象转json)时,会被忽略;JsonManagedReference标注的属性则会被序列化.
 * 在序列化时,JsonBackReference的作用相当于JsonIgnore,此时可以没有JsonManagedReference.
 * 但在反序列化(json转对象)时,如果没有JsonManagedReference,则不会自动注入JsonBackReference标注的属性(被忽略的父或子).
 * 如果有JsonManagedReference,则会自动注入JsonBackReference标注的属性.
 * 此时JsonManagedReference和JsonBackReference并不是在同一个属性上
 * 
 * {@link JsonComponent}:该注解可以将实现了{@link JsonSerializer}或{@link JsonDeserializer}的类指定序列化方式和反序列化方式.
 * 通常可以直接继承重写{@link StdSerializer#serialize}或{@link StdDeserializer#deserialize}
 * {@link JsonComponentModule}:解析{@link JsonComponent}
 * 
 * {@link ResponseBody}:可以实现{@link ResponseBodyAdvice}接口来改变返回参数
 * 
 * 参数校验相关:
 * 
 * <pre>
 * {@link Valid}:非Spring校验注解,同样可以校验,必须在{@link RequestBody}修饰的参数上使用
 * {@link Validated}:Spring在 Valid 上做了更多操作,可使用在任何参数上
 * {@link NotBlank}:非空检查,包括null,空字符串,制表符等
 * {@link NotNull}:非null检查
 * {@link Null}:null检查
 * {@link NotEmpty}:检查约束元素是否为NULL或者是EMPTY
 * {@link AssertTrue}:验证 Boolean 对象是否为 true
 * {@link AssertFalse}:验证 Boolean 对象是否为 false
 * {@link Size}:验证对象(Array,Collection,Map,String)长度是否在给定的范围之内
 * {@link Past}:验证 Date 和 Calendar 对象是否在当前时间之前
 * {@link Future}:验证 Date 和 Calendar 对象是否在当前时间之后
 * {@link Pattern}:验证 String 对象是否符合正则表达式的规则
 * {@link Min}:验证 Number 和 String 对象是否大等于指定的值
 * {@link Max}:验证 Number 和 String 对象是否小等于指定的值
 * {@link DecimalMax}:验证值必须小于指定值.参数是一个通过BigDecimal定义的最大值的字符串表示.小数存在精度
 * {@link DecimalMin}:验证值必须大于指定值.参数是一个通过BigDecimal定义的最小值的字符串表示.小数存在精度
 * {@link Digits}:验证字符串是否是符合指定格式的数字,interger指定整数精度,fraction指定小数精度
 * {@link CreditCardNumber}:信用卡验证
 * {@link Email}:验证是否是邮件地址,如果为null,不进行验证,算通过验证
 * {@link URL}:URL校验
 * </pre>
 * 
 * 非Spring常用注解:
 * 
 * <pre>
 * {@link PostConstruct}:非Spring注解,表示该组件被初始化之前需要执行的方法,在构造和init()之间调用.
 * 		一个类中只能有一方法被该注解修改,该方法不能有参数,无返回值,非静态
 * {@link PreDestroy}:非Spring注解,表示该组件被销毁之前需要执行的方法,在destory()之后调用.其他同 PostConstruct
 * {@link Resource}:和 Autowired 正好相反的注入方式
 * </pre>
 * 
 * @author 飞花梦影
 * @date 2018-07-20 23:00:58
 * @git {@link https://github.com/dreamFlyingFlower}
 */
public class S_Annotation {

}