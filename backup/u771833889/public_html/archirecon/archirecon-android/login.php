<?php
session_start();
include_once 'config.php';
header('Content-type: application/json');

	class check_login
	{
		private $db;
        private $connection;
         
        function __construct() {
            $this->db          = new DB_Connection();
            $this->connection  = $this->db->getConnection();
        }

        public function login($username,$password)
        {
            $query = mysqli_query($this->connection,"SELECT * FROM USER where USER_USERNAME='$username' and USER_PASSWORD='$password'");
            if(mysqli_num_rows($query) > 0){
                $result = mysqli_fetch_array($query);

                $status = $result['USER_VERIFICATION'];
                if($status == "no"){
                    $json['message']    = 'verifikasi';
                    $json['id']         = $result['USER_ID'];
                    $json['email']      = $result['USER_USERNAME'];
                    $json['kode']       = $result['USER_CODE'];
                    $json['nama']       = $result['USER_NAMA'];
                    $json['status']       = $result['USER_STATUS'];
                    echo json_encode($json);
                    mysqli_close($this->connection);
                }else{
                    $id     = $result['USER_ID'];
                    $queryx = mysqli_query($this->connection,"UPDATE USER SET USER_STATUS='active' WHERE USER_ID='$id'");
                    $json['message']    = 'success';
                    $json['nama']       = $result['USER_NAMA'];
                    $json['alamat']     = $result['USER_ALAMAT'];
                    $json['hp']         = $result['USER_HP'];
                    $json['id']         = $result['USER_ID'];
                    $json['role']       = $result['USER_ROLE'];
                    $json['status']       = $result['USER_STATUS'];
                    echo json_encode($json);
                    mysqli_close($this->connection);
                }
            }else{
	            $json['message'] = 'failure';
	            echo json_encode($json);
                mysqli_close($this->connection);
            }

            
        }
	}

	$check_login = new check_login();
    if(isset($_POST['username'])) {
        
        $username 	= $_POST['username'];
        $password 	= $_POST['password'];
        
        if(!empty($check_login)){
            $check_login->login($username,$password);
        }else{
            echo json_encode("you must type both inputs");
        }
    }
