# language: en
Feature: Fungsi Login Aplikasi dan Interaksi Pengguna

  Scenario: Login berhasil dengan kredensial yang valid
    Given pengguna berada di halaman login aplikasi
    When pengguna memasukkan username "bendahara" dan password "admin123"
    And pengguna menekan tombol login
    Then pengguna seharusnya berhasil login dan diarahkan ke halaman utama
#
  Scenario: Login gagal dengan password yang salah
    Given pengguna berada di halaman login aplikasi
    When pengguna memasukkan username "bendahara" dan password "passwordSALAH"
    And pengguna menekan tombol login
    Then pengguna seharusnya melihat pesan error login

  Scenario: Logout berhasil setelah login
    # Asumsi: Langkah-langkah login dari skenario "Login berhasil" sudah terjadi
    # atau ada step @Given khusus untuk memastikan pengguna sudah login.
    # Untuk contoh ini, kita ulangi langkah login agar skenario mandiri.
    Given pengguna berada di halaman login aplikasi
    When pengguna memasukkan username "bendahara" dan password "admin123"
    And pengguna menekan tombol login
    Then pengguna seharusnya berhasil login dan diarahkan ke halaman utama
    When pengguna melakukan logout
    Then pengguna seharusnya kembali ke halaman login

#  Scenario: Verifikasi elemen dropdown dalam keadaan tertutup
#    # Asumsi: Pengguna sudah login dan berada di halaman yang menampilkan elemen ini.
#    # Untuk contoh ini, kita perlu navigasi atau memastikan state aplikasi yang benar.
#    Given pengguna sudah login dan berada di halaman dashboard # Anda mungkin perlu step ini atau navigasi langsung
#    # Ganti ".user-profile-dropdown-selector" dengan CSS selector yang benar untuk elemen dropdown Anda
#    Then elemen dengan selector ".user-profile-dropdown-selector" memiliki data-state "closed"