<?php
session_start();
include_once 'config.php';
header('Content-type: application/json');
class verifikasi {
          
    private $db;
    private $connection;
      
    function __construct() {
        $this->db = new DB_Connection();
        $this->connection = $this->db->getConnection();
    }
      
    public function goVerifikasi()
    {        
        $id    = $_POST['id'];
        $kode  = $_POST['kode'];

        $query = mysqli_query($this->connection, "SELECT * FROM USER WHERE USER_ID='$id'");
        $row   = mysqli_fetch_array($query);

        if($row['kode'] == $kode){
            $lol = mysqli_query($this->connection, "UPDATE USER_ID SET USER_VERIFICATION='yes' WHERE USER_ID='$id'");
            $json['message']   = 'success';
            echo json_encode($json);
            mysqli_close($this ->connection);
        }else{
            $json['message'] = 'Data Tidak Ada';
            echo json_encode($json);
            mysqli_close($this ->connection);  
        }
 	}
     
}

 	$show_data = new verifikasi();
    $show_data->goVerifikasi();

?>