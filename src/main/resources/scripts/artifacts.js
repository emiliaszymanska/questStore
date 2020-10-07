const container = document.querySelector(".row");

function getArtifacts() {
    fetch('http://localhost:8001/artifact')
        .then(function(response) {
            return response.json();
        })
        .then(function(artifacts) {
            // console.log(artifacts);
            displayArtifacts(artifacts);
        });
}

function displayArtifacts(artifacts) {
    artifacts.forEach(artifact => {
        let node = `
        <div class="single-card">
            <div class="card-set card-block">
                <h4 class="card-set-title">${artifact.name}</h4>
                <img src="https://static.pexels.com/photos/7096/people-woman-coffee-meeting.jpg" alt="Photo" class="card-set-image">
                <h5 class="card-set-text-1">${artifact.description}</h5>
                <h5 class="card-set-text-2">Price: ${artifact.price}</h5>
                <h5 class="card-set-text-3">Is group: ${artifact.group}</h5>
            </div>
        </div>
        `;
        container.innerHTML += node;
    })
}

function getBoughtArtifact() {
    fetch('http://localhost:8001/student-wallet')
        .then(function(response) {
            return response.json();
        })
        .then(function(artifacts) {
            // console.log(artifacts);
            displayArtifacts(artifacts);
        });
}

getArtifacts();
getBoughtArtifact();
