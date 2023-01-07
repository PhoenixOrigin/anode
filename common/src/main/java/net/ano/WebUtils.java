package net.ano;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class WebUtils {

    public static String postAPI(URL url, String json) throws IOException {
        URLConnection con = url.openConnection();
        HttpURLConnection http = (HttpURLConnection) con;
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        byte[] out = json.getBytes(StandardCharsets.UTF_8);
        int length = out.length;
        http.setFixedLengthStreamingMode(length);
        http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        http.connect();
        try (OutputStream os = http.getOutputStream()) {
            os.write(out);
        }
        return new String(http.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    }

    public static JsonObject readJsonFromUrl(String url) {
        try (InputStream is = new URL(url).openStream()) {
            String jsonText = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            return JsonParser.parseString(jsonText).getAsJsonObject();
        } catch (IOException exception) {
            anode.logger.severe("[anode] Failed to read json");
        }
        return null;
    }

}
