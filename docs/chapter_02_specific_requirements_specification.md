
# Chapter 2: Software Requirement Specification

- [Purpose](#purpose)
- [Document Conventions](#document-conventions)
- [Intended Audience and Reading Suggestions](#intended-audience-and-reading-suggestions)
- [System Features](#system-features)
  - [Authentication](#authentication)
    - [Functional Requirements](#functional-requirements)
    - [Non-Functional Requirements](#non-functional-requirements)
  - [Friendship Management](#friendship-management)
    - [Functional Requirements](#functional-requirements-1)
    - [Non-Functional Requirements](#non-functional-requirements-1)
  - [Task Management](#task-management)
    - [Functional Requirements](#functional-requirements-2)
    - [Non-Functional Requirements](#non-functional-requirements-2)
  - [Task History Graph](#task-history-graph)
    - [Functional Requirements](#functional-requirements-3)
    - [Non-Functional Requirements](#non-functional-requirements-3)
- [Description and Priority](#description-and-priority)


# Chapter 2: Software Requirement Specification

<details>
<summary>Purpose</summary>

The Task Management App is developed to provide a streamlined solution for organizing, assigning, and tracking tasks. Task management involves handling a significant amount of data, and this system aims to offer a more efficient and user-friendly alternative to traditional methods. The implementation focuses on key functionalities, including user authentication, online task creation, and an intuitive user interface.
</details>

<details>
<summary>Document Conventions</summary>

For clarity and brevity, specific terms are abbreviated, such as "TMA" for Task Management App and "SRS" for Software Requirement Specification. Important information is highlighted using bold lines.
</details>

<details>
<summary>Intended Audience and Reading Suggestions</summary>

This document is intended for five user types: Applicants, Dean office, Hall, Medical, and Register. Stakeholders, future implementation teams, and project supervisors will find valuable insights in this report.
</details>

## System Features

### Authentication
<details>
<summary>Description</summary>

The Authentication module is responsible for ensuring secure access to the system. Users are required to register and authenticate using their phone numbers. The process involves collecting the user's phone number during registration, sending a one-time verification code, and confirming the user's identity upon successful code verification.
</details>

<details>
<summary>Functional Requirements</summary>

- User Registration
  - Collect the user's phone number during the registration process.
  - Verify the phone number by sending a one-time verification code.
  - Confirm user identity upon successful code verification.

- Phone Number Management:
  - Allow users to add, update, or remove a phone number associated with their account.
  - Implement security measures to prevent unauthorized changes to phone numbers.
</details>

<details>
<summary>Non-Functional Requirements</summary>

- Security:
  - User authentication and communication must be encrypted using industry-standard protocols to ensure data confidentiality.
- Usability:
  - The authentication process should be user-friendly and intuitive, with clear instructions provided during phone number verification.
- Performance:
  - The authentication process should have low latency, ensuring quick verification to enhance the user experience.
- Scalability:
  - The authentication system must be scalable to accommodate an increasing number of users without compromising performance.
</details>

### Friendship Management
<details>
<summary>Description</summary>

Friendship Management handles the establishment and maintenance of connections between users within the system. This includes sending and receiving friend requests, accepting or rejecting requests, and managing the user's list of friends.
</details>

<details>
<summary>Functional Requirements</summary>

- Friend Request Handling:
  - Design the interface for sending and receiving friend requests.
  - Implement logic for accepting or rejecting friend requests.

- Friend List Management:
  - Develop functionality for maintaining a user's list of friends.
  - Allow users to unfriend or block other users as needed.
</details>

<details>
<summary>Non-Functional Requirements</summary>

- Privacy:
  - Friend requests and friend lists should be private and visible only to the concerned users to maintain user privacy.
- Performance:
  - The system should efficiently handle friend requests and updates even as the user base grows.
- Scalability:
  - Friendship management features must scale seamlessly with the growth of the user base.
</details>

### Task Management
<details>
<summary>Description</summary>

Task Management involves the creation, assignment, and tracking of tasks within the system. Users can create tasks, assign them to others, and monitor the progress of tasks.
</details>

<details>
<summary>Functional Requirements</summary>

- Task Form Design:
  - Create an intuitive user interface for entering task details.
  - Include relevant fields such as title, description, due date, and priority.

- Task Validation:
  - Implement validation checks to ensure the correctness of task details.
  - Notify users of any errors in the task creation process.
</details>

<details>
<summary>Non-Functional Requirements</summary>

- Usability:
  - The task creation and assignment interfaces should be user-friendly, promoting ease of use.
- Performance:
  - The system should handle a large number of tasks and assignments without significant degradation in response times.
- Reliability:
  - Task data should be consistently stored and retrieved, ensuring the reliability of task management functionalities.
- Scalability:
  - The task management system must be scalable to accommodate an increasing number of tasks and users.
</details>

### Task History Graph
<details>
<summary>Description</summary>

The Task History Graph provides a visual representation of the historical data related to task completion, assignment trends, and user contributions over time.
</details>

<details>
<summary>Functional Requirements</summary>

- Graphical Representation:
  - Design a graphical representation of task history data.
  - Include elements such as completed tasks, assigned tasks, and user engagement.

- Filtering and Analysis:
  - Allow users to filter the graph based on time periods, task types, or user-specific data.
  - Provide analytical insights into task management trends.
</details>

<details>
<summary>Non-Functional Requirements</summary>

- Performance:
  - Generating and rendering the task history graph should be efficient and responsive.
- Scalability:
  - The graphing functionality must scale with increasing amounts of historical data.
- Usability:
  - Users should be able to easily interpret and navigate the task history graph.
- Availability:
  - The task history graph should be available and accessible whenever users need to review historical data.
</details>

<details>
<summary>Description and Priority</summary>

This feature allows registered users to access the system as applicants, providing detailed information under admin control. The primary goal is to facilitate easy interaction between users and the system, ensuring accurate information storage and retrieval.
</details>
