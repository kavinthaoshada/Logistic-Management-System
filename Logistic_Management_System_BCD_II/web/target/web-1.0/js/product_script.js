function getAllShipments() {
    fetch("getAllShipments", {
        method: "GET"
    }).then(response => {
        if (!response.ok) {
            throw new Error("Network error");
        }
        return response.json();
    }).then(data => {
        console.log(data);
        updateShipmentsTable(data);
    }).catch(error => {
        console.error("error", error);
    });
}

function updateShipmentsTable(data) {
    let tableBody = document.getElementById("shipmentTableBody");
    tableBody.innerHTML = "";

    data.forEach(shipment => {
        let row = document.createElement("tr");

        row.setAttribute("onclick", "openOptionModal(this)");

        let cells = [
            shipment.order_id,
            shipment.customer_id.customer_id,
            shipment.customer_id.name,
            shipment.created_at,
            shipment.status,
        ];

        cells.forEach(cellData => {
            let cell = document.createElement("td");
            cell.textContent = cellData;
            row.appendChild(cell);
        });

        tableBody.appendChild(row);
    });
}

let optionModal;

function openOptionModal(row) {
    let shipment_id = row.cells[0].textContent;
    let cus_name = row.cells[2].textContent;

    document.getElementById("shipment_id").innerText = shipment_id;
    document.getElementById("cus_name").innerText = cus_name;

    let option = document.getElementById("optionModal");
    optionModal = new bootstrap.Modal(option);
    optionModal.show();
}

let viewProductModal;

function openViewProductModal() {
    let shipment_id = document.getElementById("shipment_id").innerText;

    // document.getElementById("shipment_id2").innerText = shipment_id;

    if (optionModal != null) {
        optionModal.hide();
    }

    let viewProduct = document.getElementById("viewProductModal");
    viewProductModal = new bootstrap.Modal(viewProduct);
    viewProductModal.show();
    getProductsByShipmentId(shipment_id);
}

let updateShipmentStatusModal;

function openUpdateShipmentStatusModal() {
    let shipment_id = document.getElementById("shipment_id").innerText;

    document.getElementById("shipment_id2").innerText = shipment_id;

    if (optionModal != null) {
        optionModal.hide();
    }

    let shipmentStatus = document.getElementById("updateShipmentStatusModal");
    updateShipmentStatusModal = new bootstrap.Modal(shipmentStatus);
    updateShipmentStatusModal.show();
    getShipmentStatus();
}

function getShipmentStatus() {
    fetch("getShipmentStatus", {
        method: "GET"
    }).then(response => {
        if (!response.ok) {
            throw new Error("Network error");
        }
        return response.json();
    }).then(data => {
        console.log(data);
        updateDropdownForShipmentStatus(data);
    }).catch(error => {
        console.error("error", error);
    });
}

function updateDropdownForShipmentStatus(data) {
    let shipmentStatusDropdown = document.getElementById("shipmentStatus");

    shipmentStatusDropdown.innerHTML = "";

    data.forEach(shipmentStatus => {
        if (shipmentStatus === "PENDING" ||
            shipmentStatus === "CANCELLED" ||
            shipmentStatus === "ON_HOLD" ||
            shipmentStatus === "RETURNED" ||
            shipmentStatus === "REFUNDED") {
            let option = document.createElement("option");
            option.value = shipmentStatus;
            option.textContent = shipmentStatus;
            shipmentStatusDropdown.appendChild(option);
        }

    });
}

function updateShipmentStatus() {
    let shipment_id = document.getElementById("shipment_id2").innerText;
    let shipmentStatus = document.getElementById("shipmentStatus").value;

    if (shipmentStatus === "PENDING" ||
        shipmentStatus === "CANCELLED" ||
        shipmentStatus === "ON_HOLD" ||
        shipmentStatus === "RETURNED" ||
        shipmentStatus === "REFUNDED") {
        fetch("updateShipmentStatusFromAdmin?id=" + shipment_id + "&status=" + shipmentStatus, {
            method: "GET"
        }).then(response => {
            if (!response.ok) {
                throw new Error("Network error");
            }
            updateShipmentStatusModal.hide();
            return response.json();
        }).then(data => {
            console.log(data);
            getAllShipments();
        }).catch(error => {
            console.error("error", error);
        });
    }else {
        alert("Unable to update..!");
    }
}

function getProductsByShipmentId(shipment_id) {
    fetch("getProductsByShipmentId?id=" + shipment_id, {
        method: "GET"
    }).then(response => {
        if (!response.ok) {
            throw new Error("Network error");
        }
        return response.json();
    }).then(data => {
        updateProductTable(data);
    }).catch(error => {
        console.error("error", error);
    });
}

function updateProductTable(data) {
    let tableBody = document.getElementById("productTableBody");
    tableBody.innerHTML = "";

    data.forEach(product => {
        let row = document.createElement("tr");

        let cells = [
            product.product_id,
            product.name,
            product.description,
            product.quantity,
            product.total_weight,
        ];

        cells.forEach(cellData => {
            let cell = document.createElement("td");
            cell.textContent = cellData;
            row.appendChild(cell);
        });

        tableBody.appendChild(row);
    });
}