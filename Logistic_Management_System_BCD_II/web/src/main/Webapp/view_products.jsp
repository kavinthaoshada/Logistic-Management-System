<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>View Product</title>
    <link rel="stylesheet" href="css/style.css"/>
    <link rel="stylesheet" href="css/bootstrap.css"/>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css">
</head>
<body>
<div class="col-3 offset-1 mt-2 mb-2">
    <jsp:include page="offcanvas.jsp"/>
</div>

<div class="row">

    <div class="col-10 offset-1 mt-2 mb-2">
        <div class="row">
            <div class="col-4 offset-7 mt-1 mb-1">
                <button class="btn btn-outline-primary" type="button"
                        onclick="openAddProductModal();">Add Product
                </button>
            </div>
        </div>

        <input type="text" id="myInput" onkeyup="filterProductTable();" placeholder="Search for names.."
               title="Type in a name">

        <table id="myTable">
            <thead>
            <tr class="header">
                <th style="width:20%;">Id</th>
                <th style="width:20%;">Name</th>
                <th style="width:20%;">Description</th>
                <th style="width:20%;">Quantity Available</th>
                <th style="width:20%;">Unit Price</th>
            </tr>
            </thead>
            <tbody id="productTableBody">

            </tbody>
        </table>
    </div>

</div>

<script src="js/product_script.js"></script>
<script src="js/bootstrap.bundle.js"></script>
<script src="js/bootstrap.js"></script>

<script type="text/javascript">
    window.onload = function () {
        getAllProduct();
    };

    function filterProductTable() {
        var input, filter, table, tr, td, i, txtValue;
        input = document.getElementById("myInput");
        filter = input.value.toUpperCase();
        table = document.getElementById("myTable");
        tr = table.getElementsByTagName("tr");
        for (i = 0; i < tr.length; i++) {
            td = tr[i].getElementsByTagName("td")[1];
            if (td) {
                txtValue = td.textContent || td.innerText;
                if (txtValue.toUpperCase().indexOf(filter) > -1) {
                    tr[i].style.display = "";
                } else {
                    tr[i].style.display = "none";
                }
            }
        }
    }
</script>
</body>
</html>
