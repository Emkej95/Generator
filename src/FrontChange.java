/*/import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class FrontChange {
    private Natural natural = new Natural();
    private Sole sole = new Sole();
    private Company company = new Company();

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

    void run() {
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setCapability("marionette", true);

        System.out.println("Opening browser...");
        FirefoxDriver firefox = new FirefoxDriver();

        if (){
            System.out.println("Changing front password on DEV...");
            firefox.get("https://ibssodev2.bsbox.pl/#/login");
            firefox.manage().window().maximize();
        } else if () {
            System.out.println("Changing front password on RC...");
            firefox.get("https://rc-sso.cloud.ideabank.pl/#/login?appId=EBANKING");
            firefox.manage().window().maximize();
        }
    }
}
/*/