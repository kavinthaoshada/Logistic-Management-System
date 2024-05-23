function getAllAssignedShipments(email) {

    fetch("getAllAssignedShipments?email=" + email, {
        method: "GET"
    }).then(response => {
        if (!response.ok) {
            throw new Error("Network error");
        }
        return response.json();
    }).then(data => {
        console.log(data);
        updateAssignedShipmentsTable(data);
    }).catch(error => {
        console.error("error", error);
    });
}

function updateAssignedShipmentsTable(data) {
    let tableBody = document.getElementById("filteredShipmentTableBody");
    tableBody.innerHTML = "";

    data.forEach(shipment => {
        let row = document.createElement("tr");

        row.setAttribute("onclick", "openShipmentDetailModal(this)");

        let cells = [
            shipment.order_id,
            shipment.customer_id.customer_id,
            shipment.customer_id.name,
            shipment.customer_id.address,
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

let assShipmentDetailModal;

function openShipmentDetailModal(row) {
    let shipment_id = row.cells[0].textContent;
    let shipment_stat = row.cells[5].textContent;

    document.getElementById("shipmnt_id").value = shipment_id;
    document.getElementById("shipmnt_stat").value = shipment_stat;

    getRouteByShipment(shipment_id);

    let shipmentDetail = document.getElementById("viewShipmentDetailModal");
    assShipmentDetailModal = new bootstrap.Modal(shipmentDetail);
    assShipmentDetailModal.show();
}

function getRouteByShipment(id){
    fetch("getRouteByShipment?id=" + id, {
        method: "GET"
    }).then(response => {
        if (!response.ok) {
            throw new Error("Network error");
        }
        return response.json();
    }).then(data => {
        console.log(data);
        updateModalData(data);
    }).catch(error => {
        console.error("error", error);
    });
}

function updateModalData(data){
    data.forEach(detail => {
        document.getElementById("origin").innerHTML = detail.origin;
        document.getElementById("destination").innerHTML = detail.destination;
        document.getElementById("v_type").innerHTML = detail.vehicles.type;
        document.getElementById("v_type").innerHTML = detail.vehicles.vehicle_id;
    });
}

function updateAssShipmentStatus() {
    let emp_email = document.getElementById("emp_email").value;
    let shipment_id = document.getElementById("shipmnt_id").value;
    let stat = document.getElementById("shipmnt_stat").value;
    let bool = false;
    let updatedStat;

    if (stat === "PENDING") {
        updatedStat = "PROCESSING";
        bool = true;
    } else if (stat === "PROCESSING") {
        updatedStat = "SHIPPED";
        bool = true;
    } else if (stat === "SHIPPED") {
        updatedStat = "DELIVERED";
        bool = true;
    } else if (stat === "DELIVERED") {
        updatedStat = "COMPLETED";
        bool = true;
    } else if (stat === "COMPLETED") {
        alert("Status cannot update further");
        bool = false;
    } else {
        alert("You must have admin authority to update status.");
        bool = false;
    }

    if(bool){
        fetch("updateShipmentStatusFromAdmin?id=" + shipment_id + "&status=" + updatedStat, {
            method: "GET"
        }).then(response => {
            if (!response.ok) {
                throw new Error("Network error");
            }
            assShipmentDetailModal.hide();
            return response.json();
        }).then(data => {
            console.log(data);
            getAllAssignedShipments(emp_email);

        }).catch(error => {
            console.error("error", error);
        });
    }

}

let viewProductModalFromAssigned;
function openViewProductModalFromAssigned() {
    let shipment_id = document.getElementById("shipmnt_id").value;

    if (assShipmentDetailModal != null) {
        assShipmentDetailModal.hide();
    }

    let viewProduct = document.getElementById("viewProductModal");
    viewProductModalFromAssigned = new bootstrap.Modal(viewProduct);
    viewProductModalFromAssigned.show();
    getProductsByShipmentIdFromAssigned(shipment_id);
}

function getProductsByShipmentIdFromAssigned(shipment_id) {
    let id = shipment_id;
    console.log("id"+id);
    fetch("getProductsByShipmentId?id="+id, {
        method: "GET"
    }).then(response => {
        if (!response.ok) {
            throw new Error("Network error");
        }
        return response.json();
    }).then(data => {
        console.log("data", data);
        updateProductTableFromAssigned(data);
    }).catch(error => {
        console.error("error", error);
    });
}

function updateProductTableFromAssigned(data) {

    let tableBody = document.getElementById("productTableBodyFromAssigned");
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