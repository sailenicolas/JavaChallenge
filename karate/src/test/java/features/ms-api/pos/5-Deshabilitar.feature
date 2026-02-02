Feature: MS  - Deshabilitación POS

Background:
  * url urlms
  * header Content-Type = 'application/json'

  @ms-api   @v1.0.28
Scenario Outline: "<VDescripcion>"

  Given path "/ms-api/Pos"
  And param id = "8"
  When method delete
  Then status <Vstatus>
  Examples:
    |read("../../../resources/MS/ms-api/Pos/DeshabilitacionSuscripcion.csv")|

  @ms-api   @v1.0.28
Scenario: 138311 - Pos / Deshabilitar - No ingresar parámetro id

  Given path "/ms-api/Pos"
  When method delete
  Then status 400
  And match response == read("../../../resources/MS/ms-api/Pos/response/eliminar/IdNulo.json")