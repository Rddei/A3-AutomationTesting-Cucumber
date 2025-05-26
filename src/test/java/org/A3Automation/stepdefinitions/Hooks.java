package org.A3Automation.stepdefinitions; // Atau org.A3Automation.hooks

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions; // Opsional, untuk konfigurasi tambahan

import java.util.concurrent.TimeUnit;

public class Hooks {

    // Variabel WebDriver ini perlu di-share antar step definitions.
    // Salah satu cara sederhana adalah menggunakan variabel statis.
    // Untuk proyek yang lebih kompleks, pertimbangkan Dependency Injection.
    public static WebDriver driver;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup(); // Setup ChromeDriver
        // ChromeOptions options = new ChromeOptions(); // Opsional
        // options.addArguments("--headless"); // Opsional: jalankan tanpa membuka UI browser
        // options.addArguments("--disable-gpu");
        // options.addArguments("--window-size=1920,1080");
        driver = new ChromeDriver(/*options*/); // Inisialisasi driver, gunakan options jika ada
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