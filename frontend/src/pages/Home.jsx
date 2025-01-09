import { useNavigate, useLocation } from 'react-router-dom';
import OrderTable from "../tables/OrderTable.jsx";

function Home() {
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
                        <button onClick={handleGoToAdminPanel}>Go to admin panel</button>
                    </div>
                </header>
            </div>

            <h2>Order table</h2>
            <OrderTable/>
        </div>
    );
}

export default Home;