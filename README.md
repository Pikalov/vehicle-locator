Run the project by following command: 
`mvnw spring-boot:run`

Rest endpoints available by url:
http://localhost:8080/vehicle-locator/

Documented api available on Springfox Swagger UI:
http://localhost:8080/swagger-ui.html

docker command to pull postgis image:
`docker pull mpikalov/postgis:vehicle_locator_db`

docker command to run postgis image:
`docker run --name "postgis" -p 25432:5432 -d -t vehicle_locator_db`


All endpoints use GeoJson to transfer geo-data.

**Request body examples**:

VehicleLocation:
`{
    "id": 0,
    "location": {
        "type": "Point",
        "coordinates": [
            1.0,
            2.0
        ]
    }
}`

SearchArea:
`{
    "type": "Polygon",
    "coordinates": [
        [
            [0.0, 0.0], [10.0, 0.0], [10.0, 10.0], [0.0, 10.0], [0.0, 0.0]
        ]
    ]
}`