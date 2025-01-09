import { useEffect, useState } from 'react';

const EditTable = () => {
    const [users, setUsers] = useState([]);
    const [currentPage, setCurrentPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);
    const [loading, setLoading] = useState(false);

    //TODO: поменять на веб сокет
    const fetchUsers = async (page) => {
        setLoading(true);
        const jwtToken = localStorage.getItem('jwtToken'); // Получаем JWT из localStorage

        try {
            const response = await fetch(`http://localhost:8080/api/admin/employee/edit?page=${page}&size=10`, {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${jwtToken}`,
                    'Content-Type': 'application/json'
                }
            });

            if (!response.ok) {
                throw new Error('Ошибка при загрузке данных');
            }

            const data = await response.json();
            setUsers(data.content);
            setTotalPages(data.totalPages);
        } catch (error) {
            console.error('Ошибка при загрузке данных:', error);
        } finally {
            setLoading(false);
        }
    };
    useEffect(() => {
        fetchUsers(currentPage);
    }, [currentPage]);

    const handleNext = () => {
        if (currentPage < totalPages - 1) {
            setCurrentPage(currentPage + 1);
        }
    };

    const handlePrev = () => {
        if (currentPage > 0) {
            setCurrentPage(currentPage - 1);
        }
    };

    return (
        <div>
            {loading ? (
                <p>Загрузка...</p>
            ) : (
                <table>
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Login before</th>
                        <th>Login after</th>
                        <th>Name before</th>
                        <th>Name after</th>
                        <th>Role before</th>
                        <th>Role after</th>
                        <th>Pick up point id before</th>
                        <th>Pick up point id after</th>
                        <th>Accept</th>
                        <th>Reject</th>
                    </tr>
                    </thead>
                    <tbody>
                    {users.map((user) => (
                        <tr key={user.before.id}>
                            <td>{user.before.id}</td>
                            <td>{user.before.login}</td>
                            <td>{user.after.login}</td>
                            <td>{user.before.name}</td>
                            <td>{user.after.name}</td>
                            <td>{user.before.role}</td>
                            <td>{user.after.role}</td>
                            <td>{user.before.pick_up_point_id}</td>
                            <td>{user.after.pick_up_point_id}</td>
                            <td>
                                <button onClick={() => handleAccept(user.id)}>Accept</button>
                            </td>
                            <td>
                                <button onClick={() => handleReject(user.id)}>Reject</button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            )}

            <div>
                <button onClick={handlePrev} disabled={currentPage === 0}>
                    Backward
                </button>
                <span>
                    Page {currentPage + 1} of {totalPages}
                </span>
                <button onClick={handleNext} disabled={currentPage === totalPages - 1}>
                    Forward
                </button>
            </div>
        </div>
    );
};

export default EditTable;