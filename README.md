# Mood sensing app
It is web application that provides the apis to save users mood based on the location. It also provides
the closest location of user based on mood (Change this correctly)

## Features

- Upload a mood capture for a given user and location
- Return the mood frequency distribution for a given user
- Given the user’s current location, return the closest location where the user has been happy.

## Tech Stack

- **Backend**: Java, Spring Boot
- **Storage**: H2 db (In memory)

## Installation & Setup

### Backend (Java Spring Boot)

1. Move to the current directory:
   ```sh
   cd stem
   ```
2. Build the application jar:
   ```sh
   ./gradlew build
   ```
3. Create a docker image in local (Prerequisite docker needs to installed):
   ```sh
    docker build -t stem-mood-sensing-app:latest . #latest can be your version
   ```
4. Run the application using docker:
   ```sh
   docker run -dp 127.0.0.1:8080:8080 stem-mood-sensing-app:latest
   ```
   The backend will be available at `http://127.0.0.1:8080`

## API Endpoints

### Upload a new Mood for the user
#### Endpoint: `POST /api/mood/upload`
##### Payload:
```json
{
   "mood": "happy", <string> - can be happy, sad or neutral
   "userId": 20 , <long> - must be a valid userId
   "latitude": 22.4, <double>
   "longitude": 23.3 <double>
}
```
##### Response:
```
Mood uploaded successfully.
```

### Get the frequency distribution of the user
#### Endpoint: `Get /api/mood/frequency/<userId>`

##### Response:
```json
{
  "angry": <count of the angry mood for user>,
  "sad": <count of the sad mood for user>,
  "neutral": <count of the neutral mood for user>
}
```

### Get the closest happy location of the user
#### Endpoint: `Get /api/mood/happy-location/<userId>`
#### Request params: `latitude` - current location latitude point of user, `longitude` - current location longitude point of user

##### Response:
```json
{
  "latitude": <latitue of the location>,
  "longitude": <longitude of the location>,
  "distance": <distance from current location>
}
```



| Method | Endpoint                                        | Description                          |
|--------| ----------------------------------------------- | ------------------------------------ |
| POST   | `/api/mood/upload`                          | Upload a new Mood for the user            |
| GET    | `/api/mood/frequency/<userId>`            | Get the frequency distribution of the user  |
| GET    | `/api/mood/happy-location/<userId>`             | Get the closest happy location of the user |

## Running Tests

To run tests, run the following command in backend-server folder

```bash
  ./gradlew test
```


[//]: # (## Demo)

[//]: # ()
[//]: # (Check out the demo video: [Demo.mp4]&#40;./Demo.mp4&#41; &#40;need to be done&#41;)

## Author

Developed by **Sai Manoj**

---

for detecting emotion using the user location: https://amirmustafaofficial.medium.com/azure-face-api-advance-face-detection-5959794c26c0

indexes for optimizing the distance calculation
