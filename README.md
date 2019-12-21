Our design is meant to support animations, shapes, and the model.

For Shape interface:
This is meant to guarantee the methods we think all supportable Shape objects need. This is so
that any Shape in the future that needs to be supported can be easily integrated to our model.

For EasyAnimatorModel interface:
We think this encapsulates all the necessary methods for the model to have. The model is supposed
to hold the state of the animations, and so needs a way to say what shapes and animations it
currently has, and how it can relay that information to the Controller and Views in the future
assignments. This is simply done with the getter and adder methods.

For Animation interface:
We think this encapsulates all the methods needed to express what a motion/animation on a shape
actually is. It simply says all Animations need to give information about what their state is.

For Rectangle Class:
This is to capture how we think is best to represent a Rectangle. It is meant to capture all
that is unique about a rectangle in this assignment. Most of the methods on 2D shapes are
applicable to all 2D shapes, and so we put most of that code in AShape. In this, it is meant to
hold all that is unique to a Rectangle, mainly how to represent it, what equality for rectangles
in our model means, and unique interpretations of an abstract 2D shape.

For Ellipse Class:
This is in a similar vein to the Rectangle class. It is meant to hold all that is unique about an
ellipse, mainly how it is rendered and what equality means for an ellipse, and what the unique
intepretations of an abstract 2D shape is for an ellipse.

For EasyAnimatorModelImpl:
This is our implementation of our EasyAnimatorModel interface. It is meant to hold the overall
state of the animations, by holding inormation about what shapes are in the animation, what
animation objects are in the animation, keeping time and speed. It is meant to also make constraints
on the uniqueness of animations and shapes (mainly shapes of the same type cannot have the same
name, and animations cannot overlap and disagree on common endpoints).

For AShape:
This is our abstract representation of a 2D shape. It is meant to hold all that is the same among
our supported 2D objects. By this, we mean all the methods that supported shapes support and
all the same instance variables that are shared by 2D shapes in our model.
Invariant: Names have to be unique among Shape objects of the same type. This is to be able to
distinguish shapes of the same type. Since a Shape object can mutate, via the animations, this
is the way we decided we could keep track of Shape objects.

For AnimationResize:
This is meant to encapsulate what is unique to the resizing animation that is meant to be supported.
This extends the AAnimation class to accomplish this. The uniqueness of resizing is the ending
dimensions of how large the shape is supposed to transform to.

For AnimationMove:
This is our class to encapsulate what is currently unique about moving a shape object. This
also extends AAnimation to accomplish this. The uniqueness of move is the ending coordinates of
where the shape is supposed to end up.

For AnimationColorChange:
This is our class to encapsulate what is currently unique about changing the color of a shape
object. This also extends AAnimation to accomplish this. The uniqueness of color changing is
the final color the shape is supposed to be.

For AAnimation:
This is our abstract class for animations. It is meant to hold what is the same among all
supported Animations. This boils down to just the shape being manipulated, the start and end times
of an animation, and the start of textually representing an animation.

CHANGES FROM ASSIGNMENT 5 - 11/9/2018

In this assignment we worked on providing a textual, SVG, and visual view for our model
implementation. We also made several changes to our model implementation by adding an enumeration
for the types of animations and shapes so that we could easily distinguish between the different
types. We created two different view interfaces, one main interface that all our of views
implemented. This featured both a display and a refresh method. The other interface was used solely
for visual view and focused on drawing ellipses and rectangles. Our textual view was mainly a
reimplementation of our toString() method from the model. We were unable to finish our visual view.
If we had more time we would have focused on differentiating between the different types of
shapes and their animations and use that information to draw each animation. We also ran into
problems with our builder where we did not store the state of the shape before and after the
animation was applied to the shape.

In order to design our view implementations, we created a main View interface that specifies the
functionality that any view (textual, SVG, visual) should have. We then created several separate
view interfaces that promised specific functionality per view. We found this method to be the most
appropriate way to design our view was it compartmentalized features we needed from specific views.
We then created several view implementations (textual, SVG, visual) that implemented these features.
Our textual view was basically a redesign of our model's toString() method, and SVG was just an
enhanced version of the textual view. Our visual view used the components of Java's JFrame and
JPanel classes, as well as Graphics in order to displace images on the user's screen.


CHANGES FROM ASSIGNMENT 6 - 11/20/2018

In this assignment we made several changes to our model. One of the changes we made to our model
was that now our model only holds a list of shapes. Shapes hold their respective animations, which
means that our model can operate on animations by getting them through its shapes. We also added a
utility class to hold our guards which throw exceptions where needed. We also removed our shape's
dependency on the Point class and refactored so that they hold integer x and y fields to represent
coordinates. Our model now also holds a list of Keyframes from which are constructed through the
builder. We also have added a Controller, an EditorViewImpl, and a Bounds. The Bounds is to hold
the view box that may be specified by a user. The EditorViewImpl is our implementation of the
interactive Visual view. It is currently a work in progress, as we are unsure of how to connect
the keyframe logic we implemented into a UI. The Controller extends multiple action listeners to
respond effectively when the user types, mouse clicks, or uses our self explanatory UI. We
currently do not have any keyboard shortcuts that are not also implemented with the UI.

FINAL SUBMISSION

In this assignment we wrote adapters for both our provider's model and shape interfaces. We were
unable to get their EditorView to work with our implementation. We are unable to edit shapes, or
use any of the playback, saving, or loading features of their EditorView. Our code is able to use
functionality from their provided view however, and we wrote tests for our adapters to make sure
that they achieved the correct functionality.

Our final Review and code critique are in the provider package under resources.
