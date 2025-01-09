import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

function Login() {
    const [login, setLogin] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();

    const handleLogin = async () => {
        const response = await fetch('http://localhost:8080/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ login, password }),
        });

        if (response.ok) {
            const data = await response.json();
            const { token } = data;
            localStorage.setItem('jwtToken', token);
            navigate('/home', { state: { login } });
        } else {
            alert('Login failed');
        }
    };

    return (
        <div>
            <h2>Login Page</h2>
            <input
                type="text"
                placeholder="Login"
                value={login}
                onChange={(e) => setLogin(e.target.value)}
            />
            <input
                type="password"
                placeholder="Password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
            />
            <button onClick={handleLogin}>Login</button>
            <p>
                Don&#39;t have an account? <a href="/auth/register">Register</a>
            </p>
        </div>
    );
}

export default Login;