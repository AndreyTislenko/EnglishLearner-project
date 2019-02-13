let variants = [];
let usersAnswer = [];
let tested = false;

let menu = ['Question 1', 'Question 2', 'Question 3', 'Question 4', 'Question 5'];

$(document).ready(function() {
    //menu.length should be less than table.records.length
    $.when($.get("questions", questions => variants = questions)).then(function() {
        let mySwiper = new Swiper ('.swiper-container', {
            // If we need pagination
            pagination: {
                el: '.swiper-pagination',
                clickable: true,
                renderBullet: function (index, className) {
                    return '<span class="' + className + '">' + (menu[index]) + '</span>';
                }
            },
            // Navigation arrows
            navigation: {
                nextEl: '.swiper-button-next',
                prevEl: '.swiper-button-prev',
            }
        });

        setViewFor(variants);

        $("ul").on("click","li", function(event) {
            let bies = $(this).parent("ul").find("a");
            bies.each(function() {
                $(this).removeClass("selected");
            });

            let word = $(this).closest("div").find("h3").text();
            let answer =  $(this).find("a").addClass("selected").text();

            let indexOfTheSameWord;
            if(usersAnswer.length === 0 || !usersAnswer.some((element, index) => {indexOfTheSameWord = index; return element.word === word;})) {
                usersAnswer.push({
                    word : word,
                    answer : answer
                });
            } else {
                usersAnswer[indexOfTheSameWord].answer = answer;
            }
        });

        $("#test").click(function() {
            if(usersAnswer.length < menu.length) {
                alert("You didn't answer completely yet.");
            } else {
                $.ajax({
                    type : "post",
                    url: "userResult",
                    data : JSON.stringify(usersAnswer),
                    contentType : "application/json",
                    dataType : "json",
                    success : function () {
                        tested = true;
                        window.location.href = "http://localhost:8080/testResult";
                    },
                    error : function(e) {
                        alert("Error!");
                        console.log("ERROR: ", e);
                    }
                });
            }
        });

        $("#finish").click(function() {
            if($(".selected").length !== 5 || !tested) {
                if(confirm("Are you sure? You didn't test your answers yet.")) {
                    window.location.href = "http://localhost:8080";
                }
            } else {
                window.location.href = "http://localhost:8080";
            }
        });
    });
});

function setViewFor(variants) {
    $(".swiper-slide").each(function(index) {
        $(this).find("h3").text(variants[index].word);
        $(this).find("b").each(function(i) {
            $(this).text(variants[index].translations[i]);
        });
    });
}