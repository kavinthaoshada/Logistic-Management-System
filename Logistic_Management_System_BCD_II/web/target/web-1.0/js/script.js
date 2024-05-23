function m1() {
    alert("m1");
}

function getAllEmployees() {
    fetch("getEmployees", {
        method: "GET"
    }).then(response => {
        if (!response.ok) {
            throw new Error("Network error");
        }
        return response.json();
    }).then(data => {
        console.log(data);
        updateTable(data);
    }).catch(error => {
        console.error("error", error);
    });
}

function updateTable(data) {
    let tableBody = document.getElementById("employeeTableBody");
    tableBody.innerHTML = "";
    data.forEach(employee => {
        let row = document.createElement("tr");
        row.setAttribute("onclick", "openUpdateModal(this)");
        let cells = [
            employee.id,
            employee.email,
            employee.userName,
            employee.password,
            employee.registered_date,
            employee.user_type.name,
            employee.role
        ];
        cells.forEach(cellData => {
            let cell = document.createElement("td");
            cell.textContent = cellData;
            row.appendChild(cell);
        });

        tableBody.appendChild(row);
    });
}

function openUpdateModal(row) {
    let empId = row.cells[0].textContent;
    let empUsername = row.cells[2].textContent;

    document.getElementById("emp_id").innerText = empId;
    document.getElementById("emp_uname").innerText = empUsername;

    let updateChooser = document.getElementById("updateChooser");
    updateChooserModal = new bootstrap.Modal(updateChooser);
    updateChooserModal.show();
}

let updateRoleModal;
function openUpdateRoleModal(){
    let empId = document.getElementById("emp_id").innerText;

    document.getElementById("emp_id2").innerText = empId;

    if (updateChooserModal != null) {
        updateChooserModal.hide();
    }

    let updateRole = document.getElementById("updateRole");
    updateRoleModal = new bootstrap.Modal(updateRole);
    updateRoleModal.show();
}

function updateRole(){
    let empId = document.getElementById("emp_id2").innerText;
    let role = document.getElementById("role").value;

    fetch("updateEmployeeRole?id="+empId+"&role="+role, {
        method: "GET"
    }).then(response => {
        if (!response.ok) {
            throw new Error("Network error");
        }
        updateRoleModal.hide();

        return response.json();
    }).then(data => {
        console.log(data);
        getAllEmployees();
    }).catch(error => {
        console.error("error", error);
    });
}

let addEmployeeModal;
function openAddEmployeeModal(){
    let addEmployee = document.getElementById("addEmployeeModal");
    addEmployeeModal = new bootstrap.Modal(addEmployee);
    addEmployeeModal.show();
    getUserTypes();
}

function getUserTypes(){
    fetch("getUserTypes", {
        method: "GET"
    }).then(response => {
        if (!response.ok) {
            throw new Error("Network error");
        }
        return response.json();
    }).then(data => {
        console.log(data);
        updateDropdownForUsertype(data);
    }).catch(error => {
        console.error("error", error);
    });
}

function updateDropdownForUsertype(data){
    let userTypeDropdown = document.getElementById("usertype");

    userTypeDropdown.innerHTML = "";

    data.forEach(userType => {
        let option = document.createElement("option");
        option.value = userType.name;
        option.textContent = userType.name;
        userTypeDropdown.appendChild(option);
    });
}

function addEmployee(){
    let email = document.getElementById("email").value;
    let username = document.getElementById("username").value;
    let usertype = document.getElementById("usertype").value;
    let password = document.getElementById("password").value;
    let user_role = document.getElementById("user_role").value;

    let data = {
        "email" : email,
        "username" : username,
        "usertype" : usertype,
        "password" : password,
        "user_role" : user_role
    }

    fetch("addEmployees", {
        method: "POST",
        body: JSON.stringify(data)
    }).then(response => {
        if (!response.ok) {
            throw new Error("Network error");
        }
        getAllEmployees();
        return response.text();
    }).then(data => {
        addEmployeeModal.hide();
    }).catch(error => {
        console.error("error", error);
    });

}

let assignShipmentModal;
function OpenAssignShipmentModal(){
    let empId = document.getElementById("emp_id").innerText;

    document.getElementById("employee_id").value = empId;

    if (updateChooserModal != null) {
        updateChooserModal.hide();
    }

    let assignShipment = document.getElementById("assignShipmentModal");
    assignShipmentModal = new bootstrap.Modal(assignShipment);
    assignShipmentModal.show();
    getShipmentsToAssign();
}

function getShipmentsToAssign(){
    fetch("getAllShipments", {
        method: "GET"
    }).then(response => {
        if (!response.ok) {
            throw new Error("Network error");
        }
        return response.json();
    }).then(data => {
        console.log(data);
        updateShipmentsTableToAssign(data);
    }).catch(error => {
        console.error("error", error);
    });
}

function updateShipmentsTableToAssign(data){
    let tableBody = document.getElementById("shipmentTableBodyToAssign");
    tableBody.innerHTML = "";

    data.forEach(shipment => {
        let row = document.createElement("tr");

        let cells = [
            shipment.order_id,
            shipment.created_at,
            shipment.customer_id.customer_id,
            shipment.status,
        ];

        cells.forEach(cellData => {
            let cell = document.createElement("td");
            cell.textContent = cellData;
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

function assignShipments(){
    let employeeId = document.getElementById("employee_id").value;

    let checkedOrderIds = [];

    let checkboxes = document.querySelectorAll('#shipmentTableBodyToAssign input[type="checkbox"]');
    checkboxes.forEach(checkbox => {
        if (checkbox.checked) {
            let row = checkbox.closest("tr");
            let orderIdCell = row.cells[0];
            let orderId = orderIdCell.textContent.trim();
            checkedOrderIds.push(orderId);
        }
    });

    // alert("Checked Order IDs:"+checkedOrderIds);
    // console.log("Checked Order IDs:", checkedOrderIds);
    let arr = {
        "employeeId" : employeeId,
        "checkedOrderIds" : checkedOrderIds
    }

    fetch("assignShipmentsToEmployee", {
        method: "POST",
        body: JSON.stringify(arr)
    }).then(response => {
        if (!response.ok) {
            throw new Error("Network error");
        }
        getAllEmployees();
        return response.text();
    }).then(data => {
        assignShipmentModal.hide();
    }).catch(error => {
        console.error("error", error);
    });
}
