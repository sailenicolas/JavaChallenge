Feature: MS  - Consulta POS

Background:
  * url urlms
  * header Content-Type = 'application/json'

  @ms-api   @v1.0.28
Scenario Outline: "<VDescripcion>"

  Given path "/ms-api/Pos/byId/1"
  When method get
  Then status <Vstatus>
  And match response == read("../../../resources/MS/ms-api/Pos/response/consulta/<Vresponse>")
  Examples:
    |read("../../../resources/MS/ms-api/Pos/ConsultaSuscripcion.csv")|

@ms-api   @v1.0.28
Scenario: 138289 - Pos / Consulta - Ingresar data no existente

  Given path "/ms-api/Pos/byId/76857020"
  When method get
  Then status 404

  @ms-api   @v1.0.28
  Scenario: 138289 - Pos / Consulta - Ingresar data error

    Given path "/ms-api/Pos/byId/222d"
    When method get
    Then status 400
    And match response == read("../../../resources/MS/ms-api/pos/response/consulta/IdError.json")
