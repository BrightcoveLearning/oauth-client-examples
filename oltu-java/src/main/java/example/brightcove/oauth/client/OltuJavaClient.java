package example.brightcove.oauth.client;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Example of the OAuth client credentials flow using the Apache Oltu OAuth2 client.
 */
public class OltuJavaClient {
    /**
     * URL for requesting OAuth access tokens.
     */
    public static final String TOKEN_REQUEST_URL = "https://oauth.brightcove.com/v4/access_token";

    /**
     * Client ID of your client credential.  Change this to match whatever credential you have created.
     */
    public static final String CLIENT_ID = "YOUR_CLIENT_ID";

    /**
     * Client secret of your client credential.  Change this to match whatever credential you have created.
     */
    public static final String CLIENT_SECRET =
            "YOUR_CLIENT_SECRET";

    /**
     * Account on which you want to request a resource. Change this to match the account you want to
     * retrieve resources on.
     */
    public static final String ACCOUNT_ID = "YOUR_ACCOUNT_ID";

    /**
     * URL from which you are going to request a resource.  The example below is for the Analytics
     * resource server. :account-id will be replaced with {@link ACCOUNT_ID} below.
     */
    public static final String RESOURCE_URL_TPL =
            "https://analytics.api.brightcove.com/v1/data?accounts=:account-id&dimensions=video";

    /**
     * Request a fresh access token using the given client ID, client secret, and token request URL,
     * then request the resource at the given resource URL using that access token, and get the resource
     * content.  If an exception is thrown, print the stack trace instead.
     *
     * @param args Command line arguments are ignored.
     */
    public static void main(String[] args) {
        try {
            OAuthClient client = new OAuthClient(new URLConnectionClient());

            OAuthClientRequest request =
                    OAuthClientRequest.tokenLocation(TOKEN_REQUEST_URL)
                    .setGrantType(GrantType.CLIENT_CREDENTIALS)
                    .setClientId(CLIENT_ID)
                    .setClientSecret(CLIENT_SECRET)
                    // .setScope() here if you want to set the token scope
                    .buildQueryMessage();

            String token =
                    client.accessToken(request, OAuthJSONAccessTokenResponse.class)
                    .getAccessToken();

            String resourceUrl = RESOURCE_URL_TPL.replace(":account-id", ACCOUNT_ID);
            HttpURLConnection resource_cxn =
                    (HttpURLConnection)(new URL(resourceUrl).openConnection());
            resource_cxn.addRequestProperty("Authorization", "Bearer " + token);

            InputStream resource = resource_cxn.getInputStream();

            // Do whatever you want to do with the contents of resource at this point.

            BufferedReader r = new BufferedReader(new InputStreamReader(resource, "UTF-8"));
            String line = null;
            while ((line = r.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception exn) {
            exn.printStackTrace();
        }
    }
}
