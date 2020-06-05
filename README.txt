框架个性化定制或规范统一守则及使用方法

一.数据校验
	a.框架:hibernate,自定义的rules validator
	b.扩展:
		基于hibernate的对象整体校验添加了只校验对象的指定属性
	c.使用方法:
		1.入参为JAVA BEAN的整体校验
			1.1.直接在入参的参数上添加注解javax.validation.Valid或者org.springframework.validation.annotation.Validated(前提是入参对象中已经包含了校验规则)。
		2.入参为JAVA BEAN的指定属性的校验
			2.1.hibernate自带的分组功能：即给bean的校验规则添加groups
			2.2.扩展hibernate:使用@ValidAssigned注解并指定需要校验的属性
			注意:由于@ValidAssigned是基于hibernate validate 的扩展,所以不可和hibernate的配置冲突
		3.入参为Map的数据校验
			3.1.使用自定的校验规则:添加@ValidRules注解并指定需要校验的字段key和规则
	d.示例:com.bojin.api.web.controller.TestValidController
二.返回值剔除
	a.框架:jackson,fastjson
	b.使用方法:
		1.fastjson(适用于所有对象):
			1.1:使用@Serialize注解来描述需要序列化的字段
				1.1.1:clazz:指定属性对应的class(如不指定则只所有递归的指定key值)
				1.1.2:include:指定需要序列化的key值
				1.1.3:exclude:指定不需要序列化的key值
				注意:exclude优先于include,未在include,exclude列表的不会序列化
		2.jackson(仅适用于javabean,不建议使用):
	c.示例:com.bojin.api.web.controller.TestSerializeController
				
三.全局异常处理
	使用SPRING MVC 自带的全局的 @ExceptionHandler进行捕获和处理异常
	参考:BaseController
四.Repository开发:
	a.开发使用技术:
		spring data jpa(hibernate) + spring jdbc(JdbcTemplate,NamedParameterJdbcTemplate)混合使用
	b.开发规范:
		1.所有Repository都继承BaseRepository
		2.Repository的扩展(对于返回值):
			
		3.jpa满足不了的需要自定义处理的:(必须按照此要求,否则jpa会报错)
			3.1.接口命名规则:相应的Repository名称后面+Cust
			3.2.实现类命名规则:2.1的名称后面+Impl
			3.3.相应的Repository必须继承2.1的接口
	c.示例:com.bojin.api.core.demo.repository.UserRepository
五.json数据转换
	结合fastJson和jackson进行json-object的数据转换

六.日志log
	统一使用log4j的日志,并且所有的logger对象尽量封装在base*的基类里面
	
七.入参和返回值
	a.入参:
	b.返回值:统一为ResponseVo
	
八.缓存
	建议缓存使用spring的缓存注解把事务交给spring管理,
	

九.邮件
	
十.工具类
	1.appach commons
	2.google guava

十一.定时任务
	a.框架:扩展于spring schedule(由于Schedule注解自能添加到方法上)
	b.使用方法:在job类上定义@ScheduleJob 和 @Component注解并配置@ScheduleJob的执行时间
	c.实例:详细参考com.bojin.queue.core.demo.job.DemoJob

十二.返回值编码映射
	
十三.日志自动入库和发送邮件

	
十五.CONTROLLER自动分发到对应的service
	a.实现方式:在controller层使用aop处理
	b.controller,service使用要求
		1.controller的映射类必须用ServiceAutoMapping注解指定
		2.controller的返回结果统一为ResponseVo
		3.controller的入参和service的入参类型一一对应
		4.service的返回值类型:
			4.1.为ResponseVo时:则直接返回service的返回值
			4.2.不为ResponseVo时:则aop会把service的返回值当作数据创建一个ResponseVo返回
	c.示例:
		TestServiceAutoMappingController

