# language: en
Feature: Fungsi Login Aplikasi

  Scenario: Login berhasil dengan kredensial yang valid
    Given pengguna berada di halaman login aplikasi
    When pengguna memasukkan username "bendahara" dan password "admin123"
    And pengguna menekan tombol login
    Then pengguna seharusnya berhasil login dan diarahkan ke halaman utama

  Scenario: Login gagal dengan password yang salah
    Given pengguna berada di halaman login aplikasi
    When pengguna memasukkan username "bendahara" dan password "passwordSALAH"
    And pengguna menekan tombol login
    Then pengguna seharusnya melihat pesan error login