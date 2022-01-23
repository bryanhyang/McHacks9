# McHacks9 RailVision Challenge

Hi! Welcome to our project page. Here we give a brief summary of the heuristics we used and programs we built to create our train schedules!

## Summary
The final solution we used was inspired by the traditional method used by train schedulers: manual adjustments based off of analysis of data. We developed a highly informative and rich visualization of each schedule which allowed us to pinpoint exactly where the periods of congestion and chokepoints were on our line, and how much remaining capacity was left on each train as it visited the stops. This allowed us to determine whether we should move more trains to a certain point in time. The final proposed schedule is labeled finalSchedule.csv.

![SampleSchedule](https://user-images.githubusercontent.com/50648015/150681125-9538956d-06bd-412c-a2e8-d2cf445a4ab2.png)

## Design
Our intuition for computationally finding solutions for optimizing train schedules led us to first focus on building a "grader" to evaluate how good a given train schedule was. We also decided that visualizing the schedule would be of help to our project, and thus added that to our design. Essentially, we found that the overall workflow for developing a solution started with grading and visualizing the provided baseline schedule so that we can learn from and improve off of. We subdivided these jobs into 2 folders, TrainSolution for the grading and SolutionVisualization for the visualizing. Above you can see the visualized sample schedule provided by RailVision.


## TrainSolution

### Grading
The grading consisted of adding each passengers arrival time at each station as a value we track within a queue, with one queue representing each station. We then iterated through the schedule. For each train within the schedule, we know the time the train arrives at each station, and the number of passengers the train picks up. We can simply subtract the arrival time of the passenger from the arrival time of the train to obtain how long a passenger was waiting at a stop, allowing us to average the value when done for all the passengers.

### Solving
The solving and generation was more involved, and required the breaking down of the problem into two subproblems. The problem of choosing when to send a train down the line, and the problem of deciding how many passengers to take from each station for each train. We used two heuristics, one was that rather than trying to minimize the average wait time of each passenger, we reduce the problem into trying to minimize the number of passengers on the platform at all times. This difference might seem small, but this realization was key in allowing us create a viable program. The second heuristic was to evaluate a score for the passengers at each station, with passengers at further stations having larger scored based on the time it takes for a train to reach them. This greedy choice simply chooses the passengers with the largest wait times at each station and chooses which passenger to pick up accordingly. This heuristic resulted in the following visualization.

![SolvedSchedule](https://user-images.githubusercontent.com/50648015/150681549-c42a1b2f-0058-43c4-bd8b-2deb467d0204.png)


## finalSchedule.csv

### Manual Heuristic
Towards the end of our time in this hackathon one of our group members developed and found a more mathematic manual heuristic for designing a more optimal schedule. Though we have yet to model it computationally, we were able to find and design a schedule that performed better than both our TrainSolution schedule and the provided example schedule. 

![MelodysSchedule](https://user-images.githubusercontent.com/50648015/150681683-d010961b-fbc9-4360-b000-05a66104ac5a.png)



