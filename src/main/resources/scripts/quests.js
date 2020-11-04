const dashboardRow = document.querySelector(".dashboard-row");
const container = document.querySelector(".row");
const spinner = document.getElementById("spinner");
let object;



function enableEventListeners() {
    const types = document.querySelectorAll(".types");
    types.forEach(type => {
        type.addEventListener('click', () => {
            const typeName = type.querySelector("h4").innerHTML.toUpperCase();
            const filtered = object.filter(quest => {
                return quest.type.includes(typeName);
            });
            displayQuests(filtered);
        })
    });
}

function displayMenu() {
    let node = `
        <div class="single-card types">
            <div class="dashboard-card">
                <h4 class="dashboard-card-title">Easy</h4>
                <img src="../../images/quests/one.jpg" alt="Photo" class="dashboard-card-image">
            </div>
        </div>
        <div class="single-card types">
            <div class="dashboard-card">
                <h4 class="dashboard-card-title">Moderate</h4>
                <img src="../../images/quests/two.jpg" alt="Photo" class="dashboard-card-image">
            </div>
        </div>
        <div class="single-card types">
            <div class="dashboard-card">
                <h4 class="dashboard-card-title">Difficult</h4>
                <img src="../../images/quests/three.jpg" alt="Photo" class="dashboard-card-image">
            </div>
        </div>
        `;
    dashboardRow.innerHTML += node;

    enableEventListeners();
}

function getQuests() {
    spinner.removeAttribute('hidden');

    fetch('http://localhost:8001/quest')
        .then(function (response) {
            return response.json();
        })
        .then(function (quests) {
            spinner.setAttribute('hidden', '');
            // displayQuests(quests);
            object = quests;
            console.log(object);
            displayMenu();
        });
}

function displayQuests(quests) {
    container.innerHTML = "";

    quests.forEach(quest => {
        let node = `
        <div class="single-card">
            <div class="card-set card-block">
                <h4 class="card-set-title">${quest.name}</h4>
                <img src="https://static.pexels.com/photos/7096/people-woman-coffee-meeting.jpg" alt="Photo" class="card-set-image">
                <h5 class="card-set-text-1">${quest.description}</h5>
                <h5 class="card-set-text-2">Reward: ${quest.reward}</h5>
                <h5 class="card-set-text-3">Experience: ${quest.experience}</h5>
                <h5 class="card-set-text-4">Difficulty level: ${quest.type}</h5>
            </div>
            <button id="addQuest">Start Quest</button>
        </div>
        `;
        container.innerHTML += node;
    })
}

getQuests();