Run the project by following command: 
`mvnw spring-boot:run`

Rest endpoints available by url:
http://localhost:8080/vehicle-locator/

docker command to pull postgis image:
`docker pull mpikalov/postgis:vehicle_locator_db`


All endpoints use GeoJson to transfer geo-data.

**Request body examples**:

VehicleLocation:
`{
    "id": null,
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