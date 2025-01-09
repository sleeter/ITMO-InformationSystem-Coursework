import { useNavigate, useLocation } from 'react-router-dom';
import OrderTable from "../tables/OrderTable.jsx";
import {useState} from "react";

function Home() {
    const [editingUser, setEditingUser] = useState(null);
    const location = useLocation();
    const navigate = useNavigate();
    const username = location.state?.login || 'Guest';
    const token = localStorage.getItem('jwtToken');

    const getRoleFromToken = () => {
        if (!token) return null;
        const decodedToken = JSON.parse(atob(token.split('.')[1]));
        return decodedToken?.role || null;
    };

    const role = getRoleFromToken();

    //TODO: GET /api/employee/{id}
    const fetchUserDetails = async () => {
        const jwtToken = localStorage.getItem('jwtToken');
        const decodedToken = JSON.parse(atob(jwtToken.split('.')[1]));
        const id = decodedToken.id;
        try {
            const response = await fetch(`http://localhost:8080/api/employee/${id}`, {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${jwtToken}`,
                    'Content-Type': 'application/json'
                }
            });

            if (!response.ok) {
                throw new Error('Не удалось загрузить информацию о координате');
            }

            const user = await response.json();
            setEditingUser(user);
        } catch (error) {
            console.error('Ошибка при загрузке данных координаты:', error);
        }
    };

    const handleEditSubmit = async () => {
        editingUser.deleted = false
        const data = { login: editingUser.login, password: editingUser.password, role: editingUser.role,
            name: editingUser.name, pick_up_point_id: editingUser.pick_up_point_id, deleted: editingUser.deleted };

        const jwtToken = localStorage.getItem('jwtToken');
        try {
            const response = await fetch(`http://localhost:8080/api/employee/update/${editingUser.id}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${jwtToken}`,
                },
                body: JSON.stringify(data),
            });

            if (!response.ok) {
                throw new Error('Ошибка при отправке данных на сервер');
            }

            setEditingUser(null);
        } catch (error) {
            alert(error.message);
        }
    };

    const handleLogout = () => {
        localStorage.removeItem('jwtToken');
        navigate('/auth/login');
    };

    const handleGoToAdminPanel = () => {
        if (role !== 'ROLE_admin') {
            alert('Только пользователь с ролью "Admin" может перейти в панель администратора.');
            return;
        }
        navigate('/home/admin');
    };

    return (
        <div>
            <div>
                <header>
                    <div>
                        <span>Welcome, {username}!</span>
                    </div>
                    <div>
                        <button onClick={handleLogout}>Logout</button>
                        <button onClick={() => fetchUserDetails()}>Edit profile</button>
                        {role === 'ROLE_admin' && (<button onClick={handleGoToAdminPanel}>Go to admin panel</button>)}
                    </div>
                </header>
            </div>

            <h2>Order table</h2>
            <OrderTable/>

            {editingUser && (
                <div style={modalStyles.overlay}>
                    <div style={modalStyles.container}>
                        <h3>User</h3>
                        <label>
                            Login:
                            <input
                                type="text"
                                value={editingUser.login}
                                onChange={(e) => setEditingUser({...editingUser, login: e.target.value})}
                            />
                        </label>
                        <label>
                            Role:
                            <input
                                type="text"
                                value={editingUser.role}
                                onChange={(e) => setEditingUser({...editingUser, role: e.target.value})}
                            />
                        </label>
                        <label>
                            Name:
                            <input
                                type="text"
                                value={editingUser.name}
                                onChange={(e) => setEditingUser({...editingUser, name: e.target.value})}
                            />
                        </label>
                        <label>
                            Pick up point id:
                            <input
                                type="number"
                                value={editingUser.number}
                                onChange={(e) => setEditingUser({...editingUser, pick_up_point_id: e.target.value})}
                            />
                        </label>
                        <button type="button" onClick={handleEditSubmit}>
                            Save changes
                        </button>
                        <button type="button" onClick={() => setEditingUser(null)}>
                            Cancel
                        </button>
                    </div>
                </div>
            )}
        </div>
    );
}

const modalStyles = {
    overlay: {
        position: 'fixed',
        top: 0,
        left: 0,
        width: '100%',
        height: '100%',
        backgroundColor: 'rgba(0, 0, 0, 0.5)',
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        zIndex: 1000,
    },
    container: {
        backgroundColor: 'white',
        padding: '20px',
        borderRadius: '5px',
        width: '300px',
        textAlign: 'center',
    },
};

export default Home;