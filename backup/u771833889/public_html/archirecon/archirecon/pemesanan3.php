<?php
session_start();
include_once 'config.php';
header('Content-type: application/json');
	
	class pemesanan3
	{
		private $db;
        private $connection;
         
        function __construct() {
            $this->db = new DB_Connection();
            $this->connection = $this->db->getConnection();
        }

        function upload_image()
		{
			$denah 		= $_POST['img_denah'];
			$foto_depan = $_POST['img_foto_depan'];
			$foto_jalan = $_POST['img_foto_jalan'];
			$foto_kanan = $_POST['img_foto_kanan'];
			$foto_kiri  = $_POST['img_foto_kiri'];
			$ruang		= $_POST['img_ruang'];
			$id         = $_POST['id'];

			date_default_timezone_get("Asia/Jakarta");
			$now = date('Y-m-dH:i:s');

			/*DENAH*/
 			$upload_folder1 = "image/denah";
 			if (!file_exists($upload_folder1)) {
    			mkdir($upload_folder1, 0777, true);
			}
			$path1   		= "$upload_folder1/".$id.".png";
			$image_link1 	= URL.$path1;

			$update1 = mysqli_query($this->connection, "UPDATE PEMESANAN set PESAN_GBR_DENAH='$image_link1' where PESAN_ID='$id'");
			file_put_contents($path1, base64_decode($denah));

			/*FOTO_DEPAN*/
			$upload_folder2 = "image/foto_depan";
 			if (!file_exists($upload_folder2)) {
    			mkdir($upload_folder2, 0777, true);
			}
			$path2   		= "$upload_folder2/".$id.".png";
			$image_link2 	= URL.$path2;

			$update2 = mysqli_query($this->connection, "UPDATE PEMESANAN set PESAN_FOTO_DEPAN='$image_link2' where PESAN_ID='$id'");
			file_put_contents($path2, base64_decode($foto_depan));

			/*FOTO_JALAN*/
			$upload_folder3 = "image/foto_jalan";
 			if (!file_exists($upload_folder3)) {
    			mkdir($upload_folder3, 0777, true);
			}
			$path3   		= "$upload_folder3/".$id.".png";
			$image_link3 	= URL.$path3;

			$update3 = mysqli_query($this->connection, "UPDATE PEMESANAN set PESAN_FOTO_JALAN='$image_link3' where PESAN_ID='$id'");
			file_put_contents($path3, base64_decode($foto_jalan));


			/*FOTO_KANAN*/
			$upload_folder4 = "image/foto_kanan";
 			if (!file_exists($upload_folder4)) {
    			mkdir($upload_folder4, 0777, true);
			}
			$path4   		= "$upload_folder4/".$id.".png";
			$image_link4 	= URL.$path4;

			$update4 = mysqli_query($this->connection, "UPDATE PEMESANAN set PESAN_FOTO_KANAN='$image_link4' where PESAN_ID='$id'");
			file_put_contents($path4, base64_decode($foto_kanan));

			/*FOTO_KIRI*/
			$upload_folder5 = "image/foto_kiri";
 			if (!file_exists($upload_folder5)) {
    			mkdir($upload_folder5, 0777, true);
			}
			$path5   		= "$upload_folder5/".$id.".png";
			$image_link5 	= URL.$path5;

			$update5 = mysqli_query($this->connection, "UPDATE PEMESANAN set PESAN_FOTO_KIRI='$image_link5' where PESAN_ID='$id'");
			file_put_contents($path5, base64_decode($foto_kiri));

			/*KEBUTUHAN RUANG*/
			$upload_folder6 = "image/kebutuhan_ruang";
 			if (!file_exists($upload_folder6)) {
    			mkdir($upload_folder6, 0777, true);
			}
			$path6   		= "$upload_folder6/".$id.".png";
			$image_link6 	= URL.$path6;

			$update6 = mysqli_query($this->connection, "UPDATE PEMESANAN set PESAN_GBR_KEBUTUHAN_RUANG='$image_link6' where PESAN_ID='$id'");
			file_put_contents($path6, base64_decode($ruang));
		}
	}

	$upload = new pemesanan3();
	$upload->upload_image();