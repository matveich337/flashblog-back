package com.flasblog.back.web.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flasblog.back.model.JwtTypeModel;
import com.flasblog.back.model.UserModel;
import com.flasblog.back.service.interfaces.TokenInterface;
import com.flasblog.back.service.interfaces.UserInterface;
import com.flasblog.back.web.dto.AccountChangePasswordRequestDto;
import com.flasblog.back.web.dto.AuthorizationTokenResponseDto;
import com.flasblog.back.web.dto.UserDataRequestDto;
import com.flasblog.back.web.dto.UserResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = UserController.class)
public class ControllerTest {
  private final ObjectMapper objectMapper = new ObjectMapper();
  @MockBean private UserInterface userInterface;
  @MockBean private TokenInterface tokenInterface;
  @Autowired private MockMvc mockMvc;

  @Test
  void getUserTest() throws Exception {
    Integer id = 1;
    UserModel userModel = UserModel.builder().id(id).build();
    when(userInterface.getAuthenticatedUser()).thenReturn(userModel);
    MvcResult res = mockMvc.perform(get("/api/user")).andExpect(status().isOk()).andReturn();
    UserResponseDto resultDto =
        objectMapper.readValue(res.getResponse().getContentAsString(), UserResponseDto.class);
    assertEquals(resultDto, new UserResponseDto());
  }

  @Test
  void changeUserDataTest() throws Exception {
    String jwt = "jwt";
    when(userInterface.updateUser(any())).thenReturn(new UserModel());
    when(tokenInterface.createJwt("test@test", JwtTypeModel.AUTHORIZATION)).thenReturn(jwt);
    UserDataRequestDto request = UserDataRequestDto.builder().email("test@test").build();
    MvcResult result =
        mockMvc
            .perform(
                patch("/api/user/data")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andReturn();
    AuthorizationTokenResponseDto resultDto =
        objectMapper.readValue(
            result.getResponse().getContentAsString(), AuthorizationTokenResponseDto.class);
    AuthorizationTokenResponseDto expectedResult =
        AuthorizationTokenResponseDto.builder().authorizationToken(jwt).build();
    assertEquals(resultDto, expectedResult);
  }

  @Test
  void changeUserPasswordTest() throws Exception {
    Integer id = 1;
    String newPass = "new";
    String email = "test@test";
    UserModel mockUserModel = UserModel.builder().id(id).email(email).build();
    when(userInterface.getAuthenticatedUser()).thenReturn(mockUserModel);
    doNothing().when(userInterface).changeUserPasswordByEmail(email, newPass);
    AccountChangePasswordRequestDto requestDto =
        AccountChangePasswordRequestDto.builder().newPassword(newPass).build();
    MvcResult result =
        mockMvc
            .perform(
                patch("/api/user/password")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isOk())
            .andReturn();
  }
}
