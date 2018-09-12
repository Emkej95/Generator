import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Random;

class Company {
    void run() throws InterruptedException {
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setCapability("marionette", true);

        /*/~~~~CREATE CLASS INSTANCES AND VARIABLES~~~~/*/
        Data data = new Data();
        Pesel pesel = new Pesel();
        Identity identity = new Identity();
        Nip nip = new Nip();
        Regon regon = new Regon();

        /*/~~~~~OPEN WEB BROWSER~~~~~/*/
        FirefoxDriver firefox = new FirefoxDriver();
        firefox.get("http://cc.vm-rc-ecrm-front.ib/login");
        firefox.manage().window().maximize();

        /*/~~~~LOGIN~~~~/*/
        firefox.findElement(By.xpath("//*[@id=\"domain\"]")).sendKeys(Keys.TAB, "mkrzyzak3", Keys.TAB, "h2Ypqsop", Keys.ENTER);

        /*/~~~~ENTER CLIENT CREATOR~~~~/*/
        WebDriverWait wait = new WebDriverWait(firefox, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[3]/div[1]/div/ul[1]/li[1]/a")));
        firefox.findElement(By.xpath("/html/body/div[3]/div[1]/div/ul[1]/li[1]/a")).click();
        firefox.findElement(By.xpath("/html/body/div[3]/div[3]/div[3]/div[1]/ul/li[1]/a")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"context-company-nip\"]")));
        firefox.findElement(By.xpath("/html/body/div[3]/div[3]/div[3]/fieldset/div/div/label[3]/input")).click();
        wait.until(ExpectedConditions.urlToBe("http://cc.vm-rc-ecrm-front.ib/client/add/corporation"));

        /*/~~~~CREATE COMPANY~~~~/*/
        firefox.findElement(By.xpath("//*[@id=\"context-corporation-nip\"]")).sendKeys(nip.getNip());
        firefox.findElement(By.xpath("//*[@id=\"context-corporation-reg_pes\"]")).sendKeys(regon.getRegon());
        firefox.findElement(By.xpath("//*[@id=\"context-corporation-name\"]")).sendKeys(data.getCompanyName());
        firefox.findElement(By.xpath("//*[@id=\"context-corporation-email\"]")).sendKeys("mkrzyzak3@ideabank.pl");
        firefox.findElement(By.xpath("//*[@id=\"context-corporation-phone\"]")).sendKeys("515045208");
        firefox.findElement(By.xpath("/html/body/div[3]/div[3]/div[3]/div[8]/div/div/form/div/div[2]/fieldset/div[7]/div[2]/img")).click();
        firefox.findElement(By.xpath("/html/body/div[7]/div/div/select[2]")).click();
        firefox.findElement(By.xpath("/html/body/div[7]/div/div/select[2]/option[100]")).click();
        firefox.findElement(By.xpath("/html/body/div[7]/table/tbody/tr[2]/td[4]/a")).click();

        firefox.findElement(By.xpath("//*[@id=\"context-person-forename\"]")).sendKeys(data.getName());
        firefox.findElement(By.xpath("//*[@id=\"context-person-mother_maiden_surname\"]")).sendKeys(data.getLastName());
        firefox.findElement(By.xpath("//*[@id=\"context-person-surname\"]")).sendKeys(data.getLastName());
        firefox.findElement(By.xpath("//*[@id=\"context-person-reg_pes\"]")).sendKeys(pesel.getPesel());
        firefox.findElement(By.xpath("//*[@id=\"context-person-birth_place\"]")).sendKeys(data.getCityName());
        firefox.findElement(By.xpath("//*[@id=\"context-person-email\"]")).sendKeys("mkrzyzak3@ideabank.pl");
        firefox.findElement(By.xpath("//*[@id=\"context-person-phone\"]")).sendKeys("515045208");

        //Select random PKD
        Random randPkd = new Random();
        int pkd1 = randPkd.nextInt(654)+1;
        Select pkd = new Select(firefox.findElement(By.xpath("//*[@id=\"context-corporation-pkd\"]")));
        pkd.selectByValue(String.valueOf(pkd1));
    }
}
