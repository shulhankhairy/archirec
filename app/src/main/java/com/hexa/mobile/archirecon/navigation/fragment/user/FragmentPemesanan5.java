package com.hexa.mobile.archirecon.navigation.fragment.user;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Matrix;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hexa.mobile.archirecon.config.myCommand;
import com.hexa.mobile.archirecon.navigation.Navigationdrawer;
import com.hexa.mobile.archirecon.network.ApiPemesanan3;
import com.hexa.mobile.archirecon.network.ApiUtils;
import com.kosalgeek.android.photoutil.CameraPhoto;
import com.kosalgeek.android.photoutil.GalleryPhoto;
import com.kosalgeek.android.photoutil.ImageBase64;
import com.kosalgeek.android.photoutil.ImageLoader;
import com.kosalgeek.android.photoutil.PhotoLoader;
import com.hexa.mobile.archirecon.R;
import com.hexa.mobile.archirecon.config.myCommand;
import com.hexa.mobile.archirecon.navigation.Navigationdrawer;
import com.hexa.mobile.archirecon.network.ApiPemesanan3;
import com.hexa.mobile.archirecon.network.ApiUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FragmentPemesanan5 extends Fragment {

    View myView;
    EditText kebutuhan_ruang;
    ImageView /*kebutuhan_ruang,*/ denah_rumah, tampak_depan, tampak_kanan, tampak_kiri,tampak_jalan;
    LinearLayout ll_button;
    Button btn_continue;
    ProgressDialog loading;
    ApiPemesanan3 userService3;
    String URL ="http://www.logiswebmedia.com/archirecon/archirecon-android/pemesanan3.php";

    CameraPhoto cameraPhoto;
    GalleryPhoto galleryPhoto;

    final int CAMERA_REQUEST = 13323;
    final int GALLERY_REQUEST = 2200;

    ArrayList<String> imageList1 = new ArrayList<>();
    ArrayList<String> imageList2 = new ArrayList<>();
    ArrayList<String> imageList3 = new ArrayList<>();
    ArrayList<String> imageList4 = new ArrayList<>();
    ArrayList<String> imageList5 = new ArrayList<>();
    ArrayList<String> imageList6 = new ArrayList<>();
    private ProgressDialog pDialog;
    private Bitmap bitmap;
    int status=0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_pemesanan3, container, false);
        declaration();
        action();

        return myView;
    }

    void declaration() {
        cameraPhoto     = new CameraPhoto(getActivity());
        galleryPhoto    = new GalleryPhoto(getActivity());
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
        /*kebutuhan_ruang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status = 5;
                selectImage();
            }
        });*/

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
                upload_image5(value);
            }
        });
    }

    public void selectImage(){
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    try {
                        startActivityForResult(cameraPhoto.takePhotoIntent(), CAMERA_REQUEST);
                        cameraPhoto.addToGallery();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else if(items[item].equals("Choose from Library")){
                    Intent in = galleryPhoto.openGalleryIntent();
                    startActivityForResult(in, GALLERY_REQUEST);
                }else if(items[item].equals("Cancel")){
                    dialog.dismiss();
                }

            }
        });
        builder.show();
    }

    @Override
    public  void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {
                String photoPath = cameraPhoto.getPhotoPath();
                if(status == 1){
                    imageList1.clear();
                    imageList1.add(photoPath);
                    try {
                        Bitmap bitmap = ImageLoader.init().from(photoPath).requestSize(512, 512).getBitmap();
                        denah_rumah.setImageBitmap(rotateBitmap(photoPath, bitmap));

                    } catch (FileNotFoundException e) {
                        Log.e("Error", e.getMessage());
                    }
                }else if(status == 2){
                    imageList2.clear();
                    imageList2.add(photoPath);
                    try {
                        Bitmap bitmap = ImageLoader.init().from(photoPath).requestSize(512, 512).getBitmap();
                        tampak_kanan.setImageBitmap(rotateBitmap(photoPath, bitmap));

                    } catch (FileNotFoundException e) {
                        Log.e("Error", e.getMessage());
                    }
                }else if(status == 3){
                    imageList3.clear();
                    imageList3.add(photoPath);
                    try {
                        Bitmap bitmap = ImageLoader.init().from(photoPath).requestSize(512, 512).getBitmap();
                        tampak_kiri.setImageBitmap(rotateBitmap(photoPath, bitmap));

                    } catch (FileNotFoundException e) {
                        Log.e("Error", e.getMessage());
                    }
                }else if(status == 4){
                    imageList4.clear();
                    imageList4.add(photoPath);
                    try {
                        Bitmap bitmap = ImageLoader.init().from(photoPath).requestSize(512, 512).getBitmap();
                        tampak_depan.setImageBitmap(rotateBitmap(photoPath, bitmap));

                    } catch (FileNotFoundException e) {
                        Log.e("Error", e.getMessage());
                    }
                /*}else if(status == 5){
                    imageList5.add(photoPath);
                    try {
                        Bitmap bitmap = ImageLoader.init().from(photoPath).requestSize(512, 512).getBitmap();
                        kebutuhan_ruang.setImageBitmap(rotateBitmap(photoPath, bitmap));

                    } catch (FileNotFoundException e) {
                        Log.e("Error", e.getMessage());
                    }*/
                } else if (status == 6) {
                    imageList6.clear();
                    imageList6.add(photoPath);
                    try {
                        Bitmap bitmap = ImageLoader.init().from(photoPath).requestSize(512, 512).getBitmap();
                        tampak_jalan.setImageBitmap(rotateBitmap(photoPath, bitmap));

                    } catch (FileNotFoundException e) {
                        Log.e("Error", e.getMessage());
                    }
                }

            } else if (requestCode == GALLERY_REQUEST) {
                Uri uri = data.getData();

                galleryPhoto.setPhotoUri(uri);
                String photoPath = galleryPhoto.getPath();
                if(status == 1){
                    imageList1.clear();
                    imageList1.add(photoPath);
                    try {
                        Bitmap bitmap = ImageLoader.init().from(photoPath).requestSize(512, 512).getBitmap();
                        denah_rumah.setImageBitmap(rotateBitmap(photoPath, bitmap));

                    } catch (FileNotFoundException e) {
                        Log.e("Error", e.getMessage());
                    }
                }else if(status == 2){
                    imageList2.clear();
                    imageList2.add(photoPath);
                    try {
                        Bitmap bitmap = ImageLoader.init().from(photoPath).requestSize(512, 512).getBitmap();
                        tampak_kanan.setImageBitmap(rotateBitmap(photoPath, bitmap));

                    } catch (FileNotFoundException e) {
                        Log.e("Error", e.getMessage());
                    }
                }else if(status == 3){
                    imageList3.clear();
                    imageList3.add(photoPath);
                    try {
                        Bitmap bitmap = ImageLoader.init().from(photoPath).requestSize(512, 512).getBitmap();
                        tampak_kiri.setImageBitmap(rotateBitmap(photoPath, bitmap));

                    } catch (FileNotFoundException e) {
                        Log.e("Error", e.getMessage());
                    }
                }else if(status == 4){
                    imageList4.clear();
                    imageList4.add(photoPath);
                    try {
                        Bitmap bitmap = ImageLoader.init().from(photoPath).requestSize(512, 512).getBitmap();
                        tampak_depan.setImageBitmap(rotateBitmap(photoPath, bitmap));

                    } catch (FileNotFoundException e) {
                        Log.e("Error", e.getMessage());
                    }
                /*}else if(status == 5){
                    imageList5.add(photoPath);
                    try {
                        Bitmap bitmap = ImageLoader.init().from(photoPath).requestSize(512, 512).getBitmap();
                        kebutuhan_ruang.setImageBitmap(rotateBitmap(photoPath, bitmap));

                    } catch (FileNotFoundException e) {
                        Log.e("Error", e.getMessage());
                    }*/
                } else if (status == 6) {
                    imageList6.clear();
                    imageList6.add(photoPath);
                    try {
                        Bitmap bitmap = ImageLoader.init().from(photoPath).requestSize(512, 512).getBitmap();
                        tampak_jalan.setImageBitmap(rotateBitmap(photoPath, bitmap));

                    } catch (FileNotFoundException e) {
                        Log.e("Error", e.getMessage());
                    }
                }
            }
        }
    }

    public static Bitmap rotateBitmap(String src, Bitmap bitmap) {
        try {
            int orientation = getExifOrientation(src);

            if (orientation == 1) {
                return bitmap;
            }

            Matrix matrix = new Matrix();
            switch (orientation) {
                case 2:
                    matrix.setScale(-1, 1);
                    break;
                case 3:
                    matrix.setRotate(180);
                    break;
                case 4:
                    matrix.setRotate(180);
                    matrix.postScale(-1, 1);
                    break;
                case 5:
                    matrix.setRotate(90);
                    matrix.postScale(-1, 1);
                    break;
                case 6:
                    matrix.setRotate(90);
                    break;
                case 7:
                    matrix.setRotate(-90);
                    matrix.postScale(-1, 1);
                    break;
                case 8:
                    matrix.setRotate(-90);
                    break;
                default:
                    return bitmap;
            }

            try {
                Bitmap oriented = Bitmap.createBitmap(bitmap, 0, 0,
                        bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                bitmap.recycle();
                return oriented;
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
                return bitmap;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    private static int getExifOrientation(String src) throws IOException {
        int orientation = 1;

        try {
            /**
             * if your are targeting only api level >= 5 ExifInterface exif =
             * new ExifInterface(src); orientation =
             * exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
             */
            if (Build.VERSION.SDK_INT >= 5) {
                Class<?> exifClass = Class
                        .forName("android.media.ExifInterface");
                Constructor<?> exifConstructor = exifClass
                        .getConstructor(new Class[] { String.class });
                Object exifInstance = exifConstructor
                        .newInstance(new Object[] { src });
                Method getAttributeInt = exifClass.getMethod("getAttributeInt",
                        new Class[] { String.class, int.class });
                Field tagOrientationField = exifClass
                        .getField("TAG_ORIENTATION");
                String tagOrientation = (String) tagOrientationField.get(null);
                orientation = (Integer) getAttributeInt.invoke(exifInstance,
                        new Object[] { tagOrientation, 1 });
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        }

        return orientation;
    }

    public void upload_image5(final String id){
        final myCommand myCommand = new myCommand(getActivity());
        /*for (String imagePath : imageList5) {*/
        /*try {*/
                /*Bitmap bitmap = PhotoLoader.init().from(imagePath).requestSize(512, 512).getBitmap();
                final String encodedString = ImageBase64.encode(rotateBitmap(imagePath, bitmap));*/

        final String kebutuhan        = kebutuhan_ruang.getText().toString();
        if(kebutuhan.equals("")){
            upload_image1(id);
        }else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    upload_image1(id);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //Toast.makeText(getActivity(), "Error while uploading image", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("img_ruang", kebutuhan);
                    params.put("id", id);
                    return params;
                }
            };

            myCommand.add(stringRequest);
        }

            /*} catch (FileNotFoundException e) {
                Toast.makeText(getActivity(), "Error while loading image", Toast.LENGTH_SHORT).show();
            }*/


        myCommand.execute();
        /*}*/

        /*final String kebutuhan        = kebutuhan_ruang.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        upload_image6(id);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                //Adding parameters to request
                params.put("img_ruang", kebutuhan);
                params.put("id", id);
                return params;
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);*/

    }

    public void upload_image1(final String id){
        final myCommand myCommand = new myCommand(getActivity());
        if(imageList1.size() > 0){
            for (String imagePath : imageList1) {
                try {
                    Bitmap bitmap = PhotoLoader.init().from(imagePath).requestSize(512, 512).getBitmap();
                    final String encodedString = ImageBase64.encode(rotateBitmap(imagePath, bitmap));

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            upload_image2(id);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //Toast.makeText(getActivity(), "Error while uploading image", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("img_denah", encodedString);
                            params.put("id", id);
                            return params;
                        }
                    };

                    myCommand.add(stringRequest);

                } catch (FileNotFoundException e) {
                    Toast.makeText(getActivity(), "Error while loading image", Toast.LENGTH_SHORT).show();
                }


                myCommand.execute();
            }
        }else{
            upload_image2(id);
        }
    }

    public void upload_image2(final String id){
        final myCommand myCommand = new myCommand(getActivity());
        if(imageList2.size() > 0){
            for (String imagePath : imageList2) {
                try {
                    Bitmap bitmap = PhotoLoader.init().from(imagePath).requestSize(512, 512).getBitmap();
                    final String encodedString = ImageBase64.encode(rotateBitmap(imagePath, bitmap));

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            upload_image3(id);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //Toast.makeText(getActivity(), "Error while uploading image", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("img_foto_kanan", encodedString);
                            params.put("id", id);
                            return params;
                        }
                    };

                    myCommand.add(stringRequest);

                } catch (FileNotFoundException e) {
                    Toast.makeText(getActivity(), "Error while loading image", Toast.LENGTH_SHORT).show();
                }


                myCommand.execute();
            }
        }else{
            upload_image3(id);
        }

    }

    public void upload_image3(final String id){
        final myCommand myCommand = new myCommand(getActivity());
        if(imageList3.size() > 0){
            for (String imagePath : imageList3) {
                try {
                    Bitmap bitmap = PhotoLoader.init().from(imagePath).requestSize(512, 512).getBitmap();
                    final String encodedString = ImageBase64.encode(rotateBitmap(imagePath, bitmap));

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            upload_image4(id);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //Toast.makeText(getActivity(), "Error while uploading image", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("img_foto_kiri", encodedString);
                            params.put("id", id);
                            return params;
                        }
                    };

                    myCommand.add(stringRequest);


                } catch (FileNotFoundException e) {
                    Toast.makeText(getActivity(), "Error while loading image", Toast.LENGTH_SHORT).show();
                }


                myCommand.execute();
            }
        }else{
            upload_image4(id);
        }

    }

    public void upload_image4(final String id){
        final myCommand myCommand = new myCommand(getActivity());
        if(imageList4.size() > 0){
            for (String imagePath : imageList4) {
                try {
                    Bitmap bitmap = PhotoLoader.init().from(imagePath).requestSize(512, 512).getBitmap();
                    final String encodedString = ImageBase64.encode(rotateBitmap(imagePath, bitmap));

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            upload_image6(id);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //Toast.makeText(getActivity(), "Error while uploading image", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("img_foto_depan", encodedString);
                            params.put("id", id);
                            return params;
                        }
                    };

                    myCommand.add(stringRequest);

                } catch (FileNotFoundException e) {
                    Toast.makeText(getActivity(), "Error while loading image", Toast.LENGTH_SHORT).show();
                }


                myCommand.execute();
            }
        }else{
            upload_image6(id);
        }

    }



    public void upload_image6(final String id){
        final myCommand myCommand = new myCommand(getActivity());
        if(imageList6.size() > 0){
            for (String imagePath : imageList6) {
                try {
                    Bitmap bitmap = PhotoLoader.init().from(imagePath).requestSize(512, 512).getBitmap();
                    final String encodedString = ImageBase64.encode(rotateBitmap(imagePath, bitmap));
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            loading.dismiss();
                            Intent intent = new Intent(getActivity(), Navigationdrawer.class);
                            startActivity(intent);
                            Toast.makeText(getActivity(), "Pemesanan anda berhasil ditambahkan", Toast.LENGTH_LONG).show();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //Toast.makeText(getActivity(), "Error while uploading image", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("img_foto_jalan", encodedString);
                            params.put("id", id);
                            return params;
                        }
                    };

                    myCommand.add(stringRequest);

                } catch (FileNotFoundException e) {
                    Toast.makeText(getActivity(), "Error while loading image", Toast.LENGTH_SHORT).show();
                }


                myCommand.execute();
            }
        }else{
            loading.dismiss();
            Intent intent = new Intent(getActivity(), Navigationdrawer.class);
            startActivity(intent);
            Toast.makeText(getActivity(), "Pemesanan anda berhasil ditambahkan", Toast.LENGTH_LONG).show();
        }

    }

}
