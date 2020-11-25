const dashboardRow = document.querySelector(".dashboard-row");
const container = document.querySelector(".row");
const spinner = document.getElementById("spinner");
let object;

function enableEventListeners() {
    const types = document.querySelectorAll(".types");
    types.forEach(type => {
        type.addEventListener('click', (event) => {
            const typeName = type.querySelector("h4").innerHTML.toUpperCase().split(" ").join("_");
            const filtered = object.filter(artifact => {
                return artifact.type.includes(typeName);
            });
            displayArtifacts(filtered);
        })
    });
}

function displayMenu() {
    let node = `
        <div class="single-card types">
            <div class="dashboard-card">
                <h4 class="dashboard-card-title">Related to students</h4>
                <img src="../../images/dashboard-images/student-group.jpg" alt="Student group" class="dashboard-card-image">
            </div>
        </div>
        <div class="single-card types">
            <div class="dashboard-card">
                <h4 class="dashboard-card-title">Related to mentors</h4>
                <img src="../../images/dashboard-images/mentor.jpeg" alt="Mentor" class="dashboard-card-image">
            </div>
        </div>
        <div class="single-card types">
            <div class="dashboard-card">
                <h4 class="dashboard-card-title">Related to the teaching</h4>
                <img src="../../images/dashboard-images/study-process.jpeg" alt="Study process" class="dashboard-card-image">
            </div>
        </div>
        `;
    dashboardRow.innerHTML += node;

    enableEventListeners();
}

function getArtifacts() {
    spinner.removeAttribute('hidden');

    fetch('http://localhost:8001/artifact')
        .then(function (response) {
            return response.json();
        })
        .then(function (artifacts) {
            spinner.setAttribute('hidden', '');
            object = artifacts;
            console.log(object);
            displayMenu();
        });
}

function displayArtifacts(artifacts) {
    container.innerHTML = "";

    artifacts.forEach(artifact => {
        let node = `
        <div class="single-card">
            <div class="card-set card-block">
                <h4 class="card-set-title">${artifact.name}</h4>
                <img src="https://static.pexels.com/photos/7096/people-woman-coffee-meeting.jpg" alt="Photo" class="card-set-image">
                <h5 class="card-set-text-1">${artifact.description}</h5>
                <h5 class="card-set-text-2">Price: ${artifact.price}</h5>
                ${artifact.group ? `<button class="buy-artifact-button">Buy collectively</button>` : 
                                   `<button class="buy-artifact-button">Buy</button>`}
            </div>
        </div>
        `;
        container.innerHTML += node;
    })
}

getArtifacts();