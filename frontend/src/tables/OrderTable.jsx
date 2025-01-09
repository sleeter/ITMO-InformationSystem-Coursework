import { useEffect, useState } from 'react';

const OrderTable = () => {
    const [orders, setOrders] = useState([]);
    const [currentPage, setCurrentPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);
    const [loading, setLoading] = useState(false);

    //TODO: поменять на веб сокет
    const fetchOrders = async (page) => {
        setLoading(true);
        const jwtToken = localStorage.getItem('jwtToken');

        try {
            const response = await fetch(`http://localhost:8080/api/order?page=${page}&size=10`, {
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
            setOrders(data.content);
            setTotalPages(data.totalPages);
        } catch (error) {
            console.error('Ошибка при загрузке данных:', error);
        } finally {
            setLoading(false);
        }
    };
    useEffect(() => {
        fetchOrders(currentPage);
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
                        <th>Customer</th>
                        <th>Size</th>
                        <th>Date</th>
                        <th>Status</th>
                        <th>Total price</th>
                    </tr>
                    </thead>
                    <tbody>
                    {orders.map((order) => (
                        <tr key={order.id}>
                            <td>{order.id}</td>
                            <td>{order.customer.name}</td>
                            <td>{order.size}</td>
                            <td>{order.date}</td>
                            <td>{order.status}</td>
                            <td>{order.totalPrice}</td>
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

export default OrderTable;