<?php
	
	require 'config.php';
	header('Content-type: application/json');


	class register
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
			$nama   = $_POST['nama'];
			$hp		= $_POST['no_hp'];
			$alamat = $_POST['alamat'];
			$email  = $_POST['email'];
			$pass   = $_POST['password'];

			/*GET ID*/
			$data  = mysqli_query($this->connection, "SELECT * FROM USER ORDER BY USER_ID DESC limit 0,1");
		    if(mysqli_num_rows($data) > 0){
		        $row = mysqli_fetch_array($data);
		        $id = $row['USER_ID']+1;
		    }else{
		        $id = 1;
		    }

			/*CHECK EMAIL*/
			$check  = mysqli_query($this->connection, "SELECT * FROM USER where USER_USERNAME='$email'");
			if(mysqli_num_rows($check) > 0){
				$json['failure'] = 'Email telah terdaftar';
	            echo json_encode($json);
                mysqli_close($this->connection);
			}else{
				$query  = mysqli_query($this->connection,"INSERT INTO USER (USER_ID, USER_USERNAME, USER_PASSWORD, USER_NAMA, USER_ALAMAT, USER_HP)
														  VALUES('$id','$email','$pass','$nama','$alamat','$hp')");
				$json['success']    = 'success';
                echo json_encode($json);
                mysqli_close($this->connection);
			}
		}
	}

	$show_data = new register();
    $show_data->setData();

?>