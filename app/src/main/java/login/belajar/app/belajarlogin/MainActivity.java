package login.belajar.app.belajarlogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private Button btnLogout;
    private SQLiteHandler db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogout = (Button) findViewById(R.id.btnLogout);

        db = new SQLiteHandler(getApplicationContext());
        session = new SessionManager(getApplicationContext());
        if (!session.isLoggedIn()) {
            logoutUser();
        }
//        HashMap<String, String> user = db.getUserDetails();

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutUser();
            }
        });
    }

    private void logoutUser() {
        session.setLogin(false);
        db.deleteUsers();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
