package com.mhp.coding.challenges.mapping.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String EXPECTED_LIST = "[{\"id\":1001,\"title\":\"Article Nr.: 1001\",\"description\":\"Article Description 1001\",\"author\":\"Max Mustermann\",\"blocks\":[{\"sortIndex\":0,\"text\":\"Some Text for 1001\"},{\"sortIndex\":1,\"image\":{\"id\":1,\"url\":\"https://someurl.com/image/1\",\"imageSize\":\"LARGE\"}},{\"sortIndex\":2,\"text\":\"Second Text for 1001\"},{\"sortIndex\":3,\"images\":[{\"id\":2,\"url\":\"https://someurl.com/image/2\",\"imageSize\":\"LARGE\"},{\"id\":3,\"url\":\"https://someurl.com/image/3\",\"imageSize\":\"LARGE\"}]},{\"sortIndex\":4,\"text\":\"Third Text for 1001\"},{\"sortIndex\":5,\"url\":\"https://youtu.be/myvideo\",\"type\":\"YOUTUBE\"}]},{\"id\":2002,\"title\":\"Article Nr.: 2002\",\"description\":\"Article Description 2002\",\"author\":\"Max Mustermann\",\"blocks\":[{\"sortIndex\":0,\"text\":\"Some Text for 2002\"},{\"sortIndex\":1,\"image\":{\"id\":1,\"url\":\"https://someurl.com/image/1\",\"imageSize\":\"LARGE\"}},{\"sortIndex\":2,\"text\":\"Second Text for 2002\"},{\"sortIndex\":3,\"images\":[{\"id\":2,\"url\":\"https://someurl.com/image/2\",\"imageSize\":\"LARGE\"},{\"id\":3,\"url\":\"https://someurl.com/image/3\",\"imageSize\":\"LARGE\"}]},{\"sortIndex\":4,\"text\":\"Third Text for 2002\"},{\"sortIndex\":5,\"url\":\"https://youtu.be/myvideo\",\"type\":\"YOUTUBE\"}]},{\"id\":3003,\"title\":\"Article Nr.: 3003\",\"description\":\"Article Description 3003\",\"author\":\"Max Mustermann\",\"blocks\":[{\"sortIndex\":0,\"text\":\"Some Text for 3003\"},{\"sortIndex\":1,\"image\":{\"id\":1,\"url\":\"https://someurl.com/image/1\",\"imageSize\":\"LARGE\"}},{\"sortIndex\":2,\"text\":\"Second Text for 3003\"},{\"sortIndex\":3,\"images\":[{\"id\":2,\"url\":\"https://someurl.com/image/2\",\"imageSize\":\"LARGE\"},{\"id\":3,\"url\":\"https://someurl.com/image/3\",\"imageSize\":\"LARGE\"}]},{\"sortIndex\":4,\"text\":\"Third Text for 3003\"},{\"sortIndex\":5,\"url\":\"https://youtu.be/myvideo\",\"type\":\"YOUTUBE\"}]},{\"id\":4004,\"title\":\"Article Nr.: 4004\",\"description\":\"Article Description 4004\",\"author\":\"Max Mustermann\",\"blocks\":[{\"sortIndex\":0,\"text\":\"Some Text for 4004\"},{\"sortIndex\":1,\"image\":{\"id\":1,\"url\":\"https://someurl.com/image/1\",\"imageSize\":\"LARGE\"}},{\"sortIndex\":2,\"text\":\"Second Text for 4004\"},{\"sortIndex\":3,\"images\":[{\"id\":2,\"url\":\"https://someurl.com/image/2\",\"imageSize\":\"LARGE\"},{\"id\":3,\"url\":\"https://someurl.com/image/3\",\"imageSize\":\"LARGE\"}]},{\"sortIndex\":4,\"text\":\"Third Text for 4004\"},{\"sortIndex\":5,\"url\":\"https://youtu.be/myvideo\",\"type\":\"YOUTUBE\"}]},{\"id\":5005,\"title\":\"Article Nr.: 5005\",\"description\":\"Article Description 5005\",\"author\":\"Max Mustermann\",\"blocks\":[{\"sortIndex\":0,\"text\":\"Some Text for 5005\"},{\"sortIndex\":1,\"image\":{\"id\":1,\"url\":\"https://someurl.com/image/1\",\"imageSize\":\"LARGE\"}},{\"sortIndex\":2,\"text\":\"Second Text for 5005\"},{\"sortIndex\":3,\"images\":[{\"id\":2,\"url\":\"https://someurl.com/image/2\",\"imageSize\":\"LARGE\"},{\"id\":3,\"url\":\"https://someurl.com/image/3\",\"imageSize\":\"LARGE\"}]},{\"sortIndex\":4,\"text\":\"Third Text for 5005\"},{\"sortIndex\":5,\"url\":\"https://youtu.be/myvideo\",\"type\":\"YOUTUBE\"}]}]";
    private static final String EXPECTED_FIND = "{\"id\":2002,\"title\":\"Article Nr.: 2002\",\"description\":\"Article Description 2002\",\"author\":\"Max Mustermann\",\"blocks\":[{\"sortIndex\":0,\"text\":\"Some Text for 2002\"},{\"sortIndex\":1,\"image\":{\"id\":1,\"url\":\"https://someurl.com/image/1\",\"imageSize\":\"LARGE\"}},{\"sortIndex\":2,\"text\":\"Second Text for 2002\"},{\"sortIndex\":3,\"images\":[{\"id\":2,\"url\":\"https://someurl.com/image/2\",\"imageSize\":\"LARGE\"},{\"id\":3,\"url\":\"https://someurl.com/image/3\",\"imageSize\":\"LARGE\"}]},{\"sortIndex\":4,\"text\":\"Third Text for 2002\"},{\"sortIndex\":5,\"url\":\"https://youtu.be/myvideo\",\"type\":\"YOUTUBE\"}]}";

    @Test
    public void listArticlesTest() throws Exception {
        mockMvc.perform(get("/article").accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(content().json(EXPECTED_LIST));
    }

    @Test
    public void getByIdTest() throws Exception {
        mockMvc.perform(get("/article/2002").accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().json(EXPECTED_FIND));
    }

    @Test
    public void getByIdNotFoundTest() throws Exception {
        mockMvc.perform(get("/article/9999").accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}