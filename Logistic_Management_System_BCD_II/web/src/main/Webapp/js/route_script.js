function getAllRoutes() {
    fetch("getAllRoutes", {
        method: "GET"
    }).then(response => {
        if (!response.ok) {
            throw new Error("Network error");
        }
        return response.json();
    }).then(data => {
        console.log(data);
        updateRouteTable(data);
    }).catch(error => {
        console.error("error", error);
    });
}

function updateRouteTable(data) {
    let tableBody = document.getElementById("routeTableBody");
    tableBody.innerHTML = "";

    data.forEach(route => {
        let row = document.createElement("tr");

        row.setAttribute("onclick", "openAddShipmentsToRouteModal(this)");

        let cells = [
            route.route_id,
            route.origin,
            route.destination,
            route.distance,
            route.start_date,
            route.estimated_time,
        ];

        cells.forEach(cellData => {
            let cell = document.createElement("td");
            cell.textContent = cellData;
            row.appendChild(cell);
        });

        tableBody.appendChild(row);
    });
}

let addShipmentsToRouteModal;
function openAddShipmentsToRouteModal(row) {
    let route_id = row.cells[0].textContent;

    document.getElementById("route_id").value = route_id;

    let addShipmentsToRoute = document.getElementById("addShipmentsToRouteModal");
    addShipmentsToRouteModal = new bootstrap.Modal(addShipmentsToRoute);
    addShipmentsToRouteModal.show();
    getOptimizedShipments(route_id);
}

function getOptimizedShipments(id){
    fetch("getOptimizedShipments?id="+id, {
        method: "GET"
    }).then(response => {
        if (!response.ok) {
            throw new Error("Network error");
        }
        return response.json();
    }).then(data => {
        console.log(data);
        updateAddShipmentToRouteTable(data);
    }).catch(error => {
        console.error("error", error);
    });
}

function updateAddShipmentToRouteTable(data){
    let tableBody = document.getElementById("addShipmentToRouteTableBody");
    tableBody.innerHTML = "";

    data.forEach(optimizedShipment => {
        let row = document.createElement("tr");

        let cells = [
            optimizedShipment.order_id,
            optimizedShipment.created_at,
            optimizedShipment.customer_id.customer_id,
            optimizedShipment.status,
        ];

        cells.forEach(cell_data => {
            let cell = document.createElement("td");
            cell.textContent = cell_data;
            row.appendChild(cell);
        });

        let checkboxCell = document.createElement("td");
        let checkbox = document.createElement("input");
        checkbox.type = "checkbox";
        checkboxCell.appendChild(checkbox);
        row.appendChild(checkboxCell);

        tableBody.appendChild(row);
    });
}

function addShipmentsToRoute(){
    let route_id = document.getElementById("route_id").value;

    let checkedOrderIds = [];

    let checkboxes = document.querySelectorAll('#addShipmentToRouteTableBody input[type="checkbox"]');
    checkboxes.forEach(checkbox => {
        if (checkbox.checked) {
            let row = checkbox.closest("tr");
            let orderIdCell = row.cells[0];
            let orderId = orderIdCell.textContent.trim();
            checkedOrderIds.push(orderId);
        }
    });

    console.log("route Id : "+route_id);
    let arr = {
        "route_id" : route_id,
        "checkedOrderIds" : checkedOrderIds
    }

    fetch("assignShipmentsToRoute", {
        method: "POST",
        body: JSON.stringify(arr)
    }).then(response => {
        if (!response.ok) {
            throw new Error("Network error");
        }
        getAllRoutes();
        return response.text();
    }).then(data => {
        addShipmentsToRouteModal.hide();
    }).catch(error => {
        console.error("error", error);
    });
}

let addRouteModal;
function openAddRouteModal(){
    let addRoute = document.getElementById("addRoutesModal");
    addRouteModal = new bootstrap.Modal(addRoute);
    addRouteModal.show();
}

function addRoute(){

    let origin = document.getElementById("origin").value;
    let destination = document.getElementById("destination").value;
    let distance = document.getElementById("distance").value;
    let estimated_time = document.getElementById("estimated_time").value;

    if (!isPositiveNumber(distance)) {
        alert("Distance must be a positive number.");
        return;
    }

    let data = {
        "origin" : origin,
        "destination" : destination,
        "distance" : distance,
        "estimated_time" : estimated_time
    }

    fetch("addRoute", {
        method: "POST",
        body: JSON.stringify(data)
    }).then(response => {
        if (!response.ok) {
            throw new Error("Network error");
        }
        getAllRoutes();
        return response.text();
    }).then(data => {
        addRouteModal.hide();
    }).catch(error => {
        console.error("error", error);
    });

}

function isPositiveNumber(value) {
    return !isNaN(value) && parseFloat(value) > 0;
}

let addVehicleToRouteModal;
function openAddVehicleToRouteModal(){
    let route_id = document.getElementById("route_id").value;

    document.getElementById("r_id").value = route_id;

    if (addShipmentsToRouteModal != null) {
        addShipmentsToRouteModal.hide();
    }

    let addVehicleToRoute = document.getElementById("addVehiclesToRouteModal");
    addVehicleToRouteModal = new bootstrap.Modal(addVehicleToRoute);
    addVehicleToRouteModal.show();
    getVehiclesToAssign(route_id);
}

function getVehiclesToAssign(id){
    fetch("getVehiclesToAssign?id="+id, {
        method: "GET"
    }).then(response => {
        if (!response.ok) {
            throw new Error("Network error");
        }
        return response.json();
    }).then(data => {
        console.log(data);
        updateAddVehicleToRouteTable(data);
    }).catch(error => {
        console.error("error", error);
    });
}

function updateAddVehicleToRouteTable(data){
    let tableBody = document.getElementById("addVehiclesToRouteTableBody");
    tableBody.innerHTML = "";

    data.forEach(vehicleToAssign => {
        let row = document.createElement("tr");

        let stat;
        if(vehicleToAssign.status){
            stat = "available";
        }else{
            stat = "not-available";
        }
        let cells = [
            vehicleToAssign.vehicle_id,
            vehicleToAssign.type,
            vehicleToAssign.capacity,
            stat
        ];

        cells.forEach(cell_data => {
            let cell = document.createElement("td");
            cell.textContent = cell_data;
            row.appendChild(cell);
        });

        let checkboxCell = document.createElement("td");
        let checkbox = document.createElement("input");
        checkbox.type = "checkbox";
        checkboxCell.appendChild(checkbox);
        row.appendChild(checkboxCell);

        tableBody.appendChild(row);
    });
}

function addVehiclesToRoute(){
    let route_id = document.getElementById("route_id").value;

    let checkedVehicleIds = [];

    let checkboxes = document.querySelectorAll('#addVehiclesToRouteTableBody input[type="checkbox"]');
    checkboxes.forEach(checkbox => {
        if (checkbox.checked) {
            let row = checkbox.closest("tr");
            let orderIdCell = row.cells[0];
            let vehicleId = orderIdCell.textContent.trim();
            checkedVehicleIds.push(vehicleId);
        }
    });

    // alert("Checked Order IDs:"+checkedOrderIds);
    // console.log("Checked Order IDs:", checkedOrderIds);
    let arr = {
        "route_id" : route_id,
        "checkedVehicleIds" : checkedVehicleIds
    }

    fetch("assignVehiclesToRoute", {
        method: "POST",
        body: JSON.stringify(arr)
    }).then(response => {
        if (!response.ok) {
            throw new Error("Network error");
        }
        getAllRoutes();
        return response.text();
    }).then(data => {
        addVehicleToRouteModal.hide();
    }).catch(error => {
        console.error("error", error);
    });
}