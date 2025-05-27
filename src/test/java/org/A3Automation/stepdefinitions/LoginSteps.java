package org.A3Automation.stepdefinitions;

import io.cucumber.java.en.Given; // Menggunakan anotasi Inggris: Given
import io.cucumber.java.en.When;  // Menggunakan anotasi Inggris: When
import io.cucumber.java.en.And;   // Menggunakan anotasi Inggris: And
import io.cucumber.java.en.Then;  // Menggunakan anotasi Inggris: Then
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
// import org.openqa.selenium.WebDriver; // Tidak perlu import WebDriver di sini jika menggunakan Hooks.driver
import org.openqa.selenium.WebElement;
import static org.junit.jupiter.api.Assertions.*;

public class LoginSteps {

    public LoginSteps() {
        // Konstruktor bisa kosong
    }

    @Given("pengguna berada di halaman login aplikasi")
    public void pengguna_berada_di_halaman_login_aplikasi() {
        if (Hooks.driver == null) {
            System.err.println("KESALAHAN FATAL: Hooks.driver belum diinisialisasi (null) di step Given!");
            throw new RuntimeException("WebDriver tidak diinisialisasi.");
        }
        // URL Aplikasi yang dituju
        String urlAplikasi = "http://ptbsp.ddns.net:6882/login";
        System.out.println("STEP Given: Mencoba navigasi ke URL: " + urlAplikasi);
        try {
            Hooks.driver.get(urlAplikasi);
            System.out.println("STEP Given: Berhasil navigasi ke: " + Hooks.driver.getCurrentUrl());
            // Anda bisa menambahkan assertion untuk judul halaman jika ada dan statis
            // assertTrue(Hooks.driver.getTitle().contains("Judul Halaman Login Anda"), "Judul halaman login tidak sesuai.");
        } catch (Exception e) {
            System.err.println("STEP Given: KESALAHAN saat mencoba navigasi ke URL: " + urlAplikasi);
            e.printStackTrace();
            throw e;
        }
    }

    // Menggunakan parameter username dan password dari Gherkin
    @When("pengguna memasukkan username {string} dan password {string}")
    public void pengguna_memasukkan_username_dan_password(String username, String password) {
        if (Hooks.driver == null) {
            System.err.println("KESALAHAN FATAL: Hooks.driver adalah null di step When!");
            throw new RuntimeException("WebDriver tidak diinisialisasi sebelum step When.");
        }
        try {
            // --- PENTING: VERIFIKASI LOCATOR INI DI WEBSITE ANDA ---
            // Ganti "username" dengan atribut 'name' atau 'id' yang benar untuk kolom username
            WebElement usernameField = Hooks.driver.findElement(By.name("username")); // Asumsi: name="username"
            // Ganti "password" dengan atribut 'name' atau 'id' yang benar untuk kolom password
            WebElement passwordField = Hooks.driver.findElement(By.name("password")); // Asumsi: name="password"

            usernameField.clear(); // Bersihkan field (opsional, tapi praktik baik)
            usernameField.sendKeys(username); // Menggunakan parameter dari feature file

            passwordField.clear(); // Bersihkan field (opsional, tapi praktik baik)
            passwordField.sendKeys(password); // Menggunakan parameter dari feature file

            System.out.println("STEP When: Pengguna memasukkan username: " + username + " dan password (disensor)");
        } catch (NoSuchElementException e) {
            System.err.println("STEP When: KESALAHAN - Tidak dapat menemukan kolom username atau password. Periksa locator Anda!");
            System.err.println("Detail error: " + e.getMessage());
            throw e;
        }  catch (Exception e) {
            System.err.println("STEP When: KESALAHAN UMUM saat memasukkan username/password: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @And("pengguna menekan tombol login")
    public void pengguna_menekan_tombol_login() {
        if (Hooks.driver == null) {
            System.err.println("KESALAHAN FATAL: Hooks.driver adalah null di step And!");
            throw new RuntimeException("WebDriver tidak diinisialisasi sebelum step And.");
        }
        try {
            // --- PENTING: VERIFIKASI LOCATOR INI DI WEBSITE ANDA ---
            // Ini adalah asumsi. Tombol login Anda mungkin memiliki ID, Name, Class, atau XPath yang berbeda.
            // Contoh menggunakan CSS Selector untuk <button type="submit">
            WebElement loginButton = Hooks.driver.findElement(By.cssSelector("button[type='submit']"));
            // Alternatif jika ada ID: WebElement loginButton = Hooks.driver.findElement(By.id("id_tombol_login_anda"));
            // Alternatif jika ada Name: WebElement loginButton = Hooks.driver.findElement(By.name("name_tombol_login_anda"));

            loginButton.click();
            System.out.println("STEP And: Pengguna menekan tombol login.");
        } catch (NoSuchElementException e) {
            System.err.println("STEP And: KESALAHAN - Tidak dapat menemukan tombol login. Periksa locator Anda!");
            System.err.println("Detail error: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("STEP And: KESALAHAN UMUM saat menekan tombol login: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Then("pengguna seharusnya berhasil login dan diarahkan ke halaman utama")
    public void pengguna_seharusnya_berhasil_login_dan_diarahkan_ke_halaman_utama() {
        if (Hooks.driver == null) {
            System.err.println("KESALAHAN FATAL: Hooks.driver adalah null di step Then (sukses login)!");
            throw new RuntimeException("WebDriver tidak diinisialisasi sebelum step Then (sukses login).");
        }
        // --- PENTING: TENTUKAN KONDISI SUKSES LOGIN UNTUK WEBSITE ANDA ---
        String loginUrl = "http://ptbsp.ddns.net:6882/login";
        try {
            // Beri sedikit waktu HANYA UNTUK DEBUG jika ada redirect yang lambat.
            // IDEALNYA GUNAKAN EXPLICIT WAIT untuk menunggu elemen tertentu di halaman dashboard.
            Thread.sleep(3000); // Tunggu 3 detik (SANGAT TIDAK DIREKOMENDASIKAN UNTUK TES FINAL)
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt(); // Kembalikan status interrupt
        }
        String currentUrl = Hooks.driver.getCurrentUrl();
        System.out.println("STEP Then (sukses login): URL setelah login: " + currentUrl);
        assertNotEquals(loginUrl, currentUrl, "Pengguna masih berada di halaman login setelah mencoba login.");

        // Verifikasi tambahan (opsional, tapi lebih baik): Cek judul halaman atau elemen di dashboard
        // assertTrue(Hooks.driver.getTitle().contains("Dashboard Aplikasi Anda"), "Judul halaman dashboard tidak sesuai.");
        // try {
        //     WebElement dashboardElement = Hooks.driver.findElement(By.id("id_elemen_unik_di_dashboard"));
        //     assertTrue(dashboardElement.isDisplayed(), "Elemen dashboard tidak ditemukan.");
        // } catch (NoSuchElementException e) {
        //     fail("Login mungkin tampak berhasil (URL berubah) tapi elemen dashboard tidak ditemukan.");
        // }
        System.out.println("STEP Then (sukses login): Verifikasi berhasil, pengguna diarahkan ke halaman utama (URL berubah).");
    }

    @Then("pengguna seharusnya melihat pesan error login")
    public void pengguna_seharusnya_melihat_pesan_error_login() {
        if (Hooks.driver == null) {
            System.err.println("KESALAHAN FATAL: Hooks.driver adalah null di step Then (error login)!");
            throw new RuntimeException("WebDriver tidak diinisialisasi sebelum step Then (error login).");
        }
        // --- PENTING: VERIFIKASI LOCATOR PESAN ERROR INI DI WEBSITE ANDA ---
        // Cari elemen yang menampilkan pesan error.
        // String pesanErrorYangDiharapkan = "Username atau password salah"; // Sesuaikan jika perlu
        WebElement errorElement = null;
        try {
            // GANTI DENGAN LOCATOR YANG BENAR UNTUK PESAN ERROR DI WEBSITE ANDA
            // Contoh: mungkin sebuah div dengan class tertentu, atau span dengan id tertentu
            errorElement = Hooks.driver.findElement(By.xpath("//div[contains(@class, 'pesan-error-login')]")); // INI HANYA CONTOH LOCATOR
            // Atau: errorElement = Hooks.driver.findElement(By.id("id_elemen_pesan_error"));

            assertTrue(errorElement.isDisplayed(), "Elemen pesan error login tidak tampil.");
            System.out.println("STEP Then (error login): Pesan error login tampil: " + errorElement.getText());
            // Jika ingin memverifikasi teksnya:
            // assertTrue(errorElement.getText().contains(pesanErrorYangDiharapkan), "Teks pesan error tidak sesuai.");
        } catch (NoSuchElementException e) {
            System.err.println("STEP Then (error login): KESALAHAN - Tidak dapat menemukan elemen pesan error login. Periksa locator atau pastikan pesan error memang muncul.");
            System.err.println("Detail error: " + e.getMessage());
            fail("Elemen pesan error login tidak ditemukan. Locator: " + (errorElement != null ? errorElement.toString() : "belum sempat dicari atau salah"));
        } catch (Exception e) {
            System.err.println("STEP Then (error login): KESALAHAN UMUM saat mencari pesan error: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}