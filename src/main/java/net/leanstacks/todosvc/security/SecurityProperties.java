package net.leanstacks.todosvc.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("todo.security")
public class SecurityProperties {

  private String userPassword;

  private String adminPassword;

  public String getUserPassword() {
    return userPassword;
  }

  public void setUserPassword(String userPassword) {
    this.userPassword = userPassword;
  }

  public String getAdminPassword() {
    return adminPassword;
  }

  public void setAdminPassword(String adminPassword) {
    this.adminPassword = adminPassword;
  }

}
