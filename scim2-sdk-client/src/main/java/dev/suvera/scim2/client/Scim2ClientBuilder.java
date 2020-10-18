package dev.suvera.scim2.client;

import dev.suvera.scim2.schema.ex.ScimException;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

/**
 * author: suvera
 * date: 10/17/2020 2:26 PM
 */
@SuppressWarnings({"RedundantThrows", "FieldCanBeLocal", "unused"})
public class Scim2ClientBuilder {
    private final String endPoint;
    private final OkHttpClient.Builder builder;

    public Scim2ClientBuilder(String endPoint) {
        this.endPoint = endPoint;
        builder = new OkHttpClient.Builder()
                .connectTimeout(1200000, TimeUnit.SECONDS)
                .readTimeout(1200000, TimeUnit.SECONDS);
    }

    public Scim2Client build() throws ScimException {
        return new Scim2ClientImpl(endPoint, builder.build());
    }

    public Scim2ClientBuilder usernamePassword(String username, String password) {
        builder.authenticator(new BasicAuthenticator(username, password));
        return this;
    }

    public Scim2ClientBuilder bearerToken(String token) {
        builder.authenticator(new BearerAuthenticator(token));
        return this;
    }

    public Scim2ClientBuilder allowSelfSigned(boolean flag) {
        if (!flag) {
            return this;
        }

        try {
            TrustManager trustAll = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType)
                        throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType)
                        throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManager[] trustManagers = new TrustManager[]{trustAll};

            sslContext.init(null, trustManagers, new SecureRandom());
            builder.sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustManagers[0]);
            builder.hostnameVerifier((hostname, session) -> true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    @SuppressWarnings("RedundantThrows")
    private static class BasicAuthenticator implements Authenticator {
        private final String username;
        private final String password;

        public BasicAuthenticator(String username, String password) {
            this.username = username;
            this.password = password;
        }

        @Nullable
        @Override
        public Request authenticate(@Nullable Route route, @NotNull Response response) throws IOException {
            String credential = Credentials.basic(username, password);
            return response.request()
                    .newBuilder()
                    .header("Authorization", credential).build();
        }
    }

    @SuppressWarnings("RedundantThrows")
    private static class BearerAuthenticator implements Authenticator {
        private final String token;

        public BearerAuthenticator(String token) {
            this.token = token;
        }

        @Nullable
        @Override
        public Request authenticate(@Nullable Route route, @NotNull Response response) throws IOException {
            return response.request().newBuilder()
                    .header("Authorization", "Bearer " + token)
                    .build();
        }
    }
}
