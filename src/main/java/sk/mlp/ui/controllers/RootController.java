package sk.mlp.ui.controllers;

import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.base.Strings;

import sk.mlp.database.DatabaseServices;
import sk.mlp.security.EmailSender;
import sk.mlp.ui.model.User;
import sk.mlp.util.AuthUtils;
import sk.mlp.util.Constants;
import sk.mlp.util.DateEditor;

@Controller
@RequestMapping(value = "/")
public class RootController {

	@InitBinder
	public void initBinder(final WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateEditor());
	}

	private DatabaseServices databaseServices;

	@RequestMapping(value = { "" }, method = RequestMethod.GET)
	public String welcome() throws Exception {
		if (AuthUtils.hasRole(Constants.ApplicationRoles.ADMINISTRATORS.getValue())) {
			return "redirect:/Logged/HomePage.jsp";
		} else {
			return "redirect:/Logged/HomePage.jsp";
		}
	}

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String getLogin(Model model) throws Exception {
		return "login.html";
	}

	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String doLogout(HttpServletRequest request, Model model, final RedirectAttributes redirect) throws Exception {
		if (request.getSession() != null) {
			request.logout();
		}

		redirect.addFlashAttribute("info", true);
		redirect.addFlashAttribute("message", "Signed out");

		return "redirect:/login";
	}

	@RequestMapping(value = { "login" }, method = RequestMethod.POST)
	public String login(final HttpSession session, @RequestParam("j_username") String username, @RequestParam("j_password") String password, HttpServletRequest request, Model model, final RedirectAttributes redirect) throws Exception {

		try {
			request.login(username, password);
		} catch (ServletException e) {
			redirect.addFlashAttribute("failed", true);
			return "redirect:/login";
		}
		databaseServices = new DatabaseServices();
		boolean isUserAccepted = databaseServices.findUserByEmail(username).getUserAccepted();

		if (!isUserAccepted) {
			session.setAttribute("notActive", true);
			return "redirect:/login";
		}
		request.getSession().setAttribute("username", username);
		request.getSession().setAttribute(":logged", true);

		if (AuthUtils.hasRole(Constants.ApplicationRoles.ADMINISTRATORS.getValue())) {
			// TODO zrusit bude sa to robit podla roli spring
			request.getSession().setAttribute("Admin", "True");

		} else {
			request.getSession().setAttribute("Admin", "False");
		}
		return "redirect:/Logged/HomePage.jsp";

	}

	@RequestMapping(value = "register", method = RequestMethod.GET)
	public String doRegister(Model model) throws Exception {
		model.addAttribute("user", new User());
		return "register.html";
	}

	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String doCreateUser(@ModelAttribute User user, Model model, final HttpServletRequest request, final HttpSession session, final RedirectAttributes redirect) throws Exception {

		if (!Strings.isNullOrEmpty(user.getUserPass()) && !Strings.isNullOrEmpty(user.getRetypeUserPass()) && user.getUserPass().equals(user.getRetypeUserPass())) {
			DatabaseServices databaseServices = new DatabaseServices();
			User existingUser = databaseServices.findUserByEmail(user.getUserEmail());
			if (existingUser == null) {
				EmailSender sender = new EmailSender("smtp.gmail.com", "skuska.api.3", "skuskaapi3");
				String token = sender.getNewUserToken();
				user.setUserToken(token);
				sender.sendUserAuthEmail(user.getUserEmail(), token, user.getUserFirstName(), user.getUserLastName());
				databaseServices.createNewUser(user);
				redirect.addFlashAttribute("justRegistered", true);
				return "redirect:/login";
			} else {
				redirect.addFlashAttribute("usedEmail", true);
				return "redirect:/register";
			}
		} else {
			return "redirect:/register";
		}
	}

}
