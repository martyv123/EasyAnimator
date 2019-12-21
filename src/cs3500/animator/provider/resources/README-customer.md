<br><br>
> # Animator Views
> ## Jack Warren and Kayla Caputo
> ### CS 3500

<br><br>
# Features
## Visual
> #### Provided by `SwingView` and `ViewPanel`

- Assumes that its current tick will be updated at the correct speed in a looping manner (has no internal timer)

## Edit
> #### Provided by `EditView` and `ViewPanel`, `EditPanel`, `TimePanel`, `SavePanel`

- Assumes that its current tick will be updated at the correct speed in the correct looping manner (has no internal timer)
- `TimePanel` provides controls for speed, ticks, and looping, and a graphical indication of if the animation is looping or not
- `EditPanel` provides controls for shapes (adding, deleting, moving between layers) and keyframes (adding, deleting, editing) and selecting shapes and layers
- `SavePanel` provides controls for saving the current animation as a text or SVG file by making a copy of the current controller that is connected to the same model but a different view. In this way, since the controller is assumed to have the looping and speed state, the saved copy may "inherit" the correct speed settings
  - Saving as a file is extra functionality beyond the assignment specification