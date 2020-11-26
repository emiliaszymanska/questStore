const spinner = document.querySelector("#spinner");
const transactionsTable = document.querySelector(".transactions");

(() => {
    getNotFinishedTransactions();
})();

function displayTransactions(transactions) {
    transactionsTable.innerHTML = "";
    let headers = `
        <tr>
            <th class="column1">Artifact Name</th>
            <th class="column2">Total Price</th>
            <th class="column3">Contributors</th>
            <th class="column4"></th>
        </tr>
    `;

    transactionsTable.innerHTML += headers;

    transactions.forEach(transaction => {
        let node = `
        <tr>
            <td class="column1">${transaction.artifact.name}</td>
            <td class="column2">${transaction.artifact.price}</td>
            <td class="column3">
        `;

        transaction.payments.forEach(payment => {
            let paymentNode = `
            ${payment.student.firstName} ${payment.student.lastName} >> ${payment.amount}<br>
        `;
            node += paymentNode;
        })

        let endNode = `
            </td>
            <td class="column4"><button id="profile-submit-button">Contribute</button></td>
        </tr>
        `;
        node += endNode;

        transactionsTable.innerHTML += node;
    })
}

function getNotFinishedTransactions() {
    const sessionId = getCookie("sessionId");
    spinner.removeAttribute('hidden');
    fetch(`http://localhost:8001/student/transactions/${sessionId.slice(1, -1)}`)
        .then(function(response) {
            return response.json();
        })
        .then(function(transactions) {
            spinner.setAttribute('hidden', '');
            console.log(transactions);
            displayTransactions(transactions);
        });
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