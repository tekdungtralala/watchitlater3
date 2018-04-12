package info.wiwitadityasaputra.util.api;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeCtrl extends AbstractCtrl {

	@RequestMapping(value = { "/", "/top100", "/latest", "/register", "/login",
			"/dashboard" }, method = RequestMethod.GET)
	public String getIndex(Model model) {
		return "index";
	}
}
