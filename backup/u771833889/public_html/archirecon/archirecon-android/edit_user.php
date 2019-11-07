<?php
	
	require 'config.php';
	header('Content-type: application/json');


	class edit_user
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
			$username   		= $_POST['username'];
			$password			= $_POST['password'];
			$nama 				= $_POST['nama'];
			$alamat				= $_POST['alamat'];
			$hp 				= $_POST['no_hp'];

			$query  			= mysqli_query($this->connection,"UPDATE USER set USER_USERNAME='$username', USER_PASSWORD='$password', USER_NAMA='$nama', USER_ALAMAT='$alamat', USER_HP='$hp' where USER_ID='$id'");
			$json['message']    = 'success';
            echo json_encode($json);
            mysqli_close($this->connection);
		}
	}

	$show_data = new edit_user();
    $show_data->setData();

?>