package cm.com.newdon;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

import cm.com.newdon.classes.Foundation;
import cm.com.newdon.classes.Post;
import cm.com.newdon.common.CommonData;
import cm.com.newdon.common.GalleryHelper;

public class DonateActivity extends AppCompatActivity{

    private static final int REQUESTCAMERA = 0;
    private static final int REQUESTGALLERY = 1;
    private static final int PIC_CROP = 2;
    private Bitmap bitmap;
    private Bitmap currentBitmap;
    private Uri selectedImage;

    private Foundation foundation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);
        foundation = CommonData.getInstance().getFoundations().get(position);

        TextView tvTitle = (TextView) findViewById(R.id.tvFoundTitle);
        tvTitle.setText(foundation.getTitle());
        tvTitle.setTextColor(Color.parseColor(foundation.getCategory().getColor()));

//        logo!!!
    }

/*go to the next step - make a Don
need to transfer foundation, comment and image
*/
    public void next(View view) {
        Post tempPost = new Post();
        tempPost.setFoundation(foundation);
        tempPost.setMessage(((EditText)findViewById(R.id.etComment)).getText().toString());


        if(foundation.getLogo()!=null) {
            ImageView imLogo = (ImageView) findViewById(R.id.imFound);
            imLogo.setImageBitmap(foundation.getLogo());
        }

//        save image!!!!

        CommonData.getInstance().setTempPost(tempPost);

        Intent intent = new Intent(getApplicationContext(), MakeDonActivity.class);
        startActivity(intent);
    }

    public void attachImage(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.attach_image_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.camera:
                        capture(item);
                        return true;
                    case R.id.gallery:
                        gallery(item);
                        return true;
                    default:
                        return true;
                }
            }
        });
        popup.show();
    }


    public void capture(MenuItem item) {
        //open camera
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUESTCAMERA);
        }
    }

    public void gallery(MenuItem item) {
        //open gallery
        Intent intent = GalleryHelper.openGallery();
        startActivityForResult(intent, REQUESTGALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        bitmap = null;
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUESTCAMERA) {
//Получаем ссылку на захваченное изображение:
//                pUri = data.getData();
//                cropImage();
//            камера возвращает данные в ключе data
                bitmap = (Bitmap) data.getExtras().get("data");
            } else if (requestCode == REQUESTGALLERY) {
                selectedImage = data.getData();
                try {
                    currentBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                    bitmap = GalleryHelper.getRotatedImage(getApplicationContext(), currentBitmap, selectedImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (requestCode == PIC_CROP) {
            /*
            Получаем данные
            типобезопасный контейнер направленный на производительность чтения
            и записи данных и его цель - хранить и предоставлять некую информацию
            по типу ключ-значение, где ключ это строка, а значения - данные
            наследующие интерфейс Parcelable или класс Parcel, которые в свою
            очередь предоставляют возможность для упаковки/распаковки данных
            при межпотоковом взаимодействии, что положительно сказывается на производительности.
            Если по-русски, то в объект типа Bundle можно запихать любой примитивный
            тип, а так же String и другие, созданные вами пользовательские
            типы наследующие класс Parcel или интерфейс Parcelable. А потом
            достать их по ключу в другом потоке которому вы передали объект
            Bundle. В итоге получается что Bundle это эдакий HashMap "заточенный"
            для работы с IPC(Inter-Process Communication).
             */
                Bundle extras = data.getExtras();
//            /*
//            Получаем изображение
//            Bitmap – это объект, который хранит в себе изображение.
//            Та же канва, с которой мы обычно работаем, это обертка,
//            которая принимает команды от нас и рисует их на Bitmap, который мы видим в результате.
//             */
                bitmap = extras.getParcelable("data");
            }
        }
    }

        //    private void cropImage() {
//        try {
//        }
//        catch (ActivityNotFoundException cant) {
//            //Показываем сообщение об ошибке:
//            String errorMessage = "Ваше устройство не поддерживает работу с камерой!";
//            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
//        }
//        //Вызываем стандартное действие захвата изображения:
//        Intent cropIntent = new Intent("com.android.camera.action.CROP");
//        //Указываем тип изображения и Uri
//        cropIntent.setDataAndType(pUri,"image/*");
//        //Настраиваем характеристики захвата:
//        cropIntent.putExtra("crop", "true");
//        cropIntent.putExtra("aspectX", 1);
//        cropIntent.putExtra("aspectY", 1);
//        //Указываем размер в X and Y
//        cropIntent.putExtra("outputX", 256);
//        cropIntent.putExtra("outputY", 256);
//        //Извлекаем данные
//        cropIntent.putExtra("return-data", true);
//        //Запускаем Activity и возвращаемся в метод onActivityResult
//        startActivityForResult(cropIntent, PIC_CROP);
//    }
}
