package by.onliner.news.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import by.onliner.news.R;

public class AuthActivity extends AppCompatActivity implements View.OnClickListener {
    // View
    private EditText mEditTextUsername;
    private EditText mEditTextPassword;
    private Button mButtonAuth;
    private Button mButtonRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_auth);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mEditTextUsername = (EditText) findViewById(R.id.et_auth_username);
        mEditTextPassword = (EditText) findViewById(R.id.et_auth_password);

        mButtonAuth = (Button) findViewById(R.id.bt_auth_account);
        mButtonAuth.setOnClickListener(this);

        mButtonRegistration = (Button) findViewById(R.id.bt_reg_account);
        mButtonRegistration.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_auth_account:
                break;
            case R.id.bt_reg_account:
                break;
            default:
                break;
        }
    }
}
