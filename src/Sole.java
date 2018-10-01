import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;

import static java.lang.Thread.sleep;

class Sole {

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
            pstmt.setString(1, "Sole");
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
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE,"/dev/null");

        /*/~~~~CREATE CLASS INSTANCES AND VARIABLES~~~~/*/
        Sole sole = new Sole();
        Data data = new Data();
        Pesel pesel = new Pesel();
        Identity identity = new Identity();
        Nip nip = new Nip();
        Regon regon = new Regon();

        /*/~~~~~VARIABLES VALUES FROM METHODS~~~~~/*/
        sole.getConnection();
        String nipNumber = nip.getNip();
        String regonNumber = regon.getRegon();
        String companyName = data.getCompanyName();
        String personName = data.getName();
        String personLastName = data.getLastName();
        String peselNumber = pesel.getPesel();
        String cityName = data.getCityName();
        String idNumber = identity.getIdentity();

        /*/~~~~~OPEN WEB BROWSER~~~~~/*/
        System.out.println("Choose environment: DEV or RC");
        Scanner input = new Scanner(System.in);
        String environment = input.nextLine();
        System.out.println("Opening browser...");
        FirefoxDriver firefox = new FirefoxDriver();

        switch (environment) {
            case "RC":
            case "rc":
                firefox.get("http://cc.vm-rc-ecrm-front.ib/login");
                firefox.manage().window().maximize();
                System.out.println("~~~~OPENED~~~~");
                break;
            case "dev":
            case "DEV":
            case "test":
            case "TEST":
                firefox.get("http://cc.vm-rc-ecrm-front.ib/login");
                firefox.manage().window().maximize();
                System.out.println("~~~~OPENED~~~~");
                break;
            default:
                System.out.println("Wrong environment! Terminating...");
                Thread.currentThread().interrupt();
                break;
        }

        /*/~~~~LOGIN~~~~/*/
        System.out.println("Logging in...");
        firefox.findElement(By.xpath("//*[@id=\"domain\"]")).sendKeys(Keys.TAB, "mkrzyzak3", Keys.TAB, "h2Ypqsop", Keys.ENTER);
        System.out.println("~~~~LOGGED IN~~~~");

        /*/~~~~ENTER CLIENT CREATOR~~~~/*/
        WebDriverWait wait = new WebDriverWait(firefox, 15);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[3]/div[1]/div/ul[1]/li[1]/a")));
        firefox.findElement(By.xpath("/html/body/div[3]/div[1]/div/ul[1]/li[1]/a")).click();
        firefox.findElement(By.xpath("/html/body/div[3]/div[3]/div[3]/div[1]/ul/li[1]/a")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"context-company-nip\"]")));

        /*/~~~~CREATE SOLE PROPRIETORSHIP~~~~/*/
        firefox.findElement(By.xpath("//*[@id=\"context-company-nip\"]")).sendKeys(nipNumber);
        System.out.println("NIP entered: " + nipNumber);
        firefox.findElement(By.xpath("//*[@id=\"context-company-reg_pes\"]")).sendKeys(regonNumber);
        System.out.println("REGON entered: " + regonNumber);
        firefox.findElement(By.xpath("//*[@id=\"context-company-trade\"]")).sendKeys(String.valueOf(data.getCompanyTrade()));
        firefox.findElement(By.xpath("//*[@id=\"context-company-email\"]")).sendKeys("michal.krzyzak3@ideabank.pl");
        firefox.findElement(By.xpath("//*[@id=\"context-company-phone\"]")).sendKeys("515045208");
        System.out.println("Company contact info filled...");

        //Select random PKD
        Random randPkd = new Random();
        int pkd1 = randPkd.nextInt(654)+1;
        Select pkd = new Select(firefox.findElement(By.xpath("//*[@id=\"context-company-pkd\"]")));
        pkd.selectByValue(String.valueOf(pkd1));

        firefox.findElement(By.xpath("//*[@id=\"context-person-forename\"]")).sendKeys(personName);
        System.out.println("Person name entered: " + personName);
        firefox.findElement(By.xpath("//*[@id=\"context-person-mother_maiden_surname\"]")).sendKeys(data.getLastName());
        firefox.findElement(By.xpath("//*[@id=\"context-person-surname\"]")).sendKeys(personLastName);
        System.out.println("Person mother last name entered: " + personLastName);
        firefox.findElement(By.xpath("//*[@id=\"context-person-reg_pes\"]")).sendKeys(peselNumber);
        System.out.println("Person PESEL entered: " + peselNumber);
        firefox.findElement(By.xpath("//*[@id=\"context-person-birth_place\"]")).sendKeys(cityName);
        System.out.println("Person born in: " + cityName);
        firefox.findElement(By.xpath("//*[@id=\"context-person-email\"]")).sendKeys("mkrzyzak3@ideabank.pl");
        firefox.findElement(By.xpath("//*[@id=\"context-person-phone\"]")).sendKeys("515045208");
        firefox.findElement(By.xpath("//*[@id=\"context-company-name\"]")).sendKeys(companyName);
        System.out.println("Company name entered: " + companyName);

        System.out.println("Selecting Sole Account purpose and Deposited values...");
        Select soleAccountPurpose = new Select(firefox.findElement(By.xpath("//*[@id=\"context-company-account_purpose\"]")));
        soleAccountPurpose.selectByValue(data.getAccountPurpose());

        Select soleDepositedValues = new Select(firefox.findElement(By.xpath("//*[@id=\"context-company-level_deposited_values\"]")));
        soleDepositedValues.selectByValue(data.getDepositedValues());
        System.out.println("~~~~SELECTED~~~~");

        firefox.findElement(By.xpath("/html/body/div[3]/div[3]/div[3]/div[8]/div/form/div/div[2]/fieldset/div[9]/div[2]/img")).click();
        firefox.findElement(By.xpath("/html/body/div[7]/div/div/select[2]")).click();
        firefox.findElement(By.xpath("/html/body/div[7]/div/div/select[2]/option[100]")).click();
        firefox.findElement(By.xpath("/html/body/div[7]/table/tbody/tr[2]/td[4]/a")).click();

        firefox.findElement(By.xpath("//*[@id=\"context-person-identityDocument-number\"]")).sendKeys(idNumber);
        System.out.println("ID Number generated: " + idNumber);
        firefox.findElement(By.xpath("/html/body/div[3]/div[3]/div[3]/div[8]/div/form/div/div[1]/fieldset/div[17]/fieldset/div[4]/div[2]/img")).click(); //ID Release date
        firefox.findElement(By.xpath("/html/body/div[7]/div/div/select[2]")).click();
        firefox.findElement(By.xpath("/html/body/div[7]/div/div/select[2]/option[100]")).click();
        firefox.findElement(By.xpath("/html/body/div[7]/table/tbody/tr[2]/td[4]/a")).click();

        System.out.println("Selecting company trade...");
        Select companyTrade = new Select(firefox.findElement(By.xpath("//*[@id=\"context-company-trade\"]")));
        companyTrade.selectByValue(data.getCompanyTrade());
        System.out.println("~~~~SELECTED~~~~");

        System.out.println("Selecting legal status...");
        firefox.findElement(By.xpath("//*[@id=\"context-company-legal_status\"]")).click();
        firefox.findElement(By.xpath("/html/body/div[3]/div[3]/div[3]/div[8]/div/form/div/div[2]/fieldset/div[12]/div[2]/select/option[2]")).click();
        System.out.println("~~~~SELECTED~~~~");

        System.out.println("Selecting Poland as residence country...");
        firefox.findElement(By.xpath("//*[@id=\"context-person-residence-taxes_in_poland\"]")).click();
        firefox.findElement(By.xpath("/html/body/div[3]/div[3]/div[3]/div[8]/div/form/div/div[1]/fieldset/div[20]/fieldset/div/div[2]/select/option[2]")).click();
        System.out.println("~~~~SELECTED~~~~");

        System.out.println("Selecting PEP and date...");
        firefox.findElement(By.xpath("//*[@id=\"context-person-pep-pep\"]")).click(); //PEP
        firefox.findElement(By.xpath("/html/body/div[3]/div[3]/div[3]/div[8]/div/form/div/div[1]/fieldset/div[21]/fieldset/div[1]/div[2]/select/option[3]")).click();
        firefox.findElement(By.xpath("/html/body/div[3]/div[3]/div[3]/div[8]/div/form/div/div[1]/fieldset/div[21]/fieldset/div[3]/div[2]/img")).click(); //PEP date
        firefox.findElement(By.xpath("/html/body/div[7]/div/div/select[2]")).click();
        firefox.findElement(By.xpath("/html/body/div[7]/div/div/select[2]/option[100]")).click();
        firefox.findElement(By.xpath("/html/body/div[7]/table/tbody/tr[2]/td[4]/a")).click();
        firefox.findElement(By.xpath("//*[@id=\"context-person-address-street\"]")).sendKeys(data.getStreetName());
        firefox.findElement(By.xpath("//*[@id=\"context-person-address-house_number\"]")).sendKeys(data.getRandomNumber());
        firefox.findElement(By.xpath("//*[@id=\"context-person-address-town\"]")).sendKeys("Warszawa");
        firefox.findElement(By.xpath("//*[@id=\"context-person-address-postal_code\"]")).sendKeys("01-234");
        firefox.findElement(By.xpath("//*[@id=\"context-person-address-post\"]")).sendKeys("Warszawa");
        firefox.findElement(By.xpath("//*[@id=\"addressCopyToCorrespondence\"]")).click();
        firefox.findElement(By.xpath("//*[@id=\"addressCopyToFirm\"]")).click();
        firefox.findElement(By.xpath("//*[@id=\"addressCopyToCompanyCorrespondence\"]")).click();
        System.out.println("~~~~SELECTED~~~~");

        System.out.println("Selecting Account purpose and Deposited values...");
        Select accountPurpose = new Select(firefox.findElement(By.xpath("//*[@id=\"context-person-other-account_purpose\"]")));
        accountPurpose.selectByValue(data.getAccountPurpose());

        firefox.findElement(By.xpath("//*[@id=\"context-person-personAgreements-agreements_set_all\"]")).click();
        firefox.findElement(By.xpath("//*[@id=\"person-copyDataFromClient\"]")).click();
        firefox.findElement(By.xpath("//*[@id=\"person-copyDataFromClient\"]")).click();

        Select depositedValues = new Select(firefox.findElement(By.xpath("//*[@id=\"context-person-other-level_deposited_values\"]")));
        depositedValues.selectByValue(data.getDepositedValues());
        System.out.println("~~~~SELECTED~~~~");

        firefox.findElement(By.xpath("//*[@id=\"context-person-personAgreements-copyAgreementsFromClient\"]")).click();

        for (int i = 0; i<2; i++){
            firefox.findElement(By.xpath("//*[@id=\"company-copyDataFromClient\"]")).click();
        }

        for (int i = 0; i<2; i++){
            firefox.findElement(By.xpath("//*[@id=\"person-copyDataFromClient\"]")).click();
        }

        firefox.findElement(By.xpath("/html/body/div[3]/div[3]/div[3]/div[8]/div/form/div/div[3]/input")).click();
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
        firefox.findElement(By.xpath("/html/body/div[8]/div[2]/div/form/div/div[3]/ul/li[2]/a")).click();
        sleep(500);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"data-contracts_company-0-add_to_contract\"]")));
        Select soleFrontAccess = new Select(firefox.findElement(By.xpath("//*[@id=\"data-contracts_company-0-add_to_contract\"]")));
        soleFrontAccess.selectByValue("1");
        sleep(500);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"data-documents_company-0-verified\"]")));
        Select idCopy = new Select(firefox.findElement(By.xpath("//*[@id=\"data-documents_company-0-verified\"]")));
        idCopy.selectByValue("1");
        Select soleDocsVerified = new Select(firefox.findElement(By.xpath("//*[@id=\"data-documents_company-1-verified\"]")));
        soleDocsVerified.selectByValue("1");
        Select soleDocsVerified2 = new Select(firefox.findElement(By.xpath("//*[@id=\"data-documents_company-2-verified\"]")));
        soleDocsVerified2.selectByValue("1");
        Select soleDocsVerified3 = new Select(firefox.findElement(By.xpath("//*[@id=\"data-documents_company-3-verified\"]")));
        soleDocsVerified3.selectByValue("1");
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
        sole.insert(context,idNumber,personName,personLastName,peselNumber,regonNumber,nipNumber,phone,frontlogin, frontpassword);

        System.out.println("~~~~CLIENT CREATED~~~~" + "\n" + "~~~~CLIENT CREATED IN DEF~~~~" + "\n" + "~~~~CLIENT ADDED TO DATABASE~~~~" + "\n" + "~~~~SOLE PROPRIETORSHIP CREATION COMPLETED~~~~");
        firefox.close();
    }
}
