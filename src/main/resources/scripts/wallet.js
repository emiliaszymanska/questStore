const container = document.querySelector(".row");
const spinner = document.getElementById("spinner");

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

function getTransactions() {
    const sessionId = getCookie("sessionId");
    spinner.removeAttribute('hidden');
    fetch(`http://localhost:8001/student/wallet/${sessionId.slice(1, -1)}`)
        .then(function(response) {
            return response.json();
        })
        .then(function(transactions) {
            spinner.setAttribute('hidden', '');
            transactions.forEach(transaction => {
                displayArtifact(transaction.artifact);
            });
        });
}

function getStudentData() {
    const sessionId = getCookie("sessionId");

    fetch(`http://localhost:8001/student/data/${sessionId.slice(1, -1)}`)
        .then(function(response) {
            return response.json();
        })
        .then(function(data) {
            displayStudentData(data);
        });
}

function displayStudentData(data) {
    const balance = document.querySelector('#balance');
    const experienceLevel = document.querySelector('#level');
    balance.innerHTML += data.balance;
    experienceLevel.innerHTML += data.experienceLevel;
}

function displayArtifact(artifact) {
    let node = `
        <div class="single-card">
            <div class="card-set card-block">
                <h4 class="card-set-title">${artifact.name}</h4>
                <img src="https://static.pexels.com/photos/7096/people-woman-coffee-meeting.jpg" alt="Photo" class="card-set-image">
                <h5 class="card-set-text-1">${artifact.description}</h5>
                <h5 class="card-set-text-2">Price: ${artifact.price}</h5>
                <h5 class="card-set-text-3">Is group: ${artifact.group ? "YES" : "NO"}</h5>
            </div>
        </div>
        `;
    container.innerHTML += node;
}

getStudentData();
getTransactions();