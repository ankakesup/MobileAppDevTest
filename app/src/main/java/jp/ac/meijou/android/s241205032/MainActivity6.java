package jp.ac.meijou.android.s241205032;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.util.Optional;

import jp.ac.meijou.android.s241205032.databinding.ActivityMain5Binding;
import jp.ac.meijou.android.s241205032.databinding.ActivityMain6Binding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity6 extends AppCompatActivity {

    private ActivityMain6Binding binding; // ViewBindingのためのフィールド
    private final OkHttpClient okHttpClient = new OkHttpClient();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMain6Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//        getImage("https://placehold.jp/350x350.png");

        binding.button.setOnClickListener(view -> {
            var text = binding.editText.getText().toString();

            var url = Uri.parse("https://placehold.jp/500x500.png")
                    .buildUpon()
                    .appendQueryParameter("text", text)
                    .build()
                    .toString();

            getImage(url);
        });
    }

    private void getImage(String url) {
        // リクエスト先にgistを指定
        var request = new Request.Builder()
                .url(url)
                .build();

        // 非同期通信でリクエスト
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                // 通信に失敗した時に呼ばれる
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                var bitmap = BitmapFactory.decodeStream(response.body().byteStream());

                runOnUiThread(() -> binding.imageView.setImageBitmap(bitmap));
            }
        });
    }
}