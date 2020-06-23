package main.other;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import main.smart.rcgj.R;
import main.utils.base.BaseActivity;
import main.utils.utils.time_select.CustomDatePickerDay;
import main.utils.views.Header;

public class SwrlActivity extends BaseActivity {

    @BindView(R.id.header)
    Header header;
    @BindView(R.id.etTitle)
    EditText etTitle;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.etLine)
    EditText etLine;
    @BindView(R.id.etContent)
    EditText etContent;
    @BindView(R.id.imAdd01)
    ImageView imAdd01;
    @BindView(R.id.imAdd02)
    ImageView imAdd02;
    @BindView(R.id.imAdd03)
    ImageView imAdd03;
    @BindView(R.id.btnUp)
    Button btnFirst;

    File tmpDir;
    boolean tag = false;
    String dirPath = "temp";
    String fileName1 = "";
    String fileName2 = "";
    String fileName3 = "";
    private CustomDatePickerDay customDatePicker;
    //ͼƬlist
    private ArrayList<String> mResults = new ArrayList<>();
    //ͼƬ��ַ
    private ArrayList<String> photoPath = new ArrayList<>();
    private static final int MY_PERMISSIONS_MY_UP_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        //initDatePicker();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_swrl;
    }

    @Override
    protected boolean isHasHeader() {
        return true;
    }

    @Override
    protected void rightClient() {
        Intent intent = new Intent(this,SwrlListActivity.class);
        startActivity(intent);
    }

//    /**
//     * ѡ��ʱ��
//     */
//    private void initDatePicker() {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
//        String now = sdf.format(new Date());
//        tvTime.setText(now);
//        customDatePicker = new CustomDatePickerDay(this, new CustomDatePickerDay.ResultHandler() {
//            @Override
//            public void handle(String time) {
//                // �ص��ӿڣ����ѡ�е�ʱ��
//                tvTime.setText(time);
//            }
//            // ��ʼ�����ڸ�ʽ���ã�yyyy-MM-dd HH:mm����������������
//        }, "2000-01-01 00:00", "2030-01-01 00:00");
//        // ����ʾʱ�ͷ�
//        customDatePicker.showSpecificTime(true);
//        // ������ѭ������
//        customDatePicker.setIsLoop(false);
//    }
//
//    @OnClick({R.id.tvTime, R.id.imAdd01, R.id.btnUp})
////    public void onViewClicked(View view) {
////        switch (view.getId()) {
////            case R.id.tvTime:
////                customDatePicker.show(tvTime.getText().toString());
////                break;
////            case R.id.imAdd01:
////                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
////                        || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
////
////                    ActivityCompat.requestPermissions(this,
////                            new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_MY_UP_IMAGE);
////                } else {
////                    Matisse.from(SwrlActivity.this)
////                            .choose(MimeType.allOf())//ͼƬ����
////                            .countable(true)//true:ѡ�к���ʾ����;false:ѡ�к���ʾ�Ժ�
////                            .maxSelectable(3)//��ѡ�������
////                            .capture(false)//ѡ����Ƭʱ���Ƿ���ʾ����
////                            .captureStrategy(new CaptureStrategy(true, "com.hy.powerplatform.fileprovider"))//����1 true��ʾ���մ洢�ڹ���Ŀ¼��false��ʾ�洢��˽��Ŀ¼������2�� AndroidManifest��authoritiesֵ��ͬ����������7.0ϵͳ ��������
////                            .thumbnailScale(0.8f)  //ͼƬ���ű���
////                            .imageEngine(new GlideEngine())//ͼƬ��������
////                            .forResult(TAG_ONE);//
////                }
////                break;
////            case R.id.btnUp:
////                break;
////        }
////    }
//
////    @Override
////    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//////        switch (requestCode) {
//////            case MY_PERMISSIONS_MY_UP_IMAGE:
////////                if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
////////                    Matisse.from(SwrlActivity.this)
////////                            .choose(MimeType.allOf())//ͼƬ����
////////                            .countable(true)//true:ѡ�к���ʾ����;false:ѡ�к���ʾ�Ժ�
////////                            .maxSelectable(3)//��ѡ�������
////////                            .capture(true)//ѡ����Ƭʱ���Ƿ���ʾ����
////////                            .captureStrategy(new CaptureStrategy(true, "com.hy.powerplatform.fileprovider"))//����1 true��ʾ���մ洢�ڹ���Ŀ¼��false��ʾ�洢��˽��Ŀ¼������2�� AndroidManifest��authoritiesֵ��ͬ����������7.0ϵͳ ��������
////////                            .thumbnailScale(0.7f)  //ͼƬ���ű���
////////                            .imageEngine(new GlideEngine())//ͼƬ��������
////////                            .forResult(TAG_ONE);//
////////                } else {
////////                    Toast.makeText(this, "Ȩ�ޱ��ܾ������ֶ�����", Toast.LENGTH_SHORT).show();
////////                }
//////                return;
//////        }
////    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
//            case TAG_ONE:
//                List<Uri> result = new ArrayList<>();
//                /** ����ԭ���ļ������������ݷ���ѡ����ļ��������в��� **/
//                if (resultCode == Activity.RESULT_OK) {
//                  //  result = Matisse.obtainResult(data);
//                }
//                for (int i = 0; i < result.size(); i++) {
//                    String path = UriTrytoPath.getImageAbsolutePath(this, result.get(i));
//                    mResults.add(path);
//                }
//                if (resultCode == RESULT_OK) {
//                    Bitmap cbitmap01 = null;
//                    Bitmap cbitmap02 = null;
//                    Bitmap cbitmap03 = null;
//                    if (result.size() == 1) {
//                        try {
//                            cbitmap01 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), result.get(0));
//                            imAdd01.setImageBitmap(cbitmap01);
//                            //ͼƬ�洢������
//                            saveImageToSD(mResults, "temp");
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    if (result.size() == 2) {
//                        try {
//                            cbitmap01 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), result.get(0));
//                            cbitmap02 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), result.get(1));
//                            imAdd01.setVisibility(View.VISIBLE);
//                            imAdd02.setVisibility(View.VISIBLE);
//                            imAdd03.setVisibility(View.VISIBLE);
//                            imAdd01.setImageBitmap(cbitmap01);
//                            imAdd02.setImageBitmap(cbitmap02);
//                            imAdd03.setVisibility(View.VISIBLE);
//                            //ͼƬ�洢������
//                            saveImageToSD(mResults, "temp");
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    if (mResults.size() == 3) {
//                        try {
//                            cbitmap01 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), result.get(0));
//                            cbitmap02 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), result.get(1));
//                            cbitmap03 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), result.get(2));
//                            imAdd01.setVisibility(View.VISIBLE);
//                            imAdd02.setVisibility(View.VISIBLE);
//                            imAdd03.setVisibility(View.VISIBLE);
//                            imAdd01.setImageBitmap(cbitmap01);
//                            imAdd02.setImageBitmap(cbitmap02);
//                            imAdd03.setImageBitmap(cbitmap03);
//                            //ͼƬ�洢������
//                            saveImageToSD(mResults, "temp");
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    imAdd01.setEnabled(false);
//                    break;
//                }
//        }
//    }
//
//    /**
//     * ͼƬ���浽����
//     *
//     * @param dirPath
//     */
//    private void saveImageToSD(ArrayList<String> mResults, String dirPath) {
//        //�½��ļ������ڴ�Ųü����ͼƬ
//        tmpDir = new File(Environment.getExternalStorageDirectory() + "/" + dirPath);
//        if (!tmpDir.exists()) {
//            tmpDir.mkdir();
//        }
//        for (int i = 0; i < mResults.size(); i++) {
//            Bitmap cbitmap = BitmapFactory.decodeFile(mResults.get(i));
//            Matrix matrix = new Matrix();
//            matrix.setScale(0.1f, 0.1f);
//            Bitmap bm = Bitmap.createBitmap(cbitmap, 0, 0, cbitmap.getWidth(),
//                    cbitmap.getHeight(), matrix, true);
//            //�½��ļ��洢�ü����ͼƬ
//            File img = new File(tmpDir.getAbsolutePath() + "/" + "shigu" + String.valueOf(i) + ".png");
//            try {
//                //���ļ������
//                FileOutputStream fos = new FileOutputStream(img);
//                //��bitmapѹ����д�������(��������ΪͼƬ��ʽ��ͼƬ�����������)
//                bm.compress(Bitmap.CompressFormat.JPEG, 100, fos);
//                //ˢ�������
//                fos.flush();
//                //�ر������
//                fos.close();
//                //����File���͵�Uri
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            photoPath.add(String.valueOf(img));
//        }
//        upImage();
//    }
//
//    /**
//     * �ϴ�ͼƬ
//     */
//    private void upImage() {
//        ProgressDialogUtil.startLoad(this, getResources().getString(R.string.taxi_progress_submitting));
//        if (mResults.size() == 1) {
//            if (imAdd01.getDrawable() != null) {
//                RequestParams params = new RequestParams();
//                File file = new File(Environment.getExternalStorageDirectory() + "/" + dirPath + "/" + "shigu0" + ".png");
//                String sdpath = Environment.getExternalStorageDirectory()
//                        + "/" + dirPath;// ��ȡsdcard�ĸ�·��
//                String filepath = "shigu0" + ".png";
//                try {
//                    params.put("file", file);
////                    params.put("fullname", filepath);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//                // ִ��post����
//                // �첽�Ŀͻ��˶���
//                AsyncHttpClient client = new AsyncHttpClient();
//                client.setTimeout(30000);
//                String url = ApiAddress.mainApi1 + ApiAddress.swrlimage;
//                client.post(url, params, new AsyncHttpResponseHandler() {
//                    @Override
//                    public void onSuccess(int arg0, String arg1) {
//                        super.onSuccess(arg0, arg1);
//                        try {
//                            JSONObject jsonObject = new JSONObject(arg1);
//                            fileName1 = jsonObject.getString("msg");
//                            Message message = new Message();
//                            message.what = 1;
//                            handler.sendMessage(message);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            Log.i("XXX", e.toString());
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(int statusCode, org.apache.http.Header[] headers, byte[] responseBody, Throwable error) {
//                        super.onFailure(statusCode, headers, responseBody, error);
//                        Log.i("XXX", error.toString());
//                        Message message = new Message();
//                        message.what = Constant.TAG_TWO;
//                        handler.sendMessage(message);
//                    }
//                });
//            }
//        } else if (mResults.size() == 2) {
//            if (imAdd01.getDrawable() != null) {
//                RequestParams params = new RequestParams();
//                File file = new File(Environment.getExternalStorageDirectory() + "/" + dirPath + "/" + "shigu0" + ".png");
//                String sdpath = Environment.getExternalStorageDirectory()
//                        + "/" + dirPath;// ��ȡsdcard�ĸ�·��
//                String filepath = "shigu0" + ".png";
//                try {
//                    params.put("file", file);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//                // ִ��post����
//                // �첽�Ŀͻ��˶���
//                AsyncHttpClient client = new AsyncHttpClient();
//                client.setTimeout(30000);
//                String url = ApiAddress.mainApi1 + ApiAddress.swrlimage;
//                client.post(url, params, new AsyncHttpResponseHandler() {
//                    @Override
//                    public void onSuccess(int arg0, String arg1) {
//                        super.onSuccess(arg0, arg1);
//                        try {
//                            Log.i("XXX", "XXX");
//                            JSONObject jsonObject = new JSONObject(arg1);
//                            fileName1 = jsonObject.getString("msg");
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(int statusCode, org.apache.http.Header[] headers, byte[] responseBody, Throwable error) {
//                        super.onFailure(statusCode, headers, responseBody, error);
//                        Log.i("XXX", "XXXXX");
//                        Message message = new Message();
//                        message.what = Constant.TAG_TWO;
//                        handler.sendMessage(message);
//                    }
//                });
//            }
//            if (imAdd02.getDrawable() != null) {
//                RequestParams params = new RequestParams();
//                File file = new File(Environment.getExternalStorageDirectory() + "/" + dirPath + "/" + "shigu1" + ".png");
//                String sdpath = Environment.getExternalStorageDirectory()
//                        + "/" + dirPath;// ��ȡsdcard�ĸ�·��
//                String filepath = "shigu1" + ".png";
//                try {
//                    params.put("file", file);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//                // ִ��post����
//                // �첽�Ŀͻ��˶���
//                AsyncHttpClient client = new AsyncHttpClient();
//                client.setTimeout(30000);
//                String url = ApiAddress.mainApi1 + ApiAddress.swrlimage;
//                client.post(url, params, new AsyncHttpResponseHandler() {
//                    @Override
//                    public void onSuccess(int arg0, String arg1) {
//                        super.onSuccess(arg0, arg1);
//                        try {
//                            Log.i("XXX", "XXX");
//                            JSONObject jsonObject = new JSONObject(arg1);
//                            fileName2 = jsonObject.getString("msg");
//                            Message message = new Message();
//                            message.what = 1;
//                            handler.sendMessage(message);
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(int statusCode, org.apache.http.Header[] headers, byte[] responseBody, Throwable error) {
//                        super.onFailure(statusCode, headers, responseBody, error);
//                        Log.i("XXX", "XXXXX");
//                        Message message = new Message();
//                        message.what = Constant.TAG_TWO;
//                        handler.sendMessage(message);
//                    }
//                });
//            }
//        } else if (mResults.size() == 3) {
//            if (imAdd01.getDrawable() != null) {
//                RequestParams params = new RequestParams();
//                File file = new File(Environment.getExternalStorageDirectory() + "/" + dirPath + "/" + "shigu0" + ".png");
//                String sdpath = Environment.getExternalStorageDirectory()
//                        + "/" + dirPath;// ��ȡsdcard�ĸ�·��
//                String filepath = "shigu0" + ".png";
//                try {
//                    params.put("file", file);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//                // ִ��post����
//                // �첽�Ŀͻ��˶���
//                AsyncHttpClient client = new AsyncHttpClient();
//                client.setTimeout(30000);
//                String url = ApiAddress.mainApi1 + ApiAddress.swrlimage;
//                client.post(url, params, new AsyncHttpResponseHandler() {
//                    @Override
//                    public void onSuccess(int arg0, String arg1) {
//                        super.onSuccess(arg0, arg1);
//                        try {
//                            Log.i("XXX", "XXX");
//                            JSONObject jsonObject = new JSONObject(arg1);
//                            fileName1 = jsonObject.getString("msg");
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(int statusCode, org.apache.http.Header[] headers, byte[] responseBody, Throwable error) {
//                        super.onFailure(statusCode, headers, responseBody, error);
//                        Log.i("XXX", "XXXXX");
//                        Message message = new Message();
//                        message.what = Constant.TAG_TWO;
//                        handler.sendMessage(message);
//                    }
//                });
//            }
//            if (imAdd02.getDrawable() != null) {
//                RequestParams params = new RequestParams();
//                File file = new File(Environment.getExternalStorageDirectory() + "/" + dirPath + "/" + "shigu1" + ".png");
//                String sdpath = Environment.getExternalStorageDirectory()
//                        + "/" + dirPath;// ��ȡsdcard�ĸ�·��
//                String filepath = "shigu1" + ".png";
//                try {
//                    params.put("file", file);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//                // ִ��post����
//                // �첽�Ŀͻ��˶���
//                AsyncHttpClient client = new AsyncHttpClient();
//                client.setTimeout(30000);
//                String url = ApiAddress.mainApi1 + ApiAddress.swrlimage;
//                client.post(url, params, new AsyncHttpResponseHandler() {
//                    @Override
//                    public void onSuccess(int arg0, String arg1) {
//                        super.onSuccess(arg0, arg1);
//                        try {
//                            Log.i("XXX", "XXX");
//                            JSONObject jsonObject = new JSONObject(arg1);
//                            fileName2 = jsonObject.getString("msg");
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(int statusCode, org.apache.http.Header[] headers, byte[] responseBody, Throwable error) {
//                        super.onFailure(statusCode, headers, responseBody, error);
//                        Log.i("XXX", "XXXXX");
//                        Message message = new Message();
//                        message.what = Constant.TAG_TWO;
//                        handler.sendMessage(message);
//                    }
//                });
//            }
//            if (imAdd03.getDrawable() != null) {
//                RequestParams params = new RequestParams();
//                File file = new File(Environment.getExternalStorageDirectory() + "/" + dirPath + "/" + "shigu2" + ".png");
//                String sdpath = Environment.getExternalStorageDirectory()
//                        + "/" + dirPath;// ��ȡsdcard�ĸ�·��
//                String filepath = "shigu2" + ".png";
//                try {
//                    params.put("file", file);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//                // ִ��post����
//                // �첽�Ŀͻ��˶���
//                AsyncHttpClient client = new AsyncHttpClient();
//                client.setTimeout(30000);
//                String url = ApiAddress.mainApi1 + ApiAddress.swrlimage;
//                client.post(url, params, new AsyncHttpResponseHandler() {
//                    @Override
//                    public void onSuccess(int arg0, String arg1) {
//                        super.onSuccess(arg0, arg1);
//                        try {
//                            Log.i("XXX", "XXX");
//                            JSONObject jsonObject = new JSONObject(arg1);
//                            fileName3 = jsonObject.getString("msg");
//                            Message message = new Message();
//                            message.what = 1;
//                            handler.sendMessage(message);
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(int statusCode, org.apache.http.Header[] headers, byte[] responseBody, Throwable error) {
//                        super.onFailure(statusCode, headers, responseBody, error);
//                        Log.i("XXX", "XXXXX");
//                        Message message = new Message();
//                        message.what = Constant.TAG_TWO;
//                        handler.sendMessage(message);
//                    }
//                });
//            }
//        }
//    }
//
//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case TAG_ONE:
//                    Toast.makeText(SwrlActivity.this, "ͼƬ�ϴ��ɹ�", Toast.LENGTH_SHORT).show();
//                    ProgressDialogUtil.stopLoad();
//                    break;
//                case Constant.TAG_TWO:
//                    Toast.makeText(SwrlActivity.this, "ͼƬ�ϴ�ʧ��", Toast.LENGTH_SHORT).show();
//                    ProgressDialogUtil.stopLoad();
//                    break;
//            }
//        }
//    };
}
