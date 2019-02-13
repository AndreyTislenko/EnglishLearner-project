let score;
let results;
let isRight ;

$(document).ready(function() {
    score = 0;

    $.when($.get("getSessionScopedUserResults", userResults => results = userResults)).then(function() {
        $.get("userResult", function(result) {
            isRight = result;
            score = result.reduce((current, bool) => current + bool, 0);

            $("#myModal p").text(`Congratulations! You score is ${score} out of 5. You can finish test now.`);

            results.forEach(function(currentResult, index) {
                let row = `<tr><td>${currentResult.word}</td><td><b>${currentResult.answer}</b></td></tr>`;
                let tbody = $("#myModal tbody");
                tbody.append(row);
            });

            $(".modal-body b").each(function(index) {
                if(isRight[index]) {
                    $(this).addClass("right");
                } else {
                    $(this).addClass("wrong");
                }
            });

            $('#myModal').modal('show');
            console.log(result);
        })
    });

    $(".test").click(function() {
        window.location.href = "http://localhost:8080/userTest";
    });

    $(".table").click(function() {
        window.location.href = "http://localhost:8080/";
    });

});

