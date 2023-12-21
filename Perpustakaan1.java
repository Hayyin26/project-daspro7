import java.util.*;
import java.text.*;

public class Perpustakaan1 {
    static Scanner scan = new Scanner(System.in);
    static Scanner sc = new Scanner(System.in);
    static boolean bukuTersedia = false; // sebagai penanda judul buku ditemukan atau tidak
    static Date tanggalPeminjaman = new Date();
    static Date tanggalPengembalian = new Date();
    static String namaMahasiswa = "";
    static String nimMhs = "";
    static String usernameMhs = "";
    static String passwordMhs = "";
    static String pengguna;
    static boolean login = false;
    static boolean isPetugas = false;
    static final int MAX_BUKU = 100;
    static String judulBuku;
    static final int MAX_ANTRIAN = 100;
    static String[][] riwayatPeminjaman = new String[MAX_ANTRIAN][4]; // Menyimpan judul buku, tanggal peminjaman, dan
                                                                      // tanggal pengembalian
    static int jumlahRiwayatPeminjaman = 0;
    static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public static void main(String[] args) {
        // Deklarasi dan Inisialisasi Variabel
        String[] nama = new String[10];
        String[] username = new String[10];
        String[] passwordPengguna = new String[10];
        String[][] dataBuku = new String[100][9]; // batas maksimal input buku

        // Inisialisasi data buku awal
        // "1" = stok buku, "0" = jumlah buku yang sedang dipinjam, "null" = tanggal
        // pengembalian, "false" = status buku
        dataBuku[0] = new String[] { "C#", "A001", "Indira", "2020", "Explorer", "1", "0", null, "false" };
        dataBuku[1] = new String[] { "Java", "A002", "Nafa", "2022", "ex", "5", "0", null, "false" };
        int totalBuku = 2; // Menandai total buku yang sudah ada

        final int MAX_ANTRIAN = 100; // Jumlah maksimal antrian yang dapat ditampung
        String[] antrianPeminjaman = new String[MAX_ANTRIAN];
        int jumlahAntrian = 0;
        String[] riwayatAntrian = new String[MAX_ANTRIAN];
        int jumlahRiwayat = 0;
        int dendaPerMenit = 10000;

        // Buka Sistem
        System.out.println("==========================[ SISTEM PERPUSTAKAAN ]=============================");
        System.out.println("\nSelamat Datang! di Sistem Perpustakaan.");

        // Registrasi
        boolean isRegister = registrasiUser(nama, username, passwordPengguna, pengguna);

        if (isRegister) {
            System.out.println("\nRegistrasi berhasil");
        } else {
            System.out.println("\nRegistrasi gagal");
            return;
        }

        // Login
        boolean isLogin = login(username, passwordPengguna, pengguna);

        if (isLogin) {
            System.out.println("\nLogin berhasil.\n");
            System.out.println("\033[H\033[2J");
        } else {
            System.out.println("\nLogin gagal.\n");
            return;
        }

        // Menu
        if (!isPetugas) {
            boolean type = true;
            while (type) {
                System.out.println("===================================< MENU >==================================\n");
                System.out.println("1. Informasi Buku");
                System.out.println("2. Pinjam Buku");
                System.out.println("3. Riwayat Antrian");
                System.out.println("4. Riwayat Transaksi");
                System.out.println("5. Keluar");
                System.out.print("\nPilih menu (1/2/3/4/5): ");

                int perintah = scan.nextInt();

                switch (perintah) {

                    // Case ke-1 "Informasi Buku" meliputi data buku dan status buku
                    case 1:
                        informasiBuku(dataBuku, totalBuku);
                        break;

                    // Case ke-2 "Form Peminjaman"
                    case 2:
                        pinjamBuku(dataBuku, antrianPeminjaman, riwayatAntrian, namaMahasiswa, nimMhs, MAX_ANTRIAN,
                                jumlahAntrian, jumlahRiwayat);
                        break;

                    // Case ke-3 "Form Tampil Riwayat Antrian Peminjaman"
                    case 3:
                        tampilkanRiwayatAntrian(riwayatAntrian);
                        break;

                    // Case ke-4 "Form Tampil Riwayat Transaksi"
                    case 4:
                        tampilkanRiwayatTransaksi();
                        break;

                    // Case ke-5 "Perintah logout/keluar"
                    case 5:
                        System.out.println("\nTerima kasih telah menggunakan sistem perpustakaan.");
                        scan.close();
                        System.exit(0);

                    default:
                        System.out.println("\nPilihan tidak valid. Silakan pilih lagi.");
                }
            }
        } else {
            boolean type = true;
            while (type) {
                System.out.println("===================================< MENU >==================================\n");
                System.out.println("1. Informasi Buku");
                System.out.println("2. Tambah Data Buku");
                System.out.println("3. Update Data Buku");
                System.out.println("4. Pinjam Buku");
                System.out.println("5. Kembalikan Buku");
                System.out.println("6. Riwayat Antrian");
                System.out.println("7. Riwayat Transaksi");
                System.out.println("8. Hapus Data Buku");
                System.out.println("9. Keluar");
                System.out.print("\nPilih menu (1/2/3/4/5/6/7/8/9): ");

                int perintah = scan.nextInt();

                switch (perintah) {

                    // Case ke-1 "Informasi Buku"
                    case 1:
                        informasiBuku(dataBuku, totalBuku);
                        break;

                    // Case ke-2 "Tambahan Data Buku"
                    case 2:
                        totalBuku = tambahDataBuku(dataBuku, totalBuku, isPetugas);
                        break;

                    // Case ke-3 "Update Data Buku"
                    case 3:
                        updateDataBuku(dataBuku, judulBuku, isPetugas);
                        break;

                    // Case ke-4 "Pinjam Buku"
                    case 4:
                        pinjamBuku(dataBuku, antrianPeminjaman, riwayatAntrian, namaMahasiswa, nimMhs, MAX_ANTRIAN,
                                jumlahAntrian, jumlahRiwayat);
                        break;

                    // Case ke-5 "Kembalikan Buku"
                    case 5:
                        kembalikanBuku(dataBuku, antrianPeminjaman, riwayatAntrian, tanggalPengembalian, dendaPerMenit,
                                isPetugas);
                        break;

                    // Case ke-6 "Riwayat Antrian"
                    case 6:
                        tampilkanRiwayatAntrian(riwayatAntrian);
                        break;

                    // Case ke-7 "Riwayat Transaksi"
                    case 7:
                        tampilkanRiwayatTransaksi();
                        break;

                    // Case ke-8 "Hapus Data Buku"
                    case 8:
                        totalBuku = hapusDataBuku(dataBuku, isPetugas, totalBuku);
                        break;

                    // Case ke-9 "Keluar"
                    case 9:
                        System.out.println("\nTerima kasih telah menggunakan sistem perpustakaan.");
                        scan.close();
                        System.exit(0);

                    default:
                        System.out.println("\nPilihan tidak valid. Silakan pilih lagi.");
                }
            }
        }
    }

    // Function Registrasi
    public static boolean registrasiUser(String[] nama, String[] username, String[] passwordPengguna,
            String pengguna) {

        System.out.println("\n============================< FORM REGISTRASI >===============================");
        System.out.print("\nApakah Anda petugas atau mahasiswa? (petugas/mahasiswa): ");
        pengguna = scan.next();

        if (pengguna.equalsIgnoreCase("petugas")) {
            // Registrasi sebagai petugas
            System.out.print("\nMasukkan Nama: ");
            String namaPetugas = scan.next();
            System.out.print("Masukkan Username: ");
            String usernamePetugas = scan.next();
            System.out.print("Masukkan Password: ");
            String passwordPetugas = scan.next();

            // Simpan informasi petugas ke dalam array
            for (int i = 0; i < nama.length; i++) {
                if (nama[i] == null) {
                    nama[i] = namaPetugas;
                    username[i] = usernamePetugas;
                    passwordPengguna[i] = passwordPetugas;
                    isPetugas = true;
                    break;
                }
            }

            return true; // Registrasi berhasil sebagai petugas
        } else if (pengguna.equalsIgnoreCase("mahasiswa")) {
            // Registrasi sebagai mahasiswa
            System.out.print("\nMasukkan Nama    : ");
            String namaMahasiswa = scan.next();
            System.out.print("Masukkan Username: ");
            String usernameMhs = sc.next();
            System.out.print("Masukkan Password: ");
            String passwordMhs = sc.next();

            for (int i = 0; i < nama.length; i++) {
                if (nama[i] == null) {
                    nama[i] = namaMahasiswa;
                    username[i] = usernameMhs;
                    passwordPengguna[i] = passwordMhs;
                    break;
                }
            }

            return true; // Registrasi berhasil sebagai mahasiswa
        } else {
            return false; // Registrasi gagal karena inputan tidak valid
        }
    }

    // Function Login
    public static boolean login(String[] username, String[] passwordPengguna, String pengguna) {

        System.out.println("\n==================================< LOGIN >==================================");
        boolean isLogin = false;
        while (!isLogin) {
            System.out.print("\nMasukkan Username anda: ");
            String user = scan.next();
            System.out.print("Masukkan Password anda: ");
            String password = scan.next();

            for (int i = 0; i < username.length; i++) {
                if (user.equals(username[i]) && password.equals(passwordPengguna[i])) {
                    isLogin = true; // Jika login berhasil
                    break;
                }
            }

            if (!isLogin) {
                System.out.println("\nUsername atau password salah. Silakan coba lagi!");
            }
        }
        return true; // Login berhasil
    }

    // Fungsi untuk menampilkan informasi buku
    public static void informasiBuku(String[][] dataBuku, int totalBuku) {

        // Menampilkan header informasi buku
        System.out.println("\n=============================< INFORMASI BUKU >==============================\n");
        // Loop untuk setiap buku dalam databuku
        for (int i = 0; i < totalBuku; i++) {
            // Mengambil array yang mewakili dalam data buku
            String[] book = dataBuku[i];
            // Menampilkan informasi buku, termasuk judul, kode, pengarang, tahun terbit,
            // penerbit, dan stok
            System.out.println(
                    (i + 1) + ". " + "Judul buku\t: " + book[0] + "\n   Kode buku\t: " + book[1] +
                            "\n   Pengarang\t: " + book[2] + "\n   Tahun terbit\t: " + book[3] +
                            "\n   Penerbit\t: " + book[4] + "\n   Stok\t\t: " + book[5]);
            // Status Buku
            int stock = Integer.parseInt(book[5]);
            boolean statusPinjam = Boolean.parseBoolean(book[8]);
            // Menampilkan stok buku berdasarkan stok
            if (stock > 0) {
                System.out.println("   (Buku Tersedia)\n");
            } else {
                System.out.println("   (Buku Tidak Tersedia)\n");
            }
        }
    }

    // Fungsi untuk menambah data buku
    public static int tambahDataBuku(String[][] dataBuku, int totalBuku, boolean isPetugas) {
        if (totalBuku < MAX_BUKU) { // Cek apakah batas maksimal buku belum tercapai
            System.out.println("\n===============================< TAMBAH BUKU >===============================\n");
            // Meminta pengguna untuk memasukkan detail buku baru
            System.out.print("Masukkan judul buku\t: ");
            String title = scan.next();
            System.out.print("Masukkan kode buku\t: ");
            String code = scan.next();
            System.out.print("Masukkan nama pengarang\t: ");
            String author = scan.next();
            System.out.print("Masukkan tahun terbit\t: ");
            String year = scan.next();
            System.out.print("Masukkan nama penerbit\t: ");
            String publisher = scan.next();
            System.out.print("Masukkan jumlah stok\t: ");
            String stock = scan.next();

            // Tambahkan buku baru ke dalam array dataBuku
            dataBuku[totalBuku] = new String[] { title, code, author, year, publisher, stock, "0", null, "false" };

            // Perbarui status ketersediaan buku berdasarkan stok yang baru ditambahkan
            int newStock = Integer.parseInt(stock);
            if (newStock > 0) {
                dataBuku[totalBuku][8] = "true"; // Jika stok buku lebih besar dari 0, maka buku tersedia
            } else {
                dataBuku[totalBuku][8] = "false"; // Jika stok buku menjadi 0, maka status buku tidak tersedia
            }

            System.out.println("\nBuku berhasil ditambahkan.\n");
            return totalBuku + 1; // Tingkatkan jumlah buku yang sudah ditambahkan
        } else {
            System.out.println("Maaf, ruang untuk menambahkan buku baru sudah penuh.\n");
        }
        return totalBuku; // Kembalikan jumlah buku tanpa perubahan jika tidak ditambahkan buku baru
    }

    // Fungsi untuk proses Peminjaman Buku
    public static void pinjamBuku(String[][] dataBuku, String[] antrianPeminjaman,
            String[] riwayatAntrian, String namaMahasiswa, String nimMhs, int MAX_ANTRIAN, int jumlahAntrian,
            int jumlahRiwayat) {

        System.out.println("\n=============================< FORM PEMINJAMAN >=============================\n");
        System.out.print("Masukkan judul buku yang ingin dipinjam: ");
        scan.nextLine();
        String judulPinjam = scan.nextLine();
        boolean bukuTersedia = false;

        for (int i = 0; i < dataBuku.length; i++) {
            String[] book = dataBuku[i];
            if (judulPinjam.equalsIgnoreCase(book[0])) {
                bukuTersedia = true;
                System.out.print("Masukkan Nama Mahasiswa: ");
                namaMahasiswa = scan.nextLine();
                System.out.print("Masukkan NIM Mahasiswa: ");
                nimMhs = scan.nextLine();
                boolean statusPinjam = Boolean.parseBoolean(book[8]);
                int stokBuku = Integer.parseInt(book[5]);
                if (stokBuku > 0 || Integer.parseInt(book[5]) > 0 && !statusPinjam) {
                    book[8] = "true";
                    book[6] = String.valueOf(Integer.parseInt(book[6]) + 1);
                    book[5] = String.valueOf(stokBuku - 1);
                    // Menandai buku sebagai tersedia setelah dipinjam
                    if (Integer.parseInt(book[5]) > 0) {
                        bukuTersedia = true;
                    }
                    Date tanggalPeminjaman = new Date();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(tanggalPengembalian);
                    calendar.add(Calendar.MINUTE, 1);
                    tanggalPengembalian = calendar.getTime();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    System.out
                            .print("_____________________________________________________________________________\n\n");
                    System.out.println("Nama Mahasiswa\t: " + namaMahasiswa);
                    System.out.println("NIM Mahasiswa\t: " + nimMhs);
                    System.out.println("\nJudul buku\t\t: " + book[0] + "\nKode buku\t\t: " + book[1]);
                    System.out.println("Tanggal Peminjaman\t: " + dateFormat.format(tanggalPeminjaman));
                    System.out.println("Tanggal Pengembalian\t: " + dateFormat.format(tanggalPengembalian));
                    System.out.println("\nBuku " + book[0] + " berhasil dipinjam.");
                    System.out.println(
                            "______________________________________________________________________________\n");
                    System.out.println("Stok yang tersedia: " + book[5] + "\n");
                    tambahRiwayatPeminjaman(namaMahasiswa, judulPinjam, tanggalPeminjaman, tanggalPengembalian);
                } else {
                    System.out.println("\nStok buku " + book[0] + " tidak tersedia atau buku sedang dipinjam");
                    System.out.print("Apakah Anda ingin masuk antrian peminjaman? (ya/tidak): ");
                    String jawaban = scan.next();
                    if (jumlahAntrian < MAX_ANTRIAN && jawaban.equalsIgnoreCase("ya")) {
                        int indexKosong = -1;
                        for (int k = 0; k < riwayatAntrian.length; k++) {
                            if (riwayatAntrian[k] == null) {
                                indexKosong = k;
                                break;
                            }
                        }
                        if (indexKosong != -1) {
                            riwayatAntrian[indexKosong] = judulPinjam;
                            jumlahAntrian++;
                            System.out
                                    .println("Anda telah masuk ke antrian peminjaman untuk buku " + judulPinjam + "\n");
                        } else {
                            System.out.println("Riwayat antrian penuh.\n");
                        }
                    } else
                        System.out.println("");
                }
            }
        }

        if (!bukuTersedia) {
            System.out.println("Maaf, buku dengan judul '" + judulPinjam + "' tidak ditemukan\n");
        }
    }

    // Fungsi untuk proses Pengembalian Buku
    public static void kembalikanBuku(String[][] dataBuku, String[] antrianPeminjaman,
            String[] riwayatAntrian, Date tanggalPengembalian, int dendaPerMenit, boolean isPetugas) {
        System.out.println("\n============================< FORM PENGEMBALIAN >============================\n");
        System.out.print("Masukkan judul buku yang ingin dikembalikan: ");
        String judulKembali = scan.next();
        boolean bukuTersedia = false;
        for (int i = 0; i < dataBuku.length; i++) {
            String[] book = dataBuku[i];
            if (judulKembali.equalsIgnoreCase(book[0])) {
                bukuTersedia = true;
                System.out.print("Masukkan Nama Mahasiswa\t: ");
                namaMahasiswa = scan.next();
                System.out.print("Masukkan NIM Mahasiswa\t: ");
                nimMhs = scan.next();
                boolean statusPinjam = Boolean.parseBoolean(book[8]);
                if (statusPinjam) {
                    book[8] = "false";
                    book[5] = String.valueOf(Integer.parseInt(book[5]) + 1);

                    // Inisialisasi tanggalPengembalian
                    long selisihDetik = new Date().getTime() - tanggalPengembalian.getTime();
                    long selisihMenit = selisihDetik / (60 * 1000);

                    if (selisihMenit > 0) {
                        double denda = dendaPerMenit * selisihMenit;
                        System.out.println("Denda yang harus dibayar: Rp. " + denda);
                    }
                    // Tambahkan pengembalian buku ke riwayat peminjaman
                    for (int j = 0; j < jumlahRiwayatPeminjaman; j++) {
                        if (judulKembali.equalsIgnoreCase(riwayatPeminjaman[j][1]) && riwayatPeminjaman[j][3] == null) {
                            riwayatPeminjaman[j][3] = dateFormat.format(tanggalPengembalian); // Update tanggal
                                                                                              // pengembalian
                            break;
                        }
                    }
                    System.out.print("_____________________________________________________________________________\n");
                    System.out.println("\nNama Mahasiswa\t: " + namaMahasiswa);
                    System.out.println("NIM Mahasiswa\t: " + nimMhs);
                    System.out.println("\nJudul buku\t: " + book[0] + "\nKode buku\t: " + book[1]);
                    System.out.println("\nBuku " + book[0] + " berhasil dikembalikan.");
                    System.out
                            .print("______________________________________________________________________________\n");
                    System.out.println("Stok yang tersedia: " + book[5] + "\n");
                    hapusRiwayatAntrian(riwayatAntrian, judulKembali);
                } else {
                    System.out.println("Buku " + book[0] + " tidak sedang dipinjam.");
                }
            }
        }
        if (!bukuTersedia) {
            System.out.println("Maaf, buku dengan judul '" + judulKembali + "' tidak ditemukan\n");
        }
    }

    // Fungsi untuk menghapus riwayat antrian peminjaman
    public static void hapusRiwayatAntrian(String[] riwayatAntrian, String judulKembali) {
        boolean bukuDitemukan = false;
        for (int j = 0; j < riwayatAntrian.length; j++) {
            if (judulKembali.equalsIgnoreCase(riwayatAntrian[j])) {
                bukuDitemukan = true;
                // Geser elemen
                for (int k = j; k < riwayatAntrian.length - 1; k++) {
                    riwayatAntrian[k] = riwayatAntrian[k + 1];
                }
                // Kosongkan elemen
                riwayatAntrian[riwayatAntrian.length - 1] = null;
                break;
            }
        }
    }

    // Fungsi untuk menampilkan riwayat antrian peminjaman
    public static void tampilkanRiwayatAntrian(String[] riwayatAntrian) {

        System.out.println("\n=======================< RIWAYAT ANTRIAN PEMINJAMAN >========================\n");

        // Mengecek apakah riwayat antrian kosong
        if (riwayatAntrian.length == 0 || riwayatAntrian[0] == null) {
            System.out.println("Riwayat antrian kosong.\n");
        } else {
            for (int i = 0; i < riwayatAntrian.length; i++) {
                if (riwayatAntrian[i] != null) {
                    System.out.println((i + 1) + ". " + riwayatAntrian[i]);
                    System.out.println("");
                }
            }
        }
    }

    // Fungsi Tambah Riwayat Transaksi
    public static void tambahRiwayatPeminjaman(String namaMahasiswa, String judulBuku, Date tanggalPeminjaman,
            Date tanggalPengembalian) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        riwayatPeminjaman[jumlahRiwayatPeminjaman][0] = namaMahasiswa;
        riwayatPeminjaman[jumlahRiwayatPeminjaman][1] = judulBuku;
        riwayatPeminjaman[jumlahRiwayatPeminjaman][2] = dateFormat.format(tanggalPeminjaman);
        riwayatPeminjaman[jumlahRiwayatPeminjaman][3] = null; // Set tanggal pengembalian null saat dipinjam
        jumlahRiwayatPeminjaman++;
    }

    // Fungsi Tampil Riwayat Transaksi
    public static void tampilkanRiwayatTransaksi() {
        System.out.println("\n=========================< RIWAYAT TRANSAKSI >=========================\n");

        if (jumlahRiwayatPeminjaman == 0) {
            System.out.println("Tidak ada riwayat transaksi.\n");
        } else {
            System.out.println("Riwayat Peminjaman dan Pengembalian:\n");
            for (int i = 0; i < jumlahRiwayatPeminjaman; i++) {
                System.out.println("Nama Mahasiswa: " + riwayatPeminjaman[i][0]);
                System.out.println("Judul Buku: " + riwayatPeminjaman[i][1]);
                System.out.println("Tanggal Peminjaman: " + riwayatPeminjaman[i][2]);
                System.out.print("Tanggal Pengembalian: ");
                if (riwayatPeminjaman[i][3] != null) {
                    System.out.println(riwayatPeminjaman[i][3]);
                } else {
                    System.out.println("Belum dikembalikan");
                }
                System.out.println();
            }
        }
    }

    // Fungsi Update Data Buku
    public static void updateDataBuku(String[][] dataBuku, String judulBuku, boolean isPetugas) {
        System.out.println("\n===========================< UPDATE DATA BUKU >===============================\n");
        System.out.print("Masukkan judul buku yang ingin diupdate: ");
        scan.nextLine(); // Buang newline
        judulBuku = scan.nextLine();
        boolean bukuDitemukan = false;
        for (int i = 0; i < dataBuku.length; i++) {
            String[] book = dataBuku[i];
            if (judulBuku.equalsIgnoreCase(book[0])) {
                bukuDitemukan = true;
                boolean updateLagi = true;
                while (updateLagi) {
                    System.out.println("\nData saat ini\t:");
                    System.out.println("1. Judul buku\t: " + book[0]);
                    System.out.println("2. Kode buku\t: " + book[1]);
                    System.out.println("3. Pengarang\t: " + book[2]);
                    System.out.println("4. Tahun terbit\t: " + book[3]);
                    System.out.println("5. Penerbit\t: " + book[4]);
                    System.out.println("6. Stok\t\t: " + book[5]);

                    System.out.print("\nPilih data yang ingin diubah (1-6) atau 0 untuk selesai: ");
                    int choice = scan.nextInt();
                    scan.nextLine(); // Buang newline

                    if (choice == 0) {
                        updateLagi = false; // Pengguna selesai melakukan pembaruan
                    } else if (choice >= 1 && choice <= 6) {
                        System.out.print("Masukkan data baru: ");
                        String newData = scan.nextLine();

                        switch (choice) {
                            case 1:
                                book[0] = newData;
                                break;
                            case 2:
                                book[1] = newData;
                                break;
                            case 3:
                                book[2] = newData;
                                break;
                            case 4:
                                book[3] = newData;
                                break;
                            case 5:
                                book[4] = newData;
                                break;
                            case 6:
                                book[5] = newData;

                                // // Perbarui status ketersediaan buku berdasarkan stok baru
                                int newStock = Integer.parseInt(newData);
                                if (newStock > 0) {
                                    book[8] = "true"; // Jika stok buku menjadi 0, maka status buku tidak tersedia
                                } else {
                                    book[8] = "false"; // Jika stok buku lebih besar dari 0, maka buku tersedia
                                }
                                break;
                            default:
                                System.out.println("Pilihan tidak valid.");
                                break;
                        }

                        System.out.println("Data buku berhasil diupdate!");

                        System.out.print("\nApakah ada data lain yang ingin diupdate? (ya/tidak): ");
                        String jawaban = scan.nextLine();
                        System.out.print("");

                        while (!jawaban.equalsIgnoreCase("ya") && !jawaban.equalsIgnoreCase("tidak")) {
                            System.out.println(
                                    "Pilihan tidak valid. Apakah ada data lain yang ingin diupdate? (ya/tidak): ");
                            jawaban = scan.nextLine();
                        }

                        if (jawaban.equalsIgnoreCase("ya")) {
                            updateLagi = true; // Jika pengguna ingin melakukan pembaruan lagi
                        } else {
                            updateLagi = false; // Jika pengguna tidak ingin melakukan pembaruan lagi
                        }
                    } else {
                        System.out.println("Pilihan tidak valid.");
                    }
                }
                break; // keluar dari loop
            }
        }
        if (!bukuDitemukan) {
            System.out.println("Maaf, buku dengan judul '" + judulBuku + "' tidak ditemukan\n");
            // Meminta input judul buku lagi untuk melakukan update
            System.out.println("Silakan masukkan judul buku yang ingin diupdate: ");
            judulBuku = scan.nextLine();
            updateDataBuku(dataBuku, judulBuku, isPetugas); // Panggil kembali fungsi updateDataBuku dengan judul yang
                                                            // baru
        }
    }

    // Fungsi untuk Menghapus Data Buku
    public static int hapusDataBuku(String[][] dataBuku, boolean isPetugas, int totalBuku) {
        System.out.println("\n================================< HAPUS BUKU >===============================\n");
        System.out.print("Masukkan judul buku yang ingin dihapus: ");
        String judulHapus = scan.next();
        boolean bukuDihapus = false;

        for (int i = 0; i < totalBuku; i++) {
            String[] book = dataBuku[i];
            if (judulHapus.equalsIgnoreCase(book[0])) {
                // geser elemen
                for (int j = i; j < totalBuku - 1; j++) {
                    dataBuku[j] = dataBuku[j + 1];
                }
                // Mengosongkan elemen
                dataBuku[totalBuku - 1] = new String[dataBuku[0].length];
                bukuDihapus = true;
                System.out.println("Buku dengan judul '" + judulHapus + "' berhasil dihapus.\n");
                totalBuku--;
                break;
            }
        }
        if (!bukuDihapus) {
            System.out.println("Buku dengan judul '" + judulHapus + "' tidak ditemukan.\n");
        }
        return totalBuku; // Kembalikan totalBuku yang baru setelah menghapus
    }
}
