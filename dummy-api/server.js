const express = require('express');
const { auth } = require('express-oauth2-jwt-bearer');

const app = express();

// ✅ Replace with your Okta domain
const issuer = 'https://dev-86087318.okta.com/oauth2/default';

const checkJwt = auth({
  issuerBaseURL: issuer,
  audience: 'api://default', // Your Okta audience (default client audience)
});

// 🔒 Protect this route
app.get('/dummy/users', checkJwt, (req, res) => {
  res.json(['Alice', 'Bob', 'Charlie']);
});

app.listen(3000, () => {
  console.log('✅ Dummy API running on http://localhost:3000');
});
