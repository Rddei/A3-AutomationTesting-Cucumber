package org.A3Automation.stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
// import org.openqa.selenium.chrome.ChromeOptions; // Aktifkan jika butuh options
import java.util.concurrent.TimeUnit; // Pastikan import ini ada

public class Hooks {

    public static WebDriver driver;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup(); // Setup ChromeDriver

        // --- Opsional: Konfigurasi ChromeOptions ---
        // ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless"); // Jalankan tanpa membuka UI browser (untuk CI/CD)
        // options.addArguments("--disable-gpu");
        // options.addArguments("--window-size=1920,1080");
        // options.addArguments("--no-sandbox"); // Beberapa lingkungan CI mungkin memerlukan ini
        // options.addArguments("--disable-dev-shm-usage"); // Beberapa lingkungan CI mungkin memerlukan ini
        // driver = new ChromeDriver(options); // Gunakan ini jika options diaktifkan

        driver = new ChromeDriver(); // Gunakan ini jika tanpa options
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); // Tunggu implisit
        driver.manage().window().maximize(); // Maksimalkan jendela browser
        System.out.println("WebDriver berhasil diinisialisasi.");
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit(); // Tutup semua jendela browser dan akhiri sesi WebDriver
            System.out.println("WebDriver berhasil ditutup.");
        }
    }
}