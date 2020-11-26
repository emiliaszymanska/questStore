const spinner = document.querySelector("#spinner");
const editForm = document.querySelector("#transaction-form");

(() => {
    editForm.addEventListener('submit', function(event) {
        event.preventDefault();

        const data = `artifact=${artifact.name}&paymentDate=${payments.paymentDate.value}`;
        console.log(data);

        getTransactions(data);
    })
})();

function displayTransactions(transactions) {
    editForm.innerHTML = "";

    let node = `<div class="tweet">
                    <p id="name">${transactions.artifact.name}</p><br>
                    <p id="text">${transactions.payments.paymentDate.year}</p><br>
                </div>`;
    editForm.innerHTML += node;
}

function getTransactions(data) {
    const sessionId = getCookie("sessionId");
    fetch(`http://localhost:8001/student/add-payment/${sessionId.slice(1, -1)}`, {
        method: "POST",
        body: data
    })
        .then(function(response) {
            return response.json();
        })
        .then(function(transactions) {
            displayTransactions(transactions);
        });
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