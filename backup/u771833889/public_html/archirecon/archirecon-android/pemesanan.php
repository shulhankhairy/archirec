<?php
	
	require 'config.php';
	header('Content-type: application/json');


	class pemesanan
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
			$id_user   	= $_POST['id_user'];
			$nama   	= $_POST['nama'];
			$hp			= $_POST['no_hp'];
			$alamat 	= $_POST['alamat'];
			$pekerjaan  = $_POST['pekerjaan'];
			$perusahaan = $_POST['perusahaan'];
			date_default_timezone_get('Asia/Jakarta');
    		$now		= date('Y-m-d H:i:s');

			$data  = mysqli_query($this->connection, "SELECT * FROM PEMESANAN ORDER BY PESAN_ID DESC limit 0,1");
		    if(mysqli_num_rows($data) > 0){
		        $row = mysqli_fetch_array($data);
		        $id = $row['PESAN_ID']+1;
		    }else{
		        $id = 1;
		    }

			$query  = mysqli_query($this->connection,"INSERT INTO PEMESANAN (PESAN_ID, USER_ID, PESAN_NAMA, PESAN_HP, PESAN_PEKERJAAN, PESAN_PERUSAHAAN, PESAN_ALAMAT, PESAN_CREATE_DATE)
													  VALUES('$id','$id_user','$nama','$hp','$pekerjaan','$perusahaan','$alamat', '$now')");
			$json['message']    = 'success';
			$json['id_user']    = $id_user;
			$json['id']			= $id;
            echo json_encode($json);
            mysqli_close($this->connection);
		}
	}

	$show_data = new pemesanan();
    $show_data->setData();

?>