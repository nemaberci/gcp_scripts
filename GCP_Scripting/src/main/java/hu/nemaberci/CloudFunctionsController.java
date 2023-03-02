package hu.nemaberci;

import java.io.IOException;
import java.time.Instant;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.IdTokenCredentials;
import com.google.auth.oauth2.IdTokenProvider;

public class CloudFunctionsController {

    public static Instant callFunc(String serviceUrl, String audience) throws IOException {
            
        GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();
        if (!(credentials instanceof IdTokenProvider)) {
            throw new IllegalArgumentException("Credentials are not of instance IdTokenProvider");
        }

        IdTokenCredentials tokenCredential = IdTokenCredentials.newBuilder()
            .setIdTokenProvider((IdTokenProvider) credentials)
            .setTargetAudience(audience)
            .build();

        GenericUrl genericUrl = new GenericUrl(serviceUrl);
        HttpCredentialsAdapter adapter = new HttpCredentialsAdapter(tokenCredential);
        HttpTransport transport = new NetHttpTransport();
        HttpRequest request = transport.createRequestFactory(adapter).buildGetRequest(genericUrl);
        var response = request.execute().parseAsString();
        System.out.println(response);
        return Instant.parse(response);

    }
    
}
