package net.savagedev.namemcrewards.common.namemc;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import net.savagedev.namemcrewards.common.namemc.event.NameMcApiListener;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class NameMCAPI {
    private static final String BASE_URL = "https://api.namemc.com/server/%s/likes";
    private static final JsonParser PARSER = new JsonParser();

    static final Set<NameMcApiListener> LISTENERS = new HashSet<>();

    public static void subscribe(NameMcApiListener event) {
        NameMCAPI.LISTENERS.add(event);
    }

    public static void forceEvent(EventType type, UUID player) {
        for (NameMcApiListener listener : NameMCAPI.LISTENERS) {
            if (type == EventType.DISLIKE) {
                listener.onDislike(player);
            } else {
                listener.onLike(player);
            }
        }
    }

    public static CompletableFuture<Set<UUID>> getLikes(String address) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                HttpsURLConnection connection = (HttpsURLConnection) new URL(String.format(BASE_URL, address)).openConnection();
                connection.setRequestProperty("User-Agent", "NameMC Rewards/1.0.0");
                connection.setRequestMethod("GET");

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                    JsonArray array = PARSER.parse(reader).getAsJsonArray();
                    Set<UUID> likes = new HashSet<>();

                    array.forEach(uuid -> likes.add(UUID.fromString(uuid.getAsString())));

                    return likes;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return new HashSet<>();
        });
    }

    public enum EventType {
        LIKE, DISLIKE
    }
}
