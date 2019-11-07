<?php
	
	require 'config.php';
	header('Content-type: application/json');


	class pemesanan2
	{
		
		private $db;
    	private $connection;

		function __construct()
		{
			$this->db = new DB_Connection();
        	$this->connection = $this->db->getConnection();
		}

		function setData()
		{
			$id         		= $_POST['id'];
			$ukuran_lahan   	= $_POST['ukuran_lahan'];
			$arah_hadap_lahan	= $_POST['arah_hadap_lahan'];
			$lebar_jalan 		= $_POST['lebar_jalan'];

			$query  			= mysqli_query($this->connection,"UPDATE PEMESANAN set PESAN_UKURAN_LAHAN='$ukuran_lahan', PESAN_ARAH_HADAP_LAHAN='$arah_hadap_lahan', PESAN_LEBAR_JALAN='$lebar_jalan' where PESAN_ID='$id'");
			$json['success']    = 'success';
			$json['id']			= $id;
            echo json_encode($json);
            mysqli_close($this->connection);
		}
	}

	$show_data = new pemesanan2();
    $show_data->setData();

?>