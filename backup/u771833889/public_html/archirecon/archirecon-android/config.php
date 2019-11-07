<?php

	require_once 'database.php';
	
	class DB_Connection {
		
		private $connect;
		function __construct() {
			$this->connect = mysqli_connect(hostname, user, password, db_name)
			or die("Could not connect to db");
			//echo "Tersambung Dengan DataBase";
		}
		
		public function getConnection()
		{
			return $this->connect;
			//echo "Tidak Tersambung dengan database";
		}
	}

?>