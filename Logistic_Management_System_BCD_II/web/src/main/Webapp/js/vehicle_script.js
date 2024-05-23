function getAllVehicles() {
    fetch("getVehicles", {
        method: "GET"
    }).then(response => {
        if (!response.ok) {
            throw new Error("Network error");
        }
        return response.json();
    }).then(data => {
        console.log(data);
        updateVehicleTable(data);
    }).catch(error => {
        console.error("error", error);
    });
}

function updateVehicleTable(data) {
    let tableBody = document.getElementById("vehicleTableBody");
    tableBody.innerHTML = "";
    data.forEach(employee => {
        let row = document.createElement("tr");
        // row.setAttribute("onclick", "openUpdateModal(this)");
        let stat;
        if(employee.status){
            stat = "available";
        }else{
            stat = "not-available";
        }
        let cells = [
            employee.vehicle_id,
            employee.capacity,
            employee.type,
            stat
        ];
        cells.forEach(cellData => {
            let cell = document.createElement("td");
            cell.textContent = cellData;
            row.appendChild(cell);
        });

        tableBody.appendChild(row);
    });
}

let addVehicleModal;
function openAddVehicleModal(){
    let addVehicle = document.getElementById("addVehicleModal");
    addVehicleModal = new bootstrap.Modal(addVehicle);
    addVehicleModal.show();
    getVehicleTypes();
}

function getVehicleTypes(){
    fetch("getVehicleTypesForUpdate", {
        method: "GET"
    }).then(response => {
        if (!response.ok) {
            throw new Error("Network error");
        }
        return response.json();
    }).then(data => {
        console.log(data);
        updateDropdownForVehicleType(data);
    }).catch(error => {
        console.error("error", error);
    });
}

function updateDropdownForVehicleType(data){
    let vehicleTypeDropdown = document.getElementById("vehicleType");

    vehicleTypeDropdown.innerHTML = "";

    data.forEach(vehicleType => {
        let option = document.createElement("option");
        option.value = vehicleType;
        option.textContent = vehicleType;
        vehicleTypeDropdown.appendChild(option);
    });
}

function addVehicle(){

    let vehicle_type = document.getElementById("vehicleType").value;
    let capacity = document.getElementById("capacity").value;

    if (!isPositiveNumber(capacity)) {
        alert("Capacity must be a positive number.");
        return;
    }

    let data = {
        "vehicle_type" : vehicle_type,
        "capacity" : capacity,
    }

    fetch("addVehicle", {
        method: "POST",
        body: JSON.stringify(data)
    }).then(response => {
        if (!response.ok) {
            throw new Error("Network error");
        }
        getAllVehicles();
        return response.text();
    }).then(data => {
        addVehicleModal.hide();
    }).catch(error => {
        console.error("error", error);
    });
}
function isPositiveNumber(value) {
    return !isNaN(value) && parseFloat(value) > 0;
}