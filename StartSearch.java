import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * This class contains the pathfinding algorithm to navigate Andrew through the desert
 * to collect gems and return to the starting position.
 */
public class StartSearch {
    
    private Map desertMap;
    
    /**
     * Constructor that creates the map from the given file
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
     * Main method that runs the pathfinding algorithm
     * @param args command line arguments (map filename)
     */
    public static void main(String[] args) {
        // Check if filename was provided
        if (args.length < 1) {
            System.out.println("You must provide the name of the input file");
            System.exit(0);
        }
        
        String mapFileName = args[0];
        
        try {
            // Create the map and start search
            StartSearch search = new StartSearch(mapFileName);
            
            // Get starting information
            MapCell start = search.desertMap.getStart();
            int bagSize = search.desertMap.bagSize();
            int gemsFound = 0;
            
            // Create stack for pathfinding
            ArrayStack<MapCell> stack = new ArrayStack<MapCell>();
            
            // Push starting cell and mark it
            stack.push(start);
            start.markInStack();
            
            // Main pathfinding loop
            while (!stack.isEmpty() && gemsFound < bagSize) {
                MapCell current = stack.peek();
                
                // Find the best neighbor to visit
                MapCell next = search.bestCell(current);
                
                if (next != null) {
                    // Found a valid neighbor - visit it
                    stack.push(next);
                    next.markInStack();
                    
                    // Check if it's a gem
                    if (next.isGem()) {
                        gemsFound++;
                    }
                } else {
                    // No valid neighbors - backtrack
                    MapCell popped = stack.pop();
                    popped.markOutStack();
                }
            }
            
            // Return home - pop remaining cells
            while (!stack.isEmpty()) {
                MapCell popped = stack.pop();
                popped.markOutStack();
            }
            
            // Print results
            System.out.println("Gems collected: " + gemsFound);
            
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
    
    /**
     * Finds the best unmarked neighbor cell to visit next
     * @param cell the current cell
     * @return the best neighbor cell, or null if no valid neighbors exist
     */
    private MapCell bestCell(MapCell cell) {
        MapCell bestGem = null;
        MapCell bestCross = null;
        MapCell bestOther = null;
        
        // Check all 4 neighbors (0=North, 1=East, 2=South, 3=West)
        for (int i = 0; i < 4; i++) {
            try {
                MapCell neighbor = cell.getNeighbour(i);
                
                // Skip if neighbor doesn't exist, is marked, or is a black hole
                if (neighbor == null || neighbor.isMarked() || neighbor.isBlackHole()) {
                    continue;
                }
                
                // Check if we can move to this neighbor based on path rules
                boolean canMove = false;
                
                // From Andrew, Cross path, or Gem - can go to any valid neighbor
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
                // From Vertical path - can only go North or South
                else if (cell.isVerticalPath()) {
                    if (i == 0 || i == 2) { // North or South
                        if (neighbor.isStart() || neighbor.isCrossPath() || 
                            neighbor.isGem() || neighbor.isVerticalPath()) {
                            canMove = true;
                        }
                    }
                }
                // From Horizontal path - can only go East or West
                else if (cell.isHorizontalPath()) {
                    if (i == 1 || i == 3) { // East or West
                        if (neighbor.isStart() || neighbor.isCrossPath() || 
                            neighbor.isGem() || neighbor.isHorizontalPath()) {
                            canMove = true;
                        }
                    }
                }
                
                // If we can move to this neighbor, categorize it by priority
                if (canMove) {
                    if (neighbor.isGem()) {
                        // Priority 1: Gem (only keep first one found)
                        if (bestGem == null) {
                            bestGem = neighbor;
                        }
                    } else if (neighbor.isCrossPath()) {
                        // Priority 2: Cross path (keep smallest index)
                        if (bestCross == null) {
                            bestCross = neighbor;
                        }
                    } else {
                        // Priority 3: Other valid paths (keep smallest index)
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