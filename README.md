# Kelompok-6-JavaFX

Nama Game : 
GAMECAR 

Konsep Game :
Konsep dasar dari game ini adalah menciptakan permainan di mana pemain mengendalikan sebuah mobil yang harus menghindari mobil-mobil lawan di jalan. Game ini dirancang untuk menghibur berbagai kalangan, mulai dari anak-anak hingga orang dewasa, dengan menawarkan pengalaman bermain yang sederhana namun menarik. 

Alur Bermain :
1. Register dengan memasukan pasword dan username
2. login menggunakan username dan pasword yang telah di register
3. klik button play untuk memulai permainan
4. hindari mobil musuh sebanyak mungkin untuk mencetak skor
5. jika menabrak mobil musuh  game akan berhenti dan akan mendapatkan skor akhir
6. klik restart jika ingin memainkan game kembali

Vide Demonstrasi Game :
https://youtu.be/HBaCb8ejSqU

Note :
Jika ingin menjalankan game harus menyambungkan dulu dengan database dengan mendowload connecter mysql untuk terhubung dengan database mysql di php my admin

Database game :
CREATE DATABASE game_db;
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);
