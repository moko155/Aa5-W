import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * this class is for pathfinding to navigate through 
 * the map based on priorities 1st gems, 2nd cross paths, 3rd other valid paths. and then return home
 * when the bag is full capacity. 
 */
public class StartSearch {
    
    private Map desertMap;
    
    /**
     * creates the map from the files
     * @param filename the name of the map file
     */
    public StartSearch(String filename) {
        try {
            desertMap = new Map(filename);
        } catch (InvalidMapException e) {
            System.out.println("Invalid map format: " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
    
    /**
     * Main method that has the pathfinding algorithm
     * @param args command line arguments is mapfilename
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("You must provide the name of the input file");
            System.exit(0);
        }
        
        String mapFileName = args[0];
        
        try {
            // Create the map and start searching
            StartSearch search = new StartSearch(mapFileName);
            
            // Get starting info
            MapCell start = search.desertMap.getStart();
            int bagSize = search.desertMap.bagSize();
            int gemsFound = 0;
            System.out.println("Bag size: " + bagSize);

            
            // Create stack for pathing
            ArrayStack<MapCell> stack = new ArrayStack<MapCell>();
            
            // Push starting cell and mark it
            stack.push(start);
            start.markInStack();
            
            // Iteration counter for debugging
            int iterations = 0;

            // while stack not empty and gems found is not less than bag size 
            while (!stack.isEmpty() && gemsFound < bagSize) {
                MapCell current = stack.peek();
                
                // Find the best next cell to visit
                MapCell next = search.bestCell(current);
                
                System.out.println("Iterations: " + iterations);iterations++;
                System.out.println("Current cell: " + current.getIdentifier());
                System.out.println("Stack size: " + stack.size());
                System.out.println("Gems found: " + gemsFound);

                if (next != null) {

                    System.out.println("Moving to cell: " + next.getIdentifier() + 
                     " | Is gem? " + next.isGem());

                    stack.push(next);
                    next.markInStack();
                    
                    // Checking if it's a gem
                    if (next.isGem()) {
                        gemsFound++;
                        System.out.println("Total gems found so far: " + gemsFound);
                    }
                } else {
                    // No valid neighbors
                    MapCell popped = stack.pop();
                    popped.markOutStack();
                }
            }
            
            // Return home and pop remaining cells
            while (!stack.isEmpty()) {
                MapCell popped = stack.pop();
                popped.markOutStack();
            }
            
            System.out.println("Gems collected: " + gemsFound);
            
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
    
    /**
     * Finds best neighbor cell to visit based on priority rules
     * @param cell current cell
     * @return the best neighbor cell, or null if no valid neighbors exist
     */
    private MapCell bestCell(MapCell cell) {
        MapCell bestGem = null;
        MapCell bestCross = null;
        MapCell bestOther = null;
        
        // Check all neighbors (0=North, 1=East, 2=South, 3=West)
        for (int i = 0; i < 4; i++) {
            try {
                MapCell neighbor = cell.getNeighbour(i);
                
                // Skip if neighbor doesn't exist, is marked, or a black hole
                if (neighbor == null || neighbor.isMarked() || neighbor.isBlackHole()) {
                    continue;
                }
                
                boolean canMove = false;
                
                // if from Start, CrossPath, or Gem go to any valid path
                if (cell.isStart() || cell.isCrossPath() || cell.isGem()) {
                    if (neighbor.isGem() || neighbor.isCrossPath()) {
                        canMove = true;
                    } else if ((i == 0 || i == 2) && neighbor.isVerticalPath()) {
                        // North or South to vertical path
                        canMove = true;
                    } else if ((i == 1 || i == 3) && neighbor.isHorizontalPath()) {
                        // East or West to horizontal path
                        canMove = true;
                    }
                }
                // From Vertical path only up or down
                else if (cell.isVerticalPath()) {
                    if (i == 0 || i == 2) { // North or South
                        if (neighbor.isStart() || neighbor.isCrossPath() || 
                            neighbor.isGem() || neighbor.isVerticalPath()) {
                            canMove = true;
                        }
                    }
                }
                // From Horizontal path left or right
                else if (cell.isHorizontalPath()) {
                    if (i == 1 || i == 3) { // East or West
                        if (neighbor.isStart() || neighbor.isCrossPath() || 
                            neighbor.isGem() || neighbor.isHorizontalPath()) {
                            canMove = true;
                        }
                    }
                }
                
                // TODO If we can move to this neighbor categorize it by priority
                if (canMove) {
                    if (neighbor.isGem()) {
                        // 1 first priority gem
                        if (bestGem == null) {
                            bestGem = neighbor;
                        }
                    } else if (neighbor.isCrossPath()) {
                        // 2 second priority cross path
                        if (bestCross == null) {
                            bestCross = neighbor;
                        }
                    } else {
                        // 3 thoird priority other valid paths
                        if (bestOther == null) {
                            bestOther = neighbor;
                        }
                    }
                }
                
            } catch (InvalidNeighbourIndexException e) {
                System.out.println("Invalid neighbor index: " + e.getMessage());
            }
        }
        
        // Return based on priority
        if (bestGem != null) {
            return bestGem;
        } else if (bestCross != null) {
            return bestCross;
        } else {
            return bestOther;
        }
    }
}