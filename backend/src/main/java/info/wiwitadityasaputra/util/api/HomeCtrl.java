package info.wiwitadityasaputra.util.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeCtrl extends AbstractCtrl {

	private Logger logger = LogManager.getLogger(HomeCtrl.class);

	@RequestMapping(value = { "/", "/top100", "/latest", "/register", "/login",
			"/dashboard" }, method = RequestMethod.GET)
	public String getIndex(Model model) {
		logger.info("HomeCtrl getIndex()");
		return "index";
	}
}
