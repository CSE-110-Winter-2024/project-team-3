___
# CSE 110 - Chattedgpt  
### Successorator: :iphone: CSE 110 :iphone:
___

- Project Description: https://canvas.ucsd.edu/courses/52058/assignments/728013
___

### Milestone 1
####User Story 1
**(high)**
AS A user,
I WANT to add new goals to the current day’s goals
SO I CAN plan events for my day

- Scenario 1: Adding new goals
GIVEN I am on the daily goals page
WHEN I select the option to add a new goal
AND I use the keyboard to enter the new goal
AND I select the checkbox button
THEN the new goal should be added to the list of the current day’s goals 

---

#### User Story 2
**(low)** 
AS a user, 
I WANT to be able to have my unfinished tasks rolled over to the next day 
SO I don’t forget to complete them as soon as possible

- Scenario 1: Dealing with finished and unfinished tasks at the end of the day
GIVEN on a day I have both finished and unfinished tasks
WHEN the day ends and a new day rolls over
THEN the goals on the next day should be populated with the unfinished tasks of the day prior and the finished goals will not be there
AND the order of the goals prevail from the previous day

---

#### User Story 3
**(medium)**
Task Hierarchy of Unfinished and Finished
AS a user,
I WANT the most ordering of tasks to be maintained by the order that I inputted in, 
SO THAT I can keep track of the order I added at any moment.

- Scenario 1: Adding goal to list without finished goals
GIVEN I am adding a new goal to the list of unfinished goals
WHEN I add a new goal to the list
THEN the goal should be added to the bottom of the list with all of the unfinished tasks.

- Scenario 2: Adding goal to goal list with finished goals
GIVEN I have a list of 4 goals, 2 unfinished and 2 finished.
WHEN I add a new goal
THEN the goal will be added as an unfinished goal to the bottom of the unfinished goals, with the finished goals underneath the 3 unfinished goals
(3 unfinished goals, 2 finished goals)

---

#### User Story 4: Assuming Keyboard already shown
**(medium)**
AS A user,
I WANT TO be able to speak a phrase into an EVENT
SO I CAN add events to my day’s plans

- Scenario 1: Creating new goal with voice without incorrect inputs
GIVEN I am creating a new goal
WHEN I press the microphone button 
AND speak into my phone’s microphone
THEN the new goal will be populated with what I said into the microphone

- Scenario 2: Creating new goal with voice with incorrect inputs
GIVEN I am creating a new goal
WHEN I press the microphone button 
AND speak into my phone’s microphone
AND voice autocorrect with wrong words
THEN I can manually correct the word
AND the new goal will be populated with what I said into the microphone

- Scenario 3: Creating new goal with voice and canceling it
GIVEN I am creating a new goal
WHEN I press the microphone button 
AND speak into my phone’s microphone
AND I want to cancel my goal addition

---

#### User Story 5
**(low)**
AS A user,
I WANT TO be able to mark goals as complete
SO I KNOW what I have finished and what still needs to be done

- Scenario 1: Marking completed tasks
GIVEN that I finished my tasks for the day
WHEN I still have unfinished tasks
THEN I mark my finished tasks as complete
AND the unfinished tasks still remain
