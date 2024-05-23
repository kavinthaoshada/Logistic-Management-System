function addProductToTable() {

    var product_name = document.getElementById("product_name").value;
    var product_description = document.getElementById("product_description").value;
    var product_quantity = document.getElementById("product_quantity").value;
    var tot_weight = document.getElementById("tot_weight").value;

    if (product_name.length === 0) {
        alert("Product name cannot be empty.");
    } else if (product_quantity <= 0) {
        alert("quantity cannot be lower than or equal to 0.");
    } else if (tot_weight.length === 0) {
        alert("Total weight cannot be empty.");
    } else {

        let tr = document.getElementById('productTable').insertRow(1);

        let y1 = tr.insertCell(0);
        let y2 = tr.insertCell(1);
        let y3 = tr.insertCell(2);
        let y4 = tr.insertCell(3);
        y1.innerHTML = product_name;
        y2.innerHTML = product_description;
        y3.innerHTML = product_quantity;
        y4.innerHTML = tot_weight;

        document.getElementById("product_name").value = "";
        document.getElementById("product_description").value = "";
        document.getElementById("product_quantity").value = 0;
        document.getElementById("tot_weight").value = "";

    }
}

function addShipment(){
    let tlength = (document.getElementById("productTable").rows.length) - 1;
    let cus_id = document.getElementById("cus_id").value;

    let items = [];
    for (let i = 1; i <= tlength; i++) {
        let item = {
            "product_name": document.getElementById("productTable").rows[i].cells[0].innerHTML,
            "description": document.getElementById("productTable").rows[i].cells[1].innerHTML,
            "quantity": document.getElementById("productTable").rows[i].cells[2].innerHTML,
            "tot_weight": document.getElementById("productTable").rows[i].cells[3].innerHTML
        };
        items.push(item);
    }

    let product_list = {
        "tlength": tlength,
        "customer_id": cus_id,
        "items": items
    };

    if(tlength>0) {
        fetch("addShipment", {
            method: "POST",
            body: JSON.stringify(product_list)
        }).then(response => {
            if (!response.ok) {
                throw new Error("Network error");
            }
            return response.text();
        }).then(data => {
            alert("Shipment added successful..");
            location.reload();
        }).catch(error => {
            console.error("error", error);
        });
    }else {
        alert("You must add at least one product..");
    }
}