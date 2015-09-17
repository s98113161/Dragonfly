<%@ page language="java" contentType="text/html; charset=BIG5"
	pageEncoding="BIG5"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="../resources/css/authenticate.css" rel="stylesheet" type="text/css">
<link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" rel="stylesheet">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=Big5">
</head>
<body>
	<div class="container">
		<div class="table">
			<h3 class="header text-center">授權認證</h3>
			<div class="container-fluid">
				<div class="row">
					<div class="col-md-4">
					<div>
						<img class="center-block"
							src="http://icons.iconarchive.com/icons/double-j-design/origami-colored-pencil/256/blue-user-icon.png"></img>
					</div>
					<div>
					<p><strong>Device Name</strong></p>
					</div>
					</div>
					<h4 class="col-md-8 text-left ">關於這應用程式的內容，關於這應用程式的內容，關於這應用程式的內容，關於這應用程式的內容...</h4>
				</div>
				<h4><strong>這個應用程式必須先取得授權，同意此裝置存取您的個人資訊？</strong></h4>
			</div>

			<div id="buttons" class="row">
				<div class="text-right col-md-6 col-sm-6 col-xs-6">
					<button type="button" class="btn btn-primary">同意</button>
				</div>
				<div class="text-left col-md-6 col-sm-6 col-xs-6">
					<button type="button" class="btn btn-warning ">取消</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>