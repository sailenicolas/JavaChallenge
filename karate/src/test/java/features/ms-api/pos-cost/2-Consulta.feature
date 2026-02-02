Feature: MS  - Consulta POS COST

Background:
  * url urlms
  * header Content-Type = 'application/json'

  @ms-api   @v1.0.28
Scenario Outline: "<VDescripcion>"

  Given path "/ms-api/PosCost/byId/1/2"
  When method get
  Then status <Vstatus>
  And match response == read("../../../resources/MS/ms-api/PosCost/response/consulta/<Vresponse>")
  Examples:
    |read("../../../resources/MS/ms-api/PosCost/ConsultaSuscripcion.csv")|

@ms-api   @v1.0.28
Scenario: 138289 - Pos Cost / Consulta - Ingresar data no existente

  Given path "/ms-api/PosCost/byId/76857020/2222"
  When method get
  Then status 404
   