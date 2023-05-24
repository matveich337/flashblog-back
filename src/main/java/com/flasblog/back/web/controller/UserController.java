package com.flasblog.back.web.controller;

import com.flasblog.back.mapper.Mapper;
import com.flasblog.back.model.JwtTypeModel;
import com.flasblog.back.service.interfaces.TokenInterface;
import com.flasblog.back.service.interfaces.UserInterface;
import com.flasblog.back.web.dto.AccountChangePasswordRequestDto;
import com.flasblog.back.web.dto.AuthorizationTokenResponseDto;
import com.flasblog.back.web.dto.UserDataRequestDto;
import com.flasblog.back.web.dto.UserResponseDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** UserController. */
@AllArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {
  private UserInterface userInterface;
  private TokenInterface tokenInterface;

  /**
   * getUser.
   *
   * @return UserResponse.
   */
  @GetMapping
  public ResponseEntity<UserResponseDto> getUser() {
    return ResponseEntity.ok(Mapper.I.userModelToResponseDto(userInterface.getAuthenticatedUser()));
  }

  /**
   * changeUserData.
   *
   * @param requestDto UserDataRequest.
   * @return AuthorizationTokenResponse.
   */
  @PatchMapping("/data")
  public ResponseEntity<AuthorizationTokenResponseDto> changeUserData(
      @Valid @RequestBody UserDataRequestDto requestDto) {
    userInterface.updateUser(Mapper.I.userDataRequestDtoToUserModel(requestDto));
    return ResponseEntity.ok(
        AuthorizationTokenResponseDto.builder()
            .authorizationToken(
                tokenInterface.createJwt(
                    (requestDto.getEmail() == null)
                        ? userInterface.getAuthenticatedUser().getEmail()
                        : requestDto.getEmail(),
                    JwtTypeModel.AUTHORIZATION))
            .build());
  }

  /**
   * changeUserPassword.
   *
   * @param requestDto AccountChangePasswordRequest.
   * @return HttpStatus.
   */
  @PatchMapping("/password")
  public ResponseEntity<HttpStatus> changeUserPassword(
      @Valid @RequestBody AccountChangePasswordRequestDto requestDto) {
    userInterface.changeUserPasswordByEmail(
        userInterface.getAuthenticatedUser().getEmail(), requestDto.getNewPassword());
    return ResponseEntity.ok().build();
  }
}
