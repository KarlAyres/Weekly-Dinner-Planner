"""
This program opens a connection to the recipebook database in MYSQL, opens a directory
and reads the names and contents of the files within the directory. The program then
reformats these strings as appropriate in order to be parsed in a MySQL query.
The contents of the directory are then written to the database.
"""

# Import MySQL connector
import mysql.connector
import os

# Define the credentials for the database
databaseCredentials = {
    "host": "localhost",
    "user": "Karl",
    "password": "password",
    "database": "recipebook"
}

# Create an object to connect to the database
conn = mysql.connector.Connect(**databaseCredentials)
cursor = conn.cursor()
print(type(cursor))

# Define the directory
recipeNamePath = "D:\\Desktop\\Recipes\\Recipe Cards"

# Create list of recipe names from file names
recipeNames = os.listdir(recipeNamePath)

# Initialise lists/tuple
recipeNamesStripped = []
instructionsArray = []
data = ()

# Iterate over recipe cards, formatting instructions to list
for i in range(len(recipeNames)):
    with open("D:\\Desktop\\Recipes\\Recipe Cards\\" + recipeNames[i], "r") as file:
        instructions = [file.read()]
    file.close()
    instructionsArray.append(instructions)
    instructionsArray[i] = tuple(instructionsArray[i])

# Format recipe names
for i in range(len(recipeNames)):
    recipeNamesStripped.append([recipeNames[i].strip(".txt")])
    recipeNamesStripped[i] = tuple(recipeNamesStripped[i])

# Compile into data variable for MySQL query
for i in range(len(recipeNamesStripped)):
    data += (recipeNamesStripped[i] + instructionsArray[i],)


# Construct query template (C-style format)
query = "INSERT INTO recipe (recipe_name, instructions) VALUES (%s, %s)"

# Execute query
cursor.executemany(query, data)

# Commit the transaction
conn.commit()
cursor.close()
conn.close()
