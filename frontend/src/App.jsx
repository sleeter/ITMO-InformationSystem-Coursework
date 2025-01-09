import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Login from "./pages/Login.jsx";
import Home from "./pages/Home.jsx";
import Register from "./pages/Register.jsx";
import Admin from "./pages/Admin.jsx";

function App() {
  return (
      <Router>
          <div>
              <Routes>
                  <Route path="/auth/login" element={<Login />} />
                  <Route path="/auth/register" element={<Register />} />
                  <Route path="/home" element={<Home />} />
                  <Route path="/home/admin" element={<Admin />} />
                  <Route path="/" element={<Login />} />
              </Routes>
          </div>
      </Router>
  )
}

export default App
