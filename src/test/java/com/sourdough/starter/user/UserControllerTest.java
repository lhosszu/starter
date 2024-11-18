package com.sourdough.starter.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sourdough.starter.user.dto.UserRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser
@WebMvcTest(UserController.class)
class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void canFindUser() throws Exception {
        Mockito.when(userService.find("john-doe")).thenReturn(
                User.enabled().name("john-doe").password("lipsum").build()
        );

        mockMvc.perform(get("/users/john-doe"))
               .andExpect(status().isOk());
    }

    @Test
    void canCreateNewUser() throws Exception {
        Mockito.when(userService.create("john-doe", "lipsum")).thenReturn(
                User.enabled().name("john-doe").password("lipsum").build()
        );

        mockMvc.perform(post("/users")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(new UserRequest("john-doe", "lipsum"))))
               .andExpect(status().isOk());
    }
}
