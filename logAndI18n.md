spring官网介绍了3种日志框架
Spring Boot使用Commons Logging进行所有内部日志记录但使底层日志实现保持开放状态。为Java Util Logging、Log4j2和Logback提供了默认配置。

默认情况下，如果您使用“starter”，则使用Logback进行日志记录。还包括适当的Logback路由，以确保使用Java Util Logging、Commons Logging、Log4J或SLF4J的依赖库都能正确工作。

目前最受欢迎且被广泛采用的日志框架是Log4j 2和Logback
选用springboot默认的Logback进行日志输出。

日志默认输出到控制台，并不会输出到文件（默认情况下，Spring Boot只记录到控制台，不写日志文件）

因为标准的logback.xml配置文件加载得太早，所以不能在其中使用扩展。需要使用logback-spring.xml或定义一个日志记录。配置属性。本次选用logback-spring.xml进行日志配置。