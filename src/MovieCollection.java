import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MovieCollection
{
	private ArrayList<Movie> movies;
	private Scanner scanner;

	public MovieCollection(String fileName)
	{
		importMovieList(fileName);
		scanner = new Scanner(System.in);
	}

	public ArrayList<Movie> getMovies()
	{
		return movies;
	}

	public void menu()
	{
		String menuOption = "";

		System.out.println("Welcome to the movie collection!");
		System.out.println("Total: " + movies.size() + " movies");

		while (!menuOption.equals("q"))
		{
			System.out.println("------------ Main Menu ----------");
			System.out.println("- search (t)itles");
			System.out.println("- search (k)eywords");
			System.out.println("- search (c)ast");
			System.out.println("- see all movies of a (g)enre");
			System.out.println("- list top 50 (r)ated movies");
			System.out.println("- list top 50 (h)igest revenue movies");
			System.out.println("- (q)uit");
			System.out.print("Enter choice: ");
			menuOption = scanner.nextLine();

			if (!menuOption.equals("q"))
			{
				processOption(menuOption);
			}
		}
	}

	private void processOption(String option)
	{
		if      (option.equals("t")) searchTitles();
		else if (option.equals("c")) searchCast();
		else if (option.equals("k")) searchKeywords();
		else if (option.equals("g")) listGenres();
		else if (option.equals("r")) listHighestRated();
		else if (option.equals("h")) listHighestRevenue();
		else {
			System.out.println("Invalid choice!");
		}
	}

	private void searchTitles() {
		System.out.print("Enter a title to search for: ");
		String query = scanner.nextLine().toLowerCase();

		// Search for all movies containing `query` in their titles
		// and add them to a results list
		ArrayList<Movie> results = new ArrayList<Movie>();
		for (Movie movie : movies)
			if (movie.getTitle().toLowerCase().indexOf(query) != -1)
				results.add(movie);
		sortMovies(results);

		// Print results
		displaySearchResults(results);
	}

	private void searchKeywords() {
		System.out.print("Enter a keyword to search for: ");
		String query = scanner.nextLine().toLowerCase();

		// Search for all movies containing `query` in their titles
		// and add them to a results list
		ArrayList<Movie> results = new ArrayList<Movie>();
		for (Movie movie : movies)
			if (movie.getKeywords().toLowerCase().indexOf(query) != -1)
				results.add(movie);
		sortMovies(results);

		// Print results
		displaySearchResults(results);
	}

	private void searchCast() {
		// Build list of all cast members appearing in any movie
		ArrayList<String> allCastMembers = new ArrayList<String>();
		for (Movie movie : movies) {
			String[] castMembers = movie.getCast().split("|");
			for (String castMember : castMembers) {
				boolean isDuplicate = false;
				for (String existingCastMember : allCastMembers)
					if (existingCastMember.equals(castMember))
						isDuplicate = true;
				if (!isDuplicate) {
					allCastMembers.add(castMember);
				}
			}
		}

		System.out.print("Enter a name to search for: ");
		String query = scanner.nextLine().toLowerCase();

		// Search for all cast members containing `query` in
		// their names and add them to a results list
		ArrayList<String> results = new ArrayList<String>();
		for (String castMember : allCastMembers)
			if (castMember.indexOf(query) != -1)
				results.add(castMember);
		sortStrings(results);

		// Print cast members and ask for a cast member to see movies of
		for (int i = 0; i < results.size(); i++)
			System.out.println((i + 1) + ". " + results.get(i));
		System.out.println("Which cast member do you want to see the movies of?");
		System.out.print("Enter a number: ");

		int index = scanner.nextInt() - 1;
		String castQuery = results.get(index - 1);

		// Search for all movies with `castQuery` as a cast
		// member and add them to a new results list
		ArrayList<Movie> mResults = new ArrayList<Movie>();
		for (Movie movie : movies)
			if (movie.getCast().indexOf(castQuery) != -1)
				mResults.add(movie);
		sortMovies(mResults);

		// Print results
		displaySearchResults(mResults);
	}


	private void listGenres()
	{

	}

	private void listHighestRated()
	{

	}

	private void listHighestRevenue()
	{

	}

	private void sortMovies(ArrayList<Movie> toSort) {
		for (int i = 1; i < toSort.size(); i++) {
			int j = i;
			Movie tmp = toSort.get(j);
			while (j > 0 && tmp.compareTo(toSort.get(j - 1)) < 0) {
				toSort.set(j, toSort.get(j - 1));
				j--;
			}
			toSort.set(j, tmp);
		}
	}

	private void sortStrings(ArrayList<String> toSort) { // really gotta start giving us more extensible boilerplate code ngl
		for (int i = 1; i < toSort.size(); i++) {
			int j = i;
			String tmp = toSort.get(j);
			while (j > 0 && tmp.compareTo(toSort.get(j - 1)) < 0) {
				toSort.set(j, toSort.get(j - 1));
				j--;
			}
			toSort.set(j, tmp);
		}
	}

	private void displayMovieInfo(Movie movie) {
		System.out.println();
		System.out.println("Title: " + movie.getTitle());
		System.out.println("Tagline: " + movie.getTagline());
		System.out.println("Runtime: " + movie.getRuntime() + " minutes");
		System.out.println("Year: " + movie.getYear());
		System.out.println("Directed by: " + movie.getDirector());
		System.out.println("Cast: " + movie.getCast());
		System.out.println("Overview: " + movie.getOverview());
		System.out.println("User rating: " + movie.getUserRating());
		System.out.println("Box office revenue: " + movie.getRevenue());
	}

	private void displaySearchResults(ArrayList<Movie> results) {
		if (results.size() == 0) {
			System.out.println("No movies were found");
			System.out.println();
			return;
		}

		for (int i = 0; i < results.size(); i++)
			System.out.println((i + 1) + ". " + results.get(i).getTitle());
		System.out.println("Which movie would you like to learn more about?");
		System.out.print("Enter number: ");

		int index = scanner.nextInt() - 1;
		displayMovieInfo(results.get(index));

		System.out.println("\n ** Press Enter to Return to Main Menu **");
		scanner.nextLine();
	}

	private void importMovieList(String fileName)
	{
		try
		{
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line = bufferedReader.readLine();

			movies = new ArrayList<Movie>();

			while ((line = bufferedReader.readLine()) != null) 
			{
				String[] movieFromCSV = line.split(",");

				String title = movieFromCSV[0];
				String cast = movieFromCSV[1];
				String director = movieFromCSV[2];
				String tagline = movieFromCSV[3];
				String keywords = movieFromCSV[4];
				String overview = movieFromCSV[5];
				int runtime = Integer.parseInt(movieFromCSV[6]);
				String genres = movieFromCSV[7];
				double userRating = Double.parseDouble(movieFromCSV[8]);
				int year = Integer.parseInt(movieFromCSV[9]);
				int revenue = Integer.parseInt(movieFromCSV[10]);

				Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
				movies.add(nextMovie);  
			}
			bufferedReader.close();
		}
		catch(IOException exception)
		{
			// Print out the exception that occurred
			System.out.println("Unable to access " + exception.getMessage());              
		}
	}
}
