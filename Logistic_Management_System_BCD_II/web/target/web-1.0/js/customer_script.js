function getAllCustomer() {
    fetch("getAllCustomers", {
        method: "GET"
    }).then(response => {
        if (!response.ok) {
            throw new Error("Network error");
        }
        return response.json();
    }).then(data => {
        console.log(data);
        updateCustomerTable(data);
    }).catch(error => {
        console.error("error", error);
    });
}

function updateCustomerTable(data) {
    let tableBody = document.getElementById("customerTableBody");
    tableBody.innerHTML = "";

    data.forEach(customer => {
        let row = document.createElement("tr");

        row.setAttribute("onclick", "openStatusUpdateModal(this)");

        let cells = [
            customer.customer_id,
            customer.name,
            customer.address,
            customer.contact_number,
            customer.email,
            customer.status,
        ];

        cells.forEach(cellData => {
            let cell = document.createElement("td");
            cell.textContent = cellData;
            row.appendChild(cell);
        });

        tableBody.appendChild(row);
    });
}

let updateStatusModal;
function openStatusUpdateModal(row) {
    let customerId = row.cells[0].textContent;
    let customerName = row.cells[1].textContent;

    document.getElementById("customer_id").innerText = customerId;
    document.getElementById("customer_name").innerText = customerName;

    let updateStatus = document.getElementById("updateStatusModal");
    updateStatusModal = new bootstrap.Modal(updateStatus);
    updateStatusModal.show();
    getCustomerStatus();
}

let addShipmentDetailModal;
function openAddShipmentDetailModal(){
    let customer_id = document.getElementById("customer_id").innerText;

    document.getElementById("cus_id").value = customer_id;

    if (updateStatusModal != null) {
        updateStatusModal.hide();
    }

    let addShipmentDetail = document.getElementById("addShipmentDetailModal");
    addShipmentDetailModal = new bootstrap.Modal(addShipmentDetail);
    addShipmentDetailModal.show();
}

function getCustomerStatus(){
    fetch("getCustomerStatus", {
        method: "GET"
    }).then(response => {
        if (!response.ok) {
            throw new Error("Network error");
        }
        return response.json();
    }).then(data => {
        console.log(data);
        updateDropdownForCustomerStatus(data);
    }).catch(error => {
        console.error("error", error);
    });
}

function updateDropdownForCustomerStatus(data){
    let customerStatusDropdown = document.getElementById("customerStatus");

    customerStatusDropdown.innerHTML = "";

    data.forEach(customerStatus => {
        let option = document.createElement("option");
        option.value = customerStatus;
        option.textContent = customerStatus;
        customerStatusDropdown.appendChild(option);
    });
}

function updateStatus(){
    let customer_id = document.getElementById("customer_id").innerText;
    let customerStatus = document.getElementById("customerStatus").value;

    fetch("updateCustomerStatus?id="+customer_id+"&status="+customerStatus, {
        method: "GET"
    }).then(response => {
        if (!response.ok) {
            throw new Error("Network error");
        }
        updateStatusModal.hide();
        return response.json();
    }).then(data => {
        console.log(data);
        getAllCustomer();
    }).catch(error => {
        console.error("error", error);
    });
}

let addCustomerModal;
function openAddCustomerModal(){
    let addCustomer = document.getElementById("addCustomerModal");
    addCustomerModal = new bootstrap.Modal(addCustomer);
    addCustomerModal.show();
}

function addCustomer(){

    let name = document.getElementById("name").value;
    let address = document.getElementById("address").value;
    let contact_number = document.getElementById("c_number").value;
    let email = document.getElementById("email").value;

    let data = {
        "name" : name,
        "address" : address,
        "contact_number" : contact_number,
        "email" : email
    }

    fetch("addCustomer", {
        method: "POST",
        body: JSON.stringify(data)
    }).then(response => {
        if (!response.ok) {
            throw new Error("Network error");
        }
        getAllCustomer();
        return response.text();
    }).then(data => {
        addCustomerModal.hide();
    }).catch(error => {
        console.error("error", error);
    });

}
