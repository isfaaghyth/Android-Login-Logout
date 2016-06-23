package login.belajar.app.belajarlogin;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by Isfahani on 27-Mei-16.
 */

public class JsonObject {
    @SerializedName("login")
    List<Results> login;

    public class Results {
        @SerializedName("nama")
        public String nama;

        @SerializedName("password")
        public String password;
    }
}
