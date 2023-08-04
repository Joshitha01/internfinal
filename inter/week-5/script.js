const containerTableBody = document.getElementById('containerTableBody');
const modal = document.getElementById('modal');
const containerDetails = document.getElementById('containerDetails');

function fetchData() {
    fetch('http://localhost:3002/containers')
        .then(response => response.json())
        .then(data => {
            populateTable(data);
        })
        .catch(error => console.error('Error fetching data:', error));
}

function populateTable(containers) {
    containerTableBody.innerHTML = '';

    containers.forEach(container => {
        const row = containerTableBody.insertRow();

        row.insertCell().innerText = container.id.substring(0, 12);
        row.insertCell().innerText = container.name;
        row.insertCell().innerText = container.image;
        row.insertCell().innerText = container.ports.join(', ');
        row.insertCell().innerText = container.running ? 'Yes' : 'No';

        const startButton = document.createElement('button');
        startButton.innerText = 'Start';
        startButton.onclick = () => startContainer(container.id);
        row.insertCell().appendChild(startButton);

        const stopButton = document.createElement('button');
        stopButton.innerText = 'Stop';
        stopButton.onclick = () => stopContainer(container.id);
        row.insertCell().appendChild(stopButton);

        const detailsButton = document.createElement('button');
        detailsButton.innerText = 'Details';
        detailsButton.onclick = () => showContainerDetails(container.id);
        row.insertCell().appendChild(detailsButton);
    });
}

// ... Rest of the script.js code remains unchanged ...

function startContainer(id) {
    fetch(`http://localhost:3002/containers/start/${id}`, { method: 'POST' })
        .then(() => fetchData())
        .catch(error => console.error('Error starting container:', error));
}

function stopContainer(id) {
    fetch(`http://localhost:3002/containers/stop/${id}`, { method: 'POST' })
        .then(() => fetchData())
        .catch(error => console.error('Error stopping container:', error));
}

function showContainerDetails(id) {
    fetch(`http://localhost:3002/containers/details/${id}`)
        .then(response => response.json())
        .then(data => {
            displayModal(data);
        })
        .catch(error => console.error('Error fetching container details:', error));
}

function displayModal(data) {
    containerDetails.innerHTML = '';

    for (const key in data) {
        const detailRow = document.createElement('div');
        const detailKey = document.createElement('span');
        const detailValue = document.createElement('span');

        detailKey.innerText = `${key}:`;
        detailValue.innerText = data[key];

        detailRow.appendChild(detailKey);
        detailRow.appendChild(detailValue);
        containerDetails.appendChild(detailRow);
    }

    modal.style.display = 'block';
}

function closeModal() {
    modal.style.display = 'none';
}

// Fetch data and populate the table on page load
fetchData();
