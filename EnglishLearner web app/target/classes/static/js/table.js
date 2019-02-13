//Model
let tableModal = {};
let currentRecordIndexOnAction;

$(document).ready(function(){
    $.when(ajaxGetTable("table")).then(function () {
        setViewForModel(tableModal);
    });

    let tbody = $("table tbody");
    tbody.on("click",".edit", function() {
        currentRecordIndexOnAction = $(this).closest("tr").index();
        let record = tableModal.records[currentRecordIndexOnAction];
        let inputs = $("#editRecordModal").find('input[type="date"], input[type="text"], textarea');

        let recordArray = $.map(record, value => [value]);

        console.log(recordArray);

        inputs.each(function(index) {
            if(index === 0) {
                $(this).val(convertDate(new Date(), "-"));
            } else {
                $(this).val(recordArray[index]);
            }
        });
    });
    tbody.on("click", ".delete", function() {
        currentRecordIndexOnAction = $(this).closest("tr").index();
    });

    $("a.btn-success").click(function() {
        $("#addRecordModal").find("input[type='date']").val(convertDate(new Date(), "-"));
    });

    $("a.save").click(function() {
        saveTableModal("save");
    });

    $("a.test").click(function() {
        window.location.href = "http://localhost:8080/userTest";
    });

    //testing google translate
    /*$.ajax({
        type : "post",
        url: "googleTranslate",
        data : JSON.stringify("success"),
        contentType : "application/json",
        dataType : "json",
        success : function (response) {
            console.log(response);
        },
        error : function(e) {
            alert("Error!");
            console.log("ERROR: ", e);
        }
    });*/
});

//Controller
function ajaxGetTable(mapping) {
    return $.get(mapping, function(table) {
        tableModal = table;
        console.log("Success: ", tableModal);
    });
}
function saveTableModal(mapping){
    $.ajax({
        type : "post",
        url: mapping,
        data : JSON.stringify(tableModal),
        contentType : "application/json",
        dataType : "json",
        success : function () {
            alert("Table successfully saved!");
        },
        error : function(e) {
            alert("Error!");
            console.log("ERROR: ", e);
        }
    });
}

//View
function setViewForModel(tableModal, indexOfRecordToBeginWith = 0) {
    let actions = '<td>' +
        '<a href="#editRecordModal" class="edit" data-toggle="modal"><i class="material-icons" data-toggle="tooltip" title="Edit">&#xE254;</i></a>' +
        '<a href="#deleteRecordModal" class="delete" data-toggle="modal"><i class="material-icons" data-toggle="tooltip" title="Delete">&#xE872;</i></a>' +
        '</td>';

    $.each(tableModal.records, function (recordNumber, record) {
        if(recordNumber >= indexOfRecordToBeginWith) {
            let checkBox = '<span class="custom-checkbox">' +
                `<input type="checkbox" id="checkbox${recordNumber}" name="options[]" value="1">` +
                `<label for="checkbox${recordNumber}"></label>` +
                '</span>';

            let row = '<tr>' +
                `<td>${checkBox}</td>` +
                `<td>${convertDate(record.date, '-')}</td>` +
                `<td>${record.word}</td>` +
                `<td>${record.translation}</td>` +
                `<td>${record.examplesOfUsage}</td>` +
                `<td>${record.definition}</td>` +
                `<td>${actions}</td>` +
                '</tr>';

            $("table tbody").append(row);
        }
    });

    setCheckboxSettings();
}
function convertDate(inputFormat, joint, direction) {
    function pad(s) { return (s < 10) ? '0' + s : s; }
    let d = new Date(inputFormat);
    if(direction === "right") {
        return [pad(d.getDate()), pad(d.getMonth()+1), d.getFullYear()].join(joint);
    }
    return [d.getFullYear(), pad(d.getMonth()+1), pad(d.getDate())].join(joint)
}
function setCheckboxSettings() {
    // Activate tooltip
    $('[data-toggle="tooltip"]').tooltip();
    // Select/Deselect checkboxes
    var checkbox = $('table tbody input[type="checkbox"]');
    $("#selectAll").click(function(){
        if(this.checked){
            checkbox.each(function(){
                this.checked = true;
            });
        } else{
            checkbox.each(function(){
                this.checked = false;
            });
        }
    });
    checkbox.click(function(){
        if(!this.checked){
            $("#selectAll").prop("checked", false);
        }
    });
}


//event handlers for submit events
function addRecordToTableModel(event) {
    event.preventDefault();
    let addRecordModal = $("#addRecordModal");

    let empty = false;
    let inputs = addRecordModal.find('input[type="date"], input[type="text"], textarea');

    inputs.each(function () {if (!$(this).val()) empty = true;});

    if (!empty) {
        let rowData = [];
        inputs.each(function () {
            let field = $(this).val();
            rowData.push(field);
        });

        tableModal.records.push({
            date: rowData[0],
            word: rowData[1],
            translation: rowData[2],
            examplesOfUsage: rowData[3],
            definition: rowData[4],
        });
    }

    setViewForModel(tableModal, tableModal.records.length - 1);

    addRecordModal.modal("toggle");
}
function editRecordInTableModel(event) {
    event.preventDefault();
    let editRecordModel = $("#editRecordModal");

    let empty = false;
    let inputs = editRecordModel.find('input[type="date"], input[type="text"], textarea');

    inputs.each(function () {if (!$(this).val()) empty = true;});

    if (!empty) {
        let rowData = [];
        inputs.each(function () {
            let field = $(this).val();
            rowData.push(field);
        });

        tableModal.records.splice(currentRecordIndexOnAction, 1, {
            date: rowData[0],
            word: rowData[1],
            translation: rowData[2],
            examplesOfUsage: rowData[3],
            definition: rowData[4],
        });
    }

    $("table tbody").empty();
    setViewForModel(tableModal);

    editRecordModel.modal("toggle");
}
function deleteRecordInTableModel(event) {
    event.preventDefault();
    tableModal.records.splice(currentRecordIndexOnAction, 1);
    $("table tbody").empty();
    setViewForModel(tableModal);
    $("#deleteRecordModal").modal("toggle");
}
function deleteSelectedRecordsInTableModel(event) {
    event.preventDefault();

    let checkboxes = $('table tbody input[type="checkbox"]');
    checkboxes.each(function() {
        if(this.checked) {
            let indexOfSelectedRow = $(this).closest("tr").index();
            tableModal.records[indexOfSelectedRow] = undefined;
        }
    });

    let newRecords = [];
    for(let record of tableModal.records) {
        if(record !== undefined) {
            newRecords.push(record);
        }
    }
    tableModal.records = newRecords;

    $("table tbody").empty();
    setViewForModel(tableModal);

    $("#deleteSelectedRecordsModal").modal("toggle");
}