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
import java.util.Scanner;

import static java.lang.Thread.sleep;

class Natural {

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

    private void insert(String contextid, String idnumber, String clientname, String clientlastname, String clientpesel, String phonepassword, String frontlogin, String frontpassword, String environment){
        String sql = "INSERT INTO clients (clienttype, contextid, idnumber, clientname, clientlastname, clientpesel, clientregonnumber, clientnipnumber, phonepassword, frontlogin, frontpassword, environment) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

        try (Connection connect = this.getConnection();
             PreparedStatement pstmt = connect.prepareStatement(sql)) {
            pstmt.setString(1, "Natural");
            pstmt.setString(2, contextid);
            pstmt.setString(3, idnumber);
            pstmt.setString(4, clientname);
            pstmt.setString(5, clientlastname);
            pstmt.setString(6, clientpesel);
            pstmt.setString(7, "-");
            pstmt.setString(8, "-");
            pstmt.setString(9, phonepassword);
            pstmt.setString(10, frontlogin);
            pstmt.setString(11, frontpassword);
            pstmt.setString(12, environment);
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
        Scanner input = new Scanner(System.in);
        Natural natural = new Natural();
        Data data = new Data();
        Pesel pesel = new Pesel();
        Identity identity = new Identity();
        natural.getConnection();

        /*/~~~~~VARIABLES VALUES FROM METHODS~~~~~/*/
        String personName = data.getName();
        String personLastName = data.getLastName();
        String peselNumber = pesel.getPesel();
        String cityName = data.getCityName();
        String idNumber = identity.getIdentity();


        /*/~~~~~OPEN WEB BROWSER~~~~~/*/
        System.out.println("Choose environment: DEV or RC");
        Scanner environmentInput = new Scanner(System.in);
        String environment = environmentInput.nextLine();
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

        /*/~~~~CREATE NATURAL PERSON~~~~/*/
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"context-company-nip\"]")));
        firefox.findElement(By.xpath("/html/body/div[3]/div[3]/div[3]/fieldset/div/div/label[1]/input")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id=\"context-company-nip\"]")));
        firefox.findElement(By.xpath("//*[@id=\"context-person-forename\"]")).sendKeys(personName);
        System.out.println("Person name entered: " + personName);
        firefox.findElement(By.xpath("//*[@id=\"context-person-mother_maiden_surname\"]")).sendKeys(data.getLastName());
        firefox.findElement(By.xpath("//*[@id=\"context-person-surname\"]")).sendKeys(personLastName);
        System.out.println("Person last name entered: " + personLastName);
        firefox.findElement(By.xpath("//*[@id=\"context-person-reg_pes\"]")).sendKeys(peselNumber);
        System.out.println("Person PESEL entered: " + peselNumber);
        firefox.findElement(By.xpath("//*[@id=\"context-person-birth_place\"]")).sendKeys(cityName);
        System.out.println("Person born in: " + cityName);
        firefox.findElement(By.xpath("//*[@id=\"context-person-email\"]")).sendKeys("mkrzyzak3@ideabank.pl");
        firefox.findElement(By.xpath("//*[@id=\"context-person-phone\"]")).sendKeys("515045208");

        firefox.findElement(By.xpath("//*[@id=\"context-person-identityDocument-number\"]")).sendKeys(idNumber);
        System.out.println("ID Number generated: " + idNumber);
        firefox.findElement(By.xpath("/html/body/div[3]/div[3]/div[3]/div[8]/div/div[3]/form/div[2]/div[1]/fieldset/div[17]/fieldset/div[4]/div[2]/img")).click(); //ID Release date
        firefox.findElement(By.xpath("/html/body/div[7]/div/div/select[2]")).click();
        firefox.findElement(By.xpath("/html/body/div[7]/div/div/select[2]/option[100]")).click();
        firefox.findElement(By.xpath("/html/body/div[7]/table/tbody/tr[2]/td[4]/a")).click();
        firefox.findElement(By.xpath("//*[@id=\"context-person-residence-taxes_in_poland\"]")).click(); //Residency
        firefox.findElement(By.xpath("/html/body/div[3]/div[3]/div[3]/div[8]/div/div[3]/form/div[2]/div[1]/fieldset/div[20]/fieldset/div/div[2]/select/option[2]")).click();

        System.out.println("Selecting PEP and date...");
        firefox.findElement(By.xpath("//*[@id=\"context-person-pep-pep\"]")).click(); //PEP
        firefox.findElement(By.xpath("/html/body/div[3]/div[3]/div[3]/div[8]/div/div[3]/form/div[2]/div[1]/fieldset/div[21]/fieldset/div[1]/div[2]/select/option[3]")).click();
        firefox.findElement(By.xpath("/html/body/div[3]/div[3]/div[3]/div[8]/div/div[3]/form/div[2]/div[1]/fieldset/div[21]/fieldset/div[3]/div[2]/img")).click(); //PEP date
        firefox.findElement(By.xpath("/html/body/div[7]/div/div/select[2]")).click();
        firefox.findElement(By.xpath("/html/body/div[7]/div/div/select[2]/option[100]")).click();
        firefox.findElement(By.xpath("/html/body/div[7]/table/tbody/tr[2]/td[4]/a")).click();
        System.out.println("~~~~SELECTED~~~~");

        firefox.findElement(By.xpath("//*[@id=\"context-person-address-street\"]")).sendKeys(data.getStreetName());
        System.out.println("Selected street name...");
        firefox.findElement(By.xpath("//*[@id=\"context-person-address-house_number\"]")).sendKeys(data.getRandomNumber());
        System.out.println("Generated random house number...");
        firefox.findElement(By.xpath("//*[@id=\"context-person-address-town\"]")).sendKeys("Warszawa");
        firefox.findElement(By.xpath("//*[@id=\"context-person-address-postal_code\"]")).sendKeys("01-234");
        firefox.findElement(By.xpath("//*[@id=\"context-person-address-post\"]")).sendKeys("Warszawa");
        firefox.findElement(By.xpath("//*[@id=\"addressCopyToCorrespondence\"]")).click();
        System.out.println("Address information filled...");

        System.out.println("Selecting Account purpose and Deposited values...");
        Select accountPurpose = new Select(firefox.findElement(By.xpath("//*[@id=\"context-person-other-account_purpose\"]")));
        accountPurpose.selectByValue(data.getAccountPurpose());
        Select depositedValues = new Select(firefox.findElement(By.xpath("//*[@id=\"context-person-other-level_deposited_values\"]")));
        depositedValues.selectByValue(data.getDepositedValues());
        System.out.println("~~~~SELECTED~~~~");

        firefox.findElement(By.xpath("//*[@id=\"context-person-personAgreements-agreements_set_all\"]")).click();
        firefox.findElement(By.xpath("//*[@id=\"person-copyDataFromClient\"]")).click();
        firefox.findElement(By.xpath("//*[@id=\"person-copyDataFromClient\"]")).click();

        firefox.findElement(By.xpath("//*[@id=\"form_edit_person\"]")).click();
        sleep(20000);
        firefox.findElement(By.xpath("//*[@id=\"submit\"]")).click();

        String context = firefox.findElement(By.xpath("/html/body/div[3]/div[3]/div[3]/div[5]/div[1]/div/div/table/tbody/tr/td[2]")).getText();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[3]/div[3]/div[3]/div[5]/div[1]/div/div/table/tbody/tr/td[12]/a[1]")));
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


        System.out.println("~~~~CLIENT CREATED~~~~" + "\n" + "~~~~CLIENT CREATED IN DEF~~~~" + "\n" + "~~~~CLIENT ADDED TO DATABASE~~~~" + "\n" + "~~~~NATURAL PERSON CREATION COMPLETED~~~~");
        firefox.close();

        System.out.println("Do you want to change Front Password for that client? Y/N");
        String passwordInput = input.nextLine();

        if (passwordInput.matches("Y | y") && environment.matches("DEV | dev | TEST | test")) {
            System.out.println("Changing clients Front Password...");
            firefox.get("https://ibssodev2.bsbox.pl/#/login");
            firefox.manage().window().maximize();
        } else if (passwordInput.matches("Y | y") && environment.matches("RC | rc")){
            firefox.get("https://rc-sso.cloud.ideabank.pl/#/login");
            firefox.manage().window().maximize();
        } else if (passwordInput.matches("N | n")){
            System.out.println("Client created and added to database." + "\n" + "No need to change Front Password." + "\n" + "Terminating...");
            Thread.currentThread().interrupt();
            natural.insert(context, idNumber, personName, personLastName, peselNumber, phone, frontlogin, frontpassword, environment);
        }

    }
}