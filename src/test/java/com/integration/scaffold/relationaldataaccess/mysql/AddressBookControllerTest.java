package com.integration.scaffold.relationaldataaccess.mysql;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value="test")
public class AddressBookControllerTest {
    // java.lang.IllegalStateException: Configuration error: found multiple declarations of @BootstrapWith for test class [com.integration.scaffold.relationaldataaccess.mysql.AddressBookControllerTest]: [@org.springframework.test.context.BootstrapWith(value=org.springframework.boot.test.context.SpringBootTestContextBootstrapper.class),
    // @org.springframework.test.context.BootstrapWith(value=org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTestContextBootstrapper.class)]
    // 这两个注解分别使用了不同Bootstrap来启动应用的上下文。
    //@BootstrapWith(WebMvcTestContextBootstrapper.class)
    //@BootstrapWith(SpringBootTestContextBootstrapper.class)
    //因此，只能二选一。
    //@WebMvcTest
    //1 这个注解仅用于Controller层的单元测试。默认情况下会仅实例化所有的Controller，可以通过指定单个Controller的方式实现对单个Controller的测试。
    //2 同时，如果被测试的Controller依赖Service的话，需要对该Service进行mock,如使用@MockBean
    //3 该注解的定义中还包括了@AutoConfigureMockMvc注解，因此，可以直接使用MockMvc对被测controller发起http请求。当然这过程中是不会产生真实的网络流量的。
    //
    //@SpringBootTest
    //1 这个注解用于集成测试，也就是默认会加载完整的Spring应用程序并注入所有所需的bean。一般会通过带有@SpringBootApplication的配置类来实现。
    //2 由于会加载整个应用到Spring容器中，整个启动过程是非常缓慢的（通常10+秒起步），一般会用于集成测试，可以使用TestRestTemplete或者MockMvc来发起请求并验证响应结果。
    //3 SpringBootTest中的也可以使用Mockito等Mock工具来对某些bean进行mock，但是一般不会只对单个层进行测试，推荐用于单个应用的端到到集成测试。
    //4 如果涉及到第三方依赖，如数据库、服务间调用、Redis等，可以考虑服务虚拟化方案。
    // 大概意思就是：尝试执行无效的SQL语句时引发代码42001的错误，原因就是 H2 不支持
    //MySQL 的 ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 这些设置，创建表的时候去掉就好了。


    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGet() throws Exception{
        mockMvc.perform(get("/addressBook/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "    \"datas\": {\n" +
                        "        \"id\": 1,\n" +
                        "        \"userId\": 1,\n" +
                        "        \"consignee\": \"霜霜\",\n" +
                        "        \"phone\": \"15210675046\",\n" +
                        "        \"sex\": \"0\",\n" +
                        "        \"provinceCode\": \"001\",\n" +
                        "        \"provinceName\": \"河南\",\n" +
                        "        \"cityCode\": \"0001\",\n" +
                        "        \"cityName\": \"新乡\",\n" +
                        "        \"districtCode\": \"1234\",\n" +
                        "        \"districtName\": \"辉县\",\n" +
                        "        \"detail\": \"这是一个小县城\",\n" +
                        "        \"label\": \"县级市\",\n" +
                        "        \"isDefault\": 1,\n" +
                        "        \"createTime\": \"2024-03-26T15:51:13\",\n" +
                        "        \"updateTime\": \"2024-03-21T15:51:13\",\n" +
                        "        \"createUser\": 1111111,\n" +
                        "        \"updateUser\": 1111111,\n" +
                        "        \"isDeleted\": 1\n" +
                        "    },\n" +
                        "    \"resp_code\": 200,\n" +
                        "    \"resp_msg\": \"操作成功!\"\n" +
                        "}"));
    }


    @Test
    public void testAetAllConsignee() throws Exception {
        mockMvc.perform(get("/addressBook/consignee"))
                        .andExpect(status().isOk())
                        .andExpect(content().json("{\"datas\":[\"霜霜\"],\"resp_code\":200,\"resp_msg\":\"操作成功!\"}"));
    }

    // WebTestClient 是围绕 WebClient 的薄壳，可用于执行请求并公开专用的流利API来验证响应。
    // WebTestClient 通过使用模拟请求和响应绑定到 WebFlux 应用程序，或者它可以通过HTTP连接测试任何Web服务器。


}
