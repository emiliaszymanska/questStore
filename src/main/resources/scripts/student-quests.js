import {getCookie} from "./cookies.js";

const dashboardRow = document.querySelector(".dashboard-row");
const container = document.querySelector(".row");
const spinner = document.getElementById("spinner");
let object;

function getStudentQuests() {
    spinner.removeAttribute('hidden');
    const sessionId = getCookie("sessionId");

    fetch(`http://localhost:8001/student/quests/${sessionId.slice(1, -1)}`)
        .then(function(response) {
            return response.json();
        })
        .then(function(quests) {
            spinner.setAttribute('hidden', '');
                displayQuests(quests);
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
                <h5 class="card-set-text-4">In progress</h5>
            </div>
        </div>
        `;
        container.innerHTML += node;
    });
}

getStudentQuests();