<?php
require 'PHPMailer/PHPMailerAutoload.php';

// $email = $_POST['email'];
// $nama  = $_POST['nama'];
// $kode  = $_POST['kode'];

// $email = "novannanega@gmail.com";
// $nama  = "alen";
// $kode  = "354313";

$mail = new PHPMailer;

//$mail->SMTPDebug = 3;                               // Enable verbose debug output

$mail->isSMTP();                                      // Set mailer to use SMTP
$mail->Host = 'mx1.hostinger.co.id';  // Specify main and backup SMTP servers
$mail->SMTPAuth = true;                               // Enable SMTP authentication
$mail->Username = '';                 // SMTP username
$mail->Password = '';                           // SMTP password
$mail->SMTPSecure = 'ssl';                            // Enable TLS encryption, `ssl` also accepted
$mail->Port = 465;                                   // TCP port to connect to

$mail->setFrom('archireconstruction@gmail.com', 'Admin Archirecon');
$mail->addAddress($email, $nama);     // Add a recipient
$mail->addReplyTo('archireconstruction@gmail.com', 'Information');

$mail->isHTML(true);                                  // Set email format to HTML

$mail->Subject = 'Verifikasi Email '+$kode;
$mail->Body    = "Yth, ".$nama."<br>"."Berikut adalah kode Verifikasi Anda ".$kode."<br>"."Terima Kasih";
$mail->AltBody = 'This is the body in plain text for non-HTML mail clients';

if(!$mail->send()) {
    echo 'Message could not be sent.';
    echo 'Mailer Error: ' . $mail->ErrorInfo;
} else {
    echo "success";
}