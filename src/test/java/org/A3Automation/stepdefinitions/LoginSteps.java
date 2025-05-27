package org.A3Automation.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.TimeoutException; // Import untuk TimeoutException
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions; // Import untuk ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait;     // Import untuk WebDriverWait
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class LoginSteps {

    public LoginSteps() {
        // Konstruktor bisa kosong
    }

    // =============== Langkah-langkah Login ===============
    @Given("pengguna berada di halaman login aplikasi")
    public void pengguna_berada_di_halaman_login_aplikasi() {
        if (Hooks.driver == null) {
            System.err.println("KESALAHAN FATAL: Hooks.driver belum diinisialisasi (null) di step Given!");
            throw new RuntimeException("WebDriver tidak diinisialisasi.");
        }
        String urlAplikasi = "http://ptbsp.ddns.net:6882/login";
        System.out.println("STEP Given: Mencoba navigasi ke URL: " + urlAplikasi);
        try {
            Hooks.driver.get(urlAplikasi);
            System.out.println("STEP Given: Berhasil navigasi ke: " + Hooks.driver.getCurrentUrl());
        } catch (Exception e) {
            System.err.println("STEP Given: KESALAHAN saat mencoba navigasi ke URL: " + urlAplikasi);
            e.printStackTrace();
            throw e;
        }
    }

    @When("pengguna memasukkan username {string} dan password {string}")
    public void pengguna_memasukkan_username_dan_password(String username, String password) {
        if (Hooks.driver == null) {
            System.err.println("KESALAHAN FATAL: Hooks.driver adalah null di step When!");
            throw new RuntimeException("WebDriver tidak diinisialisasi sebelum step When.");
        }
        try {
            // !!! GANTI LOCATOR INI SESUAI WEBSITE ANDA !!!
            WebElement usernameField = Hooks.driver.findElement(By.name("username")); // Asumsi: name="username"
            WebElement passwordField = Hooks.driver.findElement(By.name("password")); // Asumsi: name="password"

            usernameField.clear();
            usernameField.sendKeys(username);
            passwordField.clear();
            passwordField.sendKeys(password);
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
            // !!! GANTI LOCATOR INI SESUAI WEBSITE ANDA !!!
            WebElement loginButton = Hooks.driver.findElement(By.cssSelector("button[type='submit']")); // Asumsi
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
        String loginUrl = "http://ptbsp.ddns.net:6882/login";
        try {
            // !!! GANTI DENGAN EXPLICIT WAIT YANG LEBIH BAIK !!!
            Thread.sleep(3000); // Tunggu 3 detik (TIDAK DIREKOMENDASIKAN UNTUK TES FINAL)
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        String currentUrl = Hooks.driver.getCurrentUrl();
        System.out.println("STEP Then (sukses login): URL setelah login: " + currentUrl);
        assertNotEquals(loginUrl, currentUrl, "Pengguna masih berada di halaman login setelah mencoba login.");
        System.out.println("STEP Then (sukses login): Verifikasi berhasil, pengguna diarahkan ke halaman utama (URL berubah).");
        // !!! TAMBAHKAN VERIFIKASI YANG LEBIH SPESIFIK UNTUK HALAMAN UTAMA ANDA !!!
        // Misalnya, cek keberadaan elemen atau judul halaman dashboard.
    }

    @Then("pengguna seharusnya melihat pesan error login")
    public void pengguna_seharusnya_melihat_pesan_error_login() {
        if (Hooks.driver == null) {
            System.err.println("KESALAHAN FATAL: Hooks.driver adalah null di step Then (error login)!");
            throw new RuntimeException("WebDriver tidak diinisialisasi sebelum step Then (error login).");
        }

        // Teks pesan error yang diharapkan
        String expectedErrorMessage = "Incorrect username or password, please try again!";
        WebElement errorElement = null;

        try {
            // Mencari elemen <p> yang berisi teks persis seperti yang diharapkan.
            // Ini adalah locator XPath yang akan mencari tag <p> di mana saja dalam dokumen
            // yang teksnya sama persis dengan expectedErrorMessage.
            String errorElementXPath = String.format("//p[text()='%s']", expectedErrorMessage);
            // Alternatif jika teks mungkin memiliki spasi ekstra di awal/akhir atau Anda hanya ingin memastikan teks tersebut ada di dalam <p>:
            // String errorElementXPath = String.format("//p[contains(normalize-space(.), '%s')]", expectedErrorMessage);

            System.out.println("STEP Then (error login): Mencari pesan error dengan XPath: " + errorElementXPath);
            errorElement = Hooks.driver.findElement(By.xpath(errorElementXPath));

            // 1. Verifikasi bahwa elemen pesan error ditampilkan
            assertTrue(errorElement.isDisplayed(), "Elemen pesan error login tidak tampil padahal elemennya ditemukan.");
            System.out.println("STEP Then (error login): Elemen pesan error ditemukan dan tampil.");

            // 2. Verifikasi bahwa teks pesan error sesuai dengan yang diharapkan (opsional, karena XPath sudah mencarinya, tapi baik untuk kepastian)
            String actualErrorMessage = errorElement.getText().trim(); // .trim() untuk menghapus spasi di awal/akhir jika ada
            assertEquals(expectedErrorMessage, actualErrorMessage, "Teks pesan error tidak sesuai dengan yang diharapkan.");
            System.out.println("STEP Then (error login): Teks pesan error login tampil sesuai: \"" + actualErrorMessage + "\"");

        } catch (NoSuchElementException e) {
            System.err.println("STEP Then (error login): KESALAHAN - Tidak dapat menemukan elemen <p> dengan teks: \"" + expectedErrorMessage + "\". Periksa locator atau pastikan pesan error memang muncul dengan teks dan tag yang benar.");
            System.err.println("Detail error: " + e.getMessage());
            // Mencetak source halaman bisa membantu debug jika elemen tidak ditemukan (bisa sangat panjang outputnya)
            // System.err.println("Page source:\n" + Hooks.driver.getPageSource());
            fail("Elemen pesan error login dengan teks yang diharapkan tidak ditemukan.");
        } catch (Exception e) {
            System.err.println("STEP Then (error login): KESALAHAN UMUM saat mencari pesan error: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    // =============== Langkah-langkah Logout ===============
    @When("pengguna melakukan logout")
    public void pengguna_melakukan_logout() {
        if (Hooks.driver == null) {
            System.err.println("KESALAHAN FATAL: Hooks.driver adalah null di step Logout!");
            throw new RuntimeException("WebDriver tidak diinisialisasi sebelum step Logout.");
        }
        try {
            // LANGKAH 1: Klik tombol/pemicu logout awal
            // Anda menggunakan "button[data-state='closed']". Pastikan ini benar-benar
            // menunjuk ke tombol logout awal Anda (yang dengan ikon SVG).
            // Alternatif yang lebih kuat mungkin: "//button[.//svg[contains(@class, 'lucide-log-out')]]"
            String initialLogoutTriggerCssSelector = "button[data-state='closed']"; // VERIFIKASI INI!
            // Atau jika Anda lebih yakin dengan XPath untuk tombol ikon:
            // String initialLogoutTriggerXPath = "//button[.//svg[contains(@class, 'lucide-log-out')]]";

            System.out.println("STEP When: Mencoba klik pemicu logout awal dengan selector: " + initialLogoutTriggerCssSelector);
            WebElement initialLogoutTrigger = Hooks.driver.findElement(By.cssSelector(initialLogoutTriggerCssSelector));
            // Jika menggunakan XPath:
            // WebElement initialLogoutTrigger = Hooks.driver.findElement(By.xpath(initialLogoutTriggerXPath));

            initialLogoutTrigger.click();
            System.out.println("STEP When: Pemicu logout awal diklik.");

            // LANGKAH 2: Tunggu dan klik tombol konfirmasi "Ya"
            // Menggunakan WebDriverWait untuk menunggu tombol "Ya" muncul dan bisa diklik
            WebDriverWait wait = new WebDriverWait(Hooks.driver, Duration.ofSeconds(10)); // Tunggu maksimal 10 detik

            String yaButtonXPath = "//button[text()='Ya']"; // Locator untuk tombol "Ya"
            System.out.println("STEP When: Menunggu tombol konfirmasi 'Ya' muncul dengan XPath: " + yaButtonXPath);

            WebElement yaButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(yaButtonXPath)));

            yaButton.click();
            System.out.println("STEP When: Tombol konfirmasi 'Ya' diklik. Proses logout seharusnya dimulai.");

        } catch (NoSuchElementException e) {
            System.err.println("STEP When: KESALAHAN - Tidak dapat menemukan salah satu elemen untuk logout (pemicu awal atau tombol 'Ya'). Periksa locator Anda!");
            System.err.println("Detail error: " + e.getMessage());
            e.printStackTrace(); // Cetak stack trace untuk detail
            throw e; // Lempar ulang agar tes gagal
        } catch (TimeoutException e) {
            System.err.println("STEP When: KESALAHAN - Waktu tunggu habis saat menunggu tombol 'Ya' muncul atau bisa diklik.");
            System.err.println("Detail error: " + e.getMessage());
            e.printStackTrace();
            throw e; // Lempar ulang agar tes gagal
        } catch (Exception e) {
            System.err.println("STEP When: KESALAHAN UMUM saat melakukan logout: " + e.getMessage());
            e.printStackTrace();
            throw e; // Lempar ulang agar tes gagal
        }
    }

    @Then("pengguna seharusnya kembali ke halaman login")
    public void pengguna_seharusnya_kembali_ke_halaman_login() {
        if (Hooks.driver == null) {
            System.err.println("KESALAHAN FATAL: Hooks.driver adalah null di step verifikasi kembali ke halaman login!");
            throw new RuntimeException("WebDriver tidak diinisialisasi.");
        }
        String expectedLoginUrl = "http://ptbsp.ddns.net:6882/login";
        try {
            // !!! GANTI DENGAN EXPLICIT WAIT YANG LEBIH BAIK !!!
            Thread.sleep(2000); // HANYA untuk memberi waktu, tidak ideal
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        String currentUrl = Hooks.driver.getCurrentUrl();
        System.out.println("STEP Then: URL setelah logout: " + currentUrl);
        assertTrue(currentUrl.startsWith(expectedLoginUrl),
                "Pengguna tidak kembali ke halaman login. URL saat ini: " + currentUrl + ", diharapkan dimulai dengan: " + expectedLoginUrl);
        System.out.println("STEP Then: Verifikasi berhasil, pengguna kembali ke halaman login.");
    }

    // =============== Langkah untuk Cek data-state ===============
    @Given("pengguna sudah login dan berada di halaman dashboard") // Anda mungkin perlu menyesuaikan teks Gherkin ini
    public void pengguna_sudah_login_dan_berada_di_halaman_dashboard() {
        // Implementasi untuk step ini:
        // 1. Pastikan pengguna sudah login (mungkin dengan memanggil langkah-langkah login,
        //    atau jika skenario ini dijalankan setelah skenario login yang berhasil).
        // 2. Navigasi ke halaman dashboard jika belum ada di sana.
        if (Hooks.driver == null) {
            System.err.println("KESALAHAN FATAL: Hooks.driver adalah null di step Given (dashboard)!");
            throw new RuntimeException("WebDriver tidak diinisialisasi.");
        }
        System.out.println("STEP Given: Memastikan pengguna berada di halaman dashboard.");
        String dashboardIndicativeUrlPart = "/dashboard"; // GANTI DENGAN INDIKASI URL DASHBOARD ANDA
        String loginUrl = "http://ptbsp.ddns.net:6882/login";

        if (Hooks.driver.getCurrentUrl().contains(loginUrl)) {
            System.out.println("Pengguna masih di halaman login, mencoba login dulu...");
            pengguna_memasukkan_username_dan_password("bendahara", "admin123"); // Gunakan kredensial valid
            pengguna_menekan_tombol_login();
            try {
                Thread.sleep(3000); // Tunggu redirect
            } catch (InterruptedException e) { e.printStackTrace(); Thread.currentThread().interrupt(); }
        }

        // Setelah login atau jika sudah login, pastikan berada di URL yang mengandung indikasi dashboard
        // assertTrue(Hooks.driver.getCurrentUrl().contains(dashboardIndicativeUrlPart),
        //         "Pengguna tidak berada di halaman dashboard. URL saat ini: " + Hooks.driver.getCurrentUrl());
        System.out.println("Pengguna diasumsikan sudah di halaman dashboard atau halaman yang relevan.");
        // Anda mungkin perlu menambahkan navigasi eksplisit ke URL dashboard jika diperlukan:
        // String dashboardUrl = "http://ptbsp.ddns.net:6882/dashboard"; // GANTI DENGAN URL DASHBOARD ANDA
        // Hooks.driver.get(dashboardUrl);
    }

    @Then("elemen dengan selector {string} memiliki data-state {string}")
    public void elemen_dengan_selector_memiliki_data_state(String cssSelector, String expectedDataState) {
        if (Hooks.driver == null) {
            System.err.println("KESALAHAN FATAL: Hooks.driver adalah null di step cek data-state!");
            throw new RuntimeException("WebDriver tidak diinisialisasi.");
        }
        WebElement element = null;
        try {
            // !!! GANTI LOCATOR INI DI FEATURE FILE DENGAN CSS SELECTOR YANG BENAR !!!
            element = Hooks.driver.findElement(By.cssSelector(cssSelector));
            String actualDataState = element.getAttribute("data-state");

            assertNotNull(actualDataState, "Elemen dengan selector CSS '" + cssSelector + "' tidak memiliki atribut 'data-state'.");
            assertEquals(expectedDataState, actualDataState,
                    "Elemen dengan selector CSS '" + cssSelector + "' memiliki data-state '" + actualDataState + "', tetapi diharapkan '" + expectedDataState + "'.");
            System.out.println("STEP Then: Elemen dengan selector CSS '" + cssSelector + "' memiliki data-state '" + actualDataState + "' sesuai harapan.");
        } catch (NoSuchElementException e) {
            System.err.println("STEP Then: KESALAHAN - Tidak dapat menemukan elemen dengan selector CSS: " + cssSelector);
            System.err.println("Detail error: " + e.getMessage());
            fail("Elemen dengan selector CSS '" + cssSelector + "' tidak ditemukan.");
        } catch (Exception e) {
            System.err.println("STEP Then: KESALAHAN UMUM saat memeriksa data-state untuk selector CSS " + cssSelector + ": " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}