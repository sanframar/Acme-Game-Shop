
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import security.Authority;
import domain.Actor;
import domain.Category;
import domain.Game;

@Service
@Transactional
public class CategoryService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private CategoryRepository		categoryRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private DeveloperService		developerService;

	@Autowired
	private GameService				gameService;

	@Autowired
	private ActorService			actorService;


	// Constructors------------------------------------------------------------
	public CategoryService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Category findOne(final int categoryId) {
		Category result;

		result = this.categoryRepository.findOne(categoryId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Category> findAll() {
		Collection<Category> result;

		result = this.categoryRepository.findAll();

		return result;
	}

	public Category create() {
		Category result;

		Assert.notNull(this.administratorService.findByPrincipal());

		result = new Category();

		return result;
	}

	public Category save(final Category category) {
		Assert.notNull(category);
		Category result;
		Actor principal;

		principal = this.actorService.findByPrincipal();
		if (this.actorService.checkAuthority(principal, Authority.ADMIN))
			Assert.isTrue(category.getGames().size() == 0);

		result = this.categoryRepository.save(category);

		return result;
	}

	public void delete(final Category category) {
		Assert.notNull(category);
		Assert.isTrue(category.getId() != 0);

		Assert.notNull(this.administratorService.findByPrincipal());
		Assert.isTrue(category.getGames().size() == 0);

		this.categoryRepository.delete(category);
	}

	// Other business methods -------------------------------------------------

	//Este m�todo no realiza un save por lo que se tendr� que hacer en el controlador
	//Adem�s se deber� realizar otro save para cada categor�a
	//No se realiza por si se a�ade una categoria al crear un juego
	public Game addCategoryWoS(final Category category, final Game game) {
		Collection<Category> categories;

		Assert.notNull(category);
		Assert.notNull(game);
		Assert.notNull(this.developerService.findByPrincipal());
		Assert.isTrue(!game.getCategories().contains(category));

		categories = game.getCategories();
		categories.add(category);
		game.setCategories(categories);

		return game;
	}

	//Leer las notaciones del m�todo addCategoryWoS
	public Game deleteCategoryWoS(final Category category, final Game game) {
		Collection<Category> categories;

		Assert.notNull(category);
		Assert.notNull(game);
		Assert.notNull(this.developerService.findByPrincipal());
		Assert.isTrue(game.getCategories().contains(category));

		categories = game.getCategories();
		categories.remove(category);
		game.setCategories(categories);

		return game;
	}

	public void addCategory(final Category category, final Game game) {
		Collection<Category> categories;
		Collection<Game> games;

		Assert.notNull(category);
		Assert.notNull(game);
		Assert.notNull(this.developerService.findByPrincipal());
		Assert.isTrue(!game.getCategories().contains(category));

		categories = game.getCategories();
		categories.add(category);
		game.setCategories(categories);
		this.gameService.save(game);

		Assert.isTrue(!category.getGames().contains(game));

		games = category.getGames();
		games.add(game);
		category.setGames(games);
		this.save(category);
	}

	public void deleteCategory(final Category category, final Game game) {
		Collection<Category> categories;
		Collection<Game> games;

		Assert.notNull(category);
		Assert.notNull(game);
		Assert.notNull(this.developerService.findByPrincipal());
		Assert.isTrue(game.getCategories().contains(category));

		categories = game.getCategories();
		categories.remove(category);
		game.setCategories(categories);
		this.gameService.save(game);

		Assert.isTrue(category.getGames().contains(game));

		games = category.getGames();
		games.remove(game);
		category.setGames(games);
		this.save(category);
	}

}
