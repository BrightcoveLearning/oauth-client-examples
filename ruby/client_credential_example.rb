require 'oauth2'

# Replace this with a locally generated client ID and client secret
# Select the analytics API as what you want to grant access to
client_id = '8a3809bd-e9e5-487e-abe9-1d3a54a1febb'
client_secret = '-1LbGuXeusgI4HhUliY16l7jupPte29K3oIGU7xY9CCqgyw6w0t6rBdgazvj6P1ho_XRJo8pPZo6UFXB3qqMKw'

client = OAuth2::Client.new(client_id, client_secret,
                            site: 'https://data.brightcove.com/',
                            token_url: 'https://oauth.brightcove.com/v4/access_token')
token = client.client_credentials.get_token

# replace 12345 with the account_id
puts token.get('/analytics-api/videocloud/account/12345/report?dimensions=video,player').body # or whatever request you want
