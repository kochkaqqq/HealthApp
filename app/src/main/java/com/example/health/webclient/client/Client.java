package com.example.health.webclient.client;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.health.PrefManager;
import com.example.health.Profile;
import com.example.health.domain.Train;
import com.example.health.domain.User;
import com.example.health.webclient.client.user.queries.AuthQuery;
import com.example.health.webclient.client.user.queries.UpdateUserScoreCommand;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.*;

public class Client
{
    private static String url = "http://192.168.0.13:5247/";
    private static String endpoint;
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private final OkHttpClient client = getUnsafeOkHttpClient();
    private final Gson gson = new Gson();

    public Client()
    {

    }

    //Commands
    public void addEntity(String endpoint, Object entity, Context context) throws IOException
    {
        String json = gson.toJson(entity);
        RequestBody body = RequestBody.create(json, JSON);

        Request request = new Request.Builder()
                .url(url + endpoint)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }

                if (entity instanceof User) {
                    String body = response.body().string();
                    User u = gson.fromJson(body, User.class);
                    PrefManager.putString(context, "id", u.Id + "");
                    PrefManager.putString(context, "user_name", ((User) entity).Name);
                    PrefManager.putString(context, "email", ((User) entity).Email);
                    PrefManager.putString(context, "score", "0");
                    //PrefManager.putString(context, "subscription_end", u.SubscriptionEnd.toString());
                    Intent intent = new Intent(context, Profile.class);
                    context.startActivity(intent);
                }
            }
        });
    }

    public void AuthUser(String email, String pass, Context context) throws IOException
    {
        AuthQuery query = new AuthQuery();
        query.Email = email;
        query.Password = pass;

        String json = gson.toJson(query);
        RequestBody body = RequestBody.create(json, JSON);

        Request request = new Request.Builder()
                .url(url + "User/AuthUser")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful())
                    throw new IOException("Unexpected code " + response);

                String body = response.body().string();
                User u = gson.fromJson(body, User.class);
                if (Objects.equals(u.Email, ""))
                    throw new IOException("Unexpected code " + response);
                PrefManager.putString(context, "id", u.Id + "");
                PrefManager.putString(context, "user_name", u.Name);
                PrefManager.putString(context, "email", u.Email);
                PrefManager.putString(context, "score", u.Score + "");
                //PrefManager.putString(context, "subscription_end", u.SubscriptionEnd.toString());
                Intent intent = new Intent(context, Profile.class);
                context.startActivity(intent);
            }
        });
    }

    public void AddPointsToUser(int id, int points, Context context) throws IOException {
        UpdateUserScoreCommand command = new UpdateUserScoreCommand();
        command.Id = id;

        String scoreStr = PrefManager.getString(context, "score", "0");
        int score = Integer.parseInt(scoreStr);
        score += points;

        PrefManager.putString(context, "score", score + "");

        command.Score = score;

        String json = gson.toJson(command);
        RequestBody body = RequestBody.create(json, JSON);

        Request request = new Request.Builder()
                .url(url + "User/UpdateUserScore")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful())
                    throw new IOException("Unexpected code " + response);
            }
        });
    }

    public void GetUserList(List<User> result) throws IOException {
        Request request = new Request.Builder()
                .url(url + "User/GetAllUser")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful())
                    throw new IOException("Unexpected code " + response);
                String body = response.body().string();
                List<User> users = gson.fromJson(body, new TypeToken<List<User>>(){}.getType());
                result.addAll(users);
            }
        });
    }

    public List<Train> GetAllPublicTrains(Context context) throws IOException {
        endpoint = "Train/GetAllPublicTrain";
        Request request = new Request.Builder()
                .url(url + endpoint)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String resBody = response.body().string();
                Type trainListType = new TypeToken<List<Train>>(){}.getType();
                return gson.fromJson(resBody, trainListType);
            } else {
                throw new RuntimeException("Request failed with code: " + response.code());
            }
        } catch (IOException e) {
            throw new RuntimeException("Error while making request", e);
        }
    }

    public List<Train> GetAllUserTrains(int id, Context context) throws IOException {
        endpoint = "";
        return new ArrayList<Train>();
    }



    private OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Создание TrustManager, который доверяет всем сертификатам
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }
                    }
            };

            // Установка TrustManager в SSLContext
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new SecureRandom());

            // Создание OkHttpClient с кастомным SSLContext
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier((hostname, session) -> true);

            return builder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    //геттеры и сеттеры
    public String getEndpoint()
    {
        return endpoint;
    }
    public void setEndpoint(String str)
    {
        endpoint = str;
    }

    public Gson getGson() {
        return gson;
    }

    public MediaType getJsonMediaType()
    {
        return JSON;
    }

    public OkHttpClient getClient()
    {
        return client;
    }
}