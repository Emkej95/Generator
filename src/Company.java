import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.*;

import static java.lang.Thread.sleep;

class Company {

    private Connection getConnection(){
        Connection connect = null;

        String dbUser = "postgres";
        String dbUrl = "jdbc:postgresql://localhost:5432/postgres";
        String dbPassword = "Start1234!";

        try {
            connect = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return connect;
    }

    private void insert(String contextid, String idnumber, String clientname, String clientlastname, String clientpesel, String clientregonnumber, String clientnipnumber, String phonepassword, String frontlogin, String frontpassword){
        String sql = "INSERT INTO clients (clienttype, contextid, idnumber, clientname, clientlastname, clientpesel, clientregonnumber, clientnipnumber, phonepassword, frontlogin, frontpassword) VALUES(?,?,?,?,?,?,?,?,?,?,?)";

        try (Connection connect = this.getConnection();
             PreparedStatement pstmt = connect.prepareStatement(sql)) {
                pstmt.setString(1, "Company");
                pstmt.setString(2, contextid);
                pstmt.setString(3, idnumber);
                pstmt.setString(4, clientname);
                pstmt.setString(5, clientlastname);
                pstmt.setString(6, clientpesel);
                pstmt.setString(7, clientregonnumber);
                pstmt.setString(8, clientnipnumber);
                pstmt.setString(9, phonepassword);
                pstmt.setString(10, frontlogin);
                pstmt.setString(11, frontpassword);
                pstmt.executeUpdate();
            } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void run() throws InterruptedException {
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setCapability("marionette", true);

        /*/~~~~CREATE CLASS INSTANCES AND VARIABLES~~~~/*/
        Company company = new Company();
        Data data = new Data();
        Pesel pesel = new Pesel();
        Identity identity = new Identity();
        Nip nip = new Nip();
        Regon regon = new Regon();

        /*/~~~~~VARIABLES VALUES FROM METHODS~~~~~/*/
        company.getConnection();
        String nipNumber = nip.getNip();
        String regonNumber = regon.getRegon();
        String companyName = data.getCompanyName();
        String personName = data.getName();
        String personLastName = data.getLastName();
        String peselNumber = pesel.getPesel();
        String cityName = data.getCityName();
        String idNumber = identity.getIdentity();
        String crsStatus = data.getCrsStatus();

        /*/~~~~~OPEN WEB BROWSER~~~~~/*/
        System.out.println("Opening browser...");
        FirefoxDriver firefox = new FirefoxDriver();
        firefox.get("http://cc.vm-rc-ecrm-front.ib/login");
        firefox.manage().window().maximize();
        System.out.println("~~~~OPENED~~~~");

        /*/~~~~LOGIN~~~~/*/
        System.out.println("Logging in...");
        firefox.findElement(By.xpath("//*[@id=\"domain\"]")).sendKeys(Keys.TAB, "mkrzyzak3", Keys.TAB, "h2Ypqsop", Keys.ENTER);
        System.out.println("~~~~LOGGED IN~~~~");

        /*/~~~~ENTER CLIENT CREATOR~~~~/*/
        System.out.println("Entering company creator...");
        WebDriverWait wait = new WebDriverWait(firefox, 15);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[3]/div[1]/div/ul[1]/li[1]/a")));
        firefox.findElement(By.xpath("/html/body/div[3]/div[1]/div/ul[1]/li[1]/a")).click();
        firefox.findElement(By.xpath("/html/body/div[3]/div[3]/div[3]/div[1]/ul/li[1]/a")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"context-company-nip\"]")));
        firefox.findElement(By.xpath("/html/body/div[3]/div[3]/div[3]/fieldset/div/div/label[3]/input")).click();
        wait.until(ExpectedConditions.urlToBe("http://cc.vm-rc-ecrm-front.ib/client/add/corporation"));
        System.out.println("~~~~ENTERED~~~~");

        /*/~~~~CREATE COMPANY~~~~/*/
        firefox.findElement(By.xpath("//*[@id=\"context-corporation-nip\"]")).sendKeys(nipNumber);
        System.out.println("NIP entered: " + nipNumber);
        firefox.findElement(By.xpath("//*[@id=\"context-corporation-reg_pes\"]")).sendKeys(regonNumber);
        System.out.println("REGON entered: " + regonNumber);
        firefox.findElement(By.xpath("//*[@id=\"context-corporation-name\"]")).sendKeys(companyName);
        System.out.println("Company name entered: " + companyName);
        firefox.findElement(By.xpath("//*[@id=\"context-corporation-email\"]")).sendKeys("mkrzyzak3@ideabank.pl");
        firefox.findElement(By.xpath("//*[@id=\"context-corporation-phone\"]")).sendKeys("515045208");
        System.out.println("Company contact info filled...");
        firefox.findElement(By.xpath("/html/body/div[3]/div[3]/div[3]/div[8]/div/div/form/div/div[2]/fieldset/div[7]/div[2]/img")).click();
        firefox.findElement(By.xpath("/html/body/div[7]/div/div/select[2]")).click();
        firefox.findElement(By.xpath("/html/body/div[7]/div/div/select[2]/option[100]")).click();
        firefox.findElement(By.xpath("/html/body/div[7]/table/tbody/tr[2]/td[4]/a")).click();
        System.out.println("Company create date entered...");

        firefox.findElement(By.xpath("//*[@id=\"context-person-forename\"]")).sendKeys(personName);
        System.out.println("Person name entered: " + personName);
        firefox.findElement(By.xpath("//*[@id=\"context-person-mother_maiden_surname\"]")).sendKeys(data.getLastName());
        firefox.findElement(By.xpath("//*[@id=\"context-person-surname\"]")).sendKeys(personLastName);
        System.out.println("Person last name entered: " + personLastName);
        firefox.findElement(By.xpath("//*[@id=\"context-person-reg_pes\"]")).sendKeys(peselNumber);
        System.out.println("Person PESEL entered: " + peselNumber);
        firefox.findElement(By.xpath("//*[@id=\"context-person-birth_place\"]")).sendKeys(cityName);
        System.out.println("Person born in: " + cityName);

        //Select random trade
        /*//*/System.out.println("Selecting company trade...");
        /*//*/Select trade = new Select(firefox.findElement(By.xpath("//*[@id=\"context-corporation-trade\"]")));
        /*//*/trade.selectByValue(data.getCompanyTrade());
        /*//*/System.out.println("~~~~SELECTED~~~~");
        //Selected

        System.out.println("Generating ID number and selecting date...");
        firefox.findElement(By.xpath("//*[@id=\"context-person-identityDocument-number\"]")).sendKeys(idNumber);
        firefox.findElement(By.xpath("/html/body/div[3]/div[3]/div[3]/div[8]/div/div/form/div/div[1]/fieldset/div[17]/fieldset/div[4]/div[2]/img")).click(); //ID Release date
        firefox.findElement(By.xpath("/html/body/div[7]/div/div/select[2]")).click();
        firefox.findElement(By.xpath("/html/body/div[7]/div/div/select[2]/option[100]")).click();
        firefox.findElement(By.xpath("/html/body/div[7]/table/tbody/tr[2]/td[4]/a")).click();
        System.out.println("ID Number generated: " + idNumber);
        System.out.println("~~~~ID SELECTED~~~~");

        //Select customer class
        /*//*/System.out.println("Selecting customer class...");
        /*//*/Select customerClass = new Select(firefox.findElement(By.xpath("//*[@id=\"context-corporation-customer_class\"]")));
        /*//*/customerClass.selectByValue(data.getCustomerClass());
        /*//*/System.out.println("~~~~SELECTED~~~~");
        //Selected

        firefox.findElement(By.xpath("//*[@id=\"context-person-email\"]")).sendKeys("mkrzyzak3@ideabank.pl");
        firefox.findElement(By.xpath("//*[@id=\"context-person-phone\"]")).sendKeys("515045208");
        System.out.println("Personal contact info filled...");

        //Select random PKD
        /*//*/System.out.println("Selecting company PKD...");
        /*//*/Select pkd = new Select(firefox.findElement(By.xpath("//*[@id=\"context-corporation-pkd\"]")));
        /*//*/pkd.selectByValue(data.getCompanyPkd());
        /*//*/System.out.println("~~~~SELECTED~~~~");
        //Selected

        //Select legal status
        /*//*/System.out.println("Selecting legal status...");
        /*//*/Select legalStatus = new Select(firefox.findElement(By.xpath("//*[@id=\"context-corporation-legal_status\"]")));
        /*//*/legalStatus.selectByValue("1");
        /*//*/System.out.println("~~~~SELECTED~~~~");
        //Selected

        //Select Poland as residence country
        /*//*/System.out.println("Selecting Poland as residence country...");
        /*//*/Select residenceCountry = new Select(firefox.findElement(By.id("context-person-residence-country-residence_country")));
        /*//*/residenceCountry.selectByValue("161");
        /*//*/System.out.println("~~~~SELECTED~~~~");
        //Selected

        //Select not PEP
        /*//*/System.out.println("Selecting PEP and date...");
        /*//*/Select pep = new Select(firefox.findElement(By.xpath("//*[@id=\"context-person-pep-pep\"]")));
        /*//*/pep.selectByValue("2");
        /*//*/firefox.findElement(By.xpath("/html/body/div[3]/div[3]/div[3]/div[8]/div/div/form/div/div[1]/fieldset/div[21]/fieldset/div[3]/div[2]/img")).click();
        /*//*/firefox.findElement(By.xpath("/html/body/div[7]/div/div/select[2]")).click();
        /*//*/firefox.findElement(By.xpath("/html/body/div[7]/div/div/select[2]/option[100]")).click();
        /*//*/firefox.findElement(By.xpath("/html/body/div[7]/table/tbody/tr[2]/td[4]/a")).click();
        /*//*/System.out.println("~~~~SELECTED~~~~");
        //Selected

        //Select company residence country as Poland
        /*//*/System.out.println("Selecting Poland as company residence country...");
        /*//*/Select residenceCountryCompany = new Select(firefox.findElement(By.xpath("//*[@id=\"context-corporation-residence-country-residence_country\"]")));
        /*//*/residenceCountryCompany.selectByValue("161");
        /*//*/System.out.println("~~~~SELECTED~~~~");
        //Selected

        //Select second country residency to none
        /*//*/System.out.println("Selecting second residency country to none...");
        /*//*/Select secondCountryResidency = new Select(firefox.findElement(By.xpath("//*[@id=\"context-corporation-residence-crs-resident_in_second_country\"]")));
        /*//*/secondCountryResidency.selectByValue("2");
        /*//*/System.out.println("~~~~SELECTED~~~~");
        //Selected

        firefox.findElement(By.xpath("//*[@id=\"context-person-address-street\"]")).sendKeys(data.getStreetName());
        System.out.println("Selected street name...");
        firefox.findElement(By.xpath("//*[@id=\"context-person-address-house_number\"]")).sendKeys(data.getRandomNumber());
        System.out.println("Generated random house number...");
        firefox.findElement(By.xpath("//*[@id=\"context-person-address-town\"]")).sendKeys("Warszawa");
        firefox.findElement(By.xpath("//*[@id=\"context-person-address-postal_code\"]")).sendKeys("01-234");
        firefox.findElement(By.xpath("//*[@id=\"context-person-address-post\"]")).sendKeys("Warszawa");
        firefox.findElement(By.xpath("//*[@id=\"addressCopyToCorrespondence\"]")).click();
        firefox.findElement(By.xpath("//*[@id=\"addressCopyToFirm\"]")).click();
        firefox.findElement(By.xpath("//*[@id=\"addressCopyToCompanyCorrespondence\"]")).click();
        System.out.println("Address information filled...");

        //Select CRS Status
        /*//*/System.out.println("Selecting CRS status...");
        /*//*/firefox.findElement(By.xpath("//*[@id=\"context-corporation-residence-crs-status-crs_residence_status\"]")).click();
        /*//*/Select crsStatusDropdown = new Select(firefox.findElement(By.xpath("//*[@id=\"context-corporation-residence-crs-status-crs_residence_status\"]")));
        /*//*/crsStatusDropdown.selectByValue(crsStatus);
        /*//*/if (crsStatus.equals("3")) {
        /*//*/firefox.findElement(By.xpath("//*[@id=\"context-corporation-residence-crs-status-name_securities_market\"]")).sendKeys("GPW");
        /*//*/firefox.findElement(By.xpath("//*[@id=\"context-corporation-residence-crs-status-entity_related\"]")).sendKeys("GPW");
        /*//*/}
        /*//*/System.out.println("~~~~SELECTED~~~~");
        //Selected

        //Select FATCA status
        /*//*/System.out.println("Selecting FATCA status...");
        /*//*/Select fatcaStatus = new Select(firefox.findElement(By.xpath("//*[@id=\"context-corporation-residence-fatca-status-fatca_corpo_status\"]")));
        /*//*/fatcaStatus.selectByValue("1");
        /*//*/firefox.findElement(By.xpath("/html/body/div[3]/div[3]/div[3]/div[8]/div/div/form/div/div[2]/fieldset/div[22]/fieldset/fieldset[3]/div[3]/div[2]/img")).click();
        /*//*/firefox.findElement(By.xpath("/html/body/div[7]/div/div/select[2]")).click();
        /*//*/firefox.findElement(By.xpath("/html/body/div[7]/div/div/select[2]/option[100]")).click();
        /*//*/firefox.findElement(By.xpath("/html/body/div[7]/table/tbody/tr[2]/td[4]/a")).click();
        /*//*/System.out.println("~~~~SELECTED~~~~");
        //Selected

        //Select Account Purpose and Deposited Values
        /*//*/System.out.println("Selecting Account purpose and Deposited values...");
        /*//*/Select accountPurpose = new Select(firefox.findElement(By.xpath("//*[@id=\"context-person-other-account_purpose\"]")));
        /*//*/Select depositedValues = new Select(firefox.findElement(By.xpath("//*[@id=\"context-person-other-level_deposited_values\"]")));
        /*//*/accountPurpose.selectByValue(data.getAccountPurpose());
        /*//*/depositedValues.selectByValue(data.getDepositedValues());
        /*//*/System.out.println("~~~~SELECTED~~~~");
        //Selected

        firefox.findElement(By.xpath("//*[@id=\"context-person-personAgreements-agreements_set_all\"]")).click();
        firefox.findElement(By.xpath("//*[@id=\"context-person-personAgreements-copyAgreementsFromClient\"]")).click();
        System.out.println("Agreements selected...");

        for (int i = 0; i < 2; i++) {
            firefox.findElement(By.xpath("//*[@id=\"corporation-copyDataFromClient\"]")).click();
        }

        for (int i = 0; i < 2; i++) {
            firefox.findElement(By.xpath("//*[@id=\"person-copyDataFromClient\"]")).click();
        }
        System.out.println("Beneficiary updated...");

        firefox.findElement(By.xpath("/html/body/div[3]/div[3]/div[3]/div[8]/div/div/form/div/div[3]/input")).click();
        sleep(20000);
        firefox.findElement(By.xpath("//*[@id=\"submit\"]")).click();

        String context = firefox.findElement(By.xpath("/html/body/div[3]/div[3]/div[3]/div[5]/div[1]/div/div/table/tbody/tr/td[2]")).getText();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[3]/div[3]/div[3]/div[5]/div[1]/div/div/table/tbody/tr/td[12]/a[1]")));
        firefox.findElement(By.xpath("/html/body/div[3]/div[3]/div[3]/div[5]/div[1]/div/div/table/tbody/tr/td[12]/a[1]")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"crmmenu-createindef\"]")));
        firefox.findElement(By.xpath("//*[@id=\"crmmenu-createindef\"]")).click();
        System.out.println("Create client in DEF...");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[3]/div[3]/div[2]/ul/li")));
        System.out.println("~~~~CREATED~~~~");

        firefox.navigate().refresh();
        sleep(5000);

        System.out.println("Setting contract status...");
        firefox.findElement(By.xpath("/html/body/div[3]/div[1]/div/ul[2]/li[7]/a")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[3]/div[3]/div[3]/div[5]/div[1]/div/div/table/tbody/tr/td[10]/a[1]")));
        firefox.findElement(By.xpath("/html/body/div[3]/div[3]/div[3]/div[5]/div[1]/div/div/table/tbody/tr/td[10]/a[1]")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"data-status\"]")));
        Select contractStatus = new Select(firefox.findElement(By.xpath("//*[@id=\"data-status\"]")));
        contractStatus.selectByValue("8");
        sleep(500);
        firefox.findElement(By.xpath("/html/body/div[8]/div[2]/div/form/div/div[3]/ul/li[2]/a")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"data-contracts_person-0-add_to_contract\"]")));
        Select frontDecision = new Select(firefox.findElement(By.xpath("//*[@id=\"data-contracts_person-0-add_to_contract\"]")));
        frontDecision.selectByValue("1");
        sleep(500);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"data-documents_person-0-verified\"]")));
        Select verificationStatus = new Select(firefox.findElement(By.xpath("//*[@id=\"data-documents_person-0-verified\"]")));
        verificationStatus.selectByValue("1");
        sleep(500);
        firefox.findElement(By.xpath("/html/body/div[8]/div[2]/div/form/div/div[3]/ul/li[3]/a")).click();
        System.out.println("~~~~CONTRACT SIGNED~~~~");

        System.out.println("Adding Front login to database...");
        firefox.findElement(By.xpath("/html/body/div[3]/div[1]/div/ul[2]/li[5]/a")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Pokaż")));
        firefox.findElement(By.linkText("Pokaż")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[8]/div[2]/table/tbody/tr[5]/td/table[2]/tbody/tr/td/table/tbody/tr/td/table/tbody/tr/td/table/tbody/tr[2]/td/center")));
        String frontLoginMsg = firefox.findElement(By.xpath("/html/body/div[8]/div[2]/table/tbody/tr[5]/td/table[2]/tbody/tr/td/table/tbody/tr/td/table/tbody/tr/td/table/tbody/tr[2]/td/center")).getText();
        String frontlogin = frontLoginMsg.substring(frontLoginMsg.length() - 8);
        System.out.println("~~~~ADDED~~~~");

        System.out.println("Adding phone and front password to database...");
        firefox.findElement(By.xpath("/html/body/div[8]/div[3]/div/button[2]")).click();
        firefox.findElement(By.xpath("/html/body/div[3]/div[1]/div/ul[2]/li[1]/a")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"crmmenu-sendphonepassword\"]")));
        sleep(5500);
        firefox.findElement(By.xpath("//*[@id=\"crmmenu-sendphonepassword\"]")).click();
        System.out.println("SMS password sent...");
        sleep(5500);
        firefox.findElement(By.xpath("//*[@id=\"crmmenu-sendsmswithpassword\"]")).click();
        System.out.println("Front password sent...");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[3]/div[1]/div/ul[2]/li[4]/a")));
        firefox.findElement(By.xpath("/html/body/div[3]/div[1]/div/ul[2]/li[4]/a")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[3]/div[3]/div[3]/div[5]/div/div/table/tbody/tr[2]/td[6]")));

        String text = firefox.findElement(By.xpath("/html/body/div[3]/div[3]/div[3]/div[5]/div/div/table/tbody/tr[2]/td[6]")).getText();
        String phone = text.substring(text.length() - 10);

        String frontText = firefox.findElement(By.xpath("/html/body/div[3]/div[3]/div[3]/div[5]/div/div/table/tbody/tr[1]/td[6]")).getText();
        String frontpassword = frontText.substring(frontText.length() - 10);
        System.out.println("~~~~ADDED~~~~");

        /*/~~~~~INSERT TO DATABASE~~~~~/*/
        company.insert(context,idNumber,personName,personLastName,peselNumber,regonNumber,nipNumber,phone,frontlogin, frontpassword);

        System.out.println("~~~~CLIENT CREATED~~~~" + "\n" + "~~~~CLIENT CREATED IN DEF~~~~" + "\n" + "~~~~CLIENT ADDED TO DATABASE~~~~" + "\n" + "~~~~COMPANY CREATION COMPLETED~~~~");

    }
}