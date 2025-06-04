const express = require('express');
const { auth } = require('express-oauth2-jwt-bearer');
const jwt = require('jsonwebtoken');

const app = express();

app.use(express.json());

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

let users = [{ id: 1, name: 'Alice' }, { id: 2, name: 'Bob' }, { id: 3, name: 'Charlie' }];
app.delete('/dummy/users/:userid', (req, res) => {
  const userId = req.params.userid;
  let filteredUsers = users.filter(user => user.id != userId);
  users = filteredUsers
  res.status(202).json({ deletedUserId: userId });
});

app.head('/dummy/users/:userid', (req, res) => {
  res.status(200).header("ContentType", "application/json").send();
});

app.listen(3000, () => {
  console.log('✅ Dummy API running on http://localhost:3000');
});

const loginUsers = [
  { id: 1, username: 'admin', password: 'pass123' },
  { id: 2, username: 'user1', password: 'pass456' },
];


const JWT_SECRET = 'your-secret-key';

app.post('/auth', (req, res) => {
  const { username, password } = req.body;
  const user = loginUsers.find(u => u.username === username && u.password === password);
  if (!user) {
    return res.status(401).json({ message: 'Invalid credentials' });
  }

  const token = jwt.sign({ id: user.id, username: user.username }, JWT_SECRET, { expiresIn: '1h' });
  res.json({ token });
});

function authenticateToken(req, res, next) {
  const authHeader = req.headers['authorization'];
  const token = authHeader?.split(' ')[1];

  if (!token) return res.status(401).json({ message: 'Missing token' });

  jwt.verify(token, JWT_SECRET, (err, user) => {
    if (err) return res.status(403).json({ message: 'Invalid token' });

    req.user = user; // user info from token
    next();
  });
}

// 👥 Protected route — GET /users
app.get('/users', authenticateToken, (req, res) => {
  // Don't return passwords in response
  const safeUsers = loginUsers.map(({ password, ...rest }) => rest);
  res.status(200).json(safeUsers);
});

const interval = 10000;
let isRateLimited = false;
app.get("/test429", (req, res) => {
  if (isRateLimited)
    return res.status(429).json({ message: "Too Many requests" })

  isRateLimited = true;
  res.status(200).json({ message: "Your request is processed" });
  setTimeout(() => {
    isRateLimited = false;
  }, interval)

});

let retry = 0;
app.get("/test500", (req, res) => {
  if (retry >= 2) {
    return res.status(200).json({ message: "Success After Retry" })
  }
  res.status(500 + retry).json();
  retry++;
});