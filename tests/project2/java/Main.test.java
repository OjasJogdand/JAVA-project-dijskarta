{
  "tests": [
    {
      "testName": "testReadCitiesDatasetValidFile",
      "inputFile": "cities_dataset.txt",
      "expectedCityIndex": {
        "Pune": 0,
        "Mumbai": 1,
        "Delhi": 2
      },
      "expectedAdjList": [
        [
          [1, 100],
          [2, 200]
        ],
        [
          [0, 100],
          [2, 150]
        ],
        [
          [0, 200],
          [1, 150]
        ]
      ]
    },
    {
      "testName": "testReadCitiesDatasetEmptyFile",
      "inputFile": "empty_cities_dataset.txt",
      "expectedCityIndex": {},
      "expectedAdjList": []
    },
    {
      "testName": "testReadCitiesDatasetInvalidFile",
      "inputFile": "invalid_cities_dataset.txt",
      "expectedCityIndex": null,
      "expectedAdjList": null
    }
  ]
}