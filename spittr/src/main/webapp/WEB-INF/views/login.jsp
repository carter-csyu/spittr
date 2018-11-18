<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h3>Login with Username and Password</h3>
<div style="display: inline-block;">
	<form name="f" method="post" action="/login">
		<table>
			<tbody>
				<tr>
					<td>User:</td>
					<td><input type="text" id="username" name="username" /></td>
				</tr>
				<tr>
					<td>Password:</td>
					<td><input type="password" id="password" name="password" /></td>
				</tr>
				<tr>
          <td colspan="2">
            <input type="checkbox" id="remember-me" name="remember-me" />
            <label for="remember-me">Remember me</label>
          </td>
        </tr>
				<tr>
					<td colspan="2">
					  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					  <input type="submit" value="Login" />
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</div>