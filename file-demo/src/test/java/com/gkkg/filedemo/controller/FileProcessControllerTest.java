package com.gkkg.filedemo.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
public class FileProcessControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void uploadFile()throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("file", "example.pdf",
                "application/pdf", "This new File To Upload".getBytes());
        MvcResult mvcResult=this.mockMvc.perform(
                multipart("/filesystem/uploadFile").
                        file(multipartFile)
                        .param("fileName", "sample1.pdf")
        )
                .andExpect(status().is(200)).andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        Assertions.assertEquals(content, "{\"status\":\"File Saved Successfully.\"}");

    }

    @Test
    public void downLoadFile()throws Exception{
        MvcResult mvcResult=this.mockMvc.perform(
                post("/filesystem/getFile")
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .param("fileName", "sample1.pdf")
                        .param("filePath", "/Users/gannavarapugopikovilkumar/Desktop/saveFilesFromProject/")
        )
                .andExpect(status().is(200)).andReturn();

        Assertions.assertEquals(23, mvcResult.getResponse().getContentAsByteArray().length);

    }





}
