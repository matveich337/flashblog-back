package com.flasblog.back.selenium;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class SelTests {
  private WebDriver driver;

  @BeforeEach
  public void setUp() {
    System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--remote-allow-origins=*");
    driver = new ChromeDriver(options);
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
  }

  @AfterEach
  public void tearDown() {
    driver.quit();
  }

  @Test
  public void registrationTest() throws IOException {
    driver.get("http://localhost:3000/");
    takeScreen("1.png");
    click("register");
    takeScreen("2.png");
    clear("email");
    takeScreen("3.png");
    type("email", "test@test");
    takeScreen("4.png");
    clear("username");
    takeScreen("5.png");
    type("username", "test");
    takeScreen("6.png");
    clear("password");
    takeScreen("7.png");
    type("password", "test");
    takeScreen("8.png");
    driver.findElement(By.cssSelector(".css-164r41r")).click();
    takeScreen("9.png");
    clear("confirmPassword");
    takeScreen("10.png");
    type("confirmPassword", "test");
    takeScreen("11.png");
    click("registerButton");
    takeScreen("12.png");
    driver.close();
  }

  @Test
  public void passwordRestoreTest() {
    driver.get("http://localhost:3000/");
    click("forgotPassword");
    click("email");
    clear("email");
    type("email", "test@test");
    click("submitEmail");
    driver.close();
  }

  @Test
  public void logginingTest() {
    driver.get("http://localhost:3000/");
    login();
    driver.close();
  }

  @Test
  public void myProfileTest() {
    driver.get("http://localhost:3000/");
    getToMyProfile();
    driver.close();
  }

  @Test
  public void myProfileDataChaneTest() {
    driver.get("http://localhost:3000/");
    getToMyProfile();
    click("modifyInfoButton");
    click("modifyDataButton");
    driver.close();
  }

  @Test
  public void myProfilePasswordChangeTest() {
    driver.get("http://localhost:3000/");
    getToMyProfile();
    click("modifyPasswordButton");
    clear("password");
    type("password", "test");
    clear("confirmPassword");
    type("confirmPassword", "test");
    driver.findElement(By.cssSelector(".css-k008qs > #modifyPasswordButton")).click();
    driver.close();
  }

  @Test
  public void createPostTest() {
    driver.get("http://localhost:3000/");
    login();
    click("newPostButton");
    clear("postTheme");
    type("postTheme", "TestTheme");
    clear("postContent");
    type("postContent", "TestContent");
    click("createPostButton");
    driver.close();
  }

  @Test
  public void commentPostTest() {
    driver.get("http://localhost:3000/");
    login();
    click("detailsButton");
    clear("commentary");
    type("commentary", "TestComment");
    click("commentarySubmitButton");
    driver.close();
  }

  @Test
  public void logoutTest() {
    driver.get("http://localhost:3000/");
    login();
    click("header-log-oug-button");
    driver.close();
  }

  private void getToMyProfile() {
    login();
    click("myProfile");
  }

  private void login() {
    clear("email");
    type("email", "test@test");
    clear("password");
    type("password", "test");
    click("loginButton");
  }

  private void click(String id) {
    driver.findElement(findById(id)).click();
  }

  private void clear(String id) {
    driver.findElement(findById(id)).clear();
  }

  private void type(String id, String keys) {
    driver.findElement(findById(id)).sendKeys(keys);
  }

  private By findById(String id) {
    return By.id(id);
  }

  private void takeScreen(String screenName) throws IOException {
    TakesScreenshot screenshot = ((TakesScreenshot) driver);
    File SrcFile = screenshot.getScreenshotAs(OutputType.FILE);
    File DestFile = new File("src/test/java/com/flasblog/back/selenium" + screenName);
    FileUtils.copyFile(SrcFile, DestFile);
  }
}
