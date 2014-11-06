# Client examples

The client library, SDK and sample code are located in this directory.

Client here refers to
[an application making protected resource requests][client-def] on
behalf of the resource owner and with its authorization.

[client-def]: http://tools.ietf.org/html/draft-ietf-oauth-v2-25#section-1.1

## Credential client registration

Many of these examples use a client credential registration with a
scope authorizing read access to the Analytics API. Such a
registration might be created as follows:

```sh
# With $BC_TOKEN (browser session cookie) and $ACCOUNT_ID (publisher account) variables:
curl -H "Authorization: BC_TOKEN $BC_TOKEN" -X POST -d 'name=example-client&maximum_scope=[{"identity":{"type":"video-cloud-account","account-id":'$ACCOUNT_ID'},"operations":["video-cloud/analytics/read"]}]' https://oauth.brightcove.com/v3/client_credentials
```
