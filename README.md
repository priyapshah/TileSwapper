# Tile Swapper

This game was created for the CIS 120: Programming Languages and Techniques final project.

 ## Core Concepts Used

### Appropriately modeling state using 2-D arrays
  
Using a 2-D array allowed me to build the backbone of my game: the board on which the game would be executed. The rows
and columns of the 2-D array represented the rows and columns on my game, the Tile and GameBoard class working together
to place different colored objects on each point of the grid so that the game may be played. Using a 2-D array to model
the state of the GameBoard also allowed ease when starting new games, the board able to be reset, cleared, and filled
all through iteration of the array.

### Appropriately modeling state using collections.
  
In order to execute the aspect of the game that removes tiles and fills the board with new ones, collections were very
useful. The use of a collection here proved to be necessary in marking tiles for removal and increasing score appropriately.
Since the spaces left once tiles are matched would be filled by the tiles above dropping down rather than replaced by 
completely new, random tiles, it was more logical and efficient to mark all the tiles that belong to the match and then 
remove them all together at a later time. Without a collection, the tile would need to be removed at that instant, 
making dropping the tiles down very inefficient (i.e. a vertical match would call drop down four times whereas storing
and removing the tiles at once later on would allow a call just once). In addition, collections made the code more 
comprehensible to follow and allowed score to be easily implemented while iterating through the collection. 
This also ensured that no tile was double counted in the score when removed, where incrementing score while iterating
through the array would double count the tile that lays between multiple directions. In choosing a collection, I went 
with a HashSet, noting that I needed to store specific points on the board without duplicates, but a specific order 
was not required, so a HashSet would be more efficient. 

### File I/O: using I/O to parse a novel file format.
  
Since no game would be fun without a little competition, I utilized File I/O in order to keep score and display the 
highest rankings. Mostly seen in the Game and Mouse classes, I used buffered readers and writers in order to take
in a user's name and store that string along with their score value in a high_scores text file. This file would then
be read line by line in order to display the top few scores when a user selects the high scores button in the main
game frame. Since I had set my game to end after a specific time, I was able to create a new window pop up that 
would prompt the player for their name to be associated with the score that they had achieved by that time.

### Using JUnit on a testable component of your game.

With many aspects of the GameBoard potentially able to have bugs, JUnit tests were a logical way to ensure that 
implementation followed my vision for the final product. I was able to test many critical components of the game, 
including if the board successfully cleared and filled, empty spaces were filled in, tiles moved down, and matches
could be made in multiple directions. 

## Implementation
  
In order to bring these components together and create my Tile Swap game, I created four main classes: Tile, GameBoard,
Game, and Mouse. The Tile class's main functions is to create the individual, randomly colored tiles at each point
of the GameBoard. It also included methods that specified the state of the individual tile, including whether or not
the tile was to be removed in a chain. The GameBoard class's main function is to draw the board on which the game would
be run correctly, placing tiles on each square of the grid and emptying it once the game had timed out. This class also
includes critical methods of the game, including ones that check if a chain has been made and removes the tiles if it has
been. Here, score is updated, and the board is kept constantly full of tiles with new ones dropping down. The Game class
hold the run method and plays the game, specifying display with JLabels and buttons. File I/O is used here in displaying
the top high scores when the high scores button has been clicked. Lastly, the mouse class handles how tiles are selected
and switched in a mouseReleased event. The status and score are constantly updated once a mouse event has occurred and 
File I/O is used here once again to prompt the user for their name in a new frame once the timer has run out and the game
has finished. 


## External Resources
  
In implementing this game, I relied on mostly JavaDocs in order to better handle TreeMaps for storing high scores
information. Office Hours were also a crucial resource to completing this assignment. I did not use any images. 
I also modeled design aspects using popular games Bejeweled and Candy Crush as guides. 