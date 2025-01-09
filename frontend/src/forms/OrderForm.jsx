import { useState } from 'react';

const OrderForm = () => {
    // Состояние для опций для селекта
    const [options, setOptions] = useState([]);
    const [paymentMethod, setPaymentMethod] = useState('card'); // Добавляем состояние для способа оплаты

    const jwtToken = localStorage.getItem('jwtToken');

    // Функция для получения продуктов
    const fetchProducts = async () => {
        try {
            const response = await fetch('http://localhost:8080/api/product', {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${jwtToken}`,
                    'Content-Type': 'application/json'
                },
            });

            if (response.ok) {
                const data = await response.json();
                // console.log(data)
                setOptions(data); // Ensure it's always an array
            } else {
                console.error('Failed to fetch products');
                setOptions([]); // Reset options to an empty array on failure
            }
        } catch (error) {
            console.error('Error:', error);
            setOptions([]); // Reset options to an empty array on error
        }
    };


    // Состояние для строк формы
    const [rows, setRows] = useState([]);

    // Добавить строку
    const addRow = () => {
        if (rows.length === 0) {
            fetchProducts(); // Получить данные только если строк нет
        }
        setRows([...rows, { selectedOption: '', quantity: 1 }]);
    };

    // Удалить строку
    const removeRow = (index) => {
        const newRows = rows.filter((_, i) => i !== index);
        setRows(newRows);
    };

    // Обработчик изменения значения в селекте
    const handleSelectChange = (e, index) => {
        const updatedRows = [...rows];
        updatedRows[index].selectedOption = e.target.value;
        setRows(updatedRows);
    };

    // Обработчик изменения значения в поле ввода количества
    const handleQuantityChange = (e, index) => {
        const updatedRows = [...rows];
        updatedRows[index].quantity = parseInt(e.target.value, 10) || 0; // Ограничиваем целыми числами
        setRows(updatedRows);
    };

    // Обработчик изменения выбранного способа оплаты
    const handlePaymentMethodChange = (e) => {
        setPaymentMethod(e.target.value);
    };

    // Обработчик отправки формы
    const handleSubmit = async (e) => {
        e.preventDefault();

        let products = [];
        rows.forEach((row, i) => {
            // Для каждого элемента в rows создаём объект в products
            products[i] = {
                product_id: parseInt(row.selectedOption, 10), // Преобразуем selectedOption в число
                count: row.quantity // Приравниваем количество
            };
        });

        const customer_id = 0; // Пример customer_id

        let size = 0;
        let total_price = 0;

        products.forEach((product) => {
            // Находим соответствующий элемент в options
            const option = options.find(o => o.id === product.product_id);

            if (option) {  // Если найдено совпадение
                // Увеличиваем size и total_price
                size += option.size * product.count;
                total_price += option.price * product.count;
            }
        });

        const pick_up_point_id = 0; // Пример pick_up_point_id
        let payment_id = paymentMethod === 'card' ? 1 : 0; // Соответствующее значение для способа оплаты

        console.log(JSON.stringify({
            customer_id: customer_id,
            size: size,
            pick_up_point_id: pick_up_point_id,
            total_price: total_price,
            payment_id: payment_id,
            products: products
        }));

        // Отправка данных на сервер
        try {
            const response = await fetch('http://localhost:8080/api/order', {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${jwtToken}`,
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    customer_id: customer_id,
                    size: size,
                    pick_up_point_id: pick_up_point_id,
                    total_price: total_price,
                    payment_id: payment_id,
                    products: products
                }),
            });

            if (response.ok) {
                console.log('Order submitted successfully');
            } else {
                console.error('Failed to submit order');
            }
        } catch (error) {
            console.error('Error:', error);
        }
    };

    return (
        <div>
            <form onSubmit={handleSubmit}>
                {rows.map((row, index) => (
                    <div key={index} style={{ display: 'flex', marginBottom: '10px' }}>
                        <select
                            value={row.selectedOption}
                            onChange={(e) => handleSelectChange(e, index)}
                            style={{marginRight: '10px'}}
                        >
                            <option value="">Select an option</option>
                            {options.map((option, i) => ( // Ensure options is always an array
                                <option key={i} value={option.id}>
                                    {option.name}
                                </option>
                            ))}
                        </select>


                        <input
                            type="number"
                            value={row.quantity}
                            onChange={(e) => handleQuantityChange(e, index)}
                            min="1"
                            style={{marginRight: '10px', width: '80px'}}
                        />

                        <button type="button" onClick={() => removeRow(index)}>-</button>
                    </div>
                ))}

                <button type="button" onClick={addRow}>
                    +
                </button>

                {/* Добавляем новый селект для выбора способа оплаты */}
                <div style={{ marginTop: '20px' }}>
                    <label htmlFor="paymentMethod">Payment Method: </label>
                    <select
                        id="paymentMethod"
                        value={paymentMethod}
                        onChange={handlePaymentMethodChange}
                    >
                        <option value="card">Card</option>
                        <option value="cash">Cash</option>
                    </select>
                </div>

                <div style={{ marginTop: '20px' }}>
                    <button type="submit">Submit Order</button>
                </div>
            </form>
        </div>
    );
};

export default OrderForm;
