package com.integration.scaffold.relationaldataaccess.mysql;

import com.integration.scaffold.relationaldataaccess.mysql.entity.AddressBook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureWebTestClient
public class AddressBookControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    void testWithMockMvc(@Autowired MockMvc mvc) throws Exception {
        mvc.perform(get("http://localhost:8080/addressBook/consignee"))
                        .andExpect(status().isOk())
                        .andExpect(content().json("{\n" +
                                "    \"datas\": [\n" +
                                "        \"云志\",\n" +
                                "        \"我爱云志\",\n" +
                                "        \"栓栓\"\n" +
                                "    ],\n" +
                                "    \"resp_code\": 200,\n" +
                                "    \"resp_msg\": \"操作成功!\"\n" +
                                "}"));
    }

    // WebTestClient 是围绕 WebClient 的薄壳，可用于执行请求并公开专用的流利API来验证响应。
    // WebTestClient 通过使用模拟请求和响应绑定到 WebFlux 应用程序，或者它可以通过HTTP连接测试任何Web服务器。
    @Test
    void testWithWebTestClient(@Autowired WebTestClient webClient) {
        AddressBook addressBook=new AddressBook();
        addressBook.setUserId(2l);
        addressBook.setConsignee("yunzhi");
        addressBook.setIsDeleted(1);
        addressBook.setIsDefault(1);
        addressBook.setPhone("16637316086");
        addressBook.setSex("0");
        webClient
                .post().uri("http://localhost:8080/addressBook/save").body(addressBook,AddressBook.class)
                .exchange()
                .expectStatus().isOk();
                //.expectBody(String.class).isEqualTo("Hello World");
    }
    @Test
    void shouldReturnDefaultMessage() throws Exception {
//        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
//                .andExpect(content().string(containsString("Hello, World")));
    }

}
