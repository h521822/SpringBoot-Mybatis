### SpringBoot：实现热部署
---
#### 方法一：使用spring-boot-devtools进行热部署以及不生效的问题解决

1. 在Maven中添加依赖
```
        <!-- devtools插件，热部署 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <optional>true</optional>
            <scope>true</scope>
        </dependency>
```
2. 在`application.properties`中配置
```
#热部署生效
spring.devtools.restart.enabled: true
#设置重启的目录
#spring.devtools.restart.additional-paths: src/main/java
#classpath目录下的WEB-INF文件夹内容修改不重启
spring.devtools.restart.exclude: WEB-INF/**
```
**但是,为什么配置了还是没有用呢 ?!**

这是因为idea默认是没有自动编译的,我们这里需要添加修改配置.打开设置
3. 

  (1) File-Settings-Compiler-Build Project automatically
  
  (2) ctrl + shift + alt + /,选择Registry,勾上 Compiler autoMake allow when app running


#### 方法二：使用`thymeleaf`进行热部署
1. 在Maven中添加依赖
```
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>
```
2.  在`application.properties`中配置
```
#清除缓存，实现热部署。也就是修改了html后不用重启(提前引入thymeleaf依赖)
spring.thymeleaf.cache=false
```
**PS: 修改完html后一定要ctrl+f9重新build一下。再回到浏览器刷新**

