package cz.wake.craftcore.utils.mojang;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URL;

public class MojangAPI {

    /**
     * Get the UUID from the MojangAPI
     *
     * @param name The name of the player who's UUID you wanna get
     * @return The UUID of the player you defined
     */
    @SuppressWarnings("deprecation")
    public static String getUUID(String name) {
        String url = "https://api.mojang.com/users/profiles/minecraft/" + name;
        try {
            String UUIDJson = IOUtils.toString(new URL(url));
            if (UUIDJson.isEmpty()) return "invalid";
            JSONObject UUIDObject = (JSONObject) JSONValue.parseWithException(UUIDJson);
            return UUIDObject.get("id").toString();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return "error";
    }

    /**
     * Know if the player exists in the Mojang database
     *
     * @param name Then name of the player that you wanna know if he exists
     * @return True or false
     */
    @SuppressWarnings("deprecation")
    public static boolean doesPlayerExist(String name) {
        String url = "https://api.mojang.com/users/profiles/minecraft/" + name;
        try {
            String UUIDJson = IOUtils.toString(new URL(url));
            return (UUIDJson.isEmpty());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;

    }
}
