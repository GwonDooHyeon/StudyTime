<!-- ---------------------------------------------------------->
<!-- 최초작성자 : 권두현(secure3141@naver.com) -->
<!-- 최초작성일 : 2023/02/15 -->

<!-- 버전 기록 : ver1(시작 23/02/15) -->
<!-- ---------------------------------------------------------->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>STUDY TIME</title>

  <!-- Bootstrap CSS -->
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
    integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

  <style>
    body {
      min-height: 100vh;

     /*background: -webkit-gradient(linear, left bottom, right top, from(#92b5db), to(#1d466c));
      background: -webkit-linear-gradient(bottom left, #92b5db 0%, #1d466c 100%);
      background: -moz-linear-gradient(bottom left, #92b5db 0%, #1d466c 100%);
      background: -o-linear-gradient(bottom left, #92b5db 0%, #1d466c 100%);
      background: linear-gradient(to top right, #92b5db 0%, #1d466c 100%); */
    }

    .input-form {
      max-width: 680px;

      margin-top: 80px;
      padding: 32px;

      background: #fff;
      -webkit-border-radius: 10px;
      -moz-border-radius: 10px;
      border-radius: 10px;
      -webkit-box-shadow: 0 8px 20px 0 rgba(0, 0, 0, 0.15);
      -moz-box-shadow: 0 8px 20px 0 rgba(0, 0, 0, 0.15);
      box-shadow: 0 8px 20px 0 rgba(0, 0, 0, 0.15)
    }
    
    #msg {color : red;}
  </style>
</head>
<body>
<%@ include file="/includes/header.jsp" %>

<div class="container">
    <div class="input-form-backgroud row">
      <div class="input-form col-md-6 mx-auto">

          <div class="mb-3">
            <label for="name">이름</label>
            <input type="text" class="form-control" id="name" name="name" required>
            <div class="invalid-feedback">
              이름을 입력해주세요.
            </div>
          </div>
          
          <div class="mb-3">
            <label for="phone">휴대폰 번호</label>
            <input type="text" class="form-control" id="phone" name="phone" required>
            <div class="invalid-feedback">
              전화번호를 입력해주세요.
            </div>
          </div>
          
          <hr class="mb-4">
          
          <button class="btn btn-primary btn-lg btn-block" id="findBtn" type="submit">아이디 찾기</button>
      </div>
    </div>
    
    <footer class="my-3 text-center text-small">
      <p class="mb-1">&copy; STUDY TIME</p>
    </footer>
  </div>


<div class="modal" tabindex="-1">
  <div class="modal-dialog">
    <div class="modal-content">
<!--       <div class="modal-header">
        <h5 class="modal-title">Modal title</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div> -->
      <div class="modal-body">
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-warning" onclick="location.href='/notice/noticeView.jsp'">닫기</button>
      </div>
    </div>
  </div>
</div>



<script>
  
	$('#home').hide();
  
    window.addEventListener('load', () => {
      const forms = document.getElementsByClassName('validation-form');

      Array.prototype.filter.call(forms, (form) => {
        form.addEventListener('submit', function (event) {
          if (form.checkValidity() === false) {
            event.preventDefault();
            event.stopPropagation();
          }

          form.classList.add('was-validated');
        }, false);
      });
    }, false);
  
//  아이디 찾기 ajax
$('#findBtn').on('click', function(){
	$.ajax({
		type : "post",
		url : "idFindChk.jsp",
		data : {name:$('#name').val(),
			    phone:$('#phone').val()
		},
		dataType:"text",
		success : function(result){
			var result = result.trim();
			if(result != null){
				$('.modal-body').html('회원님의 아이디는<span id="msg">' + result + '</span>입니다.');
			}	else{
				$('.modal-body').html('이름과 전화번호가 일치하지 않습니다.');
			}
			$('.modal').show()
		}
		
	});
});
  
  
</script>

</body>
</html>