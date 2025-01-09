import {Navigate, Outlet} from 'react-router-dom';

const ProtectedRoute = () => {
    const token = localStorage.getItem('jwtToken');
    if (!token) {
        return <Navigate to="/auth/login" />;
    }

    return <Outlet />;
};

export default ProtectedRoute;