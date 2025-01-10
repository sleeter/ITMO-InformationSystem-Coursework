import { Html5Qrcode } from "html5-qrcode";
import { useState, useEffect } from "react";
// import "./App.css";

function QRScanner() {
    const [isEnabled, setEnabled] = useState(false);
    const [qrMessage, setQrMessage] = useState("");

    useEffect(() => {
        const config = { fps: 10, qrbox: { width: 400, height: 400 } };

        const html5QrCode = new Html5Qrcode("qrCodeContainer");

        const qrScannerStop = () => {
            if (html5QrCode && html5QrCode.isScanning) {
                html5QrCode
                    .stop()
                    .then(() => console.log("Scanner stop"))
                    .catch(() => console.log("Scanner error"));
            }
        };

        const qrCodeSuccess = async (decodedText) => {
            setQrMessage(decodedText);

            const jwtToken = localStorage.getItem('jwtToken'); // Получаем JWT из localStorage
            try {
                const response = await fetch(decodedText, {
                    method: 'PUT',
                    headers: {
                        'Authorization': `Bearer ${jwtToken}`,
                        'Content-Type': 'application/json'
                    }
                });
                if (!response.ok) {
                    throw new Error('Ошибка при загрузке данных');
                }
            } catch (error) {
                console.error('Ошибка при загрузке данных:', error);
            }

            setEnabled(false);
        };

        if (isEnabled) {
            html5QrCode.start({ facingMode: "environment" }, config, qrCodeSuccess);
            setQrMessage("");
        } else {
            qrScannerStop();
        }

        return () => {
            qrScannerStop();
        };
    }, [isEnabled]);

    return (
        <div className="scaner">
            <div id="qrCodeContainer" />
            {qrMessage && <div className="qr-message"><a>Request send to: </a>{qrMessage}</div>}
            <button className="start-button" onClick={() => setEnabled(!isEnabled)}>
                {isEnabled ? "Off" : "On"}
            </button>
        </div>
    );
}

export default QRScanner;