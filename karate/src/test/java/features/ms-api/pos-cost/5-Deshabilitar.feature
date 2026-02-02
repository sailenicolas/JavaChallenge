Feature: MS  - Deshabilitación POS COST

Background:
  * url urlms
  * header Content-Type = 'application/json'

  @ms-api   @v1.0.28
  Scenario Outline: "<VDescripcion>"

    Given path "/ms-api/PosCost"
    And param id = "COST:9:1"
    When method delete
    Then status <Vstatus>
    Examples:
      |read("../../../resources/MS/ms-api/PosCost/DeshabilitacionSuscripcion.csv")|

  @ms-api   @v1.0.28
Scenario: 138311 - PosCost / Deshabilitar - No ingresar parámetro id

  Given path "/ms-api/PosCost"
  When method delete
  Then status 400
  And match response == read("../../../resources/MS/ms-api/PosCost/response/eliminar/IdNulo.json")