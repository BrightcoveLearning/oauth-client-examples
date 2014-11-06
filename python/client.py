# Example Python client using sanction:
# https://github.com/demianbrecht/sanction
#
# $ PYTHONPATH=path/to/sanction_lib_base/ python ./client.py
#
# Warning: the python-oauth2 client, github.com/simplegeo/python-oauth2, does not actually
# support current drafts of the oauth2 flow.

from sanction import Client

# The URL for access token requests
token_request_url = "https://oauth.brightcove.com/v3/access_token"

# Client ID and secret

# Your Client ID from your client credential
client_id = "762df048-1d42-468c-9d18-f023d524f94f"

# Your Client Secret from your client credential
client_secret = "s728iZUVU3YbBSJC4to_9UhYgVrxIVvIQL3OkypMSOudWg02lZk3tNDbPESUAiup0uJV_rBinbyQ70Vh7FSWLA"

# The base URL of the resource server
# The URL below is for the example resource server
resource_base = "https://data.brightcove.com/"

# Create the OAuth2 client object
client = Client(token_endpoint = token_request_url,
                resource_endpoint = resource_base,
                client_id = client_id,
                client_secret = client_secret)

# Request the access token into the client object
client.request_token(grant_type = 'client_credentials')

# Now make the resource request
# The path below is for the example resource server
print client.request('/analytics-api/videocloud/account/12345/report?dimensions=video,player')
