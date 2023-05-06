import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;
import java.io.File;

public class WeeklyDinnerPlanner {
    public static void main(String[] args) throws FileNotFoundException {

        Recipe recipe = new Recipe();

        // Create scanner object for user input
        Scanner inputKey = new Scanner(System.in);

        // Create recipesFolder file directory
        File recipesFolder = new File("D:\\Desktop\\Recipes\\Ingredients Lists");

        // Create shoppingList file directory
        File shoppingListFile = new File("D:\\Desktop\\Recipes\\Shopping List.txt");

        // Create empty lists for random recipes, user selected recipes titles, recipe objects, and shopping list
        ArrayList<Recipe> userSelectRecipeList = new ArrayList<>();
        ArrayList<String> userSelectRecipeTitles = new ArrayList<>();
        ArrayList<String[]> shoppingList = new ArrayList<>();

        // Initialise variable for recipeTitle and ingredient
        String recipeTitle;
        String ingredient;
        String newUserRecipeIndex;

        // Create list for recipe names to be stored
        File[] recipesFilesList = recipesFolder.listFiles();
        recipe.setFullRecipesList(recipesFilesList);

        // Take random sample from fullRecipesList and add to randomRecipesList
        recipe.setRandomRecipesList(recipe.getFullRecipesList());

        // Have user select a number of recipes
        System.out.println("This week's recipes:");

        // Display recipes
        for (int i = 0; i < 31; i++)
            System.out.println("(" + i + ") " + recipe.getRandomRecipesList().get(i).replace(".txt",""));

        // Take user input, continue taking input until sentinel value is entered
        int i = 0;
        System.out.println("\nSelect a recipe index or Q to exit: ");
        newUserRecipeIndex = inputKey.next();
        while (newUserRecipeIndex.equalsIgnoreCase("Q") == false) {

            // Initialise/reset recipe ingredients array and list
            String[] recipeIngredientsArray;
            ArrayList<String> recipeIngredientsList = new ArrayList<>();

            // Determine if input is valid
            if (newUserRecipeIndex.matches("^[0-9]+$")) {

                // Add selected recipe to userSelectRecipeList
                userSelectRecipeTitles.add(recipe.getRandomRecipesList().get(Integer.parseInt(newUserRecipeIndex)));
            }

            // Display selection
            recipeTitle = userSelectRecipeTitles.get(i);
            System.out.println(recipeTitle.replace(".txt", ""));

            // Define file location and create scanner object for reading file input
            String recipePath = "D:\\Desktop\\Recipes\\Ingredients Lists\\" + recipeTitle;
            File recipeFile = new File(recipePath);

            // Check file for readability
            if (recipeFile.canRead()) {

                // Create scanner FileReader object for reading each file in the folder
                Scanner inputFile = new Scanner(new FileReader(recipePath)).useDelimiter(",\\n*");

                // Iterate over lines in file
                while (inputFile.hasNext()) {
                    ingredient = inputFile.nextLine();

                    // Store each file in recipeIngredients array list
                    recipeIngredientsList.add(ingredient);

                }
            }

            // Convert arraylist to array
            recipeIngredientsArray = recipeIngredientsList.toArray(new String[0]);

            // Create new recipe object and store in selected recipe list
            userSelectRecipeList.add(new Recipe(recipeTitle, recipeIngredientsArray));

            // Prompt user again until exit
            System.out.println("Select a recipe index or Q to exit: ");
            newUserRecipeIndex = inputKey.next();

            i++;

        }

        // Display user selection
        System.out.println("You have selected the following: ");
        for (i = 0; i < userSelectRecipeList.size(); i++) {
            System.out.println(userSelectRecipeList.get(i).toString());

        }

        // Compile ingredients into a shopping list
        for (i = 0; i < userSelectRecipeList.size(); i++) {
            shoppingList.add(userSelectRecipeList.get(i).getIngredients());
        }

        // Write shoppingList to file
        if (shoppingListFile.canWrite()) {

            // Create PrintWriter object
            PrintWriter outputFile = new PrintWriter(shoppingListFile);

            // Iterate over list of ingredients and print to file
            for (i = 0; i < shoppingList.size(); i++) {
                outputFile.println(userSelectRecipeTitles.get(i).replace(".txt", ""));
                outputFile.println(Arrays.toString(shoppingList.get(i)) + "\n");
            }

            // Close file
            outputFile.close();
        }
    }
}
class Recipe {

    // Recipe fields
    protected String title;
    protected String[] ingredients;
    protected ArrayList<String> fullRecipesList = new ArrayList<>();
    protected ArrayList<String> randomRecipesList = new ArrayList<>();

    // Construct no-arg recipe
    public Recipe() {
    }

    // Construct recipe object with specified name and ingredients list
    public Recipe(String title, String[] ingredients) {
        this.title = title;
        this.ingredients = ingredients;
    }

    // Set title
    public void setTitle(String newTitle) {
        this.title = newTitle;
    }

    // Set ingredients
    public void setIngredients(String[] newIngredients) {
        this.ingredients = newIngredients;
    }

    // Set fullRecipesList
    public void setFullRecipesList(File[] newFullRecipesList) {
        for (int i = 0; i < newFullRecipesList.length; i++) {
            this.fullRecipesList.add(newFullRecipesList[i].getName());
        }
    }

    // Set randomRecipesList
    public void setRandomRecipesList(ArrayList<String> newRandomRecipesList) {
        Collections.shuffle(this.fullRecipesList);
        for (int i = 0; i < 31; i++) {
            randomRecipesList.add(i, this.fullRecipesList.get(i));
        }
    }

    // Get title
    public String getTitle() {
        return title;
    }

    // Get ingredients
    public String[] getIngredients() {
        return ingredients;
    }

    // Get fullRecipesList
    public ArrayList<String> getFullRecipesList() {
        return fullRecipesList;
    }

    // Get randomRecipesList
    public ArrayList<String> getRandomRecipesList() {
        return randomRecipesList;
    }


    // Modify toString method to display recipe details
    public String toString() {
        return getTitle().replace(".txt", "");

    }
}
