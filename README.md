# My Personal Project

## A Monty Hall Simulation

In my STAT 302: Introduction to Probability, we talked about the Monty Hall game show
situation. To read more about the setup of the game, you can visit:
https://statisticsbyjim.com/fun/monty-hall-problem/. Very briefly, the game runs as follows:

There are three doors to pick from. Two of them have a goat behind it, and one of them has
a brand-new car behind it. The door you pick corresponds to the item you are bringing home.
Monty Hall (the game show host) tells you "Pick a door!". You pick any door, let's say I pick 
door 1. After telling Monty, he opens *one of the other* door - let's say door 2 - and shows me 
a goat standing behind it. Now, he asks "would you like to switch to door 3? Or 
would you like to stick with door 1?" This is where the contestants make their final decision.

During class, we analyzed the 
mathematics behind it, and our TA mentioned briefly: "It would be cool to build
some kind of simulation, where you can run the options of 'switch' or 'no switch' 
numerous times, and you would be able to show that the math (though counter-intuitive)
is true." My application would be a dashboard where you can fully learn the Monty Hall 
problem interactively. It will include:

- A first round where users simply play through the game show as a regular contestant.
- Then, users can choose to run a simulation of the game 100, 500, or 1000 times.
- A small counter on the right will display the win vs lose record of their decision.
- Lastly, the mathematics behind the process will be displayed in an interesting way.

This project is very interesting to me as I am an avid mathematics tutor/teacher 
in my spare time. I would like to extend my help to students who may struggle with 
math in one way or another through this application. Hence, potential users of this 
program will be students who want to understand the Monty Hall problem better. Hopefully,
this can also be a way for me to practice my upper-year level statistics skills too.

## User Stories

- As a user, I want to be able to pick to switch doors or not switch doors.
- As a user, I want to be able to add as many doors, goats, and cars to the game as you'd like.
  to "design your own game" and explore the mathematics behind it.
- As a user, I want to see the wins and loses change as the simulation plays.
- As a user, I want to see the probability principles that underlie this problem.
- As a user, I want to be able to save the state of my custom-built game show set up.
- As a user, I want to be able to load a saved custom-built game show.

## Phase 4: Task 2
I implemented a bidirectional relationship between ButtonsPanel and DoorPanel as well as between
ButtonsPanel and GraphPanel. This is mostly because the GraphPanel and DoorPanel listen to the ButtonPanel.

I also implemented a type hierachy with the Prizes, where Prize is an abstract class and both Car and Goat
extend that class and override two of its methods.

## Phase 4: Task 3

There are three main things I would refactor if I had the time.
1. I actually stumbled upon this solution half way through Phase 2, but just did not find the time to refactor
it properly. This problem lies in the relationship between **RunGameShow** and the Goat, Car, and Door classes,
   *even though* it already contains a GameShow that is associated with those classes. In the UML diagram,
   this is captured by the protruding lines from the UI section to the model section. I actually solved this problem in 
   GameShow already with the method setupStandardGameShow(), but I just never removed the associations in 
   RunGameShow.
   

2. I recognized during Phase 3 that the GUI classes GraphPanel and DoorPanel are observers of the ButtonPanel class.
   If I had time, I would have implemented the Observer Pattern on them to reduce some redundancies. GraphPanel and 
   DoorPanel would implement the Observer interface, the ButtonsPanel would implement the Observable interface. Then,
   notifyObservers() would be called in the actionPerformed() method in ButtonsPanel and update would be specific for 
   GraphPanel and DoorPanel.
   

3. Lastly, I would want to eliminate the need for a JsonReader & JsonWriter in the Simulations class. Once again, there
seems to be unnecessary coupling as GameShow already contains both of those classes, and it forms a triangle between 
   GameShow, Simulation, and the data persistence classes.