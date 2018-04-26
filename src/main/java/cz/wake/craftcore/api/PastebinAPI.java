package cz.wake.craftcore.api;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class PastebinAPI {

    private static final String API_URL = "http://pastebin.com/api/api_post.php";
    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String DEFAULT_INV = "\0";

    /**
     * Paste text to Pastebin and generate link!
     * <b>Require: PastebinAPI Key!</b>
     *
     * @param devKey PastebinAPI key
     * @param paste Texts to paste
     * @return Link to pasted text
     * @throws IOException when fails
     */
    public static String postPaste(String devKey, String paste) throws IOException {
        Map<String, String> arguements = new HashMap<>();
        arguements.put("api_option", "paste");
        arguements.put("api_dev_key", devKey);
        arguements.put("api_paste_code", paste);
        String postData = postMap(arguements);
        byte[] postDataB = postData.getBytes("UTF-8");
        HttpURLConnection con = (HttpURLConnection) new URL(API_URL).openConnection();
        con.setDoOutput(true);
        con.setFixedLengthStreamingMode(postDataB.length);
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.connect();
        try (OutputStream os = con.getOutputStream()) {
            os.write(postDataB);
        }
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        con.disconnect();
        return response.toString();
    }

    private static String postPaste(PasteRequest request) throws IOException {
        Map<String, String> arguements = new HashMap<>();
        arguements.put("api_option", "paste");
        arguements.put("api_dev_key", request.getDevKey());
        arguements.put("api_paste_code", request.getPaste());
        if(request.hasUserKey())
        {
            arguements.put("api_user_key", request.getUserKey());
        }
        if(request.hasPasteName())
        {
            arguements.put("api_paste_name", request.getPasteName());
        }
        if(request.hasPasteFormat())
        {
            arguements.put("api_paste_format", request.getPasteFormat());
        }
        if(request.hasPasteState())
        {
            arguements.put("api_paste_private", request.getPasteState() + "");
        }
        if(request.hasPasteExpire())
        {
            arguements.put("api_paste_expire_date", request.getPasteExpire());
        }
        String postData = postMap(arguements);
        byte[] postDataB = postData.getBytes("UTF-8");
        HttpURLConnection con = (HttpURLConnection) new URL(API_URL).openConnection();
        con.setDoOutput(true);
        con.setFixedLengthStreamingMode(postDataB.length);
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.connect();
        try (OutputStream os = con.getOutputStream()) {
            os.write(postDataB);
        }
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        con.disconnect();
        return response.toString();
    }

    private static String postMap(Map<String, String> arguements) throws UnsupportedEncodingException {
        StringJoiner joiner = new StringJoiner("&");
        for (Map.Entry<String, String> entry : arguements.entrySet()) {
            joiner.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "=" + URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return joiner.toString();
    }

    public static class PasteRequest {
        private String devKey;
        private String paste;
        private String userKey = DEFAULT_INV;
        private String pasteName = DEFAULT_INV;
        private String pasteFormat = DEFAULT_INV;
        private int pasteState = -1;
        private String pasteExpire = DEFAULT_INV;

        public PasteRequest(String devKey, String paste) {
            this.devKey = devKey;
            this.paste = paste;
        }

        public String getDevKey() {
            return devKey;
        }

        public String getPaste() {
            return paste;
        }

        public String getUserKey() {
            return userKey;
        }

        public String getPasteName() {
            return pasteName;
        }

        public String getPasteFormat() {
            return pasteFormat;
        }

        public int getPasteState() {
            return pasteState;
        }

        public String getPasteExpire() {
            return pasteExpire;
        }

        public boolean hasUserKey() {
            return userKey != DEFAULT_INV;
        }

        public boolean hasPasteName() {
            return pasteName != DEFAULT_INV;
        }

        public boolean hasPasteFormat() {
            return pasteFormat != DEFAULT_INV;
        }

        public boolean hasPasteState() {
            return pasteState != -1;
        }

        public boolean hasPasteExpire() {
            return pasteExpire != DEFAULT_INV;
        }

        /**
         * PastebinAPI key for pasting.
         * <b>REQUIRED</b>
         *
         * @param userKey Key in string format
         */
        public void setUserKey(String userKey) {
            this.userKey = userKey;
        }

        /**
         * Sets paste name (title)
         *
         * @param pasteName Name of pasted texts
         */
        public void setPasteName(String pasteName) {
            this.pasteName = pasteName;
        }

        /**
         * Pasted format of texts (Example: java, javascript)
         *
         * @param pasteFormat Format of text
         */
        public void setPasteFormat(String pasteFormat) {
            this.pasteFormat = pasteFormat;
        }

        /**
         * Sets state for a pasted texts. Variables:
         * <b>0</b> will be public
         * <b>1</b> will be unlisted
         * <b>2</b> will be private - require Pastebin Premium
         *
         * @param pasteState State
         */
        public void setPasteState(int pasteState) {
            this.pasteState = pasteState;
        }

        /**
         * Expire time of pasted texts.
         * Example: 1H or 1D
         *
         * @param pasteExpire Formated time
         */
        public void setPasteExpire(String pasteExpire) {
            this.pasteExpire = pasteExpire;
        }

        /**
         * Final action to send to Pastebin
         *
         * @return URL of pasted texts
         * @throws IOException when fails
         */
        public String postPaste() throws IOException
        {
            return PastebinAPI.postPaste(this);
        }

    }
}
