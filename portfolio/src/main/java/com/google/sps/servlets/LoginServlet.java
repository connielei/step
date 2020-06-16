// Copyright 2020 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.sps.GetNickname;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

  /** 
   * Endpoint will return JSON containing a url, login status, text to display and 
   * if the user is logged in, their nickname
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("application/json");
    String urlToRedirectTo = "/";

    UserService userService = UserServiceFactory.getUserService();
    if (userService.isUserLoggedIn()) {
      String id = userService.getCurrentUser().getUserId();
      String nickname = GetNickname.getUserNickname(id);
      String logoutUrl = userService.createLogoutURL(urlToRedirectTo);
    
      String json = generateLogoutJson(logoutUrl, nickname);

      response.getWriter().println(json);
      return;
    } 

    String loginUrl = userService.createLoginURL(urlToRedirectTo);
    String json = generateLoginJson(loginUrl);
    response.getWriter().println(json);
  }

  /**
   * Returns a JSON formatted {"url": url, "loggedIn": false, "nickname":"", 
   * "displayText": "Log in before posting a comment."} for login and url is
   * the parameter passed in.
   */
  private String generateLoginJson(String loginUrl){
    return generateJson(loginUrl, "", "false", "Log in before posting a comment.");
  }

  /**
   * Returns a JSON formatted string {"url": url, "loggedIn": true, "nickname": nn, 
   * "displayText":"Logout"} for logout and url and nn are parameters passed in.
   */
  private String generateLogoutJson(String logoutUrl, String nickname){
    return generateJson(logoutUrl, nickname, "true", "Log out.");
  }

  /** Returns a JSON with parameters filled in */
  private String generateJson(String url, String nickname, String status, String displayText) {
    return "{\"url\": \"" + url + "\", \"loggedIn\":" + status + ",\"nickname\":\"" + 
    nickname + "\", \"displayText\": \"" + displayText + "\"}";
  }
}
