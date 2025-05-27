package org.A3Automation.stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions; // Import ChromeOptions
import java.util.HashMap; // Import HashMap
import java.util.Map; // Import Map
import java.util.concurrent.TimeUnit;

public class Hooks {

    public static WebDriver driver;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup(); // Setup ChromeDriver

        ChromeOptions options = new ChromeOptions();

        // Opsi untuk menonaktifkan fitur "Save password" dan terkait "Password Manager"
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("credentials_enable_service", false); // Menonaktifkan layanan kredensial (simpan password)
        prefs.put("profile.password_manager_enabled", false); // Menonaktifkan password manager bawaan

        // Opsi untuk menonaktifkan deteksi kebocoran kata sandi (ini yang kemungkinan besar Anda butuhkan)
        // Pada versi Chrome yang lebih baru, ini adalah preferensi yang relevan
        prefs.put("profile.password_manager_leak_detection", false);
        // Alternatif lain yang kadang disebut, bisa dicoba jika di atas tidak cukup
        // prefs.put("signin.password_manager_enabled", false);

        options.setExperimentalOption("prefs", prefs);

        // Opsi tambahan yang sering digunakan untuk "membersihkan" sesi browser otomatis
        options.addArguments("--start-maximized"); // Memulai browser dalam keadaan maximize
        options.addArguments("--disable-extensions"); // Menonaktifkan ekstensi
        options.addArguments("--disable-infobars"); // Menonaktifkan infobar seperti "Chrome is being controlled..." (mungkin sudah usang di versi baru)
        options.addArguments("--disable-notifications"); // Menonaktifkan notifikasi browser
        options.addArguments("--disable-popup-blocking"); // Menonaktifkan pemblokiran pop-up

        // Beberapa orang juga menggunakan argumen ini untuk menonaktifkan fitur sinkronisasi atau layanan yang tidak perlu
        // options.addArguments("--disable-sync");
        // options.addArguments("--disable-translate");

        // Untuk menghindari peringatan CDP, jika masih muncul dan mengganggu,
        // Anda bisa mencoba menambahkan dependensi selenium-devtools-vXXX seperti yang disarankan log,
        // atau terkadang opsi berikut bisa membantu (tapi bisa membatasi fungsionalitas DevTools)
        // options.setCapability("goog:loggingPrefs", Map.of("devtools", "OFF")); // Ini mungkin tidak menghilangkan semua warning CDP


        driver = new ChromeDriver(options); // Inisialisasi driver DENGAN options yang sudah diatur

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize(); // Baris ini mungkin tidak perlu jika sudah ada --start-maximized
        System.out.println("WebDriver berhasil diinisialisasi dengan custom options.");
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("WebDriver berhasil ditutup.");
        }
    }
}