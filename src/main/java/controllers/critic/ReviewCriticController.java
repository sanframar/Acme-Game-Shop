
package controllers.critic;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CriticService;
import services.GameService;
import services.ReviewService;
import controllers.AbstractController;
import domain.Critic;
import domain.Game;
import domain.Review;

@Controller
@RequestMapping("/review/critic")
public class ReviewCriticController extends AbstractController {

	// Service ---------------------------------------------------------------
	@Autowired
	private ReviewService	reviewService;

	@Autowired
	private CriticService	criticService;

	@Autowired
	private GameService		gameService;


	// Constructors -----------------------------------------------------------

	public ReviewCriticController() {
		super();
	}

	// List ---------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Review> reviews;
		Critic principal;

		principal = this.criticService.findByPrincipal();
		reviews = principal.getReviews();

		result = new ModelAndView("review/list");
		result.addObject("reviews", reviews);
		result.addObject("principal", principal);
		result.addObject("requestURI", "review/critic/list.do");

		return result;
	}

	// Create, edit and delete ---------------------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int gameId) {
		ModelAndView result;
		Review review;
		Game game;

		game = this.gameService.findOne(gameId);
		review = this.reviewService.create(game);
		result = this.createModelAndView(review);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int reviewId) {
		ModelAndView result;
		Review review;

		review = this.reviewService.findOne(reviewId);
		result = this.editModelAndView(review);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Review review, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.editModelAndView(review);
		else
			try {
				this.reviewService.save(review);
				result = new ModelAndView("redirect:../display.do?gameId=" + review.getGame().getId());

			} catch (final Throwable oops) {
				result = this.editModelAndView(review, "review.commit.error");

			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Review review, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = new ModelAndView("redirect:list.do");
		else
			try {
				this.reviewService.delete(review);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.editModelAndView(review, "review.commit.error");
			}

		return result;
	}

	// Ancillary methods ------------------------------------------------------
	protected ModelAndView createModelAndView(final Review review) {
		ModelAndView result;

		result = this.createModelAndView(review, null);

		return result;
	}

	protected ModelAndView createModelAndView(final Review review, final String message) {
		ModelAndView result;

		result = new ModelAndView("review/create");
		result.addObject("review", review);
		result.addObject("requestURI", "review/critic/create.do");
		result.addObject("message", message);

		return result;
	}

	protected ModelAndView editModelAndView(final Review review) {
		ModelAndView result;

		result = this.editModelAndView(review, null);

		return result;
	}

	protected ModelAndView editModelAndView(final Review review, final String message) {
		ModelAndView result;

		result = new ModelAndView("review/edit");
		result.addObject("review", review);
		result.addObject("requestURI", "review/critic/edit.do");
		result.addObject("message", message);

		return result;
	}

}