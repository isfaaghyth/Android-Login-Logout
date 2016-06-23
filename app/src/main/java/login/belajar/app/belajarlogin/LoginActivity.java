package login.belajar.app.belajarlogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private JsonObject a;
    private EditText txtUsername;
    private EditText txtPassword;
    private SessionManager session;
    private SQLiteHandler db;

    private String nama, pass;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(this);
        db = new SQLiteHandler(getApplicationContext());
        session = new SessionManager(getApplicationContext());
        if (session.isLoggedIn()) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                nama = txtUsername.getText().toString().trim();
                pass = txtPassword.getText().toString().trim();
                if (!nama.isEmpty() && !pass.isEmpty()) {
                    ParsingJson("http://dev.daeng.id/all.php?nama=" + nama);
                }
                break;
        }
    }

    private void ParsingJson(String url) {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {;
            @Override
            public void onResponse(String response) {
                Log.d("Isfa", response);
                GsonBuilder builder = new GsonBuilder();
                Gson mGson = builder.create();
                a = mGson.fromJson(response, JsonObject.class);

                if (nama.equals(a.login.get(0).nama.toString())) {
                    if (pass.equals(a.login.get(0).password.toString())) {
                        session.setLogin(true);
                        db.addUser(nama, pass);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(),"Password is wrong.",Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),"Username is wrong",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Check your Internet connection",Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }
}
