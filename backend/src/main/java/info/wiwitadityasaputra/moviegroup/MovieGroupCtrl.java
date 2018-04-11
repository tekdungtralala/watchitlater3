package info.wiwitadityasaputra.moviegroup;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import info.wiwitadityasaputra.util.api.AbstractCtrl;
import info.wiwitadityasaputra.util.api.ApiPath;

@RestController
@RequestMapping(value = ApiPath.API_MOVIEGROUP)
public class MovieGroupCtrl extends AbstractCtrl {

	private Logger logger = LogManager.getLogger(MovieGroupCtrl.class);

	private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	private MovieGroupRepository movieGroupRepo;

	@RequestMapping(method = RequestMethod.GET)
	public MovieGroupResp getMovieGroup(@RequestParam(value = "date", required = true) String dateStr)
			throws ParseException {
		logger.info("GET " + ApiPath.API_MOVIEGROUP + "?date=" + dateStr);
		MovieGroupResp result = new MovieGroupResp();
		Date date = dateFormat.parse(dateStr);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		int substractDay = 0;
		switch (cal.get(Calendar.DAY_OF_WEEK)) {
		case Calendar.MONDAY:
			substractDay = -3;
			break;
		case Calendar.TUESDAY:
			substractDay = -4;
			break;
		case Calendar.WEDNESDAY:
			substractDay = -5;
			break;
		case Calendar.THURSDAY:
			substractDay = -6;
			break;
		case Calendar.SATURDAY:
			substractDay = -1;
			break;
		case Calendar.SUNDAY:
			substractDay = -2;
			break;
		default:
			break;
		}
		cal.add(Calendar.DAY_OF_MONTH, substractDay);
		result.setFirstDayOfWeek(dateFormat.format(cal.getTime()));

		String groupName = dateFormat.format(cal.getTime()) + "_";
		cal.add(Calendar.DAY_OF_MONTH, 6);
		groupName = groupName + dateFormat.format(cal.getTime());

		logger.info(" groupName: " + groupName);
		result.setGroupName(groupName);

		result.setLastDayOfWeek(dateFormat.format(cal.getTime()));
		result.setAvailable(movieGroupRepo.findByName(groupName) != null);
		return result;
	}
}
