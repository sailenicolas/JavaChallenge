Feature: MS  - Actualizar POS Cost

Background:
  * url urlms
  * header Content-Type = 'application/json'

  @ms-api   @v1.0.28
Scenario Outline: "<VDescripcion>"

  Given path "/ms-api/PosCost"
  * def ruta = "../../../resources/MS/ms-api/PosCost/request/actualizacion/<Vjson>"
  * def req = read(ruta)
  And param id = "COST:1:2"
  And request req
  When method put
  Then status <Vstatus>
  And match response == read("../../../resources/MS/ms-api/PosCost/response/actualizacion/<Vresponse>")
  Examples:
    |read("../../../resources/MS/ms-api/PosCost/ActualizarSuscripcion.csv")|

  @ms-api   @v1.0.28
Scenario: 138299 - PosCost / Actualizar - No ingresar parámetro id

  Given path "/ms-api/PosCost"
  * def ruta = "../../../resources/MS/ms-api/PosCost/request/actualizacion/Suscripcion.json"
  * def req = read(ruta)
  And request req
  When method put
  Then status 400
  And match response == read("../../../resources/MS/ms-api/PosCost/response/actualizacion/IdNulo.json")


  @ms-api   @v1.0.28
Scenario: 138302 -  PosCost / Actualizar - Ingresar campos requeridos vacíos

  Given path "/ms-api/PosCost"
  * def ruta = "../../../resources/MS/ms-api/PosCost/request/actualizacion/CamposVacios.json"
  * def req = read(ruta)
  And request req
  When method put
  Then status 400

  @ms-api   @v1.0.28
  Scenario: 138303 - PosCost / Actualizar - No ingresar campos requeridos

    Given path "/ms-api/PosCost"
    * def ruta = "../../../resources/MS/ms-api/PosCost/request/actualizacion/CamposNulos.json"
    * def req = read(ruta)
    And request req
    When method put
    Then status 400

  @ms-api   @v1.0.28
  Scenario: 138303 - PosCost / Actualizar - Ingresar campos requeridos error ID

    Given path "/ms-api/PosCost"
    * def ruta = "../../../resources/MS/ms-api/PosCost/request/actualizacion/Suscripcion.json"
    * def req = read(ruta)
    And param id = "d"
    And request req
    When method put
    Then status 400
    And match response == read("../../../resources/MS/ms-api/PosCost/response/actualizacion/IdError.json")
