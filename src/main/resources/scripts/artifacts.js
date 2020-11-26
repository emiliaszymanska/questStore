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

            enableIndividualTransactionButtons();
            enableCollectiveTransactionButtons();
        })
    });
}

function enableIndividualTransactionButtons() {
    const buyArtifactButtons = document.querySelectorAll(".buy-artifact-button");

    buyArtifactButtons.forEach(buyArtifactButton => {
        buyArtifactButton.addEventListener('click', function (event) {
            event.preventDefault();

            if (confirm('Are you sure you want to buy it?')) {
                const id = this.getAttribute("data-id");
                const data = `artifact_id=${id}`;

                buy(data);
                console.log('Artifact was bought.');
            }
        })
    })
}

function enableCollectiveTransactionButtons() {
    const buyArtifactCollectivelyButtons = document.querySelectorAll(".buy-artifact-collectively-button");

    buyArtifactCollectivelyButtons.forEach(buyArtifactCollectivelyButton => {
        buyArtifactCollectivelyButton.addEventListener('click', function (event) {
            event.preventDefault();

            const id = this.getAttribute("data-id");
            const popupForm = document.querySelector("#popup-form");

            popupForm.addEventListener('submit', function (event) {
                event.preventDefault();

                const data = `amount=${this.amount.value}&artifact_id=${id}`;

                buy(data);
                console.log('Artifact was bought.');
            })
        })
    })
}

function hidePopup() {
    document.getElementById('popup-container').style.display = "none";
}

function showPopup() {
    document.getElementById('popup-container').style.display = "block";
}

function buy(data) {
    const sessionId = getCookie("sessionId");
    fetch(`http://localhost:8001/student/buy/${sessionId.slice(1, -1)}`, {
        method: "POST",
        body: data
    })
        .then(function (response) {
            alert("Artifact was bought");
            return response.json();
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
                ${artifact.group ?
                    `<button data-id="${artifact.id}" onclick="showPopup()" class="buy-artifact-collectively-button">Buy collectively</button>` :
                    `<button data-id="${artifact.id}" class="buy-artifact-button">Buy</button>`}
            </div>
        </div>
        `;
        container.innerHTML += node;
    })
}

function getCookie(cname) {
    //TODO refactor
    var name = cname + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

getArtifacts();