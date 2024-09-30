# TaskHive

## 1. Purpose
TaskHive is a versatile and user-friendly To-Do List application designed to help users manage their daily tasks, track achievements, and enhance productivity. This mobile application, developed by Nishaylin Sigamoney, Luke Sowray, and Samuel Bekhet, aims to provide a structured and intuitive approach to task management, offering features that cater to various user needs, from simple task tracking to advanced productivity tools.

## 2. Features
TaskHive offers a broad range of functionalities, ensuring users can effectively manage their tasks and improve their productivity. Below is a detailed breakdown of each feature:

### **Authentication**
- **Single Sign-On (SSO):** Users can effortlessly register or log in using their Google accounts, ensuring a quick and secure authentication process.
- **Email/Password Registration:** Traditional sign-up and login options are available for users who prefer using their email and password.
- **Biometric Authentication:** *Future Implementation* - Biometric options (fingerprint or facial recognition) will be added to enhance security.

### **Task Management**
The Task Management module is the core of TaskHive, offering users comprehensive tools for managing their to-do lists:
1. **Add Task**
   - Users can create a new task with multiple details:
     - **Task Title:** Brief name for the task.
     - **Task Description:** Detailed information about the task.
     - **Start Date & End Date:** Define when the task begins and when it should be completed.
     - **Location:** Task location for better context.
     - **Category:** Assign a category (e.g., Work, Personal, Study) to organize tasks.
2. **Search Task**
   - Users can search tasks using keywords from the title or description.
   - A responsive search button facilitates easy retrieval of tasks.
3. **View Tasks**
   - Displays all tasks added by the user in an organized list.

### **Tracking and Achievements**
This section motivates users by tracking their progress and achievements:
1. **Achievements**
   - **Add Achievement:** Users can document accomplishments by inputting details like Title, Description, and Date.
   - **View Achievements:** All achievements are listed, allowing users to review their progress.
2. **Habit Tracking**
   - *To Be Implemented* - This feature will help users track daily habits and maintain consistency.

### **Productivity Tools**
Tools designed to help users maintain focus and increase productivity:
1. **Context-Aware Tasks**
   - Users can input a task type and receive a list of suggested tasks based on that input. These tasks can be edited and saved to the user’s task list.
2. **Focus Mode**
   - Provides a customizable timer, allowing users to set a duration for focused work sessions, helping them stay on track.

### **Settings**
- Users can personalize the app’s appearance and settings, including enabling light/dark themes and customizing notifications.

## 3. Technologies Used
TaskHive leverages a range of technologies to deliver its features effectively:

- **Kotlin:** The primary programming language used to develop the Android application, providing concise and expressive code.
- **Android Studio:** The official IDE for Android development, used for building, debugging, and testing TaskHive.
- **Firebase:** 
  - **Authentication:** Provides secure user login and registration using email/password and Google SSO.
  - **Realtime Database:** Stores user data (tasks, achievements) in real-time, ensuring quick access and synchronization across devices.
- **Node.js & Express.js:** Used to build the REST API that handles backend operations for task management and data storage.
- **Google Cloud Datastore:** A NoSQL cloud database for storing task-related data, ensuring scalability and reliability.
- **Retrofit:** A powerful HTTP client for Android, used to make network requests to the REST API.
- **Render:** A cloud hosting platform where the REST API is deployed, ensuring reliable and scalable access to backend services.
- **GitHub:** Version control and collaboration tool used for managing the project’s source code, tracking changes, and collaborating among the development team.

## 4. Installation and Compilation
To set up and run TaskHive on your local device, follow the steps below:

### 4.1 Prerequisites
- **Android Studio**: Ensure you have the latest version of Android Studio installed on your machine. You can download it from here >> https://developer.android.com/studio
- **JDK 11 or later**: The Java Development Kit is required for Android development. You can download it from here >> https://www.oracle.com/java/technologies/javase-downloads.html
- **Git**: Required for cloning the repository from GitHub. You can download it from here >> https://git-scm.com/downloads

### 4.2 Running the Application
1. **Clone the Repository from GitHub:**
   
   git clone https://github.com/ST10026652/OPSC7312-TaskHive
   
2. **Open the Project in Android Studio:**
   - Launch Android Studio and select `Open an existing project`.
   - Navigate to the cloned `TaskHive` folder and open it.
3. **Sync Project with Gradle:**
   - Allow Android Studio to sync the project and download all necessary dependencies.
4. **Build the Project:**
   - Go to `Build > Make Project` or press `Ctrl + F9` to compile the project.
5. **Run the Application:**
   - Connect an Android device or start an Android Emulator.
   - Click the `Run` button (green arrow) to deploy the app on your device/emulator.

## 5. Usage Guide
### 5.1 Getting Started
- **Registration:** New users can register using their email/password or Google SSO.
- **Login:** Registered users can log in directly. Biometric authentication is planned for future updates.

### 5.2 Task Management
- **Adding Tasks:** Use the ‘Add Task’ feature to create tasks with detailed information, ensuring efficient task management.
- **Searching and Viewing Tasks:** Use the search feature or view all tasks from the task list.

### 5.3 Achievements and Productivity
- **Achievements:** Track accomplishments by adding and viewing achievements.
- **Context-Aware Tasks and Focus Mode:** Utilize these features to improve productivity and maintain focus.

## 6. REST API Integration
The TaskHive application is integrated with a REST API, which handles all backend processes related to task management and data retrieval.

### **API URL:**
```
https://taskhive-api.onrender.com
```
### **API Details:**
- **Backend:** The API is built using Node.js and Express.js, ensuring fast and scalable operations.
- **Data Storage:** All data is stored in Google Cloud Datastore, offering reliable storage and quick retrieval.
- **Integration:** The API is integrated with TaskHive using Retrofit, allowing seamless communication between the mobile client and backend services.
- **Functionality:** 
  - `POST /tasks`: Adds a new task to the database.
  - `GET /tasks/{id}`: Retrieves task details using the task ID.
  - `PUT /tasks/{id}`: Updates task information.
  - `DELETE /tasks/{id}`: Deletes a task.

## 7. Future Enhancements
TaskHive has several planned enhancements to improve its functionality:
- **Biometric Authentication:** Adding fingerprint and face recognition for improved security.
- **Habit Tracking:** Introducing features to help users monitor and maintain daily habits.
- **Push Notifications:** Implement reminders for upcoming tasks and achievements.
- **Data Analytics and Insights:** Provide users with detailed analytics on their productivity, task completion rates, and more.

## 8. Authors
- **Nishaylin Sigamoney** - Developer 
- **Luke Sowray** - Developer
- **Samuel Bekhet** - Developer


https://github.com/ST10026652/OPSC7312-TaskHive

