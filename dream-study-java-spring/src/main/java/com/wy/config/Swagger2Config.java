package com.wy.config;

import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * 使用swagger2自动生成文档,文档查看地址http://localhost:8810/swagger-ui.html#/
 * {@link http://www.leftso.com/blog/402.html}
 * 
 * 在使用的时候,swagger-annotations和swagger-models要用1.5.21版本的,否则会有问题
 * 
 * 若字段以大写开头,则需要加上jackjson其他2个注解,否则swagger2将无法显示字段注释
 * JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class):字段以大写开头
 * JsonAutoDetect(fieldVisibility = Visibility.ANY):检测所有修饰符字段
 * 
 * {@link EnableOpenApi}:Swagger3.0需要使用该注解打开网页浏览,且浏览地址变为ip:port/swagger-ui/index.html
 * 
 * @author 飞花梦影
 * @date 2020-12-08 11:29:10
 * @git {@link https://github.com/mygodness100}
 */
// @Configuration
// @EnableSwagger2
@EnableOpenApi
public class Swagger2Config {

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("通用文档").apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("com.wy.crl")).paths(PathSelectors.any()).build()
				// 忽略某个类,可连写多个
				.ignoredParameterTypes(Swagger2Config.class);
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("接口文档").description("通用接口文档").version("1.0").build();
	}
}