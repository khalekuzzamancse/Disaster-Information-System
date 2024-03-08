
<!-- 
# Largest heading (equivalent to H1)
## Second largest heading (equivalent to H2)
### Third largest heading (equivalent to H3)
#### Fourth largest heading (equivalent to H4)
##### Fifth largest heading (equivalent to H5)
###### Smallest heading (equivalent to H6)

Since github does not support hardcoded font size,so use heading.

note that h1 and h2 comes with horizontal line 
 -->

<!-- Cover Page Start-->
### <p align="center" > SOFTWARE REQUIREMENTS SPECIFICATION</p>

<!-- Remove the horizontal line  after title -->

#### <p align="center" >Student Activity Monitor and Alert Generator</p>

<p align="center" >
    Version: 1.2
    <br>16th Feb 2017
</p>

<p align="center" >
    Prepared by
    <br>Md YYY, Md ZZZ
</p>

<p align="center" >
    IIT GUWAHATI
</p>

<p align="center" >
    Supervised by
    <br>Prof. Samit BHATTACHARYA
</p>

<!-- Cover Page End-->
<!-- making horizontal line using ##-->
## 


<!-- Introduction Section start-->
## Introduction

<!-- Purpose sub section-->

### 1.1 Purpose

<p align="justify" >
<!-- The text alignment should be=justify content-->
  The purpose of this document is to provide a detailed description of the requirements for the Student Activity Monitor and Alert Generator software. It will outline the purpose, complete declaration, and system constraints for the development of the system. This document serves as a proposal to be presented to the customer for their approval and as a reference for the development team while creating the initial version of the system.
</p>


<!-- Document Conventions sub section-->
### 1.2 Document Conventions
| Term        | Definition                                                |
|-------------|-----------------------------------------------------------|
| Instructor  | Person who shall be using the app for monitoring         |
| Student     | Person who shall be monitored by the instructor           |
| Device      | An electronic device using which instructor is delivering their lecture |
| DESC        | Description                                               |
| DEP         | Dependency                                                |
| RAT         | Rational                                                  |
| ...         | ...                                                       |
| ...         | ...                                                       |

<!--Project Scope sub section-->
### 1.3 Project Scope
<p align="justify">

This software is meant to be deployed in an IT-enabled large classroom environment where the lecture delivered by the instructor is via a device through which both audio and video are transmitted. This software shall allow the instructor to conveniently monitor the attention of their students in real time.
 </p>

<!-- Introduction Section Ended-->

<!-- -->
<!-- Overall Description Section start-->
## Overall Description

<!--Product Perspective sub section-->

### 2.1 Product Perspective
<p align="justify">
This software will be a top up on the already existing system for communication between the instructor and the students inside a classroom in which the slides of the lecture are delivered to the students' devices along with audio which they can listen to via earphones. The slide transitions are handled automatically by the existing system. The responsibility of the proposed system shall be to monitor the attention of the students and ensure that they are not chatting/slacking or are indulged in entertainment.
</p>

<!--Product Functions sub section-->
### 2.2 Product Functions
<p align="justify">
The product leverages the sensors such as gyroscope, proximity sensor, accelerometer in the devices used by the students which determine factors such as orientation, distance from user, shaking frequency which enable the system to determine the attention of students and alert the instructor accordingly.
The application should also allow the instructor to see the summary of the data collected during lecture which shall be helpful during feedback sessions and grading.


</p>

<!--Product Functions sub section-->
### 2.3 User Classes and Characteristics
<p align="justify">
The users are divided into two classes viz. the instructors and students. The students interact with their mobile devices having the sensors which are used to determine their attention by the software. This can then be viewed by the instructor in real time or at his/her leisure.

</p>

<!--Product Functions sub section-->
### 2.4 Operating Environment
<p align="justify">
The environment is an IT enabled large classroom where both the above user classes carry their devices. The slides are shared from the instructor through his/her device to the students along with slide transitions and audio which can be heard by plugging earphones into their devices. Due to the presence of a large classroom, it is not possible for the instructor to observe all students and therefore we have the need for an app which measures the attention of the student.
The smartphones then, with their sensors take various parameters like device orientation and others into account in determining the attention of the student.


</p>

<!--Product Functions sub section-->
### 2.5 Design and Implementation Constraints
<p align="justify">
For the above purpose of implementing the app, we are forbidden to use the camera for measuring the attention. The students might exploit the sensors used to portray themselves as paying attention even when they are not.
This gives a false positive in cases when the student uses a stand to maintain the orientation rendering the accelerometer and gyroscope functions redundant.


</p>

<!--Product Functions sub section-->
### 2.6 Assumptions and Dependencies
<p align="justify">
We assume the presence of an IT-enabled large classroom with the availability of a system that can display slides from the instructor to the students along with slide transitions is available.
One assumption about the product is that it will always be used on mobile phones that have enough performance. If the phone does not have enough hardware resources available for the application, for example the users might have allocated them with other applications, there may be scenarios where the application does not work as intended or even at all. Also, we assume the devices have sophisticated sensors to meet our requirements of measuring even minute shaking to make the software precise.

</p>

<!--Overall Description ended-->

<!--Section: External Interface Requirement Start-->


## External Interface Requirement

<!---
Subsection: User Interface

- It describes what kind of user interface we want.
- What are  the characteristic of these user interface should  have
- This section does not describe the whole details of every UI element,instead it will show how the entire app UI should look and feels.
- Here we mention the high level requirement

Example as:
-->

### 3.1 User Interface


<p align="justify"> 
The software in the devices should allow the instructor to have an option to connect with the devices of all the students present in the lecture classroom. The students must also have a complementary option to connect to the instructor's device.
Thereafter the instructor should have options getting real time statistics of the attention span of the attendees. The UI should be more focused on being organized and work oriented rather than having fancy style and animation. The interface should have rigorous error management.
</p>


<!---
Subsection: Hardware Interfaces

- What are the hardware capabilities of the user,in order to run the app?
-Just mention the high level requirement 
Example as:
-->

### 3.2 Hardware Interfaces

<p align="justify"> 
The students' mobile devices should have all the necessary sensors like the gyroscope, accelerometer, and proximity sensor with enough specification to handle the task at hand. Also, the devices should have high performance and space to be able to run the software as a background 'Daemon' service.
The instructor's device should also have high enough performance to connect to all the users. The network should have enough capability to handle the traffic of the large classroom.
</p>



<!---
Subsection: Software Interfaces
-What are the software(OS) capabilities of the user,in order to run the app,
-Just mention the high level requirement 
Example as:
-->

### 3.3 Software Interfaces

<p align="justify"> 
The data collected by the devices carried by the students has to be transported to the device being carried by the instructor. The medium of transport shall be deduced by the design team if it could be using wifi, bluetooth or some other wired connection. The instructor's device shall receive the data from all the student devices.
The system calls required for the communication would have to be invoked. For gathering sensor data the driver modules of the student devices would have to be cooperated with.
</p>



<!---
Subsection: Communications Interfaces

Example as:
-->
### 3.4 Communications Interfaces

<p align="justify"> 
The transfer rate between the devices should be sufficient enough for the instructor to reflect upon the findings of the application. Since the application will be exposed to a limited environment thus the encryption concerns are not humongous.

</p>


<!--
Note that 
While going through this example SRS is that not   necessarily everything will be required  in your case. So you have to take a call   on which interfacing requirements you  want to highlight and which are not very   relevant

-->
<!--Section: External Interface Requirement ended -->




<!-- Section: Functional Requirement -->
## Functional Requirement
<!--

Section: Functional Requirement 
This is an important section of any SRS document.

This will contain the functional hierarchy means high level function what do and what do it child function

According to the type of user (mentioned in user classes),the functional requirement will be divided into different groups.
In example we have two types of user,Instructor and Student so we have to divide the functionality requirement into two groups.

Here are basically the top level function and the child function of the top level function are described.


Each function has some attribute such as:
-INPUT: 
What the input of this function
-Use noun 
-Do not describe details ,and what this input details not to describe
-Do not use action verbs,because action verbs are used to describe a process.
-You can think the INPUT as a function parameter name,without exposing what the parameter do
-OUTPUT:
-What the input of this function
-Use noun 
-Do not describe details ,and what this input details not to describe
-Do not use action verbs,because action verbs are used to describe a process.
-You can think the as a function Return type name,without exposing what it do
Example as, OUTPUT: Error message (but do not describe why this error message )
OUTPUT:Show Error message (this violation,it uses action verb,just mention the output name)
-DESC:
T-his the description section,this a paragraph section,here you can use action verbs as many as you want,you are allowed to describe -what the input and output does.
-This is optional 
-RAT
The rational is here
-This is optional 


Examples
-->

### 4.1 User Class 1: Instructor

#### 1 Start Class Session
<p align="justify"> 

- ID: FR_INS_1
- INPUT: Course name and class group
- OUTPUT: Related course page
- DESC: Start the application with the details of the current course and lecture.
- RAT: The Instructor might be taking multiple courses and lectures involving different students so the application needs to adjust to the circumstances accordingly

#### 1.1 Establish Connection Instructor

</p>


<p align="justify"> 

- ID: FR_INS_1.1
- INPUT: Course administration details
- OUTPUT: Connection between Instructor and Student
- DESC: Establish connection with the students present currently in class
- RAT: Without connection between the devices information cannot be communicated

</p>

#### 1.1.1 Real Time Individual Student Attention Statistics


<p align="justify">

- ID: FR INS 1.1.1
- INPUT: Sensor data from student devices
- OUTPUT: Details of the students' attention at that instant
- DESC: An option for getting insight on real time attention activity of the students on an individual basis
- RAT: For monitoring an individual student


</p>


#### 1.1.2 Real Time Overall Class Attention Statistics

<p align="justify"> 

- ID: FR INS 1.1.2
- INPUT: Sensor data from student devices
- OUTPUT: Summarized Pictorial or textual portrayal of the class' attention details at that instant
- DESC: An option for getting insight on real time attention activity of the students on an aggregate basis
- RAT: For monitoring the class as a whole

</p>


#### 1.1.3 End Class Session

<p align="justify"> 

- ID: FR INS 1.1.3
- INPUT: Current Session tokens
- OUTPUT: Closing current page and connection
- DESC: Close the currently opened connections and the application


</p>



#### 1.2 Connection Error Notification - Instructor

<p align="justify"> 

- ID: FR_INS_1.2
- INPUT: Data flow across instructor and student devices
- OUTPUT: Error message
- DESC: Notify Instructor in case of connection drop of a particular student
- RAT: In case of faulty connection or inefficient instruments appropriate error message should be generated

</p>



#### 2 Overall Class Attention - Non-Real Time

<p align="justify"> 

- ID: FR INS 2
- INPUT: Course administration details
- OUTPUT: Overall attention or attendance count
- DESC: Get aggregate summary of the attention spans of the individual students on a per lecture basis
- RAT: Helps the instructor during feedback sessions and grading process



</p>

#### 2.1 Individual Student Monitor - Non-Real Time

<p align="justify"> 

- ID: FR INS 2.1
- INPUT: Course and lecture names along with the name or roll no. of the student
  OUTPUT: …
- DESC: Search for a particular student for his/her specific summary

</p>



#### 2.2 Upper Division Threshold Filter

<p align="justify"> 

- ID: FR INS 2.2
- INPUT: Data taken from the sensor devices over the span of the course
- OUTPUT: list of students with more than 65% attention span overall


</p>


#### 2.3 Lower Division Threshold Filter

<p align="justify"> 

- ID: FR.INS.2.3
- INPUT: Data taken from the sensor devices over the span of the course
- OUTPUT: list of students with less than 10% attention span overall

</p>



### 4.2 User Class 2: Student

#### 1 Open Course Page

<p align="justify"> 

- ID: FR STUD 1
- INPUT: Entering course name and lecture details
- OUTEUT: Related course page
- DESC: Start the application with the details of the current course and lecture.
- RAT: The Student might be taking multiple courses and lectures involving different instructor so the application needs to adjust to the circumstances accordingly

</p>


#### 1.1 Establish Connection

<p align="justify"> 

- ID: FR STUD.1.1
- Student
- INPUT: Course Details and unique token from instructor
- OUTPUT: Connection Establishment
- RAT: Without connection between the devices information cannot be communicated.

</p>

<!--Section: Functional Requirement ended -->

<!--Section: Non Functional Requirement -->


## Non Functional Requirement

### 5.1 Performance Requirements


<p align="justify"> 

The search enabling the instructor to view the summary of a student and give his analysis should be clear and well understandable.
The program must update the real time statistics at a fast enough pace so that the instructor can have a clear view on the current attention of the class.
The program must be able to handle the information of the entire class i.e in full attendance almost 90 students and also individually not be glitch in a given mobile device.
Time to give the error message on losing a particular connection should be minimal (almost immediate).

</p>

### 5.2 Security Requirements

<p align="justify"> 
The students enter their authentication (webmail) to enable access to connection to the slides.
 Since the environment is local i.e limited to the classroom no security precaution or encryption of data is necessary

</p>