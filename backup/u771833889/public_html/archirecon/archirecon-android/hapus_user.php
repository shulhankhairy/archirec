<?php
	
	require 'config.php';
	header('Content-type: application/json');


	class hapus_user
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
			$id    = $_POST['id'];
			$query = mysqli_query($this->connection, "DELETE FROM USER WHERE USER_ID='$id'");
			$json['success']    = 'success';
            echo json_encode($json);
            mysqli_close($this->connection);
		}
	}

	$show_data = new hapus_user();
    $show_data->getData();	

?>