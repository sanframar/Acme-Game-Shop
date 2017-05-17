
package controllers.developer;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.GameService;
import domain.Game;

@Controller
@RequestMapping("/game/developer")
public class GameDeveloperController {

	// Services ---------------------------------------------------------------

	@Autowired
	private GameService		gameService;

	@Autowired
	private ActorService	actorService;


	// Constructors -----------------------------------------------------------

	public GameDeveloperController() {
		super();
	}

	// Create, Edit and Delete ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Game game;

		game = this.gameService.create();

		result = this.createEditModelAndView(game);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int gameId) {
		ModelAndView result;
		Game game;

		game = this.gameService.findOne(gameId);

		result = this.createEditModelAndView(game);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Game game, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(game);
		else
			try {
				this.gameService.save(game);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(game, "game.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Game game, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = new ModelAndView("redirect:../../welcome/index.do");
		else
			try {
				this.gameService.delete(game);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(game, "game.commit.error");
			}

		return result;
	}

	// Ancillary methods ------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Game game) {
		ModelAndView result;

		result = this.createEditModelAndView(game, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Game game, final String message) {
		ModelAndView result;

		result = new ModelAndView("game/edit");
		result.addObject("game", game);
		result.addObject("requestURI", "game/edit.do");
		result.addObject("message", message);

		return result;
	}

}