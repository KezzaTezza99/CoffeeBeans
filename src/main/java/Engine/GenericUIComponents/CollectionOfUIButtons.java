package Engine.GenericUIComponents;
import java.util.ArrayList;

// TODO : SHOULD ACTUALLY USE PIXEL WIDTH AND NOT JUST LENGTH OF LABEL AS DIFFERENT CHARACTERS ARE STILL WIDER ETC

// Give the class a group of buttons you want to be the same size, i.e., a group of main menu buttons
// they will be resized to become the same size so that "Resume" and "Exit to Main Menu" for example will be the same
// dimensions making UIs look cleaner and neater
public class CollectionOfUIButtons {
    // The list of buttons we are resizing
    private final ArrayList<UIButton> listOfButtons;

    // The biggest button in the collection
    private UIButton biggestButton;
    private int biggestLabelSize = 0;

    public CollectionOfUIButtons(ArrayList<UIButton> allButtons) {
        this.listOfButtons = allButtons;

        // Find the biggest button
        findTheBiggestButton();
        // Resize the collection to be the same size as the biggest
        resizeAllButtonsToMatchBiggest();
    }

    // We find the biggest button by simply finding the button with the longest label
    private void findTheBiggestButton() {
        // Loop through all the buttons and get the label, once we find the biggest save the index
        for (UIButton button : this.listOfButtons) {
            if (button.getLabel().length() > biggestLabelSize) {
                biggestLabelSize = button.getLabel().length();
                biggestButton = button;
            }
        }
    }

    // Resizing the buttons to match the biggest button dimensions
    private void resizeAllButtonsToMatchBiggest() {
        int width = biggestButton.getWidth();
        int height = biggestButton.getHeight();

        for(UIButton buttons : this.listOfButtons) {
            buttons.setWidth(width);
            buttons.setHeight(height);
        }
    }

    // When this class is constructed the buttons should automatically be resized, we just need to replace the list
    // with the new information
    public ArrayList<UIButton> getResizedButtons() { return this.listOfButtons; }
}