package jp.ac.meijou.android.s241205032;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Optional;

import jp.ac.meijou.android.s241205032.databinding.ActivityMain2Binding;

public class MainActivity2 extends AppCompatActivity {

    private ActivityMain2Binding binding; // ViewBindingのためのフィールド

    private final ActivityResultLauncher<Intent> getActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                switch (result.getResultCode()) {
                    case RESULT_OK -> {
                        Optional.ofNullable(result.getData())
                                .map(data -> data.getStringExtra("ret"))
                                .map(text -> "Result: " + text)
                                .ifPresent(text -> binding.textView4.setText(text));
                    }
                    case RESULT_CANCELED -> {
                        binding.textView4.setText("Result: Canceled");
                    }
                    default -> {
                        binding.textView4.setText("Result: Unknown(" + result.getResultCode() + ")");
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        // 次のaまで，ViewBindingの設定
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // a
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 明示的Intent
        binding.buttonA.setOnClickListener(view -> {
            var intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        // 暗黙的Intent
        binding.buttonB.setOnClickListener(view -> {
            var intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://www.yahoo.co.jp/"));
            startActivity(intent);
        });

        // sendButton
        binding.buttonC.setOnClickListener(view -> {
            var text = binding.editTextText.getText().toString();
            var intent = new Intent(this, MainActivity4.class);
            intent.putExtra("title", text);
            startActivity(intent);
        });

        // exec button
        binding.buttonD.setOnClickListener(view -> {
            var intent = new Intent(this, MainActivity4.class);
            getActivityResult.launch(intent);
        });
    }
}