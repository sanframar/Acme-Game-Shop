
package controllers.administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CriticService;
import controllers.AbstractController;
import domain.Critic;
import forms.CriticForm;

@Controller
@RequestMapping("/critic/administrator")
public class CriticAdministratorController extends AbstractController {

	// Service ---------------------------------------------------------------
	@Autowired
	private CriticService	criticService;


	// Constructors -----------------------------------------------------------

	public CriticAdministratorController() {
		super();
	}

	// Creation ---------------------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		CriticForm criticForm;

		criticForm = new CriticForm();
		result = this.createModelAndView(criticForm);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final CriticForm criticForm, final BindingResult binding) {

		ModelAndView result;
		Critic critic;

		if (binding.hasErrors())
			result = this.createModelAndView(criticForm);
		else
			try {
				critic = this.criticService.reconstructProfile(criticForm, "create");
				this.criticService.save(critic);
				result = new ModelAndView("redirect:/welcome/index.do");

			} catch (final Throwable oops) {
				result = this.createModelAndView(criticForm, "critic.commit.error");

			}
		return result;
	}

	// Ancillary methods ------------------------------------------------------
	protected ModelAndView createModelAndView(final CriticForm criticForm) {
		ModelAndView result;

		result = this.createModelAndView(criticForm, null);

		return result;
	}

	protected ModelAndView createModelAndView(final CriticForm criticForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("critic/create");
		result.addObject("criticForm", criticForm);
		result.addObject("requestURI", "critic/administrator/create.do");
		result.addObject("message", message);

		return result;
	}

}
