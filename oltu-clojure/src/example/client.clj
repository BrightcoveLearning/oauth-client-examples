(ns example.client
  (:import [java.net URL]
           [org.apache.oltu.oauth2.client OAuthClient URLConnectionClient]
           [org.apache.oltu.oauth2.client.request OAuthClientRequest]
           [org.apache.oltu.oauth2.client.response OAuthJSONAccessTokenResponse]
           [org.apache.oltu.oauth2.common.message.types GrantType]))

(def token-request-url
  "The URL for access token requests."
  "https://oauth.brightcove.com/v3/access_token")

(def client-id
  "The client ID from your client credential."
  "c112eb73-f676-40ee-86fa-14e3d4a79d15")

(def client-secret
  "The client secret from your client credential."
  "uL9z0_oNVDQ0T7Q3hND3H7rKF2q6Qi6wt6pQjWdnils19bHVZeJMGOkXdwWlEZqAzVR70LItekJuXcSWFnN7iQ")

(def account-id
  "The account ID to fetch Analytics info for."
  "12345")

(def oauth-client
  "An Oltu OAuth client object to use to make requests."
  (OAuthClient. (URLConnectionClient.)))

(def token-request
  "A token request message to submit through the oauth-client object and get a token in
  return."
  (.. (OAuthClientRequest/tokenLocation token-request-url)
      (setGrantType GrantType/CLIENT_CREDENTIALS)
      (setClientId client-id)
      (setClientSecret client-secret)
      ;; If you want to set the token scope, (setScope something) here.
      (buildQueryMessage)))

(defn get-token []
  "Yield a fresh access token created by submitting token-request through oauth-client.  (Makes
  an access token request to the server located at token-request-url.)"
  (.getAccessToken
   (.accessToken oauth-client token-request OAuthJSONAccessTokenResponse)))

(defn resource-url
  "Build URL of the resource you want to access using the access
  token.  The value below is for the Analytics resource server."
  [account-id]
  (URL. (str "https://data.brightcove.com/analytics-api/videocloud/account/" account-id "/report?dimensions=video,player")))

(defn resource-cxn []
  "Yield a fresh HttpURLConnection to the resource server, with the appropriate Authorization
  header attached.  (Makes an access token request using (get-token).)"
  (doto (.openConnection (resource-url account-id))
    (.addRequestProperty "Authorization" (str "Bearer " (get-token)))))

(defn authorized-content []
  "Yield the authorized content by requesting a fresh access token and then requesting the
  content using it."
  (slurp (.getInputStream (resource-cxn))))

(defn -main
  "Display analytics info for an account using OAuth2 credentials."
  [& args]
  (println (authorized-content)))
