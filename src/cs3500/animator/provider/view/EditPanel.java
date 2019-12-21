package cs3500.animator.provider.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import cs3500.animator.provider.controller.EditController;
import cs3500.animator.provider.model.BasicShapeProperties;
import cs3500.animator.provider.model.BasicShapes;
import cs3500.animator.provider.model.ROAnimatorModel;

/**
 * <p>Panel providing buttons to edit the animation. Delegates to a stored {@link EditController}
 * for all high level events, and so the buttons will have no effect until one is provided via the
 * {@link EditPanel#setController(EditController)} method.</p>
 *
 * <p>The following high-level actions are supported by this panel:</p>
 * <ul>
 * <li>Adding a new keyframe, via {@link EditController#addKeyFrame(String, int)}</li>
 * <li>Editing an existing keyframe, via {@link EditController#editKeyFrame(String, int,
 * List)}</li>
 * <li>Deleting an existing keyframe, via {@link EditController#deleteKeyFrame(String, int)}</li>
 * <li>Adding a new shape, via {@link EditController#addShape(int, Object, String)}</li>
 * <li>Moving an existing shape from one layer to another, via {@link
 * EditController#moveShape(String, int)}</li>
 * <li>Deleting an existing shape, via {@link EditController#removeShape(String)}</li>
 * </ul>
 *
 * <p>These high-level actions are supported by a variety of lower-level actions that form the
 * interrface and modal flows. A basic descriptions of the interface of this panel is two lists
 * beside each other above a set of buttons.</p>
 * <ul>
 * <li>The top left list is of the shapes, sorted by their layer. Each shape is given in the form
 * "{@code layer} - {@code name}". When a shape is selected, it will become the shape targeted by a
 * shape move or deletion.</li>
 * <li>The top right list is of the keyframes, sorted by their time. Upon selecting a shape from
 * the shapes list, the keyframes list will automatically update to be the keyframes of the selected
 * shape. When a keyframe is selected, it will become the keyframe targeted by a keyframe edit or
 * deletion.</li>
 * <li>The buttons below the two lists correspond to the six high-level actions described above:
 * adding, editing, and deleting keyframes and adding, moving, and deleting shapes.</li>
 * </ul>
 */
class EditPanel extends JPanel implements ActionListener, ListSelectionListener {

  /**
   * The {@link ROAnimatorModel} to draw data from.
   */
  private final ROAnimatorModel<Integer, BasicShapeProperties, BasicShapes> model;

  /**
   * The {@link EditController} to delegate high-level actions to.
   */
  private EditController<Integer, BasicShapeProperties, BasicShapes> controller;

  /**
   * Panel consisting of the lists of layers/shapes and keyframes.
   */
  private final JPanel listsPan;

  /**
   * The list of sprite names and layers in the form "{@code layer} - {@code name}".
   */
  private JList<String> listOfSpriteNames;

  /**
   * The list of ticks at which there are keyframes, as a list of integers.
   */
  private JList<Integer> listOfKeyframes;

  /**
   * The default list of keyframe ticks, used to drive the list of keyframes.
   */
  private DefaultListModel<Integer> frames;

  /**
   * The default list of shape names and layer, used to drive the list of sprite names.
   */
  private DefaultListModel<String> names;

  /**
   * Panel containing all of the buttons of this.
   */
  private final JPanel buttonPan;

  /**
   * Panel containing just the three buttons associated with manipulating keyframes.
   */
  private final JPanel keyPan;

  /**
   * Panel containing just the three buttons associated with manipulating sprites.
   */
  private final JPanel shapePan;

  /**
   * The name of the currently selected shape.
   */
  private String shapeName;


  /**
   * Constructor for this edit panel that takes in and stores the model. Initializes necessary
   * fields and sets up buttons and other interface elements.
   *
   * @param model the model to store and draw data from
   */
  public EditPanel(ROAnimatorModel<Integer, BasicShapeProperties, BasicShapes> model) {
    this.model = model;
    this.controller = null;
    this.shapeName = null;
    this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    this.setBorder(BorderFactory.createTitledBorder("Edit"));

    //The panel that houses both sets of buttons: keyframe and shapes
    this.buttonPan = new JPanel();

    //The panel for the lists
    this.listsPan = new JPanel();
    this.setLists();

    //the panel for the key editor
    this.keyPan = new JPanel();
    this.setKeyPan();

    //the panel for the shape editor
    this.shapePan = new JPanel();
    this.setShapePan();

    this.add(listsPan, BorderLayout.NORTH);
    this.add(buttonPan, BorderLayout.SOUTH);
  }

  /**
   * Sets up the panel for the two lists: Shapes and Keyframes.
   */
  private void setLists() {
    listsPan.setLayout(new BoxLayout(listsPan, BoxLayout.X_AXIS));
    this.add(listsPan, BorderLayout.NORTH);
    names = new DefaultListModel<>();
    model.getLayerROSprites()
        .forEach(e -> names.addElement(e.getKey() + " - " + e.getValue().getName()));
    listOfSpriteNames = new JList<>(names);
    listOfSpriteNames.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    listOfSpriteNames.addListSelectionListener(this);

    //The panel for the list of Shapes and layers
    JPanel shapes = new JPanel();
    shapes.setBorder(BorderFactory.createTitledBorder("Layers and Shapes"));
    shapes.setLayout(new BoxLayout(shapes, BoxLayout.X_AXIS));
    shapes.add(listOfSpriteNames);
    JScrollPane spriteNamesScroll = new JScrollPane(listOfSpriteNames);
    shapes.add(spriteNamesScroll);
    listsPan.add(shapes, BorderLayout.WEST);


    frames = new DefaultListModel<>();
    listOfKeyframes = new JList<>(frames);
    listOfKeyframes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    listOfKeyframes.addListSelectionListener(this);

    //the panel for the list of keyframes
    JPanel keyFrames = new JPanel();
    keyFrames.setBorder(BorderFactory.createTitledBorder("Keyframes"));
    keyFrames.setLayout(new BoxLayout(keyFrames, BoxLayout.X_AXIS));
    keyFrames.add(listOfKeyframes);
    JScrollPane keyScroll = new JScrollPane(listOfKeyframes);
    keyFrames.add(keyScroll);
    listsPan.add(keyFrames, BorderLayout.EAST);
  }

  /**
   * Sets up the panel dedicated to the Keyframe editing buttons. Those button are adding, editing,
   * and removing keyframes. They are housed in this panel to separate themselves from the shape
   * buttons.
   */
  private void setKeyPan() {
    keyPan.setBorder(BorderFactory.createTitledBorder("Edit Keyframe Options"));

    //Button for adding a new keyframe
    JButton addNew = new JButton("Add New");
    addNew.addActionListener(this);
    addNew.setActionCommand("Add keyframe");
    keyPan.add(addNew, BorderLayout.WEST);

    //Button for editing a keyframe
    JButton edit = new JButton("Edit");
    edit.addActionListener(this);
    edit.setActionCommand("Edit keyframe");
    keyPan.add(edit, BorderLayout.CENTER);

    //Button for deleting a keyframe
    JButton delete = new JButton("Delete");
    delete.setActionCommand("Delete keyframe");
    delete.addActionListener(this);
    keyPan.add(delete, BorderLayout.EAST);

    buttonPan.add(keyPan, BorderLayout.EAST);
  }

  /**
   * Sets up the panel dedicated to the Shape editing buttons, which are adding, moving, and
   * removing a shape. They are housed in this panel to separate themselves from the keyframe
   * buttons.
   */
  private void setShapePan() {
    shapePan.setBorder(BorderFactory.createTitledBorder("Edit Shape Options"));

    //The button for adding a new shape
    JButton addShape = new JButton("Add New");
    addShape.addActionListener(this);
    addShape.setActionCommand("Add Shape");
    shapePan.add(addShape, BorderLayout.WEST);

    //The button for moving a shape
    JButton move = new JButton("Move Shape");
    move.addActionListener(this);
    move.setActionCommand("Move Shape");
    shapePan.add(move, BorderLayout.CENTER);

    //the button for removing a shape
    JButton remove = new JButton("Remove");
    remove.setActionCommand("Remove Shape");
    remove.addActionListener(this);
    shapePan.add(remove, BorderLayout.EAST);

    buttonPan.add(shapePan, BorderLayout.WEST);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (!Objects.isNull(this.controller)) {
      switch (e.getActionCommand()) {
        case "Add keyframe":
          addKeyframe();
          break;
        case "Edit keyframe":
          editKeyframe();
          break;
        case "Delete keyframe":
          deleteKeyframe();
          break;
        case "Add Shape":
          addShape();
          break;
        case "Move Shape":
          moveShape();
          break;
        case "Remove Shape":
          removeShape();
          break;
        default:
          break;
      }
    }
    this.revalidate();
  }

  /**
   * Go through the model flow of adding a keyframe for a shape. Enforces that the keyframe must be
   * at a positive tick and will prompt the user repeatedly until this is done. Clicking cancel will
   * exit without effect.
   */
  private void addKeyframe() {
    if (shapeName != null) {
      OptionalInt parsed = OptionalInt.empty();
      String value = "";
      while (!IntTools.parseToInt(value).isPresent()) {
        value = JOptionPane.showInputDialog(this,
            "Please enter the tick at which you would like a keyframe to be added: ");
        if (value == null) {
          return;
        }
        parsed = IntTools.parseToInt(value);
        if (parsed.isPresent() && parsed.getAsInt() <= 0) {
          JOptionPane.showMessageDialog(this,
              "You must select a positive tick", "Try again.",
              JOptionPane.WARNING_MESSAGE);
          value = "";
        }
      }
      if (parsed.isPresent()) {
        controller.addKeyFrame(shapeName, parsed.getAsInt());
        this.update();
      }
    } else {
      JOptionPane.showMessageDialog(this,
          "You must first select a shape.", "Try again.",
          JOptionPane.WARNING_MESSAGE);
    }
  }

  /**
   * Step through the modal flow of editing an existing keyframe. Will ask for each value in order,
   * using the modal sub-flow of {@link EditPanel#getValue(String, int)}. If no shape and keyframe
   * are selected, the user will be informed as such.
   */
  private void editKeyframe() {
    if (this.listOfKeyframes.getSelectedValue() != null &&
        shapeName != null) {
      int time = this.listOfKeyframes.getSelectedValue();
      List<Integer> values = this.model.getROSpriteByName(shapeName).getKeyframes().get(time);

      int newX = this.getValue("x coordinate", values.get(0));
      int newY = this.getValue("y coordinate", values.get(1));
      int newWidth = this.getValue("width", values.get(2));
      int newHeight = this.getValue("height", values.get(3));
      int newR = this.getValue("red component", values.get(4));
      int newG = this.getValue("green component", values.get(5));
      int newB = this.getValue("blue component", values.get(6));
      List<Integer> newVals = Arrays.asList(newX, newY, newWidth, newHeight, newR, newG, newB);

      controller.editKeyFrame(shapeName, time, newVals);
      this.update();

    } else {
      JOptionPane.showMessageDialog(this,
          "You must first select a shape and a keyframe.", "Try again.",
          JOptionPane.WARNING_MESSAGE);
    }
  }

  /**
   * Deletes the selected keyframe, or tells as a user that they are unable to do so because they
   * have not yet selected a shape and a keyframe.
   */
  private void deleteKeyframe() {
    int time;
    if (listOfKeyframes.getSelectedValue() != null &&
        shapeName != null) {
      time = listOfKeyframes.getSelectedValue();
      controller.deleteKeyFrame(shapeName, time);
      frames.removeElement(time);
    } else {
      JOptionPane.showMessageDialog(this,
          "You must first select a shape and a keyframe.", "Try again.",
          JOptionPane.WARNING_MESSAGE);
    }
  }

  /**
   * Execute the modal flow of getting the name, layer, and type of shape from the user and then
   * creating said shape by delegating to the {@link EditController}. If the user cancels or closes
   * out of any modal, the flow is exited without effect.
   */
  private void addShape() {
    // get name
    String name = JOptionPane.showInputDialog(this, "What is the name of this new shape?");
    if (Objects.isNull(name)) {
      return;
    }

    // get layer
    String response = "";
    List<Integer> fullLayers = model.getLayerROSprites().map(
        Map.Entry::getKey).collect(Collectors.toList());
    while (!Objects.isNull(response) && !IntTools.parseToInt(response).isPresent()) {
      response = JOptionPane
          .showInputDialog(this, "What is the integer, empty layer to add " + name + " at?");

      if (IntTools.parseToInt(response).isPresent() && fullLayers
          .contains(IntTools.parseToInt(response).getAsInt())) {
        response = "";
        JOptionPane
            .showMessageDialog(this, "You must provide a layer that is empty.", "Try again.",
                JOptionPane.WARNING_MESSAGE);
      }
    }
    if (Objects.isNull(response)) {
      return;
    }

    // get type
    String[] shapeOptions = {"Ellipse", "Rectangle"};
    int whichButton = JOptionPane.showOptionDialog(this,
        "Please choose a type of shape:", "Options", JOptionPane.YES_NO_OPTION,
        JOptionPane.INFORMATION_MESSAGE, null, shapeOptions, shapeOptions[0]);
    if (whichButton == -1) {
      return;
    }

    // make
    controller.addShape(IntTools.parseToInt(response).getAsInt(), BasicShapes.values()[whichButton],
        name);
    this.updateToNewShape(IntTools.parseToInt(response).getAsInt(), name);
  }

  /**
   * Ask for the layer to move the currently selected shape to and then make the move.
   */
  private void moveShape() {
    if (shapeName != null) {
      String response = "";
      List<Integer> fullLayers = model.getLayerROSprites().map(
          Map.Entry::getKey).collect(Collectors.toList());
      while (!Objects.isNull(response) && !IntTools.parseToInt(response).isPresent()) {
        response = JOptionPane
            .showInputDialog(this, "What layer would you like to move " + shapeName + " to?");

        if (IntTools.parseToInt(response).isPresent() && fullLayers
            .contains(IntTools.parseToInt(response).getAsInt())) {
          response = "";
          JOptionPane
              .showMessageDialog(this, "You must provide a layer that is empty.", "Try again.",
                  JOptionPane.WARNING_MESSAGE);
        }
      }

      if (!Objects.isNull(response)) {
        controller.moveShape(shapeName, IntTools.parseToInt(response).getAsInt());
        this.updateToNewShape(IntTools.parseToInt(response).getAsInt(), shapeName);
      }
    } else {
      JOptionPane.showMessageDialog(this, "You must first select a shape.", "Try again.",
          JOptionPane.WARNING_MESSAGE);
    }
  }

  /**
   * Ask for confirmation and then delete the currently selected shape.
   */
  private void removeShape() {
    if (shapeName != null) {
      int yes = JOptionPane.showConfirmDialog(this,
          "Are you sure you want to delete the shape " + shapeName + "?");
      if (yes == JOptionPane.OK_OPTION) {
        controller.removeShape(shapeName);
        this.names.clear();
        this.model.getLayerROSprites()
            .forEach(e -> names.addElement(e.getKey() + " - " + e.getValue().getName()));
        shapeName = null;
        this.update();
      }
    } else {
      JOptionPane.showMessageDialog(this,
          "You must first select a shape.", "Try again.",
          JOptionPane.WARNING_MESSAGE);
    }
  }

  /**
   * Set the {@link EditController} to use to delegate high-level editing actions to.
   *
   * @param controller the {@link EditController} to set
   */
  public void setController(EditController<Integer, BasicShapeProperties, BasicShapes> controller) {
    this.controller = controller;
  }

  /**
   * Updates the stored data whenever the value of what is selected in the lists is changed.
   *
   * @param e the low-level {@link ListSelectionEvent} that was triggered, which is ignored
   */
  @Override
  public void valueChanged(ListSelectionEvent e) {
    if (shapeName == null) {
      this.update();
    } else if (listOfSpriteNames.getSelectedValue() != null && !(shapeName
        .equals(listOfSpriteNames.getSelectedValue().replaceFirst("[0-9]+ - ", "")))) {
      this.update();
    }
  }

  /**
   * Proceed through the flow of asking the user for the value of a given parameter. The value box
   * will be pre-filled with an initial value. This method is guaranteed to return an int regardless
   * of if the user eventually enters a valid value or if they click cancel, since in that latter
   * case the initial value will be returned.
   *
   * @param param      the name of the parameter being set
   * @param initialVal the value to have as the existing value for the parameter
   * @return the value determined in the modal flow
   */
  private int getValue(String param, int initialVal) {
    int toReturn = initialVal;
    String value = "";
    while (!IntTools.parseToInt(value).isPresent()) {
      value = JOptionPane.showInputDialog(this,
          "Desired integer value for " + param + ": ",
          String.valueOf(initialVal));
      if (value == null) {
        break;
      }
    }
    OptionalInt parsed = IntTools.parseToInt(value);
    if (parsed.isPresent() && parsed.getAsInt() >= 0) {
      toReturn = parsed.getAsInt();
    } else if (!Objects.isNull(value)) {
      JOptionPane
          .showMessageDialog(this, "The value must be a positive integer, please try again",
              "Try again", JOptionPane.WARNING_MESSAGE);
    }
    return toReturn;
  }

  /**
   * Refreshes the graphical view and supporting stored data.
   */
  private void update() {
    if (!Objects.isNull(listOfSpriteNames.getSelectedValue())) {
      this.shapeName = listOfSpriteNames.getSelectedValue().replaceFirst("-?[0-9]+ - ", "");
    }
    listOfKeyframes.removeAll();

    frames.clear();
    if (!Objects.isNull(shapeName)) {
      this.model.getROSpriteByName(shapeName)
          .getKeyframes()
          .entrySet()
          .stream()
          .mapToInt(Map.Entry::getKey)
          .distinct()
          .forEach(frames::addElement);
    }
    this.revalidate();
  }

  /**
   * Private helper method to update the lists based on a new shape being selected, given by its
   * layer and name. This method will scroll the shape list to the newly selected shape.
   *
   * @param layerToSelect the layer of the shape to select
   * @param nameToSelect  the name of the shape to select
   */
  private void updateToNewShape(int layerToSelect, String nameToSelect) {
    this.names.clear();
    this.model.getLayerROSprites()
        .forEach(e -> names.addElement(e.getKey() + " - " + e.getValue().getName()));
    this.listOfSpriteNames.validate();
    this.listOfSpriteNames.setSelectedValue(layerToSelect + " - " + nameToSelect, true);
    this.update();
  }
}
