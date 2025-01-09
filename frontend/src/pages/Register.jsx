import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

function Register() {
    const [login, setLogin] = useState('');
    const [password, setPassword] = useState('');
    const [role, setRole] = useState('')
    const [name, setName] = useState('')
    const [pick_up_point_id, setPickUpPointId] = useState('')
    const navigate = useNavigate();
    const deleted = false

    const handleRegister = async () => {
        const response = await fetch('http://localhost:8080/auth/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ login, password, role, name, pick_up_point_id, deleted }),
        });

        if (response.ok) {
            const data = await response.json();
            const { token } = data;
            localStorage.setItem('jwtToken', token);
            navigate('/home', { state: { login } });
        } else {
            alert('Registration failed');
        }
    };

    return (
        <div>
            <h2>Register Page</h2>
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
            <input
                type="text"
                placeholder="Role"
                value={role}
                onChange={(e) => setRole(e.target.value)}
            />
            <input
                type="text"
                placeholder="Name"
                value={name}
                onChange={(e) => setName(e.target.value)}
            />
            <input
                type="number"
                placeholder="Pick up point id"
                value={pick_up_point_id}
                onChange={(e) => setPickUpPointId(e.target.value)}
            />
            <button onClick={handleRegister}>Register</button>
            <p>
                Already have an account? <a href="/auth/login">Login</a>
            </p>
        </div>
    );
}

export default Register;