package jp.ac.meijou.android.s241205032;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import jp.ac.meijou.android.s241205032.databinding.ActivityMain2Binding;
import jp.ac.meijou.android.s241205032.databinding.ActivityMain4Binding;

public class MainActivity4 extends AppCompatActivity {

    private ActivityMain4Binding binding; // ViewBindingのためのフィールド

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        // 次のaまで，ViewBindingの設定
        binding = ActivityMain4Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // a
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // MainActivity2から送られたデータを取得
        var editText = getIntent().getStringExtra("title");
        binding.textView.setText(editText);

        // OK button
        binding.button11.setOnClickListener(view -> {
            var intent = getIntent();
            intent.putExtra("ret", "OK");
            setResult(RESULT_OK, intent);
            finish();
        });

        // Cancel button
        binding.button12.setOnClickListener(view -> {
            setResult(RESULT_CANCELED);
            finish();
        });

    }
}