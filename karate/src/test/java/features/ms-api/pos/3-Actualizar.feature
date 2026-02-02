Feature: MS  - Actualizar POS

Background:
  * url urlms
  * header Content-Type = 'application/json'

  @ms-api   @v1.0.28
Scenario Outline: "<VDescripcion>"

  Given path "/ms-api/Pos"
  * def ruta = "../../../resources/MS/ms-api/Pos/request/actualizacion/<Vjson>"
  * def req = read(ruta)
  And request req
  And param id = "5"
  When method put
  Then status <Vstatus>
  And match response == read("../../../resources/MS/ms-api/Pos/response/actualizacion/<Vresponse>")
  Examples:
    |read("../../../resources/MS/ms-api/Pos/ActualizarSuscripcion.csv")|

  @ms-api   @v1.0.28
Scenario: 138299 - Pos / Actualizar - No ingresar parámetro

  Given path "/ms-api/Pos"
  * def ruta = "../../../resources/MS/ms-api/Pos/request/actualizacion/Suscripcion.json"
  * def req = read(ruta)
  And request req
  When method put
  Then status 400
  And match response == read("../../../resources/MS/ms-api/Pos/response/actualizacion/IdNulo.json")


  @ms-api   @v1.0.28
Scenario: 138302 -  Pos / Actualizar - Ingresar campos requeridos vacíos

  Given path "/ms-api/Pos"
  * def ruta = "../../../resources/MS/ms-api/Pos/request/actualizacion/CamposVacios.json"
  * def req = read(ruta)
  And request req
  When method put
  Then status 400

  @ms-api   @v1.0.28
Scenario: 138303 - Pos / Actualizar - No ingresar campos requeridos

  Given path "/ms-api/Pos"
  * def ruta = "../../../resources/MS/ms-api/Pos/request/actualizacion/CamposNulos.json"
  * def req = read(ruta)
  And request req
  When method put
  Then status 400