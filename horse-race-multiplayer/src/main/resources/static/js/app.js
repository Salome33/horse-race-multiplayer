let socket = null;
let currentUserId = null;

function initSocket() {
    if (socket && socket.readyState === WebSocket.OPEN) {
        return;
    }

    const protocol = window.location.protocol === "https:" ? "wss" : "ws";
    socket = new WebSocket(`${protocol}://${window.location.host}/race`);

    socket.onopen = function () {
        setStatus("✅ Conexión WebSocket establecida.");
    };

    socket.onmessage = function (event) {
        const data = JSON.parse(event.data);

        moveHorse("horse1", data.h1);
        moveHorse("horse2", data.h2);
        moveHorse("horse3", data.h3);
        moveHorse("horse4", data.h4);

        if (data.winner) {
            setStatus(`🏆 Ganador de la carrera: ${data.winner}`);
        } else {
            setStatus("🏇 La carrera está en curso...");
        }
    };

    socket.onclose = function () {
        setStatus("⚠️ Conexión cerrada. Si es necesario, recarga la página.");
    };

    socket.onerror = function () {
        setStatus("❌ Error en la conexión WebSocket.");
    };
}

function setStatus(message) {
    document.getElementById("statusBox").innerText = message;
}

function moveHorse(id, position) {
    const horse = document.getElementById(id);
    horse.style.left = position + "px";
}

async function registerUser() {
    const username = document.getElementById("usernameInput").value.trim();

    if (!username) {
        setStatus("⚠️ Debes escribir un nombre de usuario.");
        return;
    }

    const response = await fetch("/users/register", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ username: username })
    });

    const user = await response.json();

    currentUserId = user.id;
    document.getElementById("currentUserId").innerText = user.id;
    document.getElementById("currentUsername").innerText = user.username;
    document.getElementById("currentPoints").innerText = user.points;

    setStatus(`✅ Usuario registrado: ${user.username}. Recibió 1000 puntos iniciales.`);
}

async function buyPoints() {
    if (!currentUserId) {
        setStatus("⚠️ Primero debes registrar un usuario.");
        return;
    }

    const response = await fetch(`/users/buy-points?userId=${currentUserId}`, {
        method: "POST"
    });

    const user = await response.json();
    document.getElementById("currentPoints").innerText = user.points;

    setStatus("💰 Compra exitosa: se agregaron 1000 puntos por $10000.");
}

async function refreshUser() {
    if (!currentUserId) {
        setStatus("⚠️ Primero debes registrar un usuario.");
        return;
    }

    const response = await fetch(`/users/${currentUserId}`);
    const user = await response.json();

    document.getElementById("currentUserId").innerText = user.id;
    document.getElementById("currentUsername").innerText = user.username;
    document.getElementById("currentPoints").innerText = user.points;

    setStatus("🔄 Datos del usuario actualizados.");
}

async function joinRace() {
    if (!currentUserId) {
        setStatus("⚠️ Primero debes registrar un usuario.");
        return;
    }

    const response = await fetch(`/race/join?userId=${currentUserId}`, {
        method: "POST"
    });

    const text = await response.text();
    setStatus("👥 " + text);
}

async function placeBet() {
    if (!currentUserId) {
        setStatus("⚠️ Primero debes registrar un usuario.");
        return;
    }

    const horse = document.getElementById("horseSelect").value;
    const amount = parseInt(document.getElementById("betAmount").value);

    if (!amount || amount <= 0) {
        setStatus("⚠️ Ingresa una apuesta válida.");
        return;
    }

    const response = await fetch("/race/bet", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            userId: currentUserId,
            amount: amount,
            horse: horse
        })
    });

    if (!response.ok) {
        const text = await response.text();
        setStatus("❌ " + text);
        return;
    }

    const bet = await response.json();
    await refreshUser();
    setStatus(`🎲 Apuesta realizada: ${bet.amount} puntos al caballo ${bet.horse}.`);
}

async function startRace() {
    initSocket();

    const response = await fetch("/race/start");
    const text = await response.text();

    setStatus("🚦 " + text);
}

window.onload = function () {
    initSocket();
};