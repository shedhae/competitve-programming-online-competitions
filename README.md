# Competitive programming contests website


## **Project Overview**

We are building a Codeforces-like platform to host competitive programming competitions at our university. The platform will include:

- A **home page** with active competitions and recent problems.
- A **problem set** where users can view and solve problems.
- A **competition system** to host real-time programming contests.
- A **submission system** to evaluate user code and provide results.


## **Entity relationship diagram:**

## Functional Requirements

This database will support:

* CRUD operations for users , competitions, teams, problems and submissions.
* Tracking all versions of team submissions, including multiple submissions for the same problem.
* The problem details like test cases, topics and to which cometition associated.
* Adding multiple clarifications from a user to the competition creator .
* Adding multiple announcements to participants from the competition creator.

## Representation

Entities are captured in SQLite tables with the following schema.

### Entities

The database includes the following entities:

#### Users

The `users` table includes:

* `id`, which specifies the unique ID for the user as an `INTEGER`. This column thus has the `PRIMARY KEY` constraint applied.
* `username`, which specifies the username as `TEXT`, given `TEXT` is appropriate for name fields. This column thus has the `NOT NULL` and `UNIQUE` constraint applied.
* `mail`, which specifies the user's email as `TEXT`,and `UNIQUE`.
 This column thus has the `NOT NULL` constraint applied.
* `password`, which specifies the user's password,given `TEXT` is appropriate for name fields . This column thus has the `NOT NULL` constraint applied.
* `rating`, which specifies when the user's rating. type of `INTEGER` and `DEFAULT 0` AS a default value.

#### Teams

The `teams` table includes:

* `id`, which specifies the unique ID for the team as an `INTEGER`. This column thus has the `PRIMARY KEY` constraint applied.
* `team_name`, which specifies the team  name as `TEXT`. This column thus has the `NOT NULL` constraint applied.
* `users`, which specifies the number of users within this team as `INTEGER`. This column has the `CHECK users IN (1,2,3)` , `DEFAULT 1`.

#### User_Teams

The `user_teams` table includes:

* `user_id`,  which specifies the unique ID for the user as an `INTEGER`. This column thus has the `FOREIGN KEY` that references the `users` table constraint applied.
* `team_id`, which specifies the unique ID for the team as an `INTEGER`. This column thus has the `FOREIGN KEY` that references the `teams` table constraint applied.
  
#### Competitions

The `competitions` table includes:

* `id`, which specifies the unique ID for the compeition as an `INTEGER`. This column thus has the `PRIMARY KEY` constraint applied.
* `creator_id`,which specifies the ID of the user who created the competition  This column thus has the `FOREIGN KEY`that references the `user` table constraint applied.
* `name`, which specifies the competition name as `TEXT` .This column thus has the `NOT NULL` constraint applied.
* `duration` ,  which specifies the competition duration in hours as `Numeric` and `NOT NULL` constraint applied.
* `starting_time`, which is the timestamp at which the competition start.
* `ending_time`, which is the timestamp at which the competition end.
* `scoreboard_type`, which is the type of the scoreboard as `TEXT`,`NOT NULL` ,`CHECK IN(pass-fail,score)` and `DEFAULT pass-fail`.
* `penalty_time` , Penalty time for a wrong submission. Only relevant if scoreboard_type is pass-fail.
  
#### Teams_Competitions

The `teams_competitions` table includes:

* `competition_id`,  which specifies the unique ID for the competition as an `INTEGER`. This column thus has the `FOREIGN KEY` that references the `competitions` table constraint applied.
* `team_id`, which specifies the unique ID for the team as an `INTEGER`. This column thus has the `FOREIGN KEY` that references the `teams` table constraint applied.
* `score`, which the score of the team in this competition as `INTEGER`,`DEFAULT 0`.
* `rank`, which the rank of the team in this competition as `INTEGER`.
  
#### Problems

The `problems` table includes:

* `id`, which specifies the unique ID for the instructor as an `INTEGER`. This column thus has the `PRIMARY KEY` constraint applied.
* `competition_id`, which specifies the unique ID for the competition as an `INTEGER`. This column thus has the `FOREIGN KEY` that references the `competition` table constraint applied.
* `label` , Label of the problem on the scoreboard, as `TEXT` typically a single capitalized letter and `NOT NULL`.
* `name`, which is the name of the problem set as `TEXT`.This column thus has the `UNIQUE` and `NOT NULL` constraint applied.
* `ordinal` , A unique number that determines the order the problems as `INTEGER` and `NOT NULL`.
* `time_limit` , Time limit in seconds per test data set (i.e. per single run). Should be a `NUMERIC` multiple of 0.001 and `NOT NULL`.
* `test_data_count`, the number of test cases as `INTEGER`and `NOT NULL`.

#### Test_cases

The `test_cases` table includes:

* `id`, which specifies the unique ID for the test_case as an `INTEGER`. This column thus has the `PRIMARY KEY` constraint applied.
* `problem_id`, which is the ID of the problem which the test case associated to as an `INTEGER`. This column thus has the `FOREIGN KEY` constraint applied, referencing the `id` column in the `problems` table to ensure data integrity.
* `content`, which the input , output and explanation if it is necessary as `TEXT`.

#### Topics

The `topics` table includes:

* `id`, which specifies the unique ID for the topic as an `INTEGER`. This column thus has the `PRIMARY KEY` constraint applied.
* `name`, the name of the topic as `TEXT`, `NOT NULL`.

#### Problems_Topics

The `problems_topics` table includes:

* `problem_id`,  which specifies the unique ID for the problem as an `INTEGER`. This column thus has the `FOREIGN KEY` that references the `problems` table constraint applied.
* `topic_id`, which specifies the unique ID for the topic as an `INTEGER`. This column thus has the `FOREIGN KEY` that references the `topics` table constraint applied.

#### Submissions

The `submissions` table includes:

* `id`, which specifies the unique ID for the submission as an `INTEGER`. This column thus has the `PRIMARY KEY` constraint applied.
* `team_id`, which is the ID of the user who made the submission as an `INTEGER`. This column thus has the `FOREIGN KEY` constraint applied, referencing the `id` column in the `team` table to ensure data integrity.
* `problem_id`, which is the ID of the problem which the submission solves as an `INTEGER`. This column thus has the `FOREIGN KEY` constraint applied, referencing the `id` column in the `problems` table to ensure data integrity.
* `time`, `timestamp` of when the submission was made, defaults to the current timestamp when a new row is inserted.
* `language`, Identifier of the language submitted for `TEXT`, `CHECK IN(ada,c,cpp,csharp,go,haskell,java,javascript,kotlin,objectivec,pascal,php,prolog,python2,python3,ruby,rust,scala)` and `NOT NULL`.
* `judgement`,the result of the submition `CHECK IN (in_queue,accepted,wrong_answer,time_limit_exceeded,memory_limit_exceeded,compilation_error)`, `DEFAULT in_queue`.

#### Clarifications

The `clarifications` table includes:

* `id`, which specifies the unique ID for the clarification as an `INTEGER`. This column thus has the `PRIMARY KEY` constraint applied.
* `team_id`, which specifies the ID of the team who asked for the clarification as an `INTEGER`. This column thus has the `FOREIGN KEY` constraint applied, referencing the `id` column in the `teams` table, which ensures that each clarification be referenced back to a team.
* `problem_id`, which specifies the ID of the problem for which clarification has been requeste as an `INTEGER`. This column thus has the `FOREIGN KEY` constraint applied, referencing the `id` column in the `problems` table, which ensures that each clarification be referenced back to a problem.
* `content`,the clarification content as `TEXT`, `NOT NULL`.

#### Anouncements

The `anouncements` table includes:

* `id`, which specifies the unique ID for the announcement as an `INTEGER`. This column thus has the `PRIMARY KEY` constraint applied.
* `competition_id`, which specifies the ID of the competition to clarify one or many points in one or more problems or submissions as an `INTEGER`. This column thus has the `FOREIGN KEY` constraint applied, referencing the `id` column in the `competitions` table, which ensures that each announcement be referenced back to a competition.
* `content`,the announcement content as `TEXT`, `NOT NULL` written by the competition creator.


### Relationships

The below entity relationship diagram describes the relationships among the entities in the database.

![ER Diagram](Entity Relation Ship Diagram.png)

As detailed by the diagram:

* One team is capable of making 0 to many submissions. 0, if they have yet to submit any work, and many if they submit to more than one problem (or make more than one submission to any one problem). A submission is made by one and only one team.
* A submission is associated with one and only one problem. At the same time, a problem can have 0 to many submissions: 0 if no team has yet submitted work to that problem, and many if more than one team has submitted work for that problem.
* A team contains one to many users and users belong to at least one team.
* A competition created by one user, contains many problems, many teams can participate and there are 0 to many announcements related to it.
* A team has score in particular competition and can particate in many competitions.
* A problem belongs to one competition.
* A clarification is asked by one team associated to one problem.Ateam can ask of many clarifications for many problems.
* A problem has one to many test cases, A test case associated to one problem .
* A problem can be associated to many topics and a topic can be associated to many problems.

