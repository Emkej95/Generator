import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static java.lang.Thread.sleep;

class Natural {

    void run() throws InterruptedException {
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setCapability("marionette", true);

        /*/~~~~CREATE CLASS INSTANCES AND VARIABLES~~~~/*/
        Data data = new Data();
        Pesel pesel = new Pesel();
        Identity identity = new Identity();

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

        /*/~~~~CREATE NATURAL PERSON~~~~/*/
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"context-company-nip\"]")));
        firefox.findElement(By.xpath("/html/body/div[3]/div[3]/div[3]/fieldset/div/div/label[1]/input")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id=\"context-company-nip\"]")));
        firefox.findElement(By.xpath("//*[@id=\"context-person-forename\"]")).sendKeys(data.getName());
        firefox.findElement(By.xpath("//*[@id=\"context-person-mother_maiden_surname\"]")).sendKeys(data.getLastName());
        firefox.findElement(By.xpath("//*[@id=\"context-person-surname\"]")).sendKeys(data.getLastName());
        firefox.findElement(By.xpath("//*[@id=\"context-person-reg_pes\"]")).sendKeys(pesel.getPesel());
        firefox.findElement(By.xpath("//*[@id=\"context-person-birth_place\"]")).sendKeys(data.getCityName());
        firefox.findElement(By.xpath("//*[@id=\"context-person-email\"]")).sendKeys("mkrzyzak3@ideabank.pl");
        firefox.findElement(By.xpath("//*[@id=\"context-person-phone\"]")).sendKeys("515045208");
        firefox.findElement(By.xpath("//*[@id=\"context-person-identityDocument-number\"]")).sendKeys(identity.getIdentity());
        firefox.findElement(By.xpath("/html/body/div[3]/div[3]/div[3]/div[8]/div/div[3]/form/div[2]/div[1]/fieldset/div[17]/fieldset/div[4]/div[2]/img")).click(); //ID Release date
        firefox.findElement(By.xpath("/html/body/div[7]/div/div/select[2]")).click();
        firefox.findElement(By.xpath("/html/body/div[7]/div/div/select[2]/option[100]")).click();
        firefox.findElement(By.xpath("/html/body/div[7]/table/tbody/tr[2]/td[4]/a")).click();
        firefox.findElement(By.xpath("//*[@id=\"context-person-residence-taxes_in_poland\"]")).click(); //Residency
        firefox.findElement(By.xpath("/html/body/div[3]/div[3]/div[3]/div[8]/div/div[3]/form/div[2]/div[1]/fieldset/div[20]/fieldset/div/div[2]/select/option[2]")).click();
        firefox.findElement(By.xpath("//*[@id=\"context-person-pep-pep\"]")).click(); //PEP
        firefox.findElement(By.xpath("/html/body/div[3]/div[3]/div[3]/div[8]/div/div[3]/form/div[2]/div[1]/fieldset/div[21]/fieldset/div[1]/div[2]/select/option[3]")).click();
        firefox.findElement(By.xpath("/html/body/div[3]/div[3]/div[3]/div[8]/div/div[3]/form/div[2]/div[1]/fieldset/div[21]/fieldset/div[3]/div[2]/img")).click(); //PEP date
        firefox.findElement(By.xpath("/html/body/div[7]/div/div/select[2]")).click();
        firefox.findElement(By.xpath("/html/body/div[7]/div/div/select[2]/option[100]")).click();
        firefox.findElement(By.xpath("/html/body/div[7]/table/tbody/tr[2]/td[4]/a")).click();
        firefox.findElement(By.xpath("//*[@id=\"context-person-address-street\"]")).sendKeys(data.getStreetName());
        firefox.findElement(By.xpath("//*[@id=\"context-person-address-house_number\"]")).sendKeys(data.getRandomNumber());
        firefox.findElement(By.xpath("//*[@id=\"context-person-address-town\"]")).sendKeys("Warszawa");
        firefox.findElement(By.xpath("//*[@id=\"context-person-address-postal_code\"]")).sendKeys("01-234");
        firefox.findElement(By.xpath("//*[@id=\"context-person-address-post\"]")).sendKeys("Warszawa");
        firefox.findElement(By.xpath("//*[@id=\"addressCopyToCorrespondence\"]")).click();
        firefox.findElement(By.xpath("//*[@id=\"context-person-other-account_purpose\"]")).click();
        firefox.findElement(By.xpath(data.getAccountPurpose())).click();
        firefox.findElement(By.xpath("//*[@id=\"context-person-other-level_deposited_values\"]")).click();
        firefox.findElement(By.xpath(data.getDepositedValues())).click();
        firefox.findElement(By.xpath("//*[@id=\"context-person-personAgreements-agreements_set_all\"]")).click();
        firefox.findElement(By.xpath("//*[@id=\"person-copyDataFromClient\"]")).click();
        firefox.findElement(By.xpath("//*[@id=\"person-copyDataFromClient\"]")).click();

        firefox.findElement(By.xpath("//*[@id=\"form_edit_person\"]")).click();
        sleep(5000);
        firefox.findElement(By.xpath("//*[@id=\"submit\"]")).click();

        if (firefox.findElement(By.xpath("/html/body/div[3]/div[3]/div[2]")).isDisplayed()) {
            firefox.findElement(By.xpath("//*[@id=\"submit\"]")).click();
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[3]/div[3]/div[3]/div[5]/div[1]/div/div/table/tbody/tr/td[12]/a[1]")));
        firefox.findElement(By.xpath("/html/body/div[3]/div[3]/div[3]/div[5]/div[1]/div/div/table/tbody/tr/td[12]/a[1]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"crmmenu-createindef\"]")));
        firefox.findElement(By.xpath("//*[@id=\"crmmenu-createindef\"]")).click();
    }
}
