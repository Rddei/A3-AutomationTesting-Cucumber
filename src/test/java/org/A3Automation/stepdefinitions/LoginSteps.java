package org.A3Automation.stepdefinitions;

import io.cucumber.java.id.Ketika;
// import io.cucumber.java.id.Diberikan; // Hapus atau komentari import ini
import io.cucumber.java.en.Given;     // Tambahkan import untuk @Given (dari paket 'en' untuk English)
import io.cucumber.java.id.Dan;
import io.cucumber.java.id.Maka;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import static org.junit.jupiter.api.Assertions.*;

public class LoginSteps {

    // ... (konstruktor dan step lainnya tetap sama) ...

    // Menggunakan @Given sebagai alternatif dari @Diberikan
    // Teks step ("pengguna berada di halaman login aplikasi") tetap sama
    // agar cocok dengan file .feature Anda yang berbahasa Indonesia.
    @Given("pengguna berada di halaman login aplikasi")
    public void pengguna_berada_di_halaman_login_aplikasi() {
        // Dapatkan URL dari properti atau langsung di sini
        String urlAplikasi = "http://ptbsp.ddns.net:6882"; // GANTI DENGAN URL APLIKASI ANDA
        Hooks.driver.get(urlAplikasi);
        System.out.println("Pengguna berada di halaman: " + urlAplikasi);
        // Contoh assertion sederhana
        // assertTrue(Hooks.driver.getTitle().contains("Swag Labs"), "Judul halaman login tidak sesuai.");
    }

    // Metode untuk @Ketika, @Dan, @Maka tetap menggunakan anotasi bahasa Indonesia
    // atau bisa juga diubah ke padanan bahasa Inggrisnya jika diinginkan (@When, @And, @Then)
    // dengan mengubah import yang sesuai.

    @Ketika("pengguna memasukkan username {string} dan password {string}")
    public void penggunaMemasukkanUsernameDanPassword(String username, String password) {
        WebElement usernameField = Hooks.driver.findElement(By.id("user-name"));
        WebElement passwordField = Hooks.driver.findElement(By.id("password"));

        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        System.out.println("Pengguna memasukkan username: " + username + " dan password: " + password);
    }

    @Dan("pengguna menekan tombol login")
    public void pengguna_menekan_tombol_login() {
        WebElement loginButton = Hooks.driver.findElement(By.id("login-button"));
        loginButton.click();
        System.out.println("Pengguna menekan tombol login");
    }

    @Maka("pengguna berhasil login dan melihat halaman dashboard")
    public void pengguna_berhasil_login_dan_melihat_halaman_dashboard() {
        String dashboardUrlPart = "/inventory.html";
        assertTrue(Hooks.driver.getCurrentUrl().contains(dashboardUrlPart), "Pengguna tidak diarahkan ke halaman dashboard.");
        System.out.println("Pengguna berhasil login dan melihat halaman dashboard");
    }
}