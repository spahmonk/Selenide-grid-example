package ui;

import com.codeborne.selenide.*;
import com.codeborne.selenide.webdriver.WebDriverFactory;
import com.sun.glass.ui.View;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.appears;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class AddressBookTest {

  @Before
  public void setUp() throws MalformedURLException {


    String grid="http://localhost:4444//wd/hub";
    DesiredCapabilities cap=new DesiredCapabilities();
    cap.setCapability("ignoreZoomSetting",true);
    cap.setBrowserName("firefox");
    WebDriver driver=new RemoteWebDriver(new URL(grid), cap);
    WebDriverRunner.setWebDriver(driver);


/*
    Configuration.remote="http://localhost:4444//wd/hub";
    Configuration.browser="internet explorer";
    Configuration.timeout=6000;
    clearBrowserCookies();
    Configuration.startMaximized=true;
    DesiredCapabilities cap=new DesiredCapabilities();
    cap.setCapability("ignoreZoomSetting",true);

    */



// for internet explorer need to add -Dcapabilities.ignoreZoomSetting=true in VM option


    open("http://demo.vaadin.com/AddressBook/");

  }

  @Test
  public void showsContacts() {
    SelenideElement table = $(byText("First name")).closest(".v-table");
    table.findAll(".v-table-header .v-table-header-cell").shouldHave(
        texts("First name", "Last name", "Email", "Phone number", "Street Address", "Postal Code", "City")
    );

  //  table.findAll(".v-table-table .v-table-row").shouldHave(size(13));
    
    table.find(".v-table-table .v-table-row", 0).findAll(".v-table-cell-content").shouldHave(
        texts("Lisa", "Schneider", "lisa.schneider@vaadin.com", "+358 02 555 7531", "561-9262 Iaculis Avenue", "69761", "Stockholm")
    );
  }

  @Test
  public void canAddContact() {
    $(byText("Add contact")).click();
    
    SelenideElement form = $(byText("Save")).closest(".v-form");
    form.find(".v-formlayout-row", 0).find("input").val("Sarah");
    form.find(".v-formlayout-row", 1).find("input").val("Connor");
    form.find(".v-formlayout-row", 2).find("input").val("sarah.connor@gmail.com");
    form.find(".v-formlayout-row", 3).find("input").val("0000000");
    form.find(".v-formlayout-row", 4).find("input").val("-");
    form.find(".v-formlayout-row", 5).find("input").val("11111");
    
    form.find(".v-formlayout-row", 6).find("input").sendKeys("Hon");
    $(".popupContent .v-filterselect-suggestmenu").find(byText("Hong Kong")).shouldBe(visible).click();
    
    form.find(byText("Save")).click();
  }


    @After
    public void closeDriver(){
      close();
    }



}
