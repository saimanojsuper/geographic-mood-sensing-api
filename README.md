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

### Upload an Excel File
#### Endpoint: `POST /api/document/upload`
##### Payload:
```json
{    
  "file": "<Excel file>" // in form data
}
```
##### Response:
```json
{
  "message": "File uploaded successfully",
  "document_id": "<document_id>",
  "data": [<processed data>],
  "columns": [<column names>]
}
```

### Perform an Operation on a Document
#### Endpoint: `POST /api/document/<document_id>/operations`
##### Payload Examples:

**Add Column**
```json
{
  "type": "addColumn",
  "params": {
      "columnName1": "Price",
      "columnName2": "Tax",
      "newColumnName": "Total"
  }
}
```

**Filter Rows**
```json
{
  "type": "filterRows",
  "params": {
      "condition": "Price > 70000"
  }
}
```

**Combine Columns**
```json
{
  "type": "combineColumns",
  "params": {
      "columnName1": "Price",
      "columnName2": "Tax",
      "separator": " % ",
      "newColumnName": "Price % Tax"
  }
}
```

**Undo Operation**
```json
{
  "type": "undo",
  "params": {}
}
```

##### Response:
```json
{
  "message": "Operation completed successfully",
  "version": <new_version>,
  "data": [<updated data>],
  "columns": [<updated columns>]
}
```

### Retrieve the Latest Document Version
#### Endpoint: `GET /api/document/<document_id>/latest`
##### Response:
```json
{
  "document_id": "<document_id>",
  "version": <latest_version>,
  "data": [<latest data>],
  "columns": [<latest columns>]
}
```

### Retrieve a Specific Document Version
#### Endpoint: `GET /api/document/<document_id>/versions/<int:version>`
##### Response:
```json
{
  "document_id": "<document_id>",
  "version": <requested_version>,
  "data": [<versioned data>],
  "columns": [<versioned columns>]
}
```

| Method | Endpoint                                             | Description                          |
| ------ | ---------------------------------------------------- | ------------------------------------ |
| POST   | `/api/document/upload`                               | Upload an Excel file                 |
| POST   | `/api/document/<document_id>/operations`             | Perform an operation on a document   |
| GET    | `/api/document/<document_id>/latest`                 | Retrieve the latest document version |
| GET    | `/api/document/<document_id>/versions/<int:version>` | Retrieve a specific document version |


## Running Tests

To run tests, run the following command in backend-server folder

```bash
  ./gradlew test
```


## Demo

Check out the demo video: [Demo.mp4](./Demo.mp4) (need to be done)

## Author

Developed by **Sai Manoj**

---

for detecting emotion: https://amirmustafaofficial.medium.com/azure-face-api-advance-face-detection-5959794c26c0

indexes for optimizing the distance calculation
