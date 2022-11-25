package nl.fontys.marketplacebackend.controller;

import nl.fontys.marketplacebackend.dto.logindtos.LoginRequestDTO;
import nl.fontys.marketplacebackend.dto.logindtos.LoginResponseDTO;
import nl.fontys.marketplacebackend.service.loginservices.LoginService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@ExtendWith(SpringExtension.class)
@WebMvcTest(LoginController.class)
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoginService loginService;

    @Test
    void login() throws Exception {
        LoginRequestDTO expectedRequest = LoginRequestDTO.builder()
                .email("email")
                .password("password")
                .build();
        when(loginService.login(expectedRequest)).thenReturn(
                LoginResponseDTO.builder()
                        .accessToken("123")
                        .build()
        );

        mockMvc.perform(post("/login")
                .contentType(APPLICATION_JSON_VALUE)
                .content("""
                            {
                                "email": "email",
                                "password": "password"
                            }
                        """))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "accessToken": "123"
                        }
                    """));
        verify(loginService).login(expectedRequest);
    }
}