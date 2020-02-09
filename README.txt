Project name

Game Box - Just Another CSC207 Project


Description

Android app collection of three mini-games: Tetris, Rhythm Game and Maze.


Features

•	REST API server that manages individual user accounts through login page,
    local statistics for each game and global leaderboards
•	Tetris Game has dynamic acceleration and custom 7-piece generation
    algorithm to ensure even spread of player-desired pieces
•	Rhythm Game has two game modes: Song mode with specific note intervals tuned to
    the beat, and random note generation mode with increasing difficulty over time
•	Maze Game has recursive random maze generation algorithm and two types of controls
•	Wrapper Game mode allows you to play through all three games
    - score and statistics are cumulative


Installation guide

1.	Open IntelliJ IDEA -> Check out from Version Control -> Git -> Clone the following repo for REST API:  https://github.com/mooncrest/CSC207RestApi
2.	Open rest api/src/main/java/CSC207/CSC207RestApi/Csc207RestApiApplication.java and run the file with Application configuration.
3.	Clone the project into Android Studio.
4.	Find your computer’s IP address and copy it.
5.	Open phase2/app/src/main/java/uoft/csc207/gameapplication/Utility/GameRequestService/RestApiConnector.java and replace String URL value with your IP address.
6.  ^^ Don’t forget the :8080/ suffix!
7.	Run on Pixel 3 XL on Android 9.0 emulator.
8.	Use "admin" for username and password is not required to login. Alternatively, create a new account and login.


Credits

This project was made for CSC207 Phase 2 by:

Anthony Justin Duong
Ming Xi Liu
Junyoung Seok
Ting Jui Peng
Guangwei Xia







