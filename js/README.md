# Javascript example for OAuth2 implicit flow

To try out this example:

- Serve these HTML files from any web server running HTTPS on port
  4000. If you can't stand up an HTTPS web server, you can use nginx
  as a reverse proxy to terminate SSL.
- Create a client registration whose redirect URL is the URL of
  `receive_token.html` on that web server (e.g.,
  `https://localhost:4000/receive_token.html`). (See the
  [Examples README][] for an example *app* registration call.)
- Replace the values of `client_id` and `redirect_uri` in the
  `default_token_args` object with the redirect URL and client ID you
  just defined.
- Log into dashboard or do something else that gets you a BC_TOKEN
  cookie, good for the account ID you named in your client
  registration, accessible to a cookie domain containing all the
  servers you wnt to talk to.
- Open up `call_form.html`, put in your account ID and click submit.
- Watch in the debug console as the AJAX call fails because it's not
  allowed yet.  But everything up to that point should have worked.
  In particular Brightcove OAuth should have served you a token.

[Examples README]: ../README.md
