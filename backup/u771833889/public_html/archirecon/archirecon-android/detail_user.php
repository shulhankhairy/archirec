<?php
	
	require 'config.php';
	header('Content-type: application/json');


	class detail_user
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

			$query       = mysqli_query($this->connection, "SELECT * FROM USER where USER_ID='$id'");
			while ($row  = mysqli_fetch_array($query)) {
				
				$waktu = date('D, d F Y h:m A', strtotime($row['USER_CREATE_DATE']));
				array_push($arr,array(
					"id"        	=> $row['USER_ID'],
					"username" 		=> $row['USER_USERNAME'],
					"password"   	=> $row['USER_PASSWORD'],
					"nama"      	=> $row['USER_NAMA'],
					"alamat"   		=> $row['USER_ALAMAT'],
					"hp"   			=> $row['USER_HP'],
					"status" 		=> $row['USER_STATUS'],
					"create_date"   => $waktu
				));       				
			}
			echo json_encode($arr, JSON_PRETTY_PRINT);
       
		}
	}

	$show_data = new detail_user();
    $show_data->getData();	

?>