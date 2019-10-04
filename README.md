#CS 441 - Homework 1
###Name: Chinmay Nautiyal
###UIN : 677285320


##Instructions to install project
- Cloudsim; jar files included in the project directory
- sbt installation
- git clone<repo Link> to download the repository on to local machine
- on a terminal, with the working directory set to the repository's directory, run the comman 'sbt clean compile run'




##Analysis
- This repo is currently missing implementations for different Allocation Policies and Load Balancing Heuristics. The analysis of different models is therefore missing with this proeject. 
- For the given configurations, the simulation returns 

The user's demands/jobs are used to create a broker object which acts on their behalf and submits the requests to the reesource manager. 
- The cloudlet length is uniformly picked in the range from 10000 to 100000. 
-The driver code creates 5 sample jobs which dynamically create some cloudlets. 


- The screenshots are attached as part of a pdf. 



##Limitations
The simulator makes use of Cloudsim. The simulator does not simulate the creation of a network within the datacenter. The effects of network latency therefore cannot be simulated as the simulator is right now. In retrospect, I would want to use cloudsim-Plus, there seem to be a lot of useful things which I could have used. 




## Step 5 Map Reduce Implementation
The 



##Future Work

-Integrate cloudsim plus and make use of available vm allocation policies
-use load balancing heuristics to manage cloudlet load


- Simple Pricing and Complex Prices: last semester we did a project on simulating revenue and welfare guarentess when there is a simple price per resrouce, as compared to when there are complex fixed prices per resource. We did fairly simple probabilistic simulations; Cloudsim provides a much more complex and configurable environment to run such simulations. Although I would have liked to have done those things with this homework, my resource provider simply could not handle the incoming demands of this project. Hopefully, my system will scale better for future homeworks. 






