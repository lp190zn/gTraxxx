<!--
To change this template, choose Tools | Templates
and open the template in the editor.
-->
<%
    session.removeAttribute("trackFilename");
    session.removeAttribute("trackName");
    session.removeAttribute("trackDescr");
    session.removeAttribute("trackActivity");
%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Register page</title>

    <!-- Bootstrap core CSS -->
    <link href="HTMLStyle/LoginPageStyle/css/bootstrap.css" rel="stylesheet" type="text/css">
    
    <script type="text/javascript" src="HTMLStyle/LoginPageStyle/js/jquery.min.js"></script>
    <script type="text/javascript" src="HTMLStyle/LoginPageStyle/js/bootstrap.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.0.5/angular.js"></script>
    
    <script>
        
        //var app = angular.module('myapp', []); 
        'use strict';
        angular.module('myApp', ['myApp.directives']);
        /* Controllers */
        function stageController($scope) {
            $scope.pw1 = 'password';
        }   
        /* Directives */
        angular.module('myApp.directives', [])
        .directive('pwCheck', [function () {
            return {
            require: 'ngModel',
            link: function (scope, elem, attrs, ctrl) {
                var firstPassword = '#' + attrs.pwCheck;
                    elem.add(firstPassword).on('keyup', function () {
                    scope.$apply(function () {
                    // console.info(elem.val() === $(firstPassword).val());
                    ctrl.$setValidity('pwmatch', elem.val() === $(firstPassword).val());
                    });
                });
            }
        }
        }]);

        </script>    

    <!-- Add custom CSS here -->
    <link href="HTMLStyle/LoginPageStyle/css/stylish-portfolio.css" rel="stylesheet" type="text/css">
  
  </head>

  <body ng-app="myApp">  
    <!-- Full Page Image Header Area -->
    <div id="top" class="header1">
       
       <div class="container">
           
           <div id="top1"> 
                 <a id="modal-49447" href="#modal-container-49447" data-toggle="modal"></a>
			
                <div class="modal fade" id="modal-container-49447" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
			<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
					<h3 class="modal-title" id="myModalLabel">
                                            <b>This email has been already used!!!</b>
					</h3>
			</div>
			<div class="modal-body">
				Click on the button and write new email adress...
			</div>
			<div class="modal-footer">
			<button type="button" class="btn btn-primary" data-dismiss="modal">OK</button>
				</div>
			</div>
					
		</div></div></div>
       
           <form action="Register.jsp" name="form" class="form-signin" method="POST" accept-charset="Windows-1250">
        <h2 class="form-signin-heading">Registration form</h2>
        <div ng-class="{'has-error': form.Login.$invalid, 'has-success': !form.Login.$invalid}">
        <input type="email" name="Login" class="form-control" placeholder="Your email address (Required)" required = "required" value="" ng-model="user.email" >
        </div>
        <br>
        <input type="text" name="FirstName" class="form-control" placeholder="Your firstname" value="">
        <br>
        <input type="text" name="LastName" class="form-control" placeholder="Your lastname" value="">
        <br>
        <input type="number" name="Age" class="form-control" placeholder="Age" value="" min="1">
        <br>
        <select name="Activity" class="form-control">
            <option value=" " disabled selected>Select your Activity</option>
            <option value="Hiking">Hiking</option>
            <option value="Climbing">Climbing</option>
            <option value="Moto cycling">Moto cycling</option>
            <option value="Road tripping">Road tripping</option>
            <option value="Road cycling">Road cycling</option>
            <option value="Mountain biking">Mountain biking</option>
            <option value="Sailing">Sailing</option>
            <option value="Canoeing">Canoeing</option>
            <option value="Windsurfing">Windsurfing</option>
            <option value="Kiteboarding">Kiteboarding</option>
            <option value="Paragliding">Paragliding</option>
            <option value="Flying">Flying</option> 
            <option value="Other">Other</option>
        </select>
        <br>
        <div id="1" name = "1" ng-class="{'has-error': !form.Pass.$valid,  'has-success': form.Pass.$valid}">
        <input type="password" id="Pass" name="Pass" class="form-control" placeholder="Your password (Required)" required = "required" value="" ng-model="Pass">
        </div>
        <br>
        <div id="2" name = "2" ng-class="{'has-error': form.RetypePass.$error.pwmatch || !form.RetypePass.$valid, 'has-success': !form.RetypePass.$error.pwmatch && form.RetypePass.$valid}" ng-controller="stageController">  
        <input type="password" id="RetypePass" name="RetypePass" class="form-control" placeholder="Retype your password (Required)" required = "required" value="" ng-model="RetypePass" pw-check="Pass"> 
        </div>
        <br><br>
        <script language='javascript' type='text/javascript'>
            function check() {
            if (document.form.Pass.value != document.form.RetypePass.value) {
                document.form.RetypePass.setCustomValidity('The two passwords must match!');
            } else {
                document.form.RetypePass.setCustomValidity('');
            }
        }
        </script>
        <%
            if (session.getAttribute("incorrectValues") != null && session.getAttribute("incorrectValues").toString().equals("email")) {
                                
                out.print("<script>$(document).ready(function() {$('#top1').find('a').trigger('click');});</script>");
                
                session.removeAttribute("incorrectValues");
                //out.print("<script>alert(\"This email has been already used!\")</script>");
                
            }
        %>
        <button ng-disabled="!form.$valid" class="btn btn-lg btn-primary btn-block" type="submit" onClick="return check();">Register</button>
      </form>
        <a href="LoginPage.jsp" class="label" style="">Back to Login page!</a>

    </div>
    </div>
  </body>

</html>
