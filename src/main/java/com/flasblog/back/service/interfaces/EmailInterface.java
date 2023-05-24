package com.flasblog.back.service.interfaces;

import com.flasblog.back.model.UserModel;

/**
 * GmailService.
 */
public interface EmailInterface {
  void sendAccountActivationLink(UserModel userModel);

  void sendAccountForgotPasswordLink(String email);
}
