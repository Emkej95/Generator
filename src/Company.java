import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

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
        String crsStatus = data.getCrsStatus();

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

        //Select random trade
        /*//*/Select trade = new Select(firefox.findElement(By.xpath("//*[@id=\"context-corporation-trade\"]")));
        /*//*/trade.selectByValue(data.getCompanyTrade());
        //Selected

        firefox.findElement(By.xpath("//*[@id=\"context-person-identityDocument-number\"]")).sendKeys(identity.getIdentity());
        firefox.findElement(By.xpath("/html/body/div[3]/div[3]/div[3]/div[8]/div/div/form/div/div[1]/fieldset/div[17]/fieldset/div[4]/div[2]/img")).click(); //ID Release date
        firefox.findElement(By.xpath("/html/body/div[7]/div/div/select[2]")).click();
        firefox.findElement(By.xpath("/html/body/div[7]/div/div/select[2]/option[100]")).click();
        firefox.findElement(By.xpath("/html/body/div[7]/table/tbody/tr[2]/td[4]/a")).click();

        //Select customer class
        /*//*/Select customerClass = new Select(firefox.findElement(By.xpath("//*[@id=\"context-corporation-customer_class\"]")));
        /*//*/customerClass.selectByValue(data.getCustomerClass());
        //Selected

        firefox.findElement(By.xpath("//*[@id=\"context-person-email\"]")).sendKeys("mkrzyzak3@ideabank.pl");
        firefox.findElement(By.xpath("//*[@id=\"context-person-phone\"]")).sendKeys("515045208");

        //Select random PKD
        /*//*/Select pkd = new Select(firefox.findElement(By.xpath("//*[@id=\"context-corporation-pkd\"]")));
        /*//*/pkd.selectByValue(data.getCompanyPkd());
        //Selected

        //Select legal status
        /*//*/Select legalStatus = new Select(firefox.findElement(By.xpath("//*[@id=\"context-corporation-legal_status\"]")));
        /*//*/legalStatus.selectByValue("1");
        //Selected

        //Select residence country as Poland
        /*//*/Select residenceCountry = new Select(firefox.findElement(By.id("context-person-residence-country-residence_country")));
        /*//*/residenceCountry.selectByValue("161");
        //Selected

        //Select not PEP
        /*//*/Select pep = new Select(firefox.findElement(By.xpath("//*[@id=\"context-person-pep-pep\"]")));
        /*//*/pep.selectByValue("2");
        /*//*/firefox.findElement(By.xpath("/html/body/div[3]/div[3]/div[3]/div[8]/div/div/form/div/div[1]/fieldset/div[21]/fieldset/div[3]/div[2]/img")).click();
        /*//*/firefox.findElement(By.xpath("/html/body/div[7]/div/div/select[2]")).click();
        /*//*/firefox.findElement(By.xpath("/html/body/div[7]/div/div/select[2]/option[100]")).click();
        /*//*/firefox.findElement(By.xpath("/html/body/div[7]/table/tbody/tr[2]/td[4]/a")).click();
        //Selected

        //Select company residence country as Poland
        /*//*/Select residenceCountryCompany = new Select(firefox.findElement(By.xpath("//*[@id=\"context-corporation-residence-country-residence_country\"]")));
        /*//*/residenceCountryCompany.selectByValue("161");
        //Selected

        //Select second country residency to none
        /*//*/Select secondCountryResidency = new Select(firefox.findElement(By.xpath("//*[@id=\"context-corporation-residence-crs-resident_in_second_country\"]")));
        /*//*/secondCountryResidency.selectByValue("2");
        //Selected

        firefox.findElement(By.xpath("//*[@id=\"context-person-address-street\"]")).sendKeys(data.getStreetName());
        firefox.findElement(By.xpath("//*[@id=\"context-person-address-house_number\"]")).sendKeys(data.getRandomNumber());
        firefox.findElement(By.xpath("//*[@id=\"context-person-address-town\"]")).sendKeys("Warszawa");
        firefox.findElement(By.xpath("//*[@id=\"context-person-address-postal_code\"]")).sendKeys("01-234");
        firefox.findElement(By.xpath("//*[@id=\"context-person-address-post\"]")).sendKeys("Warszawa");
        firefox.findElement(By.xpath("//*[@id=\"addressCopyToCorrespondence\"]")).click();
        firefox.findElement(By.xpath("//*[@id=\"addressCopyToFirm\"]")).click();
        firefox.findElement(By.xpath("//*[@id=\"addressCopyToCompanyCorrespondence\"]")).click();

        //Select CRS Status
        /*//*/firefox.findElement(By.xpath("//*[@id=\"context-corporation-residence-crs-status-crs_residence_status\"]")).click();
        /*//*/Select crsStatusDropdown = new Select(firefox.findElement(By.xpath("//*[@id=\"context-corporation-residence-crs-status-crs_residence_status\"]")));
        /*//*/crsStatusDropdown.selectByValue(crsStatus);
        /*//*/if (crsStatus.equals("3")){
        /*//*/      firefox.findElement(By.xpath("//*[@id=\"context-corporation-residence-crs-status-name_securities_market\"]")).sendKeys("GPW");
        /*//*/      firefox.findElement(By.xpath("//*[@id=\"context-corporation-residence-crs-status-entity_related\"]")).sendKeys("GPW");
        /*//*/}
        //Selected

        //Select FATCA status
        /*//*/Select fatcaStatus = new Select(firefox.findElement(By.xpath("//*[@id=\"context-corporation-residence-fatca-status-fatca_corpo_status\"]")));
        /*//*/fatcaStatus.selectByValue("1");
        /*//*/firefox.findElement(By.xpath("/html/body/div[3]/div[3]/div[3]/div[8]/div/div/form/div/div[2]/fieldset/div[22]/fieldset/fieldset[3]/div[3]/div[2]/img")).click();
        /*//*/firefox.findElement(By.xpath("/html/body/div[7]/div/div/select[2]")).click();
        /*//*/firefox.findElement(By.xpath("/html/body/div[7]/div/div/select[2]/option[100]")).click();
        /*//*/firefox.findElement(By.xpath("/html/body/div[7]/table/tbody/tr[2]/td[4]/a")).click();
        //Selected

        //Select Account Purpose and Deposited Values
        /*//*/Select accountPurpose = new Select(firefox.findElement(By.xpath("//*[@id=\"context-person-other-account_purpose\"]")));
        /*//*/Select depositedValues = new Select(firefox.findElement(By.xpath("//*[@id=\"context-person-other-level_deposited_values\"]")));
        /*//*/accountPurpose.selectByValue(data.getAccountPurpose());
        /*//*/depositedValues.selectByValue(data.getDepositedValues());
        //Selected

        firefox.findElement(By.xpath("//*[@id=\"context-person-personAgreements-agreements_set_all\"]")).click();
        firefox.findElement(By.xpath("//*[@id=\"context-person-personAgreements-copyAgreementsFromClient\"]")).click();

        for (int i = 0; i<2; i++){
            firefox.findElement(By.xpath("//*[@id=\"corporation-copyDataFromClient\"]")).click();
        }

        for (int i = 0; i<2; i++){
            firefox.findElement(By.xpath("//*[@id=\"person-copyDataFromClient\"]")).click();
        }

        firefox.findElement(By.xpath("/html/body/div[3]/div[3]/div[3]/div[8]/div/div/form/div/div[3]/input")).click();
        Thread.sleep(5000);
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