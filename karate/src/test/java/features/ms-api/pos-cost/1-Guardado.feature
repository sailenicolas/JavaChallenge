Feature: MS  - Guardar POS

Background:
  * url urlms
  * header Content-Type = 'application/json'

  @ms-api   @v1.0.0
Scenario Outline: "<VDescripcion>"

  Given path "/ms-api/PosCost"
  * def ruta = "../../../resources/MS/ms-api/PosCost/request/guardado/<Vjson>"
  * def req = read(ruta)
  And request req
  When method post
  Then status <Vstatus>
  And match response == read("../../../resources/MS/ms-api/PosCost/response/guardado/<Vresponse>")
  Examples:
    |read("../../../resources/MS/ms-api/PosCost/GuardarSuscripcion.csv")|

  @ms-api   @v1.0.0
Scenario: 138277 -  POS / Ingresar método no permitido

  Given path "/ms-api/PosCost"
  * def ruta = "../../../resources/MS/ms-api/PosCost/request/guardado/Suscripcion.json"
  * def req = read(ruta)
  And request req
  When method PATCH
  Then status 405
  And match response == read("../../../resources/MS/ms-api/PosCost/response/guardado/MétodoIncorrecto.json")

  @ms-api   @v1.0.0
Scenario: 138279 -  POS / Guardado - Ingresar campos requeridos vacíos

  Given path "/ms-api/PosCost"
  * def ruta = "../../../resources/MS/ms-api/PosCost/request/guardado/CamposVacios.json"
  * def req = read(ruta)
  And request req
  When method post
  Then status 400

  @ms-api   @v1.0.0
Scenario: 138280 -  POS / Guardado - No ingresar campos requeridos

  Given path "/ms-api/PosCost"
  * def ruta = "../../../resources/MS/ms-api/PosCost/request/guardado/CamposNulos.json"
  * def req = read(ruta)
  And request req
  And param rutEmpresa = "12232088-k"
  And param tefType = "ALL"
  When method post
  Then status 400

  @ms-api   @v1.0.0
Scenario: 138278 -  POS / Ingresar content type no soportado

  Given path "/ms-api/PosCost"
  * header Content-Type = 'text/html'
  * def ruta = "../../../resources/MS/ms-api/PosCost/request/guardado/Suscripcion.json"
  * def req = read(ruta)
  And request req
  When method post
  Then status 415
  And match response == read("../../../resources/MS/ms-api/PosCost/response/guardado/ContentTypeIncorrecto.json")
