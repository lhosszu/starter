package com.sourdough.starter.user;

import com.sourdough.starter.user.dto.CreateUserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static com.sourdough.starter.Fixtures.johnDoe;
import static com.sourdough.starter.util.RequestUtils.getObjectMapper;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        when(userService.find("john-doe")).thenReturn(
                johnDoe().build()
        );

        mockMvc.perform(get("/users/john-doe"))
               .andExpect(status().isOk());
    }

    @Test
    void canCreateUser() throws Exception {
        when(userService.create("john-doe", "lipsum")).thenReturn(
                johnDoe().build()
        );

        mockMvc.perform(post("/users")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(getObjectMapper().writeValueAsString(new CreateUserRequest("john-doe", "lipsum"))))
               .andExpect(status().isOk());
    }

    @Test
    void canUpdateUser() throws Exception {
        when(userService.patch(eq("john-doe"), any())).thenReturn(
                johnDoe().enabled(Boolean.FALSE).build()
        );

        mockMvc.perform(patch("/users/{name}", "john-doe")
                                .with(csrf())
                                .contentType("application/json-patch+json")
                                .content(disableUserPatch()))
               .andExpect(status().isOk());
    }

    private String disableUserPatch() {
        return getObjectMapper().createObjectNode()
                                .put("operation", "replace")
                                .put("path", "enabled")
                                .put("value", "false")
                                .toPrettyString();
    }
}
