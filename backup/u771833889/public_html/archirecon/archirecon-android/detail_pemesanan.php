<?php
	
	require 'config.php';
	header('Content-type: application/json');


	class detail_pemesanan
	{
		
		private $db;
    	private $connection;

		function __construct()
		{
			$this->db = new DB_Connection();
        	$this->connection = $this->db->getConnection();
		}

		function getData()
		{
			$id  = $_GET['id'];
			$arr = array();

			$query       = mysqli_query($this->connection, "SELECT * FROM PEMESANAN where PESAN_ID='$id'");
			while ($row  = mysqli_fetch_array($query)) {
				
				$waktu = date('D, d F Y h:m A', strtotime($row['USER_CREATE_DATE']));
				array_push($arr,array(
					"id"        			=> $row['PESAN_ID'],
					"id_user"				=> $row['USER_ID'],
					"nama"      			=> $row['PESAN_NAMA'],
					"alamat"   				=> $row['PESAN_ALAMAT'],
					"hp"   					=> $row['PESAN_HP'],
					"perusahaan" 			=> $row['PESAN_PERUSAHAAN'],
					"pekerjaan"   			=> $row['PESAN_PEKERJAAN'],
					"ukuran_lahan"			=> $row['PESAN_UKURAN_LAHAN'],
					"arah_hadap_lahan" 		=> $row['PESAN_ARAH_HADAP_LAHAN'],
					"lebar_jalan"			=> $row['PESAN_LEBAR_JALAN'],
					"img_kebutuhan_ruang" 	=> $row['PESAN_GBR_KEBUTUHAN_RUANG'],
					"img_denah"				=> $row['PESAN_GBR_DENAH'],
					"img_foto_depan"		=> $row['PESAN_FOTO_DEPAN'],
					"img_foto_kanan"		=> $row['PESAN_FOTO_KANAN'],
					"img_foto_kiri"			=> $row['PESAN_FOTO_KIRI'],
					"img_foto_jalan"		=> $row['PESAN_FOTO_JALAN'],
					"create_date"			=> $row['PESAN_CREATE_DATE']
				));       				
			}
			echo json_encode($arr, JSON_PRETTY_PRINT);
       
		}
	}

	$show_data = new detail_pemesanan();
    $show_data->getData();	

?>