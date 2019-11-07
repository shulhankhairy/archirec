package com.hexa.mobile.archirecon.navigation.fragment.user;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hexa.mobile.archirecon.model.Pemesanan3;
import com.hexa.mobile.archirecon.network.ApiPemesanan3;
import com.hexa.mobile.archirecon.network.ApiUtils;
import com.kosalgeek.android.photoutil.CameraPhoto;
import com.kosalgeek.android.photoutil.GalleryPhoto;
import com.kosalgeek.android.photoutil.ImageLoader;
import com.hexa.mobile.archirecon.R;
import com.hexa.mobile.archirecon.model.Pemesanan3;
import com.hexa.mobile.archirecon.network.ApiPemesanan3;
import com.hexa.mobile.archirecon.network.ApiUtils;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentPemesanan4  extends Fragment {

    View myView;
    ImageView kebutuhan_ruang, denah_rumah, tampak_depan, tampak_kanan, tampak_kiri,tampak_jalan;
    LinearLayout ll_button;
    Button btn_continue;
    ProgressDialog loading;
    ApiPemesanan3 userService3;
    private String path1,path2,path3,path4,path5,path6;
    Uri selected1,selected2,selected3,selected4,selected5,selected6;
    int status=0;
    String URL ="http://www.logiswebmedia.com/archirecon/archirecon-android/pemesanan3.php";
    Bitmap bitmap;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_pemesanan3, container, false);
        declaration();
        action();

        return myView;
    }

    void declaration() {
        userService3    = ApiUtils.getUserServices5();
        kebutuhan_ruang = myView.findViewById(R.id.kebutuhan_ruang);
        denah_rumah     = myView.findViewById(R.id.denah_rumah);
        tampak_depan    = myView.findViewById(R.id.tampak_depan);
        tampak_kanan    = myView.findViewById(R.id.tampak_kanan);
        tampak_kiri     = myView.findViewById(R.id.tampak_kiri);
        tampak_jalan    = myView.findViewById(R.id.tampak_jalan);
        ll_button       = myView.findViewById(R.id.ll_button);
        btn_continue    = myView.findViewById(R.id.btn_continue);
    }

    void action() {

        denah_rumah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status = 1;
                selectImage();
            }
        });
        tampak_kanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status = 2;
                selectImage();
            }
        });
        tampak_kiri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status = 3;
                selectImage();
            }
        });
        tampak_depan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status = 4;
                selectImage();
            }
        });
        kebutuhan_ruang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status = 5;
                selectImage();
            }
        });

        tampak_jalan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status = 6;
                selectImage();
            }
        });
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loading = ProgressDialog.show(getActivity(), null, "Harap Tunggu...", true, false);
                String value = getArguments().getString("id");
                pemesananStepThree(value);
            }
        });
    }

    public void pemesananStepThree(String id_get) {
        File imageFIle = new File(String.valueOf(path1));
        File imageFIle2 = new File(String.valueOf(path2));
        File imageFIle3 = new File(String.valueOf(path3));
        File imageFIle4 = new File(String.valueOf(path4));
        File imageFIle5 = new File(String.valueOf(path5));
        File imageFIle6 = new File(String.valueOf(path6));

        RequestBody id_update = RequestBody.create(MediaType.parse("text/plain"), id_get);
        RequestBody image = RequestBody.create(MediaType.parse(getActivity().getContentResolver().getType(selected1)), imageFIle);
        RequestBody image2 = RequestBody.create(MediaType.parse(getActivity().getContentResolver().getType(selected2)), imageFIle2);
        RequestBody image3 = RequestBody.create(MediaType.parse(getActivity().getContentResolver().getType(selected3)), imageFIle3);
        RequestBody image4 = RequestBody.create(MediaType.parse(getActivity().getContentResolver().getType(selected4)), imageFIle4);
        RequestBody image5 = RequestBody.create(MediaType.parse(getActivity().getContentResolver().getType(selected5)), imageFIle5);
        RequestBody image6 = RequestBody.create(MediaType.parse(getActivity().getContentResolver().getType(selected6)), imageFIle6);

        Call<Pemesanan3> call = userService3.pemesanan3Request(id_update,image,image2,image3,image4,image5,image6);
        call.enqueue(new Callback<Pemesanan3>() {
            @Override
            public void onResponse(Call<Pemesanan3> call, Response<Pemesanan3> response) {
                if(response.isSuccessful()) {
                    loading.dismiss();
                    Pemesanan3 data = response.body();
                    if (data.getSuccess().equals("success")) {
                        Toast.makeText(getActivity(),"Pesanan Anda Telah Masuk", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getActivity(),"Gambar Tidak Masuk", Toast.LENGTH_LONG).show();

                    }
                }
            }

            @Override
            public void onFailure(Call<Pemesanan3> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }

    private void selectImage() {

        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 1);
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);

                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Pemesanan Design");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                Uri uri = data.getData();
                if(status == 1){
                    /*selected1 = data.getData();
                    path1 = getRealPathFromURI(getActivity(), uri);
                    Toast.makeText(getActivity(), String.valueOf(path1), Toast.LENGTH_LONG).show();*/
                    denah_rumah.setImageBitmap(thumbnail);

                }else if(status == 2){
                    /*selected2 = data.getData();
                    path2 = getRealPathFromURI(getActivity(), uri);
                    Toast.makeText(getActivity(), String.valueOf(path2), Toast.LENGTH_LONG).show();*/
                    tampak_kanan.setImageBitmap(thumbnail);

                }else if(status == 3){
                    /*selected3 = data.getData();
                    path3 = getRealPathFromURI(getActivity(), uri);
                    Toast.makeText(getActivity(), String.valueOf(path3), Toast.LENGTH_LONG).show();*/
                    tampak_kiri.setImageBitmap(thumbnail);

                }else if(status == 4){
                    /*selected4 = data.getData();
                    path4 = getRealPathFromURI(getActivity(), uri);
                    Toast.makeText(getActivity(), String.valueOf(path4), Toast.LENGTH_LONG).show();*/
                    tampak_depan.setImageBitmap(thumbnail);

                }else if(status == 5){
                    /*selected5 = data.getData();
                    path5 = getRealPathFromURI(getActivity(), uri);
                    Toast.makeText(getActivity(), String.valueOf(path5), Toast.LENGTH_LONG).show();*/
                    kebutuhan_ruang.setImageBitmap(thumbnail);

                } else if (status == 6) {
                    /*selected6 = data.getData();
                    path6 = getRealPathFromURI(getActivity(), uri);
                    Toast.makeText(getActivity(), String.valueOf(path6), Toast.LENGTH_LONG).show();*/
                    tampak_jalan.setImageBitmap(thumbnail);
                }

                /*ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                File file = new File(Environment.getExternalStorageDirectory()+File.separator + "image.jpg");
                try {
                    file.createNewFile();
                    FileOutputStream fo = new FileOutputStream(file);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/

            } else if (requestCode == 2) {

                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getActivity().getContentResolver().query(selectedImage,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                if(status == 1){
                    selected1 = selectedImage;
                    path1 = String.valueOf(picturePath);
                    Toast.makeText(getActivity(), String.valueOf(path1), Toast.LENGTH_LONG).show();
                    denah_rumah.setImageBitmap(thumbnail);

                }else if(status == 2){
                    selected2 = selectedImage;
                    path2 = String.valueOf(picturePath);
                    Toast.makeText(getActivity(), String.valueOf(path2), Toast.LENGTH_LONG).show();
                    tampak_kanan.setImageBitmap(thumbnail);

                }else if(status == 3){
                    selected3 = selectedImage;
                    path3 = String.valueOf(picturePath);
                    Toast.makeText(getActivity(), String.valueOf(path3), Toast.LENGTH_LONG).show();
                    tampak_kiri.setImageBitmap(thumbnail);

                }else if(status == 4){
                    selected4 = selectedImage;
                    path4 = String.valueOf(picturePath);
                    Toast.makeText(getActivity(), String.valueOf(path4), Toast.LENGTH_LONG).show();
                    tampak_depan.setImageBitmap(thumbnail);

                }else if(status == 5){
                    selected5 = selectedImage;
                    path5 = String.valueOf(picturePath);
                    Toast.makeText(getActivity(), String.valueOf(path5), Toast.LENGTH_LONG).show();
                    kebutuhan_ruang.setImageBitmap(thumbnail);

                }else if (status == 6) {
                    selected6 = selectedImage;
                    path6 = String.valueOf(picturePath);
                    Toast.makeText(getActivity(), String.valueOf(path6), Toast.LENGTH_LONG).show();
                    tampak_jalan.setImageBitmap(thumbnail);
                }

            }
        }


    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = getActivity().getContentResolver().query(contentUri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":")+1);
        cursor.close();

        cursor = getActivity().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,null
                , MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

}