import { useState } from 'react';

const OrderForm = () => {
    // Состояние для опций для селекта
    const [options, setOptions] = useState([]);

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
                setOptions(data.content); // Обновляем состояние options
            } else {
                console.error('Failed to fetch products');
            }
        } catch (error) {
            console.error('Error:', error);
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

    // Обработчик отправки формы
    const handleSubmit = async (e) => {
        e.preventDefault();

        // Отправка данных на сервер
        try {
            const response = await fetch('http://localhost:8080/api/order', {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${jwtToken}`,
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(rows),
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
                            {/*<option value="">Select an option</option>*/}
                            {options.map((option, i) => (
                                // Предположим, что 'option' — это объект с полем 'name'
                                <option key={i}
                                        value={option.id}> {/* Здесь id, но можно использовать другой уникальный ключ */}
                                    {option.name} {/* Здесь мы отображаем свойство объекта */}
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

                <div style={{ marginTop: '20px' }}>
                    <button type="submit">Submit Order</button>
                </div>
            </form>
        </div>
    );
};

export default OrderForm;
