$(document).ready(
    function() {
        $("#userId").prop("maxLength", 30);
        $("#userPassword").prop("maxLength", 30);

        $("#login").click(
            function() {
                var userId       = $("#userId").val();
                var userPassword = $("#userPassword").val();

                if (userId == "") {
                    modal({
                        type: 'error',
                   		title: '로그인에러',
                   		text: "ID를 입력해 주세요",
                        closeClick: true, //Close Modal on click near the box
				        closable: true, //If Modal is closable
                   	});
                } else if (userPassword == "") {
                    modal({
                        type: 'error',
                   		title: '로그인에러',
                   		text: "비밀번호를 입력해 주세요",
                        closeClick: true, //Close Modal on click near the box
				        closable: true, //If Modal is closable
                   	});
                }
            }
        );
    }
);