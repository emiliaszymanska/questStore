const container = document.querySelector(".row");
const types = document.querySelectorAll(".types");
let object;

types.forEach(type => {
    type.addEventListener('click', () => {
        const typeName = type.querySelector("h4").innerHTML.toUpperCase();
        const filtered = object.filter(quest => {
            return quest.type.includes(typeName);
        });
        displayQuests(filtered);
    })
});

function getQuests() {
    fetch('http://localhost:8001/quest')
        .then(function (response) {
            return response.json();
        })
        .then(function (quests) {
            // displayQuests(quests);
            object = quests;
            console.log(object);
        });
}

function displayQuests(quests) {
    container.innerHTML = "";
    quests.forEach(quest => {
        let node = `
        <div class="single-card" style="display: block">
            <div class="card-set card-block">
                <h4 class="card-set-title">${quest.name}</h4>
                <img src="https://static.pexels.com/photos/7096/people-woman-coffee-meeting.jpg" alt="Photo" class="card-set-image">
                <h5 class="card-set-text-1">${quest.description}</h5>
                <h5 class="card-set-text-2">Reward: ${quest.reward}</h5>
                <h5 class="card-set-text-3">Experience: ${quest.experience}</h5>
                <h5 class="card-set-text-4">Difficulty level: ${quest.type}</h5>
            </div>
        </div>
        `;
        container.innerHTML += node;
    })
}

getQuests();